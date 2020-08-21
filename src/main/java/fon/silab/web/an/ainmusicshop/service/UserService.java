/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import java.util.List;

/**
 *
 * @author lj
 */
public interface UserService {
    
    void save(UserDto userDto);
    List<UserDto> getAll();
    UserDto findByNumber(int numberId);
    void delete(int numberId);
    void update(UserDto user);
    UserDto findByEmail(String name);

}
