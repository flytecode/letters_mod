package net.minecraft.world.entity.ai.goal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;

public class RemoveBlockGoal extends MoveToBlockGoal {
   private final Block f_25836_;
   private final Mob f_25837_;
   private int f_25838_;
   private static final int f_148135_ = 20;

   public RemoveBlockGoal(Block p_25840_, PathfinderMob p_25841_, double p_25842_, int p_25843_) {
      super(p_25841_, p_25842_, 24, p_25843_);
      this.f_25836_ = p_25840_;
      this.f_25837_ = p_25841_;
   }

   public boolean m_8036_() {
      if (!net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.f_25837_.f_19853_, this.f_25602_, this.f_25837_)) {
         return false;
      } else if (this.f_25600_ > 0) {
         --this.f_25600_;
         return false;
      } else if (this.m_25858_()) {
         this.f_25600_ = 20;
         return true;
      } else {
         this.f_25600_ = this.m_6099_(this.f_25598_);
         return false;
      }
   }

   private boolean m_25858_() {
      return this.f_25602_ != null && this.m_6465_(this.f_25598_.f_19853_, this.f_25602_) ? true : this.m_25626_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.f_25837_.f_19789_ = 1.0F;
   }

   public void m_8056_() {
      super.m_8056_();
      this.f_25838_ = 0;
   }

   public void m_7659_(LevelAccessor p_25847_, BlockPos p_25848_) {
   }

   public void m_5777_(Level p_25845_, BlockPos p_25846_) {
   }

   public void m_8037_() {
      super.m_8037_();
      Level level = this.f_25837_.f_19853_;
      BlockPos blockpos = this.f_25837_.m_142538_();
      BlockPos blockpos1 = this.m_25852_(blockpos, level);
      Random random = this.f_25837_.m_21187_();
      if (this.m_25625_() && blockpos1 != null) {
         if (this.f_25838_ > 0) {
            Vec3 vec3 = this.f_25837_.m_20184_();
            this.f_25837_.m_20334_(vec3.f_82479_, 0.3D, vec3.f_82481_);
            if (!level.f_46443_) {
               double d0 = 0.08D;
               ((ServerLevel)level).m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42521_)), (double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.7D, (double)blockpos1.m_123343_() + 0.5D, 3, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, (double)0.15F);
            }
         }

         if (this.f_25838_ % 2 == 0) {
            Vec3 vec31 = this.f_25837_.m_20184_();
            this.f_25837_.m_20334_(vec31.f_82479_, -0.3D, vec31.f_82481_);
            if (this.f_25838_ % 6 == 0) {
               this.m_7659_(level, this.f_25602_);
            }
         }

         if (this.f_25838_ > 60) {
            level.m_7471_(blockpos1, false);
            if (!level.f_46443_) {
               for(int i = 0; i < 20; ++i) {
                  double d3 = random.nextGaussian() * 0.02D;
                  double d1 = random.nextGaussian() * 0.02D;
                  double d2 = random.nextGaussian() * 0.02D;
                  ((ServerLevel)level).m_8767_(ParticleTypes.f_123759_, (double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_() + 0.5D, 1, d3, d1, d2, (double)0.15F);
               }

               this.m_5777_(level, blockpos1);
            }
         }

         ++this.f_25838_;
      }

   }

   @Nullable
   private BlockPos m_25852_(BlockPos p_25853_, BlockGetter p_25854_) {
      if (p_25854_.m_8055_(p_25853_).m_60713_(this.f_25836_)) {
         return p_25853_;
      } else {
         BlockPos[] ablockpos = new BlockPos[]{p_25853_.m_7495_(), p_25853_.m_142125_(), p_25853_.m_142126_(), p_25853_.m_142127_(), p_25853_.m_142128_(), p_25853_.m_7495_().m_7495_()};

         for(BlockPos blockpos : ablockpos) {
            if (p_25854_.m_8055_(blockpos).m_60713_(this.f_25836_)) {
               return blockpos;
            }
         }

         return null;
      }
   }

   protected boolean m_6465_(LevelReader p_25850_, BlockPos p_25851_) {
      ChunkAccess chunkaccess = p_25850_.m_6522_(SectionPos.m_123171_(p_25851_.m_123341_()), SectionPos.m_123171_(p_25851_.m_123343_()), ChunkStatus.f_62326_, false);
      if (chunkaccess == null) {
         return false;
      } else {
         return chunkaccess.m_8055_(p_25851_).m_60713_(this.f_25836_) && chunkaccess.m_8055_(p_25851_.m_7494_()).m_60795_() && chunkaccess.m_8055_(p_25851_.m_6630_(2)).m_60795_();
      }
   }
}
