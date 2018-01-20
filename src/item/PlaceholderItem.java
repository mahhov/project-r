package item;

public class PlaceholderItem extends Item {
    public static final int ID = -1;

    private static int count = 0;

    public PlaceholderItem() {
        super(ID, "PLACEHOLDER " + count++, false);
    }
}