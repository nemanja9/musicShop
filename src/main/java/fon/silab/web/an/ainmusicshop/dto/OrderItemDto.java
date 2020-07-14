/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.dto;

/**
 *
 * @author lj
 */
public class OrderItemDto {
    
    private int itemId;
    private OrderDto orderDto;
    private ProductDto product;
    private int quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(int itemId, OrderDto orderDto, ProductDto product, int quantity) {
        this.itemId = itemId;
        this.orderDto = orderDto;
        this.product = product;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" + "itemId=" + itemId + ", orderDto=" + orderDto + ", product=" + product + ", quantity=" + quantity + '}';
    }
    
    
    
    
}
