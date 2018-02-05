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
    public Sandwich (List<Ingredient> ingredientList) throws NotEnoughIngredientsException {
        List<Class> required = new ArrayList(Arrays.asList(Bread.class, Jam.class, PeanutButter.class));
        boolean hasAllMaterials = ingredientList.stream().allMatch(item -> required.remove(item.getClass()));
        if (!hasAllMaterials) {
            throw new NotEnoughIngredientsException();
        }
    }
}
