package demo.mayank.myallbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import demo.mayank.myallbooks.utils.Constants;
import retrofit2.http.Url;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ImageView imageMenu;
    TextView textUsername, textEmail;
    SliderLayout sliderLayout ;
    LinearLayout layoutLogin, layoutLogout, layoutabout, layoutfeedback, layoutorder, layoutbook,layoutshop,layoutcontact;
    HashMap<String, String> HashMapForURL ;
    HashMap<String, Integer> HashMapForLocalRes ;
    private ViewPager2 viewPager2;
    private Handler sliderHandeler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageMenu = (ImageView) findViewById(R.id.image_menu);
        /*viewLogout = findViewById(R.id.view_logout);*/
        layoutabout = (LinearLayout) findViewById(R.id.layout_about);
        layoutbook = (LinearLayout) findViewById(R.id.layout_books);
        layoutLogin = (LinearLayout) findViewById(R.id.layout_login);
        layoutLogout = (LinearLayout) findViewById(R.id.layout_logout);
        layoutshop = (LinearLayout) findViewById(R.id.layout_shop);
        layoutfeedback = (LinearLayout) findViewById(R.id.layout_feedback);
        textUsername = (TextView) findViewById(R.id.text_username);
        textEmail = (TextView) findViewById(R.id.text_email);
        layoutcontact =  findViewById(R.id.layout_contect_us);

        layoutcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,ContectusActivity.class);
                startActivity(intent);
            }
        });
        layoutshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,SopingActivity.class);
                startActivity(intent);
            }
        });

        final SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        final boolean isLoggedIn = preferences.getBoolean(Constants.KEY_ISE_LOGGED_IN, false);

        if (!isLoggedIn) {
            textUsername.setText(R.string.welcome_guest);
            textUsername.setVisibility(View.VISIBLE);
            /*layoutLogin.setVisibility(View.VISIBLE);*/
            /*viewLogin.setVisibility(View.VISIBLE);*/
            layoutLogout.setVisibility(View.GONE);
            /*viewLogout.setVisibility(View.GONE);*/
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {
            textUsername.setText(preferences.getString(Constants.KEY_USERNAME, "N/A"));
            textEmail.setText(preferences.getString(Constants.KEY_EMAIL, "N/A"));
            textUsername.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.VISIBLE);
            /*layoutLogin.setVisibility(View.GONE);*/
           /* viewLogin.setVisibility(View.GONE);*/
            layoutLogout.setVisibility(View.VISIBLE);
/*            viewLogout.setVisibility(View.VISIBLE);*/
        }
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        findViewById(R.id.layout_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SopingActivity.class));
            }
        });

        findViewById(R.id.card_books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            }
        });

        findViewById(R.id.card_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        findViewById(R.id.card_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure you want to logout?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(DashboardActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure you want to logout?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(DashboardActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
        layoutabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        layoutfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        layoutbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });


        viewPager2= findViewById(R.id.viewpagerimageslider);
        List<Slideritem>slideritems = new ArrayList<>();
        slideritems.add(new Slideritem(R.drawable.cookin));
        slideritems.add(new Slideritem(R.drawable.cooking1));
        slideritems.add(new Slideritem(R.drawable.cookin3));

        viewPager2.setAdapter(new SliderAdapter(slideritems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer =new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r + 0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandeler.removeCallbacks(sliderRunnable);
                sliderHandeler.postDelayed(sliderRunnable,3000);
            }
        });
        /*sliderLayout = (SliderLayout)findViewById(R.id.slider);*/
        /*AddImageUrlFormLocalRes();
        AddImagesUrlOnline();*/

    }
    private Runnable sliderRunnable =new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandeler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandeler.postDelayed(sliderRunnable,3000);
    }
}
