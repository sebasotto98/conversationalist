package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.AddUserToChatGrpcTask;
import com.example.cmu_project.grpc_tasks.GetChatMembersGrpcTask;
import com.example.cmu_project.grpc_tasks.RemoveUserGrpcTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ManageChatActivity extends AppCompatActivity {

    ListView chat_members_list;
    String chat_name;
    String user_to_remove;
    List<String> chat_members;
    EditText add_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chat);

        chat_name = getIntent().getExtras().getString("chat_name");

        add_user = findViewById(R.id.add_user_text);
        chat_members_list = findViewById(R.id.chat_members_list);
        chat_members = new ArrayList<>();

        new GetChatMembersGrpcTask(this, chat_members_list, chat_members).execute(chat_name);

        chat_members_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                user_to_remove = chat_members_list.getItemAtPosition(i).toString();

            }
        });

    }

    public void add_user(View view) {

        if (add_user.getText().toString().matches("")) {
            Toast.makeText(this.getApplicationContext(), "Add user is empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            new AddUserToChatGrpcTask(this, chat_members, add_user.getText().toString()).execute(user_to_remove, chat_name);
            new GetChatMembersGrpcTask(this, chat_members_list, chat_members).execute(chat_name);
            chat_members_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    user_to_remove = chat_members_list.getItemAtPosition(i).toString();

                }
            });
        }

    }

    public void remove_user(View view) {

        if (user_to_remove.matches("")) {
            Toast.makeText(this.getApplicationContext(), "No user to remove selected",
                    Toast.LENGTH_SHORT).show();
        } else {

            new RemoveUserGrpcTask(this, user_to_remove).execute(chat_name);
            new GetChatMembersGrpcTask(this, chat_members_list, chat_members).execute(chat_name);
            chat_members_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    user_to_remove = chat_members_list.getItemAtPosition(i).toString();

                }
            });

        }

    }

    @Override
    protected void onResume() {

        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ManageChatActivity.this);
        String language = prefs.getString("language", "English");

        if (language.equals("Português")) {
            setPortuguese();
        } else if (language.equals("English")) {
            setEnglish();
        }
    }

    private void setEnglish() {
        Button add_user = findViewById(R.id.add_user);
        Button remove_user = findViewById(R.id.remove_user);
        TextView add_user_text = findViewById(R.id.add_user_text);

        add_user.setText(R.string.add);
        remove_user.setText(R.string.remove);
        add_user_text.setText(R.string.add_user);
    }

    private void setPortuguese() {
        Button add_user = findViewById(R.id.add_user);
        Button remove_user = findViewById(R.id.remove_user);
        TextView add_user_text = findViewById(R.id.add_user_text);

        add_user.setText(R.string.adicionar);
        remove_user.setText(R.string.remover);
        add_user_text.setText(R.string.adicionar_utilizador);
    }

}