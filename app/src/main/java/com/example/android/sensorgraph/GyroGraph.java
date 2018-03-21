package com.example.android.sensorgraph;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class GyroGraph extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyroListener;

    private LineChart mChart,yChart,zChart;
    private Thread thread;
    private boolean plotData = true;
    TextView tvX,tvY,tvZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_graph);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);

        if( gyroscopeSensor == null ){
            Toast.makeText(this, "Your Device Has No Gyroscope!", Toast.LENGTH_SHORT).show();
            finish();
        }

        tvX = (TextView)findViewById(R.id.tvX1);
        tvY = (TextView)findViewById(R.id.tvY1);
        tvZ = (TextView)findViewById(R.id.tvZ1);

        mChart = (LineChart)findViewById(R.id.chart1g);
        mChart.getDescription().setEnabled(true);
        mChart.getDescription().setText("Real Time Gyroscope!");

        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(false);
        mChart.setBackgroundColor(Color.DKGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.GREEN);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.RED);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.CYAN);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(-10f);
        leftAxis.setDrawGridLines(true);

        YAxis rigthAxis = mChart.getAxisRight();
        rigthAxis.setEnabled(false);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setDrawBorders(false);

        //Second Chart
        yChart = (LineChart)findViewById(R.id.chart2g);
        yChart.getDescription().setEnabled(true);
        yChart.getDescription().setText("Real Time Gyroscope!");

        yChart.setTouchEnabled(false);
        yChart.setDragEnabled(false);
        yChart.setScaleEnabled(false);
        yChart.setDrawGridBackground(false);
        yChart.setPinchZoom(false);
        yChart.setBackgroundColor(Color.DKGRAY);

        LineData data2 = new LineData();
        data2.setValueTextColor(Color.WHITE);
        yChart.setData(data2);

        Legend l2 = yChart.getLegend();
        l2.setForm(Legend.LegendForm.LINE);
        l2.setTextColor(Color.GREEN);

        XAxis xl2 = yChart.getXAxis();
        xl2.setTextColor(Color.RED);
        xl2.setDrawGridLines(true);
        xl2.setAvoidFirstLastClipping(true);
        xl2.setEnabled(true);

        YAxis leftAxis2 = yChart.getAxisLeft();
        leftAxis2.setTextColor(Color.CYAN);
        leftAxis2.setDrawGridLines(false);
        leftAxis2.setAxisMaximum(10f);
        leftAxis2.setAxisMinimum(-10f);
        leftAxis2.setDrawGridLines(true);

        YAxis rigthAxis2 = yChart.getAxisRight();
        rigthAxis2.setEnabled(false);

        yChart.getAxisLeft().setDrawGridLines(false);
        yChart.getXAxis().setDrawGridLines(false);
        yChart.setDrawBorders(false);

        //Third Chart

        zChart = (LineChart)findViewById(R.id.chart3g);
        zChart.getDescription().setEnabled(true);
        zChart.getDescription().setText("Real Time Gyroscope!");

        zChart.setTouchEnabled(false);
        zChart.setDragEnabled(false);
        zChart.setScaleEnabled(false);
        zChart.setDrawGridBackground(false);
        zChart.setPinchZoom(false);
        zChart.setBackgroundColor(Color.DKGRAY);

        LineData data3 = new LineData();
        data3.setValueTextColor(Color.WHITE);
        zChart.setData(data3);

        Legend lz = zChart.getLegend();
        lz.setForm(Legend.LegendForm.LINE);
        lz.setTextColor(Color.GREEN);

        XAxis xlz = zChart.getXAxis();
        xlz.setTextColor(Color.RED);
        xlz.setDrawGridLines(true);
        xlz.setAvoidFirstLastClipping(true);
        xlz.setEnabled(true);

        YAxis leftAxisz = zChart.getAxisLeft();
        leftAxisz.setTextColor(Color.CYAN);
        leftAxisz.setDrawGridLines(false);
        leftAxisz.setAxisMaximum(10f);
        leftAxisz.setAxisMinimum(-10f);
        leftAxisz.setDrawGridLines(true);

        YAxis rigthAxisz = zChart.getAxisRight();
        rigthAxisz.setEnabled(false);

        zChart.getAxisLeft().setDrawGridLines(false);
        zChart.getXAxis().setDrawGridLines(false);
        zChart.setDrawBorders(false);

        startOn();
        gyroListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if( plotData ) {
                    addEntry(sensorEvent);
                    plotData = false;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        };
    }

    private void startOn(){
        if( thread!=null ){
            thread.interrupt();
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    plotData=true;
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void addEntry(SensorEvent event){
        //First Chart
        LineData data = mChart.getData();
        if( data != null ){
            ILineDataSet set = data.getDataSetByIndex(0);
            if(set ==null){
                set=createSet(1);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(),event.values[0]), 0);//x Axis
            tvX.setText(""+event.values[0]);
            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.setVisibleXRangeMaximum(50);
            mChart.moveViewToX(data.getEntryCount());
        }

        //Second Chart
        LineData data2 = yChart.getData();
        if( data2 != null ){
            ILineDataSet set = data2.getDataSetByIndex(0);
            if(set ==null){
                set=createSet(2);
                data2.addDataSet(set);
                Log.d("info-> ", "createSet completed!");
            }
            data2.addEntry(new Entry(set.getEntryCount(),event.values[1]), 0);//y Axis
            tvY.setText(""+event.values[1]);
            data2.notifyDataChanged();
            yChart.notifyDataSetChanged();
            yChart.setVisibleXRangeMaximum(50);
            yChart.moveViewToX(data2.getEntryCount());
        }

        //Third Chart
        LineData data3 = zChart.getData();
        if( data3 != null ){
            ILineDataSet set = data3.getDataSetByIndex(0);
            if(set ==null){
                set=createSet(3);
                data3.addDataSet(set);
            }
            data3.addEntry(new Entry(set.getEntryCount(),event.values[2]), 0);//x Axis
            tvZ.setText(""+event.values[2]);
            data3.notifyDataChanged();
            zChart.notifyDataSetChanged();
            zChart.setVisibleXRangeMaximum(50);
            zChart.moveViewToX(data.getEntryCount());
        }

    }

    private LineDataSet createSet(int i){
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        Log.d("info-> ", "createSet Entered! " + i + "...");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(1f);
        set.setColor(Color.MAGENTA);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.1f);
        return set;
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(gyroListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    protected void onDestroy() {
        sensorManager. unregisterListener(gyroListener);
        thread.interrupt();
        super.onDestroy();
    }
}
