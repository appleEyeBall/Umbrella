package com.example.olase.umbrella.HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.olase.umbrella.Activities.MainActivity;

import org.json.JSONObject;

import java.lang.reflect.Method;

public class GetWeather{
    RequestQueue requestQueue;
    JsonObjectRequest objectRequest;
    Context context;
    private static final GetWeather ourInstance = new GetWeather();

    public static GetWeather getInstance() {
        return ourInstance;
    }

    private GetWeather() {


    }

    public void getWeatherData(Activity activity, String url, LocationManager locationManager, LocationListener locationListener) {
        requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Oluwatise", "response is "+ response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Oluwatise", "error is ");
                        error.printStackTrace();
                    }
                });

        requestQueue.add(objectRequest);
        locationManager.removeUpdates(locationListener);
        Log.e("Oluwatise", "Location canceled");
    }
}
