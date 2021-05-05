package com.staysilly.socialdistancingapp.repository;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.staysilly.socialdistancingapp.models.Location;

public class AppRepository {

    /*/////////////////////////////////////////////////
    //MEMBERS
    /*/////////////////////////////////////////////////
    private final String TAG = this.getClass().getSimpleName();
    private static final String USER_LOCATION_COLLECTION = "USER_LOCATION_COLLECTION";


    /*/////////////////////////////////////////////////
    //PRIVATE METHODS
    /*/////////////////////////////////////////////////
    public static void setValue(Context context){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = database.collection(USER_LOCATION_COLLECTION);

        Location location = new Location("16582374-234723-2354823-75234", 12721.232, 432432.2123, true);

        collectionReference.document()
                .set(location);
    }

}
