package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FruitsDetailsActivity extends AppCompatActivity {

    LinearLayout apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_details);
        apple = findViewById(R.id.apple_details);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FruitsDetailsActivity.this,AppleDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}