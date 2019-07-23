package sg.edu.np.s10179055.says;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
    final double longg=0;

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

    public double getLong(Context c){

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(c);

        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) c, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                  longg=location.getLongitude();
            }
        });
        return longg;
    }
    public double getLat(){

    }
}
