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
    @ColumnDefault("0")
    private Byte discount_first;

    @Column(name = "discount_second")
    @ColumnDefault("0")
    private Byte discount_second;

    //текущая корзина товаров
    @OneToOne(mappedBy = "customer")
    private Sell bucket;
}
