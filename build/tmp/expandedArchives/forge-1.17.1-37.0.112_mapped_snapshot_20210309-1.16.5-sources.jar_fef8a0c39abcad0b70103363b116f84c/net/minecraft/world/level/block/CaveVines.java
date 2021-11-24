package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CaveVines {
   VoxelShape f_152948_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
   BooleanProperty f_152949_ = BlockStateProperties.f_155977_;

   static InteractionResult m_152953_(BlockState p_152954_, Level p_152955_, BlockPos p_152956_) {
      if (p_152954_.m_61143_(f_152949_)) {
         Block.m_49840_(p_152955_, p_152956_, new ItemStack(Items.f_151079_, 1));
         float f = Mth.m_144924_(p_152955_.f_46441_, 0.8F, 1.2F);
         p_152955_.m_5594_((Player)null, p_152956_, SoundEvents.f_144088_, SoundSource.BLOCKS, 1.0F, f);
         p_152955_.m_7731_(p_152956_, p_152954_.m_61124_(f_152949_, Boolean.valueOf(false)), 2);
         return InteractionResult.m_19078_(p_152955_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   static boolean m_152951_(BlockState p_152952_) {
      return p_152952_.m_61138_(f_152949_) && p_152952_.m_61143_(f_152949_);
   }

   static ToIntFunction<BlockState> m_181217_(int p_181218_) {
      return (p_181216_) -> {
         return p_181216_.m_61143_(BlockStateProperties.f_155977_) ? p_181218_ : 0;
      };
   }
}