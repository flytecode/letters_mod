package net.minecraft.client.player.inventory;

import com.google.common.collect.ForwardingList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Hotbar extends ForwardingList<ItemStack> {
   private final NonNullList<ItemStack> f_108780_ = NonNullList.m_122780_(Inventory.m_36059_(), ItemStack.f_41583_);

   protected List<ItemStack> delegate() {
      return this.f_108780_;
   }

   public ListTag m_108782_() {
      ListTag listtag = new ListTag();

      for(ItemStack itemstack : this.delegate()) {
         listtag.add(itemstack.m_41739_(new CompoundTag()));
      }

      return listtag;
   }

   public void m_108783_(ListTag p_108784_) {
      List<ItemStack> list = this.delegate();

      for(int i = 0; i < list.size(); ++i) {
         list.set(i, ItemStack.m_41712_(p_108784_.m_128728_(i)));
      }

   }

   public boolean isEmpty() {
      for(ItemStack itemstack : this.delegate()) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }
}