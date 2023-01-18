package app.entities.bucket;

import app.entities.Customer;
import app.entities.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "sells")
@Getter
@Setter
@NoArgsConstructor
//Продажи
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    //Ид сущности
    private Long id;

    @OneToOne
    //Покупатель
    private Customer customer;

    @Column(name = "sell_date")
    //Дата продажи
    private LocalDateTime sell_date;

    //Номер чека
    @Column(name = "sell_id")
    private Short sell_id;

    @Column(name = "complete")
    //Зарегистрирован ли чек
    private Boolean complete;

    @OneToOne(mappedBy = "sell")
    //Заказ в корзине
    private Order order;

    @Column(name = "final_cost")
    //Конечная/Текущая цена на заказ
    private Long cost;

    public void addToOrder(Product product){
        order.addProduct(product);
    }
}
