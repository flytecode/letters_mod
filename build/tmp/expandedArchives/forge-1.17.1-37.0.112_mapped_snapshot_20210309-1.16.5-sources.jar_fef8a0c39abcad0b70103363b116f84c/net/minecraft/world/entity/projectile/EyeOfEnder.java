package net.minecraft.world.entity.projectile;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EyeOfEnder extends Entity implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> f_36949_ = SynchedEntityData.m_135353_(EyeOfEnder.class, EntityDataSerializers.f_135033_);
   private double f_36950_;
   private double f_36951_;
   private double f_36952_;
   private int f_36953_;
   private boolean f_36954_;

   public EyeOfEnder(EntityType<? extends EyeOfEnder> p_36957_, Level p_36958_) {
      super(p_36957_, p_36958_);
   }

   public EyeOfEnder(Level p_36960_, double p_36961_, double p_36962_, double p_36963_) {
      this(EntityType.f_20571_, p_36960_);
      this.m_6034_(p_36961_, p_36962_, p_36963_);
   }

   public void m_36972_(ItemStack p_36973_) {
      if (!p_36973_.m_150930_(Items.f_42545_) || p_36973_.m_41782_()) {
         this.m_20088_().m_135381_(f_36949_, Util.m_137469_(p_36973_.m_41777_(), (p_36978_) -> {
            p_36978_.m_41764_(1);
         }));
      }

   }

   private ItemStack m_36981_() {
      return this.m_20088_().m_135370_(f_36949_);
   }

   public ItemStack m_7846_() {
      ItemStack itemstack = this.m_36981_();
      return itemstack.m_41619_() ? new ItemStack(Items.f_42545_) : itemstack;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(f_36949_, ItemStack.f_41583_);
   }

   public boolean m_6783_(double p_36966_) {
      double d0 = this.m_142469_().m_82309_() * 4.0D;
      if (Double.isNaN(d0)) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_36966_ < d0 * d0;
   }

   public void m_36967_(BlockPos p_36968_) {
      double d0 = (double)p_36968_.m_123341_();
      int i = p_36968_.m_123342_();
      double d1 = (double)p_36968_.m_123343_();
      double d2 = d0 - this.m_20185_();
      double d3 = d1 - this.m_20189_();
      double d4 = Math.sqrt(d2 * d2 + d3 * d3);
      if (d4 > 12.0D) {
         this.f_36950_ = this.m_20185_() + d2 / d4 * 12.0D;
         this.f_36952_ = this.m_20189_() + d3 / d4 * 12.0D;
         this.f_36951_ = this.m_20186_() + 8.0D;
      } else {
         this.f_36950_ = d0;
         this.f_36951_ = (double)i;
         this.f_36952_ = d1;
      }

      this.f_36953_ = 0;
      this.f_36954_ = this.f_19796_.nextInt(5) > 0;
   }

   public void m_6001_(double p_36984_, double p_36985_, double p_36986_) {
      this.m_20334_(p_36984_, p_36985_, p_36986_);
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double d0 = Math.sqrt(p_36984_ * p_36984_ + p_36986_ * p_36986_);
         this.m_146922_((float)(Mth.m_14136_(p_36984_, p_36986_) * (double)(180F / (float)Math.PI)));
         this.m_146926_((float)(Mth.m_14136_(p_36985_, d0) * (double)(180F / (float)Math.PI)));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }

   }

   public void m_8119_() {
      super.m_8119_();
      Vec3 vec3 = this.m_20184_();
      double d0 = this.m_20185_() + vec3.f_82479_;
      double d1 = this.m_20186_() + vec3.f_82480_;
      double d2 = this.m_20189_() + vec3.f_82481_;
      double d3 = vec3.m_165924_();
      this.m_146926_(Projectile.m_37273_(this.f_19860_, (float)(Mth.m_14136_(vec3.f_82480_, d3) * (double)(180F / (float)Math.PI))));
      this.m_146922_(Projectile.m_37273_(this.f_19859_, (float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * (double)(180F / (float)Math.PI))));
      if (!this.f_19853_.f_46443_) {
         double d4 = this.f_36950_ - d0;
         double d5 = this.f_36952_ - d2;
         float f = (float)Math.sqrt(d4 * d4 + d5 * d5);
         float f1 = (float)Mth.m_14136_(d5, d4);
         double d6 = Mth.m_14139_(0.0025D, d3, (double)f);
         double d7 = vec3.f_82480_;
         if (f < 1.0F) {
            d6 *= 0.8D;
            d7 *= 0.8D;
         }

         int j = this.m_20186_() < this.f_36951_ ? 1 : -1;
         vec3 = new Vec3(Math.cos((double)f1) * d6, d7 + ((double)j - d7) * (double)0.015F, Math.sin((double)f1) * d6);
         this.m_20256_(vec3);
      }

      float f2 = 0.25F;
      if (this.m_20069_()) {
         for(int i = 0; i < 4; ++i) {
            this.f_19853_.m_7106_(ParticleTypes.f_123795_, d0 - vec3.f_82479_ * 0.25D, d1 - vec3.f_82480_ * 0.25D, d2 - vec3.f_82481_ * 0.25D, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
         }
      } else {
         this.f_19853_.m_7106_(ParticleTypes.f_123760_, d0 - vec3.f_82479_ * 0.25D + this.f_19796_.nextDouble() * 0.6D - 0.3D, d1 - vec3.f_82480_ * 0.25D - 0.5D, d2 - vec3.f_82481_ * 0.25D + this.f_19796_.nextDouble() * 0.6D - 0.3D, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
      }

      if (!this.f_19853_.f_46443_) {
         this.m_6034_(d0, d1, d2);
         ++this.f_36953_;
         if (this.f_36953_ > 80 && !this.f_19853_.f_46443_) {
            this.m_5496_(SoundEvents.f_11897_, 1.0F, 1.0F);
            this.m_146870_();
            if (this.f_36954_) {
               this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7846_()));
            } else {
               this.f_19853_.m_46796_(2003, this.m_142538_(), 0);
            }
         }
      } else {
         this.m_20343_(d0, d1, d2);
      }

   }

   public void m_7380_(CompoundTag p_36975_) {
      ItemStack itemstack = this.m_36981_();
      if (!itemstack.m_41619_()) {
         p_36975_.m_128365_("Item", itemstack.m_41739_(new CompoundTag()));
      }

   }

   public void m_7378_(CompoundTag p_36970_) {
      ItemStack itemstack = ItemStack.m_41712_(p_36970_.m_128469_("Item"));
      this.m_36972_(itemstack);
   }

   public float m_6073_() {
      return 1.0F;
   }

   public boolean m_6097_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }
}