package com.example.auto_template;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.auto_template.databinding.SettingPrivacyManagementBinding;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrivacyManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivacyManagementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrivacyManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrivacyManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrivacyManagementFragment newInstance(String param1, String param2) {
        PrivacyManagementFragment fragment = new PrivacyManagementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    SettingPrivacyManagementBinding binding;
    PrivacyAdapter myAdapter = new PrivacyAdapter(getContext());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SettingPrivacyManagementBinding.inflate(getLayoutInflater());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.privacyRecyclerView.setLayoutManager(linearLayoutManager);
        binding.addEmptyPrivacyItemButton.setOnClickListener(v ->{
            if(myAdapter.hasEmptyItem)
                Toast.makeText(getActivity(), "이미 빈 아이템이 만들어졌습니다", Toast.LENGTH_SHORT).show();
            else{
                myAdapter.putEmptyItem();
                Log.d("test2", myAdapter.getItems().toString());
                //아이템은 정상적으로 들어감
                myAdapter.notifyItemInserted(myAdapter.getItemCount() - 1);
                myAdapter.hasEmptyItem = true;
            }
        });
        binding.privacyRecyclerView.setAdapter(myAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}