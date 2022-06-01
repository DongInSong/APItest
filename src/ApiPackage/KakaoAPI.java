package ApiPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class KakaoAPI {

    String x;
    String y;
    String address;
    String[] Result;

    public String[] getPosition(String location) throws IOException, ParseException {
        try {

            this.address = location;
            System.out.println(address);
            String addressInco = URLEncoder.encode(address, "UTF-8");

            String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + addressInco;

            String jsonString = new String();

            String buf;

            URL Url = new URL(url);

            HttpsURLConnection conn = (HttpsURLConnection) Url.openConnection();
            String auth = "KakaoAK " + "70d41ac654d6a0d273382aad14f63afc";
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Requested-With", "curl");
            conn.setRequestProperty("Authorization", auth);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((buf = br.readLine()) != null) {
                jsonString += buf;
            }

            System.out.println("Response code_KAKAO: " + conn.getResponseCode());

            JSONParser paser = new JSONParser();

            JSONObject J = (JSONObject) paser.parse(jsonString);
            JSONObject meta = (JSONObject) J.get("meta");

            JSONArray data = (JSONArray) J.get("documents");
            long size = (long) meta.get("total_count");
            System.out.println("size확인: " + size);
            System.out.println("주소: " + address);

            if (size > 0) {
                JSONObject jsonX = (JSONObject) data.get(0);
                System.out.println(jsonX.get("x").toString());
                System.out.println(jsonX.get("y").toString());
                x = jsonX.get("x").toString();
                y = jsonX.get("y").toString();
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new String[] { x, y };
    }
}