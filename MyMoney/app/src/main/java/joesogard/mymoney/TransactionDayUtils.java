package joesogard.mymoney;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionDayUtils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");

    public static Calendar setStartOfDay(Calendar day){
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        return day;
    }

    public static Calendar setEndOfDay(Calendar day){
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        return day;
    }

    public static Calendar getToday(){
        Calendar calendar = Calendar.getInstance();
        setEndOfDay(calendar);
        return calendar;
    }

    public static Calendar getFirstOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        setStartOfDay(calendar);
        return calendar;
    }

    public static List<Calendar> getDayRange(Calendar start, Calendar end){
        List<Calendar> daysRange = new ArrayList<>();
        Calendar day = (Calendar)start.clone();
        while(day.before(end)){
            daysRange.add((Calendar)day.clone());
            day.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysRange;
    }

    public static boolean isOnSameDay(Calendar day1, Calendar day2){
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR) &&
                day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH) &&
                day1.get(Calendar.DAY_OF_MONTH) == day2.get(Calendar.DAY_OF_MONTH);
    }

    public static List<Calendar> getDayRange(){
        return getDayRange(getFirstOfMonth(), getToday());
    }
}
