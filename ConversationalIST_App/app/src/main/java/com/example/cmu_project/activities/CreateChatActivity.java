package com.example.cmu_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.adevinta.leku.LocationPickerActivity;
import com.example.cmu_project.R;
import com.example.cmu_project.grpc_tasks.CreateChatGrpcTask;
import com.example.cmu_project.helpers.GlobalVariableHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class CreateChatActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(CreateChatActivity.class.getName());
    private static final int REQUEST_LOCATION_PICKER = 1;

    Button sendRequest;
    EditText chat_name;
    RadioButton current_checked;

    double chat_latitude;
    double chat_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);

        //Buttons
        sendRequest = (Button) findViewById(R.id.create_chatroom);

        //TextFields
        chat_name = (EditText) findViewById(R.id.chat_name);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION_PICKER && resultCode == RESULT_OK) {
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);

            chat_latitude = latitude;
            chat_longitude = longitude;

            String geolocation = latitude + "/" + longitude;
            System.out.println("Location is: " + geolocation);

        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

    public void create(View view) {

        String new_chat_name = chat_name.getText().toString();
        String type_of_chat = current_checked.getText().toString();
        EditText radiusId = (EditText) findViewById(R.id.radiusId);
        String radius = radiusId.getText().toString();

        if (type_of_chat.equals("GeoFanced")) {
            new CreateChatGrpcTask(this,new_chat_name,String.valueOf(chat_latitude),String.valueOf(chat_longitude),radius).execute(((GlobalVariableHelper) this.getApplication()).getUsername(),type_of_chat);
        } else {
            new CreateChatGrpcTask(this,new_chat_name).execute(((GlobalVariableHelper) this.getApplication()).getUsername(),type_of_chat);
        }
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
            case R.id.radio_geofenced:
                if (checked) {
                    current_checked = (RadioButton) findViewById(R.id.radio_geofenced);
                    findViewById(R.id.radiusId).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewRadius).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewLocation).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonMyLocation).setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void openMapOnCurrentLocation(View view) throws PackageManager.NameNotFoundException {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        AtomicReference<Double> userX = new AtomicReference<>((double) 0);
        AtomicReference<Double> userY = new AtomicReference<>((double) 0);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            openMap(userX.get(), userY.get());
        }

        mFusedLocationClient.getLastLocation().addOnCompleteListener(location -> {
            if (location.getResult() != null) {
                userX.set(location.getResult().getLatitude());
                userY.set(location.getResult().getLongitude());

                try {
                    openMap(userX.get(), userY.get());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void openMap(Double userX, Double userY) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = applicationInfo.metaData;
        String apiKey = bundle.getString("com.google.android.geo.API_KEY");

        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withLocation(userX, userY)
                .withGeolocApiKey(apiKey)
                .withSearchZone("en_EN")
                .withDefaultLocaleSearchZone()
                .shouldReturnOkOnBackPressed()
                .withStreetHidden()
                .withCityHidden()
                .withZipCodeHidden()
                .withSatelliteViewHidden()
                .withGoogleTimeZoneEnabled()
                .withVoiceSearchHidden()
                .withUnnamedRoadHidden()
                .build(getApplicationContext());

        try {
            startActivityForResult(locationPickerIntent, REQUEST_LOCATION_PICKER);
        } catch (ActivityNotFoundException e) {
            logger.warning(e.getMessage());
        }
    }

}