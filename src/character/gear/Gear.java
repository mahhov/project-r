package character.gear;

import character.Crafting;
import item.Item;
import util.MathNumbers;
import util.MathRandom;

public class Gear extends Item {
    public static final int GEAR_MAX_PROPERTIES = 7, MIN_ENCHANTABILITY = 10;

    private Property.PropertyType[] primaryProperties, secondaryProperties;

    private int enchantability;
    private Property[] properties;
    private int numProperties;

    Gear(int id, Property.PropertyType[] primaryProperties, Property.PropertyType[] secondaryProperties) {
        super(id, "", false);
        this.primaryProperties = primaryProperties;
        this.secondaryProperties = secondaryProperties;
        enchantability = 100;
        properties = new Property[GEAR_MAX_PROPERTIES];
    }

    public void addProperty(Property property) {
        properties[numProperties++] = property;
    }

    public void removeProperties(int keep) {
        numProperties = keep;
    }

    public Crafting.Source getPropertySource(int index) {
        return index < numProperties ? properties[index].propertyType.source : null;
    }

    public void decreaseEnchantability(int amount) {
        enchantability = MathNumbers.max(enchantability - amount, MIN_ENCHANTABILITY);
    }

    public int getEnchantability() {
        return enchantability;
    }

    public int getEquipmentBonus(Property.PropertyType propertyType) {
        int sum = 0;
        for (int i = 0; i < numProperties; i++)
            if (properties[i].isOfType(propertyType))
                sum += properties[i].value();
        return sum;
    }

    public int getNumProperties() {
        return numProperties;
    }

    @Override
    public String getText() {
        return "juba"; // todo placeholder
    }

    public String getPropertyText(int property) {
        return property < numProperties ? properties[property].getText() : "";
    }

    public Property.PropertyType getPrimaryProperty(Crafting.Source source) {
        return primaryProperties[source.value];
    }

    public Property.PropertyType getSecondaryProperty(Crafting.Source source) {
        return secondaryProperties[source.value];
    }

    public Property.PropertyType getRandomSecondaryProperty() {
        return secondaryProperties[MathRandom.random(0, secondaryProperties.length)];
    }

    public static boolean isGear(int id) {
        return id == Helmet.ID || id == Glove.ID || id == Boot.ID || id == Body.ID;
    }
}