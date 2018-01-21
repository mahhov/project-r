package character.gear;

public class Boot extends Gear {
    private static final Property.PropertyType[] PRIMARY_PROPERTIES = new Property.PropertyType[] {};
    private static final Property.PropertyType[] SECONDARY_PROPERTIES = new Property.PropertyType[] {};
    public static final int ID = 5;

    public Boot() {
        super(ID, PRIMARY_PROPERTIES, SECONDARY_PROPERTIES);
    }
}