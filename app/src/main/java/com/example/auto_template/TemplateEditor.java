package com.example.auto_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        assert tempTemp != null;
        Log.d("TemplateEditor", tempTemp.toString());

        binding.templateEditorTitle.setText(tempTemp.title);
        binding.templateEditorEditText.setText(tempTemp.content);

        // 텍스트 추가 버튼
        binding.btnTextKeyword.setOnClickListener(view -> {
            int cursorPosition = binding.templateEditorEditText.getSelectionStart();
            if (cursorPosition < 0) {
                Toast.makeText(this, "커서를 위치시켜주세요.", Toast.LENGTH_SHORT).show();
            } else {
                String currentText = binding.templateEditorEditText.getText().toString();
                String newText = currentText.substring(0, cursorPosition) + "{{텍스트}}" + currentText.substring(cursorPosition);
                binding.templateEditorEditText.setText(newText);
                binding.templateEditorEditText.setSelection(cursorPosition + "{{텍스트}}".length()); // 커서를 삽입된 텍스트 뒤로 이동
            }
        });

        // 날짜 추가 버튼
        binding.btnClockKeyword.setOnClickListener(view -> {
            int cursorPosition = binding.templateEditorEditText.getSelectionStart();
            if (cursorPosition < 0) {
                Toast.makeText(this, "커서를 위치시켜주세요.", Toast.LENGTH_SHORT).show();
            } else {
                String currentText = binding.templateEditorEditText.getText().toString();
                String newText = currentText.substring(0, cursorPosition) + "[[날짜]]" + currentText.substring(cursorPosition);
                binding.templateEditorEditText.setText(newText);
                binding.templateEditorEditText.setSelection(cursorPosition + "[[날짜]]".length()); // 커서를 삽입된 텍스트 뒤로 이동
            }
        });

        // 저장 버튼 수정 요망
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