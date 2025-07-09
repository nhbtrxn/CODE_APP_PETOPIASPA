package com.nhom9.myapplication;

import android.content.Intent;
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

        binding.linkRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        binding.forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });




        // Ẩn lỗi ban đầu
        binding.tvErrorEmailPhone.setVisibility(View.GONE);
        binding.tvErrorPassword.setVisibility(View.GONE);

        binding.btnLogin.setOnClickListener(v -> {
            String input = binding.emailPhoneInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();
            boolean isValid = true;

            //  Kiểm tra frontend
            if (input.isEmpty()) {
                binding.tvErrorEmailPhone.setText("Hãy nhập thông tin Email hoặc SĐT.");
                binding.tvErrorEmailPhone.setVisibility(View.VISIBLE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else if (!isValidEmailOrPhone(input)) {
                binding.tvErrorEmailPhone.setText("Email hoặc số điện thoại không đúng!");
                binding.tvErrorEmailPhone.setVisibility(View.VISIBLE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.register_border_error);
                isValid = false;
            } else {
                binding.tvErrorEmailPhone.setVisibility(View.GONE);
                binding.emailPhoneInput.setBackgroundResource(R.drawable.border_brown_white);
            }

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

            //  Gửi request lên server
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
                        String message = json.optString("message", "Không rõ lỗi");

                        if (response.isSuccessful()) {
                            JSONObject userJson = json.getJSONObject("user");

                            // Ưu tiên username, nếu trống thì lấy phone
                            String username = userJson.optString("username", "");
                            if (username.isEmpty()) {
                                username = userJson.optString("phone", "Người dùng");
                            }

                            // Chuyển sang MainActivity và truyền username
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        } else {
                            //  Hiển thị lỗi ngay dưới ô nhập
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
                        Toast.makeText(LoginActivity.this, "Lỗi khi xử lý phản hồi!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("LOGIN_FAILURE", "Lỗi kết nối server", t);
                    Toast.makeText(LoginActivity.this, "Không thể kết nối tới máy chủ!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    //  Check định dạng email hoặc SĐT Việt Nam
    private boolean isValidEmailOrPhone(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches() || input.matches("^0\\d{9}$");
    }
}
