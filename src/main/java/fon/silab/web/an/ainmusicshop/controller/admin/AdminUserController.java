package fon.silab.web.an.ainmusicshop.controller.admin;

import fon.silab.web.an.ainmusicshop.controller.UserController;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.emailTemplates.EmailTemplateGenerator;
import fon.silab.web.an.ainmusicshop.entity.ProductEntity;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.hashing.PasswordGenerator;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import fon.silab.web.an.ainmusicshop.validator.LoginValidator;
import fon.silab.web.an.ainmusicshop.validator.RegisterValidator;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;
        private final RegisterValidator registerValidator;

        

    @Autowired
    public AdminUserController(UserService userService, RegisterValidator registerValidator, LoginValidator loginValidator) {
        this.userService = userService;
        this.registerValidator = registerValidator;
    }

     @Autowired
    private MailService mailService;
    
    @GetMapping(path = "add")
    public ModelAndView add(){
    ModelAndView mav = new ModelAndView("/user/userAdd");
    mav.addObject("userToAdd",new UserDto());
    mav.addObject("roleList",roleLists());
    return mav;
    } 
    
   
    
     @ModelAttribute(name = "userToRegister")
    public UserDto userRegister() {
        return new UserDto();
    }
      @InitBinder("userToRegister")
    protected void initRegisterBinder(WebDataBinder binder) {
        binder.setValidator(registerValidator);
    }

    @PostMapping(path = "/save")
    public String saveUser(@Validated @ModelAttribute(name = "userToAdd") UserDto u,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        if (userService.findByEmail(u.getEmail())!= null) // da li korisnik vec postoji
            return "user/admin/user/all";
      
        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste lepo popunili formu!");
            return "user/admin/user/all";
            
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            u.setEmailConfirmed(0);
            String tempPassword = EmailTemplateGenerator.generateRandomToken(20);
             u.setPassword(PasswordGenerator.generateHashedPass(tempPassword));
             u.setRe_password(u.getPassword());
             
            
             u.setEmailToken(EmailTemplateGenerator.generateRandomToken(20));
             u.setPasswordToken(EmailTemplateGenerator.generateRandomToken(20));
            userService.save(u);
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno ste se registrovali! <br> Na vas email je poslat link za potvrdu naloga");
            
            try {
                mailService.send("musicshopan@gmail.com", u.getEmail(), "Potvrda registracije", EmailTemplateGenerator.dajEmailRegisterText(u));
                mailService.send("musicshopan@gmail.com", u.getEmail(), "Resetovanje lozinke", EmailTemplateGenerator.dajEmailChangePasswordText(u));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "redirect:/admin/user/all";
        }

    }
   @GetMapping(path = "all")
    public ModelAndView all(){
    ModelAndView mav = new ModelAndView("/user/userAll");
    mav.addObject("users",userService.getAll());
    return mav;
    } 

    
    @GetMapping("/deleteUser/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        userService.delete(id);
        return "redirect:/admin/user/all";

    }
    
    
    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("user/userEdit");
        modelAndView.addObject("u", userService.findByNumber(id));
        System.out.println("NADJEN OVAJ " + userService.findByNumber(id).toString());
        return modelAndView;

    }
    
    @GetMapping("/sendEmailConfirmation/{id}")
    public String sendEmailConfirmation(@PathVariable("id") int id, RedirectAttributes attributes, RedirectAttributes redirectAttributes) {
        UserDto pom = userService.findByNumber(id);
        pom.setEmailConfirmed(0);
        pom.setEmailToken(EmailTemplateGenerator.generateRandomToken(20));
        try {
            mailService.send("musicshopan@gmail.com", pom.getEmail(), "Potvrda email adrese", EmailTemplateGenerator.dajEmailChangeEmailText(pom));
                        redirectAttributes.addFlashAttribute("uspeh","Uspesno Poslat mail za potvrdu naloga!");

        } catch (Exception ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
                userService.update(pom);

        return "redirect:/admin/user/edit/"+id;
    }
    @GetMapping("/sendPasswordReset/{id}")
    public String sendPasswordReset(@PathVariable("id") int id, RedirectAttributes attributes, RedirectAttributes redirectAttributes) {
        UserDto pom = userService.findByNumber(id);
        try {
            pom.setPasswordToken(EmailTemplateGenerator.generateRandomToken(20));
            mailService.send("musicshopan@gmail.com", pom.getEmail(), "Resetovanje lozinke", EmailTemplateGenerator.dajEmailChangePasswordText(pom));
                        redirectAttributes.addFlashAttribute("uspeh","Uspesno Poslat mail za resetovanje lozinke!");
                        userService.update(pom);

        } catch (Exception ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/admin/user/edit/"+id;
    }
      @ModelAttribute(name = "roleList")
    public List<UserEntity.UserRole> roleLists() {
        return Arrays.asList(UserEntity.UserRole.values());
    }
    
    @PostMapping(path = "/edit/saveEdited")
    public String saveChanges(@Validated @ModelAttribute(name = "userToAdd") UserDto u,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes){
        UserDto stari = userService.findByNumber(u.getUserId());
        if (!stari.getEmail().equals(u.getEmail())){
            if (userService.findByEmail(u.getEmail())!=null){ // ovde se pitamo da li taj nov unet email vec pripada nekom nalogu. ako pripada baci gresku
                redirectAttributes.addFlashAttribute("uspeh", "Ta adresa vec pripada nekom nalogu!");
                return "redirect:/admin/user/all";
            }
            u.setEmailToken(EmailTemplateGenerator.generateRandomToken(20));
            u.setEmailConfirmed(0);
            try {
                mailService.send("musicshopan@gmail.com", u.getEmail(), "Potvrda email adrese", EmailTemplateGenerator.dajEmailRegisterText(u));
            } catch (Exception ex) {
                Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        u.setEmailToken(stari.getEmailToken());
        u.setPassword(stari.getPassword());
        u.setPasswordToken(stari.getPasswordToken());
        userService.update(u);
        redirectAttributes.addFlashAttribute("uspeh", "Uspesno izmenjen korisnik!");
        return "redirect:/admin/user/all";

        
    }
    
    
    }
    
    
    
    
    
    
    

