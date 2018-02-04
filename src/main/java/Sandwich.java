import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sandwich {
    public Sandwich (List<Ingredient> ingredientList) throws NotEnoughIngredientsException {
        List<Class> required = new ArrayList(Arrays.asList(Bread.class, Jam.class, PeanutButter.class));
        boolean hasAllMaterials = ingredientList.stream().anyMatch(item -> required.remove(item.getClass()));
        if (!hasAllMaterials) {
            throw new NotEnoughIngredientsException();
        }
    }
}
