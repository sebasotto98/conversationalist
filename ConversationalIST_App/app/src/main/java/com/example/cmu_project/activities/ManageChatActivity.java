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

import java.util.ArrayList;
import java.util.List;

public class ManageChatActivity extends AppCompatActivity {

    ListView chatMembersList;
    String chatName;
    String userToRemove;
    List<String> chatMembers;
    EditText addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chat);

        chatName = getIntent().getExtras().getString("chat_name");

        addUser = findViewById(R.id.add_user_text);
        chatMembersList = findViewById(R.id.chat_members_list);
        chatMembers = new ArrayList<>();

        new GetChatMembersGrpcTask(this, chatMembersList, chatMembers).execute(chatName);

        chatMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                userToRemove = chatMembersList.getItemAtPosition(i).toString();

            }
        });

    }

    public void addUser(View view) {

        if (addUser.getText().toString().matches("")) {
            Toast.makeText(this.getApplicationContext(), "Add user is empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            new AddUserToChatGrpcTask(this, chatMembers, addUser.getText().toString()).execute(userToRemove, chatName);
            new GetChatMembersGrpcTask(this, chatMembersList, chatMembers).execute(chatName);
            chatMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    userToRemove = chatMembersList.getItemAtPosition(i).toString();

                }
            });
        }

    }

    public void removeUser(View view) {

        if (userToRemove.matches("")) {
            Toast.makeText(this.getApplicationContext(), "No user to remove selected",
                    Toast.LENGTH_SHORT).show();
        } else {

            new RemoveUserGrpcTask(this, userToRemove).execute(chatName);
            new GetChatMembersGrpcTask(this, chatMembersList, chatMembers).execute(chatName);
            chatMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    userToRemove = chatMembersList.getItemAtPosition(i).toString();

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