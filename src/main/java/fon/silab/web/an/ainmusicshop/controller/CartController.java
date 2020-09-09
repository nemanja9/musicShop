package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.OrderDto;
import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.validator.OrderItemValidator;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final OrderItemValidator orderItemValidator;

    @Autowired
    public CartController(OrderItemValidator orderItemValidator) {
        this.orderItemValidator = orderItemValidator;
    }

    @InitBinder("orderItemDto")
    protected void initItemBinder(WebDataBinder binder) {
        binder.setValidator(orderItemValidator);
    }

    @GetMapping
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView("cart/cartAll");
        modelAndView.addObject("orderItemDto", new OrderItemDto());
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public String addToCart(@ModelAttribute(name = "orderItemDto") OrderItemDto orderItemDto,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("message", "ERROR");
            redirectAttributes.addFlashAttribute("message", "ERROR!");
            return "redirect:/product/" + orderItemDto.getProduct().getProductId();

        } else {
            List<OrderItemDto> lista = (List<OrderItemDto>) session.getAttribute("cart");
            if (lista == null) {
                lista = new ArrayList<>();
            }
            session.setAttribute("cart", orderItemDto.add((ArrayList<OrderItemDto>) lista));
            redirectAttributes.addFlashAttribute("message", lista.toString());
            redirectAttributes.addFlashAttribute("uspeh", "Proizvod uspesno dodat u korpu!");
            
            return "redirect:/product/" + orderItemDto.getProduct().getProductId();
        }

    }

    @PostMapping
    public String removeFromCart(@ModelAttribute(name = "orderItemDto") OrderItemDto orderItemDto,
            Model model, BindingResult result,
            RedirectAttributes redirectAttributes, HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("message", "ERROR");

            return "redirect:/cart/";

        } else {
            List<OrderItemDto> lista = (List<OrderItemDto>) session.getAttribute("cart");
            if (lista == null) {
                return "redirect:/cart/";
            }

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getProduct().getProductId() == orderItemDto.getProduct().getProductId()) {
                    lista.remove(i);
                    break;
                }
            }

            session.setAttribute("cart", lista);
            redirectAttributes.addFlashAttribute("message", "Product is removed!");
            return "redirect:/cart/";
        }

    }

    @GetMapping(path = "/plus")
    public String plus(@RequestParam int id, HttpSession session) {

        List<OrderItemDto> lista = (List<OrderItemDto>) session.getAttribute("cart");
        if (lista == null) {
            return "redirect:/cart/";
        }

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getProduct().getProductId() == id) {
                OrderItemDto pom = lista.get(i);
                pom.setQuantity(pom.getQuantity() + 1);
                lista.set(i, pom);
                break;
            }
        }

        session.setAttribute("cart", lista);
        return "redirect:/cart/";

    }

    @GetMapping(path = "/minus")
    public String minus(@RequestParam int id, HttpSession session) {

        List<OrderItemDto> lista = (List<OrderItemDto>) session.getAttribute("cart");
        if (lista == null) {
            return "redirect:/cart/";
        }

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getProduct().getProductId() == id) {
                if (lista.get(i).getQuantity() == 1) {
                    lista.remove(i);
                    break;
                }
                OrderItemDto pom = lista.get(i);
                pom.setQuantity(pom.getQuantity() - 1);
                lista.set(i, pom);
                break;
            }
        }

        session.setAttribute("cart", lista);
        return "redirect:/cart/";

    }

    @GetMapping(path = "/checkout")
    public ModelAndView checkOut(@ModelAttribute(name = "orderDto") OrderDto orderDto,
            Model model, BindingResult result,
            RedirectAttributes redirectAttributes, HttpSession session) {
        
        ModelAndView modelAndView = new ModelAndView("cart/cartCheckout");
        modelAndView.addObject("orderDto", new OrderDto());
        return modelAndView;
    }
}
