package app.entities.product;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.util.HashMap;

@Setter
@Getter
@Table(name = "purchased_products")
public class PurchasedProduct extends Product {
    private Review user_review;

    private Double avg_rate;

    private HashMap <Byte, Integer> users_rate;

    //Получение средней оценки //4
    public Double getAvg_rate() {
        return users_rate.keySet().stream().mapToDouble(Double::valueOf).average().getAsDouble();
    }
}
