package sg.edu.np.s10179055.says;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
        final View RootView = inflater.inflate(R.layout.fragment_food_places, container, false);

        //Fetch location data from the APIs
        fetchData process = new fetchData();
        process.execute();

        //Initialize Listview without loading the API data in
        data = new ArrayList<>();
        for (int x = 1; x <= 4; x++) {
            if (x == 1) {
                Location l = new Location();
                l.setLocation("Makanplace");
                l.setCurrent(currentMKP);
                data.add(l);
            } else if (x == 2) {
                Location l = new Location();
                l.setLocation("Foodclub");
                l.setCurrent(currentTwentyTwo);
                data.add(l);
            } else if (x == 3) {
                Location l = new Location();
                l.setLocation("Munch");
                l.setCurrent(currentMunch);
                data.add(l);
            } else {
                Location l = new Location();
                l.setLocation("Poolside");
                l.setCurrent(currentPoolside);
                data.add(l);
            }
        }
        LocationAdapter adapter = new LocationAdapter(RootView.getContext(), R.layout.layout_location, data);
        final ListView list = RootView.findViewById(R.id.listViewLocation);
        list.setAdapter(adapter);

        //Code for button to update the Listview
        Button display = RootView.findViewById(R.id.button);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = new ArrayList<>();
                for (int x = 1; x <= 4; x++) {
                    if (x == 1) {
                        Location l = new Location();
                        l.setLocation("Makanplace");
                        l.setCurrent(currentMKP);
                        data.add(l);
                    } else if (x == 2) {
                        Location l = new Location();
                        l.setLocation("Foodclub");
                        l.setCurrent(currentTwentyTwo);
                        data.add(l);
                    } else if (x == 3) {
                        Location l = new Location();
                        l.setLocation("Munch");
                        l.setCurrent(currentMunch);
                        data.add(l);
                    } else {
                        Location l = new Location();
                        l.setLocation("Poolside");
                        l.setCurrent(currentPoolside);
                        data.add(l);
                    }
                }
                LocationAdapter adapter = new LocationAdapter(RootView.getContext(), R.layout.layout_location, data);
                final ListView list = RootView.findViewById(R.id.listViewLocation);
                list.setAdapter(adapter);

                //Set Listview on Click to navigate to google maps for navigation fucntion
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent maps = new Intent(RootView.getContext(), MapsActivity.class);
                        maps.putExtra("canteenid",position);
                        startActivity(maps);
                    }
                });
            }
        });


        return RootView;
    }
}
