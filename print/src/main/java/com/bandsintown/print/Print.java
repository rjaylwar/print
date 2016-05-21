package com.bandsintown.print;
import android.util.Log;

public class Print {

    public static boolean mIsDebugMode = true;

    private static String mTag = "Print";

    private static CrashReporter mCrashReporter = null;

    public static void init(String appTag, boolean isDebugMode, @Nullable CrashReporter crashReporter) {
        mIsDebugMode = isDebugMode;
        mCrashReporter = crashReporter;

        mTag = "(" + appTag + ")";
    }

    /**
     * Used for logging whatever in log cat.
     *
     * @param message - log message
     */
    public static void log(Object message) {
        if(mIsDebugMode) {
            if(message == null || message.toString() == null)
                Log.d(mTag, "null");
            else
                Log.d(mTag, message.toString());
        }
    }

    /**
     * Used for logging whatever in log cat.
     *
     * @param tag - log tag
     * @param messages - log messages, separated by commas
     */
    public static void log(String tag, Object... messages) {
        if(mIsDebugMode) {
            String message;
            if(messages == null || messages.length == 0)
                message = "null";
            else {
                message = "";
                for(int i = 0; i < messages.length; i++) {
                    if(messages[i] != null)
                        message += messages[i].toString();
                    else
                        message += null;

                    if(i != messages.length - 1)
                        message += ", ";
                }
            }

            //this is so log cat can be filtered by bit tag
            try {
                Log.d(mTag + " " + tag, message);
            }
            catch(Exception e) {
                //you can't log in unit tests so this stops it from throwing an exception
            }
        }
    }

    public static void exception(Exception e, String message) {
        log(mTag + " Exception", message);
        exception(e, true);
    }


    public static void exception(Exception e) {
        exception(e, true);
    }

    public static void exception(Exception e, boolean report) {
        if(mIsDebugMode)
            e.printStackTrace();
        else if(report && mCrashReporter != null)
            mCrashReporter.logException(e);
    }

    public static void logScreen(String string) {
        if(string != null) {
            if(mIsDebugMode)
                log("Class", string);
            else if(mCrashReporter != null)
                mCrashReporter.log(string);
        }
    }

    public interface CrashReporter {
        void log(String message);
        void logException(Exception e);
    }

}
