package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Tests")
public class ProductTest {

    @Test
    @DisplayName("Create product with valid name and price")
    void testValidProductCreation() {
        Product product = new Product("Laptop", 1000.0);

        assertEquals("Laptop", product.getName());
        assertEquals(1000.0, product.getPrice());
        assertEquals(0.0, product.getDiscount());
    }

    @Test
    @DisplayName("Create product with negative price should throw exception")
    void testNegativePriceConstructor() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Phone", -50.0)
        );

        assertEquals("Price must be non-negative", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 90.0",
            "20, 80.0",
            "50, 50.0",
            "0, 100.0"
    })
    @DisplayName("Apply valid discount percentages")
    void testApplyValidDiscount(double discount, double expectedFinalPrice) {
        Product product = new Product("Item", 100.0);

        product.applyDiscount(discount);

        assertEquals(discount, product.getDiscount());
        assertEquals(expectedFinalPrice, product.getFinalPrice());
    }

    @Test
    @DisplayName("Apply negative discount should throw exception")
    void testApplyNegativeDiscount() {
        Product product = new Product("Tablet", 500.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> product.applyDiscount(-10)
        );

        assertEquals("Invalid discount", exception.getMessage());
    }

    @Test
    @DisplayName("Apply discount greater than fifty should throw exception")
    void testApplyDiscountAboveFifty() {
        Product product = new Product("Tablet", 500.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> product.applyDiscount(60)
        );

        assertEquals("Invalid discount", exception.getMessage());
    }

    @Test
    @DisplayName("Get final price after discount")
    void testGetFinalPrice() {
        Product product = new Product("Headphones", 200.0);
        product.applyDiscount(25);

        assertEquals(25.0, product.getDiscount());
        assertEquals(150.0, product.getFinalPrice());
    }

    @Test
    @Timeout(1)
    @DisplayName("Get final price should complete quickly")
    void testGetFinalPriceTimeout() {
        Product product = new Product("Monitor", 300.0);
        product.applyDiscount(10);

        double finalPrice = product.getFinalPrice();

        assertEquals(270.0, finalPrice);
        assertTrue(finalPrice > 0);
    }
}