package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.auto_template.databinding.TemplateEditorBinding;

public class TemplateEditor extends AppCompatActivity {
    TemplateEditorBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateEditorBinding.inflate(getLayoutInflater());
        Log.d("editor","안 잔다");
        fromMainIntent = getIntent();

        Template item = fromMainIntent.getSerializableExtra("template_item", Template.class);
        Log.d("LoginBBB", item.toString());
        binding.templateEditorTitle.setText(item.template_name);
        binding.templateEditorEditText.setText(item.template_content);
        toMainIntent = new Intent(this, MainActivity.class);
        binding.btnTemplateEditorExit.setOnClickListener(view ->{
            startActivity(toMainIntent);
        });
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}