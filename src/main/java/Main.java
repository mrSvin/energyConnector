import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        VRS vrs = new VRS();
        Time time = new Time();
        Mysql mysql = new Mysql();
        OwencloudApi owencloudApi = new OwencloudApi();

        long i = 0;

        while (true) {

            mysql.createTable(time.time_now());

            String token = owencloudApi.api_owencloud_token();
            vrs.vrs1(time.time_now(), token);
            vrs.vrs2(time.time_now(), token);

            System.out.println("Цикл прошел!" + i++);
            Thread.sleep(1000 * 3600);
        }

    }

}
