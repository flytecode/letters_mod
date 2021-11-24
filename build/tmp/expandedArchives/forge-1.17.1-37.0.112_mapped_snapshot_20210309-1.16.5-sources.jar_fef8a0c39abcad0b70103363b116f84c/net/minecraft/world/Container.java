package net.minecraft.world;

import java.util.Set;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface Container extends Clearable {
   int f_146642_ = 64;

   int m_6643_();

   boolean m_7983_();

   ItemStack m_8020_(int p_18941_);

   ItemStack m_7407_(int p_18942_, int p_18943_);

   ItemStack m_8016_(int p_18951_);

   void m_6836_(int p_18944_, ItemStack p_18945_);

   default int m_6893_() {
      return 64;
   }

   void m_6596_();

   boolean m_6542_(Player p_18946_);

   default void m_5856_(Player p_18955_) {
   }

   default void m_5785_(Player p_18954_) {
   }

   default boolean m_7013_(int p_18952_, ItemStack p_18953_) {
      return true;
   }

   default int m_18947_(Item p_18948_) {
      int i = 0;

      for(int j = 0; j < this.m_6643_(); ++j) {
         ItemStack itemstack = this.m_8020_(j);
         if (itemstack.m_41720_().equals(p_18948_)) {
            i += itemstack.m_41613_();
         }
      }

      return i;
   }

   default boolean m_18949_(Set<Item> p_18950_) {
      for(int i = 0; i < this.m_6643_(); ++i) {
         ItemStack itemstack = this.m_8020_(i);
         if (p_18950_.contains(itemstack.m_41720_()) && itemstack.m_41613_() > 0) {
            return true;
         }
      }

      return false;
   }
}