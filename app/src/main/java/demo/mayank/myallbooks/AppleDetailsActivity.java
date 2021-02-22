package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AppleDetailsActivity extends AppCompatActivity {


    Button place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_details);
        place = findViewById(R.id.place_order);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AppleDetailsActivity.this,PlaceOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}