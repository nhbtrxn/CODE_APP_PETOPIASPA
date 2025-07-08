package com.nhom9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.nhom9.adapters.ServiceAdapter;
import com.nhom9.models.Product;
import com.nhom9.models.Service;
import com.nhom9.models.ServiceAttribute;
import com.nhom9.myapplication.databinding.ActivityServiceDetailBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceDetailActivity extends AppCompatActivity {
    ActivityServiceDetailBinding binding;
    boolean[] isExpanded = {false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Service service = (Service) intent.getSerializableExtra("service");
        ArrayList<Service> allServices =  (ArrayList<Service>) getIntent().getSerializableExtra("allServices");
        if (service == null) {
            finish();
            return;
        }

        loadMainImage(service.getServiceImage().get(0));

        binding.txtServiceName.setText(service.getServiceName());
        binding.txtServiceDescription.setText(service.getServiceDetail());
        binding.txtServiceRate.setText(String.valueOf(service.getServiceRate()));
        binding.txtServiceNumRate.setText("(" + service.getServiceNumRate() + " Đánh giá)");
        binding.txtServiceNumUsed.setText(service.getServiceNumUsed() + "+ đã sử dụng");

        binding.lnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded[0]) {

                    binding.txtServiceDescription.setMaxLines(3);
                    binding.txtSeeMore.setText("Xem thêm");
                    binding.imvSeeMore.setImageResource(R.drawable.chevron_down);
                } else {
                    binding.txtServiceDescription.setMaxLines(Integer.MAX_VALUE);
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

        createThumbnails(service.getServiceImage());
        showServiceOptions(service);
        showSimilarServices(service, allServices);

    }
    // Xử lý tạo thumbnail cho dịch vụ
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
    // Load hiện ảnh chính của dịch vụ
    private void loadMainImage(String imageUrl) {
        String fullUrl = getFullImageUrl(imageUrl);
        if (!fullUrl.isEmpty()) {
            Glide.with(this)
                    .load(fullUrl)
                    .into(binding.imvService);
        }
    }
    // Lấy Url của ảnh từ imgur
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
    // Xử lý các option theo giá của dịch vụ (ví dụ: cân nặng từ x - y: giá dịch vụ là bao nhiêu
    // nhuộm xyz: giá dịch vụ bao nhiêu)
    private void showServiceOptions(Service service) {
        binding.lnOptionLayout.removeAllViews();
        binding.lnOptionDyeLayout.removeAllViews();

        if (service.getAttributes() == null || service.getAttributes().isEmpty()) {
            binding.lnOptionLayout.setVisibility(View.GONE);
            binding.lnOptionDyeLayout.setVisibility(View.GONE);
            return;
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        boolean hasWeightOption = false;
        boolean hasDyeOption = false;

        for (ServiceAttribute attr : service.getAttributes()) {
            if (attr.getDyeOption() != null && !attr.getDyeOption().isEmpty()) {
                hasDyeOption = true;
                String label = attr.getDyeOption();
                Double price = attr.getMinPrice();

                Button btn = new Button(this);
                btn.setText(label);
                btn.setTextSize(13f);
                btn.setPadding(10, 10, 10, 10);
                btn.setBackgroundResource(R.drawable.option_background);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 5, 0, 5);
                btn.setLayoutParams(params);

                btn.setMaxLines(1);
                btn.setEllipsize(TextUtils.TruncateAt.END);

                btn.setOnClickListener(v -> {
                    binding.txtServicePrice.setText(currencyFormat.format(price));
                    highlightOption(btn);
                });

                binding.lnOptionDyeLayout.addView(btn);

            } else {

                String label = buildLabel(attr);
                if (label == null) continue;

                hasWeightOption = true;
                Double price = attr.getMinPrice();

                Button btn = new Button(this);
                btn.setText(label);
                btn.setTextSize(13f);
                btn.setPadding(30, 10, 30, 10);
                btn.setBackgroundResource(R.drawable.option_background);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 5, 0);
                btn.setLayoutParams(params);

                btn.setOnClickListener(v -> {
                    binding.txtServicePrice.setText(currencyFormat.format(price));
                    highlightOption(btn);
                });

                binding.lnOptionLayout.addView(btn);
            }
        }

        binding.lnOptionLayout.setVisibility(hasWeightOption ? View.VISIBLE : View.GONE);
        binding.lnOptionDyeLayout.setVisibility(hasDyeOption ? View.VISIBLE : View.GONE);

        if (hasWeightOption && binding.lnOptionLayout.getChildCount() > 0) {
            ((Button) binding.lnOptionLayout.getChildAt(0)).performClick();
        }
        if (hasDyeOption && binding.lnOptionDyeLayout.getChildCount() > 0) {
            ((Button) binding.lnOptionDyeLayout.getChildAt(0)).performClick();
        }
    }
    // Tạo nhãn cho các nút Option
    private String buildLabel(ServiceAttribute attr) {
        Double min = attr.getMinSize();
        Double max = attr.getMaxSize();
        String unit = attr.getUnit();

        if (min != null && max != null) {
            return min + "–" + max + unit;
        } else if (min != null) {
            return "> " + min + unit;
        } else if (max != null) {
            return "< " + max + unit ;
        } else {
            return null;
        }
    }
    // Xử lý highlight nút khi nhấn chọn và không nhấn chọn
    private void highlightOption(Button selectedBtn) {
        for (int i = 0; i < binding.lnOptionLayout.getChildCount(); i++) {
            View child = binding.lnOptionLayout.getChildAt(i);
            if (child instanceof Button) {
                child.setBackgroundResource(R.drawable.option_background);
                ((Button) child).setTextColor(ContextCompat.getColor(this,android.R.color.black));
            }
        }
        for (int i = 0; i < binding.lnOptionDyeLayout.getChildCount(); i++) {
            View child = binding.lnOptionDyeLayout.getChildAt(i);
            if (child instanceof Button) {
                child.setBackgroundResource(R.drawable.option_background);
                ((Button) child).setTextColor(ContextCompat.getColor(this, android.R.color.black));
            }
        }
        selectedBtn.setBackgroundResource(R.drawable.option_selected_background);
        selectedBtn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }
    // Xử lý hiện thị dịch vụ tương tự
    private void showSimilarServices(Service currentService, List<Service> allServices) {
        if (allServices == null || currentService == null) return;

        List<Service> similarServices = new ArrayList<>();
        for (Service s : allServices) {
            if (!s.getServiceId().equals(currentService.getServiceId())) {
                similarServices.add(s);
            }
        }

        if (!similarServices.isEmpty()) {
            ServiceAdapter adapter = new ServiceAdapter(this, similarServices);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
            binding.rvSimilarServices.setLayoutManager(layoutManager);
            binding.rvSimilarServices.setAdapter(adapter);
        }
    }

}