package net.minecraft.world.item.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FrostWalkerEnchantment extends Enchantment {
   public FrostWalkerEnchantment(Enchantment.Rarity p_45013_, EquipmentSlot... p_45014_) {
      super(p_45013_, EnchantmentCategory.ARMOR_FEET, p_45014_);
   }

   public int m_6183_(int p_45017_) {
      return p_45017_ * 10;
   }

   public int m_6175_(int p_45027_) {
      return this.m_6183_(p_45027_) + 15;
   }

   public boolean m_6591_() {
      return true;
   }

   public int m_6586_() {
      return 2;
   }

   public static void m_45018_(LivingEntity p_45019_, Level p_45020_, BlockPos p_45021_, int p_45022_) {
      if (p_45019_.m_20096_()) {
         BlockState blockstate = Blocks.f_50449_.m_49966_();
         float f = (float)Math.min(16, 2 + p_45022_);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(BlockPos blockpos : BlockPos.m_121940_(p_45021_.m_142022_((double)(-f), -1.0D, (double)(-f)), p_45021_.m_142022_((double)f, -1.0D, (double)f))) {
            if (blockpos.m_123306_(p_45019_.m_20182_(), (double)f)) {
               blockpos$mutableblockpos.m_122178_(blockpos.m_123341_(), blockpos.m_123342_() + 1, blockpos.m_123343_());
               BlockState blockstate1 = p_45020_.m_8055_(blockpos$mutableblockpos);
               if (blockstate1.m_60795_()) {
                  BlockState blockstate2 = p_45020_.m_8055_(blockpos);
                  boolean isFull = blockstate2.m_60734_() == Blocks.f_49990_ && blockstate2.m_61143_(LiquidBlock.f_54688_) == 0; //TODO: Forge, modded waters?
                  if (blockstate2.m_60767_() == Material.f_76305_ && isFull && blockstate.m_60710_(p_45020_, blockpos) && p_45020_.m_45752_(blockstate, blockpos, CollisionContext.m_82749_()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(p_45019_, net.minecraftforge.common.util.BlockSnapshot.create(p_45020_.m_46472_(), p_45020_, blockpos), net.minecraft.core.Direction.UP)) {
                     p_45020_.m_46597_(blockpos, blockstate);
                     p_45020_.m_6219_().m_5945_(blockpos, Blocks.f_50449_, Mth.m_14072_(p_45019_.m_21187_(), 60, 120));
                  }
               }
            }
         }

      }
   }

   public boolean m_5975_(Enchantment p_45024_) {
      return super.m_5975_(p_45024_) && p_45024_ != Enchantments.f_44973_;
   }
}
