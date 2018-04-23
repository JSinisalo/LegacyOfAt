package com.hert.legacyofat.misc;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Class for a few debug functions.
 */
public class Debug {

    private static boolean debug = true;
    private static final String TAG = "LegacyOfAt";

    /**
     * Logs the message and automatically tries to get the caller of the log.
     *
     * @param msg the msg
     */
    public static void log(Object msg) {

        if(debug)
            Log.d(TAG + "/" + getCaller(), msg + "");
    }

    private static String getCaller() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Debug.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                if (callerClassName==null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste.getClassName() + "/" + ste.getMethodName();
                }
            }
        }
        return null;
    }

    /**
     * Logs the memory heap.
     */
    public static void logHeap() {
        Double allocated = new Double(android.os.Debug.getNativeHeapAllocatedSize())/new Double((1048576));
        Double available = new Double(android.os.Debug.getNativeHeapSize())/1048576.0;
        Double free = new Double(android.os.Debug.getNativeHeapFreeSize())/1048576.0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Log.d("tag", "debug. =================================");
        Log.d("tag", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
        Log.d("tag", "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
    }
}
