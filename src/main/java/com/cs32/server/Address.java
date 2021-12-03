package com.cs32.server;

/**
 * A class that stores address info.
 */
public class Address {

  private String address;
  private final String trueKey;
  private String active;

  /**
   * Constructor for an Address object.
   * @param address - The displayed address.
   * @param trueKey - The real ID.
   * @param active - True if the address is able to receive mail, else false.
   */
  public Address(String address, String trueKey, String active) {
    this.address = address;
    this.trueKey = trueKey;
    this.active = active;
  }

  public String getAddress() {
    return address;
  }

  public String getTrueKey() {
    return trueKey;
  }

  public boolean getActive() {
    if (active.equals("true")) {
      return true;
    } else if (active.equals("false")) {
      return false;
    } else {
      throw new RuntimeException("ERROR: There was an issue with the" +
          "formatting of the active attribute.");
    }
  }
}
