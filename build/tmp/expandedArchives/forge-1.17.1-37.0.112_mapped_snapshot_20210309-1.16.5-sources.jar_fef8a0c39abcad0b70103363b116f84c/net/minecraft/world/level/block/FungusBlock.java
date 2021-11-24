package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FungusBlock extends BushBlock implements BonemealableBlock {
   protected static final VoxelShape f_53596_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
   private static final double f_153271_ = 0.4D;
   private final Supplier<ConfiguredFeature<HugeFungusConfiguration, ?>> f_53597_;

   public FungusBlock(BlockBehaviour.Properties p_53600_, Supplier<ConfiguredFeature<HugeFungusConfiguration, ?>> p_53601_) {
      super(p_53600_);
      this.f_53597_ = p_53601_;
   }

   public VoxelShape m_5940_(BlockState p_53618_, BlockGetter p_53619_, BlockPos p_53620_, CollisionContext p_53621_) {
      return f_53596_;
   }

   protected boolean m_6266_(BlockState p_53623_, BlockGetter p_53624_, BlockPos p_53625_) {
      return p_53623_.m_60620_(BlockTags.f_13077_) || p_53623_.m_60713_(Blocks.f_50195_) || p_53623_.m_60713_(Blocks.f_50136_) || super.m_6266_(p_53623_, p_53624_, p_53625_);
   }

   public boolean m_7370_(BlockGetter p_53608_, BlockPos p_53609_, BlockState p_53610_, boolean p_53611_) {
      Block block = ((HugeFungusConfiguration)(this.f_53597_.get()).f_65378_).f_65897_.m_60734_();
      BlockState blockstate = p_53608_.m_8055_(p_53609_.m_7495_());
      return blockstate.m_60713_(block);
   }

   public boolean m_5491_(Level p_53613_, Random p_53614_, BlockPos p_53615_, BlockState p_53616_) {
      return (double)p_53614_.nextFloat() < 0.4D;
   }

   public void m_7719_(ServerLevel p_53603_, Random p_53604_, BlockPos p_53605_, BlockState p_53606_) {
      this.f_53597_.get().m_65385_(p_53603_, p_53603_.m_7726_().m_8481_(), p_53604_, p_53605_);
   }
}