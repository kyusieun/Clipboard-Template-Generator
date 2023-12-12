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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

import com.example.auto_template.databinding.TemplateEditorBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateEditor extends AppCompatActivity {
    TemplateEditorBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;
    String email;

    boolean isEdit = true;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }

        binding = TemplateEditorBinding.inflate(getLayoutInflater());
        fromMainIntent = getIntent();
        Template tempTemp = fromMainIntent.getParcelableExtra("selected_template", Template.class);
        if (tempTemp == null) {
            tempTemp = new Template();
            isEdit = false;
        }
        binding.sampleTitle.setText(tempTemp.title);
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
                    binding.templateEditorEditText.setSelection(cursorPosition + inputText.length() + 4); // 커서를 삽입된 텍스트 뒤로 이동
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
                    binding.templateEditorEditText.setSelection(cursorPosition + inputText.length() + 4); // 커서를 삽입된 텍스트 뒤로 이동
                }
                dialog.dismiss();
            });

            dialog.show();
        });

        // 저장 버튼
        if (isEdit == true){ // 에딧일 경우
            Template finalTempTemp = tempTemp;
            binding.btnTemplateEditorExit.setOnClickListener(view ->{
                DocumentReference docRef = db.collection(email).document(finalTempTemp.id);
                docRef.update(
                                "title", binding.sampleTitle.getText().toString(),
                                "content", binding.templateEditorEditText.getText().toString(),
                                "last_edit", Timestamp.now()
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Firestore", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.d("Firestore", "Error updating document", e);
                            }
                        });

                toMainIntent = new Intent(this, MainActivity.class);
                startActivity(toMainIntent);
                finish();
            });
        } else { // 추가일 경우
            binding.btnTemplateEditorExit.setOnClickListener(view ->{
                Map<String, Object> data = new HashMap<>();
                ArrayList<String> tag = new ArrayList<>();
                Log.d("Firestore", binding.sampleTitle.getText().toString());
                data.put("title", binding.sampleTitle.getText().toString());
                data.put("content", binding.templateEditorEditText.getText().toString());
                data.put("last_edit", Timestamp.now());
                data.put("latest_use", Timestamp.now());
                data.put("reference", 0);
                data.put("tag", tag);

                db.collection(email).add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Firestore", "DocumentSnapshot written with ID: " + documentReference.getId());
                                documentReference.update("id", documentReference.getId());
                                toMainIntent = new Intent(TemplateEditor.this, MainActivity.class);
                                startActivity(toMainIntent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.w("Firestore", "Error adding document", e);
                            }
                        });
                toMainIntent = new Intent(this, MainActivity.class);
                startActivity(toMainIntent);
                finish();
            });

        }

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}