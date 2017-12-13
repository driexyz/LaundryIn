package com.drpro.laundryin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.hold1.pagertabsindicator.PagerTabsIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private PagerAdapter viewTextAdapter;
    private Fragment demoFragments;

    TabHost tabHost;

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
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab Ongoing");
        spec.setContent(R.id.ongoing);
        spec.setIndicator("Tab Ongoing");
        tabHost.addTab(spec);

        //Tab2
        spec = tabHost.newTabSpec("Tab Complete");
        spec.setContent(R.id.complete);
        spec.setIndicator("Tab Complete");
        tabHost.addTab(spec);

        //Event
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });

        return view;
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
