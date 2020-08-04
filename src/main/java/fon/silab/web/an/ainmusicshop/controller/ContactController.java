/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.ContactDto;
import fon.silab.web.an.ainmusicshop.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contact")
public class ContactController {
    
    @Autowired
    private MailService mailService;
    
    @GetMapping
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("contact", new ContactDto());
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(@ModelAttribute("contact") ContactDto contact, Model model) {
        try {
            String content = "Name: "+contact.getName()+ "<br>Lastname: " + contact.getLastname() +"<br>Email: "+ contact.getEmail();
            content += "<br>Message subject: " + contact.getSubject();
            content += "<br>Message: " + contact.getContent();
            mailService.send("musicshopan@gmail.com", "musicshopan@gmail.com", contact.getSubject(), content);
            model.addAttribute("msg", "Poruka je uspesno poslata!");
        } catch (Exception e) {
            model.addAttribute("msg", "Poruka nije poslata!Problem sa serverom!");
        }
        
        
         return "contact";
    }
}
