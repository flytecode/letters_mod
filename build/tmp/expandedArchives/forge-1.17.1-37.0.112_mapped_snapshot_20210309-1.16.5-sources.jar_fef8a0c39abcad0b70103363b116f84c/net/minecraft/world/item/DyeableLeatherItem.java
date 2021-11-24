package net.minecraft.world.item;

import java.util.List;
import net.minecraft.nbt.CompoundTag;

public interface DyeableLeatherItem {
   String f_150826_ = "color";
   String f_150827_ = "display";
   int f_150828_ = 10511680;

   default boolean m_41113_(ItemStack p_41114_) {
      CompoundTag compoundtag = p_41114_.m_41737_("display");
      return compoundtag != null && compoundtag.m_128425_("color", 99);
   }

   default int m_41121_(ItemStack p_41122_) {
      CompoundTag compoundtag = p_41122_.m_41737_("display");
      return compoundtag != null && compoundtag.m_128425_("color", 99) ? compoundtag.m_128451_("color") : 10511680;
   }

   default void m_41123_(ItemStack p_41124_) {
      CompoundTag compoundtag = p_41124_.m_41737_("display");
      if (compoundtag != null && compoundtag.m_128441_("color")) {
         compoundtag.m_128473_("color");
      }

   }

   default void m_41115_(ItemStack p_41116_, int p_41117_) {
      p_41116_.m_41698_("display").m_128405_("color", p_41117_);
   }

   static ItemStack m_41118_(ItemStack p_41119_, List<DyeItem> p_41120_) {
      ItemStack itemstack = ItemStack.f_41583_;
      int[] aint = new int[3];
      int i = 0;
      int j = 0;
      DyeableLeatherItem dyeableleatheritem = null;
      Item item = p_41119_.m_41720_();
      if (item instanceof DyeableLeatherItem) {
         dyeableleatheritem = (DyeableLeatherItem)item;
         itemstack = p_41119_.m_41777_();
         itemstack.m_41764_(1);
         if (dyeableleatheritem.m_41113_(p_41119_)) {
            int k = dyeableleatheritem.m_41121_(itemstack);
            float f = (float)(k >> 16 & 255) / 255.0F;
            float f1 = (float)(k >> 8 & 255) / 255.0F;
            float f2 = (float)(k & 255) / 255.0F;
            i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
            aint[0] = (int)((float)aint[0] + f * 255.0F);
            aint[1] = (int)((float)aint[1] + f1 * 255.0F);
            aint[2] = (int)((float)aint[2] + f2 * 255.0F);
            ++j;
         }

         for(DyeItem dyeitem : p_41120_) {
            float[] afloat = dyeitem.m_41089_().m_41068_();
            int i2 = (int)(afloat[0] * 255.0F);
            int l = (int)(afloat[1] * 255.0F);
            int i1 = (int)(afloat[2] * 255.0F);
            i += Math.max(i2, Math.max(l, i1));
            aint[0] += i2;
            aint[1] += l;
            aint[2] += i1;
            ++j;
         }
      }

      if (dyeableleatheritem == null) {
         return ItemStack.f_41583_;
      } else {
         int j1 = aint[0] / j;
         int k1 = aint[1] / j;
         int l1 = aint[2] / j;
         float f3 = (float)i / (float)j;
         float f4 = (float)Math.max(j1, Math.max(k1, l1));
         j1 = (int)((float)j1 * f3 / f4);
         k1 = (int)((float)k1 * f3 / f4);
         l1 = (int)((float)l1 * f3 / f4);
         int j2 = (j1 << 8) + k1;
         j2 = (j2 << 8) + l1;
         dyeableleatheritem.m_41115_(itemstack, j2);
         return itemstack;
      }
   }
}