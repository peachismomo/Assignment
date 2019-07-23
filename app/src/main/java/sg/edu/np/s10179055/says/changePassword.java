package sg.edu.np.s10179055.says;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

    public void onChangePassword(View v)
    {
        SharedPreferences currentuser = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        final String currentPassword = currentuser.getString("password","");

        final Account a = new Account();
        a.setPassword(cfmNewPass.toString());

        if (currentPassword.equals(oldPass.getText()))
        {
            if (newPass.getText().equals(cfmNewPass.getText()))
            {
                reference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot memberSnapshot : dataSnapshot.getChildren())
                        {
                            String memberKey = memberSnapshot.getKey();
                            Account CurrentUser = memberSnapshot.getValue(Account.class);
                            if(CurrentUser.getPassword().equals(currentPassword))
                            {
                                if(a.regexPassword())
                                {
                                    reference.child(memberKey).child("password").setValue(a);
                                }

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }



    }

}
