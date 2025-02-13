package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EditProductUnitTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductId("2388129087");
        product.setProductName("Sebelum Ganti");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("2388129087");
        updatedProduct.setProductName("Sesudah Ganti");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.edit(updatedProduct);
        assertNotNull(result);
        assertEquals("Sesudah Ganti", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditProductFailure() {
        Product product = new Product();
        product.setProductId("1234567890");
        product.setProductName("Fail Test");
        product.setProductQuantity(5);

        Product result = productRepository.edit(product);
        assertNull(result);
    }
}
