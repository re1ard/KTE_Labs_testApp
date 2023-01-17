package app.services;

import app.entities.product.Product;
import app.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public Product newProduct(Product product){
        productRepo.save(product);
        return product;
    }

    public void changeDiscount(long product_id, byte discount){
        Product product = productRepo.getById(product_id);
        product.setDiscount(discount);
        productRepo.save(product);
    }

    public void reviewProduct(long customer_id, long product_id){

    }

}
