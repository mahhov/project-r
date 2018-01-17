package item;

public abstract class StackableItem extends Item {
    private int count;

    StackableItem(int id, String name, int count) {
        super(id, name, true);
        this.count = count;
    }

    public void add(int count) {
        this.count += count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String print() { // todo rename getText
        return String.format("%d %s", count, name);
    }
}