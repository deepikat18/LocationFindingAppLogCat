package com.example.googlemap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemap.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng IdPlatLng = new LatLng(13.628756,79.419182);
        MarkerOptions markerOptions = new MarkerOptions().position(IdPlatLng).title("Tirupati");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(IdPlatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(IdPlatLng,16f));
        Log.d("Map","Yes");
    //Circle
        mMap.addCircle(new CircleOptions()
                .center(IdPlatLng)
                .radius(1000)
                .fillColor(Color.GREEN)
                .strokeColor(Color.DKGRAY));
    // Polygon Overlay
        mMap.addPolygon(new PolygonOptions().add(new LatLng(13.628756,79.419182),
                new LatLng(13.628756,80.419182),
                new LatLng(14.628756,80.419182),
                new LatLng(14.628756,79.419182),
                new LatLng(13.628756,79.419182)).fillColor(Color.YELLOW).strokeColor(Color.BLUE));
        //Image Overlay

        mMap.addGroundOverlay(new GroundOverlayOptions()
                .position(IdPlatLng,1000f,1000f)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.google_android_11_egj3))
                .clickable(true));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Clicked home"));

                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    ArrayList< Address > arrAdr =(ArrayList<Address>) geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    Log.d("Add", arrAdr.get(0).getAddressLine(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}

