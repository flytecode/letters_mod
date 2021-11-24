package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonLandingApproachPhase extends AbstractDragonPhaseInstance {
   private static final TargetingConditions f_31253_ = TargetingConditions.m_148352_().m_148355_();
   private Path f_31254_;
   private Vec3 f_31255_;

   public DragonLandingApproachPhase(EnderDragon p_31258_) {
      super(p_31258_);
   }

   public EnderDragonPhase<DragonLandingApproachPhase> m_7309_() {
      return EnderDragonPhase.f_31379_;
   }

   public void m_7083_() {
      this.f_31254_ = null;
      this.f_31255_ = null;
   }

   public void m_6989_() {
      double d0 = this.f_31255_ == null ? 0.0D : this.f_31255_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
      if (d0 < 100.0D || d0 > 22500.0D || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
         this.m_31263_();
      }

   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31255_;
   }

   private void m_31263_() {
      if (this.f_31254_ == null || this.f_31254_.m_77392_()) {
         int i = this.f_31176_.m_31155_();
         BlockPos blockpos = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
         Player player = this.f_31176_.f_19853_.m_45949_(f_31253_, this.f_31176_, (double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
         int j;
         if (player != null) {
            Vec3 vec3 = (new Vec3(player.m_20185_(), 0.0D, player.m_20189_())).m_82541_();
            j = this.f_31176_.m_31170_(-vec3.f_82479_ * 40.0D, 105.0D, -vec3.f_82481_ * 40.0D);
         } else {
            j = this.f_31176_.m_31170_(40.0D, (double)blockpos.m_123342_(), 0.0D);
         }

         Node node = new Node(blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_());
         this.f_31254_ = this.f_31176_.m_31104_(i, j, node);
         if (this.f_31254_ != null) {
            this.f_31254_.m_77374_();
         }
      }

      this.m_31264_();
      if (this.f_31254_ != null && this.f_31254_.m_77392_()) {
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31380_);
      }

   }

   private void m_31264_() {
      if (this.f_31254_ != null && !this.f_31254_.m_77392_()) {
         Vec3i vec3i = this.f_31254_.m_77400_();
         this.f_31254_.m_77374_();
         double d0 = (double)vec3i.m_123341_();
         double d1 = (double)vec3i.m_123343_();

         double d2;
         do {
            d2 = (double)((float)vec3i.m_123342_() + this.f_31176_.m_21187_().nextFloat() * 20.0F);
         } while(d2 < (double)vec3i.m_123342_());

         this.f_31255_ = new Vec3(d0, d2, d1);
      }

   }
}