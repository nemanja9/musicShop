/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.validator;

import fon.silab.web.an.ainmusicshop.dto.ContactDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author lj
 */
@Component
public class ContactValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return ContactDto.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        
        ContactDto contactDto = (ContactDto) o;
        System.out.println("Validate user: "+contactDto);
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "userDto.firstname.empty", "userDto.firstname.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "userDto.lastname.empty", "userDto.lastname.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "userDto.email.empty", "userDto.email.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "contactDto.subject.empty", "contactDto.subject.empty = Default message");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "contactDto.content.empty", "contactDto.content.empty = Default message");
        
        if(errors.hasErrors()){
            return;
        }
        
        if(!contactDto.getEmail().contains("@") || !contactDto.getEmail().contains(".")){
            errors.rejectValue("email", "userDto.email.form", "userDto.email.form = Default message" );
        }
    }
    
}
