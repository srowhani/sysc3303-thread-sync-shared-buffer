import java.util.function.Function;

public interface Chef {
    Sandwich make();
    void eat(Sandwich s) throws InterruptedException;
}
