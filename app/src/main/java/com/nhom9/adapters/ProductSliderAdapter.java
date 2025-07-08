package com.nhom9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSliderAdapter extends RecyclerView.Adapter<ProductSliderAdapter.ProductViewHolder> {

    LayoutInflater inflater;
    List<Integer> layoutIds;

    public ProductSliderAdapter(Context context, List<Integer> layoutIds) {
        this.inflater = LayoutInflater.from(context);
        this.layoutIds = layoutIds;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(layoutIds.get(viewType), parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return layoutIds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
