/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service;

import fon.silab.web.an.ainmusicshop.dto.ProductDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lj
 */

public interface ProductService {
   
    void save(ProductDto productDto);
    List<ProductDto> getAll();
    List<ProductDto> getSome(String category, String orderBy, String manufacturer, String max, String min, String name);
    List<ProductDto> getCategory(String category);
    double getHighestPrice();
    ProductDto getOne(int numberId);
    void delete(int numberId);
    void update(ProductDto product);
    ProductDto findByName(String name);

    public ArrayList<String> getAllManufacturers();
    int getMax();
}
