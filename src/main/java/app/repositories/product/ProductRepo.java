package app.repositories.product;

import app.entities.bucket.Sell;
import app.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "SELECT COUNT(product) FROM Product product")
    Long getTotalCount();
}
