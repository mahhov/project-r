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

    Crafting() {
        gear = new Helmet();
        gear.addProperty(new Property(Property.PropertyType.HEALTH_LIFE, 39));
    }

    public String getText() {
        return gear != null ? gear.getText() : "--Select Gear to Craft--";
    }

    public String getText(int property) {
        return gear.getText(property);
    }
}