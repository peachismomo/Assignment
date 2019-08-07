package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    DatabaseReference reference;
    EditText tvUserC, tvPassC;
    TextView registerTxt;
    Button loginBtn;
    SharedPreferences sharedPreferences;
    CheckBox checkBox;
    String rememberPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initalise();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String rememberUsername = sharedPreferences.getString("username", "");
        rememberPassword = sharedPreferences.getString("password", "");

        if (!rememberUsername.equals("") && !rememberPassword.equals("")) {
            login(rememberUsername, rememberPassword);
        } else {
            setContentView(R.layout.activity_login);

            initalise();

            //open register activity
            registerTxt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent register = new Intent(getBaseContext(), register.class);
                    startActivity(register);
                    return true;
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login(tvUserC.getText().toString(), tvPassC.getText().toString());
                }
            });

        }
    }

    public void login(final String username, final String password) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account checkLogin = data.getValue(Account.class);
                    //Check if user exists
                    if (checkLogin.getUsername().equals(username)) {
                        //Check if password is correct
                        if (checkLogin.getPassword().equals(password)) {
                            //Set current username with sharedpreferences
                            SharedPreferences.Editor editor = getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
                            editor.putString("username", username);

                            if (rememberPassword.equals("")) {
                                if (checkBox.isChecked()) {
                                    editor.putString("password", password);
                                }
                            }

                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            //Go to app
                            Intent studentPage = new Intent(getApplicationContext(), student.class);
                            startActivity(studentPage);
                            break;
                        } else
                            Toast.makeText(getApplicationContext(), "Password invalid", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Username invalid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initalise() {
        reference = FirebaseDatabase.getInstance().getReference("Member");
        tvUserC = findViewById(R.id.User_Edit);
        tvPassC = findViewById(R.id.pass_edit);
        registerTxt = findViewById(R.id.registerTxt);
        loginBtn = findViewById(R.id.btnSignin);
        checkBox = findViewById(R.id.rememberChkBox);
    }
}
