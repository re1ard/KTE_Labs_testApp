package app.controllers;

import app.entities.product.Product;
import app.services.CustomerService;
import app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    private CustomerService customerService;

    @Autowired
    public ProductController(ProductService productService,
                             CustomerService customerService){
        this.productService = productService;
        this.customerService = customerService;
    }

    //3.Список товаров
    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = productService.getAll();
        if(products == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/show/{product_id}/{customer_id}")
    public ResponseEntity showProductAndReview(@PathVariable Long product_id,
                                               @PathVariable Long customer_id){
        return new ResponseEntity(productService.getProductInfo(product_id, customer_id), HttpStatus.OK);
    }
}
