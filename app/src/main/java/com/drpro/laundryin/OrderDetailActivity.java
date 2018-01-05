package com.drpro.laundryin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.drpro.laundryin.Common.Common;

public class OrderDetailActivity extends AppCompatActivity {

    public TextView userView;
    public TextView locationView;
    public TextView orderNotesView;
    public TextView orderDateView;
    public TextView etaDateView;
    public TextView orderTypeView, waktuAmbilView, totalHargaView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        userView = findViewById(R.id.txtUserView);
        locationView = findViewById(R.id.txtLocationView);
        orderNotesView = findViewById(R.id.txtOrderNotesView);
        orderDateView = findViewById(R.id.txtOrderDateView);
        etaDateView = findViewById(R.id.txtEtaDateView);
        orderTypeView = findViewById(R.id.txtOrderTypeView);
        waktuAmbilView = findViewById(R.id.txtWaktuAmbilView);
        totalHargaView = findViewById(R.id.txtTotalHargaView);

        userView.setText(Common.currentOrder.getUser());
        locationView.setText(Common.currentOrder.getLocation());
        orderNotesView.setText(Common.currentOrder.getOrderNotes());
        orderDateView.setText(Common.currentOrder.getOrderDate());
        etaDateView.setText(Common.currentOrder.getEtaDate());
        orderTypeView.setText(Common.currentOrder.getOrderType());
        waktuAmbilView.setText(Common.currentOrder.getWaktuAmbil());
        totalHargaView.setText(Common.currentOrder.getPrice());



    }
}
