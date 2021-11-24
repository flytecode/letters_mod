package net.minecraft.world.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface ItemSteerable {
   boolean m_6746_();

   void m_7760_(Vec3 p_20858_);

   float m_6748_();

   default boolean m_20854_(Mob p_20855_, ItemBasedSteering p_20856_, Vec3 p_20857_) {
      if (!p_20855_.m_6084_()) {
         return false;
      } else {
         Entity entity = p_20855_.m_146895_();
         if (p_20855_.m_20160_() && p_20855_.m_5807_() && entity instanceof Player) {
            p_20855_.m_146922_(entity.m_146908_());
            p_20855_.f_19859_ = p_20855_.m_146908_();
            p_20855_.m_146926_(entity.m_146909_() * 0.5F);
            p_20855_.m_19915_(p_20855_.m_146908_(), p_20855_.m_146909_());
            p_20855_.f_20883_ = p_20855_.m_146908_();
            p_20855_.f_20885_ = p_20855_.m_146908_();
            p_20855_.f_19793_ = 1.0F;
            p_20855_.f_20887_ = p_20855_.m_6113_() * 0.1F;
            if (p_20856_.f_20834_ && p_20856_.f_20835_++ > p_20856_.f_20836_) {
               p_20856_.f_20834_ = false;
            }

            if (p_20855_.m_6109_()) {
               float f = this.m_6748_();
               if (p_20856_.f_20834_) {
                  f += f * 1.15F * Mth.m_14031_((float)p_20856_.f_20835_ / (float)p_20856_.f_20836_ * (float)Math.PI);
               }

               p_20855_.m_7910_(f);
               this.m_7760_(new Vec3(0.0D, 0.0D, 1.0D));
               p_20855_.f_20903_ = 0;
            } else {
               p_20855_.m_21043_(p_20855_, false);
               p_20855_.m_20256_(Vec3.f_82478_);
            }

            p_20855_.m_146872_();
            return true;
         } else {
            p_20855_.f_19793_ = 0.5F;
            p_20855_.f_20887_ = 0.02F;
            this.m_7760_(p_20857_);
            return false;
         }
      }
   }
}