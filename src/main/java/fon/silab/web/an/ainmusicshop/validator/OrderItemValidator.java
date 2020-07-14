/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.validator;

import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author lj
 */

@Component
public class OrderItemValidator  implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return OrderItemDto.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderItemDto orderItemDto = (OrderItemDto) o;
        System.out.println("Validate order item: "+orderItemDto);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "orderItemDto.quantity.empty", "orderItemDto.quantity.empty = Default message");
    }
    
}
