import java.util.*;
import java.util.stream.Collectors;

public class AgentThread extends Thread implements Runnable {
    private Random random;
    private SharedBuffer<Ingredient> table;
    private List<Ingredient> ingredientList;
    private List<ChefThread> chefThreadList;
    private int sandwichCount;
    private int maxSandwiches;

    public AgentThread(SharedBuffer<Ingredient> tableBuffer, List<Ingredient> availableIngredients) {
        random = new Random();
        sandwichCount = 0;
        maxSandwiches = 20;
        table = tableBuffer;
        chefThreadList = new ArrayList();
        ingredientList = availableIngredients;
    }

    public void register (ChefThread t) {
        t.setAgent(this);
        chefThreadList.add(t);
    }

    @Override
    public void run() {
        chefThreadList.forEach(t -> t.start());
        System.out.println("Past this point");
        System.out.println(!isFinished());
        while (!isFinished()) { // continue until all sandwiches have been produced
            List<Ingredient> clone = new ArrayList<>(ingredientList);

            synchronized (table) {
                table.fill(Arrays.asList(grab(clone), grab(clone)));

                while (table.isFull()) {
                    try {
                        table.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        Logger.getInstance().debug("[done] Total sandwiches made: " + sandwichCount);
        chefThreadList.forEach(
            chef -> Logger.getInstance().info("[done] Chef %s made %s sandwiches", chef.getId(), chef.getPerformance()));
    }

    private synchronized Ingredient grab (List<Ingredient> c) {
        return c.remove(random.nextInt(c.size()));
    }

    public synchronized void reportProgress() {
        sandwichCount++;
    }

    public boolean isFinished () {
        return sandwichCount >= maxSandwiches;
    }


    public int getSandwichCount () {
        return sandwichCount;
    }
}
