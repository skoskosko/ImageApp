package skosko.imageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class SingleImage extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat = 0.00000000000;
    double lon = 0.00000000000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);



        //ImageContainer
        ImageView viewy = (ImageView) findViewById(R.id.ImageContainer);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String[] parts = location.split("-");
        lat = Double.parseDouble(parts[0]);
        lon = Double.parseDouble(parts[1]);
        //String time = intent.getStringExtra("time");


        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(viewy);

        TextView texty = (TextView) findViewById(R.id.textViewnamu);
        texty.setText(name);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);



        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //AIzaSyDeuIZodSG9yUvHn6dsVu0yx2yfLpztIaU
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng location = new LatLng(lat,lon);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Image Location or Tampere"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));

    }
}
