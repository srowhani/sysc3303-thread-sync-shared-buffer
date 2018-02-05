import java.util.function.Function;

public interface Chef {
    Sandwich make() throws NotEnoughIngredientsException;
    void eat(Sandwich s, Function<Void, Void> onComplete) throws InterruptedException;
}
