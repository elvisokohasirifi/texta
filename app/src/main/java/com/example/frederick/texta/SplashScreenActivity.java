package com.example.frederick.texta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Thread splashThread;
    TextView sub;
    Animation animation;
    TextView a;
    Animation animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sub = findViewById(R.id.sub);
        a = findViewById(R.id.main);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        a.startAnimation(animation);
        sub.startAnimation(animation2);

        splashThread = new Thread() {
            @Override
            public void run() {

                try{
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }



        };

        splashThread.start();
    }
}
