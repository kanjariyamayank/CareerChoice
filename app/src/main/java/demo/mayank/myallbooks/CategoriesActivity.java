package demo.mayank.myallbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import demo.mayank.myallbooks.models.CategoryModel;
import demo.mayank.myallbooks.models.CategoryResponseModel;
import demo.mayank.myallbooks.network.NetworkClient;
import demo.mayank.myallbooks.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView categoriesRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories();
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        findViewById(R.id.image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoriesRecyclerView.setHasFixedSize(true);
        getCategories();
    }

    private void getCategories() {


        final ProgressDialog progressDialog = new ProgressDialog(CategoriesActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting categories");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<CategoryResponseModel> categoryResponseModelCall = networkService.getCategoriesFromServer();

        categoryResponseModelCall.enqueue(new Callback<CategoryResponseModel>() {

            @Override
            public void onResponse(@NonNull Call<CategoryResponseModel> call, @NonNull Response<CategoryResponseModel> response) {
                Toast.makeText(CategoriesActivity.this, "success", Toast.LENGTH_SHORT).show();
                CategoryResponseModel categoryResponseModel = response.body();
                final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoryResponseModel.getCategories());
                categoriesRecyclerView.setAdapter(categoriesAdapter);
                progressDialog.cancel();


            }


            @Override
            public void onFailure(@NonNull Call<CategoryResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(CategoriesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        CardView categoryItemLayout;
        TextView textCategory;

        CategoryViewHolder(View view) {
            super(view);
            categoryItemLayout = (CardView) view.findViewById(R.id.category_card_view);
            textCategory = (TextView) view.findViewById(R.id.text_category);
        }
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

        List<CategoryModel> categories;

        CategoriesAdapter(List<CategoryModel> categories) {
            this.categories = categories;
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_container, parent, false));
        }

        @Override
        public void onBindViewHolder(final CategoryViewHolder holder, int position) {

            if (categories.get(holder.getAdapterPosition()).getCategory() != null) {
                holder.textCategory.setText(categories.get(holder.getAdapterPosition()).getCategory());
                holder.categoryItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                        intent.putExtra("category", categories.get(holder.getAdapterPosition()).getCategory());
                        startActivity(intent);
                    }
                });
            }
        }


    }

}
