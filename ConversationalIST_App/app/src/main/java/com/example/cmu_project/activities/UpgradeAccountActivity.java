package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.UpgradeAccountGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;

public class UpgradeAccountActivity extends AppCompatActivity {

    String guest_user;
    EditText new_user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_account);

        guest_user = ((GlobalVariableHelper) this.getApplication()).getUsername();
        this.new_user = findViewById(R.id.user_name_edit_text_guest);
        this.password = findViewById(R.id.password_edit_text_guest);


    }

    public void send_upgrade(View view) {

        if (new_user.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(),
                    R.string.field_missing, Toast.LENGTH_SHORT).show();
        } else {
            new UpgradeAccountGrpcTask(this,new_user.getText().toString()).execute(guest_user,password.getText().toString());
        }

    }
}