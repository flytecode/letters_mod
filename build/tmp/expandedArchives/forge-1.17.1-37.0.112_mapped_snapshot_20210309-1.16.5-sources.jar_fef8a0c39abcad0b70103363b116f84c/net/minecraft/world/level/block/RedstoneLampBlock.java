package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneLampBlock extends Block {
   public static final BooleanProperty f_55654_ = RedstoneTorchBlock.f_55674_;

   public RedstoneLampBlock(BlockBehaviour.Properties p_55657_) {
      super(p_55657_);
      this.m_49959_(this.m_49966_().m_61124_(f_55654_, Boolean.valueOf(false)));
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_55659_) {
      return this.m_49966_().m_61124_(f_55654_, Boolean.valueOf(p_55659_.m_43725_().m_46753_(p_55659_.m_8083_())));
   }

   public void m_6861_(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
      if (!p_55667_.f_46443_) {
         boolean flag = p_55666_.m_61143_(f_55654_);
         if (flag != p_55667_.m_46753_(p_55668_)) {
            if (flag) {
               p_55667_.m_6219_().m_5945_(p_55668_, this, 4);
            } else {
               p_55667_.m_7731_(p_55668_, p_55666_.m_61122_(f_55654_), 2);
            }
         }

      }
   }

   public void m_7458_(BlockState p_55661_, ServerLevel p_55662_, BlockPos p_55663_, Random p_55664_) {
      if (p_55661_.m_61143_(f_55654_) && !p_55662_.m_46753_(p_55663_)) {
         p_55662_.m_7731_(p_55663_, p_55661_.m_61122_(f_55654_), 2);
      }

   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55673_) {
      p_55673_.m_61104_(f_55654_);
   }
}