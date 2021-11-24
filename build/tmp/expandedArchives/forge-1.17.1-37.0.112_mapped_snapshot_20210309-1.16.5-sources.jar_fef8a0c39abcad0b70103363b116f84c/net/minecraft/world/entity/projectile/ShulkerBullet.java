package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ShulkerBullet extends Projectile {
   private static final double f_150183_ = 0.15D;
   @Nullable
   private Entity f_37312_;
   @Nullable
   private Direction f_37313_;
   private int f_37314_;
   private double f_37315_;
   private double f_37316_;
   private double f_37317_;
   @Nullable
   private UUID f_37311_;

   public ShulkerBullet(EntityType<? extends ShulkerBullet> p_37319_, Level p_37320_) {
      super(p_37319_, p_37320_);
      this.f_19794_ = true;
   }

   public ShulkerBullet(Level p_37330_, LivingEntity p_37331_, Entity p_37332_, Direction.Axis p_37333_) {
      this(EntityType.f_20522_, p_37330_);
      this.m_5602_(p_37331_);
      BlockPos blockpos = p_37331_.m_142538_();
      double d0 = (double)blockpos.m_123341_() + 0.5D;
      double d1 = (double)blockpos.m_123342_() + 0.5D;
      double d2 = (double)blockpos.m_123343_() + 0.5D;
      this.m_7678_(d0, d1, d2, this.m_146908_(), this.m_146909_());
      this.f_37312_ = p_37332_;
      this.f_37313_ = Direction.UP;
      this.m_37348_(p_37333_);
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected void m_7380_(CompoundTag p_37357_) {
      super.m_7380_(p_37357_);
      if (this.f_37312_ != null) {
         p_37357_.m_128362_("Target", this.f_37312_.m_142081_());
      }

      if (this.f_37313_ != null) {
         p_37357_.m_128405_("Dir", this.f_37313_.m_122411_());
      }

      p_37357_.m_128405_("Steps", this.f_37314_);
      p_37357_.m_128347_("TXD", this.f_37315_);
      p_37357_.m_128347_("TYD", this.f_37316_);
      p_37357_.m_128347_("TZD", this.f_37317_);
   }

   protected void m_7378_(CompoundTag p_37353_) {
      super.m_7378_(p_37353_);
      this.f_37314_ = p_37353_.m_128451_("Steps");
      this.f_37315_ = p_37353_.m_128459_("TXD");
      this.f_37316_ = p_37353_.m_128459_("TYD");
      this.f_37317_ = p_37353_.m_128459_("TZD");
      if (p_37353_.m_128425_("Dir", 99)) {
         this.f_37313_ = Direction.m_122376_(p_37353_.m_128451_("Dir"));
      }

      if (p_37353_.m_128403_("Target")) {
         this.f_37311_ = p_37353_.m_128342_("Target");
      }

   }

   protected void m_8097_() {
   }

   @Nullable
   private Direction m_150186_() {
      return this.f_37313_;
   }

   private void m_37350_(@Nullable Direction p_37351_) {
      this.f_37313_ = p_37351_;
   }

   private void m_37348_(@Nullable Direction.Axis p_37349_) {
      double d0 = 0.5D;
      BlockPos blockpos;
      if (this.f_37312_ == null) {
         blockpos = this.m_142538_().m_7495_();
      } else {
         d0 = (double)this.f_37312_.m_20206_() * 0.5D;
         blockpos = new BlockPos(this.f_37312_.m_20185_(), this.f_37312_.m_20186_() + d0, this.f_37312_.m_20189_());
      }

      double d1 = (double)blockpos.m_123341_() + 0.5D;
      double d2 = (double)blockpos.m_123342_() + d0;
      double d3 = (double)blockpos.m_123343_() + 0.5D;
      Direction direction = null;
      if (!blockpos.m_123306_(this.m_20182_(), 2.0D)) {
         BlockPos blockpos1 = this.m_142538_();
         List<Direction> list = Lists.newArrayList();
         if (p_37349_ != Direction.Axis.X) {
            if (blockpos1.m_123341_() < blockpos.m_123341_() && this.f_19853_.m_46859_(blockpos1.m_142126_())) {
               list.add(Direction.EAST);
            } else if (blockpos1.m_123341_() > blockpos.m_123341_() && this.f_19853_.m_46859_(blockpos1.m_142125_())) {
               list.add(Direction.WEST);
            }
         }

         if (p_37349_ != Direction.Axis.Y) {
            if (blockpos1.m_123342_() < blockpos.m_123342_() && this.f_19853_.m_46859_(blockpos1.m_7494_())) {
               list.add(Direction.UP);
            } else if (blockpos1.m_123342_() > blockpos.m_123342_() && this.f_19853_.m_46859_(blockpos1.m_7495_())) {
               list.add(Direction.DOWN);
            }
         }

         if (p_37349_ != Direction.Axis.Z) {
            if (blockpos1.m_123343_() < blockpos.m_123343_() && this.f_19853_.m_46859_(blockpos1.m_142128_())) {
               list.add(Direction.SOUTH);
            } else if (blockpos1.m_123343_() > blockpos.m_123343_() && this.f_19853_.m_46859_(blockpos1.m_142127_())) {
               list.add(Direction.NORTH);
            }
         }

         direction = Direction.m_122404_(this.f_19796_);
         if (list.isEmpty()) {
            for(int i = 5; !this.f_19853_.m_46859_(blockpos1.m_142300_(direction)) && i > 0; --i) {
               direction = Direction.m_122404_(this.f_19796_);
            }
         } else {
            direction = list.get(this.f_19796_.nextInt(list.size()));
         }

         d1 = this.m_20185_() + (double)direction.m_122429_();
         d2 = this.m_20186_() + (double)direction.m_122430_();
         d3 = this.m_20189_() + (double)direction.m_122431_();
      }

      this.m_37350_(direction);
      double d6 = d1 - this.m_20185_();
      double d7 = d2 - this.m_20186_();
      double d4 = d3 - this.m_20189_();
      double d5 = Math.sqrt(d6 * d6 + d7 * d7 + d4 * d4);
      if (d5 == 0.0D) {
         this.f_37315_ = 0.0D;
         this.f_37316_ = 0.0D;
         this.f_37317_ = 0.0D;
      } else {
         this.f_37315_ = d6 / d5 * 0.15D;
         this.f_37316_ = d7 / d5 * 0.15D;
         this.f_37317_ = d4 / d5 * 0.15D;
      }

      this.f_19812_ = true;
      this.f_37314_ = 10 + this.f_19796_.nextInt(5) * 10;
   }

   public void m_6043_() {
      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
         this.m_146870_();
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.f_37312_ == null && this.f_37311_ != null) {
            this.f_37312_ = ((ServerLevel)this.f_19853_).m_8791_(this.f_37311_);
            if (this.f_37312_ == null) {
               this.f_37311_ = null;
            }
         }

         if (this.f_37312_ == null || !this.f_37312_.m_6084_() || this.f_37312_ instanceof Player && this.f_37312_.m_5833_()) {
            if (!this.m_20068_()) {
               this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.04D, 0.0D));
            }
         } else {
            this.f_37315_ = Mth.m_14008_(this.f_37315_ * 1.025D, -1.0D, 1.0D);
            this.f_37316_ = Mth.m_14008_(this.f_37316_ * 1.025D, -1.0D, 1.0D);
            this.f_37317_ = Mth.m_14008_(this.f_37317_ * 1.025D, -1.0D, 1.0D);
            Vec3 vec3 = this.m_20184_();
            this.m_20256_(vec3.m_82520_((this.f_37315_ - vec3.f_82479_) * 0.2D, (this.f_37316_ - vec3.f_82480_) * 0.2D, (this.f_37317_ - vec3.f_82481_) * 0.2D));
         }

         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }
      }

      this.m_20101_();
      Vec3 vec31 = this.m_20184_();
      this.m_6034_(this.m_20185_() + vec31.f_82479_, this.m_20186_() + vec31.f_82480_, this.m_20189_() + vec31.f_82481_);
      ProjectileUtil.m_37284_(this, 0.5F);
      if (this.f_19853_.f_46443_) {
         this.f_19853_.m_7106_(ParticleTypes.f_123810_, this.m_20185_() - vec31.f_82479_, this.m_20186_() - vec31.f_82480_ + 0.15D, this.m_20189_() - vec31.f_82481_, 0.0D, 0.0D, 0.0D);
      } else if (this.f_37312_ != null && !this.f_37312_.m_146910_()) {
         if (this.f_37314_ > 0) {
            --this.f_37314_;
            if (this.f_37314_ == 0) {
               this.m_37348_(this.f_37313_ == null ? null : this.f_37313_.m_122434_());
            }
         }

         if (this.f_37313_ != null) {
            BlockPos blockpos = this.m_142538_();
            Direction.Axis direction$axis = this.f_37313_.m_122434_();
            if (this.f_19853_.m_46575_(blockpos.m_142300_(this.f_37313_), this)) {
               this.m_37348_(direction$axis);
            } else {
               BlockPos blockpos1 = this.f_37312_.m_142538_();
               if (direction$axis == Direction.Axis.X && blockpos.m_123341_() == blockpos1.m_123341_() || direction$axis == Direction.Axis.Z && blockpos.m_123343_() == blockpos1.m_123343_() || direction$axis == Direction.Axis.Y && blockpos.m_123342_() == blockpos1.m_123342_()) {
                  this.m_37348_(direction$axis);
               }
            }
         }
      }

   }

   protected boolean m_5603_(Entity p_37341_) {
      return super.m_5603_(p_37341_) && !p_37341_.f_19794_;
   }

   public boolean m_6060_() {
      return false;
   }

   public boolean m_6783_(double p_37336_) {
      return p_37336_ < 16384.0D;
   }

   public float m_6073_() {
      return 1.0F;
   }

   protected void m_5790_(EntityHitResult p_37345_) {
      super.m_5790_(p_37345_);
      Entity entity = p_37345_.m_82443_();
      Entity entity1 = this.m_37282_();
      LivingEntity livingentity = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;
      boolean flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_(), 4.0F);
      if (flag) {
         this.m_19970_(livingentity, entity);
         if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).m_147207_(new MobEffectInstance(MobEffects.f_19620_, 200), MoreObjects.firstNonNull(entity1, this));
         }
      }

   }

   protected void m_8060_(BlockHitResult p_37343_) {
      super.m_8060_(p_37343_);
      ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
      this.m_5496_(SoundEvents.f_12410_, 1.0F, 1.0F);
   }

   protected void m_6532_(HitResult p_37347_) {
      super.m_6532_(p_37347_);
      this.m_146870_();
   }

   public boolean m_6087_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_37338_, float p_37339_) {
      if (!this.f_19853_.f_46443_) {
         this.m_5496_(SoundEvents.f_12411_, 1.0F, 1.0F);
         ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123797_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
         this.m_146870_();
      }

      return true;
   }

   public void m_141965_(ClientboundAddEntityPacket p_150185_) {
      super.m_141965_(p_150185_);
      double d0 = p_150185_.m_131503_();
      double d1 = p_150185_.m_131504_();
      double d2 = p_150185_.m_131505_();
      this.m_20334_(d0, d1, d2);
   }
}
