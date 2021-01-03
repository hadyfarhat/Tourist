package com.example.tourist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourist.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchGallery(View view) {
        //Intent intent = new Intent(this, JourneyDetailView.class);
        Intent intent = new Intent(this, JourneysView.class);
        startActivity(intent);
    }

}