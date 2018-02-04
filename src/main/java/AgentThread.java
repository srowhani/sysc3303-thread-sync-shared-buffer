import java.util.*;

public class AgentThread extends Thread implements Runnable {
    private Random random;
    private List<Ingredient> ingredientList;
    private List<Ingredient> table;
    private List<ChefThread> chefThreadList;

    public AgentThread () {
        random = new Random();
        ingredientList = new ArrayList(Arrays.asList(new Bread(), new Jam(), new PeanutButter()));
        chefThreadList = new ArrayList(Arrays.asList(new ChefThread(), new ChefThread(), new ChefThread()));

        Iterator<Ingredient> grabOne = ingredientList.stream().iterator();
        chefThreadList.forEach(chefThread -> chefThread.setInfinite(grabOne.next()));

    }
    @Override
    public void run() {
        chefThreadList.forEach(ct -> ct.start());
        while (true) {
            table = new ArrayList(Arrays.asList(grab(), grab()));
            chefThreadList.forEach(chefThread -> chefThread.setIngredientList(table));
            try {
                this.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Ingredient grab () {
        return ingredientList.get(random.nextInt(ingredientList.size()));
    }

}
