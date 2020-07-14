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
import fon.silab.web.an.ainmusicshop.service.impl.PaymentServices;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Nemanja
 */
@Controller
@RequestMapping("/PayPal")
public class PaymentController {

    
    
    @PostMapping(path = "/openPayPal")
    public ModelAndView paypal(@ModelAttribute(name = "orderDto") OrderDto orderDto, HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
       
        try {
            orderDto.setOrderItems((List<OrderItemDto>) session.getAttribute("cart"));
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
