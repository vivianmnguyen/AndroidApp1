package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String LAT_VALUE = "LAT_VALUE";
    private static final String LNG_VALUE = "LNG_VALUE";
    JSONObject RESPONSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getGeocode(View view){
        //Nick driving
        EditText textInput = findViewById(R.id.editText);
        String address = textInput.getText().toString();
        String parsedAddress = address.replaceAll("\\s", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + parsedAddress + "&key=AIzaSyB3ZauVyCg_Bu2FhoQcipksZAjk0zDaIbQ";
        //end of Nick driving, Vivian driving now
        Context mContext = getApplicationContext();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                         RESPONSE = response;
                        try {
                            JSONArray results = RESPONSE.getJSONArray("results");
                            JSONObject resultsObject = results.getJSONObject(0);
                            JSONObject geometry = resultsObject.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");
                            Double latValue = location.getDouble("lat");
                            Double lngValue = location.getDouble("lng");

                            sendCoordinates(latValue, lngValue);

                            int x = 5;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                });
        

        requestQueue.add(jsonObjectRequest);
        //end of Vivian driving, Nick driving now
    }

    public void sendCoordinates(Double latValue, Double lngValue){
        Intent randomIntent = new Intent(this, MapsActivity.class);
        String latValueString = latValue.toString();
        String lngValueString = lngValue.toString();

        randomIntent.putExtra(LAT_VALUE, latValue);
        randomIntent.putExtra(LNG_VALUE, lngValue);

        startActivity(randomIntent);


    }
    //end of Nick driving, Vivian driving now

}
