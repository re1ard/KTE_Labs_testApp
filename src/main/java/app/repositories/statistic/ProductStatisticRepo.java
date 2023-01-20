package app.repositories.statistic;

import app.entities.statistic.ProductStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductStatisticRepo extends JpaRepository<ProductStatistic, Long> {
    @Query(value = "DELETE * FROM product_statistic WHERE product_id = ?1", nativeQuery = true)
    void deleteStatisticWithProductId(Long product_id);
}
