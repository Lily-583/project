package com.csci310.uscdoordrink;
import static androidx.test.InstrumentationRegistry.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Implement OnMapReadyCallback.

public class MapsActivity extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        TaskLoadedCallback{

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private AutoCompleteTextView mSearchText;
    private GoogleMap map;
    Location currentLocation;
    MarkerOptions place1, place2;
    //Button getDirection;
    String driving_duration = null;
    int drivingint = 60;
    String RecommendMerchant=null;
    String customerName="Den";
    private Polyline currentPolyline;
    List<MarkerOptions>markerOptionList=new ArrayList<MarkerOptions>();
    List<Marker>markerList=new ArrayList<Marker>();
    int isMerchant;
    String userID;
    //permission
    private static final String TAG = "MapActivity";
    List<HashMap<String,String>> allResult=new ArrayList<HashMap<String,String>>();
    List<HashMap<String,String>> GeoResult=new ArrayList<HashMap<String,String>>();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void init(){
        String[]autoOption= new String[allResult.size()];
        Log.d(TAG, "init: initializing");
        for(int i=0;i<allResult.size();i++)
        {
            String name=allResult.get(i).get("username");
            autoOption[i]=name;
        }
        mSearchText.setAdapter(new ArrayAdapter<>(MapsActivity.this, android.R.layout.simple_list_item_1,autoOption));
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager inputManager = (InputMethodManager)MapsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(MapsActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

    }

    private void geoLocate(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler;
        handler = new Handler(Looper.getMainLooper());
        String searchString = mSearchText.getText().toString();
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    insertDaniel in=new insertDaniel();
                    GeoResult =in.doInBackground();
                } catch (Exception e) {
                    System.out.println("Daniel2");
                }
                //Background work here

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        if(GeoResult!=null&&GeoResult.size()>0) {
                            for (int i = 0; i < GeoResult.size(); i++) {
                                String name = GeoResult.get(i).get("username");

                                String latstring = GeoResult.get(i).get("latitude");
                                float lat=0.0f;
                                if(latstring!=null)
                                {
                                    lat = Float.parseFloat(latstring);
                                }


                                String lonstring = GeoResult.get(i).get("lon");
                                float lon=0.0f;
                                if(lonstring!=null)
                                {
                                    lon = Float.parseFloat(lonstring);
                                }


                                if (searchString!=null&&searchString.equals(name)) {
                                    moveCamera(new LatLng(lat, lon), DEFAULT_ZOOM);
                                    if (markerList.size() > 0) {
                                        OpenSpecifiedMarker(searchString);
                                    }
                                }

                            }
                        }
                    }
                });
            }
        });
    }

    public void OpenSpecifiedMarker(String searchString)
    {
        if(markerList.size()>0)
        {
            for(int i=0;i<markerList.size();i++)
            {
                if(searchString!=null&&markerList.get(i).getTitle()!=null&&markerList.get(i).getTitle().equals(searchString))
                {
                    markerClickContent(markerList.get(i));
                }
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mSearchText=(AutoCompleteTextView) findViewById(R.id.input_search);
        initMap();
        getLocationPermission();

        //--------------
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    RecommendationQuery r=new RecommendationQuery();
                    RecommendMerchant=r.getMerchant(customerName);
                } catch (Exception e) {
                    System.out.println("Recommendation");
                }
                //Background work here

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("RecommendMerchant"+RecommendMerchant);
                        if(RecommendMerchant!=null)
                        {
                            TextView thetext = (TextView)findViewById(R.id.recommendation);
                            //System.out.println ("HBO");
                            thetext.setText("Daily Recommendation: "+RecommendMerchant);
                            //below is toast
                            String text="Daily Recommendation: "+RecommendMerchant;
                            Context context = getApplicationContext();
                            Toast toast=Toast.makeText(context, text, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        });



        //buttons----------------------------------------------------------
        Button btn_log_out= findViewById(R.id.button_map);
        Button btn_show_orderHis= findViewById(R.id.button_order_history);
        Button btn_show_profile= findViewById(R.id.button_profile);

        //receive info from intent----------------------------------
        userID=intent.getExtras().getString("userID");
        isMerchant=intent.getExtras().getInt("isMerchant");
        customerName=userID;

        if(isMerchant==1){
            btn_show_profile.setEnabled(false);
        }
        //--------------------------------------------
        btn_log_out.setOnClickListener(v->{
            finish();
            System.exit(0);
        });

        btn_show_orderHis.setOnClickListener(v->{
            Intent ID = new Intent(MapsActivity.this, OrderActivity.class);
            ID.putExtra("userID", userID);
            ID.putExtra("isMerchant", isMerchant);
            startActivity(ID);
        });

        btn_show_profile.setOnClickListener(v->{
            Intent ID = new Intent(MapsActivity.this, AnalysisActivity.class);
            ID.putExtra("userID", userID);
            startActivity(ID);
        });
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {

            if (mLocationPermissionsGranted) {

                mFusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                currentLocation = location;

                                try {

                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                            DEFAULT_ZOOM);
                                }

                                catch(Exception e)
                                {
                                    LatLng village=new LatLng(34.0202,-118.2837);
                                    moveCamera(village,15f);
                                }
                                getMerchantFromDataBase();
                            } else {
                                Log.d(TAG, "onComplete: current location is null");
                                Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        hideSoftKeyboard();
    }
    // Get a handle to the GoogleMap object and display marker.

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        map.setOnInfoWindowClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);
            map.getUiSettings().setZoomControlsEnabled(true);
        }
    }
    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick( Marker marker) {
        markerClickContent(marker);
        return false;
    }

    public void markerClickContent(Marker marker)
    {
        if(currentLocation!=null) {
            LatLng mylat = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            String url_line = getUrl(marker.getPosition(), mylat, "driving");
            try {
                new FetchURL(MapsActivity.this).execute(url_line, "driving");
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("Location");

            }
            //test
            final String[] driving = new String[1];
            final Object[] walking = new Object[1];

            String url_driving = getUrl(marker.getPosition(), mylat, "driving");
            String url_walking = getUrl(marker.getPosition(), mylat, "walking");
            Thread thread1 = new Thread(() -> {

                driving[0] = getDuration(url_driving);
            });

            Thread thread2 = new Thread(() -> {
                walking[0] = getDuration(url_walking);
            });

            thread1.start();
            thread2.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                System.out.println("Thread1");
            }
            try {
                thread2.join();
            } catch (InterruptedException e) {
                System.out.println("Thread2");
            }
            driving_duration=driving[0];
            System.out.println("passed duration");
            System.out.println(driving_duration);
            if(driving_duration!=null&&driving_duration.length()!=0)
            {
                if(driving_duration.contains("min"))
                {
                    String[]thesplit=driving_duration.split(" ");
                    drivingint=Integer.parseInt(thesplit[0]);
                }
                else
                {
                    drivingint=60;
                }
            }
            else
            {
                drivingint=60;
            }
            if(driving==null||driving[0]==null)
            {
                driving[0]="unknown";
            }
            if(walking==null||walking[0]==null)
            {
                walking[0]="unknown";
            }
            String snippet = "driving: " + driving[0] + "\n" + "walking: " + walking[0];
            marker.setSnippet(snippet);

            map.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
            marker.showInfoWindow();
        }
        else
        {
            Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" +"AIzaSyAPLYER1TPpF9RSypNubp_yz6TzCG5hogk";


        return url;
    }



    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            //Log.d("mylog", "Downloaded URL: " + data.toString());
            br.close();
        } catch (Exception e) {
            //Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            try{
                iStream.close();
                urlConnection.disconnect();
            }
            catch(Exception e)
            {
                System.out.println("Close");
            }

        }
        return data;
    }

    public static String getDuration(String theurl)
    {
        String duration=null;
        String a=null;

        try {

            a=downloadUrl(theurl);
            System.out.println("downloadUrl: "+a);
        } catch (IOException e) {
            System.out.println("DownloadUrl");
        }
        JSONObject jsnobject=null;
        try {
            jsnobject = new JSONObject(a);
        } catch (Exception jsonException) {
            System.out.println("Json2");
        }

        try {
            duration = jsnobject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
        } catch (Exception e) {
            System.out.println("Json");
        }

        return duration;
    }

    public void getMerchantFromDataBase()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler;
        handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    insertDaniel in=new insertDaniel();
                    allResult =in.doInBackground();
                } catch (Exception e) {
                    System.out.println("Daniel");
                }
                //Background work here

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        ConvertToMarker();
                        addMarker();
                        init();
                    }
                });
            }
        });

    }

    public void ConvertToMarker()
    {
        for(int i=0;i<allResult.size();i++)
        {
            String name=allResult.get(i).get("username");
            String latstring=allResult.get(i).get("latitude");
            float lat=0.0f;
            if(latstring!=null)
            {
                lat=Float.parseFloat(latstring);
            }
            String lonstring=allResult.get(i).get("lon");
            float lon=0.0f;
            if(lonstring!=null)
            {
                lon=Float.parseFloat(lonstring);
            }
            MarkerOptions op=new MarkerOptions().position(new LatLng(lat, lon)).title(name).snippet("info");
            markerOptionList.add(op);
        }
    }

    public void addMarker()
    {

        for(int i=0;i<markerOptionList.size();i++)
        {
            System.out.println("markerhere "+markerOptionList.get(i).getTitle());
            Marker m=map.addMarker(markerOptionList.get(i));
            markerList.add(m);
        }
    }


    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

        if(isMerchant!=1){
            Toast.makeText(this, "Info window clicked",
                    Toast.LENGTH_SHORT).show();
            System.out.println(marker.getTitle());
            //send intent here
            //also send over username
            Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
            intent.putExtra("customerID", userID);
            intent.putExtra("merchantID", marker.getTitle());
            intent.putExtra("NeededTime", drivingint);
            startActivity(intent);
        }
        else
        {
            String text="Only Customers Can Make Orders";
            Context context = getApplicationContext();
            Toast toast=Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}