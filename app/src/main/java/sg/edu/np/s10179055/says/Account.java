package sg.edu.np.s10179055.says;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public Account() {

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

    public void setCourse(String course) {
        this.Course = course;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        this.StudentNo = studentNo;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getLoginCount() {
        return LoginCount;
    }

    public void setLoginCount(int loginCount) {
        this.LoginCount = loginCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getNRIC() {
        return NRIC;
    }

    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public boolean regexUsername() {
        Pattern patternUser = Pattern.compile("^[a-zA-Z0-9]*.{6,12}$");
        Matcher matcherUser = patternUser.matcher(username);
        return matcherUser.matches();
    }

    public boolean regexPassword() {
        Pattern patternPass = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcherPassword = patternPass.matcher(password);
        return matcherPassword.matches();
    }

    public boolean regex() {
        return regexUsername() && regexPassword();
    }
}
