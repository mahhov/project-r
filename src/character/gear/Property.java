package character.gear;

import character.Equipment;

public class Property {
    private Equipment.PropertyType propertyType;
    private int value;

    public Property(Equipment.PropertyType propertyType, int value) {
        this.propertyType = propertyType;
        this.value = value;
    }

    boolean isOfType(Equipment.PropertyType propertyType) {
        return this.propertyType == propertyType;
    }

    int value() {
        return value;
    }

    String getText() {
        return propertyType.name + " " + value;
    }
}