package net.minecraft.network.protocol.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public class ServerStatus {
   public static final int f_179835_ = 64;
   public static final int f_179836_ = 64;
   private Component f_134900_;
   private ServerStatus.Players f_134901_;
   private ServerStatus.Version f_134902_;
   private String f_134903_;
   private transient net.minecraftforge.fmllegacy.network.FMLStatusPing forgeData;

   public net.minecraftforge.fmllegacy.network.FMLStatusPing getForgeData() {
      return this.forgeData;
   }

   public void setForgeData(net.minecraftforge.fmllegacy.network.FMLStatusPing data){
      this.forgeData = data;
      invalidateJson();
   }

   public Component m_134905_() {
      return this.f_134900_;
   }

   public void m_134908_(Component p_134909_) {
      this.f_134900_ = p_134909_;
      invalidateJson();
   }

   public ServerStatus.Players m_134914_() {
      return this.f_134901_;
   }

   public void m_134910_(ServerStatus.Players p_134911_) {
      this.f_134901_ = p_134911_;
      invalidateJson();
   }

   public ServerStatus.Version m_134915_() {
      return this.f_134902_;
   }

   public void m_134912_(ServerStatus.Version p_134913_) {
      this.f_134902_ = p_134913_;
      invalidateJson();
   }

   public void m_134906_(String p_134907_) {
      this.f_134903_ = p_134907_;
      invalidateJson();
   }

   public String m_134916_() {
      return this.f_134903_;
   }

   private java.util.concurrent.Semaphore mutex = new java.util.concurrent.Semaphore(1);
   private String json = null;
   /**
    * Returns this object as a Json string.
    * Converting to JSON if a cached version is not available.
    *
    * Also to prevent potentially large memory allocations on the server
    * this is moved from the SPacketServerInfo writePacket function
    *
    * As this method is called from the network threads so thread safety is important!
    */
   public String getJson() {
      String ret = this.json;
      if (ret == null) {
         mutex.acquireUninterruptibly();
         ret = this.json;
         if (ret == null) {
            ret = net.minecraft.network.protocol.status.ClientboundStatusResponsePacket.f_134885_.toJson(this);
            this.json = ret;
         }
         mutex.release();
      }
      return ret;
   }

   /**
    * Invalidates the cached json, causing the next call to getJson to rebuild it.
    * This is needed externally because PlayerCountData.setPlayer's is public.
    */
   public void invalidateJson() {
      this.json = null;
   }

   public static class Players {
      private final int f_134917_;
      private final int f_134918_;
      private GameProfile[] f_134919_;

      public Players(int p_134921_, int p_134922_) {
         this.f_134917_ = p_134921_;
         this.f_134918_ = p_134922_;
      }

      public int m_134923_() {
         return this.f_134917_;
      }

      public int m_134926_() {
         return this.f_134918_;
      }

      public GameProfile[] m_134927_() {
         return this.f_134919_;
      }

      public void m_134924_(GameProfile[] p_134925_) {
         this.f_134919_ = p_134925_;
      }

      public static class Serializer implements JsonDeserializer<ServerStatus.Players>, JsonSerializer<ServerStatus.Players> {
         public ServerStatus.Players deserialize(JsonElement p_134930_, Type p_134931_, JsonDeserializationContext p_134932_) throws JsonParseException {
            JsonObject jsonobject = GsonHelper.m_13918_(p_134930_, "players");
            ServerStatus.Players serverstatus$players = new ServerStatus.Players(GsonHelper.m_13927_(jsonobject, "max"), GsonHelper.m_13927_(jsonobject, "online"));
            if (GsonHelper.m_13885_(jsonobject, "sample")) {
               JsonArray jsonarray = GsonHelper.m_13933_(jsonobject, "sample");
               if (jsonarray.size() > 0) {
                  GameProfile[] agameprofile = new GameProfile[jsonarray.size()];

                  for(int i = 0; i < agameprofile.length; ++i) {
                     JsonObject jsonobject1 = GsonHelper.m_13918_(jsonarray.get(i), "player[" + i + "]");
                     String s = GsonHelper.m_13906_(jsonobject1, "id");
                     agameprofile[i] = new GameProfile(UUID.fromString(s), GsonHelper.m_13906_(jsonobject1, "name"));
                  }

                  serverstatus$players.m_134924_(agameprofile);
               }
            }

            return serverstatus$players;
         }

         public JsonElement serialize(ServerStatus.Players p_134934_, Type p_134935_, JsonSerializationContext p_134936_) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("max", p_134934_.m_134923_());
            jsonobject.addProperty("online", p_134934_.m_134926_());
            if (p_134934_.m_134927_() != null && p_134934_.m_134927_().length > 0) {
               JsonArray jsonarray = new JsonArray();

               for(int i = 0; i < p_134934_.m_134927_().length; ++i) {
                  JsonObject jsonobject1 = new JsonObject();
                  UUID uuid = p_134934_.m_134927_()[i].getId();
                  jsonobject1.addProperty("id", uuid == null ? "" : uuid.toString());
                  jsonobject1.addProperty("name", p_134934_.m_134927_()[i].getName());
                  jsonarray.add(jsonobject1);
               }

               jsonobject.add("sample", jsonarray);
            }

            return jsonobject;
         }
      }
   }

   public static class Serializer implements JsonDeserializer<ServerStatus>, JsonSerializer<ServerStatus> {
      public ServerStatus deserialize(JsonElement p_134947_, Type p_134948_, JsonDeserializationContext p_134949_) throws JsonParseException {
         JsonObject jsonobject = GsonHelper.m_13918_(p_134947_, "status");
         ServerStatus serverstatus = new ServerStatus();
         if (jsonobject.has("description")) {
            serverstatus.m_134908_(p_134949_.deserialize(jsonobject.get("description"), Component.class));
         }

         if (jsonobject.has("players")) {
            serverstatus.m_134910_(p_134949_.deserialize(jsonobject.get("players"), ServerStatus.Players.class));
         }

         if (jsonobject.has("version")) {
            serverstatus.m_134912_(p_134949_.deserialize(jsonobject.get("version"), ServerStatus.Version.class));
         }

         if (jsonobject.has("favicon")) {
            serverstatus.m_134906_(GsonHelper.m_13906_(jsonobject, "favicon"));
         }

         if (jsonobject.has("forgeData")) {
            serverstatus.setForgeData(net.minecraftforge.fmllegacy.network.FMLStatusPing.Serializer.deserialize(GsonHelper.m_13930_(jsonobject, "forgeData"), p_134949_));
         }

         return serverstatus;
      }

      public JsonElement serialize(ServerStatus p_134951_, Type p_134952_, JsonSerializationContext p_134953_) {
         JsonObject jsonobject = new JsonObject();
         if (p_134951_.m_134905_() != null) {
            jsonobject.add("description", p_134953_.serialize(p_134951_.m_134905_()));
         }

         if (p_134951_.m_134914_() != null) {
            jsonobject.add("players", p_134953_.serialize(p_134951_.m_134914_()));
         }

         if (p_134951_.m_134915_() != null) {
            jsonobject.add("version", p_134953_.serialize(p_134951_.m_134915_()));
         }

         if (p_134951_.m_134916_() != null) {
            jsonobject.addProperty("favicon", p_134951_.m_134916_());
         }

         if(p_134951_.getForgeData() != null){
            jsonobject.add("forgeData", net.minecraftforge.fmllegacy.network.FMLStatusPing.Serializer.serialize(p_134951_.getForgeData(), p_134953_));
         }

         return jsonobject;
      }
   }

   public static class Version {
      private final String f_134962_;
      private final int f_134963_;

      public Version(String p_134965_, int p_134966_) {
         this.f_134962_ = p_134965_;
         this.f_134963_ = p_134966_;
      }

      public String m_134967_() {
         return this.f_134962_;
      }

      public int m_134968_() {
         return this.f_134963_;
      }

      public static class Serializer implements JsonDeserializer<ServerStatus.Version>, JsonSerializer<ServerStatus.Version> {
         public ServerStatus.Version deserialize(JsonElement p_134971_, Type p_134972_, JsonDeserializationContext p_134973_) throws JsonParseException {
            JsonObject jsonobject = GsonHelper.m_13918_(p_134971_, "version");
            return new ServerStatus.Version(GsonHelper.m_13906_(jsonobject, "name"), GsonHelper.m_13927_(jsonobject, "protocol"));
         }

         public JsonElement serialize(ServerStatus.Version p_134975_, Type p_134976_, JsonSerializationContext p_134977_) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("name", p_134975_.m_134967_());
            jsonobject.addProperty("protocol", p_134975_.m_134968_());
            return jsonobject;
         }
      }
   }
}
