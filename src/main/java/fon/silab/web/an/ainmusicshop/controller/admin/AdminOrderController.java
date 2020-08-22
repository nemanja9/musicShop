package fon.silab.web.an.ainmusicshop.controller.admin;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.service.OrderItemService;
import fon.silab.web.an.ainmusicshop.service.OrderService;
import fon.silab.web.an.ainmusicshop.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/adminn/orders")
public class AdminOrderController {
    private final OrderService orderService;
    private final UserService userService; 
    
    @Autowired
    public AdminOrderController(OrderService orderService, OrderItemService orderItemService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    
    
     @GetMapping(path = "/all")
    public ModelAndView allOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");
        
         List<OrderDto> list = orderService.getAll();
         for (OrderDto o : list) {
             UserDto u = userService.findByEmail(orderService.getUserByOrderID(o.getOrderId()));
             o.setUserDto(u);
             System.out.println(orderService.getUserByOrderID(o.getOrderId()));
             System.out.println(userService.findByEmail(orderService.getUserByOrderID(o.getOrderId())));
             System.out.println(u);
         }
         
        modelAndView.addObject("orders", list);
        return modelAndView;
    }
     @GetMapping(path = "/all/{id}")
    public ModelAndView allOrdersForUser(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");
                
         List<OrderDto> list = orderService.getAllForUser(id);
         for (OrderDto o : list) {
             UserDto u = userService.findByEmail(orderService.getUserByOrderID(o.getOrderId()));
             o.setUserDto(u);
             System.out.println(orderService.getUserByOrderID(o.getOrderId()));
             System.out.println(userService.findByEmail(orderService.getUserByOrderID(o.getOrderId())));
             System.out.println(u);
         }
         
        modelAndView.addObject("orders", list);
        return modelAndView;
    }
    
    @GetMapping("/deleteOrder/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        orderService.delete(id);
        return "redirect:/admin/orders/all";

    }
}
