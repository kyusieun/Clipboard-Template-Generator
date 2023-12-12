package com.example.auto_template;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.auto_template.databinding.TemplateRecyclerBinding;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    ArrayList<Template> items;
    TemplateRecyclerBinding binding;
    Context context;
    ViewGroup.LayoutParams itemLp;
    public MyAdapter(Context context){
        this.context = context;
        Log.d("fff", context.toString());
    }
    public void addItems(ArrayList<Template> inputItems){
        this.items = inputItems;
        Log.d("ffff", items.toString());
        notifyDataSetChanged();
    }
    public Template getItem(int position){
        return items.get(position);
    }
    public ArrayList<Template> getItems(){
        return items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TemplateRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()));
        itemLp = binding.recyclerCardView.getLayoutParams();
            itemLp.width = (int)(parent.getWidth()*0.9);
        binding.recyclerCardView.setLayoutParams(itemLp);
        return new ViewHolder(binding, context);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
        Log.d("LOGINBB", String.valueOf(items.get(position).hashCode()));
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


    class ViewHolder extends RecyclerView.ViewHolder{
    private TemplateRecyclerBinding binding;
        Intent toTemplateEditorIntent;

        Intent toTemplateUseIntent;
        Template currentData;
        private ViewHolder(TemplateRecyclerBinding binding, Context context){
            super(binding.getRoot());
            this.binding = binding;
            binding.recyclerEditBtn.setOnClickListener(view -> {
                toTemplateEditorIntent = new Intent(context, TemplateEditor.class);
                toTemplateEditorIntent
                        .putExtra("selected_template", currentData)
                        .putExtra("selected_item_position", getAbsoluteAdapterPosition());
                startActivity(context, toTemplateEditorIntent, null);
            });

            binding.recyclerCardView.setOnClickListener(view -> {
                // editBtn과 중복 방지
                if (!binding.recyclerEditBtn.isPressed()) {
                    toTemplateUseIntent = new Intent(context, TemplateUse.class);
                    toTemplateUseIntent
                            .putExtra("selected_template", currentData)
                            .putExtra("selected_item_position", getAbsoluteAdapterPosition());
                    startActivity(context, toTemplateUseIntent, null);
                }
            });

        }
        public void setData(Template data){
            currentData = data;
            binding.recyclerTitle.setText(currentData.title);
            binding.recyclerMain.setText(currentData.content);
            binding.recyclerLastEditText.setText(currentData.last_edit.toDate().toString());
        }
        private String getClasses(){
            return currentData.title.getClass().toString();
        }
    }

}
