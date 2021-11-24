package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SimpleContainer implements Container, StackedContentsCompatible {
   private final int f_19146_;
   private final NonNullList<ItemStack> f_19147_;
   private List<ContainerListener> f_19148_;

   public SimpleContainer(int p_19150_) {
      this.f_19146_ = p_19150_;
      this.f_19147_ = NonNullList.m_122780_(p_19150_, ItemStack.f_41583_);
   }

   public SimpleContainer(ItemStack... p_19152_) {
      this.f_19146_ = p_19152_.length;
      this.f_19147_ = NonNullList.m_122783_(ItemStack.f_41583_, p_19152_);
   }

   public void m_19164_(ContainerListener p_19165_) {
      if (this.f_19148_ == null) {
         this.f_19148_ = Lists.newArrayList();
      }

      this.f_19148_.add(p_19165_);
   }

   public void m_19181_(ContainerListener p_19182_) {
      this.f_19148_.remove(p_19182_);
   }

   public ItemStack m_8020_(int p_19157_) {
      return p_19157_ >= 0 && p_19157_ < this.f_19147_.size() ? this.f_19147_.get(p_19157_) : ItemStack.f_41583_;
   }

   public List<ItemStack> m_19195_() {
      List<ItemStack> list = this.f_19147_.stream().filter((p_19197_) -> {
         return !p_19197_.m_41619_();
      }).collect(Collectors.toList());
      this.m_6211_();
      return list;
   }

   public ItemStack m_7407_(int p_19159_, int p_19160_) {
      ItemStack itemstack = ContainerHelper.m_18969_(this.f_19147_, p_19159_, p_19160_);
      if (!itemstack.m_41619_()) {
         this.m_6596_();
      }

      return itemstack;
   }

   public ItemStack m_19170_(Item p_19171_, int p_19172_) {
      ItemStack itemstack = new ItemStack(p_19171_, 0);

      for(int i = this.f_19146_ - 1; i >= 0; --i) {
         ItemStack itemstack1 = this.m_8020_(i);
         if (itemstack1.m_41720_().equals(p_19171_)) {
            int j = p_19172_ - itemstack.m_41613_();
            ItemStack itemstack2 = itemstack1.m_41620_(j);
            itemstack.m_41769_(itemstack2.m_41613_());
            if (itemstack.m_41613_() == p_19172_) {
               break;
            }
         }
      }

      if (!itemstack.m_41619_()) {
         this.m_6596_();
      }

      return itemstack;
   }

   public ItemStack m_19173_(ItemStack p_19174_) {
      ItemStack itemstack = p_19174_.m_41777_();
      this.m_19191_(itemstack);
      if (itemstack.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         this.m_19189_(itemstack);
         return itemstack.m_41619_() ? ItemStack.f_41583_ : itemstack;
      }
   }

   public boolean m_19183_(ItemStack p_19184_) {
      boolean flag = false;

      for(ItemStack itemstack : this.f_19147_) {
         if (itemstack.m_41619_() || ItemStack.m_150942_(itemstack, p_19184_) && itemstack.m_41613_() < itemstack.m_41741_()) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   public ItemStack m_8016_(int p_19180_) {
      ItemStack itemstack = this.f_19147_.get(p_19180_);
      if (itemstack.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         this.f_19147_.set(p_19180_, ItemStack.f_41583_);
         return itemstack;
      }
   }

   public void m_6836_(int p_19162_, ItemStack p_19163_) {
      this.f_19147_.set(p_19162_, p_19163_);
      if (!p_19163_.m_41619_() && p_19163_.m_41613_() > this.m_6893_()) {
         p_19163_.m_41764_(this.m_6893_());
      }

      this.m_6596_();
   }

   public int m_6643_() {
      return this.f_19146_;
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_19147_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public void m_6596_() {
      if (this.f_19148_ != null) {
         for(ContainerListener containerlistener : this.f_19148_) {
            containerlistener.m_5757_(this);
         }
      }

   }

   public boolean m_6542_(Player p_19167_) {
      return true;
   }

   public void m_6211_() {
      this.f_19147_.clear();
      this.m_6596_();
   }

   public void m_5809_(StackedContents p_19169_) {
      for(ItemStack itemstack : this.f_19147_) {
         p_19169_.m_36491_(itemstack);
      }

   }

   public String toString() {
      return this.f_19147_.stream().filter((p_19194_) -> {
         return !p_19194_.m_41619_();
      }).collect(Collectors.toList()).toString();
   }

   private void m_19189_(ItemStack p_19190_) {
      for(int i = 0; i < this.f_19146_; ++i) {
         ItemStack itemstack = this.m_8020_(i);
         if (itemstack.m_41619_()) {
            this.m_6836_(i, p_19190_.m_41777_());
            p_19190_.m_41764_(0);
            return;
         }
      }

   }

   private void m_19191_(ItemStack p_19192_) {
      for(int i = 0; i < this.f_19146_; ++i) {
         ItemStack itemstack = this.m_8020_(i);
         if (ItemStack.m_150942_(itemstack, p_19192_)) {
            this.m_19185_(p_19192_, itemstack);
            if (p_19192_.m_41619_()) {
               return;
            }
         }
      }

   }

   private void m_19185_(ItemStack p_19186_, ItemStack p_19187_) {
      int i = Math.min(this.m_6893_(), p_19187_.m_41741_());
      int j = Math.min(p_19186_.m_41613_(), i - p_19187_.m_41613_());
      if (j > 0) {
         p_19187_.m_41769_(j);
         p_19186_.m_41774_(j);
         this.m_6596_();
      }

   }

   public void m_7797_(ListTag p_19178_) {
      for(int i = 0; i < p_19178_.size(); ++i) {
         ItemStack itemstack = ItemStack.m_41712_(p_19178_.m_128728_(i));
         if (!itemstack.m_41619_()) {
            this.m_19173_(itemstack);
         }
      }

   }

   public ListTag m_7927_() {
      ListTag listtag = new ListTag();

      for(int i = 0; i < this.m_6643_(); ++i) {
         ItemStack itemstack = this.m_8020_(i);
         if (!itemstack.m_41619_()) {
            listtag.add(itemstack.m_41739_(new CompoundTag()));
         }
      }

      return listtag;
   }
}