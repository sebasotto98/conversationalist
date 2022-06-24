package com.example.cmu_project.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmu_project.R;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    ListView languageList;
    String language;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        languageList = findViewById(R.id.languages_list);
        select = findViewById(R.id.select);

        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Português");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LanguageActivity.this, android.R.layout.simple_list_item_1, languages);
        languageList.setAdapter(adapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LanguageActivity.this);
        String current_language = prefs.getString("language", "English");

        if (current_language.equals("Português")) {
            select.setText(R.string.selecionar);
        }

        languageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                language = languageList.getItemAtPosition(i).toString();

                if (language.equals("Português")) {
                    select.setText(R.string.selecionar);
                } else if (language.equals("English")) {
                    select.setText(R.string.select);
                }

            }
        });

    }

    public void select(View view) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LanguageActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", language);
        editor.apply();

        finish();
    }
}