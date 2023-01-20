package app.repositories.product;

import app.entities.product.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface DiscountRepo extends JpaRepository<Discount, Long> {

    @Query(value = "SELECT discount FROM Discount discount WHERE discount.product_id = ?1")
    Discount getDiscount(Long product_id);

    @Query(value = "DELETE FROM Discount discount WHERE discount.product_id = ?1")
    @Modifying
    @Transactional
    void deleteDiscountOnProduct(Long product_id);

    @Query(value = "SELECT discount FROM Discount discount")
    Optional<Discount> getActiveDiscount();
}
