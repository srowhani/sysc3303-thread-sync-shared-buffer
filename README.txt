sysc3303 - a2
Seena Rowhani
100945353

# Assignment Guidelines

The Sandwich-Making Chefs Problem. (This problem was first published as the cigarette-smokers problem by S.
Patil in 1971, and is one of several classic process-coordination problems that are used to evaluate facilities for
synchronizing concurrent threads and processes.)

Consider a system with three chef threads and one agent thread. Each chef continuously makes a sandwich and
then eats it. But to make and eat a sandwich, the chef needs three ingredients: bread, peanut butter, and jam. One
of the chef threads has an infinite supply of bread, another has peanut butter, and the third has jam. The agent
has an infinite supply of all three ingredients. The agent randomly selects two of the ingredients and places them
on a table. The chef who has the remaining ingredient then makes and eats a sandwich, signalling the agent on
completion. The agent then puts out another two of the three ingredients, and the cycle repeats.
If you don't like peanut butter and jam sandwiches, you may choose any three "ingredients" that make up your
favourite meal!
Follow the design process outlined in class to develop a Java monitor that synchronizes the agent and the chefs.
Produce UCM(s), UML collaboration diagrams and a UML class diagram for your design. Then write a program
(following your design) to simulate the agent and the chefs. The program should run until 20 sandwiches have
been made and consumed.

# Directory Structure

- `src/main/java` - All relevant code
- `diagrams/*` - contains all diagrams (ucm / collab / uml)
# Setup Instructions

- Import project into eclipse
- Right click on `src/main/java/RunAgentThread.java`
- Click run as dropdown option