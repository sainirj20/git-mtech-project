package com.traffic.android.mobile;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.traffic.android.util.WebServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrafficMap implements OnMapReadyCallback {

    private AppCompatActivity activity;
    private GoogleMap mMap;
    private LatLng mapCenter;
    private Integer zoom = 5;
    private String city = "city name from db";
    private String oldFilter = "false-false-false";

    private List<Map<String, Object>> smallCongestions;
    private List<Map<String, Object>> largeCongestions;
    private List<Map<String, Object>> unusualCongestions;

    public TrafficMap(AppCompatActivity activity) {
        this.activity = activity;
        try {
            WebServiceClient client = new WebServiceClient();
            Map<String, Object> response = client.getResponse();
            mapCenter = new LatLng((Double) response.get("latitude"), (Double) response.get("longitude"));
            city = (String) response.get("city");
            zoom = (Integer) response.get("zoom");
            zoom += 2;
            smallCongestions = (List<Map<String, Object>>) response.get("small");
            largeCongestions = (List<Map<String, Object>>) response.get("large");
            unusualCongestions = (List<Map<String, Object>>) response.get("unusual");

        } catch (Exception e) {
            mapCenter = new LatLng(0, 0);
            smallCongestions = new ArrayList<>();
            largeCongestions = new ArrayList<>();
            unusualCongestions = new ArrayList<>();
            Log.d("webservice response", "TrafficMap: Error" + e);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = (float) zoom;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, zoomLevel));
        onFilterChange(false);
    }

    public void onFilterChange(boolean forceChange) {
        Switch smallSwitch = activity.findViewById(R.id.filter_small);
        Switch largeSwitch = activity.findViewById(R.id.filter_large);
        Switch unsualSwitch = activity.findViewById(R.id.filter_unusual);

        TextView cityName = activity.findViewById(R.id.nav_text_city_name);
        cityName.setText(city);

        String currentFilter = smallSwitch.isChecked() + "-" + largeSwitch.isChecked() + "-" + unsualSwitch.isChecked();
        if (!forceChange && oldFilter.equals(currentFilter)) {
            return;
        } else {
            oldFilter = currentFilter;
            mMap.clear();
        }
        if (smallSwitch.isChecked()) {
            for (Map<String, Object> detail : smallCongestions) {
                addCircle(detail, 40, Color.parseColor("#FF3399"));
            }
        }

        if (largeSwitch.isChecked()) {
            for (Map<String, Object> detail : largeCongestions) {
                addCircle(detail, 50, Color.parseColor("#0000FF"));
            }
        }

        if (unsualSwitch.isChecked()) {
            for (Map<String, Object> detail : unusualCongestions) {
                addCircle(detail, 60, Color.parseColor("#FF0000"));
            }
        }
    }

    private void addCircle(Map<String, Object> detail, int radius, int color) {
        LatLng point = new LatLng((Double) detail.get("Latitude"), (Double) detail.get("Longitude"));
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.strokeWidth(2);
        circleOptions.fillColor(color);
        mMap.addCircle(circleOptions);
    }
}
