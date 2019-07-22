package sg.edu.np.s10179055.says;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class foodPlacesFragment extends Fragment {
    public static int currentMKP;
    public static int currentTwentyTwo;
    public static int currentMunch;
    public static int currentPoolside;
    ArrayList<Location> data;

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
        fetchData process = new fetchData();
        process.execute();
        return RootView;
    }

    public void onClick(View view){
        data = new ArrayList<>();
        for (int x=1; x<=4; x++){
            if (x==1){
                Location l = new Location();
                l.setLocation("Makanplace");
                l.setCurrent(currentMKP);
                data.add(l);
            }
            else if (x==2){
                Location l = new Location();
                l.setLocation("Foodclub");
                l.setCurrent(currentTwentyTwo);
                data.add(l);
            }
            else if (x==3){
                Location l = new Location();
                l.setLocation("Munch");
                l.setCurrent(currentMunch);
                data.add(l);
            }
            else {
                Location l = new Location();
                l.setLocation("Poolside");
                l.setCurrent(currentPoolside);
                data.add(l);
            }
        }
        final LocationAdapter adapter = new LocationAdapter(foodPlacesFragment.this,R.layout.layout_location.data);
        ListView lvLocation = findViewById(R.id.listViewLocation);
        lvLocation.setAdapter(adapter);
    }

}
