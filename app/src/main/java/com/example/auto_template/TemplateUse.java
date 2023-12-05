package com.example.auto_template;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.auto_template.databinding.TemplateUseBinding;

public class TemplateUse extends AppCompatActivity {
    TemplateUseBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateUseBinding.inflate(getLayoutInflater());
        fromMainIntent = getIntent();
        Template tempTemp = fromMainIntent.getParcelableExtra("selected_template", Template.class);
        assert tempTemp != null;

        binding.sampleTitle.setText(tempTemp.title);
        binding.templateEditorEditText.setText(tempTemp.content);


        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}