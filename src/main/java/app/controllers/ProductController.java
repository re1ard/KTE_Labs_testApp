package app.controllers;

import app.entities.product.Product;
import app.entities.product.PurchasedProduct;
import app.services.CustomerService;
import app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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

    //4.Получение дополнительно информации
    @GetMapping("/show")
    public ResponseEntity<PurchasedProduct> showProductAndReview(@RequestParam Long product_id,
                                                                 @RequestParam Long customer_id){
        return new ResponseEntity(productService.getProductInfo(product_id, customer_id), HttpStatus.OK);
    }

    //7.Оценка товара если он куплен
    //http://localhost:8080/api/products/rate/2/2?rate=4
    @PostMapping("/rate")
    public ResponseEntity<Integer> rateProduct(@RequestParam Long product_id,
                                               @RequestParam Long customer_id,
                                               @RequestParam(defaultValue = "-1",required = false) Byte rate){
        int rateResult = productService.rateProduct(customer_id, product_id, rate);
        if(rateResult == 1){
            return new ResponseEntity(rateResult, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(rateResult, HttpStatus.NOT_FOUND);
        }
    }
}
