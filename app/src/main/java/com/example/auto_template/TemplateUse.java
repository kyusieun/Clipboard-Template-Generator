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
import android.widget.TextView;
import android.widget.Toast;


import com.example.auto_template.databinding.TemplateUseBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUse extends AppCompatActivity {
    TemplateUseBinding binding;
    Intent fromMainIntent;
    Intent toMainIntent;
    Template tempTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = TemplateUseBinding.inflate(getLayoutInflater());
        fromMainIntent = getIntent();
        Template tempTemp = fromMainIntent.getParcelableExtra("selected_template", Template.class);
        assert tempTemp != null;

        binding.sampleTitle.setText(tempTemp.title);
        binding.templateEditorEditText.setText(tempTemp.content);
        
        // content 파싱
        List<List<Object>> parcedContent = parseContent(tempTemp.content);
        showDialogs(parcedContent, 0, tempTemp.content);

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
    public void showDialogs(List<List<Object>> parcedContent, int index, String content) {
        if (index >= parcedContent.size()) {
            String result =  replaceContent(content, parcedContent);

            toMainIntent = new Intent(this, MainActivity.class);
            toMainIntent.putExtra("template_use",result);
            startActivity(toMainIntent);
            finish();
            return;
        }

        List<Object> element = parcedContent.get(index);

        AlertDialog.Builder builder = new AlertDialog.Builder(TemplateUse.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_use_text_input, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        titleTextView.setText(String.format("%s 변수 입력", element.get(0)));
        EditText confirmTextView = dialogView.findViewById(R.id.confirmTextView);
        Button noButton = dialogView.findViewById(R.id.noButton);
        Button yesButton = dialogView.findViewById(R.id.yesButton);

        AlertDialog dialog = builder.create();

        // Set click listener for "No" button
        noButton.setOnClickListener(v -> dialog.dismiss());

        // Set click listener for "Yes" button
        yesButton.setOnClickListener(v -> {
            String inputText = confirmTextView.getText().toString();
            // 입력받은 텍스트를 element의 0번째 인덱스에 할당
            element.set(0,inputText);
            Log.d("temuse", parcedContent.toString());
            dialog.dismiss();

            // Show the next dialog
            showDialogs(parcedContent, index + 1, content);
        });

        dialog.show();
    }

    public List<List<Object>> parseContent(String content) {
        List<List<Object>> result = new ArrayList<>();

        // Regular expression pattern to match {{text}} or [[text]]
        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}|\\[\\[(.+?)\\]\\]");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            List<Object> match = new ArrayList<>();
            if (matcher.group(1) != null) {
                // If the text is inside {{ and }}, add the text and a flag of 0
                match.add(matcher.group(1));
                match.add(0);
            } else {
                // If the text is inside [[ and ]], add the text and a flag of 1
                match.add(matcher.group(2));
                match.add(1);
            }
            result.add(match);
        }
        return result;
    }

    public String replaceContent(String content, List<List<Object>> trans) {
        // Regular expression pattern to match {{text}} or [[text]]
        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}|\\[\\[(.+?)\\]\\]");
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();
        int i = 0;

        while (matcher.find()) {
            // Replace the matched text with the corresponding element in the trans list
            matcher.appendReplacement(sb, trans.get(i).get(0).toString());
            i++;
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}


