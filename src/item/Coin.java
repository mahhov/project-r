package item;

public class Coin extends StackableItem {
    public static final int ID = 1;
    public static final String NAME = "Coin";

    public Coin(int count) {
        super(ID, NAME, count);
    }
}