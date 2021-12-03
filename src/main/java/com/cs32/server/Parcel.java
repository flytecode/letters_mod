package com.cs32.server;

/**
 * A class that stores parcel info.
 */
public class Parcel {

  private final String id;
  private String recipient;
  private String sender;
  private String downloaded;
  private String parcel;

  /**
   * Constructor for a parcel object.
   * @param id - The unique ID for this parcel.
   * @param recipient - The trueKey of the recipient.
   * @param sender - The trueKey of the sender.
   * @param downloaded - True if the parcel has been downloaded into the game, else false.
   * @param parcel - The JSON data for the item(s) sent.
   */
  public Parcel(String id, String recipient, String sender, String downloaded, String parcel) {
    this.id = id;
    this.recipient = recipient;
    this.sender = sender;
    this.downloaded = downloaded;
    this.parcel = parcel;
  }

  public String getId() {
    return this.id;
  }

  public String getRecipient() {
    return recipient;
  }

  public boolean getDownloaded() {
    if (downloaded.equals("true")) {
      return true;
    } else if (downloaded.equals("false")) {
      return false;
    } else {
      throw new RuntimeException("ERROR: There was an issue with the" +
          "formatting of the downloaded attribute.");
    }
  }

  public String getParcel() {
    return this.parcel;
  }

}
