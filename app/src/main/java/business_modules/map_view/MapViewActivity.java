package business_modules.map_view;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bhagyar.oshuttle.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import application.constance.AppConstance;


public class MapViewActivity extends FragmentActivity
        implements
        OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private FloatingActionButton btnUserLocation, btnBusLocation;
    private double latitude, longitude;
    private LatLng currentLocation;
    private LatLng factoryLocation;
    private TelephonyManager telephonyManager;
    private float[] results;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        factoryLocation = new LatLng(AppConstance.factoryLatitude, AppConstance.factoryLogtitude);
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        final String deviceId = telephonyManager.getSubscriberId();
        btnUserLocation = findViewById(R.id.btnUserLocation);
        btnBusLocation = findViewById(R.id.btnBusLocation);
        currentLocation = new LatLng(latitude, longitude);
        btnUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    currentLocation = new LatLng(latitude, longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.2f), 1000, null);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title("This Is You")
                            .snippet("Approximately " + (results[0] / 1000) + " km s Straight Away from the OREL ZONE")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_green_600_24dp)));
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(),"Please Wait For The Connection",Toast.LENGTH_LONG).show();
                    Log.e("Exception Raised", "" + e);
                }

                catch (Exception e){
                    Log.e("Exception Raised", "" + e);
                }

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                currentLocation = new LatLng(latitude, longitude);
                results = new float[1];
                Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                        factoryLocation.latitude, factoryLocation.longitude,
                        results);
                mMap.clear();
                mMap.setTrafficEnabled(true);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(currentLocation)
                        .title("This Is You")
                        .snippet("Approximately " + (results[0] / 1000) + " km s Straight Away from the OREL ZONE")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_green_600_24dp))
                );
                marker.showInfoWindow();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("userCoordinates");
                myRef.child(deviceId).setValue(currentLocation);
                Log.i("Location Updated", "UPDATED AND COMMITTED TO FIRE-BASE");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
                AppConstance.GPS_STATE = true;
                Toast toast = Toast.makeText(getApplicationContext(), "GPS ENABLED", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onProviderDisabled(String s) {
                AppConstance.GPS_STATE = false;
                Toast toast = Toast.makeText(getApplicationContext(), "GPS DISABLED", Toast.LENGTH_SHORT);
                toast.show();
            }

        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.2f));
        mMap.addMarker(new MarkerOptions()
                .position(currentLocation)
        );
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOffline();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseDatabase.getInstance() != null) {
            FirebaseDatabase.getInstance().goOnline();
        }
    }


}
