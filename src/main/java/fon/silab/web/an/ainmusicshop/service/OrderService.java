/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import java.util.List;

/**
 *
 * @author lj
 */
public interface OrderService {
    
    void save(OrderDto order);
    List<OrderDto> getAll();
    OrderDto findByNumber(int numberId);
    void delete(int numberId);
    void update(OrderDto order);

    public List<OrderDto> getAllForUser(int id);
}
