package net.minecraft.world.entity.vehicle;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MinecartHopper extends AbstractMinecartContainer implements Hopper {
   public static final int f_150332_ = 4;
   private boolean f_38580_ = true;
   private int f_38581_ = -1;
   private final BlockPos f_38582_ = BlockPos.f_121853_;

   public MinecartHopper(EntityType<? extends MinecartHopper> p_38584_, Level p_38585_) {
      super(p_38584_, p_38585_);
   }

   public MinecartHopper(Level p_38587_, double p_38588_, double p_38589_, double p_38590_) {
      super(EntityType.f_20473_, p_38588_, p_38589_, p_38590_, p_38587_);
   }

   public AbstractMinecart.Type m_6064_() {
      return AbstractMinecart.Type.HOPPER;
   }

   public BlockState m_6390_() {
      return Blocks.f_50332_.m_49966_();
   }

   public int m_7144_() {
      return 1;
   }

   public int m_6643_() {
      return 5;
   }

   public void m_6025_(int p_38596_, int p_38597_, int p_38598_, boolean p_38599_) {
      boolean flag = !p_38599_;
      if (flag != this.m_38617_()) {
         this.m_38613_(flag);
      }

   }

   public boolean m_38617_() {
      return this.f_38580_;
   }

   public void m_38613_(boolean p_38614_) {
      this.f_38580_ = p_38614_;
   }

   public double m_6343_() {
      return this.m_20185_();
   }

   public double m_6358_() {
      return this.m_20186_() + 0.5D;
   }

   public double m_6446_() {
      return this.m_20189_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.m_6084_() && this.m_38617_()) {
         BlockPos blockpos = this.m_142538_();
         if (blockpos.equals(this.f_38582_)) {
            --this.f_38581_;
         } else {
            this.m_38610_(0);
         }

         if (!this.m_38593_()) {
            this.m_38610_(0);
            if (this.m_38592_()) {
               this.m_38610_(4);
               this.m_6596_();
            }
         }
      }

   }

   public boolean m_38592_() {
      if (HopperBlockEntity.m_155552_(this.f_19853_, this)) {
         return true;
      } else {
         List<ItemEntity> list = this.f_19853_.m_6443_(ItemEntity.class, this.m_142469_().m_82377_(0.25D, 0.0D, 0.25D), EntitySelector.f_20402_);
         if (!list.isEmpty()) {
            HopperBlockEntity.m_59331_(this, list.get(0));
         }

         return false;
      }
   }

   public void m_7617_(DamageSource p_38604_) {
      super.m_7617_(p_38604_);
      if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
         this.m_19998_(Blocks.f_50332_);
      }

   }

   protected void m_7380_(CompoundTag p_38608_) {
      super.m_7380_(p_38608_);
      p_38608_.m_128405_("TransferCooldown", this.f_38581_);
      p_38608_.m_128379_("Enabled", this.f_38580_);
   }

   protected void m_7378_(CompoundTag p_38606_) {
      super.m_7378_(p_38606_);
      this.f_38581_ = p_38606_.m_128451_("TransferCooldown");
      this.f_38580_ = p_38606_.m_128441_("Enabled") ? p_38606_.m_128471_("Enabled") : true;
   }

   public void m_38610_(int p_38611_) {
      this.f_38581_ = p_38611_;
   }

   public boolean m_38593_() {
      return this.f_38581_ > 0;
   }

   public AbstractContainerMenu m_7402_(int p_38601_, Inventory p_38602_) {
      return new HopperMenu(p_38601_, p_38602_, this);
   }
}