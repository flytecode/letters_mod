package com.cs32.lettersmod;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This is the ApiClient class, which creates and client and sends a response using the client.
 * Use the getData() method to get the body of the response.
 */
public class ApiClient {

  // a "post" request lmao!!! XD
  // urlParameters has to be a stringified json that contains just the body information
  public static String executePost(String targetURL, String urlParameters) {
    HttpURLConnection connection = null;

    //DEBUG
    System.out.println(urlParameters);

    try {
      //Create connection
      URL url = new URL(targetURL);
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
      DataOutputStream wr = new DataOutputStream (
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
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }


//  private final HttpClient client;
//  private String data;
//
//  /**
//   * Constructor for ApiClient. Makes a new HttpClient builder, specifying the version and timeout.
//   */
//  public ApiClient() {
//    // HttpClient with version HTTP_2 and connection timeout of 15 seconds.
//    // See https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html
//
//    this.client = HttpClient.newBuilder()
//        .version(HttpClient.Version.HTTP_2)
//        .connectTimeout(Duration.ofSeconds(15))
//        .build();
//  }
//
//  /**
//   * This method makes a request and stores the body of the response.
//   *
//   * @param req to send to the client
//   */
//  public void makeRequest(HttpRequest req) {
//    try {
//      HttpResponse<String> apiResponse = client.send(req, HttpResponse.BodyHandlers.ofString());
//      // System.out.println("Status " + apiResponse.statusCode());    //uncomment for testing
//      data = apiResponse.body();
//
//    } catch (IOException ioe) {
//      System.out.println("An I/O error occurred when sending or receiving data.");
//      System.out.println(ioe.getMessage());
//
//    } catch (InterruptedException ie) {
//      System.out.println("The operation was interrupted.");
//      System.out.println(ie.getMessage());
//
//    } catch (IllegalArgumentException iae) {
//      System.out.println(
//          "The request argument was invalid. "
//              + "It must be built as specified by HttpRequest.Builder.");
//      System.out.println(iae.getMessage());
//
//    } catch (SecurityException se) {
//      System.out.println("There was a security configuration error.");
//      System.out.println(se.getMessage());
//    }
//  }
//
//  /**
//   * This method returns the data stored in the data variable, which was given a value from
//   * the makeRequest() method.
//   *
//   * @return data found in response body
//   */
//  public String getData() {
//    String dataTemp = data;
//    data = null;
//    return dataTemp;
//  }
}
