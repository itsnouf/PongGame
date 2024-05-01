import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Adapter {
  LoginSignup LogSignUp ;

public Adapter(LoginSignup LogSignUp){
    this.LogSignUp = LogSignUp  ;

}

public boolean authentication(String username, String password) {
   return LogSignUp.authenticate(username, password);
}
public boolean usercreation(String username, String password) throws IOException{
    return LogSignUp.createUser(username, password);
   
}
public  void loadingData() throws IOException {
   LogSignUp.loadUserData();
}


public void savingData() throws IOException {
    LogSignUp.saveUserData();
}

public static void ShowGUI() {
    LoginSignup.createAndShowGUI();
}
public  boolean Userauthentication(String username, String password) throws IOException{
    return LogSignUp.authenticateUser(username, password);
}
public boolean createNewUser(String username, String password) throws IOException {
    return LogSignUp.createNewUser(username, password);
}
public  void clearFields(JTextField usernameField, JPasswordField passwordField) {
    LogSignUp.clearFields(usernameField , passwordField);
}
}




