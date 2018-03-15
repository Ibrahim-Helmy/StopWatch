package com.example.ibrahim_pc.stopwatch;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class MainActivity extends AppCompatActivity {
    CircleProgressView circleProgressView;
    Button btnStart, btnStop, btnPause;
    Chronometer chronometer;
    long time = 0;

    int progessValue = -1;

    int m = 1;


    StopWatch s = new StopWatch();
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        circleProgressView = findViewById(R.id.cpv_id);
        btnStart = findViewById(R.id.start_id);
        btnStop = findViewById(R.id.stop_id);
        btnPause = findViewById(R.id.pusebtn_id);

        chronometer = findViewById(R.id.Chronometer_id);
        circleProgressView.setTextMode(TextMode.VALUE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (m==2){
                   time+=2;
                    chronometer.setBase(SystemClock.elapsedRealtime()+time);
                    chronometer.start();
                    s.startCount();
                }else {
                if (progessValue != -1) {
                    Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                    m=1;
                } else{
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    s.startCount();
                    m=1;
                }}
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m==3){}else {
                    timer.cancel();
                    time = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.setBase(SystemClock.elapsedRealtime() + time);
                    chronometer.stop();

                    m = 2;

                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progessValue == -1) {
                    Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                } else {
                    time = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                    s.StopCount();
                    m=3;
                }
            }
        });
    }

    class StopWatch {


        public void startCount() {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {


                        progessValue++;

                        circleProgressView.setValue(progessValue);
                        if (progessValue == 59)
                            progessValue = -1;
                    }

            }, 0, 1000);


        }

        public void StopCount() {

            if (timer == null) {
                Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                progessValue = -1;
                chronometer.setBase(SystemClock.elapsedRealtime() +time);

            } else {
                if (m==2)
                    m=1;
                timer.cancel();
                timer = null;
                progessValue = -1;
                chronometer.setBase(SystemClock.elapsedRealtime() +time);

            }
        }
    }
}
