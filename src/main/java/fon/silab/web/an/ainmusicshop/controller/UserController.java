/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.entity.UserEntity.UserRole;
import fon.silab.web.an.ainmusicshop.service.UserService;
import fon.silab.web.an.ainmusicshop.validator.LoginValidator;
import fon.silab.web.an.ainmusicshop.validator.RegisterValidator;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author lj
 */

@Controller
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    private final LoginValidator loginValidator;
    private final RegisterValidator registerValidator;

    @Autowired
    public UserController(UserService userService, RegisterValidator registerValidator, LoginValidator loginValidator) {
        this.userService = userService;
        this.registerValidator = registerValidator;
        this.loginValidator = loginValidator;
    }

    @InitBinder("userToLogin")
    protected void initLoginBinder(WebDataBinder binder){
        binder.setValidator(loginValidator);
    }
    
    @InitBinder("userToRegister")
    protected void initRegisterBinder(WebDataBinder binder){
        binder.setValidator(registerValidator);
    }
    

    @GetMapping(value = "all")
    public ModelAndView all() {
        ModelAndView modelAndView = new ModelAndView("user/userAll");
        return modelAndView;
    }

    @ModelAttribute(name = "roleList")
    public List<UserRole> categoryLists(){
        return Arrays.asList(UserRole.values());
    }
    
    @GetMapping(path = "login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("user/login");
        return modelAndView;
    }
    
    @ModelAttribute(name = "userToLogin")
    public UserDto userLogin(){
        return new UserDto();
    }
    
    @PostMapping(path = "/login_user")
    public String login(@Validated @ModelAttribute(name = "userToLogin") UserDto userToLogin,
            BindingResult result, Model model,  HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/login";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto u = userService.findByEmail(userToLogin.getEmail());
            session.setAttribute("loginUser", u);
            return "redirect:/index.jsp";
        }

    }
    
    @GetMapping(path = "logout")
    public ModelAndView logout(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("contact");
        session.setAttribute("loginUser", null);
        return modelAndView;
    }
    
    @GetMapping(path = "register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView("user/register");
        return modelAndView;
    }
    
    @ModelAttribute(name = "userToRegister")
    public UserDto userRegister(){
        return new UserDto();
    }
    
    @PostMapping(path = "/register_user")
    public String signup(@Validated @ModelAttribute(name = "userToRegister") UserDto userToRegister,
            BindingResult result, Model model, 
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste lepo popunili formu!");
            return "user/register";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            userToRegister.setRoleUser(UserEntity.UserRole.ROLE_USER);
            model.addAttribute("userToRegister", userToRegister);
            userService.save(userToRegister);
            redirectAttributes.addFlashAttribute("message", "Uspesno ste se registrovali!");
            return "redirect:/user/register";
        }

    }
    
    
}
