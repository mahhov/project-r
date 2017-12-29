package util;

public class Timer {
    private static final long NANOSECONDS_IN__MILLISECOND = 1000000L;
    private static long beginTime;

    public static void restart() {
        beginTime = System.nanoTime();
    }

    public static void time(String message) {
        long endTime = System.nanoTime();
        long difTime = (endTime - beginTime) / NANOSECONDS_IN__MILLISECOND;
        System.out.println(message + " (" + difTime + "ms)");
    }
}
