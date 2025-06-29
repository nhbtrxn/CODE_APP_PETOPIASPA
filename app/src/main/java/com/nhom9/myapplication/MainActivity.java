package com.nhom9.myapplication;

import android.os.Bundle;

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

//        // Gắn tab indicator
//        new TabLayoutMediator(binding.tabIndicator, binding.viewPager,
//                (tab, position) -> {
//                }
//        ).attach();

    }
}