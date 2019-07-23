package sg.edu.np.s10179055.says;


import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private EditText n;
    private DatabaseReference r;
    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View RootView = inflater.inflate(R.layout.fragment_profile, container, false);

            n = RootView.findViewById(R.id.etName);
            r = FirebaseDatabase.getInstance().getReference().child("Member");

            r.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    String name = dataSnapshot.child("name").getValue().toString();
                    n.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
            return RootView;
    }



}
