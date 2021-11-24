package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ShearsItem extends Item {
   public ShearsItem(Item.Properties p_43074_) {
      super(p_43074_);
   }

   public boolean m_6813_(ItemStack p_43078_, Level p_43079_, BlockState p_43080_, BlockPos p_43081_, LivingEntity p_43082_) {
      if (!p_43079_.f_46443_ && !p_43080_.m_60620_(BlockTags.f_13076_)) {
         p_43078_.m_41622_(1, p_43082_, (p_43076_) -> {
            p_43076_.m_21166_(EquipmentSlot.MAINHAND);
         });
      }

      return !p_43080_.m_60620_(BlockTags.f_13035_) && !p_43080_.m_60713_(Blocks.f_50033_) && !p_43080_.m_60713_(Blocks.f_50034_) && !p_43080_.m_60713_(Blocks.f_50035_) && !p_43080_.m_60713_(Blocks.f_50036_) && !p_43080_.m_60713_(Blocks.f_152548_) && !p_43080_.m_60713_(Blocks.f_50191_) && !p_43080_.m_60713_(Blocks.f_50267_) && !p_43080_.m_60620_(BlockTags.f_13089_) ? super.m_6813_(p_43078_, p_43079_, p_43080_, p_43081_, p_43082_) : true;
   }

   public boolean m_8096_(BlockState p_43087_) {
      return p_43087_.m_60713_(Blocks.f_50033_) || p_43087_.m_60713_(Blocks.f_50088_) || p_43087_.m_60713_(Blocks.f_50267_);
   }

   public float m_8102_(ItemStack p_43084_, BlockState p_43085_) {
      if (!p_43085_.m_60713_(Blocks.f_50033_) && !p_43085_.m_60620_(BlockTags.f_13035_)) {
         if (p_43085_.m_60620_(BlockTags.f_13089_)) {
            return 5.0F;
         } else {
            return !p_43085_.m_60713_(Blocks.f_50191_) && !p_43085_.m_60713_(Blocks.f_152475_) ? super.m_8102_(p_43084_, p_43085_) : 2.0F;
         }
      } else {
         return 15.0F;
      }
   }

   @Override
   public net.minecraft.world.InteractionResult m_6880_(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
      if (entity.f_19853_.f_46443_) return net.minecraft.world.InteractionResult.PASS;
      if (entity instanceof net.minecraftforge.common.IForgeShearable) {
          net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
         BlockPos pos = new BlockPos(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         if (target.isShearable(stack, entity.f_19853_, pos)) {
            java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.f_19853_, pos,
                    net.minecraft.world.item.enchantment.EnchantmentHelper.m_44843_(net.minecraft.world.item.enchantment.Enchantments.f_44987_, stack));
            java.util.Random rand = new java.util.Random();
            drops.forEach(d -> {
               net.minecraft.world.entity.item.ItemEntity ent = entity.m_5552_(d, 1.0F);
               ent.m_20256_(ent.m_20184_().m_82520_((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
            });
            stack.m_41622_(1, entity, e -> e.m_21190_(hand));
         }
         return net.minecraft.world.InteractionResult.SUCCESS;
      }
      return net.minecraft.world.InteractionResult.PASS;
   }

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_SHEARS_ACTIONS.contains(toolAction);
   }
}
