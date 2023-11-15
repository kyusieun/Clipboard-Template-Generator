package com.example.auto_template;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.company.auto_template.R;
import com.company.auto_template.databinding.ActivityMainBinding;
import com.google.android.material.search.SearchView;


public class MainActivity extends AppCompatActivity {
    String titles[] = {"안녕하세요", "Hello", "제목1", "제목2", "제목3"};
    String mains[] = {"안녕하세요", "Hello", "본몬1", "본문2", "본문3"};

    MyAdapter myAdapter = new MyAdapter();
    public void makeData(){
        for(int i = 0; i<5; i++) {
            Template item = new Template(titles[i], mains[i]);
            myAdapter.add(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        binding.recyclerView.setAdapter(myAdapter);
        //샘플 데이터 적용
        makeData();

        binding.addFab.setOnClickListener(view -> {
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        });
        binding.filterBtn.setOnClickListener(view -> {
            showPopup(this.findViewById(R.id.myToolbar));
        });
        binding.searchBtn.setOnClickListener(view -> {
            binding.searchView.setVisibility(View.VISIBLE);
        });
        setContentView(binding.getRoot());
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sort_menu, popup.getMenu());
        popup.show();
    }
    @Override
    public void onBackPressed(){
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setVisibility(searchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        super.onBackPressed();
    }
}
