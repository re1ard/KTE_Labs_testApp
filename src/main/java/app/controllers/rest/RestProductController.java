package app.controllers.rest;

import app.controllers.abs.ProductController;
import app.services.CustomerService;
import app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProductController extends ProductController {

    @Autowired
    public RestProductController(ProductService productService, CustomerService customerService) {
        super(productService, customerService);
    }
}
