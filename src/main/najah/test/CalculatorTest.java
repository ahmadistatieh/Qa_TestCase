package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {

    private Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting Calculator tests...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finished Calculator tests...");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Calculator setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Calculator test finished");
    }

    @Test
    @Order(1)
    @DisplayName("Add multiple positive numbers")
    void testAddPositiveNumbers() {
        int result = calc.add(1, 2, 3, 4);

        assertEquals(10, result);
        assertNotEquals(9, result);
    }

    @Test
    @Order(2)
    @DisplayName("Add with empty input should return zero")
    void testAddEmptyInput() {
        int result = calc.add();

        assertEquals(0, result);
        assertTrue(result == 0);
    }

    @ParameterizedTest
    @CsvSource({
            "6, 2, 3",
            "9, 3, 3",
            "20, 5, 4",
            "15, 3, 5"
    })
    @Order(3)
    @DisplayName("Divide valid numbers using parameterized test")
    void testDivideParameterized(int a, int b, int expected) {
        int result = calc.divide(a, b);

        assertEquals(expected, result);
        assertTrue(result > 0);
    }

    @Test
    @Order(4)
    @DisplayName("Divide by zero should throw ArithmeticException")
    void testDivideByZero() {
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calc.divide(10, 0)
        );

        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Factorial of zero should return one")
    void testFactorialZero() {
        int result = calc.factorial(0);

        assertEquals(1, result);
        assertNotEquals(0, result);
    }

    @Test
    @Order(6)
    @DisplayName("Factorial of positive number")
    void testFactorialPositive() {
        int result = calc.factorial(5);

        assertEquals(120, result);
        assertTrue(result > 0);
    }

    @Test
    @Order(7)
    @DisplayName("Factorial of negative number should throw IllegalArgumentException")
    void testFactorialNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calc.factorial(-3)
        );

        assertEquals("Negative input", exception.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Factorial should complete within timeout")
    void testFactorialTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            int result = calc.factorial(10);
            assertEquals(3628800, result);
        });
    }

    @Test
    @Disabled("Intentional failing test - fix by changing expected value from 100 to 120")
    @Order(9)
    @DisplayName("Intentional failing test for demonstration")
    void failingTestExample() {
        assertEquals(100, calc.factorial(5));
    }
}