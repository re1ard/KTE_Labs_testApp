package app.controllers.soap;

import app.controllers.abs.CustomerController;
import app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soap/customers", produces = MediaType.APPLICATION_XML_VALUE)
public class SoapCustomerController extends CustomerController {

    @Autowired
    public SoapCustomerController(CustomerService customerService) {
        super(customerService);
    }
}
