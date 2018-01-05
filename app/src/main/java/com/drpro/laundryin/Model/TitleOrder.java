package com.drpro.laundryin.Model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by deri.resdiana on 1/3/2018.
 */

public class TitleOrder implements Parent<Order> {

    // a recipe contains several ingredients
    private List<Order> mOrders;
    private int mNumberOrder;

    public TitleOrder(int no, List<Order> orders) {
        mNumberOrder = no;
        mOrders = orders;
    }

    @Override
    public List<Order> getChildList() {
        return mOrders;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
