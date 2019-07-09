package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText UsernameET, NameET, NRICET, DOBET, StudentIDET, CourseET, EmailET, PasswordET, RePasswordET;

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
        String username = UsernameET.getText().toString();
        String name = NameET.getText().toString();
        String nric = NRICET.getText().toString();
        String dob = DOBET.getText().toString();
        String StudentId = StudentIDET.getText().toString();
        String course = CourseET.getText().toString();
        String email = EmailET.getText().toString();
        String password = PasswordET.getText().toString();
        String rePassword = RePasswordET.getText().toString();
        int loginCount = 0;

        Account registerAcc = new Account(username, password, course, email, StudentId, dob, loginCount, name, nric) {
        };

        if (password.equals(rePassword)) {
            if (registerAcc.regex()) {
                Dbhandler dbhandler = new Dbhandler(this, null, null, 1);
                dbhandler.addAccount(registerAcc);
                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                Intent studentPage = new Intent(getApplicationContext(), student.class);
                startActivity(studentPage);
            } else
                Toast.makeText(getApplicationContext(), "Please ensure that username has is alphanumeric and is 6-12 characters long./n" +
                        "Please ensure that password contains at least one uppercase, one symbol and one numeric value.", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
    }
}
