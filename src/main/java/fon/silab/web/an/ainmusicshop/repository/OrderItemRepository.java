/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository;

import fon.silab.web.an.ainmusicshop.entity.OrderItemEntity;
import java.util.List;

/**
 *
 * @author lj
 */
public interface OrderItemRepository {

    void save(OrderItemEntity orderItem);
    List<OrderItemEntity> getAll();
    OrderItemEntity findByNumber(int numberId);
    void delete(int numberId);
    void update(OrderItemEntity orderItem);
}
