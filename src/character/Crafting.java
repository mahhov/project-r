package character;

import character.gear.Gear;
import character.gear.Helmet;
import character.gear.Property;
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

    private static final int MIN_VALUE = 10, MAX_VALUE = 30; // 101
    private static final int BASE_MAX_VALUE_BOOST = 50;
    private static final float PRIMARY_MAX_MULT = .5f;

    private static final int ENCHANTABILITY_PENALTY_BASE_RESET = 5, ENCHANTABILITY_PENALTY_PRIMARY_RESET = 5;

    private static final float SOURCE_LEVEL_VALUE_BONUS = 10;
    private static final float PRIMARY_ENCHANT_SECOND_TIER_VALUE_BONUS = .5f;
    private static final float SECONDARY_ENCHANT_ADDITIONAL_GLOW_VALUE_BONUS = .1f;
    private static final float ENCHANT_VALUE_MULTIPLIER = 1;

    private Gear gear;
    private Glows glows;

    Crafting(Glows glows) {
        gear = new Helmet();
        this.glows = glows;
    }

    public void craftBase(Glows.Glow[] glows) {
        if (glows.length != 1)
            return;

        if (gear.getNumProperties() != 0) // todo error messages for wrong # prop, wrong # glow selected, duplicate glow selected
            return;

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
        if (glows.length != 3)
            return;

        Source prevPropertySource = gear.getPropertySource(1);
        if (gear.getNumProperties() == 2) {
            for (Glows.Glow glow : glows)
                if (glow.source.length == 1 && glow.source[0] == prevPropertySource)
                    return;
        } else if (gear.getNumProperties() != 1)
            return;

        Glows.Glow glow = glows[MathRandom.random(0, glows.length)];

        if (glow.source.length == 2) { // hybrid
            int index = glow.source[0] == prevPropertySource ? 1 : (glow.source[1] == prevPropertySource ? 0 : MathRandom.random(0, 2));
            Property.PropertyType propertyType = gear.getPrimaryProperty(glow.source[index]);
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
        if (gear.getNumProperties() != 3 && gear.getNumProperties() != 4)
            return;

        // if has 4 properties
        // check non of tier 1 and 2 selected glows are of same source as property[3]

        // add property
        // randomly select 1 glow
        // multiply = 1 + ((# glows selected - 1) * .1)
        // [10-(100*multiply*enchantability/100)] for tier 1 or tier 2
        // [10-(100*multiply*enchantability/100)] for hybrid, randomly selected (excluding property[3])
    }

    public void craftEnhance() {
        if (gear.getNumProperties() != 5 && gear.getNumProperties() != 6)
            return;

        // add property
        // randomly select of 4 glow types
        // [10-(100*enchantability/100)]
    }

    public void resetBase() {
        if (gear.getNumProperties() != 1)
            return;

        gear.removeProperties(0);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_BASE_RESET);
    }

    public void resetPrimary() {
        if (gear.getNumProperties() != 2 && gear.getNumProperties() != 3)
            return;

        gear.removeProperties(1);
        gear.decreaseEnchantability(ENCHANTABILITY_PENALTY_PRIMARY_RESET);
    }

    public void resetSecondary() {
        System.out.println("resetSecondary");
    }

    public void resetEnhance() {
        System.out.println("resetEnhance");
    }

    public String getGearText() {
        return gear != null ? gear.getText() : "--Select Gear to Craft--";
    }

    public String getEnchantabilityText() {
        return gear != null ? "Enchantability: " + gear.getEnchantabilityText() : "";
    }

    public String getPropertyText(int property) {
        return gear != null ? gear.getPropertyText(property) : "";
    }
}