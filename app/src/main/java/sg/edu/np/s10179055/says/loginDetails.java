package sg.edu.np.s10179055.says;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginDetails {
    String username;
    String password;

    public void loginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String Username){
        username = Username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String Password){
        password = Password;
    }

    public boolean regexUsername() {
        Pattern patternUser = Pattern.compile("^[A-Za-z]{6,12}$");
        Matcher matcherUser = patternUser.matcher(username);
        if (matcherUser.matches()) {
            return true;
        } else return false;
    }

    public boolean regexPassword() {
        Pattern patternPass = Pattern.compile("^+[$&+,:;=?@#|]+[A-Z]+[0-9]$");
        Matcher matcherPassword = patternPass.matcher(password);
        if (matcherPassword.matches()) {
            return true;
        } else return false;
    }

    public boolean regex() {
        if (regexUsername() && regexPassword()) {
            return true;
        } else return false;
    }
}
