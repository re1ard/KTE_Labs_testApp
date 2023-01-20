package app.repositories;

import app.entities.product.SelledProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SelledProductRepo extends JpaRepository<SelledProduct, Long> {

    @Query("SELECT count(selled_products) FROM SelledProduct selled_products WHERE selled_products.product_id = ?1 AND selled_products.customer_id = ?2")
    int buy_count(Long product_id, Long customer_id);
}
