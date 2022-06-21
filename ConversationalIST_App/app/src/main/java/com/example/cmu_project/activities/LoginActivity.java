package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.LoginUserGrpcTask;
import com.example.cmu_project.grpc_tasks.RegisterUserGrpcTask;
import com.example.cmu_project.helpers.LinkHelper;

public class LoginActivity extends AppCompatActivity {

    LinkHelper link_helper;

    Button loginButton, registerButton, quitButton;
    EditText ed1, ed2;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        link_helper = new LinkHelper();

        loginButton = (Button) findViewById(R.id.login_button);
        ed1 = (EditText) findViewById(R.id.edit_text);
        ed2 = (EditText) findViewById(R.id.edit_text2);

        registerButton = (Button) findViewById(R.id.register_button);

        quitButton = (Button) findViewById(R.id.quit_button);
        tx1 = (TextView) findViewById(R.id.text_view3);
        tx1.setVisibility(View.GONE);

        quitButton.setOnClickListener(v -> finish());

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        handleIntent(appLinkIntent);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();

        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            String chat_name = appLinkData.getLastPathSegment();
            link_helper.setFlag(true);
            link_helper.setChat_to_go(chat_name);
        }
    }

    public void login(View view) {

        if (ed1.getText().toString().matches("") || ed2.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(),
                    "Username or Password fields missing", Toast.LENGTH_SHORT).show();
        } else {
            String username = ed1.getText().toString();
            String password = ed2.getText().toString();
            new LoginUserGrpcTask(this,link_helper,username).execute(password);
        }

    }

    public void register(View view) {
        if (ed1.getText().toString().matches("") || ed2.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(),
                    "Username or Password fields missing", Toast.LENGTH_SHORT).show();
        } else {

            new RegisterUserGrpcTask(this,ed1.getText().toString(),link_helper).execute(ed2.getText().toString());

        }
    }
}