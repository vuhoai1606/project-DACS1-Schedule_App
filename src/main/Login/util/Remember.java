package util;

import java.io.*;

public class Remember {
    private static final String FILE_PATH = "remember.txt";

    public static void saveCredentials(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(username + "\n");
            writer.write(password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] loadCredentials() {
        String[] creds = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            creds[0] = reader.readLine(); // username
            creds[1] = reader.readLine(); // password
        } catch (IOException e) {
            // Không cần xử lý gì, file chưa tồn tại lần đầu
        }
        return creds;
    }

    public static void clearCredentials() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}