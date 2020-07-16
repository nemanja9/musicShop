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
public class RegisterValidator implements Validator{

    private final UserService userService;

    @Autowired
    public RegisterValidator(UserService userService) {
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "userDto.email.empty", "userDto.email.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "userDto.phoneNumber.empty", "userDto.phoneNumber.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adress", "userDto.adress.empty", "userDto.adress.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "userDto.city.empty", "userDto.city.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "userDto.zip.empty", "userDto.zip.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "userDto.password.empty", "userDto.password.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "re_password", "userDto.re_password.empty", "userDto.re_password.empty = Default message");
        
        if(errors.hasErrors()){
            return;
        }
        
        if(userService.findByEmail(userDto.getEmail()) != null){
            errors.rejectValue("email", "userDto.email.exist", "userDto.email.exist = Default message" );
        }
        
        if(!userDto.getPassword().equals(userDto.getRe_password())){
            errors.rejectValue("re_password", "userDto.pass.retype", "userDto.pass.retype = Default message" );
        }
        
        if(!userDto.getEmail().contains("@") || !userDto.getEmail().contains(".")){
            errors.rejectValue("email", "userDto.email.form", "userDto.email.form = Default message" );
        }
        
        if(userDto.getPassword().length() < 6){
            errors.rejectValue("password", "userDto.pass.form", "userDto.pass.form = Default message" );
        }
        for (Character ch : userDto.getZip().toCharArray()) {
            if(!ch.isDigit(ch)){
                errors.rejectValue("zip", "userDto.zip.form", "userDto.zip.form = Default message" );
                break;
            }
        }
        
    }
    
}
