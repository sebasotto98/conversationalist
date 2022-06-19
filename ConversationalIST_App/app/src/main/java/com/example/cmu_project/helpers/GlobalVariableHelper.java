package com.example.cmu_project.helpers;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.adapters.MessageAdapter;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;

import io.grpc.ManagedChannel;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import nl.altindag.ssl.SSLFactory;

public class GlobalVariableHelper extends Application {

    private static final Logger logger = Logger.getLogger(GlobalVariableHelper.class.getName());

    private static final String KEYSTORE_DIR = "keystores/";
    private static final String TRUSTSTORE_DIR = "truststores/";

    private final String BROADCAST_MESSAGE_INSERTED = "new_message_in_adapter";

    private final DBHelper db = new DBHelper(this);

    private ManagedChannel channel = null;
    private ServerGrpc.ServerBlockingStub serverBlockingStub = null;
    private ServerGrpc.ServerStub nonBlockingStub = null;

    private RecyclerView messageRecycler = null;
    private MessageAdapter messageAdapter = null;
    private String currentChatroomName = null;
    private String username = null;

    @Override
    public void onCreate() {
        super.onCreate();

        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        //TODO: ADD KEYSTORE AND TRUSTSTORE FOLDERS IN EMULATOR -> /storage/emulated/0/Android/data/com.example.cmu_project/files
        Path identityStorePath = Paths.get(getApplicationContext().getExternalFilesDir(null) + "/" + KEYSTORE_DIR + "client_KeystoreFile.jks");
        Path trustStorePath = Paths.get(getApplicationContext().getExternalFilesDir(null) + "/" + TRUSTSTORE_DIR + "client_TruststoreFile.jks");
        SSLFactory sslFactory = SSLFactory.builder()
                .withIdentityMaterial(identityStorePath, "testtest".toCharArray())
                .withTrustMaterial(trustStorePath, "testtest".toCharArray())
                .withHostnameVerifier(hostnameVerifier)
                .build();
        channel = OkHttpChannelBuilder.forAddress("192.168.1.80", 50051)
                .sslSocketFactory(sslFactory.getSslSocketFactory())
                .build();
        serverBlockingStub = ServerGrpc.newBlockingStub(channel);
        nonBlockingStub = ServerGrpc.newStub(channel);

    }

    public String getCurrentChatroomName() {
        return currentChatroomName;
    }

    public void setCurrentChatroomName(String currentChatroomName) {
        this.currentChatroomName = currentChatroomName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DBHelper getDb() {
        return db;
    }

    public ServerGrpc.ServerBlockingStub getServerBlockingStub() {
        return serverBlockingStub;
    }

    public ServerGrpc.ServerStub getNonBlockingStub() {
        return nonBlockingStub;
    }

    public RecyclerView getMessageRecycler() {
        return messageRecycler;
    }

    public void setMessageRecycler(RecyclerView messageRecycler) {
        this.messageRecycler = messageRecycler;
    }

    public MessageAdapter getMessageAdapter() {
        return messageAdapter;
    }

    public void setMessageAdapter(MessageAdapter messageAdapter) {
        this.messageAdapter = messageAdapter;
    }

    public String getBROADCAST_MESSAGE_INSERTED() {
        return BROADCAST_MESSAGE_INSERTED;
    }
}