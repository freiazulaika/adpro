package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void editPageTitleCheck(ChromeDriver driver) {
        createProduct(driver);
        driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
        String pageTitle = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Edit Product", pageTitle);
    }

    @Test
    void editProductSuccessTest(ChromeDriver driver) {
        createProduct(driver);
        String initialName = driver.findElement(By.className("product-card")).getText();
        driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();

        WebElement nameInput = driver.findElement(By.id("productName"));
        WebElement quantityInput = driver.findElement(By.id("productQuantity"));
        WebElement saveButton = driver.findElement(By.cssSelector(".btn-success"));

        nameInput.clear();
        nameInput.sendKeys("Setelah Edit");
        quantityInput.clear();
        quantityInput.sendKeys("1234");

        saveButton.click();

        driver.get(baseUrl + "/product/list");
        String updatedName = driver.findElement(By.className("product-card")).getText();

        assertNotEquals(initialName, updatedName);
        assertTrue(updatedName.contains("Setelah Edit"));
    }

    @Test
    void cancelEditTest(ChromeDriver driver) {
        createProduct(driver);

        String initialName = driver.findElement(By.className("product-card")).getText();
        driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();
        driver.findElement(By.cssSelector(".btn-secondary")).click();

        String currentName = driver.findElement(By.className("product-card")).getText();
        assertEquals(initialName, currentName);
    }

    private void createProduct(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement saveButton = driver.findElement(By.cssSelector(".btn.btn-primary"));

        nameInput.sendKeys("Functional Test");
        quantityInput.sendKeys("1000");
        saveButton.click();
    }
}