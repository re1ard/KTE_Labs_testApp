package app.repositories;

import app.entities.bucket.Sell;
import app.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Override
    @Query(value = "SELECT product FROM Product product")
    List<Product> findAll();

    @Query(value = "SELECT DISTINCT sells.products FROM Sell sells WHERE sells.customer.id = ?1 AND sells.complete = true")
    List<Product> getBuyedProducts(Long customer_id);
}
