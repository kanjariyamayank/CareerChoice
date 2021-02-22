package demo.mayank.myallbooks.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MRK on 19-02-2018.
 */

public class BooksResponseModel {

    @SerializedName("books")
    private List<BookModel> books;

    public List<BookModel> getBooks() {
        return books;
    }
}
