package com.drpro.laundryin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.drpro.laundryin.Interface.OrderClickListener;
import com.drpro.laundryin.R;

/**
 * Created by deri.resdiana on 1/2/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView orderNumberView;
    public TextView userView;
    public TextView locationView;
    public TextView orderNotesView;
    public TextView orderDateView;
    public TextView etaDateView;
    public TextView orderTypeView;

    private OrderClickListener orderClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        orderNumberView = itemView.findViewById(R.id.txtOrderNumber);
        //userView = itemView.findViewById(R.id.txtUserView);
        //locationView = itemView.findViewById(R.id.txtLocationView);
        //orderNotesView = itemView.findViewById(R.id.txtOrderNotesView);
        orderDateView = itemView.findViewById(R.id.txtOrderDateView);
        //etaDateView = itemView.findViewById(R.id.txtEtaDateView);
        orderTypeView = itemView.findViewById(R.id.txtOrderTypeView);

        itemView.setOnClickListener(this);
    }

    public void setOrderClickListener(OrderClickListener orderClickListener)
    {
        this.orderClickListener = orderClickListener;
    }

    @Override
    public void onClick(View v) {
        orderClickListener.onClick(v, getAdapterPosition(), false);

    }
}
