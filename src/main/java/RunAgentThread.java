import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Static class used to run the program
 */
public class RunAgentThread {
    /**
     * Instantiate agent thread, and kick off thread.
     * @param args
     */
    public static void main(String[] args) {
        SharedBuffer tableBuffer = new SharedBuffer(2);

        List<Ingredient> availableIngredients = new ArrayList<>(
                Arrays.asList(new Bread(), new Jam(), new PeanutButter()));
        // instantiate active thread
        AgentThread activeAgentThread = new AgentThread(tableBuffer, availableIngredients);
        // 1 for 1 infinite ingredient for chef thread
        availableIngredients.forEach(ingredient -> {
            ChefThread registerChef = new ChefThread(tableBuffer);
            registerChef.setInfinite(ingredient);
            activeAgentThread.register(registerChef); // assign thread context to chef
        });
        activeAgentThread.start();
    }
}
