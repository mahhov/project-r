package character;

import util.MathRandom;

public class Glows {
    public enum Glow {
        EARTH("Earth", Crafting.Source.EARTH, 1), EARTH2("Earth II", Crafting.Source.EARTH, 2),
        FIRE("Fire", Crafting.Source.FIRE, 1), FIRE2("Fire II", Crafting.Source.FIRE, 2),
        WATER("Water", Crafting.Source.WATER, 1), WATER2("Water II", Crafting.Source.WATER, 2),
        AIR("Air", Crafting.Source.AIR, 1), AIR2("Air II", Crafting.Source.AIR, 2),
        EARTH_FIRE("Earth-Fire", Crafting.Source.EARTH, Crafting.Source.FIRE),
        WATER_AIR("Water-Air", Crafting.Source.WATER, Crafting.Source.AIR),
        EARTH_WATER("Earth-Water", Crafting.Source.EARTH, Crafting.Source.WATER),
        FIRE_AIR("Fire-Air", Crafting.Source.FIRE, Crafting.Source.AIR),
        WATER_FIRE("Water-Fire", Crafting.Source.WATER, Crafting.Source.FIRE),
        AIR_EARTH("Air-Earth", Crafting.Source.AIR, Crafting.Source.EARTH);

        final String name;
        final int value;
        final Crafting.Source[] source;
        final int tier;

        Glow(String name, Crafting.Source source, int tier) {
            this.name = name;
            this.value = ordinal();
            this.source = new Crafting.Source[] {source};
            this.tier = tier;
        }

        Glow(String name, Crafting.Source source1, Crafting.Source source2) {
            this.name = name;
            this.value = ordinal();
            source = new Crafting.Source[] {source1, source2};
            this.tier = 1;
        }
    }

    private static final Glow[] GLOW_VALUES = Glow.values();

    private int[] glows;

    Glows() {
        glows = new int[getGlowCount()];

        // todo remove
        for (int i = 0; i < glows.length; i++)
            glows[i] = MathRandom.random(0, 5);
    }

    void consume(Glow[] glows) {
        for (Glow glow : glows)
            this.glows[glow.value]--;
    }

    public boolean available(Glow glow) {
        return glows[glow.value] > 0;
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