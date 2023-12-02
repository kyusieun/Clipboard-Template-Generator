package com.example.auto_template;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
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

//        Log.d("LOGIN3", loadJsonFromLocal());
        // Firestore에서 data loding 후 LogCat에 출력
        // document.getData()를 사용해서 변수에 저장 가능
        // 현재 document.getData() 쿼리의 결과는 아래 주석과 같음
        // 	{latest_use: 2023년 11월 27일 오전 5시 5분 44초 UTC+9, content: "내용", id: 0, reference: 4, tag: ["tag1", "tag2"], last_edit: 2023년 11월 26일 오전 5시 5분 28초 UTC+9, title: "안녕하세요"}


        // FireStore 데이터 추가 함수입니다.
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        // 원래 currentUser != null 인데 비활성화 하려고 null 넣었습니다.
        if (null != null) {
//            String userUid = currentUser.getUid();
            String userUid = "user1";

            Map<String, Object> userData = new HashMap<>();
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("last_edit", new Timestamp(new Date()));
            templateData.put("latest_use", new Timestamp(new Date()));
            templateData.put("reference", 4);
            List<String> tags = Arrays.asList("tag1", "tag2", "tag3");
            templateData.put("tag", tags);
            templateData.put("title", "제목");
            templateData.put("content", "안녕하세요. {{고객명}}님, 구매하신 상품에 대한 추가 정보를 [[날짜]]까지 회신하여 주시면 추가 조치를 해드리겠습니다. 추가 문의사항이 있으시다면 {{주소}}로 방문해주세요.");
            String template_name = new Timestamp(new Date()).toString();
            userData.put(template_name, templateData);
//            userData.put("email", currentUser.getEmail());
            // 사용자 UID를 기반으로 데이터 추가
            db.collection("users").document(userUid)
                    .set(userData, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Firestore", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.w("Firestore", "Error writing document", e);
                        }
                    });
        }
        db.collection("user1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    Template tempTemp;
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // QuerySnapshot으로부터 문서 목록을 얻음
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                // 각 문서에 대한 반복
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    // 문서 ID 가져오기
                                    //items.add(new Template(document.getId()));
                                    Log.d("Firestore", "Document ID: " + document.getId());
                                    // 문서 데이터 가져오기 + ArrayList<Template> 인 items에 추가
//                                    tempTemp = document.toObject(Template.class);
                                    Log.d("firestore", document.get("title", String.class));
                                    tempTemp = new Template();
                                    tempTemp.title = (String)document.get("title");
                                    tempTemp.content = document.get("content", String.class);
                                    tempTemp.last_edit = document.get("last_edit", Date.class);
                                    Log.d("firestore", document.get("last_edit", Date.class).toString());
                                    tempTemp.latest_use = ((Timestamp)document.get("latest_use")).toDate();
                                    tempTemp.tag = (ArrayList<String>)document.get("tag");
                                    tempTemp.reference = (long) document.get("reference");
                                    items.add(tempTemp);
                                    Log.d("Firestore", tempTemp.toString());
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