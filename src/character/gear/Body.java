package character.gear;

public class Body extends Gear {
    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[] {};
    private static final Property.PropertyType[] SECONDARY_PROPERTIES = new Property.PropertyType[] {};
    public static final int ID = 3;

    public Body() {
        super(ID, PRIMARY_PROPERTIES, SECONDARY_PROPERTIES);
    }
}