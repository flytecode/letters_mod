package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonTakeoffPhase extends AbstractDragonPhaseInstance {
   private boolean f_31366_;
   private Path f_31367_;
   private Vec3 f_31368_;

   public DragonTakeoffPhase(EnderDragon p_31370_) {
      super(p_31370_);
   }

   public void m_6989_() {
      if (!this.f_31366_ && this.f_31367_ != null) {
         BlockPos blockpos = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
         if (!blockpos.m_123306_(this.f_31176_.m_20182_(), 10.0D)) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
         }
      } else {
         this.f_31366_ = false;
         this.m_31375_();
      }

   }

   public void m_7083_() {
      this.f_31366_ = true;
      this.f_31367_ = null;
      this.f_31368_ = null;
   }

   private void m_31375_() {
      int i = this.f_31176_.m_31155_();
      Vec3 vec3 = this.f_31176_.m_31174_(1.0F);
      int j = this.f_31176_.m_31170_(-vec3.f_82479_ * 40.0D, 105.0D, -vec3.f_82481_ * 40.0D);
      if (this.f_31176_.m_31158_() != null && this.f_31176_.m_31158_().m_64098_() > 0) {
         j = j % 12;
         if (j < 0) {
            j += 12;
         }
      } else {
         j = j - 12;
         j = j & 7;
         j = j + 12;
      }

      this.f_31367_ = this.f_31176_.m_31104_(i, j, (Node)null);
      this.m_31376_();
   }

   private void m_31376_() {
      if (this.f_31367_ != null) {
         this.f_31367_.m_77374_();
         if (!this.f_31367_.m_77392_()) {
            Vec3i vec3i = this.f_31367_.m_77400_();
            this.f_31367_.m_77374_();

            double d0;
            do {
               d0 = (double)((float)vec3i.m_123342_() + this.f_31176_.m_21187_().nextFloat() * 20.0F);
            } while(d0 < (double)vec3i.m_123342_());

            this.f_31368_ = new Vec3((double)vec3i.m_123341_(), d0, (double)vec3i.m_123343_());
         }
      }

   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31368_;
   }

   public EnderDragonPhase<DragonTakeoffPhase> m_7309_() {
      return EnderDragonPhase.f_31381_;
   }
}