package character;

import character.gear.Gear;
import character.gear.Property;
import item.Item;
import util.MathRandom;

public class Crafting {
    public enum Source {
        EARTH("earth"), FIRE("fire"), WATER("water"), AIR("air");

        final String name;
        public final int value;

        Source(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final int MIN_VALUE = 10, MAX_VALUE = 101;
    private static final int BASE_MAX_VALUE_BOOST = 50;
    private static final float PRIMARY_MAX_MULT = .5f;
    private static final float SECONDARY_ADDITIONAL_GLOW_MAX_MULT = .1f;
    private static final int ENCHANTABILITY_PENALTY_BASE_RESET = 5, ENCHANTABILITY_PENALTY_PRIMARY_RESET = 5,
            ENCHANTABILITY_PENALTY_SECONDARY_RESET = 10, ENCHANTABILITY_PENALTY_ENHANCE_RESET = 10;
    private static final int GLOW_COST_BASE = 1, GLOW_COST_PRIMARY = 5, GLOW_COST_SECONDARY = 5;

    private int inventorySelect;
    private Gear gear;
    private Log log;
    private Inventory inventory;
    private Glows glows;

    Crafting(Log log, Inventory inventory, Glows glows) {
        this.log = log;
        this.inventory = inventory;
        this.glows = glows;
    }

    public void selectInventoryGear(int delta) {
        int start = inventorySelect;
        Item item;
        do {
            inventorySelect += delta;
            if (inventorySelect > inventory.getSize())
                inventorySelect -= inventory.getSize();
            else if (inventorySelect < 0)
                inventorySelect += inventory.getSize();

            item = inventory.getItem(inventorySelect);

            if (item != null && Gear.isGear(item.id) && !Gear.isModule(item.id)) {
                gear = (Gear) item;
                return;
            }
        } while (inventorySelect != start);
    }

    public void craftBase(Glows.Glow[] glows) {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (glows.length != 1) {
            log.add("Must select exactly 1 glows");
            return;
        }

        if (gear.getNumProperties() != 0) {
            log.add("Item must have no properties");
            return;
        }

        if (!this.glows.consume(glows, GLOW_COST_BASE)) {
            log.add("Requires " + GLOW_COST_BASE + " of each glow selected");
            return;
        }

        Glows.Glow glow = glows[0];

        if (glow.source.length == 2) { // hybrid
            int index = MathRandom.random(0, 2);
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[index]);
            int value = MathRandom.random(MIN_VALUE, MAX_VALUE + BASE_MAX_VALUE_BOOST);
            gear.addProperty(new Property(propertyType, value));

        } else if (glow.tier == 1) { // tier 1
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
            int value = MathRandom.random(MIN_VALUE, MAX_VALUE);
            gear.addProperty(new Property(propertyType, value));

        } else { // tier 2
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
            int value = MathRandom.random(MIN_VALUE, MAX_VALUE + BASE_MAX_VALUE_BOOST);
            gear.addProperty(new Property(propertyType, value));
        }

        // [10-100] for tier 1
        // [10-100] + 50 for tier 2
        // [10-100] + 50 for hybrid, randomly selected 
    }

