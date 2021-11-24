package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class WaterAnimal extends PathfinderMob {
   protected WaterAnimal(EntityType<? extends WaterAnimal> p_30341_, Level p_30342_) {
      super(p_30341_, p_30342_);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public boolean m_6040_() {
      return true;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_6914_(LevelReader p_30348_) {
      return p_30348_.m_45784_(this);
   }

   public int m_8100_() {
      return 120;
   }

   protected int m_6552_(Player p_30353_) {
      return 1 + this.f_19853_.f_46441_.nextInt(3);
   }

   protected void m_6229_(int p_30344_) {
      if (this.m_6084_() && !this.m_20072_()) {
         this.m_20301_(p_30344_ - 1);
         if (this.m_20146_() == -20) {
            this.m_20301_(0);
            this.m_6469_(DamageSource.f_19312_, 2.0F);
         }
      } else {
         this.m_20301_(300);
      }

   }

   public void m_6075_() {
      int i = this.m_20146_();
      super.m_6075_();
      this.m_6229_(i);
   }

   public boolean m_6063_() {
      return false;
   }

   public boolean m_6573_(Player p_30346_) {
      return false;
   }

   public static boolean m_149076_(EntityType<? extends LivingEntity> p_149077_, ServerLevelAccessor p_149078_, MobSpawnType p_149079_, BlockPos p_149080_, Random p_149081_) {
      return p_149080_.m_123342_() < p_149078_.m_5736_() && p_149080_.m_123342_() < p_149078_.m_6924_(Heightmap.Types.OCEAN_FLOOR, p_149080_.m_123341_(), p_149080_.m_123343_()) && m_181141_(p_149078_, p_149080_) && m_181144_(p_149080_, p_149078_);
   }

   public static boolean m_181144_(BlockPos p_181145_, ServerLevelAccessor p_181146_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_181145_.m_122032_();

      for(int i = 0; i < 5; ++i) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         BlockState blockstate = p_181146_.m_8055_(blockpos$mutableblockpos);
         if (blockstate.m_60620_(BlockTags.f_13061_)) {
            return true;
         }

         if (!blockstate.m_60713_(Blocks.f_49990_)) {
            return false;
         }
      }

      return false;
   }

   public static boolean m_181141_(ServerLevelAccessor p_181142_, BlockPos p_181143_) {
      int i = p_181142_.m_6018_().m_46470_() ? p_181142_.m_46849_(p_181143_, 10) : p_181142_.m_46803_(p_181143_);
      return i == 0;
   }
}