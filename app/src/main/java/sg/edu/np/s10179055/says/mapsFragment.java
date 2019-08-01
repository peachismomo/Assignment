package sg.edu.np.s10179055.says;


import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class mapsFragment extends Fragment {

    Account acc = new Account();
    GoogleLocation currentLocation = new GoogleLocation();
    private Handler mHandler = new Handler();
    private Runnable updateMarker = new Runnable() {
        @Override
        public void run() {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap mMap) {
                    Account currentAccount = new Account();
                    currentLocation.getLoca2(getActivity(), getContext(), currentAccount, mMap);
                    mHandler.postDelayed(updateMarker, 10000);
                    //Design
                    try {
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        mapsFragment.this.getContext(), R.raw.mapstyle));

                        if (!success) {
                            Log.e("MapsActivity", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("MapsActivity", "Can't find style. Error: ", e);
                    }
                    //setting markers of user
                    acc.locationArray(mMap);
                }
            });
        }
    };

    public mapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View RootView = inflater.inflate(R.layout.fragment_maps, container, false);

        updateMarker.run();

        return RootView;
    }
}
