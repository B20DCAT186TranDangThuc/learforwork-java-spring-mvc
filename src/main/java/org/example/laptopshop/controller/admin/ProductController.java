package org.example.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.example.laptopshop.domain.Product;
import org.example.laptopshop.service.ProductService;
import org.example.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getDashboard(Model model,
                               @RequestParam(name = "page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
                page = 1;
            }
        } catch (Exception e) {
            // page = 1
        }


        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> products = this.productService.fecthProduct(pageable);
        model.addAttribute("products", products.getContent());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {

        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String postCreateUser(Model model,
                                 @Valid @ModelAttribute("newProduct") Product product,
                                 BindingResult ProductBindingResult,
                                 @RequestParam("productNameFile") MultipartFile file) {

        // validate
        if (ProductBindingResult.hasErrors()) {
            // print error
            List<FieldError> errors = ProductBindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getField() + " - " + error.getDefaultMessage());
            }
            return "admin/product/create";
        }
        // check file is empty and save
        if (!file.isEmpty()) {
            String image = this.uploadService.handleSaveUploadFile(file, "product");
            product.setImage(image);
        }

        // save product
        this.productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    // show detail product
    @GetMapping("/admin/product/{id}")
    public String getDetailProduct(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    // show update product
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProduct(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    // update product
    @PostMapping("/admin/product/update")
    public String postMethodName(Model model,
                                 @Valid @ModelAttribute("newProduct") Product product,
                                 BindingResult ProductBindingResult,
                                 @RequestParam("productNameFile") MultipartFile file) {
        Product currentProduct = this.productService.getProductById(product.getId());
        // validate
        if (ProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        // check change image
        if (!file.isEmpty()) {
            String image = this.uploadService.handleSaveUploadFile(file, "product");
            currentProduct.setImage(image);
        }

        // save product

        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setDetailDesc(product.getDetailDesc());
        currentProduct.setShortDesc(product.getShortDesc());
        currentProduct.setFactory(product.getFactory());
        currentProduct.setTarget(product.getTarget());

        this.productService.saveProduct(currentProduct);
        return "redirect:/admin/product";
    }

    // delete product view
    @GetMapping("/admin/product/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/delete";
    }

    // delete product
    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}
