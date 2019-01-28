package com.example.olase.umbrella.Activities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.olase.umbrella.Fragments.MainFragment;
import com.example.olase.umbrella.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends FragmentActivity{
    public final int PERMISSION_LOCATION_REQUEST = 111;
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = new MainFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_LOCATION_REQUEST:
                if (grantResults.length>0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                        if (currentFragment instanceof MainFragment){
                            MainFragment mainFragment = (MainFragment) currentFragment;
                            mainFragment.getLocation();
                        }
                    }
                }
                else {
                    finish();
                }
        }
    }
}
