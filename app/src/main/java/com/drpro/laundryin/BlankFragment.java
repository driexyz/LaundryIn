package com.drpro.laundryin;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.drpro.laundryin.Adapter.OrderAdapter;
import com.drpro.laundryin.Common.Common;
import com.drpro.laundryin.Interface.ILoadMore;
import com.drpro.laundryin.Model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private PagerAdapter viewTextAdapter;
    private Fragment demoFragments;

    TabHost tabHost;

    List<Order> orders = new ArrayList<>();
    OrderAdapter adapter;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

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

        //set dynamic data
        getdatafromfirebase();

        //init view
        RecyclerView recyclerView = view.findViewById(R.id.ongoing);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderAdapter(recyclerView,getActivity(),orders);
        recyclerView.setAdapter(adapter);

        //setLoadMore

        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(orders.size() <= 50)
                {
                    orders.add(null);
                    adapter.notifyItemInserted(orders.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            orders.remove(orders.size()-1);
                            adapter.notifyItemRemoved(orders.size());

                            int index = orders.size();
                            int end = index+10;

                            for(int i=index;i<end;i++)
                            {
                                Order item = new Order(Common.currentUser.getName().toString() + i);
                                orders.add(item);
                            }

                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },2000);
                }else{
                    Toast.makeText(getActivity(),"load berhasil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getdatafromfirebase() {

        for(int i=0;i<10;i++)
        {
            Order item = new Order(Common.currentUser.getName().toString() + i);
            orders.add(item);
        }

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
