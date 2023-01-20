package app.services;

import app.entities.Customer;
import app.entities.product.SelledProduct;
import app.entities.bucket.Sell;
import app.entities.product.Product;
import app.repositories.CustomerRepo;
import app.repositories.product.ProductRepo;
import app.repositories.SellRepo;
import app.repositories.product.SelledProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SellService {

    private SellRepo sellRepo;
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;
    private SelledProductRepo selledProductRepo;

    private Short current_sell_id = 100;

    @Autowired
    public SellService(SellRepo sellRepo,
                       ProductRepo productRepo,
                       CustomerRepo customerRepo,
                       SelledProductRepo selledProductRepo) {
        this.sellRepo = sellRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.selledProductRepo = selledProductRepo;
    }

    public Sell newSellWithProducts(Customer customer, List<Product> products) {
        Sell sell = new Sell();
        sell.setCustomer_id(customer.getId());
        sellRepo.save(sell);
        return sell;
    }

    public String closeSell(Long sell_id, List<Product> products){
        Sell sell = sellRepo.getById(sell_id);
        sell.setComplete(true);
        sell.setSell_date(LocalDateTime.now());
        sell.setSell_id(current_sell_id);
        current_sell_id++;
        for(Product product: products){
            selledProductRepo.save(
                    new SelledProduct(product.getId(), sell.getCustomer_id(), sell.getId(),
                            product.getOriginalPrice(), calculateFinalCostToProduct(product, customerRepo.getById(sell.getCustomer_id()), products.size(),
                            true)));
        }
        return String.format("%05d", sell.getSell_id());
    }

    public Long calculateFinalCostFromProducts(List<Product> products, Customer customer) {
        Long final_cost = 0L;
        int product_count = products.size();

        for(Product product: products) {
            final_cost += calculateFinalCostToProduct(product, customer, product_count);
        }
        return final_cost;
    }

    public Long calculateFinalCostToProduct(Product product, Customer customer, int product_count) {
        return calculateFinalCostToProduct(product, customer, product_count, false);
    }

    public Long calculateFinalCostToProduct(Product product, Customer customer, int product_count, boolean return_discount_price) {
        byte discount;
        if (product_count >= 5 && customer.getDiscount_second() > 0) {
            discount = (byte) (product.getDiscount().byteValue() + customer.getDiscount_second().byteValue());
            if (discount > 18) discount = 18;
            if (return_discount_price) return product.getOriginalPrice() * discount / 100;
            return product.getOriginalPrice() - product.getOriginalPrice() * discount / 100;
        } else if (customer.getDiscount_first() > 0){
            discount = (byte) (product.getDiscount().byteValue() + customer.getDiscount_first().byteValue());
            if (discount > 18) discount = 18;
            if (return_discount_price) return product.getOriginalPrice() * discount / 100;
            return product.getOriginalPrice() - product.getOriginalPrice() * discount / 100;
        } else {
            discount = product.getDiscount();
            if (return_discount_price) return product.getOriginalPrice() * discount / 100;
            return product.getOriginalPrice() - product.getOriginalPrice() * discount / 100;
        }
    }
}
