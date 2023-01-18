package app.repositories;

import app.entities.product.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query(value = "SELECT review FROM Review review WHERE review.product.id = ?1 AND review.customer_id = ?2")
    Optional<Review> findCustomerReviewOnProduct(Long product_id, Long customer_id);

    @Query(value = "SELECT review FROM Review review WHERE review.product.id = ?1")
    List<Review> findReviews(Long product_id);
}
