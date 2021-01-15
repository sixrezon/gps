package com.example.gps;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public String lat, lon;
    TextView tv_lat, tv_lon, tv_acc;
    Button btn_update;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 1337);

        tv_lat = findViewById(R.id.lat);
        tv_lon = findViewById(R.id.lon);
        tv_acc = findViewById(R.id.acc);
        btn_update = findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSClass g = new GPSClass(getApplicationContext());
                Location l = g.getLocation();
                if (l != null) {
                    double x = l.getLatitude();
                    double y = l.getLongitude();

                    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
                    DecimalFormat df = new DecimalFormat("###.000000", otherSymbols);
                    lat = df.format(x);
                    lon = df.format(y);

                    tv_lat.setText(lat);
                    tv_lon.setText(lon);
                    tv_acc.setText(" " + l.getAccuracy());

                    if (l.getAccuracy() < 5) {
                        tv_acc.setTextColor(Color.GREEN);
                    } else {
                        tv_acc.setTextColor(Color.RED);
                    }
                }
                Toast.makeText(getApplicationContext(), "Обновление...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}