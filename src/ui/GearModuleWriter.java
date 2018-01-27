package ui;

import character.gear.Module;

class GearModuleWriter extends GearWriter { // todo use module gear writer in inventory and equipment for modules too
    GearModuleWriter(UiPane pane, int offset, String nullText) {
        super(pane, offset, nullText);
    }

    String getDetails() {
        return super.getDetails() + "; Weight: " + ((Module) getGear()).getWeight(); // todo write maximum weight limit in ui equipment
    }
}