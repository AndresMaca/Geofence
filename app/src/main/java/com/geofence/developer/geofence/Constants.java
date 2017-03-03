package com.geofence.developer.geofence;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Developer on 2/3/2017.
 */

public final class Constants {
    private Constants(){

    }


    public static final String PACKAGE_NAME = "com.geofence.developer.geofence";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 48;

    /**
     * For this sample, geofences expire after 48 hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    //public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    public static final float GEOFENCE_RADIUS_IN_METERS = 500; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));

        // Test
        BAY_AREA_LANDMARKS.put("Udacity Studio", new LatLng(37.3999497,-122.1084776));
    }
    public static final HashMap<String,LatLng> POPAYAN_LANDMARKS=new HashMap<String, LatLng>();
    static {
        POPAYAN_LANDMARKS.put("Casita",new LatLng(2.449484, -76.594959));
        POPAYAN_LANDMARKS.put("Unicauca",new LatLng(2.446450, -76.598289));

    }

}

