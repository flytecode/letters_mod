package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.Features;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MossBlock extends Block implements BonemealableBlock {
   public MossBlock(BlockBehaviour.Properties p_153790_) {
      super(p_153790_);
   }

   public boolean m_7370_(BlockGetter p_153797_, BlockPos p_153798_, BlockState p_153799_, boolean p_153800_) {
      return p_153797_.m_8055_(p_153798_.m_7494_()).m_60795_();
   }

   public boolean m_5491_(Level p_153802_, Random p_153803_, BlockPos p_153804_, BlockState p_153805_) {
      return true;
   }

   public void m_7719_(ServerLevel p_153792_, Random p_153793_, BlockPos p_153794_, BlockState p_153795_) {
      Feature.f_159734_.m_142674_(new FeaturePlaceContext<>(p_153792_, p_153792_.m_7726_().m_8481_(), p_153793_, p_153794_.m_7494_(), Features.f_176936_.m_65397_()));
   }
}