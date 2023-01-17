package app.services;

import app.entities.Customer;
import app.entities.bucket.Sell;
import app.entities.product.Product;
import app.repositories.CustomerRepo;
import app.repositories.ProductRepo;
import app.repositories.SellRepo;
import org.h2.util.CurrentTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SellService {

    private SellRepo sellRepo;
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;

    private Short current_sell_id = 100;

    @Autowired
    public SellService(SellRepo sellRepo,
                       ProductRepo productRepo,
                       CustomerRepo customerRepo) {
        this.sellRepo = sellRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
    }

    public Sell newSell(Customer customer){
        Sell sell = new Sell();
        sell.setCustomer(customer);
        sellRepo.save(sell);
        customer.setBucket(sell);
        customerRepo.save(customer);
        return getSell(sell.getId());
    }

    public String closeSell(Long sell_id){
        Sell sell = sellRepo.getById(sell_id);
        sell.setComplete(true);
        sell.setSell_date(LocalDateTime.now());
        sell.setSell_id(current_sell_id);
        current_sell_id++;
        return String.format("%05d", sell.getSell_id());
    }

    public void addProductToOrder(Long sell_id, Long product_id){
        Product product = productRepo.getById(product_id);
        Sell sell = sellRepo.getById(sell_id);
        sell.addToOrder(product);
        sellRepo.save(sell);
    }

    public Sell getSell(Long sell_id){
        return calcucateFinalCost(sell_id);
    }

    public Sell calcucateFinalCost(Long sell_id){
        Sell sell = sellRepo.getById(sell_id);
        HashMap<Long, Integer> discount_map = new HashMap<>();
        for(Product product: sell.getOrder().getProducts()){
            discount_map.merge(product.getId(), 1, (current, one) -> current + one);
        }

        Boolean have_chance_second_discount = false;
        Long final_cost = 0L;
        for(Map.Entry<Long, Integer> entry:discount_map.entrySet()){
            if(entry.getValue() >= 5){
                have_chance_second_discount = true;
            }
            final_cost += productRepo.getById(entry.getKey()).getPrice();
        }

        if(have_chance_second_discount && sell.getCustomer().getDiscount_second() > 0) {
            sell.setCost(final_cost - final_cost * (sell.getCustomer().getDiscount_second() / 100));
        } else if (sell.getCustomer().getDiscount_first() > 0) {
            sell.setCost(final_cost - final_cost * (sell.getCustomer().getDiscount_first() / 100));
        }

        sellRepo.save(sell);
        return sell;
    }
}
