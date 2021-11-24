package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;

public class PlayerEnderChestContainer extends SimpleContainer {
   @Nullable
   private EnderChestBlockEntity f_40101_;

   public PlayerEnderChestContainer() {
      super(27);
   }

   public void m_40105_(EnderChestBlockEntity p_40106_) {
      this.f_40101_ = p_40106_;
   }

   public boolean m_150633_(EnderChestBlockEntity p_150634_) {
      return this.f_40101_ == p_150634_;
   }

   public void m_7797_(ListTag p_40108_) {
      for(int i = 0; i < this.m_6643_(); ++i) {
         this.m_6836_(i, ItemStack.f_41583_);
      }

      for(int k = 0; k < p_40108_.size(); ++k) {
         CompoundTag compoundtag = p_40108_.m_128728_(k);
         int j = compoundtag.m_128445_("Slot") & 255;
         if (j >= 0 && j < this.m_6643_()) {
            this.m_6836_(j, ItemStack.m_41712_(compoundtag));
         }
      }

   }

   public ListTag m_7927_() {
      ListTag listtag = new ListTag();

      for(int i = 0; i < this.m_6643_(); ++i) {
         ItemStack itemstack = this.m_8020_(i);
         if (!itemstack.m_41619_()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Slot", (byte)i);
            itemstack.m_41739_(compoundtag);
            listtag.add(compoundtag);
         }
      }

      return listtag;
   }

   public boolean m_6542_(Player p_40104_) {
      return this.f_40101_ != null && !this.f_40101_.m_59282_(p_40104_) ? false : super.m_6542_(p_40104_);
   }

   public void m_5856_(Player p_40112_) {
      if (this.f_40101_ != null) {
         this.f_40101_.m_155515_(p_40112_);
      }

      super.m_5856_(p_40112_);
   }

   public void m_5785_(Player p_40110_) {
      if (this.f_40101_ != null) {
         this.f_40101_.m_155522_(p_40110_);
      }

      super.m_5785_(p_40110_);
      this.f_40101_ = null;
   }
}