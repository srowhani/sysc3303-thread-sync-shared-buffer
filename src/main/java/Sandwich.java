import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sandwich class is a combination of several ingredient classes
 * It's instantiation will attempt to make
 * If not all rquired materials are present, will throw an error
 * This error to be handled by chef thread.
 */
public class Sandwich {
    List<Ingredient> ingredients;
    public Sandwich (List<Ingredient> ingredientList) {
        ingredients = ingredientList;
    }
    /**
     * Print sandwich representation as string
     */
    @Override
    public String toString () {
        return ingredients.toString();
    }
}
