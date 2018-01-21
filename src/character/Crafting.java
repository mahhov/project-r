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

    public void craftBase(boolean[] glowsSelected) {
        System.out.println("craftBase");
    }

    public void craftPrimary(boolean[] glowsSelected) {
        System.out.println("craftPrimary");
    }

    public void craftSecondary(boolean[] glowsSelected) {
        System.out.println("craftSecondary");
    }

    public void craftEnhance(boolean[] glowsSelected) {
        System.out.println("craftEnhance");
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