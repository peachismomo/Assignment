package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    EditText UsernameET, NameET, NRICET, DOBET, StudentIDET, CourseET, EmailET, PasswordET, RePasswordET;
    DatabaseReference reference;
    int emptyFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameET = findViewById(R.id.usernameTxt);
        NameET = findViewById(R.id.nameTxt);
        NRICET = findViewById(R.id.nric);
        DOBET = findViewById(R.id.dateOfBirthTxt);
        StudentIDET = findViewById(R.id.studentIdTxt);
        CourseET = findViewById(R.id.courseTxt);
        EmailET = findViewById(R.id.emailTxt);
        PasswordET = findViewById(R.id.passwordTxt);
        RePasswordET = findViewById(R.id.reenterPasswordTxt);
    }

    public void onCancel(View view) {
        Intent cancel = new Intent(getBaseContext(), Login.class);
        startActivity(cancel);
    }

    public void onConfirm(View view) {
        emptyFields = 0;
        final String username = UsernameET.getText().toString();
        final String name = NameET.getText().toString();
        final String nric = NRICET.getText().toString();
        final String dob = DOBET.getText().toString();
        final String StudentId = StudentIDET.getText().toString();
        final String course = CourseET.getText().toString();
        final String email = EmailET.getText().toString();
        final String password = PasswordET.getText().toString();
        final String rePassword = RePasswordET.getText().toString();
        EditText[] allFields = {UsernameET, NameET, NRICET, DOBET, StudentIDET, CourseET, EmailET, PasswordET, RePasswordET};

        for (int i = 0; i < allFields.length; i++) {
            if (allFields[i].getText().toString().trim().isEmpty()) {
                emptyFields++;
            }
        }
        int currentMode = 1; //0 is offline, 1 is online

        final Account registerAcc = new Account();

        registerAcc.setUsername(username);
        registerAcc.setName(name);
        registerAcc.setNRIC(nric);
        registerAcc.setDOB(dob);
        registerAcc.setStudentNo(StudentId);
        registerAcc.setCourse(course);
        registerAcc.setEmail(email);
        registerAcc.setPassword(password);
        registerAcc.setMode(currentMode);
        registerAcc.setImgId("none");
        registerAcc.setLocationLat(0);
        registerAcc.setLocationLong(0);

        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        //Run this when resetting database.
/*        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Member").push().setValue(registerAcc);*/

        if (emptyFields == 0) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Account checkUser = data.getValue(Account.class);
                        //Check if username already taken
                        if (checkUser.getUsername().equals(username)) {
                            Toast.makeText(getApplicationContext(), "Username Taken", Toast.LENGTH_SHORT).show();
                        } else {
                            //Check if password is the same
                            if (password.equals(rePassword)) {
                                //Check regex
                                if (registerAcc.regex()) {
                                    reference.push().setValue(registerAcc);
                                    SharedPreferences.Editor editor = getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
                                    editor.putString("username", username);
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                                    Intent studentPage = new Intent(getApplicationContext(), student.class);
                                    startActivity(studentPage);
                                    break;
                                } else if (registerAcc.regexPassword()) { //Check is only
                                    Toast.makeText(getApplicationContext(), "Please ensure that username is 6-12 characters long with at least one numerical value.", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getApplicationContext(), "Please ensure that password contains at least one uppercase letter, one numerical value and one symbol.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please make sure all fields are filled in", Toast.LENGTH_SHORT).show();
    }
}
