package character;

import util.MathRandom;

public class Glows {
    public enum Glow {
        EARTH("Earth"), EARTH2("Earth II"),
        FIRE("Fire"), FIRE2("Fire II"),
        WATER("Water"), WATER2("Water II"),
        AIR("Air"), AIR2("Air II"),
        EARTH_FIRE("Earth-Fire"), WATER_AIR("Water-Air"),
        EARTH_WATER("Earth-Water"),
        FIRE_AIR("Fire-Air"),
        WATER_FIRE("Water-Fire"),
        AIR_EARTH("Air-Earth");

        final String name;
        final int value;

        Glow(String name) {
            this.name = name;
            this.value = ordinal();
        }
    }

    private static final Glow[] GLOW_VALUES = Glow.values();

    private int[] glows;

    Glows() {
        glows = new int[getGlowCount()];

        // todo remove
        for (int i = 0; i < glows.length; i++)
            glows[i] = MathRandom.random(10, 100);
    }

    public String getText(int i) {
        return GLOW_VALUES[i].name + " " + glows[i];
    }

    public static int getGlowCount() {
        return GLOW_VALUES.length;
    }

    public static Glow getGlow(int i) {
        return GLOW_VALUES[i];
    }
}