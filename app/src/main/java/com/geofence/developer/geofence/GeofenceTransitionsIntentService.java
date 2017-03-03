package com.geofence.developer.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by Developer on 2/3/2017.
 */

public class GeofenceTransitionsIntentService extends IntentService{
    protected static String name=GeofenceTransitionsIntentService.class.getSimpleName();

    public GeofenceTransitionsIntentService(){
        super(name);//Porque no hay un Constructor por default En IntentService
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent=GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError()){
            String error=GeofenceErrorMessages.getErrorStr(this,geofencingEvent.getErrorCode());
            Log.e(name,error);
            return;
        }
        geofencingEvent.getGeofenceTransition();


    }
}
