package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class CoralBlock extends Block {
   private final Block f_52128_;

   public CoralBlock(Block p_52130_, BlockBehaviour.Properties p_52131_) {
      super(p_52131_);
      this.f_52128_ = p_52130_;
   }

   public void m_7458_(BlockState p_52138_, ServerLevel p_52139_, BlockPos p_52140_, Random p_52141_) {
      if (!this.m_52134_(p_52139_, p_52140_)) {
         p_52139_.m_7731_(p_52140_, this.f_52128_.m_49966_(), 2);
      }

   }

   public BlockState m_7417_(BlockState p_52143_, Direction p_52144_, BlockState p_52145_, LevelAccessor p_52146_, BlockPos p_52147_, BlockPos p_52148_) {
      if (!this.m_52134_(p_52146_, p_52147_)) {
         p_52146_.m_6219_().m_5945_(p_52147_, this, 60 + p_52146_.m_5822_().nextInt(40));
      }

      return super.m_7417_(p_52143_, p_52144_, p_52145_, p_52146_, p_52147_, p_52148_);
   }

   protected boolean m_52134_(BlockGetter p_52135_, BlockPos p_52136_) {
      for(Direction direction : Direction.values()) {
         FluidState fluidstate = p_52135_.m_6425_(p_52136_.m_142300_(direction));
         if (fluidstate.m_76153_(FluidTags.f_13131_)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_52133_) {
      if (!this.m_52134_(p_52133_.m_43725_(), p_52133_.m_8083_())) {
         p_52133_.m_43725_().m_6219_().m_5945_(p_52133_.m_8083_(), this, 60 + p_52133_.m_43725_().m_5822_().nextInt(40));
      }

      return this.m_49966_();
   }
}