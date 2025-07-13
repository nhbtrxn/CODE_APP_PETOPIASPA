package com.nhom9.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom9.api.ApiClient;
import com.nhom9.api.ApiService;
import com.nhom9.myapplication.databinding.ActivityResetPasswordBinding;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    private boolean isOtpSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideErrorMessages();
        hideOtpSection();

        // Nút quay lại
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish(); // Đóng ResetPasswordActivity để không quay lại được bằng nút Back
        });

        // Gửi lại mã
        binding.tvResendOTP.setOnClickListener(view -> sendOtp());

        // Gửi mã hoặc xác nhận OTP
        binding.btnResetPassword.setOnClickListener(view -> {
            if (!isOtpSent) {
                if (validateInputs()) {
                    sendOtp();
                }
            } else {
                confirmResetPassword();
            }
        });
    }

    private void hideErrorMessages() {
        binding.tvErrorEmail.setVisibility(View.GONE);
        binding.tvErrorNewPassword.setVisibility(View.GONE);
        binding.tvErrorConfirmPassword.setVisibility(View.GONE);
    }

    private void hideOtpSection() {
        binding.tvOTPLabel.setVisibility(View.GONE);
        binding.edtOTP.setVisibility(View.GONE);
        binding.tvResendOTP.setVisibility(View.GONE);
    }

    private boolean validateInputs() {
        String username = binding.edtUsername.getText().toString().trim();
        String newPassword = binding.edtNewPassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
        boolean isValid = true;

        if (!isValidEmailOrPhone(username)) {
            binding.tvErrorEmail.setVisibility(View.VISIBLE);
            binding.tvErrorEmail.setText("Email hoặc số điện thoại không hợp lệ");
            binding.edtUsername.setBackgroundResource(R.drawable.register_border_error);
            isValid = false;
        } else {
            binding.tvErrorEmail.setVisibility(View.GONE);
            binding.edtUsername.setBackgroundResource(R.drawable.border_e3ccbd_ffffff);
        }

        if (!isValidPassword(newPassword)) {
            binding.tvErrorNewPassword.setVisibility(View.VISIBLE);
            binding.tvErrorNewPassword.setText("Mật khẩu phải từ 6 ký tự, có chữ hoa, số, ký tự đặc biệt");
            binding.edtNewPassword.setBackgroundResource(R.drawable.register_border_error);
            isValid = false;
        } else {
            binding.tvErrorNewPassword.setVisibility(View.GONE);
            binding.edtNewPassword.setBackgroundResource(R.drawable.border_e3ccbd_ffffff);
        }

        if (!newPassword.equals(confirmPassword)) {
            binding.tvErrorConfirmPassword.setVisibility(View.VISIBLE);
            binding.tvErrorConfirmPassword.setText("Mật khẩu không khớp");
            binding.edtConfirmPassword.setBackgroundResource(R.drawable.register_border_error);
            isValid = false;
        } else {
            binding.tvErrorConfirmPassword.setVisibility(View.GONE);
            binding.edtConfirmPassword.setBackgroundResource(R.drawable.border_e3ccbd_ffffff);
        }

        return isValid;
    }

    private void sendOtp() {
        String email = binding.edtUsername.getText().toString().trim();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        apiService.sendOtp(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isOtpSent = true;
                    Toast.makeText(ResetPasswordActivity.this, "Đã gửi mã xác nhận!", Toast.LENGTH_SHORT).show();

                    // Hiển thị phần nhập OTP
                    binding.tvOTPLabel.setVisibility(View.VISIBLE);
                    binding.edtOTP.setVisibility(View.VISIBLE);
                    binding.tvResendOTP.setVisibility(View.VISIBLE);
                    binding.btnResetPassword.setText("Xác nhận");
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Không gửi được mã xác nhận!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void confirmResetPassword() {
        String email = binding.edtUsername.getText().toString().trim();
        String newPassword = binding.edtNewPassword.getText().toString().trim();
        String otp = binding.edtOTP.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã xác nhận!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("newPassword", newPassword);
        body.put("otp", otp);

        apiService.confirmResetPassword(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Mã xác nhận sai hoặc hết hạn!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Lỗi hệ thống: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isValidEmailOrPhone(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches() || input.matches("^0\\d{9}$");
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,}$";
        return password.matches(pattern);
    }
}
