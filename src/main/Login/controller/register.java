package controller;

import model.connectDbLogin;
import view.LoginFormGUI;
import view.RegisterFormGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class register {
    public static void attachRegisterAction(RegisterFormGUI registerForm) {
        registerForm.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = registerForm.getMailField().getText();
                String username = registerForm.getUsernameField().getText();
                String password = new String(registerForm.getPasswordField().getPassword());
                String rePassword = new String(registerForm.getRePasswordField().getPassword());

                if (!validateUserInput(email, username, password, rePassword)) {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ! Tên người dùng phải có ít nhất 6 ký tự. Mật khẩu phải trùng khớp.");
                    return;
                }

                // Gửi mã xác nhận
                String code = generateVerificationCode();
                boolean mailSent = util.sendMail.sendVerificationCode(email, code);
                if (!mailSent) {
                    JOptionPane.showMessageDialog(null, "Không thể gửi email xác nhận. Vui lòng kiểm tra lại mail.");
                    return;
                }

                // Nhập mã từ người dùng
                String inputCode = JOptionPane.showInputDialog(null,
                        "Nhập mã xác nhận đã gửi tới email của bạn:", "Xác nhận Email", JOptionPane.INFORMATION_MESSAGE);
                if (inputCode != null && inputCode.equals(code)) {
                    if (register(username, email, password)) {
                        registerForm.dispose();
                        new LoginFormGUI().setVisible(true);
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Username hoặc email đã tồn tại.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sai mã xác nhận.");
                }
            }
        });

        // Nhãn “Back to login”
        registerForm.getLoginLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerForm.dispose();
                new LoginFormGUI().setVisible(true);
            }
        });
    }

    public static boolean register(String username, String email, String password) {
        String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
        String insertQuery = "INSERT INTO users (username, email, password_hash, is_verified) VALUES (?, ?, ?, FALSE)";

        try (Connection conn = connectDbLogin.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) return false; // Username hoặc email đã tồn tại

            // 🔥 Thêm Encrypt mật khẩu tại đây
            String encryptedPassword = util.AESEncryption.encrypt(password);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, encryptedPassword); // Lưu mật khẩu đã mã hóa AES
                insertStmt.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean validateUserInput(String mail, String username, String password, String rePassword) {
        if (mail.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty())
            return false;
        if (username.length() < 6)
            return false;
        if (!password.equals(rePassword))
            return false;
        if (!model.checkStrongPass.isPasswordStrong(password))
            return false;
        return true;
    }

    private static String generateVerificationCode() {
        int code = (int)(Math.random() * 900000) + 100000; // 6 chữ số
        return String.valueOf(code);
    }
}
