package app.services;

import app.entities.product.ExtraProduct;
import app.entities.product.Product;
import app.entities.product.Review;
import app.repositories.ProductRepo;
import app.repositories.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private ReviewRepo reviewRepo;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          ReviewRepo reviewRepo){
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    public Product newProduct(Product product){
        productRepo.save(product);
        return product;
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

    public void reviewProduct(long customer_id, long product_id){

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
