package app.services;

import app.entities.product.Discount;
import app.entities.product.PurchasedProduct;
import app.entities.product.Product;
import app.entities.product.Review;
import app.repositories.product.DiscountRepo;
import app.repositories.product.ProductRepo;
import app.repositories.product.ReviewRepo;
import app.repositories.SellRepo;
import app.repositories.product.SelledProductRepo;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private ReviewRepo reviewRepo;
    private SellRepo sellRepo;
    private CustomerService customerService;
    private SelledProductRepo selledProductRepo;
    private DiscountRepo discountRepo;
    private JobScheduler jobScheduler;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          ReviewRepo reviewRepo,
                          SellRepo sellRepo,
                          CustomerService customerService,
                          SelledProductRepo selledProductRepo,
                          DiscountRepo discountRepo,
                          JobScheduler jobScheduler){
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.sellRepo = sellRepo;
        this.customerService = customerService;
        this.selledProductRepo = selledProductRepo;
        this.discountRepo = discountRepo;
        this.jobScheduler = jobScheduler;

        this.jobScheduler.scheduleRecurrently("* 0 * * *", () -> changeDiscountRandomly());
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

    @Job(name = "randomly change discount on product")
    public void changeDiscountRandomly() {
        Discount active_discount = discountRepo.getActiveDiscount().orElse(null);
        if(active_discount != null) {
            Product product = productRepo.getById(active_discount.getProduct_id());
            product.setDiscount((byte) 0);
            productRepo.save(product);
            discountRepo.delete(active_discount);
        }

        long product_id = ((BigInteger) entityManager.createNativeQuery("SELECT id FROM products ORDER BY random() LIMIT 1").getSingleResult()).longValue();
        Product random_product = productRepo.getById(product_id);

        byte rnd_discount = (byte) ((new Random()).nextInt(6) + 5);
        random_product.setDiscount(rnd_discount);
        productRepo.save(random_product);

        active_discount = new Discount();
        active_discount.setProduct_id(random_product.getId());
        active_discount.setDiscount(rnd_discount);
    }

    public PurchasedProduct getProductInfo(long product_id, long customer_id) {
        PurchasedProduct product = new PurchasedProduct();
        product.setDescription(productRepo.getById(product_id).getDescription());
        Review review = reviewRepo.findCustomerReviewOnProduct(product_id, customer_id).orElse(null);
        product.setUsers_rate(getReviewRate(product_id));
        product.setAvg_rate(product.getAvg_rate());//мда
        if(review != null) product.setUser_review(review.getRating());
        return product;
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    //7.оценка товара
    public int rateProduct(long customer_id, long product_id, byte rate){
        //Predicate<Product> check_id = x -> x.getId().longValue() == product_id;
        //if(!productRepo.getBuyedProducts(customer_id).stream().anyMatch(check_id)){
        //    return -1;
        //};
        if(selledProductRepo.buy_count(product_id, customer_id) == 0){
            return -1;
        }

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
            review.setProduct_id(product_id);
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
