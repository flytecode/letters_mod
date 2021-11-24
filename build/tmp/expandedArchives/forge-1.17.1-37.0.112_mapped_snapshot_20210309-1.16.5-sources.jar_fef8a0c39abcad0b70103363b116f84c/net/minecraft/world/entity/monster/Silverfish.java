package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Silverfish extends Monster {
   private Silverfish.SilverfishWakeUpFriendsGoal f_33521_;

   public Silverfish(EntityType<? extends Silverfish> p_33523_, Level p_33524_) {
      super(p_33523_, p_33524_);
   }

   protected void m_8099_() {
      this.f_33521_ = new Silverfish.SilverfishWakeUpFriendsGoal(this);
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, this.f_33521_);
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(5, new Silverfish.SilverfishMergeWithStoneGoal(this));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_());
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
   }

   public double m_6049_() {
      return 0.1D;
   }

   protected float m_6431_(Pose p_33540_, EntityDimensions p_33541_) {
      return 0.13F;
   }

   public static AttributeSupplier.Builder m_33551_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 8.0D).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 1.0D);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12419_;
   }

   protected SoundEvent m_7975_(DamageSource p_33549_) {
      return SoundEvents.f_12421_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12420_;
   }

   protected void m_7355_(BlockPos p_33543_, BlockState p_33544_) {
      this.m_5496_(SoundEvents.f_12422_, 0.15F, 1.0F);
   }

   public boolean m_6469_(DamageSource p_33527_, float p_33528_) {
      if (this.m_6673_(p_33527_)) {
         return false;
      } else {
         if ((p_33527_ instanceof EntityDamageSource || p_33527_ == DamageSource.f_19319_) && this.f_33521_ != null) {
            this.f_33521_.m_33568_();
         }

         return super.m_6469_(p_33527_, p_33528_);
      }
   }

   public void m_8119_() {
      this.f_20883_ = this.m_146908_();
      super.m_8119_();
   }

   public void m_5618_(float p_33553_) {
      this.m_146922_(p_33553_);
      super.m_5618_(p_33553_);
   }

   public float m_5610_(BlockPos p_33530_, LevelReader p_33531_) {
      return InfestedBlock.m_54195_(p_33531_.m_8055_(p_33530_.m_7495_())) ? 10.0F : super.m_5610_(p_33530_, p_33531_);
   }

   public static boolean m_33533_(EntityType<Silverfish> p_33534_, LevelAccessor p_33535_, MobSpawnType p_33536_, BlockPos p_33537_, Random p_33538_) {
      if (m_33023_(p_33534_, p_33535_, p_33536_, p_33537_, p_33538_)) {
         Player player = p_33535_.m_45924_((double)p_33537_.m_123341_() + 0.5D, (double)p_33537_.m_123342_() + 0.5D, (double)p_33537_.m_123343_() + 0.5D, 5.0D, true);
         return player == null;
      } else {
         return false;
      }
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   static class SilverfishMergeWithStoneGoal extends RandomStrollGoal {
      private Direction f_33555_;
      private boolean f_33556_;

      public SilverfishMergeWithStoneGoal(Silverfish p_33558_) {
         super(p_33558_, 1.0D, 10);
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         if (this.f_25725_.m_5448_() != null) {
            return false;
         } else if (!this.f_25725_.m_21573_().m_26571_()) {
            return false;
         } else {
            Random random = this.f_25725_.m_21187_();
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_25725_.f_19853_, this.f_25725_) && random.nextInt(10) == 0) {
               this.f_33555_ = Direction.m_122404_(random);
               BlockPos blockpos = (new BlockPos(this.f_25725_.m_20185_(), this.f_25725_.m_20186_() + 0.5D, this.f_25725_.m_20189_())).m_142300_(this.f_33555_);
               BlockState blockstate = this.f_25725_.f_19853_.m_8055_(blockpos);
               if (InfestedBlock.m_54195_(blockstate)) {
                  this.f_33556_ = true;
                  return true;
               }
            }

            this.f_33556_ = false;
            return super.m_8036_();
         }
      }

      public boolean m_8045_() {
         return this.f_33556_ ? false : super.m_8045_();
      }

      public void m_8056_() {
         if (!this.f_33556_) {
            super.m_8056_();
         } else {
            LevelAccessor levelaccessor = this.f_25725_.f_19853_;
            BlockPos blockpos = (new BlockPos(this.f_25725_.m_20185_(), this.f_25725_.m_20186_() + 0.5D, this.f_25725_.m_20189_())).m_142300_(this.f_33555_);
            BlockState blockstate = levelaccessor.m_8055_(blockpos);
            if (InfestedBlock.m_54195_(blockstate)) {
               levelaccessor.m_7731_(blockpos, InfestedBlock.m_153430_(blockstate), 3);
               this.f_25725_.m_21373_();
               this.f_25725_.m_146870_();
            }

         }
      }
   }

   static class SilverfishWakeUpFriendsGoal extends Goal {
      private final Silverfish f_33562_;
      private int f_33563_;

      public SilverfishWakeUpFriendsGoal(Silverfish p_33565_) {
         this.f_33562_ = p_33565_;
      }

      public void m_33568_() {
         if (this.f_33563_ == 0) {
            this.f_33563_ = 20;
         }

      }

      public boolean m_8036_() {
         return this.f_33563_ > 0;
      }

      public void m_8037_() {
         --this.f_33563_;
         if (this.f_33563_ <= 0) {
            Level level = this.f_33562_.f_19853_;
            Random random = this.f_33562_.m_21187_();
            BlockPos blockpos = this.f_33562_.m_142538_();

            for(int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
               for(int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                  for(int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                     BlockPos blockpos1 = blockpos.m_142082_(j, i, k);
                     BlockState blockstate = level.m_8055_(blockpos1);
                     Block block = blockstate.m_60734_();
                     if (block instanceof InfestedBlock) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, this.f_33562_)) {
                           level.m_46953_(blockpos1, true, this.f_33562_);
                        } else {
                           level.m_7731_(blockpos1, ((InfestedBlock)block).m_153432_(level.m_8055_(blockpos1)), 3);
                        }

                        if (random.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }

      }
   }
}
