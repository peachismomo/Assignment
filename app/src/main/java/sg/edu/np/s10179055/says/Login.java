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


public class Login extends AppCompatActivity {
    DatabaseReference reference;
    EditText tvUserC, tvPassC;
    GoogleLocation gl=new GoogleLocation();

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
        //runOnce(); run this only if the locations get messed up
    }

    public void login(final String username, final String password) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account checkLogin = data.getValue(Account.class);
                    if (checkLogin.getUsername().equals(username)) {
                        if (checkLogin.getPassword().equals(password)) {
                            SharedPreferences.Editor editor = getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
                            editor.putString("username",username);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
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

    public void runOnce(){ //do not edit this code
        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("FooodPlaces");
        userLocation foodClub, munch, poolSide, makanPlace;
        foodClub = new userLocation();
        munch = new userLocation();
        poolSide = new userLocation();
        makanPlace = new userLocation();

        foodClub.setName("Food Club");
        foodClub.setLatitude(1.33438563);
        foodClub.setLongitude(103.77546608);
        foodClub.setSeaLevel(35);

        munch.setName("Munch");
        munch.setLatitude(1.33174705);
        munch.setLongitude(103.77676159);
        munch.setSeaLevel(34);

        poolSide.setName("Pool Side");
        poolSide.setLatitude(1.33508818);
        poolSide.setLongitude(103.77627343);
        poolSide.setSeaLevel(35);

        makanPlace.setName("Makan Place");
        makanPlace.setLatitude(1.3319637);
        makanPlace.setLongitude(103.7745599);
        makanPlace.setSeaLevel(42);

        locationRef.push().setValue(foodClub);
        locationRef.push().setValue(munch);
        locationRef.push().setValue(poolSide);
        locationRef.push().setValue(makanPlace);
    }
}
