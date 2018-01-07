package world.generator.simplex;

import java.util.Random;

public class SimplexNoiseHelper {
    private SimplexNoise[] octaves;
    private double[] frequencys;
    private double[] amplitudes;
    private int largestFeature;
    private double persistence;
    private int seed;

    public SimplexNoiseHelper(int largestFeature, double persistence, int seed) {
        this.largestFeature = largestFeature;
        this.persistence = persistence;
        this.seed = seed;
        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves = (int) Math.ceil(Math.log10(largestFeature) / Math.log10(2));
        octaves = new SimplexNoise[numberOfOctaves];
        frequencys = new double[numberOfOctaves];
        amplitudes = new double[numberOfOctaves];
        Random rnd = new Random(seed);
        for (int i = 0; i < numberOfOctaves; i++) {
            octaves[i] = new SimplexNoise(rnd.nextInt());
            frequencys[i] = Math.pow(2, i);
            amplitudes[i] = Math.pow(persistence, octaves.length - i);
        }
    }

    public double getNoise(int x, int y) {
        double result = 0;
        for (int i = 0; i < octaves.length; i++) {
            //double frequency = Math.pow(2,i);
            //double amplitude = Math.pow(persistence,octaves.length-i);
            result = result + octaves[i].noise(x / frequencys[i], y / frequencys[i]) * amplitudes[i];
        }
        return result;
    }

    public double getNoise(int x, int y, int z) {
        double result = 0;
        for (int i = 0; i < octaves.length; i++) {
            double frequency = Math.pow(2, i);
            double amplitude = Math.pow(persistence, octaves.length - i);
            result = result + octaves[i].noise(x / frequency, y / frequency, z / frequency) * amplitude;
        }
        return result;
    }
}