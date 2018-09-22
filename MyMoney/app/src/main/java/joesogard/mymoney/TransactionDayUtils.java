package joesogard.mymoney;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionDayUtils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");

    public static void setStartOfDay(Calendar day){
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 1);
    }

    public static void setEndOfDay(Calendar day){
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
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

    public static List<Calendar> getDayRange(){
        return getDayRange(getFirstOfMonth(), getToday());
    }
}
