package app.services;

import app.entities.product.PurchasedProduct;
import app.entities.product.Product;
import app.entities.product.Review;
import app.repositories.ProductRepo;
import app.repositories.ReviewRepo;
import app.repositories.SellRepo;
import app.repositories.SelledProductRepo;
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
    private SelledProductRepo selledProductRepo;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          ReviewRepo reviewRepo,
                          SellRepo sellRepo,
                          CustomerService customerService,
                          SelledProductRepo selledProductRepo){
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.sellRepo = sellRepo;
        this.customerService = customerService;
        this.selledProductRepo = selledProductRepo;
    }

    public Product newProduct(Product product){
        productRepo.save(product);
        return product;
    }

    public Product newProduct(String name, String description, Long price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
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

    public PurchasedProduct getProductInfo(long product_id, long customer_id) {
        PurchasedProduct product = new PurchasedProduct(product_id);
        Review review = reviewRepo.findCustomerReviewOnProduct(product_id, customer_id).orElse(null);
        product.setUsers_rate(getReviewRate(product_id));
        product.setAvg_rate(product.getAvg_rate());//мда
        product.setUser_review(review);
        return product;
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    //7.оценка товара
    public int rateProduct(long customer_id, long product_id, byte rate){
        Predicate<Product> check_id = x -> x.getId().longValue() == product_id;
        if(!productRepo.getBuyedProducts(customer_id).stream().anyMatch(check_id)){
            return -1;
        };

        Review review = reviewRepo.findCustomerReviewOnProduct(product_id, customer_id).orElse(null);
        if(review != null && (rate == -1)){
            reviewRepo.delete(review);
            return 1;
        }
        if(review != null && rate >= 0 && rate <= 5){
            review.setRating(rate);
            reviewRepo.save(review);
            return 1;
        }
        if(review == null && rate >= 0 && rate <= 5){
            review = new Review();
            review.setProduct(productRepo.getById(product_id));
            review.setCustomer_id(customer_id);
            review.setRating(rate);
            reviewRepo.save(review);
            return 1;
        }
        return -255;
    }

    //Получение всех оценок //4
    public HashMap<Byte, Integer> getReviewRate(long product_id){
        List<Review> reviews = reviewRepo.findReviews(product_id);
        HashMap<Byte, Integer> review_rate = new HashMap<>();
        for(Review review: reviews){
            review_rate.merge(review.getRating(), 1, (total, one) -> total + one);
        }
        return review_rate.isEmpty() ? null : review_rate;
    }

    //Получение средней оценки //4
    public Double calculateAvgRate(HashMap<Byte, Integer> reviews){
        return reviews.keySet().stream().mapToDouble(Double::valueOf).average().getAsDouble();
    }
}
