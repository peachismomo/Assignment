package sg.edu.np.s10179055.says;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleLocation gl = new GoogleLocation();
    int Cantid;
    LatLng selectedcant;
    String Title;
    Account acc = new Account();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Intent rcvCanteen = getIntent();
        Cantid = rcvCanteen.getIntExtra("canteenid", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Cantid == -0) {//makanplace
            selectedcant = new LatLng(1.3319637, 103.7745599);
            Title = "Makanplace";
        } else if (Cantid == 1) {//fc
            selectedcant = new LatLng(1.33438563, 103.77546608);
            Title = "FoodClub";
        } else if (Cantid == 2) {//munch
            selectedcant = new LatLng(1.33174705, 103.77676159);
            Title = "Munch";
        } else if (Cantid == 3) {//ps
            selectedcant = new LatLng(1.33508818, 103.77627343);
            Title = "PoolSide";
        }

        //Design
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        //Check if the map is loaded
        if (mMap != null) {
            gl.getLoca2(MapsActivity.this, this, acc, mMap);
            gl.setCurrent(mMap, this);
            //adding markers of canteen selected
            mMap.addMarker(new MarkerOptions().position(selectedcant).title(Title));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedcant, 12));
        }
    }
}
