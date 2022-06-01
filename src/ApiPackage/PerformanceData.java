package ApiPackage;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

public class PerformanceData {

    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
    Calendar time = Calendar.getInstance();
    String currentDate = format1.format(time.getTime());
    Date dt;

    public String performanceData(String si, String gngu) throws IOException {

        try {
            dt = format1.parse(currentDate);
            time.setTime(dt);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        time.add(Calendar.MONTH, 1);
        String nextMonth = format1.format(time.getTime());

        StringBuilder urlBuilder = new StringBuilder("http://kopis.or.kr/openApi/restful/pblprfr"); /* URL */
        urlBuilder.append("?" + URLEncoder.encode("service", "UTF-8") + "=30e086f808414e6d928c0243552b3ec8");
        urlBuilder.append("&" + URLEncoder.encode("stdate", "UTF-8") + "=" + URLEncoder.encode(currentDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("eddate", "UTF-8") + "=" + URLEncoder.encode(nextMonth, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("cpage", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("rows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("signgucode", "UTF-8") + "=" + URLEncoder.encode(si, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("signgucodesub", "UTF-8") + "=" + URLEncoder.encode(gngu, "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code_PERFORM: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }
}