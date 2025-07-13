package com.nhom9.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

import com.nhom9.myapplication.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding binding;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Xử lý khi nhấn vào nút Gửi tin nhắn
        binding.imvIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, IntroductionActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý khi nhấn vào nút Gửi tin nhắn
        binding.imvMessage.setOnClickListener(view -> {
            Intent intent = new Intent(ContactActivity.this, SendMessageActivity.class);
            startActivity(intent);
        });

        // Xử lý khi nhấn vào nút câu hỏi
        binding.imvHelp.setOnClickListener(view -> {
            Intent intent = new Intent(ContactActivity.this, FAQActivity.class);
            startActivity(intent);
        });


        binding.imvPhonecall.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_contact_options, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(true);


            View phoneOption = dialogView.findViewById(R.id.phoneOption);

            phoneOption.setOnClickListener(v -> {
                makePhoneCall();
                dialog.dismiss();
            });


            dialog.show();
        });

        binding.imvBack.setOnClickListener(v -> {
            finish();
        });
    }
    // Hàm gọi điện thoại
    private void makePhoneCall() {
        String phoneNumber = "tel:0999999999";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phoneNumber));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để gọi điện", Toast.LENGTH_SHORT).show();
            }
        }
    }
}