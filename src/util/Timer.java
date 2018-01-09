package util;

public class Timer {
    private static final int COUNT = 10;
    private static final long NANOSECONDS_IN__MILLISECOND = 1000000L;
    private static long beginTime[] = new long[COUNT];

    public static void restart(int i) {
        beginTime[i] = System.nanoTime();
    }

    public static long time(int i, String message) {
        long endTime = System.nanoTime();
        long difTime = endTime - beginTime[i];
        if (message != null)
            printTime(message, difTime);
        beginTime[i] = System.nanoTime();
        return difTime;
    }

    public static long time(int i, String message, long minTimeMs) {
        long endTime = System.nanoTime();
        long difTime = endTime - beginTime[i];
        if (message != null && difTime / NANOSECONDS_IN__MILLISECOND >= minTimeMs)
            printTime(message, difTime);
        beginTime[i] = System.nanoTime();
        return difTime;
    }

    public static void printTime(String message, long time) {
        String displayTime = time < 4 * NANOSECONDS_IN__MILLISECOND ? (int) (1000 * time / NANOSECONDS_IN__MILLISECOND) / 1000. + "ms" : time / NANOSECONDS_IN__MILLISECOND + "ms";
        System.out.println(message + " (" + displayTime + ")");
    }
}
