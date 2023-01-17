package app.services;

import app.entities.Customer;
import app.entities.bucket.Sell;
import app.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Customer newCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }

    public void changeDiscount(Long customer_id, Long discount_first, Long discount_second){
        if(discount_first == 0.0 && discount_second == 0.0){
            return;
        }

        Customer customer = customerRepo.getById(customer_id);
        if (discount_first > 0 && discount_first < 100) {
            customer.setDiscount_first(discount_first);
        }
        if (discount_second > 0 && discount_second < 100) {
            customer.setDiscount_second(discount_second);
        }
        customerRepo.save(customer);
    }
}
