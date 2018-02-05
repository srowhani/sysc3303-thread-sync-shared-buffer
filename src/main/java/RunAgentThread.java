/**
 * Static class used to run the program
 */
public class RunAgentThread {
    /**
     * Instantiate agent thread, and kick off thread.
     * @param args
     */
    public static void main(String[] args) {
        AgentThread activeAgentThread = new AgentThread();
        activeAgentThread.start();
    }
}
