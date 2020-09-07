/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.emailTemplates.EmailTemplateGenerator;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.entity.UserEntity.UserRole;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import fon.silab.web.an.ainmusicshop.validator.LoginValidator;
import fon.silab.web.an.ainmusicshop.validator.PasswordValidator;
import fon.silab.web.an.ainmusicshop.validator.ProfileValidator;
import fon.silab.web.an.ainmusicshop.validator.RegisterValidator;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    private final ProfileValidator profileValidator;
    private final PasswordValidator passwordValidator;

    @Autowired
    public UserController(UserService userService, RegisterValidator registerValidator, LoginValidator loginValidator, ProfileValidator profileValidator, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.registerValidator = registerValidator;
        this.loginValidator = loginValidator;
        this.profileValidator = profileValidator;
        this.passwordValidator = passwordValidator;
    }

    @Autowired
    private MailService mailService;

    @InitBinder("userToLogin")
    protected void initLoginBinder(WebDataBinder binder) {
        binder.setValidator(loginValidator);
    }

    @InitBinder("izmenjenKorisnik")
    protected void initProfileBinder(WebDataBinder binder) {
        binder.setValidator(profileValidator);
    }

    @InitBinder("userToChangePass")
    protected void initPassFBinder(WebDataBinder binder) {
        binder.setValidator(passwordValidator);
    }

    @InitBinder("izmenjenaSifra")
    protected void initPassBinder(WebDataBinder binder) {
        binder.setValidator(passwordValidator);
    }

    @InitBinder("userToRegister")
    protected void initRegisterBinder(WebDataBinder binder) {
        binder.setValidator(registerValidator);
    }

    @ModelAttribute(name = "roleList")
    public List<UserRole> categoryLists() {
        return Arrays.asList(UserRole.values());
    }

    @GetMapping(path = "login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("user/login");
        return modelAndView;
    }

    @GetMapping(path = "profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("izmenjenKorisnik", new UserDto());
        return modelAndView;
    }

    @ModelAttribute(name = "userToLogin")
    public UserDto userLogin() {
        return new UserDto();
    }

    @ModelAttribute(name = "izmenjenaSifra")
    public UserDto userSifra() {
        return new UserDto();
    }

    @ModelAttribute(name = "izmenjenKorisnik")
    public UserDto userKor() {
        return new UserDto();
    }

    @PostMapping(path = "/login_user")
    public String login(@Validated @ModelAttribute(name = "userToLogin") UserDto userToLogin,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/login";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto u = userService.findByEmail(userToLogin.getEmail());
            u.setPasswordToken(null); // ovde ponistavamo token koji je mozda postojao za resetovanje lozinke zbog sigurnosti i onemucavanja iskoriscavanja starog maila za reset lozinke kada korisnik ocigledno zna lozinku i loguje se sa njom
            userService.update(u);
            session.setAttribute("loginUser", u);
            return "redirect:/index.jsp";
        }

    }

    @PostMapping(path = "/edit/submitPersonalChanges")
    public String editUserPersonal(@Validated @ModelAttribute(name = "izmenjenKorisnik") UserDto userToSave,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/profile";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            userToSave.setUserId(((UserDto) session.getAttribute("loginUser")).getUserId());
            userService.update(userToSave);
            session.setAttribute("loginUser", userToSave);
            session.removeAttribute("nedostajuciPodaci");
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno sacuvane promene!");
            return "redirect:/user/profile";

        }

    }

    @PostMapping(path = "/edit/submitEmail")
    public String editUserEmail(@Validated @ModelAttribute(name = "izmenjenKorisnik") UserDto userToSave,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (userService.findByEmail(userToSave.getEmail()) != null) { // da li korisnik vec postoji
            redirectAttributes.addFlashAttribute("uspeh", "Korisnik sa tom adresom vec postoji, izaberite drugu adresu!");
            return "redirect:/user/profile";

        }

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "redirect:/user/profile";

        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto pom = ((UserDto) session.getAttribute("loginUser"));
            pom.setEmail(userToSave.getEmail());
            pom.setEmailConfirmed(0);
            pom.setEmailToken(EmailTemplateGenerator.generateRandomToken(20));
            userService.update(pom);
            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Potvrda email adrese", EmailTemplateGenerator.dajEmailChangeEmailText(pom));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("loginUser", null);
            return "redirect:/user/profile";

        }

    }

    @PostMapping(path = "/edit/submitPassword")
    public String editUserPassword(@Validated @ModelAttribute(name = "izmenjenaSifra") UserDto userToSave,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/profile";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto pom = ((UserDto) session.getAttribute("loginUser"));
            pom.setPassword(userToSave.getPassword());
            pom.setRe_password(userToSave.getRe_password());

            userService.update(pom);
            session.setAttribute("loginUser", pom);
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno promenjena lozinka!");
            return "redirect:/user/profile";

        }

    }

    @GetMapping(path = "logout")
    public String logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("../../index");
        session.setAttribute("loginUser", null);
        return "redirect:/index.jsp";
    }

    @GetMapping(path = "register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("user/register");
        return modelAndView;
    }

    @GetMapping(path = "confirmEmail")
    public ModelAndView confirmEmail(@RequestParam String email, @RequestParam String token) {
        ModelAndView modelAndView;
        UserDto user = userService.findByEmail(email);
        if (user != null && user.getEmailConfirmed() == 0 && user.getEmailToken().equals(token)) {
            modelAndView = new ModelAndView("user/emailConfirmSuccess");
            user.setEmailConfirmed(1);
            user.setEmailToken(null);
            userService.update(user);
        } else {
            modelAndView = new ModelAndView("user/emailConfirmError");
        }
        return modelAndView;
    }

    @GetMapping(path = "resetPassword/enterNewPassword")
    public ModelAndView enterNewPassword(@RequestParam String email, @RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("user/enterNewPassword");
        UserDto pom = new UserDto();

        try { // ovde ce se bacii exc ako ne postoji korisnicki nalog sa tom adresom, pa ga onda preusmeravamo na registraciju
            pom = userService.findByEmail(email);

        } catch (Exception e) {
            return new ModelAndView("user/register");
        }
        if (pom == null || pom.getPasswordToken() == null) {
            return new ModelAndView("user/register");

        }
        if (!pom.getPasswordToken().equals(token)) {
            return new ModelAndView("user/register");
        }
        pom.setPassword("");
        modelAndView.addObject("userToChangePass", pom);
        return modelAndView;
    }

    @GetMapping(path = "resetPassword")
    public ModelAndView resetPassword() {
        ModelAndView modelAndView = new ModelAndView("user/resetPassword");
        return modelAndView;
    }

    @PostMapping(path = "resetPassword")
    public String resetPasswordPost(@RequestParam(name = "email") String email, Model model) {

        UserDto pom = userService.findByEmail(email);
        if (pom == null) {
            model.addAttribute("invalid", "Korisnik sa tim emailom ne postoji!");
            return "user/resetPassword";
        } else {
            pom.setPasswordToken(EmailTemplateGenerator.generateRandomToken(20));
            userService.update(pom);
            model.addAttribute("uspeh", "Mail sa linkom za promenu lozinke je poslat na vasu adresu!");

            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Promena lozinke", EmailTemplateGenerator.dajEmailChangePasswordText(pom));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return "user/resetPassword";
    }

    @PostMapping(path = "enterNewPassword/submit")
    public String submitNewPassword(@Validated @ModelAttribute(name = "userToChangePass") UserDto u, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        UserDto pom = userService.findByEmail(u.getEmail());

        if (pom == null) {
            model.addAttribute("invalid", "Korisnik sa tim emailom ne postoji!");
            return "enterNewPassword/submit";

        } else if (result.hasErrors()) {
            redirectAttributes.addAttribute("email", pom.getEmail());
            redirectAttributes.addAttribute("token", pom.getPasswordToken());
            return "redirect:/user/resetPassword/enterNewPassword";
        } else {

            pom.setPasswordToken(null);
            pom.setPassword(u.getPassword());
            userService.update(pom);
            model.addAttribute("uspeh", "Uspesno promenjena lozinka!");

            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Lozinka promenjena", EmailTemplateGenerator.dajEmailChangedPasswordText(pom));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "user/login";
    }

    @ModelAttribute(name = "userToRegister")
    public UserDto userRegister() {
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
            userToRegister.setEmailConfirmed(0);
            userToRegister.setEmailToken(EmailTemplateGenerator.generateRandomToken(20));
            model.addAttribute("userToRegister", userToRegister);
            userService.save(userToRegister);
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno ste se registrovali! <br> Na vas email je poslat link za potvrdu naloga");
            try {
                mailService.send("musicshopan@gmail.com", userToRegister.getEmail(), "Potvrda registracije", EmailTemplateGenerator.dajEmailRegisterText(userToRegister));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "redirect:/user/register";
        }

    }

}
