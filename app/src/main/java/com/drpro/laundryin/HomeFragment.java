package com.drpro.laundryin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private TextView
            mTextLocation,
            mTextCurDate,
            mTextETADate;

    public static final int REQUEST_CODE = 1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //mTextMessage = (TextView) findViewById(R.id.message);
        mTextLocation = (TextView)view.findViewById(R.id.txtLocation);
        mTextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMaps = new Intent(getActivity(),MapsActivity.class);
                startActivityForResult(openMaps, REQUEST_CODE);
            }
        });

        mTextCurDate = (TextView) view.findViewById(R.id.currentDate);
        mTextETADate = (TextView) view.findViewById(R.id.etaDate);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.day_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mTextCurDate.setText(getCurrentDate());
        mTextETADate.setText(getEstimateDate());

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                mTextLocation.setText(data.getStringExtra("pos"));
                // do something with the result

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }

}
