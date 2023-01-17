package app.entities.bucket;

import app.entities.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
//Заказ в корзине
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    //Ид сущности
    private Long id;

    @OneToMany
    @JoinColumn(name = "products_id")
    //Корзина
    private List<Product> products;

    @OneToOne
    @JoinColumn(name = "sell_id")
    private Sell sell;

    public void addProduct(Product product){
        products.add(product);
    }
}
