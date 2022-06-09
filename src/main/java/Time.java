import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static long unixTime;
    public SimpleDateFormat sdf;
    private Date date;

    public String time_now() {

        unixTime = System.currentTimeMillis() / 1000L + 10800; //Определяем текущее время
        date = new Date(unixTime * 1000L);
        sdf = new SimpleDateFormat("yyyy-MM");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));

        return sdf.format(date);  //sdf.format(date) "2022-06"

    }
}
