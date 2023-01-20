package app.repositories.statistic;

import app.entities.statistic.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatisticRepo extends JpaRepository<CustomerStatistic, Long> {
}
