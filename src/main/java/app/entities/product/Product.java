package app.entities.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
//Продукт который существует
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    //Уникальный ид продукта
    private Long id;

    @Column(name = "name", nullable = false)
    //Название продукта
    private String name;

    @Column(name = "description")
    //Описание продукта
    private String description;

    @Column(name = "price", nullable = false)
    //цена в копейках
    private Long price;

    @Column(name = "discount")
    private Byte discount;

    @OneToMany
    @JoinColumn(name = "review_ids")
    //оценки пользователей
    private List<Review> reviewList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice(){
        //Цена вместе с копейками
        //Например: 245 рублей 35 копеек это 24535
        return price - price * (discount / 100);
    }
}
