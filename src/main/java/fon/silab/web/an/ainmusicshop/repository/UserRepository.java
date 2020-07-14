/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.repository;

import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import java.util.List;

/**
 *
 * @author lj
 */
public interface UserRepository {
    
    void save(UserEntity userEntity);
    List<UserEntity> getAll();
    UserEntity findByNumber(int numberId);
    void delete(int numberId);
    void update(UserEntity user);
    UserEntity findByEmail(String name);
}
