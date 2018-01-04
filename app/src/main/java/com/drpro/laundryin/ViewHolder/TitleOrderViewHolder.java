package com.drpro.laundryin.ViewHolder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.drpro.laundryin.Model.TitleOrder;
import com.drpro.laundryin.R;

/**
 * Created by deri.resdiana on 1/3/2018.
 */

public class TitleOrderViewHolder extends ParentViewHolder {

    private TextView mTitleTextView;

    public TitleOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTextView = itemView.findViewById(R.id.txtOrderNumber);
    }

    public void bind(TitleOrder titleOrder) {
        mTitleTextView.setText("Order Number");
    }
}
