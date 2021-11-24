package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

public class EnchantedBookItem extends Item {
   public static final String f_150830_ = "StoredEnchantments";

   public EnchantedBookItem(Item.Properties p_41149_) {
      super(p_41149_);
   }

   public boolean m_5812_(ItemStack p_41166_) {
      return true;
   }

   public boolean m_8120_(ItemStack p_41168_) {
      return false;
   }

   public static ListTag m_41163_(ItemStack p_41164_) {
      CompoundTag compoundtag = p_41164_.m_41783_();
      return compoundtag != null ? compoundtag.m_128437_("StoredEnchantments", 10) : new ListTag();
   }

   public void m_7373_(ItemStack p_41157_, @Nullable Level p_41158_, List<Component> p_41159_, TooltipFlag p_41160_) {
      super.m_7373_(p_41157_, p_41158_, p_41159_, p_41160_);
      ItemStack.m_41709_(p_41159_, m_41163_(p_41157_));
   }

   public static void m_41153_(ItemStack p_41154_, EnchantmentInstance p_41155_) {
      ListTag listtag = m_41163_(p_41154_);
      boolean flag = true;
      ResourceLocation resourcelocation = EnchantmentHelper.m_182432_(p_41155_.f_44947_);

      for(int i = 0; i < listtag.size(); ++i) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         ResourceLocation resourcelocation1 = EnchantmentHelper.m_182446_(compoundtag);
         if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
            if (EnchantmentHelper.m_182438_(compoundtag) < p_41155_.f_44948_) {
               EnchantmentHelper.m_182440_(compoundtag, p_41155_.f_44948_);
            }

            flag = false;
            break;
         }
      }

      if (flag) {
         listtag.add(EnchantmentHelper.m_182443_(resourcelocation, p_41155_.f_44948_));
      }

      p_41154_.m_41784_().m_128365_("StoredEnchantments", listtag);
   }

   public static ItemStack m_41161_(EnchantmentInstance p_41162_) {
      ItemStack itemstack = new ItemStack(Items.f_42690_);
      m_41153_(itemstack, p_41162_);
      return itemstack;
   }

   public void m_6787_(CreativeModeTab p_41151_, NonNullList<ItemStack> p_41152_) {
      if (p_41151_ == CreativeModeTab.f_40754_) {
         for(Enchantment enchantment : Registry.f_122825_) {
            if (enchantment.f_44672_ != null) {
               for(int i = enchantment.m_44702_(); i <= enchantment.m_6586_(); ++i) {
                  p_41152_.add(m_41161_(new EnchantmentInstance(enchantment, i)));
               }
            }
         }
      } else if (p_41151_.m_40795_().length != 0) {
         for(Enchantment enchantment1 : Registry.f_122825_) {
            if (p_41151_.m_40776_(enchantment1.f_44672_)) {
               p_41152_.add(m_41161_(new EnchantmentInstance(enchantment1, enchantment1.m_6586_())));
            }
         }
      }

   }
}