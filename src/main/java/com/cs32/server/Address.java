package com.cs32.server;

/**
 * A class that stores address info.
 */
public class Address {

  private String address;
  private final Integer trueKey;

  /**
   * Constructor for an Address object.
   * @param address - The displayed address.
   * @param trueKey - The real ID.
   */
  public Address(String address, Integer trueKey) {
    this.address = address;
    this.trueKey = trueKey;
  }

  public String getAddress() {
    return this.address;
  }

  public Integer getTrueKey() {
    return this.trueKey;
  }
}
