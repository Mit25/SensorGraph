package com.example.android.sensorgraph;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ProxGraph extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor proxySensor;
    SensorEventListener proxyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prox_graph);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if( proxySensor == null ){
            Toast.makeText(this, "Your Device Has No Proxy Sensor!", Toast.LENGTH_SHORT).show();
            finish();
        }

        proxyListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proxySensor.getMaximumRange()) {
                    // Detected something nearby
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    // Nothing is nearby
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proxyListener,proxySensor, 2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proxyListener);
    }
}
