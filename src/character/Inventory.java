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

    void add(Item item) {
        if (findRoomAndAdd(item))
            log.add(String.format("Obtained %s", item.print()));
        else
            log.add(String.format("Inventory full !! Lost %s", item.print()));
    }

    private boolean findRoomAndAdd(Item item) {
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

    public int getSize() {
        return items.length;
    }

    public Item getItem(int i) {
        return i < items.length ? items[i] : null;
    }

    public Iterable<String> getTexts() {
        return log;
    }
}