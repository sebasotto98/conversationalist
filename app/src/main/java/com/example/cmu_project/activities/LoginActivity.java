package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmu_project.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, registerButton, quitButton;
    EditText ed1, ed2;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);

        registerButton = (Button) findViewById(R.id.registerButton);

        quitButton = (Button) findViewById(R.id.quitButton);
        tx1 = (TextView) findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        loginButton.setOnClickListener(v -> {
            //TODO: Retrieve users from backend
            if (ed1.getText().toString().equals("admin") &&
                    ed2.getText().toString().equals("admin")) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                myIntent.putExtra("username", ed1.getText().toString());
                LoginActivity.this.startActivity(myIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                tx1.setVisibility(View.VISIBLE);
                tx1.setBackgroundColor(Color.RED);
                counter--;
                tx1.setText(Integer.toString(counter));

                if (counter == 0) {
                    loginButton.setEnabled(false);
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(ed1.getText().toString())) {
                //TODO: Do stronger validity check
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Please input a valid username.")
                        .setCancelable(true)
                        .show();
            } else {
                //TODO: Save users to backend
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                myIntent.putExtra("username", ed1.getText().toString());
                LoginActivity.this.startActivity(myIntent);
            }

        });

        quitButton.setOnClickListener(v -> finish());
    }
}