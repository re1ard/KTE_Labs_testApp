package app.entities;

import app.entities.bucket.Sell;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_first")
    private Byte discount_first = 0;

    @Column(name = "discount_second")
    private Byte discount_second = 0;

    //текущая корзина товаров
    @OneToOne
    private Sell bucket;
}
