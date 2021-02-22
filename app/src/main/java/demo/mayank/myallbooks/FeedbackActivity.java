package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button submit;
    ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ratingBar =(RatingBar)findViewById(R.id.ratingBar1);
        submit=(Button)findViewById(R.id.button1);
        imageback=(ImageView)findViewById(R.id.image_back);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(getApplicationContext(), rating+"", Toast.LENGTH_LONG).show();
            }
        });
    }
}
