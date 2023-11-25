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
import com.google.android.material.search.SearchView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String titles[] = {"안녕하세요", "Hello", "제목1", "제목2", "제목3"};
    String mains[] = {"안녕하세요. [고객명]님, 구매하신 상품에 대한 추가 정보를 [날짜]까지 회신하여 주시면, 추가 조치를 해드리겠습니다. 추가 문의 사항이 있으시다면 [주소]로 방문하여......",
            "Hello", "본몬1", "본문2", "본문3",};
    Intent toTemplateEditorIntent;
    Gson gson = new Gson();
    MyAdapter myAdapter = new MyAdapter(this);
    ArrayList<Template> items = new ArrayList<>();
    public void makeData(){
        for(int i = 0; i<5; i++) {
            Template item = new Template(titles[i], mains[i]);
            items.add(item);
        }
        Log.d("debug79", items.toString());
    }

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
        binding.recyclerView.setAdapter(myAdapter);


        //샘플 데이터 적용 || 파일 내용물 없으면 초기 샘플데이터 파일에 저장
        //참고로 리스트 객체의 걸 Gson으로 바꾸면 알파벳 순으로 자동 정렬되므로 주의
        //순서가 중요하다면 hashmap 사용할 것
        if(loadJsonFromLocal() == null) {
            makeData();
            String tempJsonString = gson.toJson(items);
            Log.d("debug77", tempJsonString);
            saveJsonToLocal(tempJsonString);
        }
        else {
            //자바 상에선 제네릭을 디시리얼라이징할 때 다른 방법이 없다고 함.
            TypeToken<ArrayList<Template>> collectionType = new TypeToken<ArrayList<Template>>(){};
            items = (ArrayList<Template>) gson.fromJson(loadJsonFromLocal(), collectionType.getType());
            Log.d("debug78", items.toString());
        }
        myAdapter.addItems(items);

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
    /*
    1. gson 라이브러리 사용
    2. 일단 이니셜때는 파일에 기본적인 템플릿들을 json String으로 저장
    3. onCreate 혹은 onResume 시에 파일에서 로딩해야 함. 일단은 onCreate만 생각
     */
}
