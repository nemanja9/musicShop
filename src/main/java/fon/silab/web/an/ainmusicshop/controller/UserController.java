/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import fon.silab.web.an.ainmusicshop.entity.UserEntity.UserRole;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import fon.silab.web.an.ainmusicshop.validator.LoginValidator;
import fon.silab.web.an.ainmusicshop.validator.RegisterValidator;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    @Autowired
    public UserController(UserService userService, RegisterValidator registerValidator, LoginValidator loginValidator) {
        this.userService = userService;
        this.registerValidator = registerValidator;
        this.loginValidator = loginValidator;
    }

    @Autowired
    private MailService mailService;

    @InitBinder("userToLogin")
    protected void initLoginBinder(WebDataBinder binder) {
        binder.setValidator(loginValidator);
    }

    @InitBinder("userToRegister")
    protected void initRegisterBinder(WebDataBinder binder) {
        binder.setValidator(registerValidator);
    }

    @GetMapping(value = "all")
    public ModelAndView all() {
        ModelAndView modelAndView = new ModelAndView("user/userAll");
        return modelAndView;
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
            return "user/login";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            userToSave.setUserId(((UserDto) session.getAttribute("loginUser")).getUserId());
            userService.update(userToSave);
            session.setAttribute("loginUser", userToSave);
            return "redirect:/index.jsp";
        }

    }

    @PostMapping(path = "/edit/submitEmail")
    public String editUserEmail(@Validated @ModelAttribute(name = "izmenjenKorisnik") UserDto userToSave,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/login";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto pom = ((UserDto) session.getAttribute("loginUser"));
            pom.setEmail(userToSave.getEmail());
            pom.setEmailConfirmed(0);
            pom.setEmailToken(generateRandomToken(20));
            userService.update(pom);
            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Potvrda email adrese", dajEmailChangeEmailText(pom));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("loginUser", null);
            return "redirect:/index.jsp";
        }

    }

    @PostMapping(path = "/edit/submitPassword")
    public String editUserPassword(@Validated @ModelAttribute(name = "izmenjenKorisnik") UserDto userToSave,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Bilo je gresaka pri validaciji...");
            model.addAttribute("invalid", "Niste ispravno popunili formu!");
            return "user/login";
        } else {
            System.out.println("Nije bilo gresaka pri validaciji...");
            UserDto pom = ((UserDto) session.getAttribute("loginUser"));
            pom.setPassword(userToSave.getPassword());
            pom.setRe_password(userToSave.getRe_password());

            userService.update(pom);
            session.setAttribute("loginUser", pom);
            return "redirect:/index.jsp";
        }

    }

    @GetMapping(path = "logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("contact");
        session.setAttribute("loginUser", null);
        return modelAndView;
    }

    @GetMapping(path = "register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("user/register");
        return modelAndView;
    }

    @GetMapping(path = "confirmEmail")
    public ModelAndView confirmEmail(@RequestParam String email, @RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("user/emailConfirmed");
        UserDto user = userService.findByEmail(email);
        if (user.getEmailToken().equals(token)) {
            user.setEmailConfirmed(1);
            user.setEmailToken(null);
            userService.update(user);
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
        if (pom == null) {
            return new ModelAndView("user/register");

        }
        if (!pom.getEmailToken().equals(token)) {
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
            pom.setEmailToken(generateRandomToken(20));
            userService.update(pom);
                        model.addAttribute("uspeh", "Mail sa linkom za promenu lozinke je poslat na vasu adresu!");

            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Potvrda registracije", dajEmailChangePasswordText(pom));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return "user/resetPassword";
    }
    @PostMapping(path = "enterNewPassword/submit")
    public String submitNewPassword(@Validated @ModelAttribute(name = "userToChangePass") UserDto u,Model model) {

        UserDto pom = userService.findByEmail(u.getEmail());
        if (pom == null) {
            model.addAttribute("invalid", "Korisnik sa tim emailom ne postoji!");
            return "enterNewPassword/submit";
        } else {
            pom.setEmailToken(null);
            pom.setPassword(u.getPassword());
            userService.update(pom);
                        model.addAttribute("uspeh", "Uspesno promenjena lozinka!");

            try {
                mailService.send("musicshopan@gmail.com", pom.getEmail(), "Lozinka promenjena", dajEmailChangedPasswordText(pom));
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
            userToRegister.setEmailToken(generateRandomToken(20));
            model.addAttribute("userToRegister", userToRegister);
            userService.save(userToRegister);
            redirectAttributes.addFlashAttribute("uspeh", "Uspesno ste se registrovali! <br> Na vas email je poslat link za potvrdu naloga");
            try {
                mailService.send("musicshopan@gmail.com", userToRegister.getEmail(), "Potvrda registracije", dajEmailRegisterText(userToRegister));
            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "redirect:/user/register";
        }

    }

    private String generateRandomToken(int duzina) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = duzina;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int ii = 0; ii < targetStringLength; ii++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        System.out.println(generatedString);
        return generatedString;
    }

    private String dajEmailRegisterText(UserDto u) {

        String emailText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + " <head> \n"
                + "  <meta charset=\"UTF-8\"> \n"
                + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n"
                + "  <meta content=\"telephone=no\" name=\"format-detection\"> \n"
                + "  <title>New email template 2020-08-18</title> \n"
                + "  <!--[if (mso 16)]>\n"
                + "    <style type=\"text/css\">\n"
                + "    a {text-decoration: none;}\n"
                + "    </style>\n"
                + "    <![endif]--> \n"
                + "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n"
                + "  <!--[if gte mso 9]>\n"
                + "<xml>\n"
                + "    <o:OfficeDocumentSettings>\n"
                + "    <o:AllowPNG></o:AllowPNG>\n"
                + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "    </o:OfficeDocumentSettings>\n"
                + "</xml>\n"
                + "<![endif]--> \n"
                + "  <style type=\"text/css\">\n"
                + "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:center; line-height:120%!important } h2 { font-size:26px!important; text-align:center; line-height:120%!important } h3 { font-size:20px!important; text-align:center; line-height:120%!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c { text-align:center!important } .es-m-txt-r { text-align:right!important } .es-m-txt-l { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-width:10px 0px 10px 0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social td { display:inline-block!important } table.es-social { display:inline-block!important } }\n"
                + "#outlook a {\n"
                + "	padding:0;\n"
                + "}\n"
                + ".ExternalClass {\n"
                + "	width:100%;\n"
                + "}\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "	line-height:100%;\n"
                + "}\n"
                + ".es-button {\n"
                + "	mso-style-priority:100!important;\n"
                + "	text-decoration:none!important;\n"
                + "}\n"
                + "a[x-apple-data-detectors] {\n"
                + "	color:inherit!important;\n"
                + "	text-decoration:none!important;\n"
                + "	font-size:inherit!important;\n"
                + "	font-family:inherit!important;\n"
                + "	font-weight:inherit!important;\n"
                + "	line-height:inherit!important;\n"
                + "}\n"
                + ".es-desk-hidden {\n"
                + "	display:none;\n"
                + "	float:left;\n"
                + "	overflow:hidden;\n"
                + "	width:0;\n"
                + "	max-height:0;\n"
                + "	line-height:0;\n"
                + "	mso-hide:all;\n"
                + "}\n"
                + "</style> \n"
                + " </head> \n"
                + " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \n"
                + "  <div class=\"es-wrapper-color\" style=\"background-color:#CCCCCC\"> \n"
                + "   <!--[if gte mso 9]>\n"
                + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "				<v:fill type=\"tile\" color=\"#cccccc\"></v:fill>\n"
                + "			</v:background>\n"
                + "		<![endif]--> \n"
                + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \n"
                + "     <tr style=\"border-collapse:collapse\"> \n"
                + "      <td valign=\"top\" style=\"padding:0;Margin:0\"> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#CCCCCC\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/63891597745475246.png\" alt=\"Smart home logo\" title=\"Smart home logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"48\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;background-color:#EE4266\" bgcolor=\"#ee4266\" align=\"left\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:590px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ee4266\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EE4266\" role=\"presentation\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td style=\"padding:0;Margin:0\"> \n"
                + "                       <table class=\"es-menu\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr class=\"links\" style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Pocetna</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Proizvodi</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Kontakt</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">O nama</a></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#EE4266\">Uspesno ste napravili nalog</h1></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"75%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #999999;background:none 0% 0% repeat scroll#FFFFFF;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666\">Zdravo,</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Uspesno ste se registrovali, ali morate potvrditi svoju email adresu klikom na link ispod</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#4A7EB0;background:#2CB543;border-width:0px;display:inline-block;border-radius:0px;width:auto\"><a href=\"http://localhost:8080/musicshop/user/confirmEmail?email=" + u.getEmail() + "&token=" + u.getEmailToken() + "\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;color:#010101;border-style:solid;border-color:#EFEFEF;border-width:10px 25px;display:inline-block;background:#EFEFEF;border-radius:0px;font-weight:normal;font-style:normal;line-height:22px;width:auto;text-align:center\">Potvrdi email</a></span></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Ukoliko niste vi napravili nalog, obrisite ovaj mail.<br>Ako vam je potrebna pomoc, kontaktirajte nas.</p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-right:5px;padding-top:20px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #FFFFFF;background:#FFFFFFnone repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EFEFEF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"padding:20px;Margin:0\"> \n"
                + "               <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:194px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:174px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td class=\"es-m-p0l\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/10221597745409018.png\" alt width=\"163\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td esdev-links-color=\"#333333\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"></a><a href=\"tel://23923929210\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\">+381 11 689 901<br>office@AINmusic.com<br>Jove Ilica 154, Vozdovac</a><br></p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td></tr></table><![endif]--></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table></td> \n"
                + "     </tr> \n"
                + "   </table> \n"
                + "  </div>  \n"
                + " </body>\n"
                + "</html>";

        return emailText;

    }
    private String dajEmailChangeEmailText(UserDto u) {

        String emailText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + " <head> \n"
                + "  <meta charset=\"UTF-8\"> \n"
                + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n"
                + "  <meta content=\"telephone=no\" name=\"format-detection\"> \n"
                + "  <title>New email template 2020-08-18</title> \n"
                + "  <!--[if (mso 16)]>\n"
                + "    <style type=\"text/css\">\n"
                + "    a {text-decoration: none;}\n"
                + "    </style>\n"
                + "    <![endif]--> \n"
                + "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n"
                + "  <!--[if gte mso 9]>\n"
                + "<xml>\n"
                + "    <o:OfficeDocumentSettings>\n"
                + "    <o:AllowPNG></o:AllowPNG>\n"
                + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "    </o:OfficeDocumentSettings>\n"
                + "</xml>\n"
                + "<![endif]--> \n"
                + "  <style type=\"text/css\">\n"
                + "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:center; line-height:120%!important } h2 { font-size:26px!important; text-align:center; line-height:120%!important } h3 { font-size:20px!important; text-align:center; line-height:120%!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c { text-align:center!important } .es-m-txt-r { text-align:right!important } .es-m-txt-l { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-width:10px 0px 10px 0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social td { display:inline-block!important } table.es-social { display:inline-block!important } }\n"
                + "#outlook a {\n"
                + "	padding:0;\n"
                + "}\n"
                + ".ExternalClass {\n"
                + "	width:100%;\n"
                + "}\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "	line-height:100%;\n"
                + "}\n"
                + ".es-button {\n"
                + "	mso-style-priority:100!important;\n"
                + "	text-decoration:none!important;\n"
                + "}\n"
                + "a[x-apple-data-detectors] {\n"
                + "	color:inherit!important;\n"
                + "	text-decoration:none!important;\n"
                + "	font-size:inherit!important;\n"
                + "	font-family:inherit!important;\n"
                + "	font-weight:inherit!important;\n"
                + "	line-height:inherit!important;\n"
                + "}\n"
                + ".es-desk-hidden {\n"
                + "	display:none;\n"
                + "	float:left;\n"
                + "	overflow:hidden;\n"
                + "	width:0;\n"
                + "	max-height:0;\n"
                + "	line-height:0;\n"
                + "	mso-hide:all;\n"
                + "}\n"
                + "</style> \n"
                + " </head> \n"
                + " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \n"
                + "  <div class=\"es-wrapper-color\" style=\"background-color:#CCCCCC\"> \n"
                + "   <!--[if gte mso 9]>\n"
                + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "				<v:fill type=\"tile\" color=\"#cccccc\"></v:fill>\n"
                + "			</v:background>\n"
                + "		<![endif]--> \n"
                + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \n"
                + "     <tr style=\"border-collapse:collapse\"> \n"
                + "      <td valign=\"top\" style=\"padding:0;Margin:0\"> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#CCCCCC\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/63891597745475246.png\" alt=\"Smart home logo\" title=\"Smart home logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"48\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;background-color:#EE4266\" bgcolor=\"#ee4266\" align=\"left\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:590px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ee4266\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EE4266\" role=\"presentation\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td style=\"padding:0;Margin:0\"> \n"
                + "                       <table class=\"es-menu\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr class=\"links\" style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Pocetna</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Proizvodi</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Kontakt</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">O nama</a></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#EE4266\">Email adresa promenjena</h1></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"75%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #999999;background:none 0% 0% repeat scroll#FFFFFF;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666\">Zdravo,</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Uspesno ste promenili email adresu naloga, ali morate potvrditi svoju novu adresu klikom na link ispod</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#4A7EB0;background:#2CB543;border-width:0px;display:inline-block;border-radius:0px;width:auto\"><a href=\"http://localhost:8080/musicshop/user/confirmEmail?email=" + u.getEmail() + "&token=" + u.getEmailToken() + "\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;color:#010101;border-style:solid;border-color:#EFEFEF;border-width:10px 25px;display:inline-block;background:#EFEFEF;border-radius:0px;font-weight:normal;font-style:normal;line-height:22px;width:auto;text-align:center\">Potvrdi email</a></span></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Ukoliko niste vi napravili promenu, kontaktirajte nas.</p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-right:5px;padding-top:20px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #FFFFFF;background:#FFFFFFnone repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EFEFEF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"padding:20px;Margin:0\"> \n"
                + "               <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:194px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:174px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td class=\"es-m-p0l\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/10221597745409018.png\" alt width=\"163\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td esdev-links-color=\"#333333\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"></a><a href=\"tel://23923929210\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\">+381 11 689 901<br>office@AINmusic.com<br>Jove Ilica 154, Vozdovac</a><br></p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td></tr></table><![endif]--></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table></td> \n"
                + "     </tr> \n"
                + "   </table> \n"
                + "  </div>  \n"
                + " </body>\n"
                + "</html>";

        return emailText;

    }
    private String dajEmailChangePasswordText(UserDto u) {

        String emailText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + " <head> \n"
                + "  <meta charset=\"UTF-8\"> \n"
                + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n"
                + "  <meta content=\"telephone=no\" name=\"format-detection\"> \n"
                + "  <title>New email template 2020-08-18</title> \n"
                + "  <!--[if (mso 16)]>\n"
                + "    <style type=\"text/css\">\n"
                + "    a {text-decoration: none;}\n"
                + "    </style>\n"
                + "    <![endif]--> \n"
                + "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n"
                + "  <!--[if gte mso 9]>\n"
                + "<xml>\n"
                + "    <o:OfficeDocumentSettings>\n"
                + "    <o:AllowPNG></o:AllowPNG>\n"
                + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "    </o:OfficeDocumentSettings>\n"
                + "</xml>\n"
                + "<![endif]--> \n"
                + "  <style type=\"text/css\">\n"
                + "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:center; line-height:120%!important } h2 { font-size:26px!important; text-align:center; line-height:120%!important } h3 { font-size:20px!important; text-align:center; line-height:120%!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c { text-align:center!important } .es-m-txt-r { text-align:right!important } .es-m-txt-l { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-width:10px 0px 10px 0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social td { display:inline-block!important } table.es-social { display:inline-block!important } }\n"
                + "#outlook a {\n"
                + "	padding:0;\n"
                + "}\n"
                + ".ExternalClass {\n"
                + "	width:100%;\n"
                + "}\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "	line-height:100%;\n"
                + "}\n"
                + ".es-button {\n"
                + "	mso-style-priority:100!important;\n"
                + "	text-decoration:none!important;\n"
                + "}\n"
                + "a[x-apple-data-detectors] {\n"
                + "	color:inherit!important;\n"
                + "	text-decoration:none!important;\n"
                + "	font-size:inherit!important;\n"
                + "	font-family:inherit!important;\n"
                + "	font-weight:inherit!important;\n"
                + "	line-height:inherit!important;\n"
                + "}\n"
                + ".es-desk-hidden {\n"
                + "	display:none;\n"
                + "	float:left;\n"
                + "	overflow:hidden;\n"
                + "	width:0;\n"
                + "	max-height:0;\n"
                + "	line-height:0;\n"
                + "	mso-hide:all;\n"
                + "}\n"
                + "</style> \n"
                + " </head> \n"
                + " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \n"
                + "  <div class=\"es-wrapper-color\" style=\"background-color:#CCCCCC\"> \n"
                + "   <!--[if gte mso 9]>\n"
                + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "				<v:fill type=\"tile\" color=\"#cccccc\"></v:fill>\n"
                + "			</v:background>\n"
                + "		<![endif]--> \n"
                + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \n"
                + "     <tr style=\"border-collapse:collapse\"> \n"
                + "      <td valign=\"top\" style=\"padding:0;Margin:0\"> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#CCCCCC\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/63891597745475246.png\" alt=\"Smart home logo\" title=\"Smart home logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"48\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;background-color:#EE4266\" bgcolor=\"#ee4266\" align=\"left\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:590px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ee4266\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EE4266\" role=\"presentation\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td style=\"padding:0;Margin:0\"> \n"
                + "                       <table class=\"es-menu\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr class=\"links\" style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Pocetna</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Proizvodi</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Kontakt</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">O nama</a></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#EE4266\">Resetovanje lozinke</h1></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"75%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #999999;background:none 0% 0% repeat scroll#FFFFFF;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666\">Zdravo,</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Da resetujete lozinku kliknite na link ispod:</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#4A7EB0;background:#2CB543;border-width:0px;display:inline-block;border-radius:0px;width:auto\"><a href=\"http://localhost:8080/musicshop/user/resetPassword/enterNewPassword?email=" + u.getEmail() + "&token=" + u.getEmailToken() + "\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;color:#010101;border-style:solid;border-color:#EFEFEF;border-width:10px 25px;display:inline-block;background:#EFEFEF;border-radius:0px;font-weight:normal;font-style:normal;line-height:22px;width:auto;text-align:center\">Resetuj lozinku</a></span></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Ukoliko niste vi napravili promenu, kontaktirajte nas.</p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-right:5px;padding-top:20px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #FFFFFF;background:#FFFFFFnone repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EFEFEF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"padding:20px;Margin:0\"> \n"
                + "               <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:194px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:174px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td class=\"es-m-p0l\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/10221597745409018.png\" alt width=\"163\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td esdev-links-color=\"#333333\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"></a><a href=\"tel://23923929210\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\">+381 11 689 901<br>office@AINmusic.com<br>Jove Ilica 154, Vozdovac</a><br></p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td></tr></table><![endif]--></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table></td> \n"
                + "     </tr> \n"
                + "   </table> \n"
                + "  </div>  \n"
                + " </body>\n"
                + "</html>";

        return emailText;

    }
    private String dajEmailChangedPasswordText(UserDto u) {

        String emailText = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                + " <head> \n"
                + "  <meta charset=\"UTF-8\"> \n"
                + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n"
                + "  <meta content=\"telephone=no\" name=\"format-detection\"> \n"
                + "  <title>New email template 2020-08-18</title> \n"
                + "  <!--[if (mso 16)]>\n"
                + "    <style type=\"text/css\">\n"
                + "    a {text-decoration: none;}\n"
                + "    </style>\n"
                + "    <![endif]--> \n"
                + "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n"
                + "  <!--[if gte mso 9]>\n"
                + "<xml>\n"
                + "    <o:OfficeDocumentSettings>\n"
                + "    <o:AllowPNG></o:AllowPNG>\n"
                + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                + "    </o:OfficeDocumentSettings>\n"
                + "</xml>\n"
                + "<![endif]--> \n"
                + "  <style type=\"text/css\">\n"
                + "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:center; line-height:120%!important } h2 { font-size:26px!important; text-align:center; line-height:120%!important } h3 { font-size:20px!important; text-align:center; line-height:120%!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c { text-align:center!important } .es-m-txt-r { text-align:right!important } .es-m-txt-l { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-width:10px 0px 10px 0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social td { display:inline-block!important } table.es-social { display:inline-block!important } }\n"
                + "#outlook a {\n"
                + "	padding:0;\n"
                + "}\n"
                + ".ExternalClass {\n"
                + "	width:100%;\n"
                + "}\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "	line-height:100%;\n"
                + "}\n"
                + ".es-button {\n"
                + "	mso-style-priority:100!important;\n"
                + "	text-decoration:none!important;\n"
                + "}\n"
                + "a[x-apple-data-detectors] {\n"
                + "	color:inherit!important;\n"
                + "	text-decoration:none!important;\n"
                + "	font-size:inherit!important;\n"
                + "	font-family:inherit!important;\n"
                + "	font-weight:inherit!important;\n"
                + "	line-height:inherit!important;\n"
                + "}\n"
                + ".es-desk-hidden {\n"
                + "	display:none;\n"
                + "	float:left;\n"
                + "	overflow:hidden;\n"
                + "	width:0;\n"
                + "	max-height:0;\n"
                + "	line-height:0;\n"
                + "	mso-hide:all;\n"
                + "}\n"
                + "</style> \n"
                + " </head> \n"
                + " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"> \n"
                + "  <div class=\"es-wrapper-color\" style=\"background-color:#CCCCCC\"> \n"
                + "   <!--[if gte mso 9]>\n"
                + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "				<v:fill type=\"tile\" color=\"#cccccc\"></v:fill>\n"
                + "			</v:background>\n"
                + "		<![endif]--> \n"
                + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"> \n"
                + "     <tr style=\"border-collapse:collapse\"> \n"
                + "      <td valign=\"top\" style=\"padding:0;Margin:0\"> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-header-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:580px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#CCCCCC\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/63891597745475246.png\" alt=\"Smart home logo\" title=\"Smart home logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"48\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td style=\"padding:0;Margin:0;padding-left:5px;padding-right:5px;background-color:#EE4266\" bgcolor=\"#ee4266\" align=\"left\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:590px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ee4266\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EE4266\" role=\"presentation\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td style=\"padding:0;Margin:0\"> \n"
                + "                       <table class=\"es-menu\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr class=\"links\" style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Pocetna</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Proizvodi</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">Kontakt</a></td> \n"
                + "                          <td style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:10px;padding-bottom:10px;border:0\" width=\"25.00%\" bgcolor=\"transparent\" align=\"center\"><a style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:none;display:block;color:#FFFFFF\" href=\"https://viewstripo.email\">O nama</a></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#EE4266\">Lozinka promenjena</h1></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"75%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #999999;background:none 0% 0% repeat scroll#FFFFFF;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666\">Zdravo,</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\">Vasa lozinka je nedavno promenjena. Ukoliko ne prepoznajete ovu aktivnost, kontaktirajte nas.</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#666666\"></p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:40px;padding-right:40px\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:520px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-right:5px;padding-top:20px;padding-bottom:20px;font-size:0\"> \n"
                + "                       <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                         <tr style=\"border-collapse:collapse\"> \n"
                + "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #FFFFFF;background:#FFFFFFnone repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td> \n"
                + "                         </tr> \n"
                + "                       </table></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EFEFEF;width:600px\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              <td align=\"left\" style=\"padding:20px;Margin:0\"> \n"
                + "               <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:194px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:174px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td class=\"es-m-p0l\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0px\"><a href=\"https://viewstripo.email\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"><img src=\"https://hwxtrn.stripocdn.email/content/guids/CABINET_1c5d359760b84be5a9f2f58cb0630393/images/10221597745409018.png\" alt width=\"163\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:173px\"><![endif]--> \n"
                + "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\"> \n"
                + "                 <tr style=\"border-collapse:collapse\"> \n"
                + "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:173px\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"> \n"
                + "                     <tr style=\"border-collapse:collapse\"> \n"
                + "                      <td esdev-links-color=\"#333333\" align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\"></a><a href=\"tel://23923929210\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:14px;text-decoration:underline;color:#333333\">+381 11 689 901<br>office@AINmusic.com<br>Jove Ilica 154, Vozdovac</a><br></p></td> \n"
                + "                     </tr> \n"
                + "                   </table></td> \n"
                + "                 </tr> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td></tr></table><![endif]--></td> \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"> \n"
                + "         <tr style=\"border-collapse:collapse\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n"
                + "             <tr style=\"border-collapse:collapse\"> \n"
                + "              \n"
                + "             </tr> \n"
                + "           </table></td> \n"
                + "         </tr> \n"
                + "       </table></td> \n"
                + "     </tr> \n"
                + "   </table> \n"
                + "  </div>  \n"
                + " </body>\n"
                + "</html>";

        return emailText;

    }
}
