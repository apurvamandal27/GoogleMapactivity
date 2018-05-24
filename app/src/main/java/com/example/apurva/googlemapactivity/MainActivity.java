package com.example.apurva.googlemapactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button locate,save;
    double latitute,longitute;
    EditText e1,e2;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locate=findViewById(R.id.locate);
        save=findViewById(R.id.save);
        e1=findViewById(R.id.latitute);
        e2=findViewById(R.id.longitute);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            //open a dialog to take permission
            ActivityCompat.requestPermissions(MainActivity.this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }

        lm= (LocationManager) getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitute=location.getLatitude();
                longitute=location.getLongitude();
                e1.setText(""+latitute);
                e2.setText(""+longitute);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String label="user Location";
                String uriBegin="geo:" + latitute + "," + longitute;
                String query=latitute + "," + longitute + "(" +label + ")";
                String encodedQuery= Uri.encode(query);

                String uriString=uriBegin + "?q=" + encodedQuery + "&Z=16";

                Uri uri=Uri.parse(uriString);
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(mapIntent);
            }
        });

    }
}
