/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository;

import fon.silab.web.an.ainmusicshop.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lj
 */

public interface ProductRepository {
    
    void save(ProductEntity product);
    List<ProductEntity> getAll();
    List<ProductEntity> getCategory(String category);
    List<ProductEntity> getSome(String category, String orderBy, String manufacturer, String max, String min, String name);
    int getHighestPrice ();
    ProductEntity findByNumber(int numberId);
    void delete(int numberId);
    void update(ProductEntity product);
    ProductEntity findByName(String name);

    public ArrayList<String> getManufacturers();
    int findMax();
}