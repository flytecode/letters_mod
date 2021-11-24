package net.minecraft.world.level.block.entity;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DispenserBlockEntity extends RandomizableContainerBlockEntity {
   private static final Random f_59227_ = new Random();
   public static final int f_155487_ = 9;
   private NonNullList<ItemStack> f_59228_ = NonNullList.m_122780_(9, ItemStack.f_41583_);

   protected DispenserBlockEntity(BlockEntityType<?> p_155489_, BlockPos p_155490_, BlockState p_155491_) {
      super(p_155489_, p_155490_, p_155491_);
   }

   public DispenserBlockEntity(BlockPos p_155493_, BlockState p_155494_) {
      this(BlockEntityType.f_58922_, p_155493_, p_155494_);
   }

   public int m_6643_() {
      return 9;
   }

   public int m_59248_() {
      this.m_59640_((Player)null);
      int i = -1;
      int j = 1;

      for(int k = 0; k < this.f_59228_.size(); ++k) {
         if (!this.f_59228_.get(k).m_41619_() && f_59227_.nextInt(j++) == 0) {
            i = k;
         }
      }

      return i;
   }

   public int m_59237_(ItemStack p_59238_) {
      for(int i = 0; i < this.f_59228_.size(); ++i) {
         if (this.f_59228_.get(i).m_41619_()) {
            this.m_6836_(i, p_59238_);
            return i;
         }
      }

      return -1;
   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.dispenser");
   }

   public void m_142466_(CompoundTag p_155496_) {
      super.m_142466_(p_155496_);
      this.f_59228_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(p_155496_)) {
         ContainerHelper.m_18980_(p_155496_, this.f_59228_);
      }

   }

   public CompoundTag m_6945_(CompoundTag p_59245_) {
      super.m_6945_(p_59245_);
      if (!this.m_59634_(p_59245_)) {
         ContainerHelper.m_18973_(p_59245_, this.f_59228_);
      }

      return p_59245_;
   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.f_59228_;
   }

   protected void m_6520_(NonNullList<ItemStack> p_59243_) {
      this.f_59228_ = p_59243_;
   }

   protected AbstractContainerMenu m_6555_(int p_59235_, Inventory p_59236_) {
      return new DispenserMenu(p_59235_, p_59236_, this);
   }
}