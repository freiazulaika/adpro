package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        // Generate ID
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(UUID.randomUUID().toString());
        }

        // Product name validation
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProduct = new ArrayList<>();
        productRepository.findAll().forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findProductById(productId);
    }

    @Override
    public Product edit(Product product) {
        return productRepository.edit(product);
    }

    @Override
    public void delete(String productId) {
        productRepository.delete(productId);
    }
}