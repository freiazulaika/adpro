package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUnitTest {

    @InjectMocks
    ProductRepository productRepository;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = new Product();
        existingProduct.setProductId("1234567890");
        existingProduct.setProductName("Sup Ikan");
        existingProduct.setProductQuantity(5);
        productRepository.create(existingProduct);
    }

    @Test
    void testDeleteProductSuccess() {
        productRepository.delete("1234567890");
        assertNull(productRepository.findProductById("1234567890"));
    }

    @Test
    void testDeleteProductFailure() {
        assertDoesNotThrow(() -> productRepository.delete("1111111111"));
    }
}
