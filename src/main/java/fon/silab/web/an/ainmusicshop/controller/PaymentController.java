/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.entity.OrderEntity;
import fon.silab.web.an.ainmusicshop.service.OrderItemService;
import fon.silab.web.an.ainmusicshop.service.OrderService;
import fon.silab.web.an.ainmusicshop.service.impl.PaymentServices;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Nemanja
 */
@Controller
@RequestMapping("/PayPal")
public class PaymentController {

    private final OrderService orderService;
    private final OrderItemService orderItemService; 

    @Autowired
    public PaymentController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }
    
    
    
    
    @PostMapping(path = "/openPayPal")
    public ModelAndView paypal(@ModelAttribute(name = "orderDto") OrderDto orderDto, HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
       
        try {
            orderDto.setOrderItems((List<OrderItemDto>) session.getAttribute("cart"));
            orderDto.setUserDto((UserDto) session.getAttribute("loginUser"));
            PaymentServices paymentServices = new PaymentServices(orderDto);
            String approvalLink = paymentServices.authorizePayment(orderDto);

            response.sendRedirect(approvalLink);

        } catch (PayPalRESTException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
           

        }
        ModelAndView modelAndView = new ModelAndView("cart/cartCheckout");
        return modelAndView;
    }
    
    
    @GetMapping(path = "/paymentComplete")
    public ModelAndView cart(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("cart/paymentComplete");
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");
//        OVDE CEMO UBACITI U BAZU PORUDZBINNU KOJA SE UPRAVO ZAVRSILA

        List<OrderItemDto> lista = (List<OrderItemDto>) session.getAttribute("cart");
        UserDto user = (UserDto) session.getAttribute("loginUser");
        
        OrderDto order = new OrderDto();
        order.setUserDto(user);
        order.setOrderDate(new Date());
        order.setOrderItems(lista);
        if(request.getParameter("paymentId")!= null){
            System.out.println(request.getParameter("token"));
            System.out.println(request.getParameter("paymentId"));
        order.setToken(request.getParameter("token"));
        order.setPaymentId(request.getParameter("paymentId"));
        order.setOrderStatus(OrderEntity.Status.PLACENO);
        order.setPaidDate(new Date());
        }
        else {
            order.setOrderStatus(OrderEntity.Status.NEPLACENO);
        }
        

                orderService.save(order);

        
        
        session.removeAttribute("cart");
        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.getPaymentDetails(paymentId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.setAttribute("shippingAddress", shippingAddress);

        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();

        }
        return modelAndView;
    }
    
    
    

}
