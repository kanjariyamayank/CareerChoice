package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView_splash;
    private static int SPLASH_TIME_OUT = 2000;
    TextView a, slogan;
    Animation topAnimantion,bottomAnimation,middleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        a = findViewById(R.id.a);
        imageView_splash = findViewById(R.id.image_splsh);
        slogan = findViewById(R.id.tagLine);
        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle);
        a.setAnimation(topAnimantion);
        slogan.setAnimation(bottomAnimation);
        imageView_splash.setAnimation(middleAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2000);

                    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    if (pref.getBoolean("activity_executed", false)) {
                        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        SharedPreferences.Editor edt = pref.edit();
                        edt.putBoolean("activity_executed", true);
                        edt.commit();
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },SPLASH_TIME_OUT);
        /*Thread background = new Thread(){
            public void run(){
                try {
                    sleep(5000);

                    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    if (pref.getBoolean("activity_executed", false)) {
                        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();*/
    }
}
