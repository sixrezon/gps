package com.example.gps;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public String lat, lon, acc;
    TextView tv_lat, tv_lon, tv_acc, tv_result;
    Button btn_update, btn_write;
    public String[] result = new String[15];

    int j = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 1337);

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_acc = findViewById(R.id.tv_acc);
        tv_result = findViewById(R.id.tv_result);
        btn_update = findViewById(R.id.btn_update);
        btn_write = findViewById(R.id.btn_write);

        Arrays.fill(result, ""); //задать всем элемента массива пустое значение

        btn_update.setOnClickListener(v -> {
            GPSClass g = new GPSClass(getApplicationContext());
            Location l = g.getLocation();
            if (l != null) {
                double latitude = l.getLatitude();
                double longitude = l.getLongitude();
                double accuracy = l.getAccuracy();

                DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat df = new DecimalFormat("###.000000", otherSymbols);
                lat = df.format(latitude);
                lon = df.format(longitude);
                acc = Double.toString(accuracy);

                tv_lat.setText(lat);
                tv_lon.setText(lon);
                tv_acc.setText(acc);

                if (l.getAccuracy() < 5) {
                    tv_acc.setTextColor(Color.GREEN);
                } else {
                    tv_acc.setTextColor(Color.RED);
                }
            }
            Toast.makeText(getApplicationContext(), "Обновление", Toast.LENGTH_SHORT).show();
        });
        btn_write.setOnClickListener(v -> {
            if(lat!=null && lon!=null) {
                out_coordinate();
            } else{
                Toast.makeText(getApplicationContext(), "Координаты не найдены!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void out_coordinate(){
        result[j] = (j + 1) + ". " + lat + "; " + lon + "\n";
        j++;
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : result) {
            stringBuilder.append(s);
        }
        String result_2 = stringBuilder.toString();
        tv_result.setText(result_2);

        if (j == 15) {
            j = 0;
            Arrays.fill(result, ""); //есть выведено 5 строк, то обнуление
        }
    }
}