/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository.impl;

import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.repository.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    @Override
    public List<UserEntity> getAll() {
        String query = "select u from users u";
        return entityManager.createQuery(query, UserEntity.class).getResultList();

    }

    @Override
    public UserEntity findByNumber(int numberId) {
        return entityManager.find(UserEntity.class, numberId);
    }

    @Override
    public void delete(int numberId) {
        UserEntity userEntity = entityManager.find(UserEntity.class, numberId);
        entityManager.remove(userEntity);
    }

    @Override
    public void update(UserEntity user) {
        entityManager.merge(user);
    }

    @Override
    public UserEntity findByEmail(String name) {
        String query = "select u from users u where email = '"+name+"'";
        UserEntity u = null;
        try{
            u = entityManager.createQuery(query, UserEntity.class).getSingleResult();
        }catch(Exception e){
            e.getMessage();
        }
        if(u != null){
            System.out.println("ASDF " + u);
            return u;
        } else{
                        System.out.println("FDSA NULL");

            return null;
        }
    }

}
