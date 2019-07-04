package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View v){
        Intent myIntent = new Intent(getBaseContext(), login.class);
        startActivity(myIntent);
    }

    public void onGuest(View v){
        Intent myIntent = new Intent(getBaseContext(), guest.class);
        startActivity(myIntent);
    }

}