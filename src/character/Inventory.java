package character;

import item.Item;
import item.StackableItem;
import util.LList;

public class Inventory {
    private Item[] items;
    private LList<String> log;

    Inventory(int size) {
        items = new Item[size];
        log = new LList<>();
    }

    void add(Item item) {
        if (findRoomAndAdd(item))
            log.addTail(String.format("Obtained %s", item.print()));
        else
            log.addTail(String.format("Inventory full !! Lost %s", item.print()));
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
}