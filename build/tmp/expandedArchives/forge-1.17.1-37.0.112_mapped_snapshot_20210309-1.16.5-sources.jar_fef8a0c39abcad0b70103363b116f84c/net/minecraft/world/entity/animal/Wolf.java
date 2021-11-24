package net.minecraft.world.entity.animal;

import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Wolf extends TamableAnimal implements NeutralMob {
   private static final EntityDataAccessor<Boolean> f_30358_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_30359_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_30360_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135028_);
   public static final Predicate<LivingEntity> f_30357_ = (p_30437_) -> {
      EntityType<?> entitytype = p_30437_.m_6095_();
      return entitytype == EntityType.f_20520_ || entitytype == EntityType.f_20517_ || entitytype == EntityType.f_20452_;
   };
   private static final float f_149082_ = 8.0F;
   private static final float f_149083_ = 20.0F;
   private float f_30361_;
   private float f_30362_;
   private boolean f_30363_;
   private boolean f_30364_;
   private float f_30365_;
   private float f_30366_;
   private static final UniformInt f_30355_ = TimeUtil.m_145020_(20, 39);
   private UUID f_30356_;

   public Wolf(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
      super(p_30369_, p_30370_);
      this.m_7105_(false);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new Wolf.WolfAvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5D, 1.5D));
      this.f_21345_.m_25352_(4, new LeapAtTargetGoal(this, 0.4F));
      this.f_21345_.m_25352_(5, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
      this.f_21345_.m_25352_(7, new BreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(9, new BegGoal(this, 8.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(3, (new HurtByTargetGoal(this)).m_26044_());
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::m_21674_));
      this.f_21346_.m_25352_(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, f_30357_));
      this.f_21346_.m_25352_(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.f_30122_));
      this.f_21346_.m_25352_(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
      this.f_21346_.m_25352_(8, new ResetUniversalAngerTargetGoal<>(this, true));
   }

   public static AttributeSupplier.Builder m_30425_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22276_, 8.0D).m_22268_(Attributes.f_22281_, 2.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_30358_, false);
      this.f_19804_.m_135372_(f_30359_, DyeColor.RED.m_41060_());
      this.f_19804_.m_135372_(f_30360_, 0);
   }

   protected void m_7355_(BlockPos p_30415_, BlockState p_30416_) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   public void m_7380_(CompoundTag p_30418_) {
      super.m_7380_(p_30418_);
      p_30418_.m_128344_("CollarColor", (byte)this.m_30428_().m_41060_());
      this.m_21678_(p_30418_);
   }

   public void m_7378_(CompoundTag p_30402_) {
      super.m_7378_(p_30402_);
      if (p_30402_.m_128425_("CollarColor", 99)) {
         this.m_30397_(DyeColor.m_41053_(p_30402_.m_128451_("CollarColor")));
      }

      this.m_147285_(this.f_19853_, p_30402_);
   }

   protected SoundEvent m_7515_() {
      if (this.m_21660_()) {
         return SoundEvents.f_12619_;
      } else if (this.f_19796_.nextInt(3) == 0) {
         return this.m_21824_() && this.m_21223_() < 10.0F ? SoundEvents.f_12625_ : SoundEvents.f_12622_;
      } else {
         return SoundEvents.f_12617_;
      }
   }

   protected SoundEvent m_7975_(DamageSource p_30424_) {
      return SoundEvents.f_12621_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12618_;
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.f_19853_.f_46443_ && this.f_30363_ && !this.f_30364_ && !this.m_21691_() && this.f_19861_) {
         this.f_30364_ = true;
         this.f_30365_ = 0.0F;
         this.f_30366_ = 0.0F;
         this.f_19853_.m_7605_(this, (byte)8);
      }

      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, true);
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_6084_()) {
         this.f_30362_ = this.f_30361_;
         if (this.m_30429_()) {
            this.f_30361_ += (1.0F - this.f_30361_) * 0.4F;
         } else {
            this.f_30361_ += (0.0F - this.f_30361_) * 0.4F;
         }

         if (this.m_20071_()) {
            this.f_30363_ = true;
            if (this.f_30364_ && !this.f_19853_.f_46443_) {
               this.f_19853_.m_7605_(this, (byte)56);
               this.m_30430_();
            }
         } else if ((this.f_30363_ || this.f_30364_) && this.f_30364_) {
            if (this.f_30365_ == 0.0F) {
               this.m_5496_(SoundEvents.f_12623_, this.m_6121_(), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
               this.m_146850_(GameEvent.f_157787_);
            }

            this.f_30366_ = this.f_30365_;
            this.f_30365_ += 0.05F;
            if (this.f_30366_ >= 2.0F) {
               this.f_30363_ = false;
               this.f_30364_ = false;
               this.f_30366_ = 0.0F;
               this.f_30365_ = 0.0F;
            }

            if (this.f_30365_ > 0.4F) {
               float f = (float)this.m_20186_();
               int i = (int)(Mth.m_14031_((this.f_30365_ - 0.4F) * (float)Math.PI) * 7.0F);
               Vec3 vec3 = this.m_20184_();

               for(int j = 0; j < i; ++j) {
                  float f1 = (this.f_19796_.nextFloat() * 2.0F - 1.0F) * this.m_20205_() * 0.5F;
                  float f2 = (this.f_19796_.nextFloat() * 2.0F - 1.0F) * this.m_20205_() * 0.5F;
                  this.f_19853_.m_7106_(ParticleTypes.f_123769_, this.m_20185_() + (double)f1, (double)(f + 0.8F), this.m_20189_() + (double)f2, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
               }
            }
         }

      }
   }

   private void m_30430_() {
      this.f_30364_ = false;
      this.f_30365_ = 0.0F;
      this.f_30366_ = 0.0F;
   }

   public void m_6667_(DamageSource p_30384_) {
      this.f_30363_ = false;
      this.f_30364_ = false;
      this.f_30366_ = 0.0F;
      this.f_30365_ = 0.0F;
      super.m_6667_(p_30384_);
   }

   public boolean m_30426_() {
      return this.f_30363_;
   }

   public float m_30446_(float p_30447_) {
      return Math.min(0.5F + Mth.m_14179_(p_30447_, this.f_30366_, this.f_30365_) / 2.0F * 0.5F, 1.0F);
   }

   public float m_30432_(float p_30433_, float p_30434_) {
      float f = (Mth.m_14179_(p_30433_, this.f_30366_, this.f_30365_) + p_30434_) / 1.8F;
      if (f < 0.0F) {
         f = 0.0F;
      } else if (f > 1.0F) {
         f = 1.0F;
      }

      return Mth.m_14031_(f * (float)Math.PI) * Mth.m_14031_(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
   }

   public float m_30448_(float p_30449_) {
      return Mth.m_14179_(p_30449_, this.f_30362_, this.f_30361_) * 0.15F * (float)Math.PI;
   }

   protected float m_6431_(Pose p_30409_, EntityDimensions p_30410_) {
      return p_30410_.f_20378_ * 0.8F;
   }

   public int m_8132_() {
      return this.m_21825_() ? 20 : super.m_8132_();
   }

   public boolean m_6469_(DamageSource p_30386_, float p_30387_) {
      if (this.m_6673_(p_30386_)) {
         return false;
      } else {
         Entity entity = p_30386_.m_7639_();
         this.m_21839_(false);
         if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
            p_30387_ = (p_30387_ + 1.0F) / 2.0F;
         }

         return super.m_6469_(p_30386_, p_30387_);
      }
   }

   public boolean m_7327_(Entity p_30372_) {
      boolean flag = p_30372_.m_6469_(DamageSource.m_19370_(this), (float)((int)this.m_21133_(Attributes.f_22281_)));
      if (flag) {
         this.m_19970_(this, p_30372_);
      }

      return flag;
   }

   public void m_7105_(boolean p_30443_) {
      super.m_7105_(p_30443_);
      if (p_30443_) {
         this.m_21051_(Attributes.f_22276_).m_22100_(20.0D);
         this.m_21153_(20.0F);
      } else {
         this.m_21051_(Attributes.f_22276_).m_22100_(8.0D);
      }

      this.m_21051_(Attributes.f_22281_).m_22100_(4.0D);
   }

   public InteractionResult m_6071_(Player p_30412_, InteractionHand p_30413_) {
      ItemStack itemstack = p_30412_.m_21120_(p_30413_);
      Item item = itemstack.m_41720_();
      if (this.f_19853_.f_46443_) {
         boolean flag = this.m_21830_(p_30412_) || this.m_21824_() || itemstack.m_150930_(Items.f_42500_) && !this.m_21824_() && !this.m_21660_();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else {
         if (this.m_21824_()) {
            if (this.m_6898_(itemstack) && this.m_21223_() < this.m_21233_()) {
               if (!p_30412_.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
               }

               this.m_5634_((float)item.m_41473_().m_38744_());
               this.m_146859_(GameEvent.f_157771_, this.m_146901_());
               return InteractionResult.SUCCESS;
            }

            if (!(item instanceof DyeItem)) {
               InteractionResult interactionresult = super.m_6071_(p_30412_, p_30413_);
               if ((!interactionresult.m_19077_() || this.m_6162_()) && this.m_21830_(p_30412_)) {
                  this.m_21839_(!this.m_21827_());
                  this.f_20899_ = false;
                  this.f_21344_.m_26573_();
                  this.m_6710_((LivingEntity)null);
                  return InteractionResult.SUCCESS;
               }

               return interactionresult;
            }

            DyeColor dyecolor = ((DyeItem)item).m_41089_();
            if (dyecolor != this.m_30428_()) {
               this.m_30397_(dyecolor);
               if (!p_30412_.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
               }

               return InteractionResult.SUCCESS;
            }
         } else if (itemstack.m_150930_(Items.f_42500_) && !this.m_21660_()) {
            if (!p_30412_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            if (this.f_19796_.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_30412_)) {
               this.m_21828_(p_30412_);
               this.f_21344_.m_26573_();
               this.m_6710_((LivingEntity)null);
               this.m_21839_(true);
               this.f_19853_.m_7605_(this, (byte)7);
            } else {
               this.f_19853_.m_7605_(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
         }

         return super.m_6071_(p_30412_, p_30413_);
      }
   }

   public void m_7822_(byte p_30379_) {
      if (p_30379_ == 8) {
         this.f_30364_ = true;
         this.f_30365_ = 0.0F;
         this.f_30366_ = 0.0F;
      } else if (p_30379_ == 56) {
         this.m_30430_();
      } else {
         super.m_7822_(p_30379_);
      }

   }

   public float m_30427_() {
      if (this.m_21660_()) {
         return 1.5393804F;
      } else {
         return this.m_21824_() ? (0.55F - (this.m_21233_() - this.m_21223_()) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F);
      }
   }

   public boolean m_6898_(ItemStack p_30440_) {
      Item item = p_30440_.m_41720_();
      return item.m_41472_() && item.m_41473_().m_38746_();
   }

   public int m_5792_() {
      return 8;
   }

   public int m_6784_() {
      return this.f_19804_.m_135370_(f_30360_);
   }

   public void m_7870_(int p_30404_) {
      this.f_19804_.m_135381_(f_30360_, p_30404_);
   }

   public void m_6825_() {
      this.m_7870_(f_30355_.m_142270_(this.f_19796_));
   }

   @Nullable
   public UUID m_6120_() {
      return this.f_30356_;
   }

   public void m_6925_(@Nullable UUID p_30400_) {
      this.f_30356_ = p_30400_;
   }

   public DyeColor m_30428_() {
      return DyeColor.m_41053_(this.f_19804_.m_135370_(f_30359_));
   }

   public void m_30397_(DyeColor p_30398_) {
      this.f_19804_.m_135381_(f_30359_, p_30398_.m_41060_());
   }

   public Wolf m_142606_(ServerLevel p_149088_, AgeableMob p_149089_) {
      Wolf wolf = EntityType.f_20499_.m_20615_(p_149088_);
      UUID uuid = this.m_142504_();
      if (uuid != null) {
         wolf.m_21816_(uuid);
         wolf.m_7105_(true);
      }

      return wolf;
   }

   public void m_30444_(boolean p_30445_) {
      this.f_19804_.m_135381_(f_30358_, p_30445_);
   }

   public boolean m_7848_(Animal p_30392_) {
      if (p_30392_ == this) {
         return false;
      } else if (!this.m_21824_()) {
         return false;
      } else if (!(p_30392_ instanceof Wolf)) {
         return false;
      } else {
         Wolf wolf = (Wolf)p_30392_;
         if (!wolf.m_21824_()) {
            return false;
         } else if (wolf.m_21825_()) {
            return false;
         } else {
            return this.m_27593_() && wolf.m_27593_();
         }
      }
   }

   public boolean m_30429_() {
      return this.f_19804_.m_135370_(f_30358_);
   }

   public boolean m_7757_(LivingEntity p_30389_, LivingEntity p_30390_) {
      if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
         if (p_30389_ instanceof Wolf) {
            Wolf wolf = (Wolf)p_30389_;
            return !wolf.m_21824_() || wolf.m_142480_() != p_30390_;
         } else if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).m_7099_((Player)p_30389_)) {
            return false;
         } else if (p_30389_ instanceof AbstractHorse && ((AbstractHorse)p_30389_).m_30614_()) {
            return false;
         } else {
            return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal)p_30389_).m_21824_();
         }
      } else {
         return false;
      }
   }

   public boolean m_6573_(Player p_30396_) {
      return !this.m_21660_() && super.m_6573_(p_30396_);
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.6F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   class WolfAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Wolf f_30451_;

      public WolfAvoidEntityGoal(Wolf p_30454_, Class<T> p_30455_, float p_30456_, double p_30457_, double p_30458_) {
         super(p_30454_, p_30455_, p_30456_, p_30457_, p_30458_);
         this.f_30451_ = p_30454_;
      }

      public boolean m_8036_() {
         if (super.m_8036_() && this.f_25016_ instanceof Llama) {
            return !this.f_30451_.m_21824_() && this.m_30460_((Llama)this.f_25016_);
         } else {
            return false;
         }
      }

      private boolean m_30460_(Llama p_30461_) {
         return p_30461_.m_30823_() >= Wolf.this.f_19796_.nextInt(5);
      }

      public void m_8056_() {
         Wolf.this.m_6710_((LivingEntity)null);
         super.m_8056_();
      }

      public void m_8037_() {
         Wolf.this.m_6710_((LivingEntity)null);
         super.m_8037_();
      }
   }
}
