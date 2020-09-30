package fon.silab.web.an.ainmusicshop.controller.admin;

import fon.silab.web.an.ainmusicshop.dto.ProductDto;
import fon.silab.web.an.ainmusicshop.entity.ProductEntity;
import fon.silab.web.an.ainmusicshop.service.ProductService;
import fon.silab.web.an.ainmusicshop.validator.OrderItemValidator;
import fon.silab.web.an.ainmusicshop.validator.ProductEditValidator;
import fon.silab.web.an.ainmusicshop.validator.ProductValidator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    private final OrderItemValidator orderItemValidator;
    private final ProductEditValidator productEditValidator;

    @Autowired
    public AdminProductController(ProductService productService, ProductValidator productValidator, OrderItemValidator orderItemValidator, ProductEditValidator productEditValidator) {
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

    @GetMapping(value = "/edit")
    public ModelAndView edit() {
        ModelAndView modelAndView = new ModelAndView("product/productEdit");
        modelAndView.addObject("allProducts", productService.getAll());
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("product/productAdd", "productDto", new ProductDto());
        return modelAndView;
    }

    @PostMapping(path = "/save")
    public String saveProduct(@Validated @ModelAttribute(name = "productDto") ProductDto productDto,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", "Niste ispravno popunili formu!");
            return "product/productAdd";
        } else {
            System.out.println("Save product: " + productDto);
            model.addAttribute("productDto", new ProductDto());
            MultipartFile image = productDto.getImg();
            String rootDirectory = session.getServletContext().getRealPath("/");
            Path path = Paths.get(rootDirectory + "/resursi/images/" + productDto.getProductName() + ".png");

            if (new File(path.toString()).exists()) {
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    Path tesan = Paths.get(rootDirectory + "/resursi/images/" + productDto.getProductName() + "(" + i + ")" + ".png");
                    if (!new File(tesan.toString()).exists()) {
                        path = tesan;
                        break;
                    }
                }
            }

            File file = new File(path.toString());
            image.transferTo(file);
            productDto.setImgPath(path.toString().split("images")[1]);
            System.out.println("IMG PATH JE " + productDto.getImgPath());

            productService.save(productDto);
            model.addAttribute("uspeh", "Uspešno unet proizvod " + productDto.getProductName() + "!");

            return "product/productAdd";
        }

    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("product/edit");
        
        try{
            productService.delete(id);
            attributes.addFlashAttribute("uspeh", "Uspešno izbrisan proizvod!");
        }catch (Exception e){
            attributes.addFlashAttribute("uspeh", "Niste uspeli da obrisete zeljeni proizvod!");
        }
        return "redirect:/admin/product/edit";

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") int id, RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("product/productEditOne");
        modelAndView.addObject("product", productService.getOne(id));
        modelAndView.addObject("categoryList", categoryLists());
        System.out.println(productService.getOne(id).toString());
        return modelAndView;

    }

    @ModelAttribute(name = "categoryList")
    public List<ProductEntity.Category> categoryLists() {
        return Arrays.asList(ProductEntity.Category.values());
    }

    @PostMapping(path = "/edit/saveEdit")
    public String saveEdited(@Validated @ModelAttribute(name = "product") ProductDto productDto,
            BindingResult result, Model model, HttpSession session,
            RedirectAttributes attributes) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("message", "Niste ispravno popunili formu!");
            return "product/productEditOne";
        } else {

            MultipartFile image = productDto.getImg();
            
            if (image.getOriginalFilename().length() > 0) {
                String rootDirectory = session.getServletContext().getRealPath("/");
                Path path = Paths.get(rootDirectory + "/resursi/images/" + productDto.getProductName() + ".png");
                Path zaBrisanje = Paths.get(rootDirectory + "/resursi/images" + productDto.getImgPath());
                new File(zaBrisanje.toString()).delete();
                System.out.println("Trebao se obrisati fajl " + zaBrisanje.toString());

                if (new File(path.toString()).exists()) {
                    for (int i = 1; i < Integer.MAX_VALUE; i++) {
                        Path tesan = Paths.get(rootDirectory + "/resursi/images/" + productDto.getProductName() + "(" + i + ")" + ".png");
                        if (!new File(tesan.toString()).exists()) {
                            path = tesan;
                            break;
                        }
                    }
                }

                File file = new File(path.toString());
                image.transferTo(file);
                productDto.setImgPath(path.toString().split("images")[1]);

            }
            
            productService.update(productDto);
            attributes.addFlashAttribute("uspeh", "Uspešno izmenjen proizvod " + productDto.getProductName());
            return "redirect:/product/all";

        }

    }

}
