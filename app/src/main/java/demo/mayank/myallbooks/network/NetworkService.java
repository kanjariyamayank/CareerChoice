package demo.mayank.myallbooks.network;

import java.util.HashMap;


import demo.mayank.myallbooks.models.BooksResponseModel;
import demo.mayank.myallbooks.models.CategoryResponseModel;
import demo.mayank.myallbooks.models.LoginResponseModel;
import demo.mayank.myallbooks.models.RegistrationResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("categories.php")
    Call<CategoryResponseModel> getCategoriesFromServer();

    @FormUrlEncoded
    @POST("books.php")
    Call<BooksResponseModel> getBooksByCategories(@Field("category") String category);

   @FormUrlEncoded
    @POST("register.php")
    Call<RegistrationResponseModel> register(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("logincook.php")
    Call<LoginResponseModel> login(@Field("email") String email, @Field("password") String password);

   /* @FormUrlEncoded
    @POST("place_order.php")
    Call<PlaceOrderResponse> placeOrder(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("order_history.php")
    Call<OrderResponseModel> getOrders(@Field("email") String email);*/
}
