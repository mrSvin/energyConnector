import com.squareup.okhttp.*;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class OwencloudApi {

    private String token;
    private OkHttpClient client;
    private Request request;
    Response response;

    //Для получения токена
    public String api_owencloud_token() {
        client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"login\":\"alva@sespel.com\",\r\n\t\"password\":\"alex9231\"\r\n}");
        request = new Request.Builder()
                .url("https://api.owencloud.ru/v1/auth/open")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        response = null;
        try {
            response = client.newCall(request).execute();

            //System.out.println(response.body().string());
            JSONObject jsonResponse = null;

            jsonResponse = new JSONObject(response.body().string());
            token = jsonResponse.getString("token");
//            System.out.println("Полученный токен: " + token);
        } catch (IOException | NullPointerException | JSONException e) {
//            e.printStackTrace();
        }
        return token;

    }

    public Double parseRashodDay(String token, String signal, String date) {

        Double result = 0.0;

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"ids\":[" + signal + "],\"start\":\"" + date + " 00:00:00\",\"end\":\"" + date + " 23:59:59\",\"step\":100000}");

        client = new OkHttpClient();
        request = new Request.Builder()
                .url("https://api.owencloud.ru/v1/parameters/data")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        response = null;
        try {
            response = client.newCall(request).execute();

            //Получаяем всё сообщение запроса
            JSONArray jsonResponse = new JSONArray(response.body().string());

            //Считываем массив с параметрами оборудования
            JSONObject jsonParametr = jsonResponse.getJSONObject(0);

            JSONArray allValuesArray = jsonParametr.getJSONArray("values");

            JSONObject valuesArrayBegin = allValuesArray.getJSONObject(0);
            JSONObject valuesArrayEnd = allValuesArray.getJSONObject(allValuesArray.length()-1);

            Double valuesBegin = valuesArrayBegin.getDouble("v");
            Double valuesEnd = valuesArrayEnd.getDouble("v");

            result = Math.round((valuesEnd -valuesBegin) * 100) / 100.0;

            System.out.println(result);

            return result;

        } catch (IOException | JSONException e) {
            //e.printStackTrace();
            System.out.println("ошибка чтения!");

            System.out.println(result);
            return result;
        }


    }

}
