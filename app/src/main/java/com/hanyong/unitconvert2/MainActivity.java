package com.hanyong.unitconvert2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open_curruncy_page(View view) {
        Intent i = new Intent(this,Curruncy.class);
        startActivity(i);
    }

    public void open_volume_page(View view) {
        Intent i = new Intent(this,Volume.class);
        startActivity(i);
    }

    public void open_area_page(View view) {
        Intent i = new Intent(this,Area.class);
        startActivity(i);
    }

    public void open_weight_page(View view) {
        Intent i = new Intent(this,Weight.class);
        startActivity(i);
    }

    public void open_temperature_page(View view) {
        Intent i = new Intent(this,Temperature.class);
        startActivity(i);
    }

    public void open_length_page(View view) {
        Intent i = new Intent(this,Length.class);
        startActivity(i);
    }
}
