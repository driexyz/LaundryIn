package com.drpro.laundryin;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.drpro.laundryin.Common.Common;
import com.drpro.laundryin.Model.Order;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
            mTextETADate,
            mTextNotes2,
            mTextCurDate2,
            mTextETADate2;

    private EditText mTextLocation, mTextLocation2;

    private BannerSlider bannerSlider;

    TabHost tabHost;

    public static final int REQUEST_CODE = 1;

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;

    private Spinner spinnerPaket,
                    spinnerAmbil,
                    spinnerPaket2,
                    spinnerAmbil2;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    MaterialFancyButton btnOrder, btnOrder2;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //===========================================================================================
        // Init view for Paket Hemat
        //===========================================================================================

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

        spinnerPaket = (Spinner) view.findViewById(R.id.spinnerPaket);
        ArrayAdapter<CharSequence> adapterPaket = ArrayAdapter.createFromResource(getActivity(),
                R.array.day_list, android.R.layout.simple_spinner_item);
        adapterPaket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaket.setAdapter(adapterPaket);

        spinnerAmbil = (Spinner) view.findViewById(R.id.spinnerAmbil);
        ArrayAdapter<CharSequence> adapterAmbil = ArrayAdapter.createFromResource(getActivity(),
                R.array.ambil_list, android.R.layout.simple_spinner_item);
        adapterAmbil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmbil.setAdapter(adapterAmbil);

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
        spec.setIndicator("Paket Hemat");
        tabHost.addTab(spec);

        //Tab2
        spec = tabHost.newTabSpec("Premium");
        spec.setContent(R.id.premium);
        spec.setIndicator("Premium");
        tabHost.addTab(spec);

        int titleColor = Color.CYAN; //<-- change this to the color you want the title text to be
        for(int i = 0;i < tabHost.getTabWidget().getChildCount(); i++)
        {
            TextView textView = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            textView.setTextColor(getResources().getColor(R.color.accent));
            textView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.lato), Typeface.BOLD);
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
                if (TextUtils.isEmpty(mTextLocation.getText().toString())) {
                    mTextLocation.setError("REQUIRED");
                    Toast.makeText(getActivity(), "Mohon isi lokasi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
                else {
                    openPopupPayment();
                }
            }

        });

        //===========================================================================================
        // Init view for Premium
        //===========================================================================================

        //mTextMessage = (TextView) findViewById(R.id.message);
        mTextLocation2 = (EditText)view.findViewById(R.id.txtLocation2);
        mTextLocation2.setOnClickListener(new View.OnClickListener() {
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

        mTextNotes2 = (TextView) view.findViewById(R.id.txtInfo12);
        mTextCurDate2 = (TextView) view.findViewById(R.id.currentDate2);
        mTextETADate2 = (TextView) view.findViewById(R.id.etaDate2);

        spinnerPaket2 = (Spinner) view.findViewById(R.id.spinnerPaket2);
        ArrayAdapter<CharSequence> adapterPaket2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.day_list, android.R.layout.simple_spinner_item);
        adapterPaket2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaket2.setAdapter(adapterPaket2);

        spinnerAmbil2 = (Spinner) view.findViewById(R.id.spinnerAmbil2);
        ArrayAdapter<CharSequence> adapterAmbil2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.ambil_list, android.R.layout.simple_spinner_item);
        adapterAmbil2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmbil2.setAdapter(adapterAmbil2);

        mTextCurDate2.setText(getCurrentDate());
        mTextETADate2.setText(getEstimateDate());

        btnOrder2 = view.findViewById(R.id.btn_order2);
        btnOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mTextLocation2.getText().toString())) {
                    mTextLocation2.setError("REQUIRED");
                    Toast.makeText(getActivity(), "Mohon isi lokasi anda terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
                else {
                    openPopupPaymentPremium();
                }
            }

        });


        return view;
    }

    private void goToHistoryOrder() {
        BlankFragment bleng = new BlankFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, bleng, "FragmentName");
        ft.commit();
    }

    private void submitOrder(String metodePembayaran) {

        final String user = Common.currentUser.getName();
        final String location = mTextLocation.getText().toString();
        final String notes = mTextNotes.getText().toString();
        final String curdate = mTextCurDate.getText().toString();
        final String etadate = mTextETADate.getText().toString();
        final String paket = spinnerPaket.getSelectedItem().toString() ;
        final String ordertype = "Paket Hemat " + paket;
        final Boolean isPremium = false;
        final String waktuAmbil = spinnerAmbil.getSelectedItem().toString();
        String price = "";

        if(paket.equals("3 Hari"))
        {
            price = "Rp. 5000";
        }
        else if(paket.equals("1 Hari"))
        {
            price = "Rp. 7000";
        }
        else if(paket.equals("6 Jam"))
        {
            price = "Rp. 10000";
        }

        final Order orders = new Order(user, location, notes, curdate, etadate, ordertype, isPremium, "Rp. 10000", waktuAmbil, metodePembayaran);

        mDatabase.child("Users").child(Common.currentUser.getPhone().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                writeToDatabase(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void submitOrderPremium(String metodePembayaran) {

        final String user = Common.currentUser.getName();
        final String location = mTextLocation2.getText().toString();
        final String notes = mTextNotes2.getText().toString();
        final String curdate = mTextCurDate2.getText().toString();
        final String etadate = mTextETADate2.getText().toString();
        final String paket = spinnerPaket2.getSelectedItem().toString() ;
        final String ordertype = "Paket Premium " + paket;
        final Boolean isPremium = false;
        final String waktuAmbil = spinnerAmbil2.getSelectedItem().toString();
        String price = "";

        // location is required


        if(paket.equals("3 Hari"))
        {
            price = "Rp. 5000";
        }
        else if(paket.equals("1 Hari"))
        {
            price = "Rp. 7000";
        }
        else if(paket.equals("6 Jam"))
        {
            price = "Rp. 10000";
        }

        /*// notes is required
        if (TextUtils.isEmpty(notes)) {
            mTextNotes.setError("REQUIRED");
            return;
        }*/

        final Order orders = new Order(user, location, notes, curdate, etadate, ordertype, isPremium, "Rp. 10000", waktuAmbil, metodePembayaran);

        mDatabase.child("Users").child(Common.currentUser.getPhone().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                writeToDatabase(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void openPopupPaymentPremium() {

        String retVal;
        new AwesomeSuccessDialog(getContext())
                .setTitle("Metode Pembayaran")
                .setMessage("Mohon pilih metode pembayaran")
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_account_balance_wallet_black_24dp, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText("Langsung")
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white)
                .setNegativeButtonText("Nanti")
                .setNegativeButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                        Toast.makeText(getActivity(), "Pesanan telah dikirim, mohon tunggu !", Toast.LENGTH_SHORT).show();
                        goToHistoryOrder();
                        submitOrderPremium("Langsung");
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                        Toast.makeText(getActivity(), "Pesanan telah dikirim, mohon tunggu !", Toast.LENGTH_SHORT).show();
                        goToHistoryOrder();
                        submitOrderPremium("Nanti");
                    }
                })
                .show();
    }

    private void openPopupPayment() {

        String retVal;
        new AwesomeSuccessDialog(getContext())
                .setTitle("Metode Pembayaran")
                .setMessage("Mohon pilih metode pembayaran")
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_account_balance_wallet_black_24dp, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText("Langsung")
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white)
                .setNegativeButtonText("Nanti")
                .setNegativeButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                        Toast.makeText(getActivity(), "Pesanan telah dikirim, mohon tunggu !", Toast.LENGTH_SHORT).show();
                        goToHistoryOrder();
                        submitOrder("Langsung");
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                        Toast.makeText(getActivity(), "Pesanan telah dikirim, mohon tunggu !", Toast.LENGTH_SHORT).show();
                        goToHistoryOrder();
                        submitOrder("Nanti");
                    }
                })
                .show();
    }

    private void writeToDatabase(Order orders) {
        String key = mDatabase.child("Orders").push().getKey();
        Map<String, Object> postValues = orders.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( "/Users/" + Common.currentUser.getPhone().toString() + "/Orders/" + key, postValues);
        childUpdates.put("/Users-order/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
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
                if(tabHost.getCurrentTab() == 0)
                {
                    mTextLocation.setText(stBuilder.toString());
                }
                else if (tabHost.getCurrentTab() == 1)
                {
                    mTextLocation2.setText(stBuilder.toString());
                }
            }
        }
    }

}
