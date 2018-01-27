package ui;

import item.gear.Gear;
import item.gear.Module;

class GearWriter {
    static final int SIZE = Gear.GEAR_MAX_PROPERTIES + 2;
    private UiPane pane;
    private int offset;
    private String nullText;
    private Gear gear;

    GearWriter(UiPane pane, int offset, String nullText) {
        this.pane = pane;
        this.offset = offset;
        this.nullText = nullText;
        refreshText();
    }

    void setGear(Gear gear) {
        this.gear = gear;
        refreshText();
    }

    void refreshText() {
        if (gear == null) {
            pane.setText(offset, nullText);
            for (int i = offset + 1; i < Gear.GEAR_MAX_PROPERTIES + offset + 2; i++)
                pane.setText(i, "");

        } else {
            pane.setText(offset, gear.getText());
            pane.setText(offset + 1, getDetails());
            for (int i = 0; i < Gear.GEAR_MAX_PROPERTIES; i++)
                pane.setText(offset + 2 + i, gear.getPropertyText(i));
        }
    }

    private String getDetails() {
        if (Gear.isModule(gear.id))
            return "Enchantability: " + gear.getEnchantability() + "; Weight: " + ((Module) gear).getWeight();
        else
            return "Enchantability: " + gear.getEnchantability();
    }
}