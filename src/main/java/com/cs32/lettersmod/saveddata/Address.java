package com.cs32.lettersmod.saveddata;

import net.minecraft.nbt.CompoundNBT;

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


  public void writeToNBT(CompoundNBT nbt)
  {
    nbt.putString("address", this.address);
  }
}
