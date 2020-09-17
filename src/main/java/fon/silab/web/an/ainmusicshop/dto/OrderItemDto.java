/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.dto;

import java.util.ArrayList;

/**
 *
 * @author lj
 */
public class OrderItemDto {

    private int itemId;
    private int orderId;
    private ProductDto product;
    private int quantity;
    private double itemPrice;

    public ArrayList<OrderItemDto> add(ArrayList<OrderItemDto> lista) {
        boolean postoji = false;
        OrderItemDto pom = new OrderItemDto();

        for (OrderItemDto orderItemDto : lista) {
            if (product.getProductId() == orderItemDto.getProduct().getProductId()) {
                postoji = true;
                orderItemDto.setQuantity(orderItemDto.getQuantity() + quantity);
                break;
            }
        }
        if (!postoji) {
            pom.setProduct(product);
            pom.setQuantity(quantity);
            pom.setItemPrice(product.getPrice());
            lista.add(pom);
        }
        return lista;

    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public OrderItemDto() {
    }

    public OrderItemDto(int itemId, int orderDto, ProductDto product, int quantity) {
        this.itemId = itemId;
        this.orderId = orderDto;
        this.product = product;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
        return product + ", quantity=" + quantity + '.';
    }

}
