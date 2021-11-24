package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsDescriptionDto extends ValueObject implements ReflectionBasedSerialization {
   @SerializedName("name")
   public String f_87462_;
   @SerializedName("description")
   public String f_87463_;

   public RealmsDescriptionDto(String p_87465_, String p_87466_) {
      this.f_87462_ = p_87465_;
      this.f_87463_ = p_87466_;
   }
}