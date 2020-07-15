/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.dto;

import fon.silab.web.an.ainmusicshop.entity.ProductEntity.Category;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lj
 */
public class ProductDto {
    
    private int productId;
    private Category category;
    private String productName;
    private String price;
    private String manufacturer;
    private String description;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    private MultipartFile img;

    public ProductDto() {
    }

    public ProductDto(int productId, Category category, String productName, String price,String manufacturer, String description) {
        this.productId = productId;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.manufacturer = manufacturer;
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ProductDto{" + "productId=" + productId + ", category=" + category + ", productName=" + productName + ", price=" + price + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    
    
}
