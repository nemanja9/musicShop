/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.dto;

import fon.silab.web.an.ainmusicshop.entity.OrderEntity.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lj
 */

public class OrderDto {
    
    private int orderId;
    private UserDto userDto;
    private Status orderStatus;
    private Date orderDate;
    private Date paidDate;
    private Date shippedDate;
    private List<OrderItemDto> orderItems;
    private String token;
    private String paymentId;

    public OrderDto() {
        orderItems = new ArrayList<>();
    }

    public OrderDto(int orderId, UserDto userDto, Status orderStatus, Date orderDate, Date paidDate, Date shippedDate, List<OrderItemDto> orderItems, String token, String paymentId) {
        super();
        this.orderId = orderId;
        this.userDto = userDto;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.paidDate = paidDate;
        this.shippedDate = shippedDate;
        this.orderItems = orderItems;
        this.token = token;
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    
    

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderDto{" + "orderId=" + orderId + ", userDto=" + userDto + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", paidDate=" + paidDate + ", shippedDate=" + shippedDate + ", orderItems=" + orderItems + '}';
    }
    
    
}
