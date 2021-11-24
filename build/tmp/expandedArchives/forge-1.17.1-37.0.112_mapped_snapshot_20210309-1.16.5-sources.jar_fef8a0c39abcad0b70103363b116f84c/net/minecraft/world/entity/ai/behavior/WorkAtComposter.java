package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WorkAtComposter extends WorkAtPoi {
   private static final List<Item> f_24786_ = ImmutableList.of(Items.f_42404_, Items.f_42733_);

   protected void m_5628_(ServerLevel p_24790_, Villager p_24791_) {
      Optional<GlobalPos> optional = p_24791_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
      if (optional.isPresent()) {
         GlobalPos globalpos = optional.get();
         BlockState blockstate = p_24790_.m_8055_(globalpos.m_122646_());
         if (blockstate.m_60713_(Blocks.f_50715_)) {
            this.m_24802_(p_24791_);
            this.m_24792_(p_24790_, p_24791_, globalpos, blockstate);
         }

      }
   }

   private void m_24792_(ServerLevel p_24793_, Villager p_24794_, GlobalPos p_24795_, BlockState p_24796_) {
      BlockPos blockpos = p_24795_.m_122646_();
      if (p_24796_.m_61143_(ComposterBlock.f_51913_) == 8) {
         p_24796_ = ComposterBlock.m_51998_(p_24796_, p_24793_, blockpos);
      }

      int i = 20;
      int j = 10;
      int[] aint = new int[f_24786_.size()];
      SimpleContainer simplecontainer = p_24794_.m_141944_();
      int k = simplecontainer.m_6643_();
      BlockState blockstate = p_24796_;

      for(int l = k - 1; l >= 0 && i > 0; --l) {
         ItemStack itemstack = simplecontainer.m_8020_(l);
         int i1 = f_24786_.indexOf(itemstack.m_41720_());
         if (i1 != -1) {
            int j1 = itemstack.m_41613_();
            int k1 = aint[i1] + j1;
            aint[i1] = k1;
            int l1 = Math.min(Math.min(k1 - 10, i), j1);
            if (l1 > 0) {
               i -= l1;

               for(int i2 = 0; i2 < l1; ++i2) {
                  blockstate = ComposterBlock.m_51929_(blockstate, p_24793_, itemstack, blockpos);
                  if (blockstate.m_61143_(ComposterBlock.f_51913_) == 7) {
                     this.m_24797_(p_24793_, p_24796_, blockpos, blockstate);
                     return;
                  }
               }
            }
         }
      }

      this.m_24797_(p_24793_, p_24796_, blockpos, blockstate);
   }

   private void m_24797_(ServerLevel p_24798_, BlockState p_24799_, BlockPos p_24800_, BlockState p_24801_) {
      p_24798_.m_46796_(1500, p_24800_, p_24801_ != p_24799_ ? 1 : 0);
   }

   private void m_24802_(Villager p_24803_) {
      SimpleContainer simplecontainer = p_24803_.m_141944_();
      if (simplecontainer.m_18947_(Items.f_42406_) <= 36) {
         int i = simplecontainer.m_18947_(Items.f_42405_);
         int j = 3;
         int k = 3;
         int l = Math.min(3, i / 3);
         if (l != 0) {
            int i1 = l * 3;
            simplecontainer.m_19170_(Items.f_42405_, i1);
            ItemStack itemstack = simplecontainer.m_19173_(new ItemStack(Items.f_42406_, l));
            if (!itemstack.m_41619_()) {
               p_24803_.m_5552_(itemstack, 0.5F);
            }

         }
      }
   }
}