package app.controllers.soap;

import app.controllers.abs.ProductController;
import app.services.CustomerService;
import app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soap/products", produces = MediaType.APPLICATION_XML_VALUE)
public class SoapProductController extends ProductController {

    @Autowired
    public SoapProductController(ProductService productService, CustomerService customerService) {
        super(productService, customerService);
    }
}
