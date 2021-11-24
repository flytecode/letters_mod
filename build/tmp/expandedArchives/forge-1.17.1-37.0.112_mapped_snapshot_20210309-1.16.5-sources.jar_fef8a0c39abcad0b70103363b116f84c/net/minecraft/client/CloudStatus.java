package net.minecraft.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum CloudStatus {
   OFF("options.off"),
   FAST("options.clouds.fast"),
   FANCY("options.clouds.fancy");

   private final String f_90655_;

   private CloudStatus(String p_167710_) {
      this.f_90655_ = p_167710_;
   }

   public String m_90666_() {
      return this.f_90655_;
   }
}