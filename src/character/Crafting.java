package character;

import character.gear.Gear;
import character.gear.Helmet;
import character.gear.Property;

public class Crafting {
    public enum Source {
        EARTH("Earth"), FIRE("Fire"), WATER("Water"), AIR("Air");

        final String name;
        final int value;

        Source(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private Gear gear;
    private Glows glows;

    Crafting(Glows glows) {
        gear = new Helmet();
        gear.addProperty(new Property(Property.PropertyType.HEALTH_LIFE, 39));
        this.glows = glows;
    }

    public void craftBase(Glows.Glow glow) {
        // check item has 0 properties

        // check exactly 1 glow selected

        // add property
        // [10-100] for tier 1
        // [10-100] + 50 for tier 2
        // [10-100] + 50 for hybrid, randomly selected 
    }

    public void craftPrimary(Glows.Glow glow1, Glows.Glow glow2, Glows.Glow glow3) {
        // check item has 1 or 2 properties

        // check exactly 3 glows selected

        // if has 2 property
        // check non of tier 1 and 2 selected glows are of same source as property[1]

        // add property
        // randomly select 1 glow
        // [10-(100*.5)] for tier 1
        // [10-100] for tier 2
        // [10-(100*.5)] for hybrid, randomly selected (excluding property[1])
    }

    public void craftSecondary(Glows.Glow[] glows) {
        // check item has 3 or 4 properties

        // check at least 1 glow selected

        // if has 4 properties
        // check non of tier 1 and 2 selected glows are of same source as property[3]

        // add property
        // randomly select 1 glow
        // multiply = 1 + ((# glows selected - 1) * .1)
        // [10-(100*multiply*enchantability/100)] for tier 1 or tier 2
        // [10-(100*multiply*enchantability/100)] for hybrid, randomly selected (excluding property[3])
    }

    public void craftEnhance() {
        // check item has 5 or 6 properties

        // add property
        // randomly select of 4 glow types
        // [10-(100*enchantability/100)]
    }

    public void resetBase() {
        System.out.println("resetBase");
    }

    public void resetPrimary() {
        System.out.println("resetPrimary");
    }

    public void resetSecondary() {
        System.out.println("resetSecondary");
    }

    public void resetEnhance() {
        System.out.println("resetEnhance");
    }

    public String getText() {
        return gear != null ? gear.getText() : "--Select Gear to Craft--";
    }

    public String getText(int property) {
        return gear.getText(property);
    }
}