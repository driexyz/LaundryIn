package com.drpro.laundryin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by deri.resdiana on 12/25/2017.
 */

public class Order {

    public String user;
    public String location;
    public String orderNotes;
    public String orderDate;
    public String etaDate;
    public String orderType;
    public Boolean isPremium;

    public Order(String user, String location, String orderNotes, String orderDate, String etaDate, String orderType, Boolean isPremium) {
        this.user = user;
        this.location = location;
        this.orderNotes = orderNotes;
        this.orderDate = orderDate;
        this.etaDate = etaDate;
        this.orderType = orderType;
        this.isPremium = isPremium;
    }

    public Order(String user) {
        this.user = user;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("location", location);
        result.put("orderNotes", orderNotes);
        result.put("orderDate", orderDate);
        result.put("etaDate", etaDate);
        result.put("orderType", orderType);
        result.put("isPremium", isPremium);

        return result;
    }
    // [END post_to_map]

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getEtaDate() {
        return etaDate;
    }

    public void setEtaDate(String etaDate) {
        this.etaDate = etaDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }
}
