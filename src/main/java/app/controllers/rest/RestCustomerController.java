package app.controllers.rest;

import app.controllers.abs.CustomerController;
import app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestCustomerController extends CustomerController {

    @Autowired
    public RestCustomerController(CustomerService customerService) {
        super(customerService);
    }
}
