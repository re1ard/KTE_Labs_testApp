package app.controllers.rest;

import app.controllers.abs.SellController;
import app.services.CustomerService;
import app.services.ProductService;
import app.services.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sell", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestSellController extends SellController {

    @Autowired
    public RestSellController(CustomerService customerService, ProductService productService, SellService sellService) {
        super(customerService, productService, sellService);
    }
}
