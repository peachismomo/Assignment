package sg.edu.np.s10179055.says;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

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
    private int Mode;
    private String Name;
    private String NRIC;
    private double longg;
    private double latt;

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

    public int getMode() {
        return Mode;
    }

    public void setMode(int userMode) {
        this.Mode = userMode;
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

    public double getLong(Context c, Activity a) {
        int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(c);
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) c, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                longg = location.getLongitude();
            }
        });
        return longg;
    }

    public double getLat(Context c) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(c);

        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) c, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latt = location.getLatitude();
            }
        });
        return latt;
    }
}
