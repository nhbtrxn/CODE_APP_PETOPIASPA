package com.nhom9.api;

import com.nhom9.models.Product;
import com.nhom9.models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    // Lấy tất cả Products
    @GET("products")
    Call<List<Product>> getProducts();
    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    // POST - Tạo mới (Create)
    @POST("products")
    Call<Product> createProduct(@Body Product product);

    // PUT - Cập nhật toàn bộ (Update)
    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") String id, @Body Product product);

    // PATCH - Cập nhật một phần
    @PATCH("products/{id}")
    Call<Product> patchProduct(@Path("id") String id, @Body Product product);

    // DELETE - Xóa (Delete)
    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") String id);

    // SERVICE
    @GET("services")
    Call<List<Service>> getService();
    @GET("services/{id}")
    Call<Service> getServiceById(@Path("id") String id);

    // POST - Tạo mới (Create)
    @POST("services")
    Call<Service> createService(@Body Service service);

    // PUT - Cập nhật toàn bộ (Update)
    @PUT("services/{id}")
    Call<Service> updateService(@Path("id") String id, @Body Service service);

    // PATCH - Cập nhật một phần
    @PATCH("services/{id}")
    Call<Service> patchService(@Path("id") String id, @Body Service service);

    // DELETE - Xóa (Delete)
    @DELETE("services/{id}")
    Call<Void> deleteService(@Path("id") String id);
}
