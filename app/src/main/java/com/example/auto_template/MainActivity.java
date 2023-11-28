package com.example.auto_template;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
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
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    // Access a Cloud Firestore instance from your Activity
    String dummy_id = "auto_template@gmail.com";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("templates").document(dummy_id);

    Intent toTemplateEditorIntent;
    Gson gson = new Gson();
    MyAdapter myAdapter = new MyAdapter(this);
    ArrayList<Template> items = new ArrayList<>();
//    ArrayList<Template> items;
    TypeToken<ArrayList<Template>> collectionType = new TypeToken<ArrayList<Template>>(){};

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

                //실행하자마자 0됨. 절대 길이가 설정되기 전, 에 호출되어 버려서 그런듯
                Log.d("debug33", String.format("%d %d", itemView, parentView));
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
//            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
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



        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // "template" 필드를 Map으로 가져옴
                        Map<String, Object> templateMap = (Map<String, Object>) document.get("template");

                        if (templateMap != null) {
                            // "content" 필드를 가져옴
                            String content = (String) templateMap.get("content");
                            Log.d("FireStore", "content: " + content);
                            // content를 사용하여 원하는 작업 수행
                            // 예: TextView에 content를 설정
                            // textView.setText(content);
                        } else {
                            // "template" 필드가 없는 경우 처리
                        }
                        Log.d("FireStore", "DocumentSnapshot data: " + document.getData().toString());

                        items = parseMapToTemplateCollection(document.getData());
                    } else {
                        Log.d("FireStore", "No such document");
                    }
                } else {
                    Log.d("FireStore", "get failed with ", task.getException());
                }
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
    private void saveJsonToLocal(String jsonString){
        //일단은 앱 설치 후 첫 실행할 때만 해당 메소드 호출되도록 하는게 목표(튜토리얼 역할 정도)
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.openFileOutput("MyDataFile", this.MODE_PRIVATE));
            objectOutputStream.writeObject(jsonString);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String loadJsonFromLocal(){
        String myData = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.openFileInput("MyDataFile"));
            myData = (String) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return myData;
    }
    private ArrayList<Template> parseMapToTemplateCollection(Map<String, Object> map){
        Template tempTemp;
        ArrayList<Template> tempList = new ArrayList<>();
        //데이터 여러개일 때의 처리는 while로. 현재는 형식을 모르겠음
        //while(){
        Map<String, Object> map2 = (Map<String, Object>) map.get("template");
        //tempTemp = new Template();
        tempTemp = gson.fromJson(gson.toJson(map2), Template.class);
        //}
        tempList.add(tempTemp);

        return tempList;
    }
    /*
    1. gson 라이브러리 사용
    2. 일단 이니셜때는 파일에 기본적인 템플릿들을 json String으로 저장
    3. onCreate 혹은 onResume 시에 파일에서 로딩해야 함. 일단은 onCreate만 생각
     */
}
