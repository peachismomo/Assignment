package sg.edu.np.s10179055.says;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String username;
    private String password;
    private String Course;
    private String Email;
    private String StudentNo;
    private String DOB;
    private int LoginCount;
    private String Name;
    private String NRIC;

    public Account(String username, String password, String course, String email, String studentNo, String DOB, int loginCount, String name, String NRIC) {
        this.username = username;
        this.password = password;
        Course = course;
        Email = email;
        StudentNo = studentNo;
        this.DOB = DOB;
        LoginCount = loginCount;
        Name = name;
        this.NRIC = NRIC;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return Course;
    }

    public String getEmail() {
        return Email;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public String getDOB() {
        return DOB;
    }

    public int getLoginCount() {
        return LoginCount;
    }

    public String getName() {
        return Name;
    }

    public String getNRIC() {
        return NRIC;
    }

    public boolean regexUsername() {
        Pattern patternUser = Pattern.compile("^[A-Za-z]{6,12}$");
        Matcher matcherUser = patternUser.matcher(username);
        return matcherUser.matches();
    }

    public boolean regexPassword() {
        Pattern patternPass = Pattern.compile("^+[$&+,:;=?@#|]+[A-Z]+[0-9]$");
        Matcher matcherPassword = patternPass.matcher(password);
        return matcherPassword.matches();
    }

    public boolean regex() {
        return regexUsername() && regexPassword();
    }
}
