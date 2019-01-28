package com.example.olase.umbrella.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.olase.umbrella.HelperClasses.GetWeather;
import com.example.olase.umbrella.HelperClasses.PermissionClass;
import com.example.olase.umbrella.R;

import java.security.Provider;

import androidx.core.content.ContextCompat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends androidx.fragment.app.Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Switch remindSwitch;
    private double latitude;
    private double longitude;
    LocationManager locationManager;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private final String API_KEY = "de47268df02853d38ab1f69ccc89d632";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        remindSwitch = (Switch) parentView.findViewById(R.id.remind_switch);

        remindSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remindSwitch.isChecked()){
                    getLocation();
                }
            }
        });
        return parentView;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.e("Oluwatise", "Location changed");
            GetWeather getWeather = GetWeather.getInstance();
            String url = BASE_URL + "lat=" + location.getLatitude() +
                    "&lon="+location.getLongitude() +
                    "&APPID="+API_KEY;
            Log.e("Oluwatise", "url is "+url);
            getWeather.getWeatherData(getActivity(), url, locationManager, locationListener);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("Oluwatise", "prov enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("Oluwatise", "prov disabled");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("Oluwatise", "status changed");
        }
    };

    public void getLocation(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Log.e("Oluwatise", "permission is granted. gonna get location now");
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
        }
        else {
            Log.e("Oluwatise", "permission not granted. gonna request permission");
            PermissionClass permissionClass = new PermissionClass(getActivity(), remindSwitch);
            permissionClass.showDialog();
        }
    }



}
