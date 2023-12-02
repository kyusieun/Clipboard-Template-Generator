package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.Timestamp;

import com.example.auto_template.databinding.TemplateEditorBinding;

public class TemplateEditor extends AppCompatActivity {
    TemplateEditorBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateEditorBinding.inflate(getLayoutInflater());
        fromMainIntent = getIntent();
        Template tempTemp = fromMainIntent.getParcelableExtra("selected_template", Template.class);
        Log.d("test1", tempTemp.toString());
        binding.templateEditorTitle.setText(tempTemp.title);
        binding.templateEditorEditText.setText(tempTemp.content);


        binding.btnTemplateEditorExit.setOnClickListener(view ->{
            tempTemp.title = binding.templateEditorTitle.getText().toString();
            tempTemp.content = binding.templateEditorEditText.getText().toString();
            tempTemp.last_edit = Timestamp.now();
            toMainIntent = new Intent(this, MainActivity.class);
            toMainIntent.putExtra("changed_template", tempTemp)
                            .putExtra("changed_item_position", fromMainIntent.getIntExtra("selected_item_position", -1));
            startActivity(toMainIntent);
        });
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}