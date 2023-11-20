package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.company.auto_template.R;
import com.company.auto_template.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        //현재 MainActivity으로 이동
        Intent toLoginIntent = new Intent(this, MainActivity.class);
        binding.startLoginBtn.setOnClickListener(view -> {
            startActivity(toLoginIntent);
        });
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}