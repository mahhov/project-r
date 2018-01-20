package character;

import item.Item;
import item.StackableItem;
import ui.TextSystem;
import util.Queue;

public class Inventory implements TextSystem {
    private static final int LOG_SIZE = 8;
    private Item[] items;
    private Queue<String> log;

    Inventory(int size) {
        items = new Item[size];
        log = new Queue<>(LOG_SIZE);
    }

    void addWithLog(Item item) {
        if (add(item))
            log.add(String.format("Obtained %s", item.getText()));
        else
            log.add(String.format("Inventory full !! Lost %s", item.getText()));
    }

    boolean add(Item item) {
        if (item.stackable)
            for (Item inventoryItem : items)
                if (inventoryItem != null && inventoryItem.id == item.id) {
                    ((StackableItem) inventoryItem).add(item.getCount());
                    return true;
                }

        for (int i = 0; i < items.length; i++)
            if (items[i] == null) {
                items[i] = item;
                return true;
            }

        return false;
    }


    public void swap(int moving, int selected) {
        Item temp = items[moving];
        items[moving] = items[selected];
        items[selected] = temp;
    }

    void put(int index, Item item) {
        items[index] = item;
    }

    public int getSize() {
        return items.length;
    }

    public Item getItem(int i) {
        return i < items.length ? items[i] : null;
    }

    @Override
    public Iterable<String> getTexts() {
        return log;
    }
}