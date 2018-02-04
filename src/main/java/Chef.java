public interface Chef {
    Sandwich make() throws NotEnoughIngredientsException;
    void eat(Sandwich s);
}
