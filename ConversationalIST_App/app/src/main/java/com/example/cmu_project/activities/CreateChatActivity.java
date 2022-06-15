package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.CreateChatGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;


public class CreateChatActivity extends AppCompatActivity {

    Button sendRequest;
    EditText chat_name;
    RadioButton current_checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);

        //Buttons
        sendRequest = (Button) findViewById(R.id.create_chatroom);

        //TextFields
        chat_name = (EditText) findViewById(R.id.chat_name);


    }

    public void create(View view) {

        String new_chat_name = chat_name.getText().toString();
        String type_of_chat = current_checked.getText().toString();

        new CreateChatGrpcTask(this,new_chat_name).execute(((GlobalVariableHelper) this.getApplication()).getUsername(),type_of_chat);

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton)view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_public:
                if (checked) {
                    current_checked = (RadioButton) findViewById(R.id.radio_public);
                    findViewById(R.id.radiusId).setVisibility(View.GONE);
                    findViewById(R.id.textViewRadius).setVisibility(View.GONE);
                    findViewById(R.id.textViewLocation).setVisibility(View.GONE);
                    findViewById(R.id.buttonMyLocation).setVisibility(View.GONE);
                }
                break;
            case R.id.radio_private:
                if (checked) {
                    current_checked = (RadioButton) findViewById(R.id.radio_private);
                    findViewById(R.id.radiusId).setVisibility(View.GONE);
                    findViewById(R.id.textViewRadius).setVisibility(View.GONE);
                    findViewById(R.id.textViewLocation).setVisibility(View.GONE);
                    findViewById(R.id.buttonMyLocation).setVisibility(View.GONE);
                }
                break;
            case R.id.radio_geofanced:
                if (checked) {
                    current_checked = (RadioButton) findViewById(R.id.radio_geofanced);
                    findViewById(R.id.radiusId).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewRadius).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewLocation).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonMyLocation).setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}