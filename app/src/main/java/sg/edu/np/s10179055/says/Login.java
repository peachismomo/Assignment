package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    Dbhandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new Dbhandler(this,null,null,1);
    }

    public void onLoginClick(View v) {
        EditText tvUserC = findViewById(R.id.User_Edit);
        EditText tvPassC = findViewById(R.id.pass_edit);

        String userNameInput = tvUserC.getText().toString();
        String passInput = tvPassC.getText().toString();

        loginDetails lgd= new loginDetails(userNameInput,passInput);


        if (lgd.regexPassword() && lgd.regexUsername()) {
            if(db.findAccount(userNameInput,passInput)){
                Intent mainPage = new Intent(getBaseContext(), student.class);
                startActivity(mainPage);
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT);
                tvPassC.getText().clear();
                tvUserC.getText().clear();
            }
        }
    }

}
