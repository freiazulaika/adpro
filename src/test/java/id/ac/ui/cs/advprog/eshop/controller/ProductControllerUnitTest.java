package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerUnitTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();

        String viewName = productController.createProductPost(product, model);

        verify(productService, times(1)).create(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);

        verify(model, times(1)).addAttribute("products", productList);
        assertEquals("ProductList", viewName);
    }

    @Test
    void testEditProductPage() {
        Product product = new Product();
        when(productService.findProductById("1")).thenReturn(product);

        String viewName = productController.editProductPage("1", model);

        verify(model, times(1)).addAttribute("product", product);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();

        String viewName = productController.editProductPost(product);

        verify(productService, times(1)).edit(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("1");

        verify(productService, times(1)).delete("1");
        assertEquals("redirect:/product/list", viewName);
    }
}
