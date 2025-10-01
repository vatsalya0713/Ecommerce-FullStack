package com.genie.Ecomm.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private String userName;
    private String email;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(Long id, double totalAmount, String status, Date orderDate, String userName, String email, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.userName = userName;
        this.email = email;
        this.orderItems = orderItems;
    }

    public OrderDTO(Long id, double totalAmount, String status, Date orderDate, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;


        this.orderItems = orderItems;
    }
}