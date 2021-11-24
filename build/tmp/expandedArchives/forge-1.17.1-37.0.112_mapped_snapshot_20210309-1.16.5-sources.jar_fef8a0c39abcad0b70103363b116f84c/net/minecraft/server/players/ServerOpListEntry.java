package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;

public class ServerOpListEntry extends StoredUserEntry<GameProfile> {
   private final int f_11355_;
   private final boolean f_11356_;

   public ServerOpListEntry(GameProfile p_11360_, int p_11361_, boolean p_11362_) {
      super(p_11360_);
      this.f_11355_ = p_11361_;
      this.f_11356_ = p_11362_;
   }

   public ServerOpListEntry(JsonObject p_11358_) {
      super(m_11367_(p_11358_));
      this.f_11355_ = p_11358_.has("level") ? p_11358_.get("level").getAsInt() : 0;
      this.f_11356_ = p_11358_.has("bypassesPlayerLimit") && p_11358_.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int m_11363_() {
      return this.f_11355_;
   }

   public boolean m_11366_() {
      return this.f_11356_;
   }

   protected void m_6009_(JsonObject p_11365_) {
      if (this.m_11373_() != null) {
         p_11365_.addProperty("uuid", this.m_11373_().getId() == null ? "" : this.m_11373_().getId().toString());
         p_11365_.addProperty("name", this.m_11373_().getName());
         p_11365_.addProperty("level", this.f_11355_);
         p_11365_.addProperty("bypassesPlayerLimit", this.f_11356_);
      }
   }

   @Nullable
   private static GameProfile m_11367_(JsonObject p_11368_) {
      if (p_11368_.has("uuid") && p_11368_.has("name")) {
         String s = p_11368_.get("uuid").getAsString();

         UUID uuid;
         try {
            uuid = UUID.fromString(s);
         } catch (Throwable throwable) {
            return null;
         }

         return new GameProfile(uuid, p_11368_.get("name").getAsString());
      } else {
         return null;
      }
   }
}