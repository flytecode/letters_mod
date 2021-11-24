package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ShieldItem extends Item {
   public static final int f_151182_ = 5;
   public static final float f_151183_ = 3.0F;
   public static final String f_151184_ = "Base";

   public ShieldItem(Item.Properties p_43089_) {
      super(p_43089_);
      DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
   }

   public String m_5671_(ItemStack p_43109_) {
      return p_43109_.m_41737_("BlockEntityTag") != null ? this.m_5524_() + "." + m_43102_(p_43109_).m_41065_() : super.m_5671_(p_43109_);
   }

   public void m_7373_(ItemStack p_43094_, @Nullable Level p_43095_, List<Component> p_43096_, TooltipFlag p_43097_) {
      BannerItem.m_40542_(p_43094_, p_43096_);
   }

   public UseAnim m_6164_(ItemStack p_43105_) {
      return UseAnim.BLOCK;
   }

   public int m_8105_(ItemStack p_43107_) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43099_, Player p_43100_, InteractionHand p_43101_) {
      ItemStack itemstack = p_43100_.m_21120_(p_43101_);
      p_43100_.m_6672_(p_43101_);
      return InteractionResultHolder.m_19096_(itemstack);
   }

   public boolean m_6832_(ItemStack p_43091_, ItemStack p_43092_) {
      return p_43092_.m_150922_(ItemTags.f_13168_) || super.m_6832_(p_43091_, p_43092_);
   }

   public static DyeColor m_43102_(ItemStack p_43103_) {
      return DyeColor.m_41053_(p_43103_.m_41698_("BlockEntityTag").m_128451_("Base"));
   }

   /* ******************** FORGE START ******************** */

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
   }
}
