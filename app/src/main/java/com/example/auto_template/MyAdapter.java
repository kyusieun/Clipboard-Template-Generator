package com.example.auto_template;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.core.content.ContextCompat.startForegroundService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.company.auto_template.databinding.TemplateRecyclerBinding;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    List<Template> items = new ArrayList<>();
    TemplateRecyclerBinding binding;
    Context context;
    Intent toTemplateEditorIntent;
    public MyAdapter(Context context){this.context = context;}
    public void add(Template data){
        items.add(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("viewholder", "oncreateView");
        binding = TemplateRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
        //클릭 시마다 인텐트를 생성하도록 하는게 맞을 수는 있지만 보류
        toTemplateEditorIntent = new Intent(context, TemplateEditor.class);
        binding.recyclerEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(context, toTemplateEditorIntent, new Bundle());
//                Toast.makeText(itemView.getContext(),binding.recyclerTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TemplateRecyclerBinding binding;

        public ViewHolder(TemplateRecyclerBinding binding){
            super(binding.getRoot());
            //MyAdapter가 아니라 ViewHolder의 생성자 호출
            //원래 View를 전달하는 거였음.
           this.binding = binding;
        }
        public void setData(Template data){
            binding.recyclerTitle.setText(data.title);
            binding.recyclerMain.setText(data.main);
        }
    }

}
