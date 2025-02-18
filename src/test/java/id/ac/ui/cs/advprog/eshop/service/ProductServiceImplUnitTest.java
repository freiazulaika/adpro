package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    void testCreateProduct_WithExistingId() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertEquals("existing-id", createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testCreateProduct_EmptyName() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(10);

        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
        verify(productRepository, never()).create(any(Product.class));
    }

    @Test
    void testCreateProduct_NullName() {
        Product product = new Product();
        product.setProductName(null);
        product.setProductQuantity(10);

        assertThrows(IllegalArgumentException.class, () -> productService.create(product));
        verify(productRepository, never()).create(any(Product.class));
    }

    @Test
    void testFindAll_Success() {
        List<Product> productList = new ArrayList<>();
        productList.add(createTestProduct("1", "Product 1"));
        productList.add(createTestProduct("2", "Product 2"));

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getProductName());
        assertEquals("Product 2", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>().iterator());

        List<Product> result = productService.findAll();

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById_Success() {
        Product product = createTestProduct("test-id", "Test Product");
        when(productRepository.findProductById("test-id")).thenReturn(product);

        Product result = productService.findProductById("test-id");

        assertNotNull(result);
        assertEquals("test-id", result.getProductId());
        assertEquals("Test Product", result.getProductName());
        verify(productRepository, times(1)).findProductById("test-id");
    }

    @Test
    void testFindProductById_NotFound() {
        when(productRepository.findProductById("non-existent-id")).thenReturn(null);
        Product result = productService.findProductById("non-existent-id");
        assertNull(result);
        verify(productRepository, times(1)).findProductById("non-existent-id");
    }

    @Test
    void testEditProduct_Success() {
        Product product = createTestProduct("test-id", "Updated Product");
        when(productRepository.edit(any(Product.class))).thenReturn(product);
        Product result = productService.edit(product);
        assertNotNull(result);
        assertEquals("Updated Product", result.getProductName());
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDeleteProduct_Success() {
        String productId = "test-id";
        doNothing().when(productRepository).delete(productId);
        productService.delete(productId);
        verify(productRepository, times(1)).delete(productId);
    }

    private Product createTestProduct(String id, String name) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(10);
        return product;
    }
}