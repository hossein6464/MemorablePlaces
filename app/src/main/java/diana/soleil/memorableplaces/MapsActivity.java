package diana.soleil.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    List<Address> listAddresses;
    String addresses;
    Geocoder geocoder2;
    List<Address> listAddresses2;
    String addresses2;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses ="";
                mMap.clear();
                try {
                    listAddresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    Log.i("ListAddresses",listAddresses.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (listAddresses.size() > 0 && listAddresses.get(0) != null ) {

                    if (listAddresses.get(0).getFeatureName() != null) {
                        addresses += listAddresses.get(0).getFeatureName() + " - ";
                    }
                    if (listAddresses.get(0).getThoroughfare() != null) {
                        addresses += listAddresses.get(0).getThoroughfare() + " ";
                    }
                    if (listAddresses.get(0).getLocality() != null) {
                        addresses += listAddresses.get(0).getLocality() + " ";
                    }
                    if (listAddresses.get(0).getPostalCode() != null) {
                        addresses += listAddresses.get(0).getPostalCode();
                    }
                }
                Log.i("Addresses:", addresses);
                mMap.addMarker(new MarkerOptions().position(userLocation).title(addresses));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
         else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }


        // Add a marker in Sydney and move the camera

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                geocoder2 = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses2 ="";
                try {
                    listAddresses2 = geocoder2.getFromLocation(latLng.latitude,latLng.longitude,1);
                    Log.i("ListAddresses",listAddresses2.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (listAddresses2.size() > 0 && listAddresses2.get(0) != null ) {

                    if (listAddresses2.get(0).getFeatureName() != null) {
                        addresses2 += listAddresses2.get(0).getFeatureName() + " - ";
                    }
                    if (listAddresses2.get(0).getThoroughfare() != null) {
                        addresses2 += listAddresses2.get(0).getThoroughfare() + " ";
                    }
                    if (listAddresses2.get(0).getLocality() != null) {
                        addresses2 += listAddresses2.get(0).getLocality() + " ";
                    }
                    if (listAddresses2.get(0).getPostalCode() != null) {
                        addresses2 += listAddresses2.get(0).getPostalCode();
                    }
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("addresses",addresses2);
                startActivity(intent);
                finish();
                Log.i("Addresses2:", addresses2);
                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses2));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                Toast.makeText(MapsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}