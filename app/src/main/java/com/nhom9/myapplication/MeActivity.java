package com.nhom9.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom9.myapplication.databinding.ActivityMeBinding;
import com.nhom9.myapplication.databinding.ActivityMeSignedBinding;

public class MeActivity extends AppCompatActivity {

    ActivityMeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
}

    private void addEvents() {

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String phone = sharedPreferences.getString("phone", null);

        boolean isLoggedIn = (username != null && !username.isEmpty() && !username.equals("Người dùng"))
                || (phone != null && !phone.isEmpty());

        if (isLoggedIn) {
            // Nếu đã đăng nhập thì chuyển sang Me_SignedActivity
            Intent intent = new Intent(MeActivity.this, Me_SignedActivity.class);
            startActivity(intent);
            finish(); // Đóng MeActivity luôn
            return;
        }

        // Nếu chưa đăng nhập thì hiển thị giao diện hiện tại
        binding = ActivityMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvGreeting.setVisibility(View.GONE);
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.btnRegister.setVisibility(View.VISIBLE);

        // Nút login
        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MeActivity.this, LoginActivity.class));
        });

        // Nút register
        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(MeActivity.this, RegisterActivity.class));
        });

    }
}
