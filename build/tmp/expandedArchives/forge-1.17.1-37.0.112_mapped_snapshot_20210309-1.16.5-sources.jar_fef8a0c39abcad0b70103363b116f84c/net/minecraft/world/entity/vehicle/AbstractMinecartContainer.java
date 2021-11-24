package net.minecraft.world.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public abstract class AbstractMinecartContainer extends AbstractMinecart implements Container, MenuProvider {
   private NonNullList<ItemStack> f_38202_ = NonNullList.m_122780_(36, ItemStack.f_41583_);
   @Nullable
   private ResourceLocation f_38204_;
   private long f_38205_;

   protected AbstractMinecartContainer(EntityType<?> p_38213_, Level p_38214_) {
      super(p_38213_, p_38214_);
   }

   protected AbstractMinecartContainer(EntityType<?> p_38207_, double p_38208_, double p_38209_, double p_38210_, Level p_38211_) {
      super(p_38207_, p_38211_, p_38208_, p_38209_, p_38210_);
   }

   public void m_7617_(DamageSource p_38228_) {
      super.m_7617_(p_38228_);
      if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
         Containers.m_18998_(this.f_19853_, this, this);
         if (!this.f_19853_.f_46443_) {
            Entity entity = p_38228_.m_7640_();
            if (entity != null && entity.m_6095_() == EntityType.f_20532_) {
               PiglinAi.m_34873_((Player)entity, true);
            }
         }
      }

   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_38202_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_38218_) {
      this.m_38254_((Player)null);
      return this.f_38202_.get(p_38218_);
   }

   public ItemStack m_7407_(int p_38220_, int p_38221_) {
      this.m_38254_((Player)null);
      return ContainerHelper.m_18969_(this.f_38202_, p_38220_, p_38221_);
   }

   public ItemStack m_8016_(int p_38244_) {
      this.m_38254_((Player)null);
      ItemStack itemstack = this.f_38202_.get(p_38244_);
      if (itemstack.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         this.f_38202_.set(p_38244_, ItemStack.f_41583_);
         return itemstack;
      }
   }

   public void m_6836_(int p_38225_, ItemStack p_38226_) {
      this.m_38254_((Player)null);
      this.f_38202_.set(p_38225_, p_38226_);
      if (!p_38226_.m_41619_() && p_38226_.m_41613_() > this.m_6893_()) {
         p_38226_.m_41764_(this.m_6893_());
      }

   }

   public SlotAccess m_141942_(final int p_150257_) {
      return p_150257_ >= 0 && p_150257_ < this.m_6643_() ? new SlotAccess() {
         public ItemStack m_142196_() {
            return AbstractMinecartContainer.this.m_8020_(p_150257_);
         }

         public boolean m_142104_(ItemStack p_150265_) {
            AbstractMinecartContainer.this.m_6836_(p_150257_, p_150265_);
            return true;
         }
      } : super.m_141942_(p_150257_);
   }

   public void m_6596_() {
   }

   public boolean m_6542_(Player p_38230_) {
      if (this.m_146910_()) {
         return false;
      } else {
         return !(p_38230_.m_20280_(this) > 64.0D);
      }
   }

   public void m_142687_(Entity.RemovalReason p_150255_) {
      if (!this.f_19853_.f_46443_ && p_150255_.m_146965_()) {
         Containers.m_18998_(this.f_19853_, this, this);
      }

      super.m_142687_(p_150255_);
   }

   protected void m_7380_(CompoundTag p_38248_) {
      super.m_7380_(p_38248_);
      if (this.f_38204_ != null) {
         p_38248_.m_128359_("LootTable", this.f_38204_.toString());
         if (this.f_38205_ != 0L) {
            p_38248_.m_128356_("LootTableSeed", this.f_38205_);
         }
      } else {
         ContainerHelper.m_18973_(p_38248_, this.f_38202_);
      }

   }

   protected void m_7378_(CompoundTag p_38235_) {
      super.m_7378_(p_38235_);
      this.f_38202_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (p_38235_.m_128425_("LootTable", 8)) {
         this.f_38204_ = new ResourceLocation(p_38235_.m_128461_("LootTable"));
         this.f_38205_ = p_38235_.m_128454_("LootTableSeed");
      } else {
         ContainerHelper.m_18980_(p_38235_, this.f_38202_);
      }

   }

   public InteractionResult m_6096_(Player p_38232_, InteractionHand p_38233_) {
      InteractionResult ret = super.m_6096_(p_38232_, p_38233_);
      if (ret.m_19077_()) return ret;
      p_38232_.m_5893_(this);
      if (!p_38232_.f_19853_.f_46443_) {
         this.m_146852_(GameEvent.f_157803_, p_38232_);
         PiglinAi.m_34873_(p_38232_, true);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   protected void m_7114_() {
      float f = 0.98F;
      if (this.f_38204_ == null) {
         int i = 15 - AbstractContainerMenu.m_38938_(this);
         f += (float)i * 0.001F;
      }

      if (this.m_20069_()) {
         f *= 0.95F;
      }

      this.m_20256_(this.m_20184_().m_82542_((double)f, 0.0D, (double)f));
   }

   public void m_38254_(@Nullable Player p_38255_) {
      if (this.f_38204_ != null && this.f_19853_.m_142572_() != null) {
         LootTable loottable = this.f_19853_.m_142572_().m_129898_().m_79217_(this.f_38204_);
         if (p_38255_ instanceof ServerPlayer) {
            CriteriaTriggers.f_10563_.m_54597_((ServerPlayer)p_38255_, this.f_38204_);
         }

         this.f_38204_ = null;
         LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.f_19853_)).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78965_(this.f_38205_);
         // Forge: add this entity to loot context, however, currently Vanilla uses 'this' for the player creating the chests. So we take over 'killer_entity' for this.
         lootcontext$builder.m_78972_(LootContextParams.f_81458_, this);
         if (p_38255_ != null) {
            lootcontext$builder.m_78963_(p_38255_.m_36336_()).m_78972_(LootContextParams.f_81455_, p_38255_);
         }

         loottable.m_79123_(this, lootcontext$builder.m_78975_(LootContextParamSets.f_81411_));
      }

   }

   public void m_6211_() {
      this.m_38254_((Player)null);
      this.f_38202_.clear();
   }

   public void m_38236_(ResourceLocation p_38237_, long p_38238_) {
      this.f_38204_ = p_38237_;
      this.f_38205_ = p_38238_;
   }

   @Nullable
   public AbstractContainerMenu m_7208_(int p_38251_, Inventory p_38252_, Player p_38253_) {
      if (this.f_38204_ != null && p_38253_.m_5833_()) {
         return null;
      } else {
         this.m_38254_(p_38252_.f_35978_);
         return this.m_7402_(p_38251_, p_38252_);
      }
   }

   protected abstract AbstractContainerMenu m_7402_(int p_38222_, Inventory p_38223_);

   // Forge Start
   private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
      if (this.m_6084_() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
         return itemHandler.cast();
      return super.getCapability(capability, facing);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      itemHandler.invalidate();
   }

   @Override
   public void reviveCaps() {
      super.reviveCaps();
      itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));
   }
}
