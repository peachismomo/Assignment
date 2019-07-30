package sg.edu.np.s10179055.says;

import android.content.Context;
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

public class changePassword extends AppCompatActivity {
    EditText oldPass;
    EditText newPass;
    EditText cfmNewPass;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPass = findViewById(R.id.etOldPass);
        newPass = findViewById(R.id.etNewPass);
        cfmNewPass = findViewById(R.id.etCfmNewPass);
        reference = FirebaseDatabase.getInstance().getReference().child("Member");
    }

    public void onChangePassword(View v) {
        SharedPreferences currentuser = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        final String currentusername = currentuser.getString("username", "");

        Account a = new Account();
        a.setPassword(newPass.getText().toString());
        if (!oldPass.getText().toString().isEmpty() && !newPass.getText().toString().isEmpty() && !cfmNewPass.getText().toString().isEmpty()) {
            if (a.regexPassword()) {
                if (!newPass.getText().toString().equals(oldPass.getText().toString())) {
                    if (newPass.getText().toString().equals(cfmNewPass.getText().toString())) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                                    Account a = memberSnapshot.getValue(Account.class);
                                    if (a.getUsername().equals(currentusername)) {
                                        if (oldPass.getText().toString().equals(a.getPassword())) {
                                            reference.child(memberSnapshot.getKey()).child("password").setValue(newPass.getText().toString());
                                            Intent profilePage = new Intent(getApplicationContext(), student.class);
                                            startActivity(profilePage);
                                            break;
                                        } else
                                            Toast.makeText(getApplicationContext(), "Password invalid!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else
                        Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "New password must not be the same as your old password!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Please ensure that password contains at least one uppercase letter, one numerical value and one symbol.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Please do not leave any blank fields", Toast.LENGTH_SHORT).show();
    }

}
