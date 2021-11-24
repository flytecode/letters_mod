package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Slot {
   private final int f_40217_;
   public final Container f_40218_;
   public int f_40219_;
   public final int f_40220_;
   public final int f_40221_;

   public Slot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
      this.f_40218_ = p_40223_;
      this.f_40217_ = p_40224_;
      this.f_40220_ = p_40225_;
      this.f_40221_ = p_40226_;
   }

   public void m_40234_(ItemStack p_40235_, ItemStack p_40236_) {
      int i = p_40236_.m_41613_() - p_40235_.m_41613_();
      if (i > 0) {
         this.m_7169_(p_40236_, i);
      }

   }

   protected void m_7169_(ItemStack p_40232_, int p_40233_) {
   }

   protected void m_6405_(int p_40237_) {
   }

   protected void m_5845_(ItemStack p_40239_) {
   }

   public void m_142406_(Player p_150645_, ItemStack p_150646_) {
      this.m_6654_();
   }

   public boolean m_5857_(ItemStack p_40231_) {
      return true;
   }

   public ItemStack m_7993_() {
      return this.f_40218_.m_8020_(this.f_40217_);
   }

   public boolean m_6657_() {
      return !this.m_7993_().m_41619_();
   }

   public void m_5852_(ItemStack p_40240_) {
      this.f_40218_.m_6836_(this.f_40217_, p_40240_);
      this.m_6654_();
   }

   public void m_6654_() {
      this.f_40218_.m_6596_();
   }

   public int m_6641_() {
      return this.f_40218_.m_6893_();
   }

   public int m_5866_(ItemStack p_40238_) {
      return Math.min(this.m_6641_(), p_40238_.m_41741_());
   }

   @Nullable
   public Pair<ResourceLocation, ResourceLocation> m_7543_() {
      return backgroundPair;
   }

   public ItemStack m_6201_(int p_40227_) {
      return this.f_40218_.m_7407_(this.f_40217_, p_40227_);
   }

   public boolean m_8010_(Player p_40228_) {
      return true;
   }

   public boolean m_6659_() {
      return true;
   }

   /**
    * Retrieves the index in the inventory for this slot, this value should typically not
    * be used, but can be useful for some occasions.
    *
    * @return Index in associated inventory for this slot.
    */
   public int getSlotIndex() {
      return f_40217_;
   }

   /**
    * Checks if the other slot is in the same inventory, by comparing the inventory reference.
    * @param other
    * @return true if the other slot is in the same inventory
    */
   public boolean isSameInventory(Slot other) {
      return this.f_40218_ == other.f_40218_;
   }

   private Pair<ResourceLocation, ResourceLocation> backgroundPair;
   /**
    * Sets the background atlas and sprite location.
    *
    * @param atlas The atlas name
    * @param sprite The sprite located on that atlas.
    * @return this, to allow chaining.
    */
   public Slot setBackground(ResourceLocation atlas, ResourceLocation sprite) {
       this.backgroundPair = Pair.of(atlas, sprite);
       return this;
   }

   public Optional<ItemStack> m_150641_(int p_150642_, int p_150643_, Player p_150644_) {
      if (!this.m_8010_(p_150644_)) {
         return Optional.empty();
      } else if (!this.m_150651_(p_150644_) && p_150643_ < this.m_7993_().m_41613_()) {
         return Optional.empty();
      } else {
         p_150642_ = Math.min(p_150642_, p_150643_);
         ItemStack itemstack = this.m_6201_(p_150642_);
         if (itemstack.m_41619_()) {
            return Optional.empty();
         } else {
            if (this.m_7993_().m_41619_()) {
               this.m_5852_(ItemStack.f_41583_);
            }

            return Optional.of(itemstack);
         }
      }
   }

   public ItemStack m_150647_(int p_150648_, int p_150649_, Player p_150650_) {
      Optional<ItemStack> optional = this.m_150641_(p_150648_, p_150649_, p_150650_);
      optional.ifPresent((p_150655_) -> {
         this.m_142406_(p_150650_, p_150655_);
      });
      return optional.orElse(ItemStack.f_41583_);
   }

   public ItemStack m_150659_(ItemStack p_150660_) {
      return this.m_150656_(p_150660_, p_150660_.m_41613_());
   }

   public ItemStack m_150656_(ItemStack p_150657_, int p_150658_) {
      if (!p_150657_.m_41619_() && this.m_5857_(p_150657_)) {
         ItemStack itemstack = this.m_7993_();
         int i = Math.min(Math.min(p_150658_, p_150657_.m_41613_()), this.m_5866_(p_150657_) - itemstack.m_41613_());
         if (itemstack.m_41619_()) {
            this.m_5852_(p_150657_.m_41620_(i));
         } else if (ItemStack.m_150942_(itemstack, p_150657_)) {
            p_150657_.m_41774_(i);
            itemstack.m_41769_(i);
            this.m_5852_(itemstack);
         }

         return p_150657_;
      } else {
         return p_150657_;
      }
   }

   public boolean m_150651_(Player p_150652_) {
      return this.m_8010_(p_150652_) && this.m_5857_(this.m_7993_());
   }

   public int m_150661_() {
      return this.f_40217_;
   }
}
