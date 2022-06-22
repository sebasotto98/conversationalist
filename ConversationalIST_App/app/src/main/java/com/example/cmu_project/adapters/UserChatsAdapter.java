package com.example.cmu_project.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import com.example.cmu_project.activities.ManageChatActivity;
import com.example.cmu_project.grpc_tasks.LeaveChatGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.example.cmu_project.R;
import com.example.cmu_project.activities.ChatActivity;

import java.util.List;

public class UserChatsAdapter extends BaseAdapter implements ListAdapter {

    private List<String> user_chats_list;
    private List<String> user_owner_chats;
    private final Context context;
    public Application application;
    public String user;

    public UserChatsAdapter(List<String> user_chats_list,List<String> user_owner_chats, Context context, Application application,String user) {
        this.user_chats_list = user_chats_list;
        this.user_owner_chats = user_owner_chats;
        this.context = context;
        this.application = application;
        this.user = user;
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

    @SuppressLint("SetTextI18n")
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
        Button leavebtn = (Button)view.findViewById(R.id.btn_item_leave);
        Button managebtn = (Button)view.findViewById(R.id.btn_item_manage);
        callbtn.setText(user_chats_list.get(position));
        leavebtn.setText("Leave");
        managebtn.setText("Manage");

        if (!user_owner_chats.contains(user_chats_list.get(position))) {
            managebtn.setVisibility(View.GONE);
        }

        callbtn.setOnClickListener(v -> {
            ((GlobalVariableHelper) app).setCurrentChatroomName(callbtn.getText().toString());

            Intent myIntent = new Intent(v.getContext(), ChatActivity.class);
            myIntent.putExtra("chatroom", callbtn.getText().toString());
            v.getContext().startActivity(myIntent);
        });

        leavebtn.setOnClickListener(v -> {
            new LeaveChatGrpcTask((Activity) v.getContext(),callbtn,leavebtn).execute(user,user_chats_list.get(position));
        });

        managebtn.setOnClickListener(v -> {

            Intent myIntent = new Intent(v.getContext(), ManageChatActivity.class);
            myIntent.putExtra("chat_name",callbtn.getText().toString());
            v.getContext().startActivity(myIntent);

        });

        return view;
    }
}