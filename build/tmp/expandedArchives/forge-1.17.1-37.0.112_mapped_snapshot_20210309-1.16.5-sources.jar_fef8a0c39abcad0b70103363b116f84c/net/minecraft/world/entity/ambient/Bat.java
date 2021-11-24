package net.minecraft.world.entity.ambient;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Bat extends AmbientCreature {
   public static final float f_148698_ = 74.48451F;
   public static final int f_148699_ = Mth.m_14167_(2.4166098F);
   private static final EntityDataAccessor<Byte> f_27407_ = SynchedEntityData.m_135353_(Bat.class, EntityDataSerializers.f_135027_);
   private static final int f_148700_ = 1;
   private static final TargetingConditions f_27408_ = TargetingConditions.m_148353_().m_26883_(4.0D);
   private BlockPos f_27409_;

   public Bat(EntityType<? extends Bat> p_27412_, Level p_27413_) {
      super(p_27412_, p_27413_);
      this.m_27456_(true);
   }

   public boolean m_142039_() {
      return !this.m_27452_() && this.f_19797_ % f_148699_ == 0;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_27407_, (byte)0);
   }

   protected float m_6121_() {
      return 0.1F;
   }

   public float m_6100_() {
      return super.m_6100_() * 0.95F;
   }

   @Nullable
   public SoundEvent m_7515_() {
      return this.m_27452_() && this.f_19796_.nextInt(4) != 0 ? null : SoundEvents.f_11731_;
   }

   protected SoundEvent m_7975_(DamageSource p_27451_) {
      return SoundEvents.f_11733_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11732_;
   }

   public boolean m_6094_() {
      return false;
   }

   protected void m_7324_(Entity p_27415_) {
   }

   protected void m_6138_() {
   }

   public static AttributeSupplier.Builder m_27455_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 6.0D);
   }

   public boolean m_27452_() {
      return (this.f_19804_.m_135370_(f_27407_) & 1) != 0;
   }

   public void m_27456_(boolean p_27457_) {
      byte b0 = this.f_19804_.m_135370_(f_27407_);
      if (p_27457_) {
         this.f_19804_.m_135381_(f_27407_, (byte)(b0 | 1));
      } else {
         this.f_19804_.m_135381_(f_27407_, (byte)(b0 & -2));
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_27452_()) {
         this.m_20256_(Vec3.f_82478_);
         this.m_20343_(this.m_20185_(), (double)Mth.m_14107_(this.m_20186_()) + 1.0D - (double)this.m_20206_(), this.m_20189_());
      } else {
         this.m_20256_(this.m_20184_().m_82542_(1.0D, 0.6D, 1.0D));
      }

   }

   protected void m_8024_() {
      super.m_8024_();
      BlockPos blockpos = this.m_142538_();
      BlockPos blockpos1 = blockpos.m_7494_();
      if (this.m_27452_()) {
         boolean flag = this.m_20067_();
         if (this.f_19853_.m_8055_(blockpos1).m_60796_(this.f_19853_, blockpos)) {
            if (this.f_19796_.nextInt(200) == 0) {
               this.f_20885_ = (float)this.f_19796_.nextInt(360);
            }

            if (this.f_19853_.m_45946_(f_27408_, this) != null) {
               this.m_27456_(false);
               if (!flag) {
                  this.f_19853_.m_5898_((Player)null, 1025, blockpos, 0);
               }
            }
         } else {
            this.m_27456_(false);
            if (!flag) {
               this.f_19853_.m_5898_((Player)null, 1025, blockpos, 0);
            }
         }
      } else {
         if (this.f_27409_ != null && (!this.f_19853_.m_46859_(this.f_27409_) || this.f_27409_.m_123342_() <= this.f_19853_.m_141937_())) {
            this.f_27409_ = null;
         }

         if (this.f_27409_ == null || this.f_19796_.nextInt(30) == 0 || this.f_27409_.m_123306_(this.m_20182_(), 2.0D)) {
            this.f_27409_ = new BlockPos(this.m_20185_() + (double)this.f_19796_.nextInt(7) - (double)this.f_19796_.nextInt(7), this.m_20186_() + (double)this.f_19796_.nextInt(6) - 2.0D, this.m_20189_() + (double)this.f_19796_.nextInt(7) - (double)this.f_19796_.nextInt(7));
         }

         double d2 = (double)this.f_27409_.m_123341_() + 0.5D - this.m_20185_();
         double d0 = (double)this.f_27409_.m_123342_() + 0.1D - this.m_20186_();
         double d1 = (double)this.f_27409_.m_123343_() + 0.5D - this.m_20189_();
         Vec3 vec3 = this.m_20184_();
         Vec3 vec31 = vec3.m_82520_((Math.signum(d2) * 0.5D - vec3.f_82479_) * (double)0.1F, (Math.signum(d0) * (double)0.7F - vec3.f_82480_) * (double)0.1F, (Math.signum(d1) * 0.5D - vec3.f_82481_) * (double)0.1F);
         this.m_20256_(vec31);
         float f = (float)(Mth.m_14136_(vec31.f_82481_, vec31.f_82479_) * (double)(180F / (float)Math.PI)) - 90.0F;
         float f1 = Mth.m_14177_(f - this.m_146908_());
         this.f_20902_ = 0.5F;
         this.m_146922_(this.m_146908_() + f1);
         if (this.f_19796_.nextInt(100) == 0 && this.f_19853_.m_8055_(blockpos1).m_60796_(this.f_19853_, blockpos1)) {
            this.m_27456_(true);
         }
      }

   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   public boolean m_142535_(float p_148702_, float p_148703_, DamageSource p_148704_) {
      return false;
   }

   protected void m_7840_(double p_27419_, boolean p_27420_, BlockState p_27421_, BlockPos p_27422_) {
   }

   public boolean m_6090_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_27424_, float p_27425_) {
      if (this.m_6673_(p_27424_)) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_ && this.m_27452_()) {
            this.m_27456_(false);
         }

         return super.m_6469_(p_27424_, p_27425_);
      }
   }

   public void m_7378_(CompoundTag p_27427_) {
      super.m_7378_(p_27427_);
      this.f_19804_.m_135381_(f_27407_, p_27427_.m_128445_("BatFlags"));
   }

   public void m_7380_(CompoundTag p_27443_) {
      super.m_7380_(p_27443_);
      p_27443_.m_128344_("BatFlags", this.f_19804_.m_135370_(f_27407_));
   }

   public static boolean m_27433_(EntityType<Bat> p_27434_, LevelAccessor p_27435_, MobSpawnType p_27436_, BlockPos p_27437_, Random p_27438_) {
      if (p_27437_.m_123342_() >= p_27435_.m_5736_()) {
         return false;
      } else {
         int i = p_27435_.m_46803_(p_27437_);
         int j = 4;
         if (m_27453_()) {
            j = 7;
         } else if (p_27438_.nextBoolean()) {
            return false;
         }

         return i > p_27438_.nextInt(j) ? false : m_21400_(p_27434_, p_27435_, p_27436_, p_27437_, p_27438_);
      }
   }

   private static boolean m_27453_() {
      LocalDate localdate = LocalDate.now();
      int i = localdate.get(ChronoField.DAY_OF_MONTH);
      int j = localdate.get(ChronoField.MONTH_OF_YEAR);
      return j == 10 && i >= 20 || j == 11 && i <= 3;
   }

   protected float m_6431_(Pose p_27440_, EntityDimensions p_27441_) {
      return p_27441_.f_20378_ / 2.0F;
   }
}