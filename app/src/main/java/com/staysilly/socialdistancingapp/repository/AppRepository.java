package com.staysilly.socialdistancingapp.repository;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.staysilly.socialdistancingapp.models.UserLocation;

import java.util.UUID;


public class AppRepository {

    /*/////////////////////////////////////////////////
    //MEMBERS
    /*/////////////////////////////////////////////////
    private final String TAG = this.getClass().getSimpleName();
    private static final String USER_LOCATION_COLLECTION = "USER_LOCATION_COLLECTION";
    private static final String KEY_USER_UUID = "KEY_USER_UUID";
    public static final String UNKNOWN = "unknown";


    /*/////////////////////////////////////////////////
    //PRIVATE METHODS
    /*/////////////////////////////////////////////////
    public static void saveUserCurrentLocation(Context context){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = database.collection(USER_LOCATION_COLLECTION);

        UserLocation location = new UserLocation("16582374-234723-2354823-75234", 12721.232, 432432.2123, true);

        collectionReference.document()
                .set(location);
    }
    public static String getCurrentUserId(Activity context){
        return context.getPreferences(Context.MODE_PRIVATE).getString(KEY_USER_UUID, UNKNOWN);
    }
    public static void setCurrentUserId(Activity context){
        context.getPreferences(Context.MODE_PRIVATE).edit().putString(KEY_USER_UUID, UUID.randomUUID().toString()).apply();;
    }

}
