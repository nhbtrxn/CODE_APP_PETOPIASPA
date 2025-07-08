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
import com.nhom9.myapplication.R;
import com.nhom9.models.Service;
import com.nhom9.models.ServiceAttribute;
import com.nhom9.myapplication.ServiceDetailActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    Context context;
    List<Service> serviceList;

    public ServiceAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvItemImage;
        TextView txtItemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvItemImage = itemView.findViewById(R.id.imvItemImage);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
        }
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        Service service = serviceList.get(position);

        // Xử lý lấy ảnh từ Imgur
        List<String> images = service.getServiceImage();
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

        // Xử lý giá
        List<ServiceAttribute> attributes = service.getAttributes();
        if (attributes != null && !attributes.isEmpty()) {
            Double minPrice = null;
            Double maxPrice = null;

            for (ServiceAttribute attribute : attributes) {
                if (attribute.getMinPrice() != null) {
                    minPrice = (minPrice == null) ? attribute.getMinPrice() : Math.min(minPrice, attribute.getMinPrice());
                }
                if (attribute.getMaxPrice() != null) {
                    maxPrice = (maxPrice == null) ? attribute.getMaxPrice() : Math.max(maxPrice, attribute.getMaxPrice());
                }
            }

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

            if (minPrice != null && maxPrice != null) {
                if (minPrice.equals(maxPrice)) {
                    holder.txtItemPrice.setText(currencyFormat.format(minPrice));
                } else {
                    String minStr = (minPrice == Math.floor(minPrice))
                            ? numberFormat.format(minPrice.longValue())
                            : currencyFormat.format(minPrice);

                    String maxStr = currencyFormat.format(maxPrice);

                    holder.txtItemPrice.setText(minStr + " - " + maxStr);
                }
            } else {
                holder.txtItemPrice.setText("Giá liên hệ");
            }
        } else {
            holder.txtItemPrice.setText("Giá liên hệ");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceDetailActivity.class);
            intent.putExtra("service", service);
            intent.putExtra("allServices", new ArrayList<>(serviceList));
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return serviceList.size();
    }
}