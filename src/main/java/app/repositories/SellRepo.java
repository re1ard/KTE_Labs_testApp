package app.repositories;

import app.entities.bucket.Sell;
import app.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellRepo extends JpaRepository<Sell, Long> {
    @Query(value = "SELECT sells FROM Sell sells WHERE sells.customer_id = ?1 AND sells.complete = true")
    List<Sell> getCompleteSells(Long customer_id);

}
