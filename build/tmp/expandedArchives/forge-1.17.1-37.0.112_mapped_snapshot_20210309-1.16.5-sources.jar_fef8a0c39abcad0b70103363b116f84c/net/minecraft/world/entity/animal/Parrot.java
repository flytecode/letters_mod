package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Parrot extends ShoulderRidingEntity implements FlyingAnimal {
   private static final EntityDataAccessor<Integer> f_29354_ = SynchedEntityData.m_135353_(Parrot.class, EntityDataSerializers.f_135028_);
   private static final Predicate<Mob> f_29355_ = new Predicate<Mob>() {
      public boolean test(@Nullable Mob p_29453_) {
         return p_29453_ != null && Parrot.f_29358_.containsKey(p_29453_.m_6095_());
      }
   };
   private static final Item f_29356_ = Items.f_42572_;
   private static final Set<Item> f_29357_ = Sets.newHashSet(Items.f_42404_, Items.f_42578_, Items.f_42577_, Items.f_42733_);
   private static final int f_148986_ = 5;
   static final Map<EntityType<?>, SoundEvent> f_29358_ = Util.m_137469_(Maps.newHashMap(), (p_29398_) -> {
      p_29398_.put(EntityType.f_20551_, SoundEvents.f_12246_);
      p_29398_.put(EntityType.f_20554_, SoundEvents.f_12268_);
      p_29398_.put(EntityType.f_20558_, SoundEvents.f_12247_);
      p_29398_.put(EntityType.f_20562_, SoundEvents.f_12248_);
      p_29398_.put(EntityType.f_20563_, SoundEvents.f_12249_);
      p_29398_.put(EntityType.f_20565_, SoundEvents.f_12250_);
      p_29398_.put(EntityType.f_20567_, SoundEvents.f_12251_);
      p_29398_.put(EntityType.f_20568_, SoundEvents.f_12252_);
      p_29398_.put(EntityType.f_20453_, SoundEvents.f_12253_);
      p_29398_.put(EntityType.f_20455_, SoundEvents.f_12254_);
      p_29398_.put(EntityType.f_20456_, SoundEvents.f_12255_);
      p_29398_.put(EntityType.f_20458_, SoundEvents.f_12256_);
      p_29398_.put(EntityType.f_20459_, SoundEvents.f_12257_);
      p_29398_.put(EntityType.f_20468_, SoundEvents.f_12258_);
      p_29398_.put(EntityType.f_20509_, SoundEvents.f_12259_);
      p_29398_.put(EntityType.f_20511_, SoundEvents.f_12260_);
      p_29398_.put(EntityType.f_20512_, SoundEvents.f_12261_);
      p_29398_.put(EntityType.f_20513_, SoundEvents.f_12262_);
      p_29398_.put(EntityType.f_20518_, SoundEvents.f_12263_);
      p_29398_.put(EntityType.f_20521_, SoundEvents.f_12264_);
      p_29398_.put(EntityType.f_20523_, SoundEvents.f_12265_);
      p_29398_.put(EntityType.f_20524_, SoundEvents.f_12266_);
      p_29398_.put(EntityType.f_20526_, SoundEvents.f_12267_);
      p_29398_.put(EntityType.f_20479_, SoundEvents.f_12268_);
      p_29398_.put(EntityType.f_20481_, SoundEvents.f_12269_);
      p_29398_.put(EntityType.f_20491_, SoundEvents.f_12270_);
      p_29398_.put(EntityType.f_20493_, SoundEvents.f_12271_);
      p_29398_.put(EntityType.f_20495_, SoundEvents.f_12220_);
      p_29398_.put(EntityType.f_20496_, SoundEvents.f_12221_);
      p_29398_.put(EntityType.f_20497_, SoundEvents.f_12222_);
      p_29398_.put(EntityType.f_20500_, SoundEvents.f_12223_);
      p_29398_.put(EntityType.f_20501_, SoundEvents.f_12224_);
      p_29398_.put(EntityType.f_20530_, SoundEvents.f_12225_);
   });
   public float f_29350_;
   public float f_29351_;
   public float f_29352_;
   public float f_29353_;
   private float f_29359_ = 1.0F;
   private float f_148987_ = 1.0F;
   private boolean f_29348_;
   private BlockPos f_29349_;

   public Parrot(EntityType<? extends Parrot> p_29362_, Level p_29363_) {
      super(p_29362_, p_29363_);
      this.f_21342_ = new FlyingMoveControl(this, 10, false);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, -1.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -1.0F);
      this.m_21441_(BlockPathTypes.COCOA, -1.0F);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_29389_, DifficultyInstance p_29390_, MobSpawnType p_29391_, @Nullable SpawnGroupData p_29392_, @Nullable CompoundTag p_29393_) {
      this.m_29448_(this.f_19796_.nextInt(5));
      if (p_29392_ == null) {
         p_29392_ = new AgeableMob.AgeableMobGroupData(false);
      }

      return super.m_6518_(p_29389_, p_29390_, p_29391_, p_29392_, p_29393_);
   }

   public boolean m_6162_() {
      return false;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new PanicGoal(this, 1.25D));
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
      this.f_21345_.m_25352_(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new LandOnOwnersShoulderGoal(this));
      this.f_21345_.m_25352_(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
   }

   public static AttributeSupplier.Builder m_29438_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 6.0D).m_22268_(Attributes.f_22280_, (double)0.4F).m_22268_(Attributes.f_22279_, (double)0.2F);
   }

   protected PathNavigation m_6037_(Level p_29417_) {
      FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
      flyingpathnavigation.m_26440_(false);
      flyingpathnavigation.m_7008_(true);
      flyingpathnavigation.m_26443_(true);
      return flyingpathnavigation;
   }

   protected float m_6431_(Pose p_29411_, EntityDimensions p_29412_) {
      return p_29412_.f_20378_ * 0.6F;
   }

   public void m_8107_() {
      if (this.f_29349_ == null || !this.f_29349_.m_123306_(this.m_20182_(), 3.46D) || !this.f_19853_.m_8055_(this.f_29349_).m_60713_(Blocks.f_50131_)) {
         this.f_29348_ = false;
         this.f_29349_ = null;
      }

      if (this.f_19853_.f_46441_.nextInt(400) == 0) {
         m_29382_(this.f_19853_, this);
      }

      super.m_8107_();
      this.m_29442_();
   }

   public void m_6818_(BlockPos p_29395_, boolean p_29396_) {
      this.f_29349_ = p_29395_;
      this.f_29348_ = p_29396_;
   }

   public boolean m_29439_() {
      return this.f_29348_;
   }

   private void m_29442_() {
      this.f_29353_ = this.f_29350_;
      this.f_29352_ = this.f_29351_;
      this.f_29351_ = (float)((double)this.f_29351_ + (double)(!this.f_19861_ && !this.m_20159_() ? 4 : -1) * 0.3D);
      this.f_29351_ = Mth.m_14036_(this.f_29351_, 0.0F, 1.0F);
      if (!this.f_19861_ && this.f_29359_ < 1.0F) {
         this.f_29359_ = 1.0F;
      }

      this.f_29359_ = (float)((double)this.f_29359_ * 0.9D);
      Vec3 vec3 = this.m_20184_();
      if (!this.f_19861_ && vec3.f_82480_ < 0.0D) {
         this.m_20256_(vec3.m_82542_(1.0D, 0.6D, 1.0D));
      }

      this.f_29350_ += this.f_29359_ * 2.0F;
   }

   public static boolean m_29382_(Level p_29383_, Entity p_29384_) {
      if (p_29384_.m_6084_() && !p_29384_.m_20067_() && p_29383_.f_46441_.nextInt(2) == 0) {
         List<Mob> list = p_29383_.m_6443_(Mob.class, p_29384_.m_142469_().m_82400_(20.0D), f_29355_);
         if (!list.isEmpty()) {
            Mob mob = list.get(p_29383_.f_46441_.nextInt(list.size()));
            if (!mob.m_20067_()) {
               SoundEvent soundevent = m_29408_(mob.m_6095_());
               p_29383_.m_6263_((Player)null, p_29384_.m_20185_(), p_29384_.m_20186_(), p_29384_.m_20189_(), soundevent, p_29384_.m_5720_(), 0.7F, m_29399_(p_29383_.f_46441_));
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public InteractionResult m_6071_(Player p_29414_, InteractionHand p_29415_) {
      ItemStack itemstack = p_29414_.m_21120_(p_29415_);
      if (!this.m_21824_() && f_29357_.contains(itemstack.m_41720_())) {
         if (!p_29414_.m_150110_().f_35937_) {
            itemstack.m_41774_(1);
         }

         if (!this.m_20067_()) {
            this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12190_, this.m_5720_(), 1.0F, 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F);
         }

         if (!this.f_19853_.f_46443_) {
            if (this.f_19796_.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_29414_)) {
               this.m_21828_(p_29414_);
               this.f_19853_.m_7605_(this, (byte)7);
            } else {
               this.f_19853_.m_7605_(this, (byte)6);
            }
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (itemstack.m_150930_(f_29356_)) {
         if (!p_29414_.m_150110_().f_35937_) {
            itemstack.m_41774_(1);
         }

         this.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 900));
         if (p_29414_.m_7500_() || !this.m_20147_()) {
            this.m_6469_(DamageSource.m_19344_(p_29414_), Float.MAX_VALUE);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (!this.m_142592_() && this.m_21824_() && this.m_21830_(p_29414_)) {
         if (!this.f_19853_.f_46443_) {
            this.m_21839_(!this.m_21827_());
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return super.m_6071_(p_29414_, p_29415_);
      }
   }

   public boolean m_6898_(ItemStack p_29446_) {
      return false;
   }

   public static boolean m_29423_(EntityType<Parrot> p_29424_, LevelAccessor p_29425_, MobSpawnType p_29426_, BlockPos p_29427_, Random p_29428_) {
      BlockState blockstate = p_29425_.m_8055_(p_29427_.m_7495_());
      return (blockstate.m_60620_(BlockTags.f_13035_) || blockstate.m_60713_(Blocks.f_50440_) || blockstate.m_60620_(BlockTags.f_13106_) || blockstate.m_60713_(Blocks.f_50016_)) && p_29425_.m_45524_(p_29427_, 0) > 8;
   }

   public boolean m_142535_(float p_148989_, float p_148990_, DamageSource p_148991_) {
      return false;
   }

   protected void m_7840_(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
   }

   public boolean m_7848_(Animal p_29381_) {
      return false;
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_148993_, AgeableMob p_148994_) {
      return null;
   }

   public boolean m_7327_(Entity p_29365_) {
      return p_29365_.m_6469_(DamageSource.m_19370_(this), 3.0F);
   }

   @Nullable
   public SoundEvent m_7515_() {
      return m_29385_(this.f_19853_, this.f_19853_.f_46441_);
   }

   public static SoundEvent m_29385_(Level p_29386_, Random p_29387_) {
      if (p_29386_.m_46791_() != Difficulty.PEACEFUL && p_29387_.nextInt(1000) == 0) {
         List<EntityType<?>> list = Lists.newArrayList(f_29358_.keySet());
         return m_29408_(list.get(p_29387_.nextInt(list.size())));
      } else {
         return SoundEvents.f_12188_;
      }
   }

   private static SoundEvent m_29408_(EntityType<?> p_29409_) {
      return f_29358_.getOrDefault(p_29409_, SoundEvents.f_12188_);
   }

   protected SoundEvent m_7975_(DamageSource p_29437_) {
      return SoundEvents.f_12192_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12189_;
   }

   protected void m_7355_(BlockPos p_29419_, BlockState p_29420_) {
      this.m_5496_(SoundEvents.f_12226_, 0.15F, 1.0F);
   }

   protected boolean m_142039_() {
      return this.f_146794_ > this.f_148987_;
   }

   protected void m_142043_() {
      this.m_5496_(SoundEvents.f_12191_, 0.15F, 1.0F);
      this.f_148987_ = this.f_146794_ + this.f_29351_ / 2.0F;
   }

   public float m_6100_() {
      return m_29399_(this.f_19796_);
   }

   public static float m_29399_(Random p_29400_) {
      return (p_29400_.nextFloat() - p_29400_.nextFloat()) * 0.2F + 1.0F;
   }

   public SoundSource m_5720_() {
      return SoundSource.NEUTRAL;
   }

   public boolean m_6094_() {
      return true;
   }

   protected void m_7324_(Entity p_29367_) {
      if (!(p_29367_ instanceof Player)) {
         super.m_7324_(p_29367_);
      }
   }

   public boolean m_6469_(DamageSource p_29378_, float p_29379_) {
      if (this.m_6673_(p_29378_)) {
         return false;
      } else {
         this.m_21839_(false);
         return super.m_6469_(p_29378_, p_29379_);
      }
   }

   public int m_29440_() {
      return Mth.m_14045_(this.f_19804_.m_135370_(f_29354_), 0, 4);
   }

   public void m_29448_(int p_29449_) {
      this.f_19804_.m_135381_(f_29354_, p_29449_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29354_, 0);
   }

   public void m_7380_(CompoundTag p_29422_) {
      super.m_7380_(p_29422_);
      p_29422_.m_128405_("Variant", this.m_29440_());
   }

   public void m_7378_(CompoundTag p_29402_) {
      super.m_7378_(p_29402_);
      this.m_29448_(p_29402_.m_128451_("Variant"));
   }

   public boolean m_142592_() {
      return !this.f_19861_;
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.5F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }
}
