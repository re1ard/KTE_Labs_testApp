package app.services;

import app.entities.Customer;
import app.entities.bucket.Sell;
import app.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;

    private SellService sellService;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, SellService sellService){
        this.customerRepo = customerRepo;
        this.sellService = sellService;
    }

    public Sell newSellToCustomer(Long customer_id){
        Customer customer = customerRepo.getById(customer_id);
        return sellService.newSell(customer);
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Customer newCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    public void changeFirstDiscount(Long customer_id, Byte discount) {
        if (discount >= 0 && discount < 100) {
            Customer customer = customerRepo.getById(customer_id);
            customer.setDiscount_first(discount);
            customerRepo.save(customer);
        }
    }

    public void changeSecondDiscount(Long customer_id, Byte discount) {
        if (discount >= 0 && discount < 100) {
            Customer customer = customerRepo.getById(customer_id);
            customer.setDiscount_second(discount);
            customerRepo.save(customer);
        }
    }
}
