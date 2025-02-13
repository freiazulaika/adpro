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
public class CreateProductFunctionalTest {

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
    void pageTitleCheck(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        String pageTitle = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Product List", pageTitle);
    }

    @Test
    void navigateCheck(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        assertEquals("Create New Product", buttonClickTest(driver));
    }

    @Test
    void createNewProductTest(ChromeDriver driver) {
        assertNotNull(createProductTest(driver));
    }

    String buttonClickTest(ChromeDriver driver) {
        WebElement createProductButton = driver.findElement(By.cssSelector(".btn-success.mb-4"));
        assertNotNull(createProductButton);
        createProductButton.click();
        return driver.findElement(By.tagName("h3")).getText();
    }

    String createProductTest(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement addProductName = driver.findElement(By.id("nameInput"));
        WebElement addProductQuantity = driver.findElement(By.id("quantityInput"));
        WebElement saveButton = driver.findElement(By.cssSelector(".btn.btn-primary"));

        assertNotNull(addProductName);
        assertNotNull(addProductQuantity);
        assertNotNull(saveButton);

        addProductName.sendKeys("TESTING");
        addProductQuantity.sendKeys("120");
        saveButton.click();

        driver.get(baseUrl + "/product/list");
        assertNotNull(driver.findElement(By.className("product-card")));
        return driver.findElement(By.className("product-card")).getText();
    }
}