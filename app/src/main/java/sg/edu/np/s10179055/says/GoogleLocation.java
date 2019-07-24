package sg.edu.np.s10179055.says;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GoogleLocation {
    private double Lat;
    private double Long;
    private Location Loca;

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

    public Location getLoca(Context c, Activity a) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(c);
        int MY_PERMISSION_ACCESS_FINE_LOCATION = 1;

        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        else{
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Loca=location;
                        setLat(Loca.getLatitude());
                        setLong(Loca.getLongitude());
                    }
                    else{

                    }
                }
            });
            fusedLocationClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return Loca;
    }


}
