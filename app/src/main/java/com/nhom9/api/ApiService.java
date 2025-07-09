    package com.nhom9.api;

    //import com.nhom9.models.Product;
    //import com.nhom9.models.Service;

    //import java.util.List;
    import com.nhom9.models.LoginRequest;
    import com.nhom9.models.User;


    import java.util.Map;

    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.http.Body;
    import retrofit2.http.POST;

    public interface ApiService {
        // Lấy tất cả Products
    //    @GET("products")
    //    Call<List<Product>> getProducts();
    //    @GET("products/{id}")
    //    Call<Product> getProductById(@Path("id") String id);
    //
    //    // POST - Tạo mới (Create)
    //    @POST("products")
    //    Call<Product> createProduct(@Body Product product);
    //
    //    // PUT - Cập nhật toàn bộ (Update)
    //    @PUT("products/{id}")
    //    Call<Product> updateProduct(@Path("id") String id, @Body Product product);
    //
    //    // PATCH - Cập nhật một phần
    //    @PATCH("products/{id}")
    //    Call<Product> patchProduct(@Path("id") String id, @Body Product product);
    //
    //    // DELETE - Xóa (Delete)
    //    @DELETE("products/{id}")
    //    Call<Void> deleteProduct(@Path("id") String id);
    //
    //    // SERVICE
    //    @GET("services")
    //    Call<List<Service>> getService();
    //    @GET("services/{id}")
    //    Call<Service> getServiceById(@Path("id") String id);
    //
    //    // POST - Tạo mới (Create)
    //    @POST("services")
    //    Call<Service> createService(@Body Service service);
    //
    //    // PUT - Cập nhật toàn bộ (Update)
    //    @PUT("services/{id}")
    //    Call<Service> updateService(@Path("id") String id, @Body Service service);
    //
    //    // PATCH - Cập nhật một phần
    //    @PATCH("services/{id}")
    //    Call<Service> patchService(@Path("id") String id, @Body Service service);
    //
    //    // DELETE - Xóa (Delete)
    //    @DELETE("services/{id}")
    //    Call<Void> deleteService(@Path("id") String id);


        // REGISTER
        @POST("users/register")
        Call<User> register(@Body User user);
        // LOGIN
        @POST("users/login")
        Call<ResponseBody> login(@Body LoginRequest request);
        // RESET PASSWORD
        @POST("/api/users/reset-password")
        Call<ResponseBody> resetPassword(@Body Map<String, String> body);
        // Gửi mã xác nhận
        @POST("/users/send-otp")
        Call<ResponseBody> sendOtp(@Body Map<String, String> body);

        // Xác nhận OTP và đổi mật khẩu
        @POST("/users/confirm-reset")
        Call<ResponseBody> confirmResetPassword(@Body Map<String, String> body);



    }