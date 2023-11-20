package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.company.auto_template.R;
import com.company.auto_template.databinding.TemplateEditorBinding;

public class TemplateEditor extends AppCompatActivity {
    TemplateEditorBinding binding;
    Intent toMainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateEditorBinding.inflate(getLayoutInflater());
        toMainIntent = new Intent(this, MainActivity.class);
        binding.btnTemplateEditorExit.setOnClickListener(view ->{
            startActivity(toMainIntent);
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_editor);
    }
}