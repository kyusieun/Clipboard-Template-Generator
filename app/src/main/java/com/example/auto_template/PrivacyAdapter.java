package com.example.auto_template;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_template.databinding.ListHeaderBinding;
import com.example.auto_template.databinding.TemplateRecyclerBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrivacyAdapter extends RecyclerView.Adapter<PrivacyAdapter.ViewHolder> {
    ArrayList<Privacy> items = new ArrayList<>();
    boolean hasEmptyItem = false;
    ListHeaderBinding binding;
    Context context;
    ViewGroup.LayoutParams itemLp;
    public PrivacyAdapter(Context context){
        this.context = context;
    }
    public ArrayList<Privacy> getItems(){
        return items;
    }
    public void putItem(String inputStr, List<String> inputList){
        items.add(new Privacy(inputStr, inputList));
        notifyDataSetChanged();
    }
    public void putEmptyItem(){
        items.add(new Privacy("빈 키워드", List.of("빈 아이템")));
        notifyItemInserted(items.size()-1);
        notifyDataSetChanged();
        hasEmptyItem = true;

    }
    public boolean hasEmptyItem(){return hasEmptyItem;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListHeaderBinding.inflate(LayoutInflater.from(parent.getContext()));
        Log.d("test", "onCreate 실행");
        itemLp = binding.headerTitle.getLayoutParams();
        itemLp.width = (int)(parent.getWidth()*0.9);
        binding.headerTitle.setLayoutParams(itemLp);
        return new ViewHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrivacyItemAdapter adapter = new PrivacyItemAdapter();
        //포지션으로 할려면 어쩔 수 없이 배열을 써야 함.
        holder.setData(items.get(position));
        adapter.putItems(items.get(position).contents);
        binding.headerContents.setLayoutManager(new LinearLayoutManager(this.context));
        Log.d("test67", String.valueOf(binding.headerTitle.getWidth()));
        Log.d("test66", String.valueOf(binding.headerContents.getWidth()));

        binding.headerContents.setAdapter(adapter);


        Log.d("test3", holder.itemView.toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ListHeaderBinding binding;
        Context context;
        PrivacyItemAdapter adapter;
        private ViewHolder(ListHeaderBinding binding, Context context){
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;


            //
        }
        public void setData(Privacy item){
            binding.headerTitle.setText(item.header);

//            Log.d("test5", binding.headerContents.toString());
        }
    }

}
