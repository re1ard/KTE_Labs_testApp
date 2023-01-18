package app.configs;

import app.services.CustomerService;
import app.services.ProductService;
import app.services.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AfterInit {
    private CustomerService customerService;
    private ProductService productService;
    private SellService sellService;

    @Autowired
    public AfterInit(CustomerService customerService,
                     ProductService productService,
                     SellService sellService) {
        this.customerService = customerService;
        this.productService = productService;
        this.sellService = sellService;
    }

    @PostConstruct
    public void FillTestDB() {
        customerService.newCustomer("Ivan Ivanich");
        customerService.newCustomer("Petor Petrovich");
        customerService.newCustomer("San Sanich");

        productService.newProduct("Бипки", "Никто не знает, что такое бипки", 14000L);
        productService.newProduct("Супер-Бипки", "Эт ваще отвал", 435500L);
        productService.newProduct("Мини-Бибки", "Меньше только Супер-Бипка", 954L);
    }
}
