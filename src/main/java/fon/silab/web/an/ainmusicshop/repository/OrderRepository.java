/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository;

import fon.silab.web.an.ainmusicshop.entity.OrderEntity;
import java.util.List;

/**
 *
 * @author lj
 */
public interface OrderRepository {
    
    void save(OrderEntity order);
    List<OrderEntity> getAll();
    OrderEntity findByNumber(int numberId);
    void delete(int numberId);
    void update(OrderEntity order);
    
}
