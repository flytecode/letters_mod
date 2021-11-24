package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class OreBlock extends Block {
   private final UniformInt f_153990_;

   public OreBlock(BlockBehaviour.Properties p_55140_) {
      this(p_55140_, UniformInt.m_146622_(0, 0));
   }

   public OreBlock(BlockBehaviour.Properties p_153992_, UniformInt p_153993_) {
      super(p_153992_);
      this.f_153990_ = p_153993_;
   }

   public void m_8101_(BlockState p_55142_, ServerLevel p_55143_, BlockPos p_55144_, ItemStack p_55145_) {
      super.m_8101_(p_55142_, p_55143_, p_55144_, p_55145_);
   }

   @Override
   public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader reader, BlockPos pos, int fortune, int silktouch) {
      return silktouch == 0 ? this.f_153990_.m_142270_(RANDOM) : 0;
   }
}
