package net.minecraft.world;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class ContainerHelper {
   public static ItemStack m_18969_(List<ItemStack> p_18970_, int p_18971_, int p_18972_) {
      return p_18971_ >= 0 && p_18971_ < p_18970_.size() && !p_18970_.get(p_18971_).m_41619_() && p_18972_ > 0 ? p_18970_.get(p_18971_).m_41620_(p_18972_) : ItemStack.f_41583_;
   }

   public static ItemStack m_18966_(List<ItemStack> p_18967_, int p_18968_) {
      return p_18968_ >= 0 && p_18968_ < p_18967_.size() ? p_18967_.set(p_18968_, ItemStack.f_41583_) : ItemStack.f_41583_;
   }

   public static CompoundTag m_18973_(CompoundTag p_18974_, NonNullList<ItemStack> p_18975_) {
      return m_18976_(p_18974_, p_18975_, true);
   }

   public static CompoundTag m_18976_(CompoundTag p_18977_, NonNullList<ItemStack> p_18978_, boolean p_18979_) {
      ListTag listtag = new ListTag();

      for(int i = 0; i < p_18978_.size(); ++i) {
         ItemStack itemstack = p_18978_.get(i);
         if (!itemstack.m_41619_()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Slot", (byte)i);
            itemstack.m_41739_(compoundtag);
            listtag.add(compoundtag);
         }
      }

      if (!listtag.isEmpty() || p_18979_) {
         p_18977_.m_128365_("Items", listtag);
      }

      return p_18977_;
   }

   public static void m_18980_(CompoundTag p_18981_, NonNullList<ItemStack> p_18982_) {
      ListTag listtag = p_18981_.m_128437_("Items", 10);

      for(int i = 0; i < listtag.size(); ++i) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         int j = compoundtag.m_128445_("Slot") & 255;
         if (j >= 0 && j < p_18982_.size()) {
            p_18982_.set(j, ItemStack.m_41712_(compoundtag));
         }
      }

   }

   public static int m_18956_(Container p_18957_, Predicate<ItemStack> p_18958_, int p_18959_, boolean p_18960_) {
      int i = 0;

      for(int j = 0; j < p_18957_.m_6643_(); ++j) {
         ItemStack itemstack = p_18957_.m_8020_(j);
         int k = m_18961_(itemstack, p_18958_, p_18959_ - i, p_18960_);
         if (k > 0 && !p_18960_ && itemstack.m_41619_()) {
            p_18957_.m_6836_(j, ItemStack.f_41583_);
         }

         i += k;
      }

      return i;
   }

   public static int m_18961_(ItemStack p_18962_, Predicate<ItemStack> p_18963_, int p_18964_, boolean p_18965_) {
      if (!p_18962_.m_41619_() && p_18963_.test(p_18962_)) {
         if (p_18965_) {
            return p_18962_.m_41613_();
         } else {
            int i = p_18964_ < 0 ? p_18962_.m_41613_() : Math.min(p_18964_, p_18962_.m_41613_());
            p_18962_.m_41774_(i);
            return i;
         }
      } else {
         return 0;
      }
   }
}