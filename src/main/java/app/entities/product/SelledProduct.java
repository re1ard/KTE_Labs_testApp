package app.entities.product;

import app.entities.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "selled_products")
@Getter
@Setter
@NoArgsConstructor
public class SelledProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Long product_id;

    @Column
    private Long customer_id;

    @Column
    private Long sell_id;

    @Column
    private Long original_price;

    @Column
    private Long discount_price;

    public SelledProduct(Long product_id, Long customer_id, Long sell_id, Long original_price, Long discount_price){
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.sell_id = sell_id;
        this.original_price = original_price;
        this.discount_price = discount_price;
    }
}
