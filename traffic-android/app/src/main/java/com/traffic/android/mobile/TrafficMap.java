package com.traffic.android.mobile;

import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrafficMap implements OnMapReadyCallback {

    private AppCompatActivity activity;
    private GoogleMap mMap;

    public TrafficMap(AppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onFilterChange() {
        mMap.clear();
        Switch smallSwitch = activity.findViewById(R.id.filter_small);
        if(smallSwitch.isChecked()){
            mMap.addMarker(new MarkerOptions() .position(new LatLng(34.1, 13.1)).title("Marker small"));
        }
        LatLng sydney = new LatLng(34, 13);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in xyz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
