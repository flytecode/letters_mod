package com.cs32.lettersmod;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * This is the ApiClient class, which creates and client and sends a response using the client.
 * Use the getData() method to get the body of the response.
 */
public class ApiClient {
  private final HttpClient client;
  private String data;

  /**
   * Constructor for ApiClient. Makes a new HttpClient builder, specifying the version and timeout.
   */
  public ApiClient() {
    // HttpClient with version HTTP_2 and connection timeout of 15 seconds.
    // See https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html

    this.client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(15))
        .build();
  }

  /**
   * This method makes a request and stores the body of the response.
   *
   * @param req to send to the client
   */
  public void makeRequest(HttpRequest req) {
    try {
      HttpResponse<String> apiResponse = client.send(req, HttpResponse.BodyHandlers.ofString());
      // System.out.println("Status " + apiResponse.statusCode());    //uncomment for testing
      data = apiResponse.body();

    } catch (IOException ioe) {
      System.out.println("An I/O error occurred when sending or receiving data.");
      System.out.println(ioe.getMessage());

    } catch (InterruptedException ie) {
      System.out.println("The operation was interrupted.");
      System.out.println(ie.getMessage());

    } catch (IllegalArgumentException iae) {
      System.out.println(
          "The request argument was invalid. "
              + "It must be built as specified by HttpRequest.Builder.");
      System.out.println(iae.getMessage());

    } catch (SecurityException se) {
      System.out.println("There was a security configuration error.");
      System.out.println(se.getMessage());
    }
  }

  /**
   * This method returns the data stored in the data variable, which was given a value from
   * the makeRequest() method.
   *
   * @return data found in response body
   */
  public String getData() {
    String dataTemp = data;
    data = null;
    return dataTemp;
  }
}
