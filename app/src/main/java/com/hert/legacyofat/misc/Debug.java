package com.hert.legacyofat.misc;

import android.util.Log;

/**
 * Created by juhos on 17.3.2018.
 */

public class Debug {

    private static boolean debug = true;
    private static final String TAG = "LegacyOfAt";

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

    public static String getHorribleString() {
        return "߶ H͊ͧ̿̄ò̻̗̟̮̳̆̐͆̿̒ͩṛ̴͕͎͓ͬͬ̉̈̑r̛̘̳͆͋̈ͦͦ̉ͯȋ̮̜͇̩̘͒͛̇ͯ̿̋b͇̰̣͍͉̘͊ͣͯ̂l̔e̖̮͍̹̻ͯ͗̐͊̎̐́ ̥̟͂ͨ̃S̢͈̟̱͕̭̯ͅṫ̈͒҉̥͔̱̻ͅr̹͛ĩ̩̻̖̟̱̮͎͆̈ͥ̊́͠n͔͔̿̀́̊̀gͪ ̂ͮ͋¯̙͍̱̣̗̳̙̤̆̌̓ͦͬ̽̂͘_́(̖̫̏ͬ̄̐ͧ͆ͨ͘ツ̺̺̥̈̊̍̐ͭ͜)̭̑͗͑_̳̙̤͙̬͑͋͢ͅ/͙̫̥̰ͪ̿ͣ̚͘¯̙͚̱͌̂ͥͫ̅̈̇͞ ͬ̎̀̃͡�̱̰͚̄ͧ̏̒ͯ̈́̅͠�ͣ̆ͪͯ̈́̔߶ௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌௌ";
    }
}
