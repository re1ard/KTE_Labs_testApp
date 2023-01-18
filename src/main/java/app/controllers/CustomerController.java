package app.controllers;

import app.entities.Customer;
import app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //1.Список всех клиентов
    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.findAll();
        if(customers == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //2.Изменение скидок
    @PatchMapping("/{id}")
    public ResponseEntity changeDiscount(@PathVariable Long id,
                                           @RequestParam(value = "first_discount", required = false, defaultValue = "-1") Byte first_discount,
                                           @RequestParam(value = "second_discount", required = false, defaultValue = "-1") Byte second_discount) {
        if(first_discount >= 0){
            customerService.changeFirstDiscount(id, first_discount);
            return new ResponseEntity(HttpStatus.OK);
        }

        if(second_discount >= 0){
            customerService.changeSecondDiscount(id, second_discount);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
