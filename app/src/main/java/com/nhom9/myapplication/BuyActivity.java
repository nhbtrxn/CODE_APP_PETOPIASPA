package com.nhom9.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nhom9.fragment.ProductFragment;
import com.nhom9.fragment.ServiceFragment;
import com.nhom9.myapplication.databinding.ActivityBuyBinding;

public class BuyActivity extends AppCompatActivity {
    ActivityBuyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnService.setSelected(true);
        binding.btnProduct.setSelected(false);
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), new ServiceFragment())
                .commit();

        binding.btnService.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentContainer.getId(), new ServiceFragment())
                    .commit();
            binding.btnService.setSelected(true);
            binding.btnProduct.setSelected(false);
        });

        binding.btnProduct.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentContainer.getId(), new ProductFragment())
                    .commit();

            binding.btnService.setSelected(false);
            binding.btnProduct.setSelected(true);
        });

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_main) {
                // Xử lý trang MainActivity
                Intent intent = new Intent(BuyActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_me) {
                // Xử lý trang MeActivity
                Intent intent = new Intent(BuyActivity.this, MeActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

    }
}