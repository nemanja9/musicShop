/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.validator;

import fon.silab.web.an.ainmusicshop.dto.ProductDto;
import fon.silab.web.an.ainmusicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author lj
 */
@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ProductDto.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ProductDto productDto = (ProductDto) o;
        System.out.println("Validate product: " + productDto);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "productDto.category.empty", "productDto.category.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "productDto.name.empty", "productDto.name.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "productDto.description.empty", "productDto.description.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manufacturer", "productDto.manufacturer.empty", "productDto.manufacturer.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "productDto.price.empty", "productDto.price.empty = Default message");
        ValidationUtils.rejectIfEmpty(errors, "img", "productDto.img.empty", "productDto.img.empty = Default message");

        if (errors.hasErrors()) {
            return;
        }

        if (productDto.getImg() == null || productDto.getImg().isEmpty()) {
            errors.rejectValue("img", "productDto.img.exist", "productDto.img.exist = Default message");
        }

        if (!isNumeric(productDto.getPrice())){
            errors.rejectValue("price", "productDto.price.number", "productDto.price.number = Default message");
        }
        
        if (productDto.getPrice().length() > 11){
            errors.rejectValue("price", "productDto.price.length", "productDto.price.length = Default message");
        }
        
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); 
    }
}
