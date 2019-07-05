package sg.edu.np.s10179055.says;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class reportingFragment extends Fragment {

    private String address = "madladReports@gmail.com";
    private EditText subject;
    private EditText msg;

    public reportingFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_reporting, container, false);

        subject = RootView.findViewById(R.id.subjectTxt);
        msg = RootView.findViewById(R.id.messageTxt);

        Button buttonSend = RootView.findViewById(R.id.sendBtn);
        buttonSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        return RootView;
    }

    //Send report via E-mail.
    private void sendMail(){
        String subj = subject.getText().toString();
        String message = msg.getText().toString();
        String[] recipient = address.split(",");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subj);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        //Only open email clients.
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, "Choose an Email Client."));
        subject.getText().clear();
        msg.getText().clear();
    }
}
