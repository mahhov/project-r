package character;

import character.gear.Helmet;
import item.Item;
import item.PlaceholderItem;
import item.StackableItem;

public class Inventory {
    private Item[] items;
    private Log log;

    Inventory(int size, Log log) {
        items = new Item[size];
        this.log = log;
        
        // todo remove
        add(new PlaceholderItem());
        add(new Helmet());
        add(new PlaceholderItem());
        add(new PlaceholderItem());
        add(new Helmet());
        add(new PlaceholderItem());
        add(new PlaceholderItem());
        add(new Helmet());
        add(new PlaceholderItem());
        add(new PlaceholderItem());
        add(new Helmet());
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
}