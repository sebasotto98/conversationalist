package com.example.cmu_project.grpc_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmu_project.R;
import com.example.cmu_project.helpers.GlobalVariableHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.examples.backendserver.JoinableChatsReply;
import io.grpc.examples.backendserver.JoinableChatsRequest;
import io.grpc.examples.backendserver.Location;
import io.grpc.examples.backendserver.ServerGrpc;

public class GetAvailableChatsToJoinGrpcTask extends AsyncTask<Object,Void, JoinableChatsReply> {

    private final WeakReference<Activity> activityReference;
    private double user_latitude;
    private double user_longitude;

    public GetAvailableChatsToJoinGrpcTask(Activity activity,double user_latitude,double user_longitude) {
        this.activityReference = new WeakReference<>(activity);
        this.user_latitude = user_latitude;
        this.user_longitude = user_longitude;

    }

    @Override
    protected JoinableChatsReply doInBackground(Object... params) {

        String user = (String) params[0];

        try {
            ServerGrpc.ServerBlockingStub stub
                    = ((GlobalVariableHelper) activityReference.get().getApplication())
                    .getServerBlockingStub();

            Location location = Location.newBuilder().setLatitude(String.valueOf(user_latitude)).setLongitude(String.valueOf(user_longitude)).build();
            JoinableChatsRequest request = JoinableChatsRequest.newBuilder().setUser(user).setUserLocation(location).build();

            return stub.withDeadlineAfter(5, TimeUnit.SECONDS).getJoinableChats(request);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            Log.d("GetAvailableChatsToJoinGrpcTask", sw.toString());
            return null;
        }


    }

    @Override
    protected void onPostExecute(JoinableChatsReply chats) {

        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        if(chats != null) {

            List<String> current_chats = chats.getChatsList();
            ListView chats_list = (ListView) activity.findViewById(R.id.chats_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,current_chats);
            chats_list.setAdapter(adapter);

        } else {
            Toast.makeText(activity.getApplicationContext(), "Error contacting the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}