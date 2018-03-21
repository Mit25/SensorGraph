package com.example.android.sensorgraph;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String s="...";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void goToProxGraph(View view){
        Intent intent = new Intent(this, ProxGraph.class);
        startActivity(intent);
    }

    public void goToAccGraph(View view){
        Intent intent = new Intent(this, AccGraphNew.class);
        startActivity(intent);
    }

    public void goToGyroGraph(View view){
        Intent intent = new Intent(this, GyroGraph.class);
        startActivity(intent);
    }

    public void goToMicGraph(View view){
        Intent intent = new Intent(this, MicRecord.class);
        startActivity(intent);
    }


}
