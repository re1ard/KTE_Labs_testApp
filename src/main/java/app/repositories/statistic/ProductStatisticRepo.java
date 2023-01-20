package app.repositories.statistic;

import app.entities.statistic.CustomerStatistic;
import app.entities.statistic.ProductStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface ProductStatisticRepo extends JpaRepository<ProductStatistic, Long> {
    @Query(value = "DELETE FROM ProductStatistic product_statistic WHERE product_statistic.product_id = ?1")
    @Modifying
    @Transactional
    void deleteStatisticWithProductId(Long product_id);

    @Query(value = "SELECT stats FROM ProductStatistic stats WHERE stats.product_id = ?1")
    ProductStatistic getStatistic(Long product_id);
}
