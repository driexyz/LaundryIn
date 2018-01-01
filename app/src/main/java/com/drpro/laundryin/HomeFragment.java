package com.drpro.laundryin;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.drpro.laundryin.Common.Common;
import com.drpro.laundryin.Model.Order;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    private TextView
            mTextNotes,
            mTextCurDate,
            mTextETADate;

    private EditText mTextLocation;

    private BannerSlider bannerSlider;

    TabHost tabHost;

    public static final int REQUEST_CODE = 1;

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    MaterialFancyButton btnOrder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //mTextMessage = (TextView) findViewById(R.id.message);
        mTextLocation = (EditText)view.findViewById(R.id.txtLocation);
        mTextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent openMaps = new Intent(getActivity(),MapsActivity.class);
                //startActivityForResult(openMaps, REQUEST_CODE);

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        mTextNotes = (TextView) view.findViewById(R.id.txtInfo1);
        mTextCurDate = (TextView) view.findViewById(R.id.currentDate);
        mTextETADate = (TextView) view.findViewById(R.id.etaDate);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.day_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mTextCurDate.setText(getCurrentDate());
        mTextETADate.setText(getEstimateDate());

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider1);
        addBanners();

        tabHost = (TabHost)view.findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Standard");
        spec.setContent(R.id.free);
        spec.setIndicator("Standard");
        tabHost.addTab(spec);

        //Tab2
        spec = tabHost.newTabSpec("Premium");
        spec.setContent(R.id.premium);
        spec.setIndicator("Premium");
        tabHost.addTab(spec);

        int titleColor = Color.WHITE; //<-- change this to the color you want the title text to be
        for(int i = 0;i < tabHost.getTabWidget().getChildCount(); i++)
        {
            TextView textView = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            textView.setTextColor(titleColor);
        }

        //Event
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        btnOrder = view.findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        return view;
    }

    private void submitOrder() {
        final String user = Common.currentUser.getName();
        final String location = mTextLocation.getText().toString();
        final String notes = mTextNotes.getText().toString();
        final String curdate = mTextCurDate.getText().toString();
        final String etadate = mTextETADate.getText().toString();
        final String ordertype = "Paket Hemat";
        final Boolean isPremium = false;

        // location is required
        if (TextUtils.isEmpty(location)) {
            mTextLocation.setError("REQUIRED");
            return;
        }

        // notes is required
        if (TextUtils.isEmpty(notes)) {
            mTextNotes.setError("REQUIRED");
            return;
        }

        Order orders = new Order(user, location, notes, curdate, etadate, ordertype, isPremium);

        String key = mDatabase.child("orders").push().getKey();
        Map<String, Object> postValues = orders.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/orders/" + key, postValues);
        childUpdates.put("/user-orders/" + key, postValues);
        mDatabase.updateChildren(childUpdates);

        Toast.makeText(getActivity(), "Pesanan telah dikirim, mohon tunggu !", Toast.LENGTH_SHORT).show();
        mTextLocation.setText("");
        mTextNotes.setText("");

    }

    public String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String formattedDate = df.format(c.getTime());
        // Now formattedDate have current date/time
        return formattedDate;
    }

    public String getEstimateDate()
    {
        Calendar cNow = Calendar.getInstance();
        Calendar cEta = Calendar.getInstance();
        cEta.setTime(cNow.getTime());
        cEta.add(Calendar.DATE, 2);

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String formattedDate = df.format(cEta.getTime());
        // Now formattedDate have current date/time
        return formattedDate;
    }

    private void addBanners(){
        List<Banner> remoteBanners=new ArrayList<>();
        //Add banners using image urls
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/4b88d2c1-9f95-4c51-867b-bf977b0caa8c/preview.gif"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/05e9b7d9-ade2-4aed-9cb4-9e24e5a3530d/preview.jpg"
        ));
        bannerSlider.setBanners(remoteBanners);

    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                mTextLocation.setText(data.getStringExtra("pos"));
                // do something with the result

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                /*stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");*/
                stBuilder.append("Address: ");
                stBuilder.append(address);
                mTextLocation.setText(stBuilder.toString());
            }
        }
    }

}
