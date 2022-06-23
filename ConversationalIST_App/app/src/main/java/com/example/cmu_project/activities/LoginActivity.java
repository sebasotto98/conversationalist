package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

    Menu menu = null;

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
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            String current_language = prefs.getString("language", "English");
            if(current_language.equals("Português")){
                Toast.makeText(getApplicationContext(),
                        R.string.campo_em_falta, Toast.LENGTH_SHORT).show();
            } else if (current_language.equals("English")) {
                Toast.makeText(getApplicationContext(),
                        R.string.field_missing, Toast.LENGTH_SHORT).show();
            }

        } else {
            String username = ed1.getText().toString();
            String password = ed2.getText().toString();
            new LoginUserGrpcTask(this,link_helper,username).execute(password);
        }

    }

    public void register(View view) {
        if (ed1.getText().toString().matches("") || ed2.getText().toString().matches("")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            String current_language = prefs.getString("language", "English");
            if(current_language.equals("Português")){
                Toast.makeText(getApplicationContext(),
                        R.string.campo_em_falta, Toast.LENGTH_SHORT).show();
            } else if (current_language.equals("English")) {
                Toast.makeText(getApplicationContext(),
                        R.string.field_missing, Toast.LENGTH_SHORT).show();
            }
        } else {

            new RegisterUserGrpcTask(this,ed1.getText().toString(),link_helper).execute(ed2.getText().toString());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String current_language = prefs.getString("language", "English");
        if(current_language.equals("Português")){
            setPortuguese();
        } else if (current_language.equals("English")) {
            setEnglish();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.language){
            Intent myIntent = new Intent(LoginActivity.this, Languages.class);
            LoginActivity.this.startActivity(myIntent);
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String language = prefs.getString("language", "English");

        if(menu != null) {
            if (language.equals("Português")) {
                setPortuguese();
            } else if (language.equals("English")) {
                setEnglish();
            }
        }
    }

    private void setEnglish(){
        TextView textview = (TextView) findViewById(R.id.textview);
        TextView textview2 = (TextView) findViewById(R.id.textView2);
        (menu.findItem(R.id.language)).setTitle(R.string.language);
        textview.setText(R.string.login_or_register);
        ed1.setHint(R.string.enter_username);
        textview2.setText(R.string.attempts_left);
        registerButton.setText(R.string.register);
        quitButton.setText(R.string.quit);
    }

    private void setPortuguese(){
        TextView textview = (TextView) findViewById(R.id.textview);
        TextView textview2 = (TextView) findViewById(R.id.textView2);
        (menu.findItem(R.id.language)).setTitle(R.string.linguagem);
        textview.setText(R.string.login_ou_registar);
        ed1.setHint(R.string.nome_utilizador);
        textview2.setText(R.string.tentativas);
        registerButton.setText(R.string.registar);
        quitButton.setText(R.string.sair);
    }
}