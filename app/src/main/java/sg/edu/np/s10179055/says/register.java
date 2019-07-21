package sg.edu.np.s10179055.says;

import android.content.Intent;
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
        Intent cancel = new Intent(getBaseContext(), MainActivity.class);
        startActivity(cancel);
    }

    public void onConfirm(View view) {
        final String username = UsernameET.getText().toString();
        final String name = NameET.getText().toString();
        final String nric = NRICET.getText().toString();
        final String dob = DOBET.getText().toString();
        final String StudentId = StudentIDET.getText().toString();
        final String course = CourseET.getText().toString();
        final String email = EmailET.getText().toString();
        final String password = PasswordET.getText().toString();
        final String rePassword = RePasswordET.getText().toString();
        int loginCount = 0;

        final Account registerAcc = new Account();

        registerAcc.setUsername(username);
        registerAcc.setName(name);
        registerAcc.setNRIC(nric);
        registerAcc.setDOB(dob);
        registerAcc.setStudentNo(StudentId);
        registerAcc.setCourse(course);
        registerAcc.setEmail(email);
        registerAcc.setPassword(password);
        registerAcc.setLoginCount(loginCount);

        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Account checkUser = data.getValue(Account.class);
                    if(checkUser.getUsername().equals(username)){
                        Toast.makeText(getApplicationContext(),"Username Taken", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (password.equals(rePassword)) {
                            if (registerAcc.regex()) {
                                reference.push().setValue(registerAcc);

                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                                Intent studentPage = new Intent(getApplicationContext(), student.class);
                                startActivity(studentPage);
                            } else if (registerAcc.regexPassword()) {
                                Toast.makeText(getApplicationContext(), "Please ensure that username is 6-12 characters long with at least one numerical value.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "Please ensure that contains at least one uppercase letter, one numerical value and one symbol.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
