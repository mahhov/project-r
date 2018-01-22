package character;

import character.gear.Gear;
import character.gear.Property;
import item.Item;
import util.MathRandom;

public class Crafting {
    public enum Source {
        EARTH("Earth"), FIRE("Fire"), WATER("Water"), AIR("Air");

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

    private int inventorySelect;
    private Gear gear;
    private Inventory inventory;
    private Glows glows;

    Crafting(Inventory inventory, Glows glows) {
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

            if (item != null && Gear.isGear(item.id)) {
                gear = (Gear) item;
                return;
            }
        } while (inventorySelect != start);
    }

    public String craftBase(Glows.Glow[] glows) {
        if (gear == null)
            return "Must select a gear";

        if (glows.length != 1)
            return "Must select exactly 1 glows";

        if (gear.getNumProperties() != 0)
            return "Item must have no properties";

        this.glows.consume(glows);

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

        return null;

        // [10-100] for tier 1
        // [10-100] + 50 for tier 2
        // [10-100] + 50 for hybrid, randomly selected 
    }

    public String craftPrimary(Glows.Glow[] glows) {
        if (gear == null)
            return "Must select a gear";

        if (glows.length != 3)
            return "Must select exactly 3 glows";

        Source prevPropertySource = gear.getPropertySource(1);
        if (gear.getNumProperties() != 1 && (gear.getNumProperties() != 2 || hasDuplicateGlowSource(glows, prevPropertySource)))
            return "Item must have 1 or 2 properties";

        this.glows.consume(glows);

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

        return null;

        // randomly select 1 glow
        // [10-(100*.5)] for tier 1
        // [10-100] for tier 2
        // [10-(100*.5)] for hybrid, randomly selected (excluding property[1])
    }

    public String craftSecondary(Glows.Glow[] glows) {
        if (gear == null)
            return "Must select a gear";

        if (glows.length == 0)
            return "Must select at least 1 glow";

        Source prevPropertySource = gear.getPropertySource(3);
        if (gear.getNumProperties() != 3 && (gear.getNumProperties() != 4 || hasDuplicateGlowSource(glows, prevPropertySource)))
            return "Item must have 3 or 4 properties";

        this.glows.consume(glows);

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

        return null;

        // randomly select 1 glow
        // multiply = (1 + ((# glows selected - 1) * .1)) * (enchantability/100)
        // [10-(100*multiply)] for tier 1 or tier 2
        // [10-(100*multiply)] for hybrid, randomly selected (excluding property[3])
    }

    public String craftEnhance() {
        if (gear == null)
            return "Must select a gear";

        if (gear.getNumProperties() != 5 && gear.getNumProperties() != 6)
            return "Item must have 5 or 6 properties";

        Property.PropertyType propertyType = gear.getRandomSecondaryProperty();
        float multiply = gear.getEnchantability() / 100f;
        int value = MathRandom.random(MIN_VALUE, (int) (MAX_VALUE * multiply));
        gear.addProperty(new Property(propertyType, value));

        return null;

        // add property
        // randomly select of 4 glow types
        // [10-(100*enchantability/100)]
    }

    public String resetBase() {
        if (gear == null)
            return "Must select a gear";

        if (gear.getNumProperties() != 1)
            return "Item must have exactly 1 property";

        gear.removeProperties(0);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_BASE_RESET);
        return null;
    }

    public String resetPrimary() {
        if (gear == null)
            return "Must select a gear";

        if (gear.getNumProperties() != 2 && gear.getNumProperties() != 3)
            return "Item must have 2 or 3 properties";

        gear.removeProperties(1);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_PRIMARY_RESET);
        return null;
    }

    public String resetSecondary() {
        if (gear == null)
            return "Must select a gear";

        if (gear.getNumProperties() != 4 && gear.getNumProperties() != 5)
            return "Item must have 4 or 5 properties";

        gear.removeProperties(3);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_SECONDARY_RESET);
        return null;
    }

    public String resetEnhance() {
        if (gear == null)
            return "Must select a gear";

        if (gear.getNumProperties() != 6 && gear.getNumProperties() != 7)
            return "Item must have 6 or 7 properties";

        gear.removeProperties(5);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_ENHANCE_RESET);
        return null;
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

    public String getGearText() {
        return gear != null ? gear.getText() : "--Select Gear to Craft--";
    }

    public String getEnchantabilityText() {
        return gear != null ? "Enchantability: " + gear.getEnchantability() : "";
    }

    public String getPropertyText(int property) {
        return gear != null ? gear.getPropertyText(property) : "";
    }
}