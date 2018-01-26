package character;

import character.gear.Gear;
import character.gear.Module;
import item.Item;
import util.MathRandom;

public class ModuleCrafting {
    public static final int MODULE_MAX_PROPERTIES = 7, MIN_ENCHANTABILITY = 10;

    private static final int MIN_VALUE = 10, MAX_VALUE = 101;
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
        //        if (module == null) {
        //            log.add("Must select a gear");
        //            return;
        //        }
        //
        //        if (glows.length != 1) {
        //            log.add("Must select exactly 1 glows");
        //            return;
        //        }
        //
        //        if (module.getNumProperties() != 0) {
        //            log.add("Item must have no properties");
        //            return;
        //        }
        //
        //        if (!this.glows.consume(glows, GLOW_COST_BASE)) {
        //            log.add("Requires " + GLOW_COST_BASE + " of each glow selected");
        //            return;
        //        }
        //
        //        Glows.Glow glow = glows[0];
        //
        //        if (glow.source.length == 2) { // hybrid
        //            int index = MathRandom.random(0, 2);
        //            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[index]);
        //            int value = MathRandom.random(MIN_VALUE, MAX_VALUE + BASE_MAX_VALUE_BOOST);
        //            gear.addProperty(new Property(propertyType, value));
        //
        //        } else if (glow.tier == 1) { // tier 1
        //            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
        //            int value = MathRandom.random(MIN_VALUE, MAX_VALUE);
        //            gear.addProperty(new Property(propertyType, value));
        //
        //        } else { // tier 2
        //            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
        //            int value = MathRandom.random(MIN_VALUE, MAX_VALUE + BASE_MAX_VALUE_BOOST);
        //            gear.addProperty(new Property(propertyType, value));
        //        }

        // [10-100] for tier 1
        // [10-100] + 50 for tier 2
        // [10-100] + 50 for hybrid, randomly selected 
    }

    public void resetBase() {
        //        if (module == null) {
        //            log.add("Must select a module");
        //            return;
        //        }
        //
        //        if (module.getNumProperties() == 0) {
        //            log.add("Item must have at least 1 property");
        //            return;
        //        }
        //
        //        module.removeProperties(0);
        //        module.decreaseEnchantability(ENCHANTABILITY_PENALTY_BASE_RESET);
    }

    private boolean hasDuplicateGlowSource(Glows.Glow[] glows, Crafting.Source prevPropertySource) {
        for (Glows.Glow glow : glows)
            if (glow.source.length == 1 && glow.source[0] == prevPropertySource)
                return true;
        return false;
    }

    private Crafting.Source getNonDuplicateGlowSourceForHybridGlow(Glows.Glow glow, Crafting.Source prevPropertySource) {
        if (glow.source[0] == prevPropertySource)
            return glow.source[1];
        else if (glow.source[1] == prevPropertySource)
            return glow.source[0];
        else
            return glow.source[MathRandom.random(0, 2)];
    }

    public Module getModule() {
        return module;
    }
}