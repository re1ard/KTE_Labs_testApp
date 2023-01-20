package app.services;

import app.entities.product.Product;
import app.entities.statistic.CustomerStatistic;
import app.entities.statistic.ProductStatistic;
import app.repositories.CustomerRepo;
import app.repositories.product.ProductRepo;
import app.repositories.product.SelledProductRepo;
import app.repositories.statistic.CustomerStatisticRepo;
import app.repositories.statistic.ProductStatisticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StatisticService {
    private CustomerStatisticRepo customerStatisticRepo;
    private ProductStatisticRepo productStatisticRepo;
    private SelledProductRepo selledProductRepo;
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public StatisticService(CustomerStatisticRepo customerStatisticRepo,
                            ProductStatisticRepo productStatisticRepo,
                            SelledProductRepo selledProductRepo,
                            ProductRepo productRepo,
                            CustomerRepo customerRepo) {
        this.customerStatisticRepo = customerStatisticRepo;
        this.productStatisticRepo = productStatisticRepo;
        this.selledProductRepo = selledProductRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
    }

    public ProductStatistic getProduct(Long product_id) {
        return productStatisticRepo.getStatistic(product_id);
    }

    public CustomerStatistic getCustomer(Long customer_id) {
        return customerStatisticRepo.getStatistic(customer_id);
    }

    public void updateProductStatistic(){
        productRepo.findAll().stream().forEach(x -> ReCreateStatisticToProduct(x.getId()));
    }

    public void updateCustomerStatistic(){
        customerRepo.findAll().stream().forEach(x -> ReCreateStatisticToCustomer(x.getId()));
    }

    public void ReCreateStatisticToProduct(Long product_id){
        productStatisticRepo.deleteStatisticWithProductId(product_id);
        ProductStatistic statistic = new ProductStatistic();
        statistic.setProduct_id(product_id);
        statistic.setComplete_sells_count(selledProductRepo.getRegisterSellCountOnProduct(product_id).orElse(0L));
        statistic.setTotal_product_sells_price(selledProductRepo.getOriginalPriceSum(product_id).orElse(0L));
        statistic.setTotal_discount_sells_price(selledProductRepo.getDiscountSum(product_id).orElse(0L));
        productStatisticRepo.save(statistic);
    }

    public void ReCreateStatisticToCustomer(Long customer_id){
        customerStatisticRepo.deleteStatisticWithCustomerId(customer_id);
        CustomerStatistic statistic = new CustomerStatistic();
        statistic.setCustomer_id(customer_id);
        statistic.setComplete_sells_count(selledProductRepo.getRegisterSellCountOnCustomer(customer_id).orElse(0L));
        statistic.setTotal_full_buy_price(selledProductRepo.getOriginalPriceSumPerCustomer(customer_id).orElse(0L));
        statistic.setTotal_full_discount(selledProductRepo.getDiscountSumPerCustomer(customer_id).orElse(0L));
        customerStatisticRepo.save(statistic);
    }
}
