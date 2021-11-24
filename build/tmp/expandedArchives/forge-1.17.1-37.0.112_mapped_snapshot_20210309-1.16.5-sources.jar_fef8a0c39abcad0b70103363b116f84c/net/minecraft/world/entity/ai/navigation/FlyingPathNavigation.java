package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class FlyingPathNavigation extends PathNavigation {
   public FlyingPathNavigation(Mob p_26424_, Level p_26425_) {
      super(p_26424_, p_26425_);
   }

   protected PathFinder m_5532_(int p_26428_) {
      this.f_26508_ = new FlyNodeEvaluator();
      this.f_26508_.m_77351_(true);
      return new PathFinder(this.f_26508_, p_26428_);
   }

   protected boolean m_7632_() {
      return this.m_26576_() && this.m_26574_() || !this.f_26494_.m_20159_();
   }

   protected Vec3 m_7475_() {
      return this.f_26494_.m_20182_();
   }

   public Path m_6570_(Entity p_26430_, int p_26431_) {
      return this.m_7864_(p_26430_.m_142538_(), p_26431_);
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

   protected boolean m_6454_(Vec3 p_26433_, Vec3 p_26434_, int p_26435_, int p_26436_, int p_26437_) {
      int i = Mth.m_14107_(p_26433_.f_82479_);
      int j = Mth.m_14107_(p_26433_.f_82480_);
      int k = Mth.m_14107_(p_26433_.f_82481_);
      double d0 = p_26434_.f_82479_ - p_26433_.f_82479_;
      double d1 = p_26434_.f_82480_ - p_26433_.f_82480_;
      double d2 = p_26434_.f_82481_ - p_26433_.f_82481_;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      if (d3 < 1.0E-8D) {
         return false;
      } else {
         double d4 = 1.0D / Math.sqrt(d3);
         d0 = d0 * d4;
         d1 = d1 * d4;
         d2 = d2 * d4;
         double d5 = 1.0D / Math.abs(d0);
         double d6 = 1.0D / Math.abs(d1);
         double d7 = 1.0D / Math.abs(d2);
         double d8 = (double)i - p_26433_.f_82479_;
         double d9 = (double)j - p_26433_.f_82480_;
         double d10 = (double)k - p_26433_.f_82481_;
         if (d0 >= 0.0D) {
            ++d8;
         }

         if (d1 >= 0.0D) {
            ++d9;
         }

         if (d2 >= 0.0D) {
            ++d10;
         }

         d8 = d8 / d0;
         d9 = d9 / d1;
         d10 = d10 / d2;
         int l = d0 < 0.0D ? -1 : 1;
         int i1 = d1 < 0.0D ? -1 : 1;
         int j1 = d2 < 0.0D ? -1 : 1;
         int k1 = Mth.m_14107_(p_26434_.f_82479_);
         int l1 = Mth.m_14107_(p_26434_.f_82480_);
         int i2 = Mth.m_14107_(p_26434_.f_82481_);
         int j2 = k1 - i;
         int k2 = l1 - j;
         int l2 = i2 - k;

         while(j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
            if (d8 < d10 && d8 <= d9) {
               d8 += d5;
               i += l;
               j2 = k1 - i;
            } else if (d9 < d8 && d9 <= d10) {
               d9 += d6;
               j += i1;
               k2 = l1 - j;
            } else {
               d10 += d7;
               k += j1;
               l2 = i2 - k;
            }
         }

         return true;
      }
   }

   public void m_26440_(boolean p_26441_) {
      this.f_26508_.m_77355_(p_26441_);
   }

   public boolean m_148212_() {
      return this.f_26508_.m_77357_();
   }

   public void m_26443_(boolean p_26444_) {
      this.f_26508_.m_77351_(p_26444_);
   }

   public boolean m_148213_() {
      return this.f_26508_.m_77357_();
   }

   public boolean m_6342_(BlockPos p_26439_) {
      return this.f_26495_.m_8055_(p_26439_).m_60634_(this.f_26495_, p_26439_, this.f_26494_);
   }
}