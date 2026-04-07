package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RecipeBook Tests")
@Execution(ExecutionMode.CONCURRENT)
public class RecipeBookTest {

    private Recipe createRecipe(String name, String price, String coffee, String milk, String sugar, String chocolate)
            throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        return recipe;
    }

    @Test
    @DisplayName("Recipe book should start with four empty slots")
    void testInitialRecipeBookSize() {
        RecipeBook book = new RecipeBook();

        assertEquals(4, book.getRecipes().length);
        assertNull(book.getRecipes()[0]);
    }

    @Test
    @DisplayName("Add valid recipe successfully")
    void testAddRecipe() throws Exception {
        RecipeBook book = new RecipeBook();
        Recipe recipe = createRecipe("Espresso", "10", "2", "0", "1", "0");

        boolean added = book.addRecipe(recipe);

        assertTrue(added);
        assertEquals("Espresso", book.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Adding duplicate recipe should fail")
    void testAddDuplicateRecipe() throws Exception {
        RecipeBook book = new RecipeBook();
        Recipe recipe1 = createRecipe("Latte", "15", "2", "2", "1", "0");
        Recipe recipe2 = createRecipe("Latte", "20", "3", "1", "1", "1");

        boolean firstAdd = book.addRecipe(recipe1);
        boolean secondAdd = book.addRecipe(recipe2);

        assertTrue(firstAdd);
        assertFalse(secondAdd);
    }

    @Test
    @DisplayName("Adding recipe to full recipe book should fail")
    void testAddRecipeToFullBook() throws Exception {
        RecipeBook book = new RecipeBook();

        assertTrue(book.addRecipe(createRecipe("R1", "10", "1", "1", "1", "1")));
        assertTrue(book.addRecipe(createRecipe("R2", "10", "1", "1", "1", "1")));
        assertTrue(book.addRecipe(createRecipe("R3", "10", "1", "1", "1", "1")));
        assertTrue(book.addRecipe(createRecipe("R4", "10", "1", "1", "1", "1")));

        boolean added = book.addRecipe(createRecipe("R5", "10", "1", "1", "1", "1"));

        assertFalse(added);
    }

    @Test
    @DisplayName("Delete existing recipe should return its name")
    void testDeleteRecipe() throws Exception {
        RecipeBook book = new RecipeBook();
        Recipe recipe = createRecipe("Mocha", "18", "2", "1", "1", "2");
        book.addRecipe(recipe);

        String deletedName = book.deleteRecipe(0);

        assertEquals("Mocha", deletedName);
        assertEquals("", book.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Delete non existing recipe should return null")
    void testDeleteNullRecipe() {
        RecipeBook book = new RecipeBook();

        String deletedName = book.deleteRecipe(0);

        assertNull(deletedName);
    }

    @Test
    @DisplayName("Edit existing recipe should return old recipe name")
    void testEditRecipe() throws Exception {
        RecipeBook book = new RecipeBook();
        Recipe oldRecipe = createRecipe("Cappuccino", "12", "2", "2", "1", "0");
        Recipe newRecipe = createRecipe("Americano", "14", "3", "0", "0", "0");

        book.addRecipe(oldRecipe);
        String editedName = book.editRecipe(0, newRecipe);

        assertEquals("Cappuccino", editedName);
        assertEquals("", book.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Edit non existing recipe should return null")
    void testEditNullRecipe() throws Exception {
        RecipeBook book = new RecipeBook();
        Recipe newRecipe = createRecipe("Flat White", "16", "2", "2", "0", "0");

        String editedName = book.editRecipe(0, newRecipe);

        assertNull(editedName);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 10",
            "0, 0",
            "25, 25"
    })
    @DisplayName("Set valid recipe prices")
    void testSetValidPrice(String inputPrice, int expected) throws Exception {
        Recipe recipe = new Recipe();

        recipe.setPrice(inputPrice);

        assertEquals(expected, recipe.getPrice());
    }

    @Test
    @DisplayName("Set invalid negative price should throw RecipeException")
    void testSetNegativePrice() {
        Recipe recipe = new Recipe();

        RecipeException exception = assertThrows(
                RecipeException.class,
                () -> recipe.setPrice("-5")
        );

        assertEquals("Price must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Set invalid coffee amount should throw RecipeException")
    void testSetInvalidCoffeeAmount() {
        Recipe recipe = new Recipe();

        RecipeException exception = assertThrows(
                RecipeException.class,
                () -> recipe.setAmtCoffee("abc")
        );

        assertEquals("Units of coffee must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Recipe toString should return recipe name")
    void testRecipeToString() {
        Recipe recipe = new Recipe();
        recipe.setName("Hot Chocolate");

        assertEquals("Hot Chocolate", recipe.toString());
        assertEquals("Hot Chocolate", recipe.getName());
    }

    @Test
    @Timeout(1)
    @DisplayName("Get recipes should complete quickly")
    void testGetRecipesTimeout() {
        RecipeBook book = new RecipeBook();

        Recipe[] recipes = book.getRecipes();

        assertNotNull(recipes);
        assertEquals(4, recipes.length);
    }
}