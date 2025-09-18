package com.genie.Ecomm.model;

import jakarta.persistence.Entity;

import java.util.Map;

public class OrderRequest {
 private Map<Long,Integer> productQuentities;
 private double totalAmount;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<Long, Integer> getProductQuentities() {
        return productQuentities;
    }

    public void setProductQuentities(Map<Long, Integer> productQuentities) {
        this.productQuentities = productQuentities;
    }
}
