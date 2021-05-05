package com.staysilly.socialdistancingapp.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.staysilly.socialdistancingapp.models.UserLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


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
    public static void saveUserCurrentLocation(UserLocation userLocation){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = database.collection(USER_LOCATION_COLLECTION);
        collectionReference.document()
                .set(userLocation);
    }
    public static LiveData<List<UserLocation>> getAllUsersLocation(){
        final MutableLiveData<List<UserLocation>> retVal = new MutableLiveData<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = database.collection(USER_LOCATION_COLLECTION);
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<UserLocation> locations = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                UserLocation location = documentSnapshot.toObject(UserLocation.class);
                                locations.add(location);
                            }
                            retVal.postValue(locations);
                        }
                    }
                });

        return retVal;
    }
    public static String getCurrentUserId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY_USER_UUID, UNKNOWN);
    }
    public static void setCurrentUserId(Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(KEY_USER_UUID, UUID.randomUUID().toString()).apply();
    }

}
