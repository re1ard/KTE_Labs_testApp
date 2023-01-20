package app.repositories.statistic;

import app.entities.statistic.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Repository
@EnableTransactionManagement
public interface CustomerStatisticRepo extends JpaRepository<CustomerStatistic, Long> {
    @Query(value = "DELETE FROM CustomerStatistic customer_statistic WHERE customer_statistic.customer_id = ?1")
    @Modifying
    @Transactional
    void deleteStatisticWithCustomerId(Long customer_id);

    @Query(value = "SELECT stats FROM CustomerStatistic stats WHERE stats.customer_id = ?1")
    CustomerStatistic getStatistic(Long customer_id);
}
