package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelBlockEntity extends RandomizableContainerBlockEntity {
   private NonNullList<ItemStack> f_58591_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
   private ContainerOpenersCounter f_155050_ = new ContainerOpenersCounter() {
      protected void m_142292_(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
         BarrelBlockEntity.this.m_58600_(p_155064_, SoundEvents.f_11725_);
         BarrelBlockEntity.this.m_58606_(p_155064_, true);
      }

      protected void m_142289_(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
         BarrelBlockEntity.this.m_58600_(p_155074_, SoundEvents.f_11724_);
         BarrelBlockEntity.this.m_58606_(p_155074_, false);
      }

      protected void m_142148_(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
      }

      protected boolean m_142718_(Player p_155060_) {
         if (p_155060_.f_36096_ instanceof ChestMenu) {
            Container container = ((ChestMenu)p_155060_.f_36096_).m_39261_();
            return container == BarrelBlockEntity.this;
         } else {
            return false;
         }
      }
   };

   public BarrelBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
      super(BlockEntityType.f_58942_, p_155052_, p_155053_);
   }

   public CompoundTag m_6945_(CompoundTag p_58612_) {
      super.m_6945_(p_58612_);
      if (!this.m_59634_(p_58612_)) {
         ContainerHelper.m_18973_(p_58612_, this.f_58591_);
      }

      return p_58612_;
   }

   public void m_142466_(CompoundTag p_155055_) {
      super.m_142466_(p_155055_);
      this.f_58591_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(p_155055_)) {
         ContainerHelper.m_18980_(p_155055_, this.f_58591_);
      }

   }

   public int m_6643_() {
      return 27;
   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.f_58591_;
   }

   protected void m_6520_(NonNullList<ItemStack> p_58610_) {
      this.f_58591_ = p_58610_;
   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.barrel");
   }

   protected AbstractContainerMenu m_6555_(int p_58598_, Inventory p_58599_) {
      return ChestMenu.m_39237_(p_58598_, p_58599_, this);
   }

   public void m_5856_(Player p_58616_) {
      if (!this.f_58859_ && !p_58616_.m_5833_()) {
         this.f_155050_.m_155452_(p_58616_, this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   public void m_5785_(Player p_58614_) {
      if (!this.f_58859_ && !p_58614_.m_5833_()) {
         this.f_155050_.m_155468_(p_58614_, this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   public void m_58619_() {
      if (!this.f_58859_) {
         this.f_155050_.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   void m_58606_(BlockState p_58607_, boolean p_58608_) {
      this.f_58857_.m_7731_(this.m_58899_(), p_58607_.m_61124_(BarrelBlock.f_49043_, Boolean.valueOf(p_58608_)), 3);
   }

   void m_58600_(BlockState p_58601_, SoundEvent p_58602_) {
      Vec3i vec3i = p_58601_.m_61143_(BarrelBlock.f_49042_).m_122436_();
      double d0 = (double)this.f_58858_.m_123341_() + 0.5D + (double)vec3i.m_123341_() / 2.0D;
      double d1 = (double)this.f_58858_.m_123342_() + 0.5D + (double)vec3i.m_123342_() / 2.0D;
      double d2 = (double)this.f_58858_.m_123343_() + 0.5D + (double)vec3i.m_123343_() / 2.0D;
      this.f_58857_.m_6263_((Player)null, d0, d1, d2, p_58602_, SoundSource.BLOCKS, 0.5F, this.f_58857_.f_46441_.nextFloat() * 0.1F + 0.9F);
   }
}