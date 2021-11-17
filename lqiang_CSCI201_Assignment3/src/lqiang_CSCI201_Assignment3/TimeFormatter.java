//package PA3.util;
package lqiang_CSCI201_Assignment3;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    private static final long startTime = System.currentTimeMillis();

    public static String getTimestamp() {
//        return new SimpleDateFormat("mm:ss.SSS").format(new Date()).substring(0, 11);
    	long current=System.currentTimeMillis();
    	final long minutes = TimeUnit.MILLISECONDS.toMinutes(current)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(current));

		final long seconds = TimeUnit.MILLISECONDS.toSeconds(current)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current));

		final long ms = TimeUnit.MILLISECONDS.toMillis(current)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(current));

		return "["+String.format("%02d:%02d:%03d", minutes, seconds, ms)+"]";
    }

    public static String getTimestampDiff() {
        long duration = System.currentTimeMillis() - startTime;
        return new SimpleDateFormat("HH:mm:ss.SS").format(duration).substring(0, 11);
    }

}
