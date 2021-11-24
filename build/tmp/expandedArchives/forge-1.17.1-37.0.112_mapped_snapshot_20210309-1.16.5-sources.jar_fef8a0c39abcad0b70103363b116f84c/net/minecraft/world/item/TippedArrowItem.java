package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class TippedArrowItem extends ArrowItem {
   public TippedArrowItem(Item.Properties p_43354_) {
      super(p_43354_);
   }

   public ItemStack m_7968_() {
      return PotionUtils.m_43549_(super.m_7968_(), Potions.f_43584_);
   }

   public void m_6787_(CreativeModeTab p_43356_, NonNullList<ItemStack> p_43357_) {
      if (this.m_41389_(p_43356_)) {
         for(Potion potion : Registry.f_122828_) {
            if (!potion.m_43488_().isEmpty()) {
               p_43357_.add(PotionUtils.m_43549_(new ItemStack(this), potion));
            }
         }
      }

   }

   public void m_7373_(ItemStack p_43359_, @Nullable Level p_43360_, List<Component> p_43361_, TooltipFlag p_43362_) {
      PotionUtils.m_43555_(p_43359_, p_43361_, 0.125F);
   }

   public String m_5671_(ItemStack p_43364_) {
      return PotionUtils.m_43579_(p_43364_).m_43492_(this.m_5524_() + ".effect.");
   }
}