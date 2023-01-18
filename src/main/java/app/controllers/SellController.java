package app.controllers;

import app.entities.Customer;
import app.entities.bucket.Sell;
import app.entities.product.Product;
import app.services.CustomerService;
import app.services.ProductService;
import app.services.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sell")
public class SellController {

    private CustomerService customerService;
    private ProductService productService;
    private SellService sellService;

    @Autowired
    public SellController(CustomerService customerService,
                          ProductService productService,
                          SellService sellService) {
        this.customerService = customerService;
        this.productService = productService;
        this.sellService = sellService;
    }

    //5. Запрос итоговой стоимости перед чеком
    @GetMapping("/prepare")
    public ResponseEntity<Long> prepare_buy(@RequestParam Long customer_id,
                                        @RequestParam List<Long> products_ids,
                                      @RequestParam List<Integer> count) {

        Customer customer = customerService.getCustomer(customer_id);
        if(customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Product> prepare_bucket = new ArrayList<>();
        for(int i = 0; i < products_ids.size(); i++){
            Product product = productService.getProduct(products_ids.get(i));
            for(int j = 0;j < count.size(); j++){
                prepare_bucket.add(product);
            }
        }

        return new ResponseEntity<>(sellService.calculateFinalCostFromProduct(prepare_bucket, customer), HttpStatus.OK);
    }

    //6. Регистрация продажи
    @PostMapping("/register")
    public ResponseEntity<String> register_buy(@RequestParam Long customer_id,
                                               @RequestParam List<Long> products_ids,
                                               @RequestParam List<Integer> count,
                                               @RequestParam Long final_price) {

        Customer customer = customerService.getCustomer(customer_id);
        if(customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Product> prepare_bucket = new ArrayList<>();
        for(int i = 0; i < products_ids.size(); i++){
            Product product = productService.getProduct(products_ids.get(i));
            for(int j = 0;j < count.size(); j++){
                prepare_bucket.add(product);
            }
        }
        Long bucket_price = sellService.calculateFinalCostFromProduct(prepare_bucket, customer);
        if(bucket_price != final_price) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Sell created_sell = sellService.newSellWithProducts(customer, prepare_bucket);
        return new ResponseEntity<String>(sellService.closeSell(created_sell.getId()), HttpStatus.CREATED);
    }
}
