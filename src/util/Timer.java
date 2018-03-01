package util;

public class Timer {
    private static final int COUNT = 10;
    private static final long NANOSECONDS_IN__MILLISECOND = 1000000L;
    private static long[] beginTime = new long[COUNT], totalTime = new long[COUNT];

    public static void restart(int i) {
        beginTime[i] = System.nanoTime();
    }

    public static long time(int i) {
        return time(i, null, 0);
    }

    public static long time(int i, String message) {
        return time(i, message, 0);
    }

    public static long time(int i, String message, long minTimeMs) {
        long endTime = System.nanoTime();
        long difTime = endTime - beginTime[i];
        totalTime[i] += difTime;
        if (message != null && difTime / NANOSECONDS_IN__MILLISECOND >= minTimeMs)
            printTime(message, difTime);
        beginTime[i] = System.nanoTime();
        return difTime;
    }

    public static long totalTime(int i, String message) {
        if (message != null)
            printTime(message, totalTime[i]);
        return totalTime[i];
    }

    public static void printTime(String message, long time) {
        String displayTime = time < 4 * NANOSECONDS_IN__MILLISECOND ? (int) (1000 * time / NANOSECONDS_IN__MILLISECOND) / 1000. + "ms" : time / NANOSECONDS_IN__MILLISECOND + "ms";
        System.out.println(message + " (" + displayTime + ")");
    }
}