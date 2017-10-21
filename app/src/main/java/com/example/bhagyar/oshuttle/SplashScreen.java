package com.example.bhagyar.oshuttle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import business_modules.map_view.MapViewActivity;

public class SplashScreen extends AppCompatActivity {

    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        animatedCircleLoadingView.startDeterminate();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(10);
            }
        }, 1000);
        animatedCircleLoadingView.startIndeterminate();
        Intent intent = new Intent(SplashScreen.this, MapViewActivity.class);
        startActivity(intent);

    }
}
