package com.drpro.laundryin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.drpro.laundryin.Model.Order;
import com.drpro.laundryin.R;

/**
 * Created by deri.resdiana on 1/3/2018.
 */

public class OrderViewHolder2 extends ChildViewHolder {

    private TextView userView;
    private TextView locationView;
    private TextView orderNotesView;
    private TextView orderDateView;
    private TextView etaDateView;
    private TextView orderTypeView;

    public OrderViewHolder2(View itemView) {
        super(itemView);

        userView = itemView.findViewById(R.id.txtUserView);
        locationView = itemView.findViewById(R.id.txtLocationView);
        orderNotesView = itemView.findViewById(R.id.txtOrderNotesView);
        orderDateView = itemView.findViewById(R.id.txtOrderDateView);
        etaDateView = itemView.findViewById(R.id.txtEtaDateView);
        orderTypeView = itemView.findViewById(R.id.txtOrderTypeView);
    }

    public void bind(Order orders) {
        userView.setText("Nama: " + orders.getUser());
        locationView.setText("Lokasi: " + orders.getLocation());
        orderNotesView.setText("Catatan: " + orders.getOrderNotes());
        orderDateView.setText("Tgl Order: " + orders.getOrderDate());
        etaDateView.setText("Tgl. Ambil: " + orders.getEtaDate());
        orderTypeView.setText("Tipe: " + orders.getOrderType());
    }
}
