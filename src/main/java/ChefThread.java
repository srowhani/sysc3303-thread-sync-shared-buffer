import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChefThread extends Thread implements Chef, Runnable {
    private Ingredient infinite;
    private AgentThread agent;
    private int performance;
    public ChefThread(AgentThread agentThread) {
        agent = agentThread;
        performance = 0;
    }

    @Override
    public void run() {
        while (!agent.isFinished()) {
            synchronized (agent) {
                try {
                    Sandwich sandwich = make();
                    System.out.println(String.format("[progress] Chef %s made sandwich", getId()));
                    eat(sandwich);
                    agent.cleanTable();
                    agent.reportProgress();
                    performance++;
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
    public void eat(Sandwich sandwich) throws InterruptedException {
        System.out.println(String.format("[progress] Chef %s eating sandwich (150ms)", getId()));
        List<Ingredient> availableIngredients = new ArrayList(agent.getTable());
        availableIngredients.add(getInfinite());
        System.out.println(String.format("[progress] availableIngredients: %s", availableIngredients.toString()));
        sleep(150);
        agent.notifyAll();
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
