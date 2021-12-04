//package com.cs32.lettersmod;
//
//import java.sql.Array;
//import java.sql.Connection;
//import java.sql.Driver;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//
//// TODO documentation
//
///**
// * ORM for MailDatabase.sqlite3.
// *
// * @author lbrito2
// */
//public class LettersDatabase {
//  private Connection conn = null;
//
//  public LettersDatabase(String pathToFile) {
//    Class.forName("org.sqlite.JDBC");
//    String urlToDB = "jdbc:sqlite:" + pathToFile;
//    conn = DriverManager.getConnection(urlToDB);
//    Statement stat = conn.createStatement();
//    stat.executeUpdate("PRAGMA foreign_keys=ON;");
//  }
//
//  public ArrayList<Address> rawAddressQuery(String query) {
//    PreparedStatement prep = conn.prepareStatement(query);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Address> resultList = new ArrayList<Address>();
//    while (rs.next()) {
//      Address currentAddress = new Address(rs.getString(1),
//          rs.getString(2),
//          rs.getString(3));
//      resultList.add(currentAddress);
//    }
//    return resultList;
//  }
//
//  public ArrayList<Address> getAddressByAddress(String address) {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT Address, TrueKey, Active"
//            + "FROM Addresses"
//            + "WHERE Address=?"
//    );
//    prep.setString(1, address);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Address> resultList = new ArrayList<Address>();
//    while (rs.next()) {
//      Address currentAddress = new Address(rs.getString(1),
//          rs.getString(2),
//          rs.getString(3));
//      resultList.add(currentAddress);
//    }
//    return resultList;
//  }
//
//  public ArrayList<Address> getAddressByTrueKey(String address) {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT Address, TrueKey, Active"
//            + "FROM Addresses"
//            + "WHERE TrueKey=?"
//    );
//    prep.setString(1, address);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Address> resultList = new ArrayList<Address>();
//    while (rs.next()) {
//      Address currentAddress = new Address(rs.getString(1),
//          rs.getString(2),
//          rs.getString(3));
//      resultList.add(currentAddress);
//    }
//    return resultList;
//  }
//  // - Change address, active, downloaded
//
//  public ArrayList<Parcel> getParcelById(int id) {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT ID, Recipient, Sender, Downloaded, JsonData"
//            + "FROM Parcels"
//            + "WHERE ID=?"
//    );
//    prep.setInt(1, id);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Parcel> resultList = new ArrayList<Parcel>();
//    while (rs.next()) {
//
//      Parcel currentParcel = new Parcel(rs.getInt(1)),
//      rs.getString(2),
//          rs.getString(3),
//          rs.getString(4),
//          rs.getString(5));
//      resultList.add(currentParcel);
//    }
//    return resultList;
//  }
//
//  public ArrayList<Parcel> getParcelByRecipient(String recipient) {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT ID, Recipient, Sender, Downloaded, JsonData"
//            + "FROM Parcels"
//            + "WHERE Recipient=?"
//    );
//    prep.setString(1, recipient);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Parcel> resultList = new ArrayList<Parcel>();
//    while (rs.next()) {
//      Parcel currentParcel = new Parcel(rs.getInt(1)),
//      rs.getString(2),
//          rs.getString(3),
//          rs.getString(4),
//          rs.getString(5));
//      Parcel currentParcel = new Parcel()
//      resultList.add(currentParcel);
//    }
//    return resultList;
//  }
//
//  public ArrayList<Parcel> getParcelBySender(String sender) {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT ID, Recipient, Sender, Downloaded, JsonData"
//            + "FROM Parcels"
//            + "WHERE Sender=?"
//    );
//    prep.setString(1, sender);
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Parcel> resultList = new ArrayList<Parcel>();
//    while (rs.next()) {
//      Parcel currentParcel = new Parcel(rs.getInt(1)),
//      rs.getString(2),
//          rs.getString(3),
//          rs.getString(4),
//          rs.getString(5));
//      resultList.add(currentParcel);
//    }
//    return resultList;
//  }
//
//  public ArrayList<Parcel> getDownloadedParcels() {
//    PreparedStatement prep = conn.prepareStatement(
//        "SELECT ID, Recipient, Sender, Downloaded, JsonData"
//            + "FROM Parcels"
//            + "WHERE Downloaded=true"
//    );
//    ResultSet rs = prep.executeQuery();
//    ArrayList<Parcel> resultList = new ArrayList<Parcel>();
//    while (rs.next()) {
//      Parcel currentParcel = new Parcel(rs.getInt(1)),
//      rs.getString(2),
//          rs.getString(3),
//          rs.getString(4),
//          rs.getString(5));
//      resultList.add(currentParcel);
//    }
//    return resultList;
//  }
//
//  public void setParcelDownloaded(int id, boolean downloaded) {
//    PreparedStatement prep = conn.prepareStatement(
//        "UPDATE Parcels"
//            + "SET Downloaded = ?"
//            + "WHERE Id=?"
//    );
//    prep.setInt(2, id);
//    prep.setBoolean(1, downloaded)
//    prep.executeUpdate();
//  }
//
//  public void setAddressActive(string trueKey, boolean active) {
//    PreparedStatement prep = conn.prepareStatement(
//        "UPDATE Addresses"
//            + "SET Active = ?"
//            + "WHERE TrueKey=?"
//    );
//    prep.setBoolean(1, active);
//    prep.setString(2, trueKey);
//    prep.executeUpdate();
//  }
//
//  public void insertParcel(Parcel parcel) {
//    PreparedStatement prep = conn.prepareStatement(
//        "INSERT INTO Parcels (ID, Recipient, Sender, Downloaded, Parcel)"
//            + "VALUES (?, ?, ?, ?, ?)"
//    )
//    prep.setInt(1, parcel.getId());
//    prep.setString(2, parcel.getRecipient());
//    prep.setString(3, parcel.getSender());
//    prep.setBoolean(4, parcel.getDownloaded());
//    prep.setString(5, parcel.getParcel());
//
//    prep.executeUpdate();
//  }
//
//  public void insertAddress(Address address) {
//    PreparedStatement prep = conn.prepareStatement(
//        "INSERT INTO Addresses (Address, TrueKey, Active)"
//            + "VALUES (?, ?, ?)"
//    )
//    prep.setString(1, address.getAddress());
//    prep.setString(2, address.getTrueKey());
//    prep.setString(3, address.getActive());
//
//    prep.executeUpdate();
//  }
//}
