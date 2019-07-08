package sg.edu.np.s10179055.says;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        TextView tvuserC = findViewById(R.id.User_Edit);
        TextView tvPassC = findViewById(R.id.pass_edit);

        String userNameInput = tvuserC.getText().toString();
        String passInput = tvPassC.getText().toString();

        Pattern patternUser = Pattern.compile("^[A-Za-z]{6,12}$");
        Pattern patternPass = Pattern.compile("^+[$&+,:;=?@#|]+[A-Z]+[0-9]$");

        Matcher matcherUser = patternUser.matcher(userNameInput);
        Matcher matcherPass = patternPass.matcher(passInput);

        if (matcherPass.matches() || matcherUser.matches()) {
            db.findAccount(userNameInput,passInput);
        }


    }

}
