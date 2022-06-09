import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VRS {

    Owencloud owencloud = new Owencloud();
    OwencloudApi owencloudApi = new OwencloudApi();
    Mysql mysql = new Mysql();

    public void vrs1(String date, String token) throws InterruptedException {

        List daysData = new ArrayList();

        for (int i=1;i<31; i++) {
            String dateRequsest = date + "-" + i;  //"2022-05-3"
            Double dayRashod = owencloudApi.parseRashodDay(token,"2952629",dateRequsest);
            daysData.add(dayRashod);
            Thread.sleep(1000);
        }
        mysql.writeData(daysData, date, "vrs1");

    }

    public void vrs2(String date, String token) throws InterruptedException {

        List daysData = new ArrayList();

        for (int i=1;i<31; i++) {
            String dateRequsest = date + "-" + i;  //"2022-05-3"
            Double dayRashod = owencloudApi.parseRashodDay(token,"11270048",dateRequsest);
            daysData.add(dayRashod);
            Thread.sleep(1000);
        }
        mysql.writeData(daysData, date, "vrs2");

    }


}

