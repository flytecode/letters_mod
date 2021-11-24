package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class GroundPathNavigation extends PathNavigation {
   private boolean f_26446_;

   public GroundPathNavigation(Mob p_26448_, Level p_26449_) {
      super(p_26448_, p_26449_);
   }

   protected PathFinder m_5532_(int p_26453_) {
      this.f_26508_ = new WalkNodeEvaluator();
      this.f_26508_.m_77351_(true);
      return new PathFinder(this.f_26508_, p_26453_);
   }

   protected boolean m_7632_() {
      return this.f_26494_.m_20096_() || this.m_26574_() || this.f_26494_.m_20159_();
   }

   protected Vec3 m_7475_() {
      return new Vec3(this.f_26494_.m_20185_(), (double)this.m_26493_(), this.f_26494_.m_20189_());
   }

   public Path m_7864_(BlockPos p_26475_, int p_26476_) {
      if (this.f_26495_.m_8055_(p_26475_).m_60795_()) {
         BlockPos blockpos;
         for(blockpos = p_26475_.m_7495_(); blockpos.m_123342_() > this.f_26495_.m_141937_() && this.f_26495_.m_8055_(blockpos).m_60795_(); blockpos = blockpos.m_7495_()) {
         }

         if (blockpos.m_123342_() > this.f_26495_.m_141937_()) {
            return super.m_7864_(blockpos.m_7494_(), p_26476_);
         }

         while(blockpos.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_(blockpos).m_60795_()) {
            blockpos = blockpos.m_7494_();
         }

         p_26475_ = blockpos;
      }

      if (!this.f_26495_.m_8055_(p_26475_).m_60767_().m_76333_()) {
         return super.m_7864_(p_26475_, p_26476_);
      } else {
         BlockPos blockpos1;
         for(blockpos1 = p_26475_.m_7494_(); blockpos1.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_(blockpos1).m_60767_().m_76333_(); blockpos1 = blockpos1.m_7494_()) {
         }

         return super.m_7864_(blockpos1, p_26476_);
      }
   }

   public Path m_6570_(Entity p_26465_, int p_26466_) {
      return this.m_7864_(p_26465_.m_142538_(), p_26466_);
   }

   private int m_26493_() {
      if (this.f_26494_.m_20069_() && this.m_26576_()) {
         int i = this.f_26494_.m_146904_();
         BlockState blockstate = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_20185_(), (double)i, this.f_26494_.m_20189_()));
         int j = 0;

         while(blockstate.m_60713_(Blocks.f_49990_)) {
            ++i;
            blockstate = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_20185_(), (double)i, this.f_26494_.m_20189_()));
            ++j;
            if (j > 16) {
               return this.f_26494_.m_146904_();
            }
         }

         return i;
      } else {
         return Mth.m_14107_(this.f_26494_.m_20186_() + 0.5D);
      }
   }

   protected void m_6804_() {
      super.m_6804_();
      if (this.f_26446_) {
         if (this.f_26495_.m_45527_(new BlockPos(this.f_26494_.m_20185_(), this.f_26494_.m_20186_() + 0.5D, this.f_26494_.m_20189_()))) {
            return;
         }

         for(int i = 0; i < this.f_26496_.m_77398_(); ++i) {
            Node node = this.f_26496_.m_77375_(i);
            if (this.f_26495_.m_45527_(new BlockPos(node.f_77271_, node.f_77272_, node.f_77273_))) {
               this.f_26496_.m_77388_(i);
               return;
            }
         }
      }

   }

   protected boolean m_6454_(Vec3 p_26469_, Vec3 p_26470_, int p_26471_, int p_26472_, int p_26473_) {
      int i = Mth.m_14107_(p_26469_.f_82479_);
      int j = Mth.m_14107_(p_26469_.f_82481_);
      double d0 = p_26470_.f_82479_ - p_26469_.f_82479_;
      double d1 = p_26470_.f_82481_ - p_26469_.f_82481_;
      double d2 = d0 * d0 + d1 * d1;
      if (d2 < 1.0E-8D) {
         return false;
      } else {
         double d3 = 1.0D / Math.sqrt(d2);
         d0 = d0 * d3;
         d1 = d1 * d3;
         p_26471_ = p_26471_ + 2;
         p_26473_ = p_26473_ + 2;
         if (!this.m_26454_(i, Mth.m_14107_(p_26469_.f_82480_), j, p_26471_, p_26472_, p_26473_, p_26469_, d0, d1)) {
            return false;
         } else {
            p_26471_ = p_26471_ - 2;
            p_26473_ = p_26473_ - 2;
            double d4 = 1.0D / Math.abs(d0);
            double d5 = 1.0D / Math.abs(d1);
            double d6 = (double)i - p_26469_.f_82479_;
            double d7 = (double)j - p_26469_.f_82481_;
            if (d0 >= 0.0D) {
               ++d6;
            }

            if (d1 >= 0.0D) {
               ++d7;
            }

            d6 = d6 / d0;
            d7 = d7 / d1;
            int k = d0 < 0.0D ? -1 : 1;
            int l = d1 < 0.0D ? -1 : 1;
            int i1 = Mth.m_14107_(p_26470_.f_82479_);
            int j1 = Mth.m_14107_(p_26470_.f_82481_);
            int k1 = i1 - i;
            int l1 = j1 - j;

            while(k1 * k > 0 || l1 * l > 0) {
               if (d6 < d7) {
                  d6 += d4;
                  i += k;
                  k1 = i1 - i;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }

               if (!this.m_26454_(i, Mth.m_14107_(p_26469_.f_82480_), j, p_26471_, p_26472_, p_26473_, p_26469_, d0, d1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean m_26454_(int p_26455_, int p_26456_, int p_26457_, int p_26458_, int p_26459_, int p_26460_, Vec3 p_26461_, double p_26462_, double p_26463_) {
      int i = p_26455_ - p_26458_ / 2;
      int j = p_26457_ - p_26460_ / 2;
      if (!this.m_26480_(i, p_26456_, j, p_26458_, p_26459_, p_26460_, p_26461_, p_26462_, p_26463_)) {
         return false;
      } else {
         for(int k = i; k < i + p_26458_; ++k) {
            for(int l = j; l < j + p_26460_; ++l) {
               double d0 = (double)k + 0.5D - p_26461_.f_82479_;
               double d1 = (double)l + 0.5D - p_26461_.f_82481_;
               if (!(d0 * p_26462_ + d1 * p_26463_ < 0.0D)) {
                  BlockPathTypes blockpathtypes = this.f_26508_.m_7209_(this.f_26495_, k, p_26456_ - 1, l, this.f_26494_, p_26458_, p_26459_, p_26460_, true, true);
                  if (!this.m_7367_(blockpathtypes)) {
                     return false;
                  }

                  blockpathtypes = this.f_26508_.m_7209_(this.f_26495_, k, p_26456_, l, this.f_26494_, p_26458_, p_26459_, p_26460_, true, true);
                  float f = this.f_26494_.m_21439_(blockpathtypes);
                  if (f < 0.0F || f >= 8.0F) {
                     return false;
                  }

                  if (blockpathtypes == BlockPathTypes.DAMAGE_FIRE || blockpathtypes == BlockPathTypes.DANGER_FIRE || blockpathtypes == BlockPathTypes.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   protected boolean m_7367_(BlockPathTypes p_26467_) {
      if (p_26467_ == BlockPathTypes.WATER) {
         return false;
      } else if (p_26467_ == BlockPathTypes.LAVA) {
         return false;
      } else {
         return p_26467_ != BlockPathTypes.OPEN;
      }
   }

   private boolean m_26480_(int p_26481_, int p_26482_, int p_26483_, int p_26484_, int p_26485_, int p_26486_, Vec3 p_26487_, double p_26488_, double p_26489_) {
      for(BlockPos blockpos : BlockPos.m_121940_(new BlockPos(p_26481_, p_26482_, p_26483_), new BlockPos(p_26481_ + p_26484_ - 1, p_26482_ + p_26485_ - 1, p_26483_ + p_26486_ - 1))) {
         double d0 = (double)blockpos.m_123341_() + 0.5D - p_26487_.f_82479_;
         double d1 = (double)blockpos.m_123343_() + 0.5D - p_26487_.f_82481_;
         if (!(d0 * p_26488_ + d1 * p_26489_ < 0.0D) && !this.f_26495_.m_8055_(blockpos).m_60647_(this.f_26495_, blockpos, PathComputationType.LAND)) {
            return false;
         }
      }

      return true;
   }

   public void m_26477_(boolean p_26478_) {
      this.f_26508_.m_77355_(p_26478_);
   }

   public boolean m_148216_() {
      return this.f_26508_.m_77357_();
   }

   public void m_148214_(boolean p_148215_) {
      this.f_26508_.m_77351_(p_148215_);
   }

   public boolean m_26492_() {
      return this.f_26508_.m_77357_();
   }

   public void m_26490_(boolean p_26491_) {
      this.f_26446_ = p_26491_;
   }
}