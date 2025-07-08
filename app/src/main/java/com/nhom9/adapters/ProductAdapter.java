package com.nhom9.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom9.myapplication.ProductDetailActivity;
import com.nhom9.myapplication.R;
import com.nhom9.models.Product;
import com.nhom9.models.ProductAttribute;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvItemImage;
        TextView  txtItemPrice,txtItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvItemImage = itemView.findViewById(R.id.imvItemImage);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            txtItemName = itemView.findViewById(R.id.txtItemName);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        // Xử lý lấy ảnh từ Imgur
        List<String> images = product.getProductImage();
        if (images != null && !images.isEmpty()) {
            String raw = images.get(0);
            String imageUrl;

            if (raw.startsWith("http")) {
                if (raw.contains("imgur.com") && !raw.contains("i.imgur.com")) {
                    String[] parts = raw.split("/");
                    String id = parts[parts.length - 1];
                    imageUrl = "https://i.imgur.com/" + id + ".png";
                } else {
                    imageUrl = raw;
                }
            } else {
                imageUrl = "https://i.imgur.com/" + raw + ".png";
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imvItemImage);
        } else {
            holder.imvItemImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.txtItemName.setText(product.getProductName());

        // Xử lý lấy giá
        List<ProductAttribute> attributes = product.getAttributes();
        if (attributes != null && !attributes.isEmpty()) {
            double minPrice = attributes.get(0).getProductPrice();
            for (ProductAttribute attr : attributes) {
                double price = attr.getProductPrice();
                if (price < minPrice) minPrice = price;
            }
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            holder.txtItemPrice.setText(formatter.format(minPrice));
        } else {
            holder.txtItemPrice.setText("Giá liên hệ");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", product);
            intent.putExtra("allProducts", new ArrayList<>(productList));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
