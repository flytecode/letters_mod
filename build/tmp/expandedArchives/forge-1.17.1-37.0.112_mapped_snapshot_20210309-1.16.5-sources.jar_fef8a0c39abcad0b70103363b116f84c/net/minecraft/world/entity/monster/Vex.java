package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class Vex extends Monster {
   public static final float f_149863_ = 45.836624F;
   public static final int f_149864_ = Mth.m_14167_(3.9269907F);
   protected static final EntityDataAccessor<Byte> f_33977_ = SynchedEntityData.m_135353_(Vex.class, EntityDataSerializers.f_135027_);
   private static final int f_149865_ = 1;
   Mob f_33980_;
   @Nullable
   private BlockPos f_33981_;
   private boolean f_33978_;
   private int f_33979_;

   public Vex(EntityType<? extends Vex> p_33984_, Level p_33985_) {
      super(p_33984_, p_33985_);
      this.f_21342_ = new Vex.VexMoveControl(this);
      this.f_21364_ = 3;
   }

   public boolean m_142039_() {
      return this.f_19797_ % f_149864_ == 0;
   }

   public void m_6478_(MoverType p_33997_, Vec3 p_33998_) {
      super.m_6478_(p_33997_, p_33998_);
      this.m_20101_();
   }

   public void m_8119_() {
      this.f_19794_ = true;
      super.m_8119_();
      this.f_19794_ = false;
      this.m_20242_(true);
      if (this.f_33978_ && --this.f_33979_ <= 0) {
         this.f_33979_ = 20;
         this.m_6469_(DamageSource.f_19313_, 1.0F);
      }

   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(4, new Vex.VexChargeAttackGoal());
      this.f_21345_.m_25352_(8, new Vex.VexRandomMoveGoal());
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Raider.class)).m_26044_());
      this.f_21346_.m_25352_(2, new Vex.VexCopyOwnerTargetGoal(this));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
   }

   public static AttributeSupplier.Builder m_34040_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 14.0D).m_22268_(Attributes.f_22281_, 4.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33977_, (byte)0);
   }

   public void m_7378_(CompoundTag p_34008_) {
      super.m_7378_(p_34008_);
      if (p_34008_.m_128441_("BoundX")) {
         this.f_33981_ = new BlockPos(p_34008_.m_128451_("BoundX"), p_34008_.m_128451_("BoundY"), p_34008_.m_128451_("BoundZ"));
      }

      if (p_34008_.m_128441_("LifeTicks")) {
         this.m_33987_(p_34008_.m_128451_("LifeTicks"));
      }

   }

   public void m_7380_(CompoundTag p_34015_) {
      super.m_7380_(p_34015_);
      if (this.f_33981_ != null) {
         p_34015_.m_128405_("BoundX", this.f_33981_.m_123341_());
         p_34015_.m_128405_("BoundY", this.f_33981_.m_123342_());
         p_34015_.m_128405_("BoundZ", this.f_33981_.m_123343_());
      }

      if (this.f_33978_) {
         p_34015_.m_128405_("LifeTicks", this.f_33979_);
      }

   }

   public Mob m_34026_() {
      return this.f_33980_;
   }

   @Nullable
   public BlockPos m_34027_() {
      return this.f_33981_;
   }

   public void m_34033_(@Nullable BlockPos p_34034_) {
      this.f_33981_ = p_34034_;
   }

   private boolean m_34010_(int p_34011_) {
      int i = this.f_19804_.m_135370_(f_33977_);
      return (i & p_34011_) != 0;
   }

   private void m_33989_(int p_33990_, boolean p_33991_) {
      int i = this.f_19804_.m_135370_(f_33977_);
      if (p_33991_) {
         i = i | p_33990_;
      } else {
         i = i & ~p_33990_;
      }

      this.f_19804_.m_135381_(f_33977_, (byte)(i & 255));
   }

   public boolean m_34028_() {
      return this.m_34010_(1);
   }

   public void m_34042_(boolean p_34043_) {
      this.m_33989_(1, p_34043_);
   }

   public void m_33994_(Mob p_33995_) {
      this.f_33980_ = p_33995_;
   }

   public void m_33987_(int p_33988_) {
      this.f_33978_ = true;
      this.f_33979_ = p_33988_;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12499_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12501_;
   }

   protected SoundEvent m_7975_(DamageSource p_34023_) {
      return SoundEvents.f_12502_;
   }

   public float m_6073_() {
      return 1.0F;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_34002_, DifficultyInstance p_34003_, MobSpawnType p_34004_, @Nullable SpawnGroupData p_34005_, @Nullable CompoundTag p_34006_) {
      this.m_6851_(p_34003_);
      this.m_6850_(p_34003_);
      return super.m_6518_(p_34002_, p_34003_, p_34004_, p_34005_, p_34006_);
   }

   protected void m_6851_(DifficultyInstance p_33993_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42383_));
      this.m_21409_(EquipmentSlot.MAINHAND, 0.0F);
   }

   class VexChargeAttackGoal extends Goal {
      public VexChargeAttackGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         if (Vex.this.m_5448_() != null && !Vex.this.m_21566_().m_24995_() && Vex.this.f_19796_.nextInt(7) == 0) {
            return Vex.this.m_20280_(Vex.this.m_5448_()) > 4.0D;
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return Vex.this.m_21566_().m_24995_() && Vex.this.m_34028_() && Vex.this.m_5448_() != null && Vex.this.m_5448_().m_6084_();
      }

      public void m_8056_() {
         LivingEntity livingentity = Vex.this.m_5448_();
         Vec3 vec3 = livingentity.m_146892_();
         Vex.this.f_21342_.m_6849_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1.0D);
         Vex.this.m_34042_(true);
         Vex.this.m_5496_(SoundEvents.f_12500_, 1.0F, 1.0F);
      }

      public void m_8041_() {
         Vex.this.m_34042_(false);
      }

      public void m_8037_() {
         LivingEntity livingentity = Vex.this.m_5448_();
         if (Vex.this.m_142469_().m_82381_(livingentity.m_142469_())) {
            Vex.this.m_7327_(livingentity);
            Vex.this.m_34042_(false);
         } else {
            double d0 = Vex.this.m_20280_(livingentity);
            if (d0 < 9.0D) {
               Vec3 vec3 = livingentity.m_146892_();
               Vex.this.f_21342_.m_6849_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1.0D);
            }
         }

      }
   }

   class VexCopyOwnerTargetGoal extends TargetGoal {
      private final TargetingConditions f_34053_ = TargetingConditions.m_148353_().m_148355_().m_26893_();

      public VexCopyOwnerTargetGoal(PathfinderMob p_34056_) {
         super(p_34056_, false);
      }

      public boolean m_8036_() {
         return Vex.this.f_33980_ != null && Vex.this.f_33980_.m_5448_() != null && this.m_26150_(Vex.this.f_33980_.m_5448_(), this.f_34053_);
      }

      public void m_8056_() {
         Vex.this.m_6710_(Vex.this.f_33980_.m_5448_());
         super.m_8056_();
      }
   }

   class VexMoveControl extends MoveControl {
      public VexMoveControl(Vex p_34062_) {
         super(p_34062_);
      }

      public void m_8126_() {
         if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.f_24975_ - Vex.this.m_20185_(), this.f_24976_ - Vex.this.m_20186_(), this.f_24977_ - Vex.this.m_20189_());
            double d0 = vec3.m_82553_();
            if (d0 < Vex.this.m_142469_().m_82309_()) {
               this.f_24981_ = MoveControl.Operation.WAIT;
               Vex.this.m_20256_(Vex.this.m_20184_().m_82490_(0.5D));
            } else {
               Vex.this.m_20256_(Vex.this.m_20184_().m_82549_(vec3.m_82490_(this.f_24978_ * 0.05D / d0)));
               if (Vex.this.m_5448_() == null) {
                  Vec3 vec31 = Vex.this.m_20184_();
                  Vex.this.m_146922_(-((float)Mth.m_14136_(vec31.f_82479_, vec31.f_82481_)) * (180F / (float)Math.PI));
                  Vex.this.f_20883_ = Vex.this.m_146908_();
               } else {
                  double d2 = Vex.this.m_5448_().m_20185_() - Vex.this.m_20185_();
                  double d1 = Vex.this.m_5448_().m_20189_() - Vex.this.m_20189_();
                  Vex.this.m_146922_(-((float)Mth.m_14136_(d2, d1)) * (180F / (float)Math.PI));
                  Vex.this.f_20883_ = Vex.this.m_146908_();
               }
            }

         }
      }
   }

   class VexRandomMoveGoal extends Goal {
      public VexRandomMoveGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         return !Vex.this.m_21566_().m_24995_() && Vex.this.f_19796_.nextInt(7) == 0;
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8037_() {
         BlockPos blockpos = Vex.this.m_34027_();
         if (blockpos == null) {
            blockpos = Vex.this.m_142538_();
         }

         for(int i = 0; i < 3; ++i) {
            BlockPos blockpos1 = blockpos.m_142082_(Vex.this.f_19796_.nextInt(15) - 7, Vex.this.f_19796_.nextInt(11) - 5, Vex.this.f_19796_.nextInt(15) - 7);
            if (Vex.this.f_19853_.m_46859_(blockpos1)) {
               Vex.this.f_21342_.m_6849_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D, 0.25D);
               if (Vex.this.m_5448_() == null) {
                  Vex.this.m_21563_().m_24950_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D, 180.0F, 20.0F);
               }
               break;
            }
         }

      }
   }
}