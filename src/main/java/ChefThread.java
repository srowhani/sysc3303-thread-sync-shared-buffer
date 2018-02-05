import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * ChefThread
 *
 * Instantiates with reference to agent thread.
 *
 * On thread instantiation, continues to run until agent reports finished.
 * Synchronizes on agent object
 * Attempts to make sandwich
 *   If is able to make sandwich (has all correct materials)
 *   Begins eating, notifies agent completion
 * If cannot make sandwich, immediately notifies to continue other attempts.
 * @see Chef
 * @see java.lang.Thread
 */
public class ChefThread extends Thread implements Chef, Runnable {
    private Ingredient infinite;
    private AgentThread agent;
    private int performance;

    /**
     * Contructor for ChefThread instance.
     * @param agentThread
     */
    public ChefThread(AgentThread agentThread) {
        agent = agentThread;
        performance = 0;
    }

    @Override
    public void run() {
        while (!agent.isFinished()) {
            synchronized (agent) {
                try {
                    Sandwich sandwich = make(); // will throw error if not able to make
                    System.out.println(String.format("[progress] Chef %s made sandwich", getId()));

                    eat(sandwich, onComplete -> {
                        agent.cleanTable();
                        agent.reportProgress();
                        performance++;
                        return null;
                    });

                    // System.out.println(String.format("ChefThread[%s] made sandwich", this.getId()));
                } catch (NotEnoughIngredientsException e) {
                    // notify in the case that they aren't able to make a sandwich
                    agent.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public synchronized Sandwich make() throws NotEnoughIngredientsException {
        if (agent.isTableClean()) {
            throw new NotEnoughIngredientsException();
        }
        List<Ingredient> availableIngredients = new ArrayList(agent.getTable());
        availableIngredients.add(getInfinite());

        return new Sandwich(availableIngredients);
    }

    @Override
    public void eat(Sandwich sandwich, Function<Void, Void> onComplete) throws InterruptedException {
        System.out.println(String.format("[progress] Chef %s eating sandwich (150ms)", getId()));
        List<Ingredient> availableIngredients = new ArrayList(agent.getTable());
        availableIngredients.add(getInfinite());
        System.out.println(String.format("[progress] availableIngredients: %s", availableIngredients.toString()));
        sleep(150);
        agent.notifyAll();
        onComplete.apply(null);
    }

    public Ingredient getInfinite() {
        return infinite;
    }

    public void setInfinite(Ingredient infinite) {
        this.infinite = infinite;
    }

    public AgentThread getAgent() {
        return agent;
    }

    public void setAgent(AgentThread agent) {
        this.agent = agent;
    }

    public int getPerformance() {
        return performance;
    }
}
