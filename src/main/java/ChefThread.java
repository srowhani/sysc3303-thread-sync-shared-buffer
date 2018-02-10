import java.util.*;
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
    private final SharedBuffer<Ingredient> table;
    private Ingredient infinite;
    private AgentThread agent;
    private int performance;
    private boolean isEating;
    private Random generator;

    public ChefThread(SharedBuffer<Ingredient> tableBuffer) {
        agent = null;
        table = tableBuffer;
        performance = 0;
        isEating = false;
        generator = new Random();
    }

    @Override
    public void run() {
        while (!agent.isFinished()) {
            Sandwich s;
            synchronized (table) {
            	// wait while ingredients are not correct and thread
            	// is currently not eating
                while (!hasCorrectIngredients() || isEating) {
                    if (agent.isFinished()) { // exit condition of in case sync block has already begun
                        table.notifyAll(); // notify to remove lock on other waiting threads (agent)
                        return; // end early by returning function
                    }
                    try {
                        table.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                s = make();
                Logger.getInstance().info("[progress] %s made sandwich %s", this, s);
                table.clear(); // wipe table, signaling agent that they can begin refilling it
                agent.reportProgress(); // publish progress so agent can decide whether or not to end
                performance++; // self report of performance to determine how much work each chef did
            }

            try {
                eat(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean hasCorrectIngredients() {
        Set<Class> s = new HashSet();

        List<Ingredient> availableIngredients = new ArrayList<Ingredient>(table);
        availableIngredients.add(getInfinite());

        table.forEach(item -> s.add(item.getClass()));
        s.add(getInfinite().getClass());

        return s.size() == 3;
    }

    @Override
    public synchronized Sandwich make() {
        List<Ingredient> availableIngredients = new ArrayList<Ingredient>(table);
        availableIngredients.add(getInfinite());

        return new Sandwich(availableIngredients);
    }

    @Override
    public void eat(Sandwich sandwich) throws InterruptedException {
        int timeout = 150 + generator.nextInt(75); // wait variable time to signify eating
        Logger.getInstance().debug("[progress] %s - [start] eating sandwich - %s", this, sandwich);
        isEating = true;

        sleep(timeout);

        isEating = false;
        Logger.getInstance().debug("[progress] %s - [end] eating sandwich - %s", this, sandwich);
    }

    public Ingredient getInfinite() {
        return infinite;
    }

    public void setInfinite(Ingredient infinite) {
        this.infinite = infinite;
    }

    public void setAgent(AgentThread agent) {
        this.agent = agent;
    }

    public int getPerformance() {
        return performance;
    }
}
