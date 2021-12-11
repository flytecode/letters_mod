package com.cs32.lettersmod.network.saveddata;

/**
 * A class that stores address info.
 */
public class Address {

  private String address;

  /**
   * Constructor for an Address object.
   * @param address - The displayed address.
   */
  public Address(String address) {
    this.address = address;
  }

  public String getAddress() {
    return this.address;
  }

}
