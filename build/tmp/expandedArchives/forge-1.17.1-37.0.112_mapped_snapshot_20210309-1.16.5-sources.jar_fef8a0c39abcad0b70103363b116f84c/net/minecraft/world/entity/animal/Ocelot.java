package net.minecraft.world.entity.animal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Ocelot extends Animal {
   public static final double f_148945_ = 0.6D;
   public static final double f_148946_ = 0.8D;
   public static final double f_148947_ = 1.33D;
   private static final Ingredient f_28981_ = Ingredient.m_43929_(Items.f_42526_, Items.f_42527_);
   private static final EntityDataAccessor<Boolean> f_28982_ = SynchedEntityData.m_135353_(Ocelot.class, EntityDataSerializers.f_135035_);
   private Ocelot.OcelotAvoidEntityGoal<Player> f_28983_;
   private Ocelot.OcelotTemptGoal f_28984_;

   public Ocelot(EntityType<? extends Ocelot> p_28987_, Level p_28988_) {
      super(p_28987_, p_28988_);
      this.m_29037_();
   }

   boolean m_29038_() {
      return this.f_19804_.m_135370_(f_28982_);
   }

   private void m_29045_(boolean p_29046_) {
      this.f_19804_.m_135381_(f_28982_, p_29046_);
      this.m_29037_();
   }

   public void m_7380_(CompoundTag p_29024_) {
      super.m_7380_(p_29024_);
      p_29024_.m_128379_("Trusting", this.m_29038_());
   }

   public void m_7378_(CompoundTag p_29013_) {
      super.m_7378_(p_29013_);
      this.m_29045_(p_29013_.m_128471_("Trusting"));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28982_, false);
   }

   protected void m_8099_() {
      this.f_28984_ = new Ocelot.OcelotTemptGoal(this, 0.6D, f_28981_, true);
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, this.f_28984_);
      this.f_21345_.m_25352_(7, new LeapAtTargetGoal(this, 0.3F));
      this.f_21345_.m_25352_(8, new OcelotAttackGoal(this));
      this.f_21345_.m_25352_(9, new BreedGoal(this, 0.8D));
      this.f_21345_.m_25352_(10, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1.0000001E-5F));
      this.f_21345_.m_25352_(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, Chicken.class, false));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, false, false, Turtle.f_30122_));
   }

   public void m_8024_() {
      if (this.m_21566_().m_24995_()) {
         double d0 = this.m_21566_().m_24999_();
         if (d0 == 0.6D) {
            this.m_20124_(Pose.CROUCHING);
            this.m_6858_(false);
         } else if (d0 == 1.33D) {
            this.m_20124_(Pose.STANDING);
            this.m_6858_(true);
         } else {
            this.m_20124_(Pose.STANDING);
            this.m_6858_(false);
         }
      } else {
         this.m_20124_(Pose.STANDING);
         this.m_6858_(false);
      }

   }

   public boolean m_6785_(double p_29041_) {
      return !this.m_29038_() && this.f_19797_ > 2400;
   }

   public static AttributeSupplier.Builder m_29036_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, 3.0D);
   }

   public boolean m_142535_(float p_148949_, float p_148950_, DamageSource p_148951_) {
      return false;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return SoundEvents.f_12173_;
   }

   public int m_8100_() {
      return 900;
   }

   protected SoundEvent m_7975_(DamageSource p_29035_) {
      return SoundEvents.f_12172_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12174_;
   }

   private float m_29039_() {
      return (float)this.m_21133_(Attributes.f_22281_);
   }

   public boolean m_7327_(Entity p_28990_) {
      return p_28990_.m_6469_(DamageSource.m_19370_(this), this.m_29039_());
   }

   public InteractionResult m_6071_(Player p_29021_, InteractionHand p_29022_) {
      ItemStack itemstack = p_29021_.m_21120_(p_29022_);
      if ((this.f_28984_ == null || this.f_28984_.m_25955_()) && !this.m_29038_() && this.m_6898_(itemstack) && p_29021_.m_20280_(this) < 9.0D) {
         this.m_142075_(p_29021_, p_29022_, itemstack);
         if (!this.f_19853_.f_46443_) {
            if (this.f_19796_.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_29021_)) {
               this.m_29045_(true);
               this.m_29047_(true);
               this.f_19853_.m_7605_(this, (byte)41);
            } else {
               this.m_29047_(false);
               this.f_19853_.m_7605_(this, (byte)40);
            }
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return super.m_6071_(p_29021_, p_29022_);
      }
   }

   public void m_7822_(byte p_28995_) {
      if (p_28995_ == 41) {
         this.m_29047_(true);
      } else if (p_28995_ == 40) {
         this.m_29047_(false);
      } else {
         super.m_7822_(p_28995_);
      }

   }

   private void m_29047_(boolean p_29048_) {
      ParticleOptions particleoptions = ParticleTypes.f_123750_;
      if (!p_29048_) {
         particleoptions = ParticleTypes.f_123762_;
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.02D;
         double d1 = this.f_19796_.nextGaussian() * 0.02D;
         double d2 = this.f_19796_.nextGaussian() * 0.02D;
         this.f_19853_.m_7106_(particleoptions, this.m_20208_(1.0D), this.m_20187_() + 0.5D, this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   protected void m_29037_() {
      if (this.f_28983_ == null) {
         this.f_28983_ = new Ocelot.OcelotAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8D, 1.33D);
      }

      this.f_21345_.m_25363_(this.f_28983_);
      if (!this.m_29038_()) {
         this.f_21345_.m_25352_(4, this.f_28983_);
      }

   }

   public Ocelot m_142606_(ServerLevel p_148956_, AgeableMob p_148957_) {
      return EntityType.f_20505_.m_20615_(p_148956_);
   }

   public boolean m_6898_(ItemStack p_29043_) {
      return f_28981_.test(p_29043_);
   }

   public static boolean m_29025_(EntityType<Ocelot> p_29026_, LevelAccessor p_29027_, MobSpawnType p_29028_, BlockPos p_29029_, Random p_29030_) {
      return p_29030_.nextInt(3) != 0;
   }

   public boolean m_6914_(LevelReader p_29005_) {
      if (p_29005_.m_45784_(this) && !p_29005_.m_46855_(this.m_142469_())) {
         BlockPos blockpos = this.m_142538_();
         if (blockpos.m_123342_() < p_29005_.m_5736_()) {
            return false;
         }

         BlockState blockstate = p_29005_.m_8055_(blockpos.m_7495_());
         if (blockstate.m_60713_(Blocks.f_50440_) || blockstate.m_60620_(BlockTags.f_13035_)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_29007_, DifficultyInstance p_29008_, MobSpawnType p_29009_, @Nullable SpawnGroupData p_29010_, @Nullable CompoundTag p_29011_) {
      if (p_29010_ == null) {
         p_29010_ = new AgeableMob.AgeableMobGroupData(1.0F);
      }

      return super.m_6518_(p_29007_, p_29008_, p_29009_, p_29010_, p_29011_);
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.5F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   public boolean m_20161_() {
      return this.m_20089_() == Pose.CROUCHING || super.m_20161_();
   }

   static class OcelotAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Ocelot f_29049_;

      public OcelotAvoidEntityGoal(Ocelot p_29051_, Class<T> p_29052_, float p_29053_, double p_29054_, double p_29055_) {
         super(p_29051_, p_29052_, p_29053_, p_29054_, p_29055_, EntitySelector.f_20406_::test);
         this.f_29049_ = p_29051_;
      }

      public boolean m_8036_() {
         return !this.f_29049_.m_29038_() && super.m_8036_();
      }

      public boolean m_8045_() {
         return !this.f_29049_.m_29038_() && super.m_8045_();
      }
   }

   static class OcelotTemptGoal extends TemptGoal {
      private final Ocelot f_29058_;

      public OcelotTemptGoal(Ocelot p_29060_, double p_29061_, Ingredient p_29062_, boolean p_29063_) {
         super(p_29060_, p_29061_, p_29062_, p_29063_);
         this.f_29058_ = p_29060_;
      }

      protected boolean m_7497_() {
         return super.m_7497_() && !this.f_29058_.m_29038_();
      }
   }
}
