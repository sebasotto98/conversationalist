package com.example.cmu_project.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmu_project.R;

import java.util.ArrayList;
import java.util.List;

public class Languages extends AppCompatActivity {

    ListView language_list;
    String language;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languages_activity);

        language_list = (ListView) findViewById(R.id.languages_list);
        select = (Button) findViewById(R.id.select);


        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Português");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Languages.this,android.R.layout.simple_list_item_1,languages);
        language_list.setAdapter(adapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Languages.this);
        String current_language = prefs.getString("language", "English");

        if(current_language.equals("Português")){
            select.setText(R.string.selecionar);
        }

        language_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                language = language_list.getItemAtPosition(i).toString();

            }
        });

    }

    public void select(View view) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Languages.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", language);
        editor.apply();

        //debug only
        String a = prefs.getString("language", "English");
        Toast.makeText(Languages.this, a,
                Toast.LENGTH_SHORT).show();

        finish();
    }
}
