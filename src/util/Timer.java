package util;

public class Timer {
    private static final long NANOSECONDS_IN__MILLISECOND = 1000000L;
    private static long beginTime;

    public static void restart() {
        beginTime = System.nanoTime();
    }

    public static long time(String message) {
        long endTime = System.nanoTime();
        long difTime = endTime - beginTime;
        if (message != null) {
            String displayTime = difTime < 4 * NANOSECONDS_IN__MILLISECOND ? (int) (1000 * difTime / NANOSECONDS_IN__MILLISECOND) / 1000. + "ms" : difTime / NANOSECONDS_IN__MILLISECOND + "ms";
            System.out.println(message + " (" + displayTime + ")");
        }
        beginTime = System.nanoTime();
        return difTime;
    }
    
    public static long time(String message, long minTimeMs) {
        long endTime = System.nanoTime();
        long difTime = endTime - beginTime;
        if (message != null && difTime / NANOSECONDS_IN__MILLISECOND >= minTimeMs) {
            String displayTime = difTime < 4 * NANOSECONDS_IN__MILLISECOND ? (int) (1000 * difTime / NANOSECONDS_IN__MILLISECOND) / 1000. + "ms" : difTime / NANOSECONDS_IN__MILLISECOND + "ms";
            System.out.println(message + " (" + displayTime + ")");
        }
        beginTime = System.nanoTime();
        return difTime;
    }
}
