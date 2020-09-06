/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.validator;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author andjela
 */
@Component
public class ProfileValidator implements Validator{
    
    private final UserService userService;

    @Autowired
    public ProfileValidator(UserService userService) {
        this.userService = userService;
    }
    
    
    @Override
    public boolean supports(Class<?> type) {
        return UserDto.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;
        System.out.println("Validate user: "+userDto);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "userDto.firstname.empty", "userDto.firstname.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "userDto.lastname.empty", "userDto.lastname.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "userDto.phoneNumber.empty", "userDto.phoneNumber.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adress", "userDto.adress.empty", "userDto.adress.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "userDto.city.empty", "userDto.city.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "userDto.zip.empty", "userDto.zip.empty = Default message");
       
        if(errors.hasErrors()){
            return;
        }
        
       
        
        for (Character ch : userDto.getZip().toCharArray()) {
            if(!ch.isDigit(ch)){
                errors.rejectValue("zip", "userDto.zip.form", "userDto.zip.form = Default message" );
                break;
            }
        }
        
    }
}
