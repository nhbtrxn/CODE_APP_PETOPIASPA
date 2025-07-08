package com.nhom9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.nhom9.adapters.ProductAdapter;
import com.nhom9.models.Product;
import com.nhom9.myapplication.databinding.ActivityProductDetailBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    ActivityProductDetailBinding binding;
    boolean[] isExpanded = {false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");
        ArrayList<Product> allProducts = (ArrayList<Product>) getIntent().getSerializableExtra("allProducts");
        if (product == null) {
            finish();
            return;
        }

        loadMainImage(product.getProductImage().get(0));

        binding.txtProductName.setText(product.getProductName());
        binding.txtProductDescription.setText(product.getProductDetail());

        // Xử lý hiện giá (lấy giá nhỏ nhất trong các thuộc tính giá của sản phẩm)
        double minPrice = -1;
        if (product.getAttributes() != null && !product.getAttributes().isEmpty()) {
            minPrice = product.getAttributes().get(0).getProductPrice();
            for (var attr : product.getAttributes()) {
                double price = attr.getProductPrice();
                if (price < minPrice) minPrice = price;
            }
        }

        if (minPrice >= 0) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
            binding.txtProductPrice.setText(formatter.format(minPrice));
        } else {
            binding.txtProductPrice.setText("Giá liên hệ");
        }
        binding.txtProductRate.setText(String.valueOf(product.getProductRate()));
        binding.txtProductSold.setText("(" + product.getProductNumberSold() + ")");
        binding.txtTopProduct.setText("Thuộc top bán chạy của " + product.getProductCategory());
        binding.lnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded[0]) {

                    binding.txtProductDescription.setMaxLines(3);
                    binding.txtSeeMore.setText("Xem thêm");
                    binding.imvSeeMore.setImageResource(R.drawable.chevron_down);
                } else {
                    binding.txtProductDescription.setMaxLines(Integer.MAX_VALUE);
                    binding.txtSeeMore.setText("Thu gọn");
                    binding.imvSeeMore.setImageResource(R.drawable.chevron_up);
                }
                isExpanded[0] = !isExpanded[0];
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createThumbnails(product.getProductImage());
        showSimilarProducts(product, allProducts);
    }
    // Tạo Thumbnail các ảnh của sản phẩm
    private void createThumbnails(List<String> images) {
        if (images == null || images.isEmpty()) return;

        binding.layoutThumbnails.removeAllViews();

        for (String imageUrl : images) {
            ImageView thumbnail = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 120);
            params.setMargins(8, 0, 8, 0);
            thumbnail.setLayoutParams(params);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String fullUrl = getFullImageUrl(imageUrl);

            Glide.with(this)
                    .load(fullUrl)
                    .into(thumbnail);

            thumbnail.setOnClickListener(v -> loadMainImage(imageUrl));
            binding.layoutThumbnails.addView(thumbnail);
        }
    }
    // Load ảnh chính của sản phẩm
    private void loadMainImage(String imageUrl) {
        String fullUrl = getFullImageUrl(imageUrl);
        if (!fullUrl.isEmpty()) {
            Glide.with(this)
                    .load(fullUrl)
                    .into(binding.imvProduct);
        }
    }
    // Lấy link ảnh từ imgur
    private String getFullImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return "";

        if (imageUrl.contains("i.imgur.com")) {
            return imageUrl;
        } else if (imageUrl.contains("imgur.com")) {
            String imageId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            return "https://i.imgur.com/" + imageId + ".png";
        }
        return imageUrl;
    }
    // Xử lý hiển thị sản phẩm tương tự
    private void showSimilarProducts(Product product, List<Product> allProducts) {
        if (allProducts == null || product == null) return;

        List<Product> similarProducts = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getProductCategory().equals(product.getProductCategory()) && !p.getProductId().equals(product.getProductId())){
                similarProducts.add(p);
            }
        }

        if (!similarProducts.isEmpty()) {
            ProductAdapter adapter = new ProductAdapter(this, similarProducts);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
            binding.rvSimilarProducts.setLayoutManager(layoutManager);
            binding.rvSimilarProducts.setAdapter(adapter);
        }
    }

}
