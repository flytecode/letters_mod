package net.minecraft.world.effect;

import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;

public final class MobEffectUtil {
   public static String m_19581_(MobEffectInstance p_19582_, float p_19583_) {
      if (p_19582_.m_19577_()) {
         return "**:**";
      } else {
         int i = Mth.m_14143_((float)p_19582_.m_19557_() * p_19583_);
         return StringUtil.m_14404_(i);
      }
   }

   public static boolean m_19584_(LivingEntity p_19585_) {
      return p_19585_.m_21023_(MobEffects.f_19598_) || p_19585_.m_21023_(MobEffects.f_19592_);
   }

   public static int m_19586_(LivingEntity p_19587_) {
      int i = 0;
      int j = 0;
      if (p_19587_.m_21023_(MobEffects.f_19598_)) {
         i = p_19587_.m_21124_(MobEffects.f_19598_).m_19564_();
      }

      if (p_19587_.m_21023_(MobEffects.f_19592_)) {
         j = p_19587_.m_21124_(MobEffects.f_19592_).m_19564_();
      }

      return Math.max(i, j);
   }

   public static boolean m_19588_(LivingEntity p_19589_) {
      return p_19589_.m_21023_(MobEffects.f_19608_) || p_19589_.m_21023_(MobEffects.f_19592_);
   }
}