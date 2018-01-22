package ui;

import character.gear.Gear;

class GearWriter {
    static final int SIZE = Gear.GEAR_MAX_PROPERTIES + 2;
    private UiPane pane;
    private int offset;
    private int end;
    private String nullText;
    private Gear gear;

    GearWriter(UiPane pane, int offset, String nullText) {
        this.pane = pane;
        this.offset = offset;
        end = offset + SIZE;
        this.nullText = nullText;
    }

    void setGear(Gear gear) {
        this.gear = gear;
        refreshGear();
    }

    void refreshGear() {
        if (gear == null) {
            pane.setText(offset, nullText);
            for (int i = offset + 1; i < end; i++)
                pane.setText(i, "");

        } else {
            pane.setText(offset, gear.getText());
            pane.setText(offset + 1, "Enchantability: " + gear.getEnchantability());
            for (int i = offset + 2; i < end; i++)
                pane.setText(i, gear.getPropertyText(i));
        }
    }
}