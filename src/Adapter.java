import java.io.IOException;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Adapter implements Authenticator {
    private final LoginSignupProxy loginSignupProxy;

    public Adapter(LoginSignupProxy loginSignupProxy) {
        this.loginSignupProxy = loginSignupProxy;
    }

    @Override
    public boolean authenticate(String username, String password) throws IOException {
        return loginSignupProxy.authenticate(username, password);
    }

    @Override
    public boolean createUser(String username, String password) throws IOException {
        return loginSignupProxy.createUser(username, password);
    }

    public void loadUserData() throws IOException {
        loginSignupProxy.loadUserData();
    }

    public void saveUserData() throws IOException {
        loginSignupProxy.saveUserData();
    }

    public static void ShowGUI() {
        LoginSignup.createAndShowGUI();
    }

    public boolean Userauthentication(String username, String password) throws IOException {
        return loginSignupProxy.authenticate(username, password);
    }

    public boolean createNewUser(String username, String password) throws IOException {
        return loginSignupProxy.createUser(username, password);
    }

//    public void clearFields(JTextField usernameField, JPasswordField passwordField) {
//        loginSignupProxy.clearFields(usernameField, passwordField);
//    }
}




