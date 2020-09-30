package fon.silab.web.an.ainmusicshop.controller.admin;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.emailTemplates.EmailTemplateGenerator;
import fon.silab.web.an.ainmusicshop.entity.OrderEntity;
import fon.silab.web.an.ainmusicshop.service.MailService;
import fon.silab.web.an.ainmusicshop.service.OrderItemService;
import fon.silab.web.an.ainmusicshop.service.OrderService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminOrderController(OrderService orderService, OrderItemService orderItemService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    @Autowired
    private MailService mailService;

    @GetMapping(path = "/all")
    public ModelAndView allOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");

        List<OrderDto> list = orderService.getAll();
        if(list.isEmpty()){
            attributes.addFlashAttribute("uspeh", "Sistem nije uspeo da pronađe porudžbine!");
        }
                
        modelAndView.addObject("orders", list);
        return modelAndView;
    }

    @GetMapping(path = "/all/{id}")
    public ModelAndView allOrdersForUser(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");

        List<OrderDto> list = orderService.getAllForUser(id);

        modelAndView.addObject("orders", list);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("orders/orderEdit");

        modelAndView.addObject("order", orderService.findByNumber(id));

        return modelAndView;

    }

    @GetMapping("/deleteOrder/{id}")
    public String editOrder(@PathVariable("id") int id, RedirectAttributes attributes) {
        try {
            orderService.delete(id);
            attributes.addFlashAttribute("uspeh", "Uspešno obrisana porudžbina!");
        } catch (Exception e) {
            attributes.addFlashAttribute("uspeh", "Niste uspeli da obrisete zeljenu porudzbinu!");
        }
        return "redirect:/admin/orders/all";

    }

    @PostMapping(path = "/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam int novStatus, RedirectAttributes attributes) {

        OrderDto pom = orderService.findByNumber(id);
        pom.setOrderStatusInt(novStatus);
        orderService.update(pom);
        attributes.addFlashAttribute("uspeh", "Status porudžbine sa ID <b>" + id + "</b> ušpesno promenjen na <b>" + pom.getOrderStatus() + "</b>");

        try {
            if (novStatus == OrderEntity.Status.POSLATO.ordinal()) {
                mailService.send("musicshopan@gmail.com", pom.getUserDto().getEmail(), "Porudzbina poslata", EmailTemplateGenerator.dajEmailPromenaStatusaPorudzbine(pom));
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminOrderController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("USLI U CATCH!!!");
        }

        return "redirect:/admin/orders/all";

    }

}
