/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lj
 */

@Entity(name = "orders")
@Table(name = "orders")
public class OrderEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    private int orderId;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity userEntity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 256)
    private Status orderStatus;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date", length = 10)
    private Date orderDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "required_date", length = 10)
    private Date requiredDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "shipped_date", length = 10)
    private Date shippedDate;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, targetEntity=OrderItemEntity.class)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private List<OrderItemEntity> orderItems;

    public OrderEntity() {
        orderItems = new ArrayList<>();
    }

    public OrderEntity(int orderId, UserEntity user, Status orderStatus, Date orderDate, Date requiredDate, Date shippedDate, List<OrderItemEntity> orderItems) {
        super();
        this.orderId = orderId;
        this.userEntity = user;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity user) {
        this.userEntity = user;
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

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.orderId;
        hash = 97 * hash + Objects.hashCode(this.userEntity);
        hash = 97 * hash + Objects.hashCode(this.orderDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderEntity other = (OrderEntity) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderEntity{" + "orderId=" + orderId + ", user=" + userEntity + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate + ", orderItems=" + orderItems + '}';
    }

    public enum Status {
        SPAKOVANO,
        POSLATO,
        ISPORUCENO
    }
    
   
}