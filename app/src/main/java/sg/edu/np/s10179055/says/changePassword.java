package sg.edu.np.s10179055.says;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class changePassword extends AppCompatActivity {
    EditText oldPass;
    EditText newPass;
    EditText cfmNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }
}
