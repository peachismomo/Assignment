package sg.edu.np.s10179055.says;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleLocation {
    private double Lat;
    private double Long;
    private Location Loca;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;

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

    public void getLoca2(Activity a, Context c) {//get location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(a);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(a, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Loca = task.getResult();
                        setLat(Loca.getLatitude());
                        setLong(Loca.getLongitude());
                    }
                }
            });
        }
    }
}
