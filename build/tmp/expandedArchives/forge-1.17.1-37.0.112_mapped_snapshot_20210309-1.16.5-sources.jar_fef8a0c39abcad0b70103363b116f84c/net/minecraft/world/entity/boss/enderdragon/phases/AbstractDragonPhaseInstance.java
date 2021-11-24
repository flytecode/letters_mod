package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDragonPhaseInstance implements DragonPhaseInstance {
   protected final EnderDragon f_31176_;

   public AbstractDragonPhaseInstance(EnderDragon p_31178_) {
      this.f_31176_ = p_31178_;
   }

   public boolean m_7080_() {
      return false;
   }

   public void m_6991_() {
   }

   public void m_6989_() {
   }

   public void m_8059_(EndCrystal p_31184_, BlockPos p_31185_, DamageSource p_31186_, @Nullable Player p_31187_) {
   }

   public void m_7083_() {
   }

   public void m_7081_() {
   }

   public float m_7072_() {
      return 0.6F;
   }

   @Nullable
   public Vec3 m_5535_() {
      return null;
   }

   public float m_7584_(DamageSource p_31181_, float p_31182_) {
      return p_31182_;
   }

   public float m_7089_() {
      float f = (float)this.f_31176_.m_20184_().m_165924_() + 1.0F;
      float f1 = Math.min(f, 40.0F);
      return 0.7F / f1 / f;
   }
}