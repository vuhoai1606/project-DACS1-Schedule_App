package controller;

import model.CommonConstants;
import model.connectDbLogin;
import util.sendMail;
import view.ForgotPassFormGUI;
import view.VerificationDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;


public class forgotPass {
    private static String currentCode;
    private static String targetEmail;

    public static void attachForgotPassActions(ForgotPassFormGUI form) {
        form.getChangePasswordButton().addActionListener(e -> {
            String username = form.getUsernameField().getText();
            String email = form.getMailField().getText();
            if (!validateEmailForUser(username, email)) {
                JOptionPane.showMessageDialog(form, "Sai thông tin username hoặc email chưa được đăng ký.");
                return;
            }
            targetEmail = email;
            currentCode = generateVerificationCode();
            sendMail.sendVerificationCode(email, currentCode);
            showVerificationDialog(form);
        });
        // Quay lại giao diện Login
        form.getLoginLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                form.dispose();
                new view.LoginFormGUI().setVisible(true);
            }
        });

        // mở GUI Register
        form.getRegisterLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                form.dispose();
                new view.RegisterFormGUI().setVisible(true);
            }
        });
    }

    private static boolean validateEmailForUser(String username, String email) {
        String query = "SELECT * FROM users WHERE username = ? AND email = ?";
        try (Connection conn = connectDbLogin.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String generateVerificationCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    private static void showVerificationDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Nhập mã xác nhận", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 5));
        JLabel label = new JLabel("Nhập mã xác nhận đã gửi qua email:");
        JTextField codeField = new JTextField();

        JPanel resendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel resendLabel = new JLabel("Gửi lại mã");
        JLabel countdownLabel = new JLabel("(30s)");
        resendLabel.setForeground(Color.GRAY);
        resendLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resendLabel.setEnabled(false);

        resendPanel.add(resendLabel);
        resendPanel.add(countdownLabel);
        panel.add(label);
        panel.add(codeField);
        panel.add(resendPanel);
        dialog.add(panel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Xác nhận");
        dialog.add(confirmButton, BorderLayout.SOUTH);

        java.util.Timer[] timer = new java.util.Timer[1]; // để quản lý timer ngoài scope
        final int[] countdown = {30};

        Runnable startCountdown = () -> {
            countdown[0] = 30;
            resendLabel.setForeground(Color.GRAY);
            resendLabel.setEnabled(false);

            if (timer[0] != null) timer[0].cancel();
            timer[0] = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    countdown[0]--;
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText("(" + countdown[0] + "s)");
                    });
                    if (countdown[0] <= 0) {
                        timer[0].cancel();
                        SwingUtilities.invokeLater(() -> {
                            resendLabel.setForeground(CommonConstants.PRIMARY_COLOR); // dễ nhìn hơn
                            resendLabel.setEnabled(true);
                            countdownLabel.setText("");
                        });
                    }
                }
            };
            timer[0].scheduleAtFixedRate(task, 1000, 1000);
        };
        startCountdown.run(); // Bắt đầu khi dialog mở

        resendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!resendLabel.isEnabled()) return;

                currentCode = generateVerificationCode();
                sendMail.sendVerificationCode(targetEmail, currentCode);
                startCountdown.run(); // Đếm lại
            }
        });

        confirmButton.addActionListener(e -> {
            String inputCode = codeField.getText();
            if (inputCode.equals(currentCode)) {
                dialog.dispose();
                showResetPasswordDialog(parent); // ✅ mở form đổi mật khẩu
            } else {
                JOptionPane.showMessageDialog(dialog, "Mã xác minh không đúng!");
            }
        });

        dialog.setVisible(true);
    }

    private static void showResetPasswordDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Đặt lại mật khẩu", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(parent);

        JLabel passLabel = new JLabel("Mật khẩu mới:");
        JPasswordField passField = new JPasswordField();
        JLabel rePassLabel = new JLabel("Nhập lại mật khẩu:");
        JPasswordField rePassField = new JPasswordField();
        JButton confirmButton = new JButton("Xác nhận");

        dialog.add(passLabel); dialog.add(passField);
        dialog.add(rePassLabel); dialog.add(rePassField);
        dialog.add(new JLabel()); dialog.add(confirmButton);

        confirmButton.addActionListener(e -> {
            String newPass = new String(passField.getPassword());
            String reNewPass = new String(rePassField.getPassword());

            if (!model.checkStrongPass.isPasswordStrong(newPass)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu phải >=6 ký tự, chứa chữ thường, chữ HOA, ký tự đặc biệt và số.");
                return;
            }
            if (!newPass.equals(reNewPass)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu nhập lại không khớp!");
                return;
            }

            if (updatePassword(targetEmail, newPass)) {
                JOptionPane.showMessageDialog(dialog, "Đặt lại mật khẩu thành công!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Có lỗi xảy ra khi cập nhật mật khẩu.");
            }
        });

        dialog.setVisible(true);
    }

    private static boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password_hash = ? WHERE email = ?";
        try (Connection conn = connectDbLogin.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 🔥 Encrypt mật khẩu trước khi lưu
            String encryptedPassword = util.AESEncryption.encrypt(newPassword);

            stmt.setString(1, encryptedPassword); // Lưu mật khẩu đã mã hóa AES
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}