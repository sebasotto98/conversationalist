package com.example.cmu_project.adapters;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserChatsAdapter extends BaseAdapter implements ListAdapter {

    private List<String> user_chats_list = new ArrayList<>();
    private Context context;
    public Application application;

    public UserChatsAdapter(List<String> user_chats_list, Context context,Application application) {
        this.user_chats_list = user_chats_list;
        this.context = context;
        this.application = application;
    }

    @Override
    public int getCount() {
        return user_chats_list.size();
    }

    @Override
    public Object getItem(int pos) {
        return user_chats_list.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        Application app = this.application;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_view, null);
        }


        //Handle buttons and add onClickListeners
        Button callbtn= (Button)view.findViewById(R.id.btn_item);
        callbtn.setText(user_chats_list.get(position));


        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                ((GlobalVariableHelper) app).setCurrentChatroomName(callbtn.getText().toString());

                Intent myIntent = new Intent(v.getContext(), ChatActivity.class);
                myIntent.putExtra("chatroom", callbtn.getText().toString());
                v.getContext().startActivity(myIntent);

            }
        });


        return view;
    }


}

