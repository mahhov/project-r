package character.gear;

public class Glove extends Gear {
    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[] {};
    private static final Property.PropertyType[] SECONDARY_PROPERTIES = new Property.PropertyType[] {};
    public static final int ID = 4;

    public Glove() {
        super(ID, PRIMARY_PROPERTIES, SECONDARY_PROPERTIES);
    }
}