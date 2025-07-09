package com.nhom9.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom9.myapplication.databinding.ActivityMeBinding;

public class MeActivity extends AppCompatActivity {

    ActivityMeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String phone = sharedPreferences.getString("phone", null);

        boolean isLoggedIn = (username != null && !username.isEmpty() && !username.equals("Người dùng"))
                || (phone != null && !phone.isEmpty());

        if (isLoggedIn) {
            String displayName = (username != null && !username.isEmpty() && !username.equals("Người dùng"))
                    ? username : phone;

            binding.tvGreeting.setText("Xin chào, " + displayName + "!");
            binding.tvGreeting.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnRegister.setVisibility(View.GONE);
        } else {
            binding.tvGreeting.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.VISIBLE);
        }



        // Kiểm tra trạng thái đăng nhập
        if (username != null || phone != null) {
            // Ưu tiên username
            String displayName = (username != null && !username.isEmpty()) ? username : phone;

            // Hiển thị lời chào
            binding.tvGreeting.setText("Xin chào, " + displayName + "!");
            binding.tvGreeting.setVisibility(View.VISIBLE);
            // Sau khi hiển thị lời chào:
            binding.btnLogout.setVisibility(View.VISIBLE);
            binding.btnLogout.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Xoá tất cả
                editor.apply();
                recreate(); // Tải lại activity
            });


            // Ẩn nút Đăng nhập & Đăng ký
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnRegister.setVisibility(View.GONE);
        } else {
            // Chưa đăng nhập → hiện 2 nút
            binding.tvGreeting.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.VISIBLE);
        }

        // Xử lý click nút
        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MeActivity.this, LoginActivity.class));
        });

        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(MeActivity.this, RegisterActivity.class));
        });
    }
}
