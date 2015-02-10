import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
    JSONObject json = readJsonFromUrl("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
    // System.out.println(json.toString());
    // System.out.println(json.get("query"));
     // JSONObject queryObject = json.get("query");
    JSONObject queryObject= json.getJSONObject("query");
    JSONObject resultsObject = queryObject.getJSONObject("results");
    JSONObject channelObject = resultsObject.getJSONObject("channel");
    JSONObject itemObject = channelObject.getJSONObject("item");
    // JSONArray forecastArray = itemObject.getJSONArray("forecast");
    JSONArray forecastArray = itemObject.getJSONArray("forecast");
    JSONObject day1 = forecastArray.getJSONObject(0);
    String day1High = day1.getString("high");
    String day1Low = day1.getString("low");

    JSONArray forecastArray = itemObject.getJSONArray("forecast");
    JSONObject day2 = forecastArray.getJSONObject(1);
    String day2High = day1.getString("high");
    String day2Low = day1.getString("low");

     System.out.println(day2High);
     System.out.println(day2Low);

  }
}