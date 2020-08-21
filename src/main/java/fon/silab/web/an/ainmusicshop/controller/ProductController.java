/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fon.silab.web.an.ainmusicshop.controller;

import fon.silab.web.an.ainmusicshop.dto.OrderItemDto;
import fon.silab.web.an.ainmusicshop.dto.ProductDto;
import fon.silab.web.an.ainmusicshop.entity.ProductEntity.Category;
import fon.silab.web.an.ainmusicshop.service.ProductService;
import fon.silab.web.an.ainmusicshop.validator.OrderItemValidator;
import fon.silab.web.an.ainmusicshop.validator.ProductEditValidator;
import java.util.ArrayList;
import fon.silab.web.an.ainmusicshop.validator.ProductValidator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author lj
 */
@Controller
@RequestMapping("/product")

public class ProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    private final OrderItemValidator orderItemValidator;
    private final ProductEditValidator productEditValidator;

    @Autowired
    public ProductController(ProductService productService, ProductValidator productValidator, OrderItemValidator orderItemValidator, ProductEditValidator productEditValidator) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.orderItemValidator = orderItemValidator;
        this.productEditValidator = productEditValidator;
    }
    
    
    @InitBinder("productDto")
    protected void initProductBinder(WebDataBinder binder) {
        binder.setValidator(productValidator);
    }
    @InitBinder("product")
    protected void initProductEditBinder(WebDataBinder binder) {
        binder.setValidator(productEditValidator);
    }

    @InitBinder("orderItemDto")
    protected void initItemBinder(WebDataBinder binder) {
        binder.setValidator(orderItemValidator);
    }

   
   
    @GetMapping(value = "all")
    public ModelAndView all(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("product/productAll");
        boolean orderByOK = false;
        boolean manufacturerOK = false;
        boolean categoryOK = false;
        boolean minOk = false;
        boolean maxOk = false;
        boolean nameOk = true;
        String orderByStr = null;
        String min = null;
        String max = null;

        
        if(req.getParameter("productName") != null){
            for (char c : req.getParameter("productName").toCharArray()) {
                if(!Character.isLetter(c)){
                    nameOk = false;
                }
            }
        }
        
        if (req.getParameter("orderby") != null) {
            switch (req.getParameter("orderby")) {
                case "name.asc":
                    orderByOK = true;
                    orderByStr = "productName asc";
                    break;
                case "name.desc":
                    orderByOK = true;
                    orderByStr = "productName desc";
                    break;
                case "price.asc":
                    orderByOK = true;
                    orderByStr = "price asc";
                    break;
                case "price.desc":
                    orderByOK = true;
                    orderByStr = "price desc";
                    break;

            }
        }
        ArrayList<String> manufacturers = productService.getAllManufacturers();
        for (String manufacturer : manufacturers) {
            if (req.getParameter("manufacturer") != null && manufacturer.equals(req.getParameter("manufacturer"))) {
                manufacturerOK = true;
                break;
            }
        }
        for (Category kat : categoryLists()) {
            if (req.getParameter("category") == null || req.getParameter("category").equals(kat.toString())) {
                categoryOK = true;
                break;
            }
        }

        if (req.getParameter("priceMax") != null) {
            try {
                max = "price <= " + Integer.parseInt(req.getParameter("priceMax"));
                maxOk = true;
            } catch (NumberFormatException e) {
                maxOk = false;
            }
        }
        if (req.getParameter("priceMin") != null) {
            try {
                min = "price >= " + Integer.parseInt(req.getParameter("priceMin"));
                minOk = true;
            } catch (NumberFormatException e) {
                minOk = false;
            }
        }

        modelAndView.addObject("allProducts", productService.getSome((categoryOK ? req.getParameter("category") : null),
                (orderByOK ? orderByStr : null), (manufacturerOK ? req.getParameter("manufacturer") : null), (maxOk ? max : null), (minOk ? min : null),
                (nameOk ? req.getParameter("productName") : null)));

        modelAndView.addObject("kategorije", categoryLists());
        modelAndView.addObject("proizvodjaci", manufacturersList());
        modelAndView.addObject("najvecaCena", productService.getHighestPrice());
//        System.out.println(productService.getHighestPrice());
        return modelAndView;
    }

    @GetMapping(value = "all/category")
    public ModelAndView categoryIs(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView("product/productAll");
        System.out.println(name);

        boolean postojiKat = false;
        for (Category kat : categoryLists()) {
            if (name.trim().equals(kat.toString().trim())) {
                postojiKat = true;
                break;
            }
        }
        if (!postojiKat) {
            modelAndView.addObject("kategorije", categoryLists());
            return modelAndView;
        }

        modelAndView.addObject("allProducts", productService.getCategory(name));

        modelAndView.addObject("kategorije", categoryLists());
        return modelAndView;
    }

 

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("product/productOne", "productDto", productService.getOne(id));
        OrderItemDto orderItem = new OrderItemDto();
        orderItem.setProduct(productService.getOne(id));
        modelAndView.addObject("orderItemDto", orderItem);
        return modelAndView;

    }

    @ModelAttribute(name = "categoryList")
    public List<Category> categoryLists() {
        return Arrays.asList(Category.values());
    }

    @ModelAttribute(name = "manufacturers")
    public List<String> manufacturersList() {
        return productService.getAllManufacturers();
    }

   

}
