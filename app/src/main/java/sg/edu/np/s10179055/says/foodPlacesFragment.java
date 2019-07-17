package sg.edu.np.s10179055.says;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class foodPlacesFragment extends Fragment {
    public static TextView tvDisplay;
    Button btn;

    public foodPlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        /*
        Code your spaghetti here
        */
        tvDisplay = RootView.findViewById(R.id.textViewDisplay);
        btn = RootView.findViewById(R.id.button);

        return RootView;
    }

    public void onClick(View view){
        fetchData process = new fetchData();
        process.execute();
    }

}
