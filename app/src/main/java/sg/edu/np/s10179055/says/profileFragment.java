package sg.edu.np.s10179055.says;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private TextView user;
    private TextView name;
    private TextView sNo;
    private TextView nric;
    private TextView course;
    private TextView email;



    private DatabaseReference r;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View RootView = inflater.inflate(R.layout.fragment_profile, container, false);

        user = RootView.findViewById(R.id.tvUser);
        name = RootView.findViewById(R.id.tvName);
        sNo = RootView.findViewById(R.id.tvStudentID);
        nric = RootView.findViewById(R.id.tvNRIC);
        course = RootView.findViewById(R.id.tvCourse);
        email = RootView.findViewById(R.id.tvEmail);


        r = FirebaseDatabase.getInstance().getReference().child("Member");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences currentUser = RootView.getContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                String currentUsername = currentUser.getString("username", "");

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    if (CurrentUser.getUsername().equals(currentUsername)) {
                        user.setText(CurrentUser.getUsername());
                        name.setText(CurrentUser.getName());
                        sNo.setText(CurrentUser.getStudentNo());
                        nric.setText(CurrentUser.getNRIC());
                        course.setText(CurrentUser.getCourse());
                        email.setText(CurrentUser.getEmail());


                        //do the rest here
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Button changePassword = RootView.findViewById(R.id.btnChangePass);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RootView.getContext(), sg.edu.np.s10179055.says.changePassword.class);
                startActivity(i);
            }
        });
        return RootView;
    }


}
