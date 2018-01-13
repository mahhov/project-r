package item;

public abstract class Item {
    public final int id;
    public final String name;
    public final boolean stackable;

    Item(int id, String name, boolean stackable) {
        this.id = id;
        this.name = name;
        this.stackable = stackable;
    }
    
    public int getCount(){
        return 1;
    }

    public String print() {
        return name;
    }
}