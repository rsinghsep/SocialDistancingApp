package com.staysilly.socialdistancingapp.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.staysilly.socialdistancingapp.R;
import com.staysilly.socialdistancingapp.models.UserLocation;
import com.staysilly.socialdistancingapp.repository.AppRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    /*/////////////////////////////////////////////////
    //MEMBERS
    /*/////////////////////////////////////////////////
    private final String TAG = "**" + this.getClass().getSimpleName();
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;


    /*/////////////////////////////////////////////////
    //LIFECYCLE METHODS
    /*/////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    /*/////////////////////////////////////////////////
    //PRIVATE METHODS
    /*/////////////////////////////////////////////////
    private void moveMapToLastKnownLocation(final GoogleMap map) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "provide location permission", Toast.LENGTH_LONG).show();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d(TAG, "found last known location");
                if (map!=null){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    CameraPosition myPosition = new CameraPosition.Builder().target(latLng).zoom(5).bearing(0).tilt(30).build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

                    String id = AppRepository.getCurrentUserId(HomeActivity.this);

                    UserLocation userLocation = new UserLocation(
                            AppRepository.getCurrentUserId(HomeActivity.this),
                            latitude,
                            longitude,
                            true);
                    AppRepository.saveUserCurrentLocation(userLocation);
                }
            }
        });
    }
    private void observeAllUsersLocation(final GoogleMap map){
        AppRepository.getAllUsersLocation().observe(this, new Observer<List<UserLocation>>() {
            @Override
            public void onChanged(List<UserLocation> userLocations) {
                if (userLocations!=null && !userLocations.isEmpty()){
                    for (UserLocation location : userLocations){
                        if (location!=null){
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            showUserOnMap(map, latLng);
                        }
                    }
                }
            }
        });
    }
    private void showUserOnMap(GoogleMap map, LatLng latLng){
        if (map==null||latLng==null){
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
    }


    /*/////////////////////////////////////////////////
    //Interface overrides
    /*/////////////////////////////////////////////////
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        Log.d(TAG, "map is ready");
        googleMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "ACCESS_FINE_LOCATION permission needed");
            Toast.makeText(this, "provide location permission", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d(TAG, "permission is granted");
        googleMap.setMyLocationEnabled(true);
        moveMapToLastKnownLocation(googleMap);
        observeAllUsersLocation(googleMap);
    }

}