package com.example.auto_template;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.auto_template.databinding.FragmentTemplateMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemplateMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemplateMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TemplateMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemplateMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemplateMainFragment newInstance(String param1, String param2) {
        TemplateMainFragment fragment = new TemplateMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent toTemplateEditorIntent;
    MyAdapter myAdapter;
    ArrayList<Template> items = new ArrayList<Template>();
    Template tempTemp;
    FragmentTemplateMainBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("fff", "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            myAdapter = new MyAdapter(requireContext());
        }

    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(requireContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sort_menu, popup.getMenu());
        popup.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTemplateMainBinding.inflate(getLayoutInflater());
        Log.d("fff", "onCreateView");
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("fff", "onViewCreated");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int itemView = (int) (parent.getWidth() * 0.9);
                int parentView = parent.getWidth();
                int margin = (parentView - itemView) / 2;
                outRect.set(margin, 0, margin, 0);
            }
        });
        binding.addFab.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "addFab", Toast.LENGTH_SHORT).show();
            //템플릿 추가 버튼 구현 필요
        });
        binding.filterBtn.setOnClickListener(v -> {
            showPopup(binding.myToolbar);
        });
        binding.searchBtn.setOnClickListener(v -> {
            binding.searchView.setVisibility(View.VISIBLE);
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        items.clear();
        db.collection("user1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
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
                        Log.d("fff", myAdapter.getItems().toString());
                    }
                });
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d("fff", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("fff", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("fff", "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d("fff", "onDetach");
        super.onDetach();
    }
    /* Fragment 변경 후 미작동. 수정 필요
    public void onBackPressed(){
        SearchView searchView = binding.searchView;
        searchView.setVisibility(searchView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    */

}