package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import fon.silab.web.an.ainmusicshop.service.OrderItemService;
import fon.silab.web.an.ainmusicshop.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService; 

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }
    
    
     @GetMapping(path = "/all")
    public ModelAndView allOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");
        modelAndView.addObject("orders", orderService.getAll());
         for (OrderDto object : orderService.getAll()) {
             System.out.println(object.getUserDto());
         }
 
        return modelAndView;
    }
    
    @GetMapping("/deleteOrder/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("");
        orderService.delete(id);
        return "redirect:/";

    }
    
    
}
