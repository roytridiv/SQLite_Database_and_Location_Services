package com.example.database_practice_sqllite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SetLocation extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap m;
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private GeoDataClient placeDetectionClient;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private GoogleApiClient googleApiClient;


    private final LatLng defaultLocation = new LatLng(23.7449105, 90.3983921);

    private LatLng current;
    private boolean locationGrante;

    private Location lastknownlocation;
    private String s = "";

    private String name ;

    Button b;
    TextView tx, tx2;
    private boolean l = true;

    private static final String t = "Set Location Activity";
    private static final int REQUEST_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        name = getIntent().getStringExtra("userName");





        //Retrieve location
        if (savedInstanceState != null)
            lastknownlocation = savedInstanceState.getParcelable(KEY_LOCATION);


        setContentView(R.layout.activity_set_location);


        placeDetectionClient = Places.getGeoDataClient(SetLocation.this, null);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        LayoutInflater layoutInflater = LayoutInflater.from(SetLocation.this);
        View promtView = layoutInflater.inflate(R.layout.currentlocation, null);
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(SetLocation.this);
        alertdialog.setView(promtView);

        final EditText edit = promtView.findViewById(R.id.editText);
        alertdialog.setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //getLocationPermission();
                        if (ok()) {
                            init();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertdialog.create();
        alertdialog.show();


    }

   // public boolean tryonce = false ;

    private void init() {

        Log.d("MY_APP_DEBUG", "button pressed");

        if (ActivityCompat.checkSelfPermission(SetLocation.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(SetLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        !=
                        PackageManager.PERMISSION_GRANTED) {

            Log.d("MY_APP_DEBUG", "permission");
            requestPermission();
           // tryonce = true;
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(SetLocation.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("MY_APP_DEBUG", "location found");
                    if (l == true) {
                       // Toast.makeText(SetLocation.this, "Press one more time to get LOCATION NAME", Toast.LENGTH_LONG).show();
                        l = false;
                    }

                    current = new LatLng(location.getLatitude(), location.getLongitude());

                    String lat = "" + location.getLatitude();
                    String lng = "" + location.getLongitude();
                   // tx.setText(lat + " " + lng);

                    Log.d("MY_APP", "lat lng : " + lat + " " + " " + lng);

                    SQL sql = new SQL(SetLocation.this);
                    SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();

                   int ok =  sql.updateValue(lat , lng , name);
                    Log.d("MY_APP", "loaction values set : "+ ok);

                   if(ok > 0){
                       Intent intent = new Intent(SetLocation.this , MainActivity.class);
                       startActivity(intent);
                       finish();
                   }

                }
            }
        });
        }


        Log.d("MY_APP_DEBUG", "kisu ekta ::;':::");




    }


    public boolean ok() {

        boolean res = false;
        Log.d("MY_APP_DEBUG", "okok");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SetLocation.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d("MY_APP_DEBUG", "its working");
            res = true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            System.out.println("n");
            Log.d("MY_APP_DEBUG", "error occured");
            // Dialog d = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available , Error);
            res = false;

        } else {
            Toast.makeText(SetLocation.this, "Can not make a map", Toast.LENGTH_SHORT).show();
        }
        return res;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(SetLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }


//    private  String cityName(LatLng mayCoordinates) {
//
//        String n = "";
//        Geocoder geo = new Geocoder(SetLocation.this, Locale.getDefault());
//        try {
//
//            List<Address> addresses = geo.getFromLocation(mayCoordinates.latitude, mayCoordinates.longitude, 1);
//            String a = addresses.get(0).getAddressLine(0);
//            n = addresses.get(0).getLocality();
//            n= n + "\n" + addresses.get(0).getFeatureName()+ ","
//                    + addresses.get(0).getSubLocality() + ","
//                    + addresses.get(0).getPremises() + ","
//                    + addresses.get(0).getAdminArea() + ","
//                    + addresses.get(0).getCountryName() + ","
//                    + addresses.get(0).getCountryCode() + ","
//                    + addresses.get(0).getPostalCode();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return n;
//    }
//
//    /**
//     * Prompts the user for permission to use the device location.
//     */
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    /**
//     * Handles the result of the request for location permissions.
//     */
//    @Override


    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(SetLocation.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("MY_APP_DEBUG", "location found");
                            if (l == true) {
                                //Toast.makeText(SetLocation.this, "Press one more time to get LOCATION NAME", Toast.LENGTH_LONG).show();
                                l = false;
                            }

                            current = new LatLng(location.getLatitude(), location.getLongitude());

                            String lat = "" + location.getLatitude();
                            String lng = "" + location.getLongitude();
                            tx.setText(lat + " " + lng);

                            Log.d("MY_APP", "lat lng : " + lat + " " + " " + lng);
                        }
                    }
                });
            }
        }
    }


}

