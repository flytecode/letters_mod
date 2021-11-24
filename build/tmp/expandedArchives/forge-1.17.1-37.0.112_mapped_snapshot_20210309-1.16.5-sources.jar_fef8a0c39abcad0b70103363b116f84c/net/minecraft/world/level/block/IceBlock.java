package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

public class IceBlock extends HalfTransparentBlock {
   public IceBlock(BlockBehaviour.Properties p_54155_) {
      super(p_54155_);
   }

   public void m_6240_(Level p_54157_, Player p_54158_, BlockPos p_54159_, BlockState p_54160_, @Nullable BlockEntity p_54161_, ItemStack p_54162_) {
      super.m_6240_(p_54157_, p_54158_, p_54159_, p_54160_, p_54161_, p_54162_);
      if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_54162_) == 0) {
         if (p_54157_.m_6042_().m_63951_()) {
            p_54157_.m_7471_(p_54159_, false);
            return;
         }

         Material material = p_54157_.m_8055_(p_54159_.m_7495_()).m_60767_();
         if (material.m_76334_() || material.m_76332_()) {
            p_54157_.m_46597_(p_54159_, Blocks.f_49990_.m_49966_());
         }
      }

   }

   public void m_7455_(BlockState p_54164_, ServerLevel p_54165_, BlockPos p_54166_, Random p_54167_) {
      if (p_54165_.m_45517_(LightLayer.BLOCK, p_54166_) > 11 - p_54164_.m_60739_(p_54165_, p_54166_)) {
         this.m_54168_(p_54164_, p_54165_, p_54166_);
      }

   }

   protected void m_54168_(BlockState p_54169_, Level p_54170_, BlockPos p_54171_) {
      if (p_54170_.m_6042_().m_63951_()) {
         p_54170_.m_7471_(p_54171_, false);
      } else {
         p_54170_.m_46597_(p_54171_, Blocks.f_49990_.m_49966_());
         p_54170_.m_46586_(p_54171_, Blocks.f_49990_, p_54171_);
      }
   }

   public PushReaction m_5537_(BlockState p_54173_) {
      return PushReaction.NORMAL;
   }
}