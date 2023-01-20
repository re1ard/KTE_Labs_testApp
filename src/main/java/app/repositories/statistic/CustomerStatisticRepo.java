package app.repositories.statistic;

import app.entities.statistic.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatisticRepo extends JpaRepository<CustomerStatistic, Long> {
    @Query(value = "DELETE * FROM customer_statistic WHERE customer_id = ?1", nativeQuery = true)
    void deleteStatisticWithCustomerId(Long customer_id);
}
