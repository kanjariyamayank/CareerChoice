package demo.mayank.myallbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import demo.mayank.myallbooks.database.DatabaseHandler;
import demo.mayank.myallbooks.models.BookModel;
import demo.mayank.myallbooks.network.NetworkClient;
import demo.mayank.myallbooks.network.NetworkService;
import demo.mayank.myallbooks.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView booksRecyclerView;
    ImageView imageBack;
    TextView textTotalAmount;
    List<BookModel> cartItems;
    int totalAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textTotalAmount = (TextView) findViewById(R.id.text_total_amount);
        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        booksRecyclerView = findViewById(R.id.cart_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksRecyclerView.setHasFixedSize(true);
        cartItems = new DatabaseHandler(getApplicationContext()).getCartItems();
        booksRecyclerView.setAdapter(new BooksAdapter(cartItems));
        /*butonPlaceOrder = (Button) findViewById(R.id.button_place_order);
        butonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean(Constants.KEY_ISE_LOGGED_IN, false);

                if (!isLoggedIn) {
                    Toast.makeText(CartActivity.this, "Please login first.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user_email", preferences.getString(Constants.KEY_EMAIL, "N/A"));
                    params.put("total_amount", String.valueOf(totalAmount));
                    params.put("products", new Gson().toJson(cartItems));
                    params.put("order_date", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                    placeOrder(params);
                }
            }
        });
*/

        calculateTotal();
    }

    /*private void placeOrder(HashMap<String, String> params) {

        final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Placing order...");
        progressDialog.setCancelable(false);
        progressDialog.show();

      *//*  NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<PlaceOrderResponse> placeOrderCall = networkService.placeOrder(params);
        placeOrderCall.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceOrderResponse> call, @NonNull Response<PlaceOrderResponse> response) {
                PlaceOrderResponse responseBody = response.body();
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        Toast.makeText(CartActivity.this, responseBody.getMessage(), Toast.LENGTH_LONG).show();
                        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                        //new SendMailTask(CartActivity.this).execute("nidhitimbadiya17@gmail.com", "nidhi123", "khakhkhar.mayur@gmail.com, vaghelagunjani22@gmail.com", "New Order Placed.", "Hello, Your order of \u20B9" + totalAmount+ " Has been placed successfully, Thank you.");
                        new SendMailTask(CartActivity.this).execute("nidhitimbadiya17@gmail.com", "nidhi123",
                                preferences.getString(Constants.KEY_EMAIL, "N/A"), "New Order Placed.", "Hello, Your order of \u20B9" + totalAmount + " Has been placed successfully, Thank you.");
                        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                        databaseHandler.deleteCart();
                        finish();
                    } else {
                        Toast.makeText(CartActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                progressDialog.dismiss();
            }*//*

            *//*@Override
            public void onFailure(@NonNull Call<PlaceOrderResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });*//*
    }*/

    private void calculateTotal() {
        totalAmount = 0;
        String s = "";
        for (int i = 0; i <= cartItems.size() - 1; i++) {
            totalAmount = totalAmount + Integer.parseInt(cartItems.get(i).getPrice());
        }

        textTotalAmount.setText("Total Amt. \u20B9 " + String.valueOf(totalAmount));

    }

    private class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

        List<BookModel> books;

        BooksAdapter(List<BookModel> books) {
            this.books = books;
        }


        @Override
        public int getItemCount() {
            return books.size();
        }

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.cart_item_container, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(final BookViewHolder holder, int position) {


            if (books.get(position).getName() != null && !books.get(position).getName().equals("")) {
                holder.textName.setText(books.get(position).getName());
            } else {
                holder.textName.setVisibility(View.GONE);
            }

            if (books.get(position).getPrice() != null && !books.get(position).getPrice().equals("")) {
                holder.textPrice.setText("\u20B9" + books.get(position).getPrice());
            } else {
                holder.textPrice.setVisibility(View.GONE);
            }

            if (books.get(position).getAuthor() != null && !books.get(position).getAuthor().equals("")) {
                holder.textAuthor.setText(books.get(position).getAuthor());
            } else {
                holder.textAuthor.setVisibility(View.GONE);
            }

            if (books.get(position).getImage() != null && !books.get(position).getImage().equals("")) {
                Picasso.with(getApplicationContext()).load(
                        books.get(position).getImage()
                ).into(holder.imageBook);
            } else {
                holder.imageBook.setVisibility(View.GONE);
            }

            holder.textRemoveFromCart.setPaintFlags(holder.textRemoveFromCart.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.textRemoveFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatabaseHandler(getApplicationContext()).removeItem(books.get(holder.getAdapterPosition()).getId());
                    cartItems.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), cartItems.size());
                    calculateTotal();
                }
            });

        }

        class BookViewHolder extends RecyclerView.ViewHolder {

            ImageView imageBook;
            TextView textName, textAuthor, textPrice, textRemoveFromCart;

            BookViewHolder(View view) {
                super(view);

                imageBook = (ImageView) view.findViewById(R.id.image_book);
                textName = (TextView) view.findViewById(R.id.text_book_name);
                textAuthor = (TextView) view.findViewById(R.id.text_book_author);
                textPrice = (TextView) view.findViewById(R.id.text_book_price);
                textRemoveFromCart = (TextView) view.findViewById(R.id.text_remove_item);
            }
        }
    }
}