package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.Timestamp;

import com.example.auto_template.databinding.TemplateEditorBinding;

import java.sql.Time;
import java.util.ArrayList;

public class TemplateEditor extends AppCompatActivity {
    TemplateEditorBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateEditorBinding.inflate(getLayoutInflater());
        Log.d("editor","안 잔다");
        fromMainIntent = getIntent();
        binding.templateEditorTitle.setText(fromMainIntent.getStringExtra("current_title"));
        binding.templateEditorEditText.setText(fromMainIntent.getStringExtra("current_content"));


        binding.btnTemplateEditorExit.setOnClickListener(view ->{
            toMainIntent = new Intent(this, MainActivity.class);
            startActivity(toMainIntent);
        });
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}