package com.example.cmu_project.helpers;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmu_project.R;
import com.example.cmu_project.adapters.MessageAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import io.grpc.ManagedChannel;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.okhttp.OkHttpChannelBuilder;
import nl.altindag.ssl.SSLFactory;

public class GlobalVariableHelper extends Application {

    static {
        setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private static final Logger logger = Logger.getLogger(GlobalVariableHelper.class.getName());

    private final DBHelper db = new DBHelper(this);

    private ServerGrpc.ServerBlockingStub serverBlockingStub = null;
    private ServerGrpc.ServerStub nonBlockingStub = null;

    private RecyclerView messageRecycler = null;
    private MessageAdapter messageAdapter = null;
    private String currentChatroomName = null;
    private String username = null;

    private final NotificationsHelper notificationsHelper = new NotificationsHelper(this);

    public GlobalVariableHelper() throws PackageManager.NameNotFoundException {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final Bundle bundle = applicationInfo.metaData;
        final String KEYSTORE_PASS = bundle.getString("keystore_pass");
        final String HOST_ADDRESS = bundle.getString("host_address");

        InputStream identityStorePath = getResources().openRawResource( R.raw.client_keystore_file);
        InputStream trustStorePath = getResources().openRawResource( R.raw.client_truststore_file);

        KeyStore keystore = null;
        try {
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(identityStorePath, KEYSTORE_PASS.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyManagerFactory kmf = null;
        try {
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            kmf.init(keystore, KEYSTORE_PASS.toCharArray());
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyManager[] keyManager = kmf.getKeyManagers();

        KeyStore truststore = null;
        try {
            truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            truststore.load(trustStorePath, KEYSTORE_PASS.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        TrustManager[] trustManager = new TrustManager[] {
                new TrustManagerHelper(truststore)
        };

        SSLFactory sslFactory = SSLFactory.builder().withDefaultTrustMaterial().build();
        SSLContext sslContext = sslFactory.getSslContext();

        try {
            sslContext.init(keyManager, trustManager, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        ManagedChannel channel = OkHttpChannelBuilder.forAddress(HOST_ADDRESS, 50051)
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(sslFactory.getHostnameVerifier())
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

    public NotificationsHelper getNotificationsHelper(){
        return notificationsHelper;
    }

}