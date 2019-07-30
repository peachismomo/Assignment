package sg.edu.np.s10179055.says;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
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
    private String imgId;
    private double locationLong;
    private double locationLat;

    public Account() {

    }

    public double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
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

    public Account getCurrentUser(Context context, final callBack call) {
        final String currentUsername = getCurrentUsername(context);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Member");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    if (CurrentUser.getUsername().equals(currentUsername)) {
                        call.onCallBack(CurrentUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return new Account();
    }

    public interface callBack {
        void onCallBack(Account account);
    }

    public void setFirebaseImgId(final String imgId, Context context) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Member");
        final String currentUsername = getCurrentUsername(context);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    if (CurrentUser.getUsername().equals(currentUsername)) {
                        reference.child(data.getKey()).child("imgId").setValue(imgId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getCurrentUsername(Context context) {
        SharedPreferences currentUser = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String currentUsername = currentUser.getString("username", "");
        return currentUsername;
    }

    //Push location to firebase database
    public void fireBaseLocation(Context context, final double locationLong, final double locationLat) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Member");
        final String currentUsername = getCurrentUsername(context);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    if (CurrentUser.getUsername().equals(currentUsername)) {
                        reference.child(data.getKey()).child("locationLong").setValue(locationLong);
                        reference.child(data.getKey()).child("locationLat").setValue(locationLat);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Set markers for all users
    public void locationArray(final GoogleMap map, final Context context) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    setMarker(map, CurrentUser, context);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Set marker options
    public void setMarker(final GoogleMap map, final Account currentuser, final Context context) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child(currentuser.getUsername() + "/" + currentuser.getImgId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    map.addMarker(new MarkerOptions().position(new LatLng(currentuser.getLocationLat(), currentuser.getLocationLong()))
                            .title(currentuser.getUsername()))
                            .setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }


}
