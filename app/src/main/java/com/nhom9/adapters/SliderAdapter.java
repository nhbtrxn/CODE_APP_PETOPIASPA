package com.nhom9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom9.myapplication.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    Context context;
    int[] images;

    public SliderAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.imageButton.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }
}
