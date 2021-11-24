package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsWorldResetDto extends ValueObject implements ReflectionBasedSerialization {
   @SerializedName("seed")
   private final String f_87638_;
   @SerializedName("worldTemplateId")
   private final long f_87639_;
   @SerializedName("levelType")
   private final int f_87640_;
   @SerializedName("generateStructures")
   private final boolean f_87641_;

   public RealmsWorldResetDto(String p_87643_, long p_87644_, int p_87645_, boolean p_87646_) {
      this.f_87638_ = p_87643_;
      this.f_87639_ = p_87644_;
      this.f_87640_ = p_87645_;
      this.f_87641_ = p_87646_;
   }
}