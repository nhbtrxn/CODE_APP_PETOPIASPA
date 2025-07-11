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
import com.nhom9.models.User;
import com.nhom9.myapplication.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Chuyển về màn hình đăng nhập
        binding.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Mặc định ẩn lỗi
        binding.tvErrorPhone.setVisibility(View.GONE);
        binding.tvErrorEmail.setVisibility(View.GONE);
        binding.tvErrorPassword.setVisibility(View.GONE);

        binding.btnRegister.setOnClickListener(v -> {
            String phone = binding.edtPhone.getText().toString().trim();
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();

            boolean isValid = true;

            // Kiểm tra SĐT
            if (!isValidPhone(phone)) {
                binding.tvErrorPhone.setVisibility(View.VISIBLE);
                binding.edtPhone.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorPhone.setVisibility(View.GONE);
                binding.edtPhone.setBackgroundResource(R.drawable.border_brown_white);
            }

            // Kiểm tra Email
            if (!isValidEmail(email)) {
                binding.tvErrorEmail.setVisibility(View.VISIBLE);
                binding.edtEmail.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorEmail.setVisibility(View.GONE);
                binding.edtEmail.setBackgroundResource(R.drawable.border_brown_white);
            }

            // Kiểm tra mật khẩu
            if (!isValidPassword(password)) {
                binding.tvErrorPassword.setVisibility(View.VISIBLE);
                binding.edtPassword.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorPassword.setVisibility(View.GONE);
                binding.edtPassword.setBackgroundResource(R.drawable.border_brown_white);
            }

            if (isValid) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                User newUser = new User();
                newUser.setPhone(phone);
                newUser.setEmail(email);
                newUser.setPassword(password);

                Call<User> call = apiService.register(newUser);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                            // Lấy đối tượng user từ response
                            User user = response.body();

                            String username = user.getUsername();
                            if (username == null || username.isEmpty()) {
                                username = user.getPhone();
                            }
                            // Lưu username và phone vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.putString("phone", user.getPhone()); //
                            editor.apply();


                            // Chuyển thẳng sang MainActivity và truyền username
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("API_REGISTER", "Lỗi kết nối: ", t);
                        Toast.makeText(RegisterActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^0\\d{9}$");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,}$";
        return password.matches(pattern);
    }
}
