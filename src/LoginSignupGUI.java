import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

interface Authenticator {
    boolean authenticate(String username, String password) throws IOException;
    boolean createUser(String username, String password) throws IOException;
}

class LoginSignup implements Authenticator {
    private Map<String, String> userMap;
    private static final String DATA_FILE = "userdata.txt";

    public LoginSignup() throws IOException {
        userMap = new HashMap<>();
        loadUserData();
    }

    public boolean authenticate(String username, String password) {
        if (userMap.containsKey(username)) {
            String storedPassword = userMap.get(username);
            return storedPassword.equals(password);
        }
        return false;
    }

    public boolean createUser(String username, String password) throws IOException {
        if (!userMap.containsKey(username)) {
            userMap.put(username, password);
            saveUserData();
            return true;
        }
        return false;
    }

    public void loadUserData() throws IOException {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String username = parts[0];
                        String password = parts[1];
                        userMap.put(username, password);
                    }
                }
            }
        }
    }

   public void saveUserData() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<String, String> entry : userMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

   public static void createAndShowGUI() {
        JFrame frame = new JFrame("Login and Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Set the size of the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");
        JLabel statusLabel = new JLabel();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton);
        panel.add(statusLabel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (authenticateUser(username, password)) {
                        statusLabel.setText("Login successful!");
                    } else {
                        statusLabel.setText("Invalid username or password.");
                    }
                } catch (IOException ex) {
                    statusLabel.setText("An error occurred while processing the request.");
                    ex.printStackTrace();
                }

                clearFields(usernameField, passwordField);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (createNewUser(username, password)) {
                        statusLabel.setText("User created successfully!");
                    } else {
                        statusLabel.setText("Username already exists.");
                    }
                } catch (IOException ex) {
                    statusLabel.setText("An error occurred while processing the request.");
                    ex.printStackTrace();
                }

                clearFields(usernameField, passwordField);
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

   public static boolean authenticateUser(String username, String password) throws IOException {
        LoginSignup loginSignup = new LoginSignup();
        return loginSignup.authenticate(username, password);
    }

    public static boolean createNewUser(String username, String password) throws IOException {
        LoginSignup loginSignup = new LoginSignup();
        return loginSignup.createUser(username, password);
    }

    public static void clearFields(JTextField usernameField, JPasswordField passwordField) {
        usernameField.setText("");
        passwordField.setText("");
    }
}