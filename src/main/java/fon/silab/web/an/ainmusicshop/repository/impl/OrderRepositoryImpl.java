/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository.impl;

import fon.silab.web.an.ainmusicshop.entity.OrderEntity;
import fon.silab.web.an.ainmusicshop.repository.OrderRepository;
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
public class OrderRepositoryImpl implements OrderRepository{

    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public void save(OrderEntity order) {
        entityManager.persist(order);
    }

    @Override
    public List<OrderEntity> getAll() {
        String query = "select o from orders o";
        return entityManager.createQuery(query, OrderEntity.class).getResultList();
    }

    @Override
    public OrderEntity findByNumber(int numberId) {
        return entityManager.find(OrderEntity.class, numberId);
    }

    @Override
    public void delete(int numberId) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, numberId);
        entityManager.remove(orderEntity);
    }

    @Override
    public void update(OrderEntity order) {
        entityManager.merge(order);
    }

    @Override
    public String getUserByOrderID(int id) {
        String query = "SELECT u.email FROM users u JOIN orders o ON u.userId = o.userEntity WHERE o.orderId = " + id;
        return entityManager.createQuery(query, String.class).getSingleResult();
    }

    @Override
    public List<OrderEntity> getAllForUser(int id) {
            String query = "select o from orders o where o.userEntity = "+ id;
        return entityManager.createQuery(query, OrderEntity.class).getResultList();    

    }

    
    
    
    
}
