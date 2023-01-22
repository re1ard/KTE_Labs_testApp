package app.controllers.soap;

import app.controllers.abs.SellController;
import app.services.CustomerService;
import app.services.ProductService;
import app.services.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soap/sell", produces = MediaType.APPLICATION_XML_VALUE)
public class SoapSellController extends SellController {

    @Autowired
    public SoapSellController(CustomerService customerService, ProductService productService, SellService sellService) {
        super(customerService, productService, sellService);
    }
}
