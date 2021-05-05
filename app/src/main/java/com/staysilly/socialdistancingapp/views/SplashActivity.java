package com.staysilly.socialdistancingapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.staysilly.socialdistancingapp.R;
import com.staysilly.socialdistancingapp.repository.AppRepository;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    /*/////////////////////////////////////////////////
    //MEMBERS
    /*/////////////////////////////////////////////////
    private final String TAG = this.getClass().getSimpleName();


    /*/////////////////////////////////////////////////
    //LIFECYCLE METHODS
    /*/////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fakeWait(this);
    }


    /*/////////////////////////////////////////////////
    //PRIVATE METHODS
    /*/////////////////////////////////////////////////
    private void checkCurrentUserAndLaunchHomeScreen(Activity activity){
        String currentUserId = AppRepository.getCurrentUserId(activity);
        if (currentUserId==null || currentUserId.isEmpty() || currentUserId.equals(AppRepository.UNKNOWN)){
            AppRepository.setCurrentUserId(activity);
        }
        startHomeActivity();
    }
    private void startHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private void fakeWait(final Activity activity){
        if (activity==null){
            Log.d(TAG, "null activity");
            return;
        }
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                checkCurrentUserAndLaunchHomeScreen(activity);
            }
        }.start();
    }

}