package app.services;

import app.entities.product.ExtraProduct;
import app.entities.product.Product;
import app.entities.product.Review;
import app.repositories.ProductRepo;
import app.repositories.ReviewRepo;
import app.repositories.SellRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private ReviewRepo reviewRepo;
    private SellRepo sellRepo;
    private CustomerService customerService;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          ReviewRepo reviewRepo,
                          SellRepo sellRepo,
                          CustomerService customerService){
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.sellRepo = sellRepo;
        this.customerService = customerService;
    }

    public Product newProduct(Product product){
        productRepo.save(product);
        return product;
    }

    public Product getProduct(Long product_id){
        return productRepo.getById(product_id);
    }

    public void changeDiscount(long product_id, byte discount){
        Product product = productRepo.getById(product_id);
        product.setDiscount(discount);
        productRepo.save(product);
    }

    public ExtraProduct getProductInfo(long product_id, long customer_id) {
        ExtraProduct product = (ExtraProduct) productRepo.getById(product_id);
        Review review = reviewRepo.findCustomerReviewOnProduct(product_id, customer_id).orElse(null);
        product.setUsers_rate(getReviewRate(product_id));
        product.setAvg_rate(product.getAvg_rate());//мда
        return product;
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    //7.оценка товара
    public boolean rateProduct(long customer_id, long product_id, byte rate){
        Predicate<Product> check_id = x -> x.getId() == product_id;
        if(!productRepo.getBuyedProducts(customer_id).stream().anyMatch(check_id)){
            return false;
        };

        Review review = reviewRepo.findCustomerReviewOnProduct(product_id, customer_id).orElse(null);
        if(review != null && (rate == -1)){
            reviewRepo.delete(review);
            return true;
        }
        if(review != null && rate >= 0 && rate <= 5){
            review.setRating(rate);
            reviewRepo.save(review);
            return true;
        }
        if(review == null && rate >= 0 && rate <= 5){
            review = new Review();
            review.setProduct(productRepo.getById(product_id));
            review.setCustomer(customerService.getCustomer(customer_id));
            reviewRepo.save(review);
            return true;
        }
        return false;
    }

    //Получение всех оценок //4
    public HashMap<Byte, Integer> getReviewRate(long product_id){
        List<Review> reviews = reviewRepo.findReviews(product_id);
        HashMap<Byte, Integer> review_rate = new HashMap<>();
        for(Review review: reviews){
            review_rate.merge(review.getRating(), 1, (total, one) -> total + one);
        }
        return review_rate;
    }

    //Получение средней оценки //4
    public Double calculateAvgRate(HashMap<Byte, Integer> reviews){
        return reviews.keySet().stream().mapToDouble(Double::valueOf).average().getAsDouble();
    }
}
