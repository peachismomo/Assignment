package sg.edu.np.s10179055.says;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    GoogleLocation gl = new GoogleLocation();
    int Cantid;
    LatLng selectedcant;
    String Title;
    double Lat;
    double Long;
    private LocationCallback locationCallback;
    Account acc = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Intent rcvCanteen = getIntent();
        Cantid = rcvCanteen.getIntExtra("canteenid", 0);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        if (mMap != null) {
            gl.getLoca2(this, MapsActivity.this,mMap,acc);
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            mMap.addMarker(new MarkerOptions().position(selectedcant).title(Title));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedcant, 12));
        }
    }
}
