package com.cs32.lettersmod;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class stores helper functions that allow for easy "post" requests (get it like mail)
 *
 * How to use:
 *
 * ApiClient poster = new ApiClient("http://localhost:4567/ways");
 *               JsonObject reqBody = new JsonObject();
 *               reqBody.addProperty("lat1", 41.828147);
 *               reqBody.addProperty("lon1", -71.407971);
 *               reqBody.addProperty("lat2", 41.823142);
 *               reqBody.addProperty("lon2", -71.392231);
 *
 */
public class ApiClient {

  private final String tURL; // http://localhost:4567/ways

  public ApiClient(String targetURL) {
    tURL = targetURL;
  }

  public JsonObject postFromJson(JsonObject jo) throws IOException {
    // make post request to server
    String postRes = this.executePost(jo.toString());

    // get and print out the results of post request
    assert postRes != null;
    return (new JsonParser().parse(postRes).getAsJsonObject());
  }

  // a "post" request lmao!!! XD
  // urlParameters has to be a stringified json that contains just the body information
  private String executePost(String urlParameters) throws IOException {
    HttpURLConnection connection = null;

    try {
      //Create connection
      URL url = new URL(this.tURL);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type",
          "application/x-www-form-urlencoded");

      connection.setRequestProperty("Content-Length",
          Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Access-Control-Allow-Origin", "*");

      connection.setUseCaches(false);
      connection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream(
          connection.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.close();

      //Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      return response.toString();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
