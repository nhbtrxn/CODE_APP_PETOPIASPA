package com.nhom9.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom9.api.ApiClient;
import com.nhom9.api.ApiService;
import com.nhom9.models.LoginRequest;
import com.nhom9.myapplication.databinding.ActivityLoginBinding;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Chuyển sang trang đăng ký
        binding.linkRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Chuyển sang trang quên mật khẩu
        binding.forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });

        // Ẩn lỗi ban đầu
        binding.tvErrorEmailPhone.setVisibility(View.GONE);
        binding.tvErrorPassword.setVisibility(View.GONE);

        // Xử lý khi nhấn nút đăng nhập
        binding.btnLogin.setOnClickListener(v -> {
            String input = binding.emailPhoneInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();
            boolean isValid = true;

            // Kiểm tra input email hoặc SĐT
            if (input.isEmpty()) {
                binding.tvErrorEmailPhone.setText("Hãy nhập Email hoặc SĐT.");
                binding.tvErrorEmailPhone.setVisibility(View.VISIBLE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else if (!isValidEmailOrPhone(input)) {
                binding.tvErrorEmailPhone.setText("Email hoặc số điện thoại không hợp lệ!");
                binding.tvErrorEmailPhone.setVisibility(View.VISIBLE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorEmailPhone.setVisibility(View.GONE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.border_brown_white);
            }

            // Kiểm tra mật khẩu
            if (password.isEmpty()) {
                binding.tvErrorPassword.setText("Hãy nhập mật khẩu.");
                binding.tvErrorPassword.setVisibility(View.VISIBLE);
                binding.passwordInput.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorPassword.setVisibility(View.GONE);
                binding.passwordInput.setBackgroundResource(R.drawable.border_brown_white);
            }

            if (!isValid) return;

            // Gửi request login
            LoginRequest loginRequest = new LoginRequest(input, password);
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ResponseBody> call = apiService.login(loginRequest);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String body = response.isSuccessful() ?
                                response.body().string() : response.errorBody().string();

                        JSONObject json = new JSONObject(body);
                        String message = json.optString("message", "Có lỗi xảy ra");

                        if (response.isSuccessful()) {
                            JSONObject userJson = json.getJSONObject("user");

                            String username = userJson.optString("username", "");
                            String phone = userJson.optString("phone", "");

                            // Dùng phone khi không có username
                            String displayName = !username.isEmpty() ? username : phone;

                            // Lưu thông tin vào SharedPreferences
                            if (!displayName.isEmpty()) {
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", username);
                                editor.putString("phone", phone);
                                editor.apply();
                            }

                            // Chuyển sang MainActivity và truyền username
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", displayName);
                            startActivity(intent);
                            finish();
                        } else {
                            // Hiển thị lỗi cụ thể
                            if (message.toLowerCase().contains("mật khẩu")) {
                                binding.tvErrorPassword.setText(message);
                                binding.tvErrorPassword.setVisibility(View.VISIBLE);
                                binding.passwordInput.setBackgroundResource(R.drawable.register_border_error);
                            } else {
                                binding.tvErrorEmailPhone.setText(message);
                                binding.tvErrorEmailPhone.setVisibility(View.VISIBLE);
                                binding.emailPhoneInput.setBackgroundResource(R.drawable.register_border_error);
                            }
                        }

                    } catch (Exception e) {
                        Log.e("LOGIN_ERROR", "Lỗi xử lý JSON", e);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("LOGIN_FAILURE", "Lỗi kết nối", t);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Hàm kiểm tra định dạng email hoặc SĐT
    private boolean isValidEmailOrPhone(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches() || input.matches("^0\\d{9}$");
    }
}
