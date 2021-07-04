package com.example.practicalexam.view;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.practicalexam.R;
import com.example.practicalexam.globals.GlobalString;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String CountryName;
    String Region;
    String CallingCode;
    String Population;
    String Currencies;
    String LngLat;
    String Languages;
    String Border;
    String Flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void Init() {
        ImageView imageBack = findViewById(R.id.imgBack);
        ImageView imageView = findViewById(R.id.imgFlag);
        TextView txtDetails = findViewById(R.id.txtDetails);
        GlobalString gs = new GlobalString();
        Intent intent = getIntent();
        CountryName = intent.getStringExtra(gs.CountryName);
        Region = intent.getStringExtra(gs.Region);
        CallingCode = intent.getStringExtra(gs.CallingCode).replace("[", "").replace("]", "");
        Population = GlobalString.getFormatedAmount(intent.getStringExtra(gs.Population));
        Currencies = intent.getStringExtra(gs.Currencies).replace("[", "").replace("]", "");
        LngLat = intent.getStringExtra(gs.LngLat).replace("[", "").replace("]", "");
        Languages = intent.getStringExtra(gs.Languages).replace("[", "").replace("]", "");
        Border = intent.getStringExtra(gs.Borders).replace("[", "").replace("]", "");
        Flag = intent.getStringExtra(gs.Flag);

        String Details = "Country Name: " + CountryName + "\n" + "Region: " + Region + "\n"
                + "Calling Code: " + CallingCode + "\n" + "Population: " + Population + "\n" + "Currencies: " + Currencies + "\n" + "Languages: " + Languages + "\n"
                + "Borders: " + Border;
        txtDetails.setText(Details);
        SvgLoader.pluck()
                .with(MapsActivity.this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(Flag, imageView);

        imageBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] latLng = LngLat.split(",");
        double lat = Double.parseDouble(GlobalString.nullcheckInt(latLng[0]));
        double longi = Double.parseDouble(GlobalString.nullcheckInt(latLng[1]));
        LatLng pin = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(pin).title(CountryName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));

    }
}