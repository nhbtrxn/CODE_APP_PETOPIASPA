package com.nhom9.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom9.myapplication.databinding.ActivityFaqBinding;

public class FAQActivity extends AppCompatActivity {

    ActivityFaqBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bấm nút quay lại
        binding.imvBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại
        });

        // Set click listeners for each question
        binding.question1.setOnClickListener(v -> toggleAnswer(binding.answerText1));
        binding.question2.setOnClickListener(v -> toggleAnswer(binding.answerText2));
        binding.question3.setOnClickListener(v -> toggleAnswer(binding.answerText3));
        binding.question4.setOnClickListener(v -> toggleAnswerForLocation(binding.answerText4, binding.btnFind));
        binding.question5.setOnClickListener(v -> toggleAnswer(binding.answerText5));
        binding.question6.setOnClickListener(v -> toggleAnswer(binding.answerText6));
        binding.question7.setOnClickListener(v -> toggleAnswer(binding.answerText7));
        binding.question8.setOnClickListener(v -> toggleAnswer(binding.answerText8));
        binding.question9.setOnClickListener(v -> toggleAnswer(binding.answerText9));
        binding.question10.setOnClickListener(v -> toggleAnswer(binding.answerText10));
        binding.question11.setOnClickListener(v -> toggleAnswer(binding.answerText11));
        binding.question12.setOnClickListener(v -> toggleAnswer(binding.answerText12));
        binding.question13.setOnClickListener(v -> toggleAnswer(binding.answerText13));
        binding.question14.setOnClickListener(v -> toggleAnswer(binding.answerText14));
        binding.question15.setOnClickListener(v -> toggleAnswer(binding.answerText15));
    }


    private void toggleAnswer(View answerView) {
        if (answerView.getVisibility() == View.GONE) {
            answerView.setVisibility(View.VISIBLE);
        } else {
            answerView.setVisibility(View.GONE);
        }
    }
    private void toggleAnswerForLocation(View answerView, View buttonView) {
        if (answerView.getVisibility() == View.GONE) {
            answerView.setVisibility(View.VISIBLE);
            buttonView.setVisibility(View.VISIBLE); // Hiển thị nút Tìm địa chỉ
        } else {
            answerView.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE); // Ẩn nút Tìm địa chỉ
        }
    }
}


