package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RegionPingResult extends ValueObject implements ReflectionBasedSerialization {
   @SerializedName("regionName")
   private final String f_87647_;
   @SerializedName("ping")
   private final int f_87648_;

   public RegionPingResult(String p_87650_, int p_87651_) {
      this.f_87647_ = p_87650_;
      this.f_87648_ = p_87651_;
   }

   public int m_87652_() {
      return this.f_87648_;
   }

   public String toString() {
      return String.format(Locale.ROOT, "%s --> %.2f ms", this.f_87647_, (float)this.f_87648_);
   }
}