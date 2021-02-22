package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SopingActivity extends AppCompatActivity {

    LinearLayout friut_list,apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soping);
        apple = findViewById(R.id.apple_details);
        friut_list = findViewById(R.id.linerlayout_Fruits);
        friut_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SopingActivity.this,FruitsDetailsActivity.class);
                startActivity(intent);
            }
        });

        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SopingActivity.this,AppleDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}