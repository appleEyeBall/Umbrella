package com.example.olase.umbrella.HelperClasses;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.olase.umbrella.Activities.MainActivity;
import com.example.olase.umbrella.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PermissionClass {

    Context context;
    Switch remindSwitch;


    public PermissionClass(Context context, Switch remindSwitch) {
        this.context = context;
        this.remindSwitch = remindSwitch;
    }

    private Dialog setupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("We need your location");
        builder.setMessage("Your location will help us get localized weather info");
        builder.setPositiveButton("Got it! ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkAndRequestPermission();
            }
        });

        builder.setNegativeButton("Not interested", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                remindSwitch.setChecked(false);

            }
        });
        return  builder.create();
    }

    public void showDialog (){
        setupDialog().show();
    }

    private void checkAndRequestPermission(){
        MainActivity activity = (MainActivity) context;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission for FINE location
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, activity.PERMISSION_LOCATION_REQUEST);
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, activity.PERMISSION_INTERNET_REQUEST);
        }

    }
}
