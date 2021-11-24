package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonHoldingPatternPhase extends AbstractDragonPhaseInstance {
   private static final TargetingConditions f_31224_ = TargetingConditions.m_148352_().m_148355_();
   private Path f_31225_;
   private Vec3 f_31226_;
   private boolean f_31227_;

   public DragonHoldingPatternPhase(EnderDragon p_31230_) {
      super(p_31230_);
   }

   public EnderDragonPhase<DragonHoldingPatternPhase> m_7309_() {
      return EnderDragonPhase.f_31377_;
   }

   public void m_6989_() {
      double d0 = this.f_31226_ == null ? 0.0D : this.f_31226_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
      if (d0 < 100.0D || d0 > 22500.0D || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
         this.m_31242_();
      }

   }

   public void m_7083_() {
      this.f_31225_ = null;
      this.f_31226_ = null;
   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31226_;
   }

   private void m_31242_() {
      if (this.f_31225_ != null && this.f_31225_.m_77392_()) {
         BlockPos blockpos = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.f_65714_));
         int i = this.f_31176_.m_31158_() == null ? 0 : this.f_31176_.m_31158_().m_64098_();
         if (this.f_31176_.m_21187_().nextInt(i + 3) == 0) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31379_);
            return;
         }

         double d0 = 64.0D;
         Player player = this.f_31176_.f_19853_.m_45949_(f_31224_, this.f_31176_, (double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
         if (player != null) {
            d0 = blockpos.m_123309_(player.m_20182_(), true) / 512.0D;
         }

         if (player != null && (this.f_31176_.m_21187_().nextInt(Mth.m_14040_((int)d0) + 2) == 0 || this.f_31176_.m_21187_().nextInt(i + 2) == 0)) {
            this.m_31236_(player);
            return;
         }
      }

      if (this.f_31225_ == null || this.f_31225_.m_77392_()) {
         int j = this.f_31176_.m_31155_();
         int k = j;
         if (this.f_31176_.m_21187_().nextInt(8) == 0) {
            this.f_31227_ = !this.f_31227_;
            k = j + 6;
         }

         if (this.f_31227_) {
            ++k;
         } else {
            --k;
         }

         if (this.f_31176_.m_31158_() != null && this.f_31176_.m_31158_().m_64098_() >= 0) {
            k = k % 12;
            if (k < 0) {
               k += 12;
            }
         } else {
            k = k - 12;
            k = k & 7;
            k = k + 12;
         }

         this.f_31225_ = this.f_31176_.m_31104_(j, k, (Node)null);
         if (this.f_31225_ != null) {
            this.f_31225_.m_77374_();
         }
      }

      this.m_31243_();
   }

   private void m_31236_(Player p_31237_) {
      this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31378_);
      this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31378_).m_31358_(p_31237_);
   }

   private void m_31243_() {
      if (this.f_31225_ != null && !this.f_31225_.m_77392_()) {
         Vec3i vec3i = this.f_31225_.m_77400_();
         this.f_31225_.m_77374_();
         double d0 = (double)vec3i.m_123341_();
         double d1 = (double)vec3i.m_123343_();

         double d2;
         do {
            d2 = (double)((float)vec3i.m_123342_() + this.f_31176_.m_21187_().nextFloat() * 20.0F);
         } while(d2 < (double)vec3i.m_123342_());

         this.f_31226_ = new Vec3(d0, d2, d1);
      }

   }

   public void m_8059_(EndCrystal p_31232_, BlockPos p_31233_, DamageSource p_31234_, @Nullable Player p_31235_) {
      if (p_31235_ != null && this.f_31176_.m_6779_(p_31235_)) {
         this.m_31236_(p_31235_);
      }

   }
}