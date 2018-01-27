package character;

import item.gear.Gear;
import item.gear.Module;
import item.gear.Property;
import item.Item;
import util.MathRandom;

public class ModuleCrafting {
    private static final int MIN_VALUE = 50, MAX_VALUE = 151;
    private static final int ENCHANTABILITY_PENALTY_BASE_RESET = 5;
    private static final int GLOW_COST_BASE = 10;

    private int inventorySelect;
    private Module module;
    private Log log;
    private Inventory inventory;
    private Glows glows;

    ModuleCrafting(Log log, Inventory inventory, Glows glows) {
        this.log = log;
        this.inventory = inventory;
        this.glows = glows;
    }

    public void selectInventoryModule(int delta) {
        int start = inventorySelect;
        Item item;
        do {
            inventorySelect += delta;
            if (inventorySelect > inventory.getSize())
                inventorySelect -= inventory.getSize();
            else if (inventorySelect < 0)
                inventorySelect += inventory.getSize();

            item = inventory.getItem(inventorySelect);

            if (item != null && Gear.isModule(item.id)) {
                module = (Module) item;
                return;
            }
        } while (inventorySelect != start);
    }

    public void craftBase(Glows.Glow[] glows) {
        if (module == null) {
            log.add("Must select a gear");
            return;
        }

        if (glows.length != 4) {
            log.add("Must select exactly 4 glows");
            return;
        }

        if (module.getNumProperties() >= Module.MODULE_MAX_PROPERTIES) {
            log.add("Item must have at most " + (Module.MODULE_MAX_PROPERTIES - 1) + " properties");
            return;
        }

        if (!this.glows.consume(glows, GLOW_COST_BASE)) {
            log.add("Requires " + GLOW_COST_BASE + " of each glow selected");
            return;
        }

        Property.PropertyType propertyType = module.getRandomPrimaryProperty();
        float multiply = module.getEnchantability() / 100f;
        int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * multiply));
        module.addProperty(new Property(propertyType, value));
    }

    public void resetBase() {
        if (module == null) {
            log.add("Must select a module");
            return;
        }

        if (module.getNumProperties() == 0) {
            log.add("Item must have at least 1 property");
            return;
        }

        module.removeProperties(0);
        module.decreaseEnchantability(ENCHANTABILITY_PENALTY_BASE_RESET);
    }

    public Module getModule() {
        return module;
    }
}