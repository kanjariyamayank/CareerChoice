package demo.mayank.myallbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import demo.mayank.myallbooks.models.BookModel;
import demo.mayank.myallbooks.models.BooksResponseModel;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksActivity extends AppCompatActivity {

    RecyclerView booksRecyclerView;
    TextView textToolbarTitle;
    ImageView imageBack;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBooks();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        textToolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        textToolbarTitle.setText(getIntent().getStringExtra("category"));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        booksRecyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksRecyclerView.setHasFixedSize(true);

        getBooks();
    }

    private void getBooks() {

        final ProgressDialog progressDialog = new ProgressDialog(BooksActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting Recipe");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<BooksResponseModel> getBooks = networkService.getBooksByCategories(getIntent().getStringExtra("category"));
        getBooks.enqueue(new Callback<BooksResponseModel>() {
            @Override
            public void onResponse(Call<BooksResponseModel> call, Response<BooksResponseModel> response) {
                progressDialog.cancel();
                BooksAdapter booksAdapter = new BooksAdapter(response.body().getBooks());
                booksRecyclerView.setAdapter(booksAdapter);
            }

            @Override
            public void onFailure(Call<BooksResponseModel> call, Throwable t) {
                progressDialog.cancel();
            }
        });

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
                    R.layout.book_item_container, parent, false
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

            if (books.get(position).getDescription() != null && !books.get(position).getDescription().equals("")) {
                holder.textDescription.setText(books.get(position).getDescription());
            } else {
                holder.textDescription.setVisibility(View.GONE);
            }
            if (books.get(position).getImage() != null && !books.get(position).getImage().equals("")) {
                Picasso.with(getApplicationContext()).load(
                        books.get(position).getImage()).into(holder.imageBook);
                Toast.makeText(BooksActivity.this, "load", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BooksActivity.this, "sorry", Toast.LENGTH_SHORT).show();

                holder.imageBook.setVisibility(View.GONE);
            }

            holder.cardBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                    intent.putExtra("image", books.get(holder.getAdapterPosition()).getImage());
                    intent.putExtra("image2", books.get(holder.getAdapterPosition()).getImage2());
                    intent.putExtra("image3", books.get(holder.getAdapterPosition()).getImage3());
                    intent.putExtra("image4", books.get(holder.getAdapterPosition()).getImage4());
                    intent.putExtra("author", books.get(holder.getAdapterPosition()).getAuthor());
                    intent.putExtra("publishedYear", books.get(holder.getAdapterPosition()).getPublishedYear());
                    intent.putExtra("description", books.get(holder.getAdapterPosition()).getDescription());
                    intent.putExtra("price", books.get(holder.getAdapterPosition()).getPrice());
                    intent.putExtra("name", books.get(holder.getAdapterPosition()).getName());
                    intent.putExtra("id", books.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("category", books.get(holder.getAdapterPosition()).getCategory());
                    startActivity(intent);
                }
            });
        }

        class BookViewHolder extends RecyclerView.ViewHolder {

            CardView cardBook;
            ImageView imageBook;
            TextView textName, textAuthor, textPrice, textDescription;

            BookViewHolder(View view) {
                super(view);
                cardBook = (CardView) view.findViewById(R.id.book_card_view);
                imageBook = (ImageView) view.findViewById(R.id.image_book);
                textName = (TextView) view.findViewById(R.id.text_book_name);
                textAuthor = (TextView) view.findViewById(R.id.text_book_author);
                textPrice = (TextView) view.findViewById(R.id.text_book_price);
                textDescription = (TextView) view.findViewById(R.id.text_description);
            }
        }
    }
}