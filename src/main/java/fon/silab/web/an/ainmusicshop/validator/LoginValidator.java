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
 * @author lj
 */

@Component
public class LoginValidator implements Validator{

    
    private final UserService userService;

    @Autowired
    public LoginValidator(UserService userService) {
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
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "userDto.email.empty", "userDto.email.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "userDto.password.empty", "userDto.password.empty = Default message");
        
        if(errors.hasErrors()){
            return;
        }
        
        if(userService.findByEmail(userDto.getEmail()) == null){
            errors.rejectValue("email", "userDto.email.notExist", "userDto.email.notExist = Default message" );
        }
        
        if(!userDto.getPassword().equals(userService.findByEmail(userDto.getEmail()).getPassword())){
            errors.rejectValue("password", "userDto.password.wrong", "userDto.password.wrong = Default message" );
        }
    }
    
}
