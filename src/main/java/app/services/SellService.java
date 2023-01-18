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
import java.util.List;
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

    public Sell newSellWithProducts(Customer customer, List<Product> products) {
        Sell sell = new Sell();
        sell.setCustomer(customer);
        products.forEach(sell::addToOrder);
        //sell.setProducts(products);
        sellRepo.save(sell);
        return sell;
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
        return calculateFinalCost(sell_id);
    }

    public Long calculateFinalCostFromProduct(List<Product> products, Customer customer) {
        Long final_cost = 0L;
        HashMap<Long, Integer> discount_map = new HashMap<>();
        for(Product product: products){
            discount_map.merge(product.getId(), 1, (current, one) -> current + one);
            final_cost += product.getPrice();
        }

        Boolean have_chance_second_discount = false;
        for(Map.Entry<Long, Integer> entry:discount_map.entrySet()){
            if(entry.getValue() >= 5){
                have_chance_second_discount = true;
            }
        }

        if(have_chance_second_discount && customer.getDiscount_second() > 0) {
            return (final_cost - final_cost * (customer.getDiscount_second() / 100));
        } else if (customer.getDiscount_first() > 0) {
            return (final_cost - final_cost * (customer.getDiscount_first() / 100));
        } else {
            return final_cost;
        }
    }

    public Sell calculateFinalCost(Long sell_id){
        Sell sell = sellRepo.getById(sell_id);
        Long cost = calculateFinalCostFromProduct(sell.getProducts(), sell.getCustomer());
        sell.setCost(cost);
        sellRepo.save(sell);
        return sell;
    }
}
