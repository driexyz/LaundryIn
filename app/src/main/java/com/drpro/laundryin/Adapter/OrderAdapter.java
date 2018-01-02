package com.drpro.laundryin.Adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.drpro.laundryin.Interface.ILoadMore;
import com.drpro.laundryin.Model.Order;
import com.drpro.laundryin.R;

/**
 * Created by deri.resdiana on 1/1/2018.
 */

class LoadingViewHolder extends RecyclerView.ViewHolder
{

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}

class OrderViewHolder extends  RecyclerView.ViewHolder
{

    public TextView orderNumber;

    public OrderViewHolder(View itemView) {
        super(itemView);
        orderNumber = itemView.findViewById(R.id.txtOrderNumber);
    }
}

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_TYPE_LOADING=1, VIEW_TYPE_ITEM=0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Order> orders;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;

    public OrderAdapter(RecyclerView recyclerView, Activity activity, List<Order> orders) {
        this.activity = activity;
        this.orders = orders;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return orders.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_order_layout, parent, false);
            return new OrderViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_order_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof OrderViewHolder)
        {
            Order order = orders.get(position);
            OrderViewHolder viewHolder = (OrderViewHolder) holder;
            viewHolder.orderNumber.setText(order.getUser().toString());
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
