/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lj
 */
@Entity (name = "order_items")
@Table(name = "order_items")
public class OrderItemEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", unique = true, nullable = false)
    private int itemId;
    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private OrderEntity orderEntity;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private ProductEntity product;
    
    @Column(name = "quantity", nullable = false, length = 50, columnDefinition="Integer default '0'")
    private int quantity;

    public OrderItemEntity() {
    }

    public OrderItemEntity(int itemId, ProductEntity product, int quantity) {
        super();
        this.itemId = itemId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getOrder() {
        return orderEntity;
    }

    public void setOrder(OrderEntity order) {
        this.orderEntity = order;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.itemId);
        hash = 79 * hash + Objects.hashCode(this.product);
        hash = 79 * hash + Objects.hashCode(this.quantity);
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
        final OrderItemEntity other = (OrderItemEntity) obj;
        if (!Objects.equals(this.itemId, other.itemId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{" + "itemId=" + itemId + ", product=" + product + ", quantity=" + quantity + '}';
    }

    
    
    
    
}
