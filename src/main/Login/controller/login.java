package controller;

import view.LoginFormGUI;
import model.connectDbLogin;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    public static void attachLoginAction(LoginFormGUI loginForm) {
        loginForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginForm.getUsernameField().getText();
                String password = new String(loginForm.getPasswordField().getPassword());

                if (validateLogin(loginForm, username, password)) {
                    JOptionPane.showMessageDialog(loginForm, "Login thành công!");

                    // Nếu tick Remember
                    if (loginForm.getRememberMeCheckBox().isSelected()) {
                        util.Remember.saveCredentials(username, password);
                    } else {
                        util.Remember.clearCredentials();
                    }

                    loginForm.dispose();
                    new view.ScheduleAppGUI().setVisible(true); // mở App lên
                } else {
                    JOptionPane.showMessageDialog(loginForm, "Sai thông tin đăng nhập!");
                }
            }
        });

        // FORGOT PASSWORD LABEL
        loginForm.getForgotPasswordLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginForm.dispose(); // đóng form hiện tại
                new view.ForgotPassFormGUI().setVisible(true); // mở forgot password
            }
        });

        // REGISTER LABEL
        loginForm.getRegisterLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginForm.dispose(); // đóng form hiện tại
                new view.RegisterFormGUI().setVisible(true); // mở form đăng ký
            }
        });
    }

    public static boolean validateLogin(LoginFormGUI loginForm, String username, String password) {
        String query = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection conn = connectDbLogin.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                // Username không tồn tại
                int option = JOptionPane.showConfirmDialog(
                        loginForm,
                        "Tài khoản không tồn tại. Bạn có muốn đăng ký không?",
                        "Thông báo",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    loginForm.dispose();
                    new view.RegisterFormGUI().setVisible(true);
                }
                return false;
            } else {
                // Username tồn tại, kiểm tra password
                String encryptedPassword = rs.getString("password_hash");
                String decryptedPassword = util.AESEncryption.decrypt(encryptedPassword);

                return password.equals(decryptedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
