package app.entities.statistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer_statistic")
@Setter
@Getter
@NoArgsConstructor
public class CustomerStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_id")
    //ид покупателя
    private Long customer_id;

    @Column(name = "complete_sells_count")
    //количество чеков
    private Long complete_sells_count;

    @Column(name = "total_full_buy_price")
    //общая стоимость чеков по исходной цене
    private Long total_full_buy_price;

    @Column(name = "total_full_discount")
    private Long total_full_discount;
}
