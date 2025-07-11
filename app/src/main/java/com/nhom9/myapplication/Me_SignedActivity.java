package com.nhom9.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nhom9.adapters.AppointmentAdapter;
import com.nhom9.adapters.MyPetAdapter;
import com.nhom9.models.Appointment;
import com.nhom9.models.MyPet;
import com.nhom9.myapplication.databinding.ActivityMeSignedBinding;

import java.util.ArrayList;

public class Me_SignedActivity extends AppCompatActivity {

    ActivityMeSignedBinding binding;
    MyPetAdapter adapter;
    AppointmentAdapter adapterappointment;
    ArrayList<MyPet> pets;
    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeSignedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
        addEvents();

    }

    private void addEvents() {

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String phone = prefs.getString("phone", null);

        String displayName = (username != null && !username.trim().isEmpty() && !username.equals("Người dùng"))
                ? username
                : (phone != null ? phone : "Người dùng");

        binding.txtName.setText(displayName);

        // Nút back
        binding.imvBack.setOnClickListener(v -> finish());

        // Nút đăng xuất
        binding.btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Me_SignedActivity.this, MeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }


    private void loadData() {
        pets = new ArrayList<>();
        pets.add(new MyPet(R.drawable.sugo_dog, "Sú Gơ","7 tuổi" ));
        pets.add(new MyPet(R.drawable.mydieu_cat, "Mỹ Diệu","3 tuổi" ));
        pets.add(new MyPet(R.drawable.mynau_cat, "Mỹ Nâu","3 tuổi" ));

        adapter = new MyPetAdapter(Me_SignedActivity.this, R.layout.my_pets, pets);

        LinearLayoutManager manager = new LinearLayoutManager(Me_SignedActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvPets.setLayoutManager(manager);
        binding.rvPets.setAdapter(adapter);

        ////////////
        appointments = new ArrayList<>();
        appointments.add(new Appointment("Thứ hai, 7 tháng 7 - 5.00PM", "Cắt tỉa lông", "Sú Gơ"));
        appointments.add(new Appointment("Thứ năm, 10 tháng 7 - 3.00PM", "Tắm rửa", "Mỹ Diệu"));

        adapterappointment = new AppointmentAdapter(Me_SignedActivity.this,R.layout.appointments_list, appointments);
        binding.lvAppointments.setAdapter(adapterappointment);

    }
}