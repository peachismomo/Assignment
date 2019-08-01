package sg.edu.np.s10179055.says;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleLocation {
    private double Lat;
    private double Long;
    private Location Loca;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public GoogleLocation() {
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    //getting the last known location of the user
    public void getLoca2(Activity a, final Context c, final Account acc, GoogleMap mMap) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(a);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(a, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Loca = task.getResult();
                        if (task != null) {
                            setLat(Loca.getLatitude());
                            setLong(Loca.getLongitude());
                            acc.fireBaseLocation(c, Loca.getLongitude(), Loca.getLatitude());//updating the firebase of the user location
                        } else
                            Toast.makeText(c, "Unable to get location.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mMap.setMyLocationEnabled(true);
        }
    }

    public void setCurrent(GoogleMap map, Context c) {
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else map.setMyLocationEnabled(false);
    }
}