package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;

import demo.mayank.myallbooks.database.DatabaseHandler;
import demo.mayank.myallbooks.models.BookModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class BookDetailActivity extends AppCompatActivity {

    ImageView imageBook, imageAddToCart;
    TextView textAuthor, textPublishedYear, textDescription, textPrice, textToolbarTitle;
    SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        imageBook = (ImageView) findViewById(R.id.image_book);
        imageAddToCart = (ImageView) findViewById(R.id.image_add_to_cart);
        textAuthor = (TextView) findViewById(R.id.text_author);
        textPublishedYear = (TextView) findViewById(R.id.text_published_year);
        textDescription = (TextView) findViewById(R.id.text_description);
        textPrice = (TextView) findViewById(R.id.text_price);
        textToolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        findViewById(R.id.image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /* Animation animation = AnimationUtils.loadAnimation(BookDetailActivity.this, R.anim.bounce_animation);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                imageAddToCart.startAnimation(animation);*/
                BookModel bookModel = new BookModel();
                bookModel.setId(getIntent().getStringExtra("id"));
                bookModel.setCategory(getIntent().getStringExtra("category"));
                bookModel.setName(getIntent().getStringExtra("name"));
                bookModel.setDescription(getIntent().getStringExtra("description"));
                bookModel.setAuthor(getIntent().getStringExtra("author"));
                bookModel.setImage(getIntent().getStringExtra("image"));
                bookModel.setPublishedYear(getIntent().getStringExtra("publishedYear"));
                bookModel.setPrice(getIntent().getStringExtra("price"));

                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.addToCart(bookModel);
                Toast.makeText(BookDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image"))
                .into(imageBook);

        textAuthor.setText(getIntent().getStringExtra("author"));
        textPublishedYear.setText(getIntent().getStringExtra("publishedYear"));
        textDescription.setText(getIntent().getStringExtra("description"));
        textToolbarTitle.setText(getIntent().getStringExtra("name"));
        textPrice.setText("\u20B9" + getIntent().getStringExtra("price"));
        try {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Image 1", getIntent().getStringExtra("image"));
            url_maps.put("Image 2", getIntent().getStringExtra("image2"));
            url_maps.put("Image 3", getIntent().getStringExtra("image3"));
            url_maps.put("Image 4", getIntent().getStringExtra("image4"));

            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                sliderLayout.addSlider(textSliderView);
            }
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(4000);

        } catch (Exception ex) {
        }
    }
}