package by.nepravsky.sm.domain.utils;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class TimeConverter {

    @Inject
    public TimeConverter() {
    }

    public String calculateDMHE(long seconds){

        long day, hour, min, sec;

        day = TimeUnit.SECONDS.toDays(seconds);
        hour = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(day);
        min = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.DAYS.toMinutes(day)
                - TimeUnit.HOURS.toMinutes(hour);
        sec = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));

        StringBuffer buffer = new StringBuffer();

        if (day > 0){
            buffer.append(day).append("d ");
        }

        if (hour > 0){
            buffer.append(hour).append("h ");
        }

        if (min > 0){
            buffer.append(min).append("m ");
        }
        if (sec > 0){
            buffer.append(sec).append("sec ");
        }

        return buffer.toString();
    }
}
