package com.calvinbarker.assignment3;

import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        OnMapReadyCallback {

    private TextView textLat, textLong, textAddress;

    private MapFragment mapFragment;
    private GoogleMap map;

    private GoogleApiClient c = null;
    private Geocoder g = null;

    private LatLng brooks, polk, well;
    private Circle brooksCir, polkCir, wellCir;

    private List<LatLng> locs = null;
    private Map<LatLng, Circle> latMat = null;


    private LatLng lastLoc = null;
    private Marker currentMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLat = (TextView) findViewById(R.id.lat);
        textLong = (TextView) findViewById(R.id.lon);
        textAddress = (TextView) findViewById(R.id.address);

        if (c == null) {
            c = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (g == null) {
            g = new Geocoder(this, Locale.getDefault());
        }

        if (lastLoc == null) {
            lastLoc = new LatLng(0,0);
        }

        locs = new ArrayList<>();
        brooks = new LatLng(35.909535, -79.052977);
        polk = new LatLng(35.910839, -79.050545);
        well = new LatLng(35.912045, -79.051246);

        locs.add(brooks);
        locs.add(polk);
        locs.add(well);

        latMat = new HashMap<LatLng, Circle>();

        initMap();
    }

    // Initialize GoogleMaps
    private void initMap(){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void toggle(Circle cir) {
        if (cir.isVisible()) {
            cir.setVisible(false);
        } else {
            cir.setVisible(true);
        }
    }
    // Callback called when Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions()
                .position(brooks)
                .title("Brooks Hall"));
        map.addMarker(new MarkerOptions()
                .position(polk)
                .title("Polk Place"));
        map.addMarker(new MarkerOptions()
                .position(well)
                .title("Old Well"));

        polkCir = map.addCircle(new CircleOptions()
                .center(polk)
                .radius(25)
                .strokeColor(Color.RED)
                .strokeWidth(3)
        );
        polkCir.setVisible(false);

        brooksCir = map.addCircle(new CircleOptions()
                .center(brooks)
                .radius(25)
                .strokeColor(Color.RED)
                .strokeWidth(3)
        );
        brooksCir.setVisible(false);

        wellCir = map.addCircle(new CircleOptions()
                .center(well)
                .radius(25)
                .strokeColor(Color.RED)
                .strokeWidth(3)
        );
        wellCir.setVisible(false);

        currentMarker = map.addMarker(new MarkerOptions()
                .position(lastLoc)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dot3))
        );

        latMat.put(brooks, brooksCir);
        latMat.put(polk, polkCir);
        latMat.put(well, wellCir);
    }

    @Override
    protected void onStart() {
        c.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        c.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest req = new LocationRequest();
        req.setInterval(1000)
                .setFastestInterval(100)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(c, req, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Location: " + location.getLatitude() + ", " + location.getLongitude());
        writeActualLocation(location);

        lastLoc = new LatLng(location.getLatitude(), location.getLongitude()) ;
        currentMarker.setPosition(lastLoc);

        float[] results = new float[3];
        for (LatLng l : locs) {
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), l.latitude, l.longitude, results);
            System.out.println("Circle visibility: " + latMat.get(l).isVisible() + ". Distance: " + results[0]);

            if (latMat.get(l) != null) {
                if (results[0] <= 25) {
                    if (!latMat.get(l).isVisible()) {
                        toggle(latMat.get(l));
                        // TODO: play media
                    }
                    // Do nothing if already is visible

                } else if (latMat.get(l).isVisible()) {
                    toggle(latMat.get(l));
                    // TODO: stop media
                }
            }
        }
    }

    // Write location coordinates on UI
    private void writeActualLocation(Location location) {
        textLat.setText( "Lat: " + location.getLatitude() );
        textLong.setText( "Long: " + location.getLongitude() );

        try {
            textAddress.setText("Address: Searching...");
            List<android.location.Address> la = g.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            android.location.Address ad = la.get(0);

            String adrStr = "";
            adrStr = ad.getAddressLine(0) + ", " + ad.getLocality() + ", " + ad.getAdminArea() + " " + ad.getPostalCode();

            textAddress.setText("Address: " + adrStr);
        } catch (Exception ex) {

        }
    }
}
