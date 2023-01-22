package app.repositories.product;

import app.entities.product.SelledProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SelledProductRepo extends JpaRepository<SelledProduct, Long> {

    @Query("SELECT count(selled_products) FROM SelledProduct selled_products WHERE selled_products.product_id = ?1 AND selled_products.customer_id = ?2")
    int buy_count(Long product_id, Long customer_id);

    @Query("SELECT count(distinct selled_product.sell_id) FROM SelledProduct selled_product WHERE selled_product.product_id = ?1")
    Optional<Long> getRegisterSellCountOnProduct(Long product_id);

    @Query("SELECT SUM(selled_product.original_price) FROM SelledProduct selled_product WHERE selled_product.product_id = ?1")
    Optional<Long> getOriginalPriceSum(Long product_id);

    @Query("SELECT SUM(selled_product.discount_price) FROM SelledProduct selled_product WHERE selled_product.product_id = ?1")
    Optional<Long> getDiscountSum(Long product_id);

    @Query("SELECT count(distinct selled_product.sell_id) FROM SelledProduct selled_product WHERE selled_product.customer_id = ?1")
    Optional<Long> getRegisterSellCountOnCustomer(Long customer_id);

    @Query("SELECT SUM(selled_product.original_price) FROM SelledProduct selled_product WHERE selled_product.customer_id = ?1")
    Optional<Long> getOriginalPriceSumPerCustomer(Long customer_id);

    @Query("SELECT SUM(selled_product.discount_price) FROM SelledProduct selled_product WHERE selled_product.customer_id = ?1")
    Optional<Long> getDiscountSumPerCustomer(Long customer_id);
}
