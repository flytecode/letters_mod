package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownTrident extends AbstractArrow {
   private static final EntityDataAccessor<Byte> f_37558_ = SynchedEntityData.m_135353_(ThrownTrident.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Boolean> f_37554_ = SynchedEntityData.m_135353_(ThrownTrident.class, EntityDataSerializers.f_135035_);
   private ItemStack f_37555_ = new ItemStack(Items.f_42713_);
   private boolean f_37556_;
   public int f_37557_;

   public ThrownTrident(EntityType<? extends ThrownTrident> p_37561_, Level p_37562_) {
      super(p_37561_, p_37562_);
   }

   public ThrownTrident(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_) {
      super(EntityType.f_20487_, p_37570_, p_37569_);
      this.f_37555_ = p_37571_.m_41777_();
      this.f_19804_.m_135381_(f_37558_, (byte)EnchantmentHelper.m_44928_(p_37571_));
      this.f_19804_.m_135381_(f_37554_, p_37571_.m_41790_());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_37558_, (byte)0);
      this.f_19804_.m_135372_(f_37554_, false);
   }

   public void m_8119_() {
      if (this.f_36704_ > 4) {
         this.f_37556_ = true;
      }

      Entity entity = this.m_37282_();
      int i = this.f_19804_.m_135370_(f_37558_);
      if (i > 0 && (this.f_37556_ || this.m_36797_()) && entity != null) {
         if (!this.m_37594_()) {
            if (!this.f_19853_.f_46443_ && this.f_36705_ == AbstractArrow.Pickup.ALLOWED) {
               this.m_5552_(this.m_7941_(), 0.1F);
            }

            this.m_146870_();
         } else {
            this.m_36790_(true);
            Vec3 vec3 = entity.m_146892_().m_82546_(this.m_20182_());
            this.m_20343_(this.m_20185_(), this.m_20186_() + vec3.f_82480_ * 0.015D * (double)i, this.m_20189_());
            if (this.f_19853_.f_46443_) {
               this.f_19791_ = this.m_20186_();
            }

            double d0 = 0.05D * (double)i;
            this.m_20256_(this.m_20184_().m_82490_(0.95D).m_82549_(vec3.m_82541_().m_82490_(d0)));
            if (this.f_37557_ == 0) {
               this.m_5496_(SoundEvents.f_12516_, 10.0F, 1.0F);
            }

            ++this.f_37557_;
         }
      }

      super.m_8119_();
   }

   private boolean m_37594_() {
      Entity entity = this.m_37282_();
      if (entity != null && entity.m_6084_()) {
         return !(entity instanceof ServerPlayer) || !entity.m_5833_();
      } else {
         return false;
      }
   }

   protected ItemStack m_7941_() {
      return this.f_37555_.m_41777_();
   }

   public boolean m_37593_() {
      return this.f_19804_.m_135370_(f_37554_);
   }

   @Nullable
   protected EntityHitResult m_6351_(Vec3 p_37575_, Vec3 p_37576_) {
      return this.f_37556_ ? null : super.m_6351_(p_37575_, p_37576_);
   }

   protected void m_5790_(EntityHitResult p_37573_) {
      Entity entity = p_37573_.m_82443_();
      float f = 8.0F;
      if (entity instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)entity;
         f += EnchantmentHelper.m_44833_(this.f_37555_, livingentity.m_6336_());
      }

      Entity entity1 = this.m_37282_();
      DamageSource damagesource = DamageSource.m_19337_(this, (Entity)(entity1 == null ? this : entity1));
      this.f_37556_ = true;
      SoundEvent soundevent = SoundEvents.f_12514_;
      if (entity.m_6469_(damagesource, f)) {
         if (entity.m_6095_() == EntityType.f_20566_) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity1, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity1);
            }

            this.m_7761_(livingentity1);
         }
      }

      this.m_20256_(this.m_20184_().m_82542_(-0.01D, -0.1D, -0.01D));
      float f1 = 1.0F;
      if (this.f_19853_ instanceof ServerLevel && this.f_19853_.m_46470_() && this.m_150194_()) {
         BlockPos blockpos = entity.m_142538_();
         if (this.f_19853_.m_45527_(blockpos)) {
            LightningBolt lightningbolt = EntityType.f_20465_.m_20615_(this.f_19853_);
            lightningbolt.m_20219_(Vec3.m_82539_(blockpos));
            lightningbolt.m_20879_(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
            this.f_19853_.m_7967_(lightningbolt);
            soundevent = SoundEvents.f_12521_;
            f1 = 5.0F;
         }
      }

      this.m_5496_(soundevent, f1, 1.0F);
   }

   public boolean m_150194_() {
      return EnchantmentHelper.m_44936_(this.f_37555_);
   }

   protected boolean m_142470_(Player p_150196_) {
      return super.m_142470_(p_150196_) || this.m_36797_() && this.m_150171_(p_150196_) && p_150196_.m_150109_().m_36054_(this.m_7941_());
   }

   protected SoundEvent m_7239_() {
      return SoundEvents.f_12515_;
   }

   public void m_6123_(Player p_37580_) {
      if (this.m_150171_(p_37580_) || this.m_37282_() == null) {
         super.m_6123_(p_37580_);
      }

   }

   public void m_7378_(CompoundTag p_37578_) {
      super.m_7378_(p_37578_);
      if (p_37578_.m_128425_("Trident", 10)) {
         this.f_37555_ = ItemStack.m_41712_(p_37578_.m_128469_("Trident"));
      }

      this.f_37556_ = p_37578_.m_128471_("DealtDamage");
      this.f_19804_.m_135381_(f_37558_, (byte)EnchantmentHelper.m_44928_(this.f_37555_));
   }

   public void m_7380_(CompoundTag p_37582_) {
      super.m_7380_(p_37582_);
      p_37582_.m_128365_("Trident", this.f_37555_.m_41739_(new CompoundTag()));
      p_37582_.m_128379_("DealtDamage", this.f_37556_);
   }

   public void m_6901_() {
      int i = this.f_19804_.m_135370_(f_37558_);
      if (this.f_36705_ != AbstractArrow.Pickup.ALLOWED || i <= 0) {
         super.m_6901_();
      }

   }

   protected float m_6882_() {
      return 0.99F;
   }

   public boolean m_6000_(double p_37588_, double p_37589_, double p_37590_) {
      return true;
   }
}