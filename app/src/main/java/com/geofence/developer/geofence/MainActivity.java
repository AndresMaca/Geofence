package com.geofence.developer.geofence;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,OnConnectionFailedListener,ResultCallback<Status>{
    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> mGeofenceList;
    private Button mAddGeoFencesButton;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        mGeofenceList = new ArrayList<Geofence>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        populateGeofenceList();
        buildGoogleApiClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Override Android Methods
     */


    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnecting()||mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mGoogleApiClient.isConnecting()||!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
    }
    //2.449484, -76.594959

    /*
    Override methods Google
     */

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {
        if(status.isSuccess()){
            Toast.makeText(this, "Geofences Added", Toast.LENGTH_SHORT).show();

        }else{
            String errorMessage=GeofenceErrorMessages.getErrorStr(this,status.getStatusCode());
            Log.e("","error");
        }

    }

    /*
    My methods
     */

    public void populateGeofenceList(){
        for(Map.Entry<String,LatLng>entry:Constants.POPAYAN_LANDMARKS.entrySet()){
            mGeofenceList.add(new Geofence.Builder().setRequestId(entry.getKey())
            .setCircularRegion(entry.getValue().latitude,entry.getValue().longitude,Constants.GEOFENCE_RADIUS_IN_METERS)
            .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT)
            .build());
        }

    }
    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient=new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this).
                addConnectionCallbacks(this).
                addApi(LocationServices.API).
                build();


    }
    private PendingIntent getGeofencePendingIntent(){
        Intent intent=new Intent (this,GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void addGeofencesButton(View view){//Geofences button Handler
        if(!mGoogleApiClient.isConnected()){
            Toast.makeText(this, getString(R.string.not_conected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, getGeofencingRequest(), getGeofencePendingIntent()).setResultCallback(this);
        }catch (SecurityException sE){
            Log.i("Error",sE.toString());
        }
    }
    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder=new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }



}
