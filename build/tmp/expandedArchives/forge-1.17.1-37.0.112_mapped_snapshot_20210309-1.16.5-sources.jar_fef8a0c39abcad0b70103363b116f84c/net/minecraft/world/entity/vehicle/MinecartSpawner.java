package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MinecartSpawner extends AbstractMinecart {
   private final BaseSpawner f_38621_ = new BaseSpawner() {
      public void m_142523_(Level p_150342_, BlockPos p_150343_, int p_150344_) {
         p_150342_.m_7605_(MinecartSpawner.this, (byte)p_150344_);
      }

      @Override
      @javax.annotation.Nullable
      public net.minecraft.world.entity.Entity getSpawnerEntity() {
         return MinecartSpawner.this;
      }
   };
   private final Runnable f_150333_;

   public MinecartSpawner(EntityType<? extends MinecartSpawner> p_38623_, Level p_38624_) {
      super(p_38623_, p_38624_);
      this.f_150333_ = this.m_150334_(p_38624_);
   }

   public MinecartSpawner(Level p_38626_, double p_38627_, double p_38628_, double p_38629_) {
      super(EntityType.f_20474_, p_38626_, p_38627_, p_38628_, p_38629_);
      this.f_150333_ = this.m_150334_(p_38626_);
   }

   private Runnable m_150334_(Level p_150335_) {
      return p_150335_ instanceof ServerLevel ? () -> {
         this.f_38621_.m_151311_((ServerLevel)p_150335_, this.m_142538_());
      } : () -> {
         this.f_38621_.m_151319_(p_150335_, this.m_142538_());
      };
   }

   public AbstractMinecart.Type m_6064_() {
      return AbstractMinecart.Type.SPAWNER;
   }

   public BlockState m_6390_() {
      return Blocks.f_50085_.m_49966_();
   }

   protected void m_7378_(CompoundTag p_38633_) {
      super.m_7378_(p_38633_);
      this.f_38621_.m_151328_(this.f_19853_, this.m_142538_(), p_38633_);
   }

   protected void m_7380_(CompoundTag p_38635_) {
      super.m_7380_(p_38635_);
      this.f_38621_.m_151339_(this.f_19853_, this.m_142538_(), p_38635_);
   }

   public void m_7822_(byte p_38631_) {
      this.f_38621_.m_151316_(this.f_19853_, p_38631_);
   }

   public void m_8119_() {
      super.m_8119_();
      this.f_150333_.run();
   }

   public BaseSpawner m_150340_() {
      return this.f_38621_;
   }

   public boolean m_6127_() {
      return true;
   }
}
