package net.minecraft.world.entity.animal.horse;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public abstract class AbstractChestedHorse extends AbstractHorse {
   private static final EntityDataAccessor<Boolean> f_30482_ = SynchedEntityData.m_135353_(AbstractChestedHorse.class, EntityDataSerializers.f_135035_);
   public static final int f_149477_ = 15;

   protected AbstractChestedHorse(EntityType<? extends AbstractChestedHorse> p_30485_, Level p_30486_) {
      super(p_30485_, p_30486_);
      this.f_30523_ = false;
   }

   protected void m_7505_() {
      this.m_21051_(Attributes.f_22276_).m_22100_((double)this.m_30629_());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_30482_, false);
   }

   public static AttributeSupplier.Builder m_30501_() {
      return m_30627_().m_22268_(Attributes.f_22279_, (double)0.175F).m_22268_(Attributes.f_22288_, 0.5D);
   }

   public boolean m_30502_() {
      return this.f_19804_.m_135370_(f_30482_);
   }

   public void m_30504_(boolean p_30505_) {
      this.f_19804_.m_135381_(f_30482_, p_30505_);
   }

   protected int m_7506_() {
      return this.m_30502_() ? 17 : super.m_7506_();
   }

   public double m_6048_() {
      return super.m_6048_() - 0.25D;
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.m_30502_()) {
         if (!this.f_19853_.f_46443_) {
            this.m_19998_(Blocks.f_50087_);
         }

         this.m_30504_(false);
      }

   }

   public void m_7380_(CompoundTag p_30496_) {
      super.m_7380_(p_30496_);
      p_30496_.m_128379_("ChestedHorse", this.m_30502_());
      if (this.m_30502_()) {
         ListTag listtag = new ListTag();

         for(int i = 2; i < this.f_30520_.m_6643_(); ++i) {
            ItemStack itemstack = this.f_30520_.m_8020_(i);
            if (!itemstack.m_41619_()) {
               CompoundTag compoundtag = new CompoundTag();
               compoundtag.m_128344_("Slot", (byte)i);
               itemstack.m_41739_(compoundtag);
               listtag.add(compoundtag);
            }
         }

         p_30496_.m_128365_("Items", listtag);
      }

   }

   public void m_7378_(CompoundTag p_30488_) {
      super.m_7378_(p_30488_);
      this.m_30504_(p_30488_.m_128471_("ChestedHorse"));
      this.m_30625_();
      if (this.m_30502_()) {
         ListTag listtag = p_30488_.m_128437_("Items", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            int j = compoundtag.m_128445_("Slot") & 255;
            if (j >= 2 && j < this.f_30520_.m_6643_()) {
               this.f_30520_.m_6836_(j, ItemStack.m_41712_(compoundtag));
            }
         }
      }

      this.m_7493_();
   }

   public SlotAccess m_141942_(int p_149479_) {
      return p_149479_ == 499 ? new SlotAccess() {
         public ItemStack m_142196_() {
            return AbstractChestedHorse.this.m_30502_() ? new ItemStack(Items.f_42009_) : ItemStack.f_41583_;
         }

         public boolean m_142104_(ItemStack p_149485_) {
            if (p_149485_.m_41619_()) {
               if (AbstractChestedHorse.this.m_30502_()) {
                  AbstractChestedHorse.this.m_30504_(false);
                  AbstractChestedHorse.this.m_30625_();
               }

               return true;
            } else if (p_149485_.m_150930_(Items.f_42009_)) {
               if (!AbstractChestedHorse.this.m_30502_()) {
                  AbstractChestedHorse.this.m_30504_(true);
                  AbstractChestedHorse.this.m_30625_();
               }

               return true;
            } else {
               return false;
            }
         }
      } : super.m_141942_(p_149479_);
   }

   public InteractionResult m_6071_(Player p_30493_, InteractionHand p_30494_) {
      ItemStack itemstack = p_30493_.m_21120_(p_30494_);
      if (!this.m_6162_()) {
         if (this.m_30614_() && p_30493_.m_36341_()) {
            this.m_30620_(p_30493_);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }

         if (this.m_20160_()) {
            return super.m_6071_(p_30493_, p_30494_);
         }
      }

      if (!itemstack.m_41619_()) {
         if (this.m_6898_(itemstack)) {
            return this.m_30580_(p_30493_, itemstack);
         }

         if (!this.m_30614_()) {
            this.m_7564_();
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }

         if (!this.m_30502_() && itemstack.m_150930_(Blocks.f_50087_.m_5456_())) {
            this.m_30504_(true);
            this.m_7609_();
            if (!p_30493_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            this.m_30625_();
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }

         if (!this.m_6162_() && !this.m_6254_() && itemstack.m_150930_(Items.f_42450_)) {
            this.m_30620_(p_30493_);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }
      }

      if (this.m_6162_()) {
         return super.m_6071_(p_30493_, p_30494_);
      } else {
         this.m_6835_(p_30493_);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      }
   }

   protected void m_7609_() {
      this.m_5496_(SoundEvents.f_11811_, 1.0F, (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
   }

   public int m_7488_() {
      return 5;
   }
}