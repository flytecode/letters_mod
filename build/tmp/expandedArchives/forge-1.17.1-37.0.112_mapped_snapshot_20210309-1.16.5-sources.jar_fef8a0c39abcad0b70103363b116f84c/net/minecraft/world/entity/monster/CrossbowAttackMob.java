package net.minecraft.world.entity.monster;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public interface CrossbowAttackMob extends RangedAttackMob {
   void m_6136_(boolean p_32339_);

   void m_5811_(LivingEntity p_32328_, ItemStack p_32329_, Projectile p_32330_, float p_32331_);

   @Nullable
   LivingEntity m_5448_();

   void m_5847_();

   default void m_32336_(LivingEntity p_32337_, float p_32338_) {
      InteractionHand interactionhand = ProjectileUtil.getWeaponHoldingHand(p_32337_, item -> item instanceof CrossbowItem);
      ItemStack itemstack = p_32337_.m_21120_(interactionhand);
      if (p_32337_.m_21093_(is -> is.m_41720_() instanceof CrossbowItem)) {
         CrossbowItem.m_40887_(p_32337_.f_19853_, p_32337_, interactionhand, itemstack, p_32338_, (float)(14 - p_32337_.f_19853_.m_46791_().m_19028_() * 4));
      }

      this.m_5847_();
   }

   default void m_32322_(LivingEntity p_32323_, LivingEntity p_32324_, Projectile p_32325_, float p_32326_, float p_32327_) {
      double d0 = p_32324_.m_20185_() - p_32323_.m_20185_();
      double d1 = p_32324_.m_20189_() - p_32323_.m_20189_();
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3 = p_32324_.m_20227_(0.3333333333333333D) - p_32325_.m_20186_() + d2 * (double)0.2F;
      Vector3f vector3f = this.m_32332_(p_32323_, new Vec3(d0, d3, d1), p_32326_);
      p_32325_.m_6686_((double)vector3f.m_122239_(), (double)vector3f.m_122260_(), (double)vector3f.m_122269_(), p_32327_, (float)(14 - p_32323_.f_19853_.m_46791_().m_19028_() * 4));
      p_32323_.m_5496_(SoundEvents.f_11847_, 1.0F, 1.0F / (p_32323_.m_21187_().nextFloat() * 0.4F + 0.8F));
   }

   default Vector3f m_32332_(LivingEntity p_32333_, Vec3 p_32334_, float p_32335_) {
      Vec3 vec3 = p_32334_.m_82541_();
      Vec3 vec31 = vec3.m_82537_(new Vec3(0.0D, 1.0D, 0.0D));
      if (vec31.m_82556_() <= 1.0E-7D) {
         vec31 = vec3.m_82537_(p_32333_.m_20289_(1.0F));
      }

      Quaternion quaternion = new Quaternion(new Vector3f(vec31), 90.0F, true);
      Vector3f vector3f = new Vector3f(vec3);
      vector3f.m_122251_(quaternion);
      Quaternion quaternion1 = new Quaternion(vector3f, p_32335_, true);
      Vector3f vector3f1 = new Vector3f(vec3);
      vector3f1.m_122251_(quaternion1);
      return vector3f1;
   }
}
