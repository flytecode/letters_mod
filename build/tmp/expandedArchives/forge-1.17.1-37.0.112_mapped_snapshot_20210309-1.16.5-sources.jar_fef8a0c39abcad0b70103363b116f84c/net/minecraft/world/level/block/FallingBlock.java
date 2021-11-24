package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class FallingBlock extends Block implements Fallable {
   public FallingBlock(BlockBehaviour.Properties p_53205_) {
      super(p_53205_);
   }

   public void m_6807_(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
      p_53234_.m_6219_().m_5945_(p_53235_, this, this.m_7198_());
   }

   public BlockState m_7417_(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor p_53229_, BlockPos p_53230_, BlockPos p_53231_) {
      p_53229_.m_6219_().m_5945_(p_53230_, this, this.m_7198_());
      return super.m_7417_(p_53226_, p_53227_, p_53228_, p_53229_, p_53230_, p_53231_);
   }

   public void m_7458_(BlockState p_53216_, ServerLevel p_53217_, BlockPos p_53218_, Random p_53219_) {
      if (m_53241_(p_53217_.m_8055_(p_53218_.m_7495_())) && p_53218_.m_123342_() >= p_53217_.m_141937_()) {
         FallingBlockEntity fallingblockentity = new FallingBlockEntity(p_53217_, (double)p_53218_.m_123341_() + 0.5D, (double)p_53218_.m_123342_(), (double)p_53218_.m_123343_() + 0.5D, p_53217_.m_8055_(p_53218_));
         this.m_6788_(fallingblockentity);
         p_53217_.m_7967_(fallingblockentity);
      }
   }

   protected void m_6788_(FallingBlockEntity p_53206_) {
   }

   protected int m_7198_() {
      return 2;
   }

   public static boolean m_53241_(BlockState p_53242_) {
      Material material = p_53242_.m_60767_();
      return p_53242_.m_60795_() || p_53242_.m_60620_(BlockTags.f_13076_) || material.m_76332_() || material.m_76336_();
   }

   public void m_7100_(BlockState p_53221_, Level p_53222_, BlockPos p_53223_, Random p_53224_) {
      if (p_53224_.nextInt(16) == 0) {
         BlockPos blockpos = p_53223_.m_7495_();
         if (m_53241_(p_53222_.m_8055_(blockpos))) {
            double d0 = (double)p_53223_.m_123341_() + p_53224_.nextDouble();
            double d1 = (double)p_53223_.m_123342_() - 0.05D;
            double d2 = (double)p_53223_.m_123343_() + p_53224_.nextDouble();
            p_53222_.m_7106_(new BlockParticleOption(ParticleTypes.f_123814_, p_53221_), d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public int m_6248_(BlockState p_53238_, BlockGetter p_53239_, BlockPos p_53240_) {
      return -16777216;
   }
}