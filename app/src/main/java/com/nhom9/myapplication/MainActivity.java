package com.nhom9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayoutMediator;
import com.nhom9.adapters.SliderAdapter;
import com.nhom9.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int[] imageList = {
            R.drawable.connect,
            R.drawable.forum,
            R.drawable.blog
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        SliderAdapter adapter = new SliderAdapter(this, imageList);
        binding.viewPager.setAdapter(adapter);

        // Xử lý khi nhấn vào nút Chính sách
        binding.btnPolicy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PolicyActivity.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            startActivity(intent);
        });

        // Xử lý khi nhấn vào nút Liên hệ
        binding.btnContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            startActivity(intent);
        });

        // Xử lý khi nhấn vào nút hỏi đáp
        binding.btnAsking.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, QaAActivity.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            startActivity(intent);
        });
        // Xử lý khi nhấn vào nút mua sắm

        binding.btnShopping.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BuyActivity.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            startActivity(intent);
        });
        //
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // xử lý trang chủ
                return true;
            } else if (id == R.id.nav_booking) {
                // xử lý trang đặt lịch
                return true;
            } else if (id == R.id.nav_me) {
                // Xử lý trang MeActivity
                Intent intent = new Intent(MainActivity.this, MeActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });
//        // Gắn tab indicator
//        new TabLayoutMediator(binding.tabIndicator, binding.viewPager,
//                (tab, position) -> {
//                }
//        ).attach();
        TextView tvUsernameGreeting = findViewById(R.id.tvUsernameGreeting);
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            tvUsernameGreeting.setText("Xin chào, " + username + "!");
        } else {
            tvUsernameGreeting.setVisibility(View.GONE); // Ẩn nếu không có username
        }


    }
}