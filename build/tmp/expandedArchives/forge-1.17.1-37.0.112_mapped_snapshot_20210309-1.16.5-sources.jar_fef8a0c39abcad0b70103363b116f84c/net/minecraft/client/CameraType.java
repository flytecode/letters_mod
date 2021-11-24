package net.minecraft.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum CameraType {
   FIRST_PERSON(true, false),
   THIRD_PERSON_BACK(false, false),
   THIRD_PERSON_FRONT(false, true);

   private static final CameraType[] f_90602_ = values();
   private final boolean f_90603_;
   private final boolean f_90604_;

   private CameraType(boolean p_90610_, boolean p_90611_) {
      this.f_90603_ = p_90610_;
      this.f_90604_ = p_90611_;
   }

   public boolean m_90612_() {
      return this.f_90603_;
   }

   public boolean m_90613_() {
      return this.f_90604_;
   }

   public CameraType m_90614_() {
      return f_90602_[(this.ordinal() + 1) % f_90602_.length];
   }
}