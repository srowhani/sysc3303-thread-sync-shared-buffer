import java.util.ArrayList;
import java.util.List;

public class ChefThread extends Thread implements Chef, Runnable {
    private Ingredient infinite;
    private List<Ingredient> ingredientList;

    public ChefThread () {
        infinite = null;
        ingredientList = new ArrayList();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Sandwich sandwich = make();
                eat(sandwich);
                this.notify();
            } catch (NotEnoughIngredientsException e) {
                System.out.println(String.format("ChefThread[%s] cannot make sandwich", this.getId()));
            }

        }
    }

    @Override
    public synchronized Sandwich make() throws NotEnoughIngredientsException {
        return new Sandwich(ingredientList);
    }

    @Override
    public void eat(Sandwich sandwich) {

    }

    public Ingredient getInfinite() {
        return infinite;
    }

    public void setInfinite(Ingredient infinite) {
        this.infinite = infinite;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
