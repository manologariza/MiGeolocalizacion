package com.example.manolo.migeolocalizacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, LocationListener{

    private TextView tvLatitud, tvLongitud, tvAltura, tvPrecision;
    private LocationManager locationManager;
    private Criteria criteria;
    private String mejorProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView) findViewById(R.id.tvLongitud);
        tvAltura = (TextView) findViewById(R.id.tvAltura);
        tvPrecision = (TextView) findViewById(R.id.tvPrecision);

        getLocalizacion();
    }

    public void getLocalizacion() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        mejorProveedor = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(mejorProveedor);

        if(location!=null){
            tvLatitud.setText(String.valueOf(location.getLatitude()));
            tvLongitud.setText(String.valueOf(location.getLongitude()));
            tvAltura.setText(String.valueOf(location.getAltitude()));
            tvPrecision.setText(String.valueOf(location.getAccuracy()));
        }
        else{
            Toast.makeText(getApplicationContext(), "Determinando nueva ubicación", Toast.LENGTH_LONG).show();
            locationManager.requestLocationUpdates(mejorProveedor, 1000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
                
        tvLatitud.setText(String.valueOf(location.getLatitude()));
        tvLongitud.setText(String.valueOf(location.getLongitude()));
        tvAltura.setText(String.valueOf(location.getAltitude()));
        tvPrecision.setText(String.valueOf(location.getAccuracy()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
