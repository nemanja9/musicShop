/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository.impl;

import fon.silab.web.an.ainmusicshop.entity.OrderItemEntity;
import fon.silab.web.an.ainmusicshop.repository.OrderItemRepository;
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
public class OrderItemRepositoryImpl implements OrderItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(OrderItemEntity orderItem) {
        entityManager.persist(orderItem);
    }

    @Override
    public List<OrderItemEntity> getAll() {
        String query = "select o from order_items o";
        return entityManager.createQuery(query, OrderItemEntity.class).getResultList();
    }

    @Override
    public OrderItemEntity findByNumber(int numberId) {
        return entityManager.find(OrderItemEntity.class, numberId);
    }

    @Override
    public void delete(int numberId) {
        OrderItemEntity orderItemEntity = entityManager.find(OrderItemEntity.class, numberId);
        entityManager.remove(orderItemEntity);
    }

    @Override
    public void update(OrderItemEntity orderItem) {
        entityManager.merge(orderItem);
    }

}
