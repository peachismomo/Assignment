package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.jar.Attributes;

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

    public void onCancel(View view){
        Intent cancel = new Intent(getBaseContext(), MainActivity.class);
        startActivity(cancel);
    }

    public void onConfirm(View view){
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

        Account newAccount = new Account();
        //Add to database
    }
}
