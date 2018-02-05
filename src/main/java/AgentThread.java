import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AgentThread extends Thread implements Runnable {
    private Random random;
    private List<Ingredient> ingredientList;
    private List<Ingredient> table;
    private List<ChefThread> chefThreadList;
    private int sandwichCount;
    private int maxSandwiches;
    private boolean isTableClean;

    public AgentThread () {
        random = new Random();
        sandwichCount = 0;
        maxSandwiches = 20;
        ingredientList = new ArrayList(Arrays.asList(new Bread(), new Jam(), new PeanutButter()));
        isTableClean = true;

        chefThreadList = new ArrayList(ingredientList.stream().map(ingredient -> {
            ChefThread instance = new ChefThread(this);
            instance.setInfinite(ingredient);
            return instance;
        }).collect(Collectors.toList()));
    }

    @Override
    public void run() {
        chefThreadList.forEach(t -> t.start());
        while (true) { // continue until all sandwiches have been produced
            List<Ingredient> clone = ingredientList.stream().collect(Collectors.toList());
            table = new ArrayList(Arrays.asList(grab(clone), grab(clone)));
            isTableClean = false;
            synchronized (this) {
                try {
                    this.wait(); // if state is not ready, voluntarily give up lock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.notifyAll();
            }
            if (isFinished()) {
                // System.out.println("finished");
                break;
            }
        }

        System.out.println("[done] Total sandwiches made: " + sandwichCount);
        chefThreadList.forEach(
            chef -> System.out.println("[done] Chef " + chef.getId() + " made " + chef.getPerformance() + " sandwiches"));
    }

    private synchronized Ingredient grab (List<Ingredient> c) {
        return c.remove(random.nextInt(c.size()));
    }

    public void cleanTable() {
        isTableClean = true;
        table = new ArrayList();
    }

    public synchronized void reportProgress() {
        sandwichCount++;
    }

    public synchronized boolean isFinished () {
        return sandwichCount >= maxSandwiches;
    }

    public synchronized List<Ingredient> getTable () {
        return table;
    }

    public synchronized boolean isTableClean() {
        return isTableClean;
    }
}
