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

import com.hold1.pagertabsindicator.PagerTabsIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private ViewPager viewPager;
    private PagerTabsIndicator tabsIndicator;
    private PagerAdapter viewTextAdapter;
    private Fragment demoFragments;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabsIndicator = view.findViewById(R.id.tabs_indicator);

        viewTextAdapter = new TextAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(viewTextAdapter);
        tabsIndicator.setViewPager(viewPager);

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
