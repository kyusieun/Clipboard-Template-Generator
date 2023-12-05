package com.example.auto_template;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        // 텍스트 버튼
        binding.btnTextKeyword.setOnClickListener(view -> {
            // Create a dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(TemplateEditor.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_text_input, null);
            builder.setView(dialogView);

            EditText confirmTextView = dialogView.findViewById(R.id.confirmTextView);
            Button noButton = dialogView.findViewById(R.id.noButton);
            Button yesButton = dialogView.findViewById(R.id.yesButton);

            AlertDialog dialog = builder.create();

            // Set click listener for "No" button
            noButton.setOnClickListener(v -> dialog.dismiss());

            // Set click listener for "Yes" button
            yesButton.setOnClickListener(v -> {
                String inputText = confirmTextView.getText().toString();
                // Handle the input text here
                // For example, you can insert the text into the editor
                int cursorPosition = binding.templateEditorEditText.getSelectionStart();
                if (cursorPosition < 0) {
                    Toast.makeText(this, "커서를 위치시켜주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String currentText = binding.templateEditorEditText.getText().toString();
                    String newText = currentText.substring(0, cursorPosition) + "{{" + inputText + "}}" + currentText.substring(cursorPosition);
                    binding.templateEditorEditText.setText(newText);
                    binding.templateEditorEditText.setSelection(cursorPosition + inputText.length()); // 커서를 삽입된 텍스트 뒤로 이동
                }
                dialog.dismiss();
            });

            dialog.show();
        });

        // 날짜 버튼
        binding.btnClockKeyword.setOnClickListener(view -> {
            // Create a dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(TemplateEditor.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_text_input, null);
            builder.setView(dialogView);

            EditText confirmTextView = dialogView.findViewById(R.id.confirmTextView);
            Button noButton = dialogView.findViewById(R.id.noButton);
            Button yesButton = dialogView.findViewById(R.id.yesButton);

            AlertDialog dialog = builder.create();

            // Set click listener for "No" button
            noButton.setOnClickListener(v -> dialog.dismiss());

            // Set click listener for "Yes" button
            yesButton.setOnClickListener(v -> {
                String inputText = confirmTextView.getText().toString();
                // Handle the input text here
                // For example, you can insert the text into the editor
                int cursorPosition = binding.templateEditorEditText.getSelectionStart();
                if (cursorPosition < 0) {
                    Toast.makeText(this, "커서를 위치시켜주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String currentText = binding.templateEditorEditText.getText().toString();
                    String newText = currentText.substring(0, cursorPosition) + "[[" + inputText + "]]" + currentText.substring(cursorPosition);
                    binding.templateEditorEditText.setText(newText);
                    binding.templateEditorEditText.setSelection(cursorPosition + inputText.length()); // 커서를 삽입된 텍스트 뒤로 이동
                }
                dialog.dismiss();
            });

            dialog.show();
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