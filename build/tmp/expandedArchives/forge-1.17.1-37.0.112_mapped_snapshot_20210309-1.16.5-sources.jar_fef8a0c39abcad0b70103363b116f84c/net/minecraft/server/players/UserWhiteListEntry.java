package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserWhiteListEntry extends StoredUserEntry<GameProfile> {
   public UserWhiteListEntry(GameProfile p_11462_) {
      super(p_11462_);
   }

   public UserWhiteListEntry(JsonObject p_11460_) {
      super(m_11465_(p_11460_));
   }

   protected void m_6009_(JsonObject p_11464_) {
      if (this.m_11373_() != null) {
         p_11464_.addProperty("uuid", this.m_11373_().getId() == null ? "" : this.m_11373_().getId().toString());
         p_11464_.addProperty("name", this.m_11373_().getName());
      }
   }

   private static GameProfile m_11465_(JsonObject p_11466_) {
      if (p_11466_.has("uuid") && p_11466_.has("name")) {
         String s = p_11466_.get("uuid").getAsString();

         UUID uuid;
         try {
            uuid = UUID.fromString(s);
         } catch (Throwable throwable) {
            return null;
         }

         return new GameProfile(uuid, p_11466_.get("name").getAsString());
      } else {
         return null;
      }
   }
}