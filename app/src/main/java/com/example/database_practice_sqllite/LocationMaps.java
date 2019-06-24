package com.example.database_practice_sqllite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class LocationMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String name , location1, location2;
    private float cor1, cor2;
    Context context ;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);

        name = getIntent().getStringExtra("name");
        location1 = getIntent().getStringExtra("latitude");
        location2 = getIntent().getStringExtra("longitude");

        Log.d("MY_APP" , "locations: "+ location1 +", "+location2 );


        if(location1.equals("null") || location2.equals("null")){


            Intent i = new Intent(LocationMaps.this , SetLocation.class);
            i.putExtra("userName" , name);
            startActivity(i);

        }else{

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            cor1 = Float.parseFloat(location1);
            cor2 = Float.parseFloat(location2);
            Log.d("MY_APP" , "locations: "+ cor1 +", "+cor2 );

            mapFragment.getMapAsync(this);

        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Address> addresses = null;

        Geocoder geocoder = new Geocoder(LocationMaps.this);
        try {
            addresses = geocoder.getFromLocation(cor1, cor2, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("MY_APP", "The size is " +
                addresses.size());

        if (addresses.size() != 0) {
            Address address = addresses.get(0);
            LatLng sydney = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in " + address.getSubLocality() + ", " + address.getCountryName()));
            float zoomLevel = 16.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
            mMap.getMaxZoomLevel();
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                public boolean onMarkerClick(Marker marker) {
                   // Toast.makeText(LocationMaps.this , "marker e tip disi " , Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LocationMaps.this , CounterActivity.class));

                    return true ;
                }
            });
        }else{
        LatLng sydney = new LatLng(cor1 , cor2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            Log.d("MY_APP" , "kono location i paynai :/ ");
        }
//        progressDialog.dismiss();

    }
}
