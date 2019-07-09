package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHandler(this, null, null, 1);
    }

    public void onLoginClick(View v) {
        EditText tvUserC = findViewById(R.id.User_Edit);
        EditText tvPassC = findViewById(R.id.pass_edit);

        String userNameInput = tvUserC.getText().toString();
        String passInput = tvPassC.getText().toString();

        Account lgd = new Account();
        lgd.setUsername(userNameInput);
        lgd.setPassword(passInput);

        if (db.findAccount(userNameInput, passInput)) {
            Intent mainPage = new Intent(getBaseContext(), student.class);
            startActivity(mainPage);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid UsernameET or password", Toast.LENGTH_SHORT).show();
            tvPassC.getText().clear();
            tvUserC.getText().clear();
        }
    }

}
