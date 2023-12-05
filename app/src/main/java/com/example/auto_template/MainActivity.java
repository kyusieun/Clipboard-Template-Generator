package com.example.auto_template;

import static com.example.auto_template.TemplateMainFragment.newInstance;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_template.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        BottomNavigationView navigationBarView = binding.bottomNav;
        binding.bottomNav.setOnClickListener(view -> {
            Log.d("fff1", "container");
        });
        transferTo(TemplateMainFragment.newInstance("param1", "param2"));

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    // Respond to navigation item 1 click
                     transferTo(TemplateMainFragment.newInstance("param1", "param2"));
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                       transferTo(SettingFragment.newInstance("param1", "param2"));
                    return true;
                }

                return false;
            }
        });

        navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
        setContentView(binding.getRoot());
    }

    private void transferTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentContainer.getId(), fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        Log.d("fff", "onResumeMain");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("fff", "onStopMain");
        super.onStop();
    }
    /*

    protected void onNewIntent(Intent intent) {
        tempTemp = intent.getParcelableExtra("changed_template", Template.class);
        items.set(intent.getIntExtra("changed_item_position", -1), tempTemp);
        Log.d("fromTemplateEditor", tempTemp.toString());


//            String userUid = currentUser.getUid();
        String userUid = "user1";
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("last_edit", tempTemp.last_edit);
        templateData.put("latest_use", tempTemp.latest_use);
        templateData.put("reference", tempTemp.reference);
        templateData.put("tag", tempTemp.tag);
        templateData.put("title", tempTemp.title);
        templateData.put("content", tempTemp.content);
        db.collection(userUid).document(tempTemp.id)
                .set(templateData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "템플릿 업데이트 성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("Firestore", "템플릿 업데이트 실패", e);
                    }
                });


        myAdapter.notifyDataSetChanged();
        super.onNewIntent(intent);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sort_menu, popup.getMenu());
        popup.show();
    }*/
    /*@Override
    public void onBackPressed(){
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setVisibility(searchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        super.onBackPressed();
    }*/

}