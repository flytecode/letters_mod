package com.cs32.lettersmod.courier;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;

import java.io.IOException;

// class that holds the meat of the functionalities needed for commands and for mailbox tileentity
// these functions are used within the commands classes and the tileentities
public class MailCourier {
  /**
   * Initializes an address for a world
   * @param saver - world saved data for this world
   * @return string indicating success or failure
   */
  public static String initAddr(SavedDataClass saver) {
    if (!saver.data.contains("address")) {
      try {
        // set up apiclient, send a post request to server with old addr
        ApiClient poster = new ApiClient("https://serene-bayou-00030.herokuapp.com/initaddr");
        JsonObject reqBody = new JsonObject();
        JsonObject reqResult = poster.postFromJson(reqBody);

        // get back the new addr and print it out
        String newAddr = reqResult.get("newaddr").getAsString();

        // save the nbt data in world data
        saver.data.putString("address", newAddr);
        saver.markDirty();

        return newAddr + " being set as worldAddress";
      } catch (IOException e) {
        e.printStackTrace();
        return "Error connecting to server, please try again.";
      }
    } else {
      // if a mailbox has already been created for this world
      return "worldAddress has already been set";
    }
  }

  /**
   * Changes the address of the current world
   * @param saver - saved data for that world
   * @param oldAddress - the old address for that world
   * @return - string indicating success/failure
   */
  public static String changeAddr(SavedDataClass saver, String oldAddress) {
    if (saver.data.contains("address")) {
      String currentAddress = saver.data.getString("address");

      if (!currentAddress.equals(oldAddress)) {
        // incorrect oldaddr, tell the user and don't do anything else
        return "oldaddr does not match current worldAddress.";
      } else {
        try {
          // set up apiclient, send a post request to server with old addr
          ApiClient poster = new ApiClient("https://serene-bayou-00030.herokuapp.com/changeaddr"); // http://localhost:4567/changeaddr
          JsonObject reqBody = new JsonObject();
          reqBody.addProperty("oldaddr", oldAddress);
          JsonObject reqResult = poster.postFromJson(reqBody);

          // get back the new addr and print it out
          String newAddr = reqResult.get("newaddr").getAsString();

          // save the nbt data in world data
          saver.data.putString("address", newAddr);
          saver.markDirty();

          return newAddr + " being set as worldAddress";

        } catch (IOException e) {
          e.printStackTrace();
          return "Error connecting to server, please try again.";
        }
      }
    } else {
      // if a mailbox has not been created for this world yet
      return "worldAddress has not yet been set, please create a mailbox for this world.";
    }
  }


  /**
   * Function that gets mail and stores in particular SavedDataClass
   * @param saver - SavedDataClass that contains data for this world
   * @param mailboxSlots  - number of free slots to get packages for (max number of packages to retrieve)
   * @return string describing result of getMail, which can be printed or displayed in TileEntity
   */
  public static String getMail(SavedDataClass saver, int mailboxSlots) {
    if (mailboxSlots == 0) {
      // there is no space left in local mailbox
      return "No space left - empty your mailbox!";
    }

    if (!saver.data.contains("address")) {
      // if a mailbox has not yet been created for this world
      return "worldAddress has not been set";
    } else {
      // get the current address
      String currentAddress = saver.data.getString("address");

      try {
        // set up apiclient, send a post request to server with old addr
        ApiClient poster = new ApiClient("https://serene-bayou-00030.herokuapp.com/getmail");
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("address", currentAddress);
        reqBody.addProperty("maxNumParcels", mailboxSlots);
        JsonObject reqResult = poster.postFromJson(reqBody);

        // get back the returned parcels
        JsonArray parcels = reqResult.getAsJsonArray("parcels");

        // get the parcels currently in the mailbox
        ListNBT parcelList = (ListNBT) saver.data.get("parcelList");
        if (parcelList == null) {
          parcelList = new ListNBT();
        }

        // go through and add returned parcels to list
        for (JsonElement parcelJson : parcels) {
          CompoundNBT parcel = JsonToNBT.getTagFromJson(parcelJson.getAsString());
          parcelList.add(parcel);
        }

        // save the data and mark dirty
        saver.data.put("parcelList", parcelList);
        saver.markDirty();

        // indicate success
        return parcels.size() + " parcels retrieved!";

      } catch (IOException e) {
        e.printStackTrace();
        return "Error connecting to server, please try again.";
      } catch (CommandSyntaxException e) {
        e.printStackTrace();
        return "Malformed data retrieved from server, please try again.";
      }
    }
  }

  /**
   * Send a parcel to another world
   * @param saver - world saved data
   * @param recipient - string of recipient's address
   * @param parcelString - json string of the parcel to be sent
   * @return - string indicating success of the send
   */
  public static String send(SavedDataClass saver, String recipient, String parcelString) {
    if (saver.data.contains("address")) {
      String currentAddress = saver.data.getString("address");

      try {
        // set up apiclient, send a post request to server with old addr
        ApiClient poster = new ApiClient("https://serene-bayou-00030.herokuapp.com/send");
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sender", currentAddress);
        reqBody.addProperty("recipient", recipient);
        reqBody.addProperty("parcelString", parcelString);
        JsonObject reqResult = poster.postFromJson(reqBody);

        // get back a string indicating whether the parcel was receieved or not
        String receivedParcel = reqResult.get("receivedParcel").getAsString();

        // if it was null, indicate that the parcel was not received by the database
        if (receivedParcel == null) {
          return "Parcel was not received by server";
        } else {
          return receivedParcel + " was received by mailroom database";
        }
      } catch (IOException e) {
        e.printStackTrace();
        return "Error connecting to server, please try again.";
      }
    } else {
      // if a mailbox has not yet been created for this world
      return "worldAddress has not been set";
    }
  }
}
