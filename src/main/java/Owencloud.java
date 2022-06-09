import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Owencloud {

    public String owencloudEnergyMonth(String date, Integer idSignal, Integer idDevice) {
        String result = null;

        try {
            String zapros = "https://web.owencloud.ru/device/graphics/" + idDevice + "?from_date=01-" + date + "&till_date=31-" + date + "&hs=00&ms=00&he=23&me=59&parameters=[" + idSignal + "]";
            Document doc = Jsoup.connect(zapros)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:92.0) Gecko/20100101 Firefox/92.0")
                    .referrer("https://web.owencloud.ru")
                    .cookie("_csrf", "d4cf6a4109446c6d09c5ecb03153713f00ab0a088dea8de0ebf63ebd1eb68235a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22KKxk9Aeqcv-7NAVvhme0RRd87edGphlw%22%3B%7D")
                    .cookie("_gat", "1")
                    .cookie("_ga", "GA1.2.870102505.1621942960")
                    .cookie("_gid", "GA1.2.1619310851.1633324910")
                    .cookie("language", "4bcc0098164ac448a4e38bf5cebcaf9f781cb9ca5720c1bf7557e5bc92121fe5a%3A2%3A%7Bi%3A0%3Bs%3A8%3A%22language%22%3Bi%3A1%3Bs%3A5%3A%22ru-RU%22%3B%7D")
                    .cookie("PHPSESSID", "d526ee9ac6feaf075f7b27290664cbe7")
                    .cookie("SERVERID", "penza")
                    .cookie("cookieconsent_status", "allow")
                    .cookie("atab_device-navbar", "params")
                    .cookie("_identity", "2ed594e115ae065be5412b649a163babee97efcc9cfd93815d2770bbb31f944ca%3A2%3A%7Bi%3A0%3Bs%3A9%3A%22_identity%22%3Bi%3A1%3Bs%3A50%3A%22%5B14629%2C%225bQ9AY1996skepFSCMYoLZuo3dReSBll%22%2C2592000%5D%22%3B%7D")
                    .get();
            result = String.valueOf(doc);
            int begin = result.indexOf("[[");
            int end = result.indexOf("]]") + 2;
            if (begin > -1) {
                result = result.substring(begin, end);
            }
//            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList convertOwenclowdToArray(String month, int idSignal, int idDevice) {
        String result = owencloudEnergyMonth(month, idSignal, idDevice);
        result = result.replace("[", "");
        result = result.replace("]", "");

        ArrayList<String> data = new ArrayList<>();
        while (result.indexOf(",") > 0) {
            data.add(result.substring(0, result.indexOf(",")));
            result = result.substring(result.indexOf(",") + 1, result.length());
        }
        data.add(result);

        return data;
    }

    public String[] rashodDaysSum(String month, Integer idSignal, Integer idDevice) {
        ArrayList<String> dataOwen = convertOwenclowdToArray(month, idSignal, idDevice);

        String[] data = new String[32];
        List<Integer> temp = new ArrayList<>();
        temp.add(0);

        for (int i = 0; i < data.length; i++) {
            data[i] = "0";
        }
        data[0] = dataOwen.get(1);

        int size = dataOwen.size();

        if (dataOwen.get(0).length() < 25) {
            for (int i = 0; i < size; i += 2) {

                SimpleDateFormat day = new SimpleDateFormat("dd");
                day.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));

                long dateTime = Long.parseLong(dataOwen.get(0));
                long dateTimeDay = Long.parseLong(dataOwen.get(i));
                long searchDay = dateTimeDay - dateTime;
                int dayInt = Integer.parseInt(day.format(searchDay));

                int maxDay = Collections.max(temp);
                if (dayInt >= maxDay) {
                    temp.add(dayInt);
//                    System.out.println(dayInt + " " + maxDay + " " + searchDay);
                    if (dataOwen.get(i + 1).equals("null") == false) {
                        data[dayInt] = dataOwen.get(i + 1);
                    }

                }

            }
        }

//        for (int i=0; i<dataOwen.size(); i++) {
//            System.out.println(i + " " + dataOwen.get(i));
//        }

        return data;
    }

}
