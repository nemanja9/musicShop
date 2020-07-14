/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.service;

import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import java.util.List;

/**
 *
 * @author lj
 */
public interface OrderItemService {
    
    void save(OrderItemDto orderItem);
    List<OrderItemDto> getAll();
    OrderItemDto findByNumber(int numberId);
    void delete(int numberId);
    void update(OrderItemDto orderItem);
}
