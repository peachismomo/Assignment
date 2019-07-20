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


public class Login extends AppCompatActivity {
    DatabaseReference reference;
    EditText tvUserC, tvPassC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reference = FirebaseDatabase.getInstance().getReference("Member");
        tvUserC = findViewById(R.id.User_Edit);
        tvPassC = findViewById(R.id.pass_edit);
    }

    public void onLoginClick(View v) {
        login(tvUserC.getText().toString(), tvPassC.getText().toString());
    }

    public void login(final String username, final String password) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account checkLogin = data.getValue(Account.class);
                    if (checkLogin.getUsername().equals(username)) {
                        if (checkLogin.getPassword().equals(password)) {
                            Intent studentPage = new Intent(getApplicationContext(), student.class);
                            startActivity(studentPage);
                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
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

}
