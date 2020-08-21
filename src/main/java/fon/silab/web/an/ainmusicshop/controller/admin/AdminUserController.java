package fon.silab.web.an.ainmusicshop.controller.admin;

import fon.silab.web.an.ainmusicshop.controller.UserController;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.emailTemplates.EmailTemplateGenerator;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import fon.silab.web.an.ainmusicshop.validator.LoginValidator;
import fon.silab.web.an.ainmusicshop.validator.RegisterValidator;
import java.io.IOException;
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
@RequestMapping("/adminn/user")
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
    mav.addObject("userToRegister",new UserDto());
    return mav;
    } 
    
    @PostMapping(path = "/ssave")
    public String save(@Validated @ModelAttribute(name = "userDto") UserDto userDto,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) throws IOException {
         if (result.hasErrors()) {
            model.addAttribute("message", "Niste ispravno popunili formu!");
            return "product/productAdd";
        } else {
             userDto.setPassword("asdasd");
             userDto.setRe_password("asdasd");
             userService.save(userDto);
            model.addAttribute("uspeh", "Uspesno ste uneli korisnika ");
         }
            return "/";
        
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
    public String signup(@ModelAttribute(name = "userToRegister") UserDto userToRegister,
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
//            userToRegister.setEmailToken(generateRandomToken(20));
            model.addAttribute("userToRegister", userToRegister);
             userToRegister.setPassword("asdasd");
             userToRegister.setRe_password("asdasd");
            userService.save(userToRegister);
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno ste se registrovali! <br> Na vas email je poslat link za potvrdu naloga");
            
            try {
//                mailService.send("musicshopan@gmail.com", userToRegister.getEmail(), "Potvrda registracije", dajEmailRegisterText(userToRegister));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "redirect:/user/register";
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
        return "redirect:/adminn/user/all";

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
        System.out.println("NADJEN OVAJ " + userService.findByNumber(id).toString());
        try {
            mailService.send("musicshopan@gmail.com", pom.getEmail(), "Potvrda email adrese", EmailTemplateGenerator.dajEmailChangeEmailText(pom));
                        redirectAttributes.addFlashAttribute("uspeh","Uspesno Poslat mail za potvrdu naloga!");

        } catch (Exception ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/adminn/user/edit/"+id;
    }
    @GetMapping("/sendPasswordReset/{id}")
    public String sendPasswordReset(@PathVariable("id") int id, RedirectAttributes attributes, RedirectAttributes redirectAttributes) {
        UserDto pom = userService.findByNumber(id);
        System.out.println("NADJEN OVAJ " + userService.findByNumber(id).toString());
        try {
            mailService.send("musicshopan@gmail.com", pom.getEmail(), "Resetovanje lozinke", EmailTemplateGenerator.dajEmailChangePasswordText(pom));
                        redirectAttributes.addFlashAttribute("uspeh","Uspesno Poslat mail za resetovanje lozinke!");

        } catch (Exception ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/adminn/user/edit/"+id;
    }
    
    
    
    }
    
    
    
    
    
    
    

