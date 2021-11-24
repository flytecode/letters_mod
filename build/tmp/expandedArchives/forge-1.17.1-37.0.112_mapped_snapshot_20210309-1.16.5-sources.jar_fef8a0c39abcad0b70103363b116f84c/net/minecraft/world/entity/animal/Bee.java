package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Bee extends Animal implements NeutralMob, FlyingAnimal {
   public static final float f_148718_ = 120.32113F;
   public static final int f_148719_ = Mth.m_14167_(1.4959966F);
   private static final EntityDataAccessor<Byte> f_27703_ = SynchedEntityData.m_135353_(Bee.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Integer> f_27704_ = SynchedEntityData.m_135353_(Bee.class, EntityDataSerializers.f_135028_);
   private static final int f_148729_ = 2;
   private static final int f_148730_ = 4;
   private static final int f_148731_ = 8;
   private static final int f_148732_ = 1200;
   private static final int f_148733_ = 2400;
   private static final int f_148734_ = 3600;
   private static final int f_148735_ = 4;
   private static final int f_148736_ = 10;
   private static final int f_148737_ = 10;
   private static final int f_148738_ = 18;
   private static final int f_148739_ = 32;
   private static final int f_148740_ = 2;
   private static final int f_148741_ = 16;
   private static final int f_148742_ = 20;
   public static final String f_148720_ = "CropsGrownSincePollination";
   public static final String f_148721_ = "CannotEnterHiveTicks";
   public static final String f_148722_ = "TicksSincePollination";
   public static final String f_148723_ = "HasStung";
   public static final String f_148724_ = "HasNectar";
   public static final String f_148727_ = "FlowerPos";
   public static final String f_148728_ = "HivePos";
   private static final UniformInt f_27705_ = TimeUtil.m_145020_(20, 39);
   private UUID f_27706_;
   private float f_27707_;
   private float f_27708_;
   private int f_27709_;
   int f_27710_;
   private int f_27711_;
   private int f_27712_;
   private static final int f_148725_ = 200;
   int f_27713_;
   private static final int f_148726_ = 200;
   int f_27714_ = Mth.m_14072_(this.f_19796_, 20, 60);
   @Nullable
   BlockPos f_27697_;
   @Nullable
   BlockPos f_27698_;
   Bee.BeePollinateGoal f_27699_;
   Bee.BeeGoToHiveGoal f_27700_;
   private Bee.BeeGoToKnownFlowerGoal f_27701_;
   private int f_27702_;

   public Bee(EntityType<? extends Bee> p_27717_, Level p_27718_) {
      super(p_27717_, p_27718_);
      this.f_21342_ = new FlyingMoveControl(this, 20, true);
      this.f_21365_ = new Bee.BeeLookControl(this);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, -1.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 16.0F);
      this.m_21441_(BlockPathTypes.COCOA, -1.0F);
      this.m_21441_(BlockPathTypes.FENCE, -1.0F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_27703_, (byte)0);
      this.f_19804_.m_135372_(f_27704_, 0);
   }

   public float m_5610_(BlockPos p_27788_, LevelReader p_27789_) {
      return p_27789_.m_8055_(p_27788_).m_60795_() ? 10.0F : 0.0F;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new Bee.BeeAttackGoal(this, (double)1.4F, true));
      this.f_21345_.m_25352_(1, new Bee.BeeEnterHiveGoal());
      this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 1.25D, Ingredient.m_43911_(ItemTags.f_13149_), false));
      this.f_27699_ = new Bee.BeePollinateGoal();
      this.f_21345_.m_25352_(4, this.f_27699_);
      this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.25D));
      this.f_21345_.m_25352_(5, new Bee.BeeLocateHiveGoal());
      this.f_27700_ = new Bee.BeeGoToHiveGoal();
      this.f_21345_.m_25352_(5, this.f_27700_);
      this.f_27701_ = new Bee.BeeGoToKnownFlowerGoal();
      this.f_21345_.m_25352_(6, this.f_27701_);
      this.f_21345_.m_25352_(7, new Bee.BeeGrowCropGoal());
      this.f_21345_.m_25352_(8, new Bee.BeeWanderGoal());
      this.f_21345_.m_25352_(9, new FloatGoal(this));
      this.f_21346_.m_25352_(1, (new Bee.BeeHurtByOtherGoal(this)).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new Bee.BeeBecomeAngryTargetGoal(this));
      this.f_21346_.m_25352_(3, new ResetUniversalAngerTargetGoal<>(this, true));
   }

   public void m_7380_(CompoundTag p_27823_) {
      super.m_7380_(p_27823_);
      if (this.m_27854_()) {
         p_27823_.m_128365_("HivePos", NbtUtils.m_129224_(this.m_27855_()));
      }

      if (this.m_27852_()) {
         p_27823_.m_128365_("FlowerPos", NbtUtils.m_129224_(this.m_27851_()));
      }

      p_27823_.m_128379_("HasNectar", this.m_27856_());
      p_27823_.m_128379_("HasStung", this.m_27857_());
      p_27823_.m_128405_("TicksSincePollination", this.f_27710_);
      p_27823_.m_128405_("CannotEnterHiveTicks", this.f_27711_);
      p_27823_.m_128405_("CropsGrownSincePollination", this.f_27712_);
      this.m_21678_(p_27823_);
   }

   public void m_7378_(CompoundTag p_27793_) {
      this.f_27698_ = null;
      if (p_27793_.m_128441_("HivePos")) {
         this.f_27698_ = NbtUtils.m_129239_(p_27793_.m_128469_("HivePos"));
      }

      this.f_27697_ = null;
      if (p_27793_.m_128441_("FlowerPos")) {
         this.f_27697_ = NbtUtils.m_129239_(p_27793_.m_128469_("FlowerPos"));
      }

      super.m_7378_(p_27793_);
      this.m_27919_(p_27793_.m_128471_("HasNectar"));
      this.m_27925_(p_27793_.m_128471_("HasStung"));
      this.f_27710_ = p_27793_.m_128451_("TicksSincePollination");
      this.f_27711_ = p_27793_.m_128451_("CannotEnterHiveTicks");
      this.f_27712_ = p_27793_.m_128451_("CropsGrownSincePollination");
      this.m_147285_(this.f_19853_, p_27793_);
   }

   public boolean m_7327_(Entity p_27722_) {
      boolean flag = p_27722_.m_6469_(DamageSource.m_19364_(this), (float)((int)this.m_21133_(Attributes.f_22281_)));
      if (flag) {
         this.m_19970_(this, p_27722_);
         if (p_27722_ instanceof LivingEntity) {
            ((LivingEntity)p_27722_).m_21321_(((LivingEntity)p_27722_).m_21235_() + 1);
            int i = 0;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
               i = 10;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
               i = 18;
            }

            if (i > 0) {
               ((LivingEntity)p_27722_).m_147207_(new MobEffectInstance(MobEffects.f_19614_, i * 20, 0), this);
            }
         }

         this.m_27925_(true);
         this.m_21662_();
         this.m_5496_(SoundEvents.f_11692_, 1.0F, 1.0F);
      }

      return flag;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_27856_() && this.m_27869_() < 10 && this.f_19796_.nextFloat() < 0.05F) {
         for(int i = 0; i < this.f_19796_.nextInt(2) + 1; ++i) {
            this.m_27779_(this.f_19853_, this.m_20185_() - (double)0.3F, this.m_20185_() + (double)0.3F, this.m_20189_() - (double)0.3F, this.m_20189_() + (double)0.3F, this.m_20227_(0.5D), ParticleTypes.f_123782_);
         }
      }

      this.m_27867_();
   }

   private void m_27779_(Level p_27780_, double p_27781_, double p_27782_, double p_27783_, double p_27784_, double p_27785_, ParticleOptions p_27786_) {
      p_27780_.m_7106_(p_27786_, Mth.m_14139_(p_27780_.f_46441_.nextDouble(), p_27781_, p_27782_), p_27785_, Mth.m_14139_(p_27780_.f_46441_.nextDouble(), p_27783_, p_27784_), 0.0D, 0.0D, 0.0D);
   }

   void m_27880_(BlockPos p_27881_) {
      Vec3 vec3 = Vec3.m_82539_(p_27881_);
      int i = 0;
      BlockPos blockpos = this.m_142538_();
      int j = (int)vec3.f_82480_ - blockpos.m_123342_();
      if (j > 2) {
         i = 4;
      } else if (j < -2) {
         i = -4;
      }

      int k = 6;
      int l = 8;
      int i1 = blockpos.m_123333_(p_27881_);
      if (i1 < 15) {
         k = i1 / 2;
         l = i1 / 2;
      }

      Vec3 vec31 = AirRandomPos.m_148387_(this, k, l, i, vec3, (double)((float)Math.PI / 10F));
      if (vec31 != null) {
         this.f_21344_.m_26529_(0.5F);
         this.f_21344_.m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, 1.0D);
      }
   }

   @Nullable
   public BlockPos m_27851_() {
      return this.f_27697_;
   }

   public boolean m_27852_() {
      return this.f_27697_ != null;
   }

   public void m_27876_(BlockPos p_27877_) {
      this.f_27697_ = p_27877_;
   }

   @VisibleForDebug
   public int m_148774_() {
      return Math.max(this.f_27700_.f_27980_, this.f_27701_.f_28010_);
   }

   @VisibleForDebug
   public List<BlockPos> m_148775_() {
      return this.f_27700_.f_27981_;
   }

   private boolean m_27865_() {
      return this.f_27710_ > 3600;
   }

   boolean m_27866_() {
      if (this.f_27711_ <= 0 && !this.f_27699_.m_28086_() && !this.m_27857_() && this.m_5448_() == null) {
         boolean flag = this.m_27865_() || this.f_19853_.m_46471_() || this.f_19853_.m_46462_() || this.m_27856_();
         return flag && !this.m_27868_();
      } else {
         return false;
      }
   }

   public void m_27915_(int p_27916_) {
      this.f_27711_ = p_27916_;
   }

   public float m_27935_(float p_27936_) {
      return Mth.m_14179_(p_27936_, this.f_27708_, this.f_27707_);
   }

   private void m_27867_() {
      this.f_27708_ = this.f_27707_;
      if (this.m_27873_()) {
         this.f_27707_ = Math.min(1.0F, this.f_27707_ + 0.2F);
      } else {
         this.f_27707_ = Math.max(0.0F, this.f_27707_ - 0.24F);
      }

   }

   protected void m_8024_() {
      boolean flag = this.m_27857_();
      if (this.m_20072_()) {
         ++this.f_27702_;
      } else {
         this.f_27702_ = 0;
      }

      if (this.f_27702_ > 20) {
         this.m_6469_(DamageSource.f_19312_, 1.0F);
      }

      if (flag) {
         ++this.f_27709_;
         if (this.f_27709_ % 5 == 0 && this.f_19796_.nextInt(Mth.m_14045_(1200 - this.f_27709_, 1, 1200)) == 0) {
            this.m_6469_(DamageSource.f_19318_, this.m_21223_());
         }
      }

      if (!this.m_27856_()) {
         ++this.f_27710_;
      }

      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, false);
      }

   }

   public void m_27853_() {
      this.f_27710_ = 0;
   }

   private boolean m_27868_() {
      if (this.f_27698_ == null) {
         return false;
      } else {
         BlockEntity blockentity = this.f_19853_.m_7702_(this.f_27698_);
         return blockentity instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)blockentity).m_58773_();
      }
   }

   public int m_6784_() {
      return this.f_19804_.m_135370_(f_27704_);
   }

   public void m_7870_(int p_27795_) {
      this.f_19804_.m_135381_(f_27704_, p_27795_);
   }

   public UUID m_6120_() {
      return this.f_27706_;
   }

   public void m_6925_(@Nullable UUID p_27791_) {
      this.f_27706_ = p_27791_;
   }

   public void m_6825_() {
      this.m_7870_(f_27705_.m_142270_(this.f_19796_));
   }

   private boolean m_27884_(BlockPos p_27885_) {
      BlockEntity blockentity = this.f_19853_.m_7702_(p_27885_);
      if (blockentity instanceof BeehiveBlockEntity) {
         return !((BeehiveBlockEntity)blockentity).m_58775_();
      } else {
         return false;
      }
   }

   @VisibleForDebug
   public boolean m_27854_() {
      return this.f_27698_ != null;
   }

   @Nullable
   @VisibleForDebug
   public BlockPos m_27855_() {
      return this.f_27698_;
   }

   @VisibleForDebug
   public GoalSelector m_148772_() {
      return this.f_21345_;
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133697_(this);
   }

   int m_27869_() {
      return this.f_27712_;
   }

   private void m_27870_() {
      this.f_27712_ = 0;
   }

   void m_27871_() {
      ++this.f_27712_;
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.f_19853_.f_46443_) {
         if (this.f_27711_ > 0) {
            --this.f_27711_;
         }

         if (this.f_27713_ > 0) {
            --this.f_27713_;
         }

         if (this.f_27714_ > 0) {
            --this.f_27714_;
         }

         boolean flag = this.m_21660_() && !this.m_27857_() && this.m_5448_() != null && this.m_5448_().m_20280_(this) < 4.0D;
         this.m_27929_(flag);
         if (this.f_19797_ % 20 == 0 && !this.m_27872_()) {
            this.f_27698_ = null;
         }
      }

   }

   boolean m_27872_() {
      if (!this.m_27854_()) {
         return false;
      } else {
         BlockEntity blockentity = this.f_19853_.m_7702_(this.f_27698_);
         return blockentity instanceof BeehiveBlockEntity;
      }
   }

   public boolean m_27856_() {
      return this.m_27921_(8);
   }

   void m_27919_(boolean p_27920_) {
      if (p_27920_) {
         this.m_27853_();
      }

      this.m_27832_(8, p_27920_);
   }

   public boolean m_27857_() {
      return this.m_27921_(4);
   }

   private void m_27925_(boolean p_27926_) {
      this.m_27832_(4, p_27926_);
   }

   private boolean m_27873_() {
      return this.m_27921_(2);
   }

   private void m_27929_(boolean p_27930_) {
      this.m_27832_(2, p_27930_);
   }

   boolean m_27889_(BlockPos p_27890_) {
      return !this.m_27816_(p_27890_, 32);
   }

   private void m_27832_(int p_27833_, boolean p_27834_) {
      if (p_27834_) {
         this.f_19804_.m_135381_(f_27703_, (byte)(this.f_19804_.m_135370_(f_27703_) | p_27833_));
      } else {
         this.f_19804_.m_135381_(f_27703_, (byte)(this.f_19804_.m_135370_(f_27703_) & ~p_27833_));
      }

   }

   private boolean m_27921_(int p_27922_) {
      return (this.f_19804_.m_135370_(f_27703_) & p_27922_) != 0;
   }

   public static AttributeSupplier.Builder m_27858_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22280_, (double)0.6F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, 2.0D).m_22268_(Attributes.f_22277_, 48.0D);
   }

   protected PathNavigation m_6037_(Level p_27815_) {
      FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_27815_) {
         public boolean m_6342_(BlockPos p_27947_) {
            return !this.f_26495_.m_8055_(p_27947_.m_7495_()).m_60795_();
         }

         public void m_7638_() {
            if (!Bee.this.f_27699_.m_28086_()) {
               super.m_7638_();
            }
         }
      };
      flyingpathnavigation.m_26440_(false);
      flyingpathnavigation.m_7008_(false);
      flyingpathnavigation.m_26443_(true);
      return flyingpathnavigation;
   }

   public boolean m_6898_(ItemStack p_27895_) {
      return p_27895_.m_150922_(ItemTags.f_13149_);
   }

   boolean m_27896_(BlockPos p_27897_) {
      return this.f_19853_.m_46749_(p_27897_) && this.f_19853_.m_8055_(p_27897_).m_60620_(BlockTags.f_13041_);
   }

   protected void m_7355_(BlockPos p_27820_, BlockState p_27821_) {
   }

   protected SoundEvent m_7515_() {
      return null;
   }

   protected SoundEvent m_7975_(DamageSource p_27845_) {
      return SoundEvents.f_11741_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11740_;
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public Bee m_142606_(ServerLevel p_148760_, AgeableMob p_148761_) {
      return EntityType.f_20550_.m_20615_(p_148760_);
   }

   protected float m_6431_(Pose p_27804_, EntityDimensions p_27805_) {
      return this.m_6162_() ? p_27805_.f_20378_ * 0.5F : p_27805_.f_20378_ * 0.5F;
   }

   public boolean m_142535_(float p_148750_, float p_148751_, DamageSource p_148752_) {
      return false;
   }

   protected void m_7840_(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {
   }

   public boolean m_142039_() {
      return this.m_142592_() && this.f_19797_ % f_148719_ == 0;
   }

   public boolean m_142592_() {
      return !this.f_19861_;
   }

   public void m_27864_() {
      this.m_27919_(false);
      this.m_27870_();
   }

   public boolean m_6469_(DamageSource p_27762_, float p_27763_) {
      if (this.m_6673_(p_27762_)) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_) {
            this.f_27699_.m_28087_();
         }

         return super.m_6469_(p_27762_, p_27763_);
      }
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   protected void m_6197_(Tag<Fluid> p_27825_) {
      this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.01D, 0.0D));
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.5F * this.m_20192_()), (double)(this.m_20205_() * 0.2F));
   }

   boolean m_27816_(BlockPos p_27817_, int p_27818_) {
      return p_27817_.m_123314_(this.m_142538_(), (double)p_27818_);
   }

   abstract class BaseBeeGoal extends Goal {
      public abstract boolean m_7989_();

      public abstract boolean m_8011_();

      public boolean m_8036_() {
         return this.m_7989_() && !Bee.this.m_21660_();
      }

      public boolean m_8045_() {
         return this.m_8011_() && !Bee.this.m_21660_();
      }
   }

   class BeeAttackGoal extends MeleeAttackGoal {
      BeeAttackGoal(PathfinderMob p_27960_, double p_27961_, boolean p_27962_) {
         super(p_27960_, p_27961_, p_27962_);
      }

      public boolean m_8036_() {
         return super.m_8036_() && Bee.this.m_21660_() && !Bee.this.m_27857_();
      }

      public boolean m_8045_() {
         return super.m_8045_() && Bee.this.m_21660_() && !Bee.this.m_27857_();
      }
   }

   static class BeeBecomeAngryTargetGoal extends NearestAttackableTargetGoal<Player> {
      BeeBecomeAngryTargetGoal(Bee p_27966_) {
         super(p_27966_, Player.class, 10, true, false, p_27966_::m_21674_);
      }

      public boolean m_8036_() {
         return this.m_27969_() && super.m_8036_();
      }

      public boolean m_8045_() {
         boolean flag = this.m_27969_();
         if (flag && this.f_26135_.m_5448_() != null) {
            return super.m_8045_();
         } else {
            this.f_26137_ = null;
            return false;
         }
      }

      private boolean m_27969_() {
         Bee bee = (Bee)this.f_26135_;
         return bee.m_21660_() && !bee.m_27857_();
      }
   }

   class BeeEnterHiveGoal extends Bee.BaseBeeGoal {
      public boolean m_7989_() {
         if (Bee.this.m_27854_() && Bee.this.m_27866_() && Bee.this.f_27698_.m_123306_(Bee.this.m_20182_(), 2.0D)) {
            BlockEntity blockentity = Bee.this.f_19853_.m_7702_(Bee.this.f_27698_);
            if (blockentity instanceof BeehiveBlockEntity) {
               BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
               if (!beehiveblockentity.m_58775_()) {
                  return true;
               }

               Bee.this.f_27698_ = null;
            }
         }

         return false;
      }

      public boolean m_8011_() {
         return false;
      }

      public void m_8056_() {
         BlockEntity blockentity = Bee.this.f_19853_.m_7702_(Bee.this.f_27698_);
         if (blockentity instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
            beehiveblockentity.m_58741_(Bee.this, Bee.this.m_27856_());
         }

      }
   }

   @VisibleForDebug
   public class BeeGoToHiveGoal extends Bee.BaseBeeGoal {
      public static final int f_148804_ = 600;
      int f_27980_ = Bee.this.f_19853_.f_46441_.nextInt(10);
      private static final int f_148805_ = 3;
      final List<BlockPos> f_27981_ = Lists.newArrayList();
      @Nullable
      private Path f_27982_;
      private static final int f_148806_ = 60;
      private int f_27983_;

      BeeGoToHiveGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_7989_() {
         return Bee.this.f_27698_ != null && !Bee.this.m_21536_() && Bee.this.m_27866_() && !this.m_28001_(Bee.this.f_27698_) && Bee.this.f_19853_.m_8055_(Bee.this.f_27698_).m_60620_(BlockTags.f_13072_);
      }

      public boolean m_8011_() {
         return this.m_7989_();
      }

      public void m_8056_() {
         this.f_27980_ = 0;
         this.f_27983_ = 0;
         super.m_8056_();
      }

      public void m_8041_() {
         this.f_27980_ = 0;
         this.f_27983_ = 0;
         Bee.this.f_21344_.m_26573_();
         Bee.this.f_21344_.m_26566_();
      }

      public void m_8037_() {
         if (Bee.this.f_27698_ != null) {
            ++this.f_27980_;
            if (this.f_27980_ > 600) {
               this.m_28007_();
            } else if (!Bee.this.f_21344_.m_26572_()) {
               if (!Bee.this.m_27816_(Bee.this.f_27698_, 16)) {
                  if (Bee.this.m_27889_(Bee.this.f_27698_)) {
                     this.m_28008_();
                  } else {
                     Bee.this.m_27880_(Bee.this.f_27698_);
                  }
               } else {
                  boolean flag = this.m_27990_(Bee.this.f_27698_);
                  if (!flag) {
                     this.m_28007_();
                  } else if (this.f_27982_ != null && Bee.this.f_21344_.m_26570_().m_77385_(this.f_27982_)) {
                     ++this.f_27983_;
                     if (this.f_27983_ > 60) {
                        this.m_28008_();
                        this.f_27983_ = 0;
                     }
                  } else {
                     this.f_27982_ = Bee.this.f_21344_.m_26570_();
                  }

               }
            }
         }
      }

      private boolean m_27990_(BlockPos p_27991_) {
         Bee.this.f_21344_.m_26529_(10.0F);
         Bee.this.f_21344_.m_26519_((double)p_27991_.m_123341_(), (double)p_27991_.m_123342_(), (double)p_27991_.m_123343_(), 1.0D);
         return Bee.this.f_21344_.m_26570_() != null && Bee.this.f_21344_.m_26570_().m_77403_();
      }

      boolean m_27993_(BlockPos p_27994_) {
         return this.f_27981_.contains(p_27994_);
      }

      private void m_27998_(BlockPos p_27999_) {
         this.f_27981_.add(p_27999_);

         while(this.f_27981_.size() > 3) {
            this.f_27981_.remove(0);
         }

      }

      void m_28006_() {
         this.f_27981_.clear();
      }

      private void m_28007_() {
         if (Bee.this.f_27698_ != null) {
            this.m_27998_(Bee.this.f_27698_);
         }

         this.m_28008_();
      }

      private void m_28008_() {
         Bee.this.f_27698_ = null;
         Bee.this.f_27713_ = 200;
      }

      private boolean m_28001_(BlockPos p_28002_) {
         if (Bee.this.m_27816_(p_28002_, 2)) {
            return true;
         } else {
            Path path = Bee.this.f_21344_.m_26570_();
            return path != null && path.m_77406_().equals(p_28002_) && path.m_77403_() && path.m_77392_();
         }
      }
   }

   public class BeeGoToKnownFlowerGoal extends Bee.BaseBeeGoal {
      private static final int f_148807_ = 600;
      int f_28010_ = Bee.this.f_19853_.f_46441_.nextInt(10);

      BeeGoToKnownFlowerGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_7989_() {
         return Bee.this.f_27697_ != null && !Bee.this.m_21536_() && this.m_28020_() && Bee.this.m_27896_(Bee.this.f_27697_) && !Bee.this.m_27816_(Bee.this.f_27697_, 2);
      }

      public boolean m_8011_() {
         return this.m_7989_();
      }

      public void m_8056_() {
         this.f_28010_ = 0;
         super.m_8056_();
      }

      public void m_8041_() {
         this.f_28010_ = 0;
         Bee.this.f_21344_.m_26573_();
         Bee.this.f_21344_.m_26566_();
      }

      public void m_8037_() {
         if (Bee.this.f_27697_ != null) {
            ++this.f_28010_;
            if (this.f_28010_ > 600) {
               Bee.this.f_27697_ = null;
            } else if (!Bee.this.f_21344_.m_26572_()) {
               if (Bee.this.m_27889_(Bee.this.f_27697_)) {
                  Bee.this.f_27697_ = null;
               } else {
                  Bee.this.m_27880_(Bee.this.f_27697_);
               }
            }
         }
      }

      private boolean m_28020_() {
         return Bee.this.f_27710_ > 2400;
      }
   }

   class BeeGrowCropGoal extends Bee.BaseBeeGoal {
      static final int f_148808_ = 30;

      public boolean m_7989_() {
         if (Bee.this.m_27869_() >= 10) {
            return false;
         } else if (Bee.this.f_19796_.nextFloat() < 0.3F) {
            return false;
         } else {
            return Bee.this.m_27856_() && Bee.this.m_27872_();
         }
      }

      public boolean m_8011_() {
         return this.m_7989_();
      }

      public void m_8037_() {
         if (Bee.this.f_19796_.nextInt(30) == 0) {
            for(int i = 1; i <= 2; ++i) {
               BlockPos blockpos = Bee.this.m_142538_().m_6625_(i);
               BlockState blockstate = Bee.this.f_19853_.m_8055_(blockpos);
               Block block = blockstate.m_60734_();
               boolean flag = false;
               IntegerProperty integerproperty = null;
               if (blockstate.m_60620_(BlockTags.f_13074_)) {
                  if (block instanceof CropBlock) {
                     CropBlock cropblock = (CropBlock)block;
                     if (!cropblock.m_52307_(blockstate)) {
                        flag = true;
                        integerproperty = cropblock.m_7959_();
                     }
                  } else if (block instanceof StemBlock) {
                     int j = blockstate.m_61143_(StemBlock.f_57013_);
                     if (j < 7) {
                        flag = true;
                        integerproperty = StemBlock.f_57013_;
                     }
                  } else if (blockstate.m_60713_(Blocks.f_50685_)) {
                     int k = blockstate.m_61143_(SweetBerryBushBlock.f_57244_);
                     if (k < 3) {
                        flag = true;
                        integerproperty = SweetBerryBushBlock.f_57244_;
                     }
                  } else if (blockstate.m_60713_(Blocks.f_152538_) || blockstate.m_60713_(Blocks.f_152539_)) {
                     ((BonemealableBlock)blockstate.m_60734_()).m_7719_((ServerLevel)Bee.this.f_19853_, Bee.this.f_19796_, blockpos, blockstate);
                  }

                  if (flag) {
                     Bee.this.f_19853_.m_46796_(2005, blockpos, 0);
                     Bee.this.f_19853_.m_46597_(blockpos, blockstate.m_61124_(integerproperty, Integer.valueOf(blockstate.m_61143_(integerproperty) + 1)));
                     Bee.this.m_27871_();
                  }
               }
            }

         }
      }
   }

   class BeeHurtByOtherGoal extends HurtByTargetGoal {
      BeeHurtByOtherGoal(Bee p_28033_) {
         super(p_28033_);
      }

      public boolean m_8045_() {
         return Bee.this.m_21660_() && super.m_8045_();
      }

      protected void m_5766_(Mob p_28035_, LivingEntity p_28036_) {
         if (p_28035_ instanceof Bee && this.f_26135_.m_142582_(p_28036_)) {
            p_28035_.m_6710_(p_28036_);
         }

      }
   }

   class BeeLocateHiveGoal extends Bee.BaseBeeGoal {
      public boolean m_7989_() {
         return Bee.this.f_27713_ == 0 && !Bee.this.m_27854_() && Bee.this.m_27866_();
      }

      public boolean m_8011_() {
         return false;
      }

      public void m_8056_() {
         Bee.this.f_27713_ = 200;
         List<BlockPos> list = this.m_28055_();
         if (!list.isEmpty()) {
            for(BlockPos blockpos : list) {
               if (!Bee.this.f_27700_.m_27993_(blockpos)) {
                  Bee.this.f_27698_ = blockpos;
                  return;
               }
            }

            Bee.this.f_27700_.m_28006_();
            Bee.this.f_27698_ = list.get(0);
         }
      }

      private List<BlockPos> m_28055_() {
         BlockPos blockpos = Bee.this.m_142538_();
         PoiManager poimanager = ((ServerLevel)Bee.this.f_19853_).m_8904_();
         Stream<PoiRecord> stream = poimanager.m_27181_((p_28045_) -> {
            return p_28045_ == PoiType.f_27348_ || p_28045_ == PoiType.f_27349_;
         }, blockpos, 20, PoiManager.Occupancy.ANY);
         return stream.map(PoiRecord::m_27257_).filter(Bee.this::m_27884_).sorted(Comparator.comparingDouble((p_148811_) -> {
            return p_148811_.m_123331_(blockpos);
         })).collect(Collectors.toList());
      }
   }

   class BeeLookControl extends LookControl {
      BeeLookControl(Mob p_28059_) {
         super(p_28059_);
      }

      public void m_8128_() {
         if (!Bee.this.m_21660_()) {
            super.m_8128_();
         }
      }

      protected boolean m_8106_() {
         return !Bee.this.f_27699_.m_28086_();
      }
   }

   class BeePollinateGoal extends Bee.BaseBeeGoal {
      private static final int f_148812_ = 400;
      private static final int f_148813_ = 20;
      private static final int f_148814_ = 60;
      private final Predicate<BlockState> f_28063_ = (p_28074_) -> {
         if (p_28074_.m_60620_(BlockTags.f_13041_)) {
            if (p_28074_.m_60713_(Blocks.f_50355_)) {
               return p_28074_.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.UPPER;
            } else {
               return true;
            }
         } else {
            return false;
         }
      };
      private static final double f_148815_ = 0.1D;
      private static final int f_148816_ = 25;
      private static final float f_148817_ = 0.35F;
      private static final float f_148818_ = 0.6F;
      private static final float f_148819_ = 0.33333334F;
      private int f_28064_;
      private int f_28065_;
      private boolean f_28066_;
      private Vec3 f_28067_;
      private int f_28068_;
      private static final int f_148820_ = 600;

      BeePollinateGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_7989_() {
         if (Bee.this.f_27714_ > 0) {
            return false;
         } else if (Bee.this.m_27856_()) {
            return false;
         } else if (Bee.this.f_19853_.m_46471_()) {
            return false;
         } else {
            Optional<BlockPos> optional = this.m_28090_();
            if (optional.isPresent()) {
               Bee.this.f_27697_ = optional.get();
               Bee.this.f_21344_.m_26519_((double)Bee.this.f_27697_.m_123341_() + 0.5D, (double)Bee.this.f_27697_.m_123342_() + 0.5D, (double)Bee.this.f_27697_.m_123343_() + 0.5D, (double)1.2F);
               return true;
            } else {
               Bee.this.f_27714_ = Mth.m_14072_(Bee.this.f_19796_, 20, 60);
               return false;
            }
         }
      }

      public boolean m_8011_() {
         if (!this.f_28066_) {
            return false;
         } else if (!Bee.this.m_27852_()) {
            return false;
         } else if (Bee.this.f_19853_.m_46471_()) {
            return false;
         } else if (this.m_28085_()) {
            return Bee.this.f_19796_.nextFloat() < 0.2F;
         } else if (Bee.this.f_19797_ % 20 == 0 && !Bee.this.m_27896_(Bee.this.f_27697_)) {
            Bee.this.f_27697_ = null;
            return false;
         } else {
            return true;
         }
      }

      private boolean m_28085_() {
         return this.f_28064_ > 400;
      }

      boolean m_28086_() {
         return this.f_28066_;
      }

      void m_28087_() {
         this.f_28066_ = false;
      }

      public void m_8056_() {
         this.f_28064_ = 0;
         this.f_28068_ = 0;
         this.f_28065_ = 0;
         this.f_28066_ = true;
         Bee.this.m_27853_();
      }

      public void m_8041_() {
         if (this.m_28085_()) {
            Bee.this.m_27919_(true);
         }

         this.f_28066_ = false;
         Bee.this.f_21344_.m_26573_();
         Bee.this.f_27714_ = 200;
      }

      public void m_8037_() {
         ++this.f_28068_;
         if (this.f_28068_ > 600) {
            Bee.this.f_27697_ = null;
         } else {
            Vec3 vec3 = Vec3.m_82539_(Bee.this.f_27697_).m_82520_(0.0D, (double)0.6F, 0.0D);
            if (vec3.m_82554_(Bee.this.m_20182_()) > 1.0D) {
               this.f_28067_ = vec3;
               this.m_28088_();
            } else {
               if (this.f_28067_ == null) {
                  this.f_28067_ = vec3;
               }

               boolean flag = Bee.this.m_20182_().m_82554_(this.f_28067_) <= 0.1D;
               boolean flag1 = true;
               if (!flag && this.f_28068_ > 600) {
                  Bee.this.f_27697_ = null;
               } else {
                  if (flag) {
                     boolean flag2 = Bee.this.f_19796_.nextInt(25) == 0;
                     if (flag2) {
                        this.f_28067_ = new Vec3(vec3.m_7096_() + (double)this.m_28089_(), vec3.m_7098_(), vec3.m_7094_() + (double)this.m_28089_());
                        Bee.this.f_21344_.m_26573_();
                     } else {
                        flag1 = false;
                     }

                     Bee.this.m_21563_().m_24946_(vec3.m_7096_(), vec3.m_7098_(), vec3.m_7094_());
                  }

                  if (flag1) {
                     this.m_28088_();
                  }

                  ++this.f_28064_;
                  if (Bee.this.f_19796_.nextFloat() < 0.05F && this.f_28064_ > this.f_28065_ + 60) {
                     this.f_28065_ = this.f_28064_;
                     Bee.this.m_5496_(SoundEvents.f_11693_, 1.0F, 1.0F);
                  }

               }
            }
         }
      }

      private void m_28088_() {
         Bee.this.m_21566_().m_6849_(this.f_28067_.m_7096_(), this.f_28067_.m_7098_(), this.f_28067_.m_7094_(), (double)0.35F);
      }

      private float m_28089_() {
         return (Bee.this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
      }

      private Optional<BlockPos> m_28090_() {
         return this.m_28075_(this.f_28063_, 5.0D);
      }

      private Optional<BlockPos> m_28075_(Predicate<BlockState> p_28076_, double p_28077_) {
         BlockPos blockpos = Bee.this.m_142538_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int i = 0; (double)i <= p_28077_; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < p_28077_; ++j) {
               for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                  for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                     blockpos$mutableblockpos.m_122154_(blockpos, k, i - 1, l);
                     if (blockpos.m_123314_(blockpos$mutableblockpos, p_28077_) && p_28076_.test(Bee.this.f_19853_.m_8055_(blockpos$mutableblockpos))) {
                        return Optional.of(blockpos$mutableblockpos);
                     }
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   class BeeWanderGoal extends Goal {
      private static final int f_148821_ = 22;

      BeeWanderGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         return Bee.this.f_21344_.m_26571_() && Bee.this.f_19796_.nextInt(10) == 0;
      }

      public boolean m_8045_() {
         return Bee.this.f_21344_.m_26572_();
      }

      public void m_8056_() {
         Vec3 vec3 = this.m_28097_();
         if (vec3 != null) {
            Bee.this.f_21344_.m_26536_(Bee.this.f_21344_.m_7864_(new BlockPos(vec3), 1), 1.0D);
         }

      }

      @Nullable
      private Vec3 m_28097_() {
         Vec3 vec3;
         if (Bee.this.m_27872_() && !Bee.this.m_27816_(Bee.this.f_27698_, 22)) {
            Vec3 vec31 = Vec3.m_82512_(Bee.this.f_27698_);
            vec3 = vec31.m_82546_(Bee.this.m_20182_()).m_82541_();
         } else {
            vec3 = Bee.this.m_20252_(0.0F);
         }

         int i = 8;
         Vec3 vec32 = HoverRandomPos.m_148465_(Bee.this, 8, 7, vec3.f_82479_, vec3.f_82481_, ((float)Math.PI / 2F), 3, 1);
         return vec32 != null ? vec32 : AirAndWaterRandomPos.m_148357_(Bee.this, 8, 4, -2, vec3.f_82479_, vec3.f_82481_, (double)((float)Math.PI / 2F));
      }
   }
}
