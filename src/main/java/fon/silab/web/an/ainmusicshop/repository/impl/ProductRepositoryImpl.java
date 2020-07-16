/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository.impl;

import fon.silab.web.an.ainmusicshop.entity.ProductEntity;
import fon.silab.web.an.ainmusicshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lj
 */
@Component
@Transactional(propagation = Propagation.MANDATORY)
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(ProductEntity product) {
        entityManager.persist(product);
    }

    @Override
    public List<ProductEntity> getAll() {
        String query = "select p from products p";
        return entityManager.createQuery(query, ProductEntity.class).getResultList();
    }
    
     @Override
    public List<ProductEntity> getSome(String category, String orderBy, String manufacturer, String max, String min, String name) {
        if(name == null) name = "%";
        if (category == null) category = "%";
        if (manufacturer == null) manufacturer = "%";
        String query = "select p from products p where category like '"+ category +"'" + " and product_name like'%" + name +  "%'" +
                " and manufacturer like '" + manufacturer + "'" + (max!=null?" and " + max:" ") + (min!=null?" and " + min:" ") + (orderBy==null?"":" order by "+orderBy);
         System.out.println(query);
        return entityManager.createQuery(query, ProductEntity.class).getResultList();
    }
    
    
    @Override
    public ArrayList<String> getManufacturers() {
        String query = "SELECT DISTINCT manufacturer FROM products p";
        return (ArrayList<String>) entityManager.createQuery(query, String.class).getResultList();
        
    }
    
    @Override
    public int getHighestPrice() {
        String query = "select max(p.price) from products p";
        if(entityManager.createQuery(query, String.class).getSingleResult() != null){
            return Integer.parseInt(entityManager.createQuery(query, String.class).getSingleResult());
        }else{
            return Integer.MIN_VALUE;
        }
    }
    
     @Override
    public List<ProductEntity> getCategory(String category) {
        String query = "select p from products p where category='" + category + "'";
         System.out.println(query);
        return entityManager.createQuery(query, ProductEntity.class).getResultList();
    }

    @Override
    public ProductEntity findByNumber(int numberId) {
        return entityManager.find(ProductEntity.class, numberId);
    }

    @Override
    public void delete(int numberId) {
        ProductEntity productEntity = entityManager.find(ProductEntity.class, numberId);
        System.out.println(productEntity.toString());
        entityManager.remove(productEntity);
            

    }

    @Override
    public void update(ProductEntity product) {
        System.out.println("IDDDDDDDDDD " + product.getProductId());
        entityManager.merge(product);
    }

    @Override
    public ProductEntity findByName(String name) {
        String query = "select p from products p where product_name = '"+name+"'";
        return entityManager.createQuery(query, ProductEntity.class).getSingleResult();
    }

    @Override
    public int findMax() {
        String query = "select max(p.productId) from products p";
        if(entityManager.createQuery(query, Integer.class).getSingleResult() != null){
            return entityManager.createQuery(query, Integer.class).getSingleResult();
        }else{
            return 0;
        }
        
    }

}
