package com.mojang.realmsclient.dto;

import com.google.gson.Gson;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardedSerializer {
   private final Gson f_87411_ = new Gson();

   public String m_87413_(ReflectionBasedSerialization p_87414_) {
      return this.f_87411_.toJson(p_87414_);
   }

   @Nullable
   public <T extends ReflectionBasedSerialization> T m_87415_(String p_87416_, Class<T> p_87417_) {
      return this.f_87411_.fromJson(p_87416_, p_87417_);
   }
}