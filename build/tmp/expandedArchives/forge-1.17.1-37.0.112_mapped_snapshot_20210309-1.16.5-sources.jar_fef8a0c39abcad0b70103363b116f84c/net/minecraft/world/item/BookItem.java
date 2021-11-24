package net.minecraft.world.item;

public class BookItem extends Item {
   public BookItem(Item.Properties p_40643_) {
      super(p_40643_);
   }

   public boolean m_8120_(ItemStack p_40646_) {
      return p_40646_.m_41613_() == 1;
   }

   public int m_6473_() {
      return 1;
   }
}