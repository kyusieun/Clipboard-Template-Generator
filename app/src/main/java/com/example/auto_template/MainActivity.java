package com.example.auto_template;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_template.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.search.SearchView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent toTemplateEditorIntent;
    Gson gson = new Gson();
    MyAdapter myAdapter = new MyAdapter(this);
    ArrayList<Template> items = new ArrayList<Template>();
    Template tempTemp;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int itemView = (int)(parent.getWidth()*0.9);
                int parentView = parent.getWidth();
                int margin = (parentView - itemView)/2;
                outRect.set(margin, 0, margin, 0);
            }
        });


        binding.addFab.setOnClickListener(view -> {
//            Log.d("debug66", "클릭리스너실행됨");
//            //어댑터에 추가 시 에러. 로컬 저장 방식 부터 정하기 -> 해결
////            myAdapter.add(new Template());
////            toTemplateEditorIntent.putExtra("template_item", myAdapter.get(myAdapter.getItemCount()));
//            toTemplateEditorIntent = new Intent(this, TemplateEditor.class);
//            startActivity(toTemplateEditorIntent);
        });
        binding.filterBtn.setOnClickListener(view -> {
            showPopup(this.findViewById(R.id.myToolbar));
        });
        binding.searchBtn.setOnClickListener(view -> {
            binding.searchView.setVisibility(View.VISIBLE);
        });
        setContentView(binding.getRoot());
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        items.clear();
        db.collection("user1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // QuerySnapshot으로부터 문서 목록을 얻음
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                // 각 문서에 대한 반복
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    // 문서 데이터 가져오기 + ArrayList<Template> 인 items에 추가
                                    tempTemp = document.toObject(Template.class);
                                    tempTemp.id = document.getId();
                                    Log.d("Firestore", tempTemp.toString());
                                    items.add(tempTemp);
                                }
                            } else {
                                Log.d("Firestore", "No documents found in the collection.");
                            }
                        } else {
                            Log.w("Firestore", "Error getting documents.", task.getException());
                        }
                        //어댑터에 데이터 연결. 위치를 옮기면 오류
                        myAdapter.addItems(items);
                        binding.recyclerView.setAdapter(myAdapter);
                    }
                });
        myAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String clipboard_data = intent.getStringExtra("template_use");
        if (clipboard_data != null) {
            Log.d("fromTemplateUse", clipboard_data);

            // Get the system clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // Create a clip data
            ClipData clip = ClipData.newPlainText("label", clipboard_data);
            // Set the clipboard's primary clip.
            clipboard.setPrimaryClip(clip);
        }

        super.onNewIntent(intent);
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