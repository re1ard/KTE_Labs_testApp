package app.entities.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_statistic")
@Setter
@Getter
@NoArgsConstructor
public class ProductStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(name = "product_id")
    private Long product_id;

    @Column(name = "complete_sells_count")
    //количество чеков
    private Long complete_sells_count;

    @Column(name = "total_product_sells_price")
    private Long total_product_sells_price;

    @Column(name = "total_discount_sells_price")
    private Long total_discount_sells_price;
}
