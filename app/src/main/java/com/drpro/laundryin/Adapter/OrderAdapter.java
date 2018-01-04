package com.drpro.laundryin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.drpro.laundryin.Model.Order;
import com.drpro.laundryin.Model.TitleOrder;
import com.drpro.laundryin.R;
import com.drpro.laundryin.ViewHolder.OrderViewHolder2;
import com.drpro.laundryin.ViewHolder.TitleOrderViewHolder;

import java.util.List;

/**
 * Created by deri.resdiana on 1/3/2018.
 */

public class OrderAdapter extends ExpandableRecyclerAdapter<TitleOrder, Order, TitleOrderViewHolder, OrderViewHolder2> {

    private LayoutInflater mInflater;
    private List<TitleOrder> mTitleOrderList;

    public OrderAdapter(Context context, @NonNull List<TitleOrder> titleOrderList) {
        super(titleOrderList);
        mTitleOrderList = titleOrderList;
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public TitleOrderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View titleOrderView;
        titleOrderView = mInflater.inflate(R.layout.item_title_order_layout, parentViewGroup, false);

        return new TitleOrderViewHolder(titleOrderView);
    }

    @UiThread
    @NonNull
    @Override
    public OrderViewHolder2 onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View orderView;
        orderView = mInflater.inflate(R.layout.item_order_layout, childViewGroup, false);
        return new OrderViewHolder2(orderView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull TitleOrderViewHolder titleOrderViewHolder, int parentPosition, @NonNull TitleOrder titleOrder) {
        titleOrderViewHolder.bind(titleOrder);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull OrderViewHolder2 orderViewHolder2, int parentPosition, int childPosition, @NonNull Order order) {
        orderViewHolder2.bind(order);
    }

}