    public void craftPrimary(Glows.Glow[] glows) {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (glows.length != 3) {
            log.add("Must select exactly 3 glows");
            return;
        }

        if (gear.getNumProperties() != 1 && gear.getNumProperties() != 2) {
            log.add("Item must have 1 or 2 properties");
            return;
        }

        Source prevPropertySource = gear.getPropertySource(1);
        if (gear.getNumProperties() == 2 && hasDuplicateGlowSource(glows, prevPropertySource)) {
            log.add("Item already has primary property of source " + prevPropertySource.name);
            return;
        }

        if (!this.glows.consume(glows, GLOW_COST_PRIMARY)) {
            log.add("Requires " + GLOW_COST_PRIMARY + " of each glow selected");
            return;
        }

        Glows.Glow glow = glows[MathRandom.random(0, glows.length)];

        if (glow.source.length == 2) { // hybrid
            Source source = getNonDuplicateGlowSourceForHybridGlow(glow, prevPropertySource);
            Property.PropertyType propertyType = gear.getPrimaryProperty(source);
            int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * PRIMARY_MAX_MULT));
            gear.addProperty(new Property(propertyType, value));

        } else if (glow.tier == 1) { // tier 1
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
            int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * PRIMARY_MAX_MULT));
            gear.addProperty(new Property(propertyType, value));

        } else { // tier 2
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[0]);
            int value = MathRandom.random(MIN_VALUE, MAX_VALUE);
            gear.addProperty(new Property(propertyType, value));
        }

        // randomly select 1 glow
        // [10-(100*.5)] for tier 1
        // [10-100] for tier 2
        // [10-(100*.5)] for hybrid, randomly selected (excluding property[1])
    }

    public void craftSecondary(Glows.Glow[] glows) {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (glows.length == 0) {
            log.add("Must select at least 1 glow");
            return;
        }

        if (gear.getNumProperties() != 3 && gear.getNumProperties() != 4) {
            log.add("Item must have 3 or 4 properties");
            return;
        }

        Source prevPropertySource = gear.getPropertySource(3);
        if (gear.getNumProperties() == 4 && hasDuplicateGlowSource(glows, prevPropertySource)) {
            log.add("Item already has secondary property of source " + prevPropertySource.name);
            return;
        }

        if (!this.glows.consume(glows, GLOW_COST_SECONDARY)) {
            log.add("Requires " + GLOW_COST_SECONDARY + " of each glow selected");
            return;
        }

        Glows.Glow glow = glows[MathRandom.random(0, glows.length)];
        float multiply = (1 + (glows.length - 1) * SECONDARY_ADDITIONAL_GLOW_MAX_MULT) * gear.getEnchantability() / 100f;

        if (glow.source.length == 2) { // hybrid
            Source source = getNonDuplicateGlowSourceForHybridGlow(glow, prevPropertySource);
            Property.PropertyType propertyType = gear.getSecondaryProperty(source);
            int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * multiply));
            gear.addProperty(new Property(propertyType, value));

        } else { // tier 1 or tier 2
            Property.PropertyType propertyType = gear.getSecondaryProperty(glow.source[0]);
            int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * multiply));
            gear.addProperty(new Property(propertyType, value));
        }

        // randomly select 1 glow
        // multiply = (1 + ((# glows selected - 1) * .1)) * (enchantability/100)
        // [10-(100*multiply)] for tier 1 or tier 2
        // [10-(100*multiply)] for hybrid, randomly selected (excluding property[3])
    }

    public void craftEnhance() {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (gear.getNumProperties() != 5 && gear.getNumProperties() != 6) {
            log.add("Item must have 5 or 6 properties");
            return;
        }

        Property.PropertyType propertyType = gear.getRandomSecondaryProperty();
        float multiply = gear.getEnchantability() / 100f;
        int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * multiply));
        gear.addProperty(new Property(propertyType, value));

        // add property
        // randomly select of 4 glow types
        // [10-(100*enchantability/100)]
    }

    public void resetBase() {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (gear.getNumProperties() != 1) {
            log.add("Item must have exactly 1 property");
            return;
        }

        gear.removeProperties(0);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_BASE_RESET);
    }

    public void resetPrimary() {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (gear.getNumProperties() != 2 && gear.getNumProperties() != 3) {
            log.add("Item must have 2 or 3 properties");
            return;
        }

        gear.removeProperties(1);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_PRIMARY_RESET);
    }

    public void resetSecondary() {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (gear.getNumProperties() != 4 && gear.getNumProperties() != 5) {
            log.add("Item must have 4 or 5 properties");
            return;
        }

        gear.removeProperties(3);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_SECONDARY_RESET);
    }

    public void resetEnhance() {
        if (gear == null) {
            log.add("Must select a gear");
            return;
        }

        if (gear.getNumProperties() != 6 && gear.getNumProperties() != 7) {
            log.add("Item must have 6 or 7 properties");
            return;
        }

        gear.removeProperties(5);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_ENHANCE_RESET);
    }

    private boolean hasDuplicateGlowSource(Glows.Glow[] glows, Source prevPropertySource) {
        for (Glows.Glow glow : glows)
            if (glow.source.length == 1 && glow.source[0] == prevPropertySource)
                return true;
        return false;
    }

    private Source getNonDuplicateGlowSourceForHybridGlow(Glows.Glow glow, Source prevPropertySource) {
        if (glow.source[0] == prevPropertySource)
            return glow.source[1];
        else if (glow.source[1] == prevPropertySource)
            return glow.source[0];
        else
            return glow.source[MathRandom.random(0, 2)];
    }

    public Gear getGear() {
        return gear;
    }
}