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
    public Boolean isComplete;
    public String price;
    public String waktuAmbil;
    public String metodePembayaran;

    public Order(String user, String location, String orderNotes, String orderDate, String etaDate, String orderType, Boolean isPremium, String price, String waktuAmbil, String metodePembayaran) {
        this.user = user;
        this.location = location;
        this.orderNotes = orderNotes;
        this.orderDate = orderDate;
        this.etaDate = etaDate;
        this.orderType = orderType;
        this.isPremium = isPremium;
        this.price = price;
        this.waktuAmbil = waktuAmbil;
        this.metodePembayaran = metodePembayaran;
        this.isComplete = false;
    }

    public Order(String user) {
        this.user = user;
    }

    public Order() {
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
        result.put("price", price);
        result.put("waktuAmbil", waktuAmbil);
        result.put("metodePembayaran", metodePembayaran);

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

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWaktuAmbil() {
        return waktuAmbil;
    }

    public void setWaktuAmbil(String waktuAmbil) {
        this.waktuAmbil = waktuAmbil;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
}
