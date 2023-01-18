package app.entities.product;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
public class PurchasedProduct extends Product {
    public PurchasedProduct(Long product_id){
        this.id = product_id;
    }

    private Long id;

    private Review user_review;

    private Double avg_rate;

    private HashMap <Byte, Integer> users_rate;

    //Получение средней оценки //4
    public Double getAvg_rate() {
        if (users_rate == null) return 0.0;
        return users_rate.keySet().stream().mapToDouble(Double::valueOf).average().getAsDouble();
    }
}
