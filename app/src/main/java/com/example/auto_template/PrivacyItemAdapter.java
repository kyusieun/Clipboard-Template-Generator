package com.example.auto_template;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_template.databinding.ListRecylerBinding;
import com.example.auto_template.databinding.TemplateRecyclerBinding;

import java.util.List;

public class PrivacyItemAdapter extends RecyclerView.Adapter<PrivacyItemAdapter.ViewHolder>{
    List<String> items;
    ListRecylerBinding binding;
    ViewGroup.LayoutParams itemLp;
    public void putItems(List<String>input){
        items = input;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListRecylerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Log.d("test5", parent.toString());
        itemLp = binding.listRecycler.getLayoutParams();
        itemLp.width = 1000;
        binding.listRecycler.setLayoutParams(itemLp);
        return new ViewHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ListRecylerBinding binding;
        private ViewHolder(ListRecylerBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setData(String item){
            binding.listRecyclerText.setText(item);
        }
    }
}
