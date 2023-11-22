package com.example.auto_template;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
    ViewGroup.LayoutParams itemLp;
    public MyAdapter(Context context){this.context = context;}
    public void add(Template data){
        items.add(data);
        notifyDataSetChanged();
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
        Intent toTemplateEditorIntent;
        Template currentData;

        public ViewHolder(TemplateRecyclerBinding binding, Context context){
            super(binding.getRoot());
            //MyAdapter가 아니라 ViewHolder의 생성자 호출
            //원래 View를 전달하는 거였음.
           this.binding = binding;
            binding.recyclerEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toTemplateEditorIntent = new Intent(context, TemplateEditor.class);
                    toTemplateEditorIntent.putExtra("template_item", currentData);
                    startActivity(context, toTemplateEditorIntent, null);
//                Toast.makeText(itemView.getContext(),binding.recyclerTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        public void setData(Template data){
            currentData = data;
            binding.recyclerTitle.setText(currentData.template_name);
            binding.recyclerMain.setText(currentData.template_content);
        }
    }

}
