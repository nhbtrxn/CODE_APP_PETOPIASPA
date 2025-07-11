package com.nhom9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom9.models.MyPet;
import com.nhom9.myapplication.R;

import java.util.ArrayList;
public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.ViewHolder> {
    Context context;
    int pet_layout;
    ArrayList<MyPet> pets;

    //constructor


    public MyPetAdapter(Context context, int pet_layout, ArrayList<MyPet> pets) {
        this.context = context;
        this.pet_layout = pet_layout;
        this.pets = pets;
    }

//    public MyPetAdapter(Context context, ArrayList<MyPet> pets) {
//        this.context = context;
//        this.pets = pets;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_pets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyPet p = pets.get(position);
        holder.imvPetPhoto.setImageResource(p.getPhoto());
        holder.txtPetName.setText(p.getName());
        holder.txtPetAge.setText(p.getAge());
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvPetPhoto;
        TextView txtPetName, txtPetAge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvPetPhoto = itemView.findViewById(R.id.imvPetPhoto);
            txtPetName = itemView.findViewById(R.id.txtPetName);
            txtPetAge = itemView.findViewById(R.id.txtPetAge);
        }
    }
}
