package by.nepravsky.sm.domain.utils;

import android.text.TextUtils;

public class NumberValidator {

    public static boolean isDouble(String string){

        if (TextUtils.isEmpty(string)){
            return false;
        }

        try {
            double d = Double.parseDouble(string);
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }

        return true;
    }
}
