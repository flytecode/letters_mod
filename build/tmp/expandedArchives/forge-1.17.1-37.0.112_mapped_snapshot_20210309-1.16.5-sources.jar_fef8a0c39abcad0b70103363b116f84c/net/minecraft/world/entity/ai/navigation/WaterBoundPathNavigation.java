package net.minecraft.world.entity.ai.navigation;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WaterBoundPathNavigation extends PathNavigation {
   private boolean f_26592_;

   public WaterBoundPathNavigation(Mob p_26594_, Level p_26595_) {
      super(p_26594_, p_26595_);
   }

   protected PathFinder m_5532_(int p_26598_) {
      this.f_26592_ = this.f_26494_.m_6095_() == EntityType.f_20559_;
      this.f_26508_ = new SwimNodeEvaluator(this.f_26592_);
      return new PathFinder(this.f_26508_, p_26598_);
   }

   protected boolean m_7632_() {
      return this.f_26592_ || this.m_26574_();
   }

   protected Vec3 m_7475_() {
      return new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20227_(0.5D), this.f_26494_.m_20189_());
   }

   public void m_7638_() {
      ++this.f_26498_;
      if (this.f_26506_) {
         this.m_26569_();
      }

      if (!this.m_26571_()) {
         if (this.m_7632_()) {
            this.m_7636_();
         } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            Vec3 vec3 = this.f_26496_.m_77380_(this.f_26494_);
            if (this.f_26494_.m_146903_() == Mth.m_14107_(vec3.f_82479_) && this.f_26494_.m_146904_() == Mth.m_14107_(vec3.f_82480_) && this.f_26494_.m_146907_() == Mth.m_14107_(vec3.f_82481_)) {
               this.f_26496_.m_77374_();
            }
         }

         DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
         if (!this.m_26571_()) {
            Vec3 vec31 = this.f_26496_.m_77380_(this.f_26494_);
            this.f_26494_.m_21566_().m_6849_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, this.f_26497_);
         }
      }
   }

   protected void m_7636_() {
      if (this.f_26496_ != null) {
         Vec3 vec3 = this.m_7475_();
         float f = this.f_26494_.m_20205_();
         float f1 = f > 0.75F ? f / 2.0F : 0.75F - f / 2.0F;
         Vec3 vec31 = this.f_26494_.m_20184_();
         if (Math.abs(vec31.f_82479_) > 0.2D || Math.abs(vec31.f_82481_) > 0.2D) {
            f1 = (float)((double)f1 * vec31.m_82553_() * 6.0D);
         }

         int i = 6;
         Vec3 vec32 = Vec3.m_82539_(this.f_26496_.m_77400_());
         if (Math.abs(this.f_26494_.m_20185_() - vec32.f_82479_) < (double)f1 && Math.abs(this.f_26494_.m_20189_() - vec32.f_82481_) < (double)f1 && Math.abs(this.f_26494_.m_20186_() - vec32.f_82480_) < (double)(f1 * 2.0F)) {
            this.f_26496_.m_77374_();
         }

         for(int j = Math.min(this.f_26496_.m_77399_() + 6, this.f_26496_.m_77398_() - 1); j > this.f_26496_.m_77399_(); --j) {
            vec32 = this.f_26496_.m_77382_(this.f_26494_, j);
            if (!(vec32.m_82557_(vec3) > 36.0D) && this.m_6454_(vec3, vec32, 0, 0, 0)) {
               this.f_26496_.m_77393_(j);
               break;
            }
         }

         this.m_6481_(vec3);
      }
   }

   protected void m_6481_(Vec3 p_26600_) {
      if (this.f_26498_ - this.f_26499_ > 100) {
         if (p_26600_.m_82557_(this.f_26500_) < 2.25D) {
            this.m_26573_();
         }

         this.f_26499_ = this.f_26498_;
         this.f_26500_ = p_26600_;
      }

      if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
         Vec3i vec3i = this.f_26496_.m_77400_();
         if (vec3i.equals(this.f_26501_)) {
            this.f_26502_ += Util.m_137550_() - this.f_26503_;
         } else {
            this.f_26501_ = vec3i;
            double d0 = p_26600_.m_82554_(Vec3.m_82512_(this.f_26501_));
            this.f_26504_ = this.f_26494_.m_6113_() > 0.0F ? d0 / (double)this.f_26494_.m_6113_() * 100.0D : 0.0D;
         }

         if (this.f_26504_ > 0.0D && (double)this.f_26502_ > this.f_26504_ * 2.0D) {
            this.f_26501_ = Vec3i.f_123288_;
            this.f_26502_ = 0L;
            this.f_26504_ = 0.0D;
            this.m_26573_();
         }

         this.f_26503_ = Util.m_137550_();
      }

   }

   protected boolean m_6454_(Vec3 p_26602_, Vec3 p_26603_, int p_26604_, int p_26605_, int p_26606_) {
      Vec3 vec3 = new Vec3(p_26603_.f_82479_, p_26603_.f_82480_ + (double)this.f_26494_.m_20206_() * 0.5D, p_26603_.f_82481_);
      return this.f_26495_.m_45547_(new ClipContext(p_26602_, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.f_26494_)).m_6662_() == HitResult.Type.MISS;
   }

   public boolean m_6342_(BlockPos p_26608_) {
      return !this.f_26495_.m_8055_(p_26608_).m_60804_(this.f_26495_, p_26608_);
   }

   public void m_7008_(boolean p_26612_) {
   }
}