/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.ContactDto;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.validator.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactValidator contactValidator;

    @Autowired
    public ContactController(ContactValidator contactValidator) {
        this.contactValidator = contactValidator;
    }

    @Autowired
    private MailService mailService;

    @InitBinder
    protected void initItemBinder(WebDataBinder binder) {
        binder.setValidator(contactValidator);
    }

    @GetMapping
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("contact", new ContactDto());
        return modelAndView;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(@Validated @ModelAttribute("contact") ContactDto contact, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                String content = "Name: " + contact.getName() + "<br>Lastname: " + contact.getLastname() + "<br>Email: " + contact.getEmail();
                content += "<br>Message subject: " + contact.getSubject();
                content += "<br>Message: " + contact.getContent();
                mailService.send("musicshopan@gmail.com", "musicshopan@gmail.com", contact.getSubject(), content);
                model.addAttribute("uspeh", "Poruka je uspesno poslata!");
            } catch (Exception e) {
                model.addAttribute("uspeh", "Poruka nije poslata!Problem sa serverom!");
            }
        } else {
            model.addAttribute("msg", "Niste ispravno popunili formu!");
            return "contact";
        }

        return "contact";
    }
}
