package com.drpro.laundryin;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.drpro.laundryin.Common.Common;
import com.drpro.laundryin.Interface.OrderClickListener;
import com.drpro.laundryin.Model.Order;
import com.drpro.laundryin.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private PagerAdapter viewTextAdapter;
    private Fragment demoFragments;

    TabHost tabHost;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private FirebaseRecyclerAdapter<Order, OrderViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        viewTextAdapter = new TextAdapter(getActivity().getSupportFragmentManager());

        tabHost = (TabHost)view.findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("In Progress");
        spec.setContent(R.id.ongoing);
        spec.setIndicator("In Progress");
        tabHost.addTab(spec);

        //Tab2
        spec = tabHost.newTabSpec("Completed");
        spec.setContent(R.id.completed);
        spec.setIndicator("Completed");
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

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference("/Users/" + Common.currentUser.getPhone().toString() + "/Orders/");
        // [END create_database_reference]

        mRecycler = view.findViewById(R.id.ongoing);
        mRecycler.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);

        //load data
        getdatafromfirebase();

        return view;
    }

    private void getdatafromfirebase() {

        mAdapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class,R.layout.item_order_layout,OrderViewHolder.class,mDatabase){
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Order model, int position) {
                viewHolder.orderNumberView.setText("No. Order : #" + String.valueOf(position + 1));
                //viewHolder.userView.setText("Nama: " + model.getUser());
                //viewHolder.locationView.setText("Lokasi: " + model.getLocation());
                //viewHolder.orderNotesView.setText("Catatan: " + model.getOrderNotes());
                viewHolder.orderDateView.setText("Tgl Order: " + model.getOrderDate());
               // viewHolder.etaDateView.setText("Tgl. Ambil: " + model.getEtaDate());
                viewHolder.orderTypeView.setText("Tipe: " + model.getOrderType());

                viewHolder.setOrderClickListener(new OrderClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        Common.currentOrder = model;
                        //Toast.makeText(getActivity(), "order clicked", Toast.LENGTH_SHORT).show();
                        Intent orderDetail = new Intent(getContext(), OrderDetailActivity.class);
                        startActivity(orderDetail);
                        //getActivity().finish();
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

    }

    class TextAdapter extends FragmentPagerAdapter {

        public TextAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return demoFragments;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

}
