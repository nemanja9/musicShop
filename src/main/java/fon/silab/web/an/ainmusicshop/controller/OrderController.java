package fon.silab.web.an.ainmusicshop.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    @GetMapping(path = "/all")
    public ModelAndView allOrdersForUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("orders/ordersAll");
                
         List<OrderDto> list = orderService.getAllForUser(((UserDto) session.getAttribute("loginUser")).getUserId());
        
         
        modelAndView.addObject("orders", list);
        return modelAndView;
    }
    
     
    
    
}
