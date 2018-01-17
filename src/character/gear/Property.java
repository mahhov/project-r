package character.gear;

import character.Equipment;

class Property {
    private Equipment.PropertyType propertyType;
    private int value;

    Property(Equipment.PropertyType propertyType, int value) {
        this.propertyType = propertyType;
        this.value = value;
    }

    boolean isOfType(Equipment.PropertyType propertyType) {
        return this.propertyType == propertyType;
    }

    int value() {
        return value;
    }
}