package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Drowned extends Zombie implements RangedAttackMob {
   public static final float f_149692_ = 0.03F;
   boolean f_32342_;
   protected final WaterBoundPathNavigation f_32340_;
   protected final GroundPathNavigation f_32341_;

   public Drowned(EntityType<? extends Drowned> p_32344_, Level p_32345_) {
      super(p_32344_, p_32345_);
      this.f_19793_ = 1.0F;
      this.f_21342_ = new Drowned.DrownedMoveControl(this);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_32340_ = new WaterBoundPathNavigation(this, p_32345_);
      this.f_32341_ = new GroundPathNavigation(this, p_32345_);
   }

   protected void m_6878_() {
      this.f_21345_.m_25352_(1, new Drowned.DrownedGoToWaterGoal(this, 1.0D));
      this.f_21345_.m_25352_(2, new Drowned.DrownedTridentAttackGoal(this, 1.0D, 40, 10.0F));
      this.f_21345_.m_25352_(2, new Drowned.DrownedAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(5, new Drowned.DrownedGoToBeachGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new Drowned.DrownedSwimUpGoal(this, 1.0D, this.f_19853_.m_5736_()));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0D));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Drowned.class)).m_26044_(ZombifiedPiglin.class));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::m_32395_));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Axolotl.class, true, false));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_32372_, DifficultyInstance p_32373_, MobSpawnType p_32374_, @Nullable SpawnGroupData p_32375_, @Nullable CompoundTag p_32376_) {
      p_32375_ = super.m_6518_(p_32372_, p_32373_, p_32374_, p_32375_, p_32376_);
      if (this.m_6844_(EquipmentSlot.OFFHAND).m_41619_() && this.f_19796_.nextFloat() < 0.03F) {
         this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack(Items.f_42715_));
         this.f_21347_[EquipmentSlot.OFFHAND.m_20749_()] = 2.0F;
      }

      return p_32375_;
   }

   public static boolean m_32349_(EntityType<Drowned> p_32350_, ServerLevelAccessor p_32351_, MobSpawnType p_32352_, BlockPos p_32353_, Random p_32354_) {
      Optional<ResourceKey<Biome>> optional = p_32351_.m_45837_(p_32353_);
      boolean flag = p_32351_.m_46791_() != Difficulty.PEACEFUL && m_33008_(p_32351_, p_32353_, p_32354_) && (p_32352_ == MobSpawnType.SPAWNER || p_32351_.m_6425_(p_32353_).m_76153_(FluidTags.f_13131_));
      if (!Objects.equals(optional, Optional.of(Biomes.f_48208_)) && !Objects.equals(optional, Optional.of(Biomes.f_48212_))) {
         return p_32354_.nextInt(40) == 0 && m_32366_(p_32351_, p_32353_) && flag;
      } else {
         return p_32354_.nextInt(15) == 0 && flag;
      }
   }

   private static boolean m_32366_(LevelAccessor p_32367_, BlockPos p_32368_) {
      return p_32368_.m_123342_() < p_32367_.m_5736_() - 5;
   }

   protected boolean m_7586_() {
      return false;
   }

   protected SoundEvent m_7515_() {
      return this.m_20069_() ? SoundEvents.f_11816_ : SoundEvents.f_11815_;
   }

   protected SoundEvent m_7975_(DamageSource p_32386_) {
      return this.m_20069_() ? SoundEvents.f_11820_ : SoundEvents.f_11819_;
   }

   protected SoundEvent m_5592_() {
      return this.m_20069_() ? SoundEvents.f_11818_ : SoundEvents.f_11817_;
   }

   protected SoundEvent m_7660_() {
      return SoundEvents.f_11875_;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_11876_;
   }

   protected ItemStack m_5728_() {
      return ItemStack.f_41583_;
   }

   protected void m_6851_(DifficultyInstance p_32348_) {
      if ((double)this.f_19796_.nextFloat() > 0.9D) {
         int i = this.f_19796_.nextInt(16);
         if (i < 10) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42713_));
         } else {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42523_));
         }
      }

   }

   protected boolean m_7808_(ItemStack p_32364_, ItemStack p_32365_) {
      if (p_32365_.m_150930_(Items.f_42715_)) {
         return false;
      } else if (p_32365_.m_150930_(Items.f_42713_)) {
         if (p_32364_.m_150930_(Items.f_42713_)) {
            return p_32364_.m_41773_() < p_32365_.m_41773_();
         } else {
            return false;
         }
      } else {
         return p_32364_.m_150930_(Items.f_42713_) ? true : super.m_7808_(p_32364_, p_32365_);
      }
   }

   protected boolean m_7593_() {
      return false;
   }

   public boolean m_6914_(LevelReader p_32370_) {
      return p_32370_.m_45784_(this);
   }

   public boolean m_32395_(@Nullable LivingEntity p_32396_) {
      if (p_32396_ != null) {
         return !this.f_19853_.m_46461_() || p_32396_.m_20069_();
      } else {
         return false;
      }
   }

   public boolean m_6063_() {
      return !this.m_6069_();
   }

   boolean m_32392_() {
      if (this.f_32342_) {
         return true;
      } else {
         LivingEntity livingentity = this.m_5448_();
         return livingentity != null && livingentity.m_20069_();
      }
   }

   public void m_7023_(Vec3 p_32394_) {
      if (this.m_6142_() && this.m_20069_() && this.m_32392_()) {
         this.m_19920_(0.01F, p_32394_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
      } else {
         super.m_7023_(p_32394_);
      }

   }

   public void m_5844_() {
      if (!this.f_19853_.f_46443_) {
         if (this.m_6142_() && this.m_20069_() && this.m_32392_()) {
            this.f_21344_ = this.f_32340_;
            this.m_20282_(true);
         } else {
            this.f_21344_ = this.f_32341_;
            this.m_20282_(false);
         }
      }

   }

   protected boolean m_32391_() {
      Path path = this.m_21573_().m_26570_();
      if (path != null) {
         BlockPos blockpos = path.m_77406_();
         if (blockpos != null) {
            double d0 = this.m_20275_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
            if (d0 < 4.0D) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_6504_(LivingEntity p_32356_, float p_32357_) {
      ThrownTrident throwntrident = new ThrownTrident(this.f_19853_, this, new ItemStack(Items.f_42713_));
      double d0 = p_32356_.m_20185_() - this.m_20185_();
      double d1 = p_32356_.m_20227_(0.3333333333333333D) - throwntrident.m_20186_();
      double d2 = p_32356_.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      throwntrident.m_6686_(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
      this.m_5496_(SoundEvents.f_11821_, 1.0F, 1.0F / (this.m_21187_().nextFloat() * 0.4F + 0.8F));
      this.f_19853_.m_7967_(throwntrident);
   }

   public void m_32398_(boolean p_32399_) {
      this.f_32342_ = p_32399_;
   }

   static class DrownedAttackGoal extends ZombieAttackGoal {
      private final Drowned f_32400_;

      public DrownedAttackGoal(Drowned p_32402_, double p_32403_, boolean p_32404_) {
         super(p_32402_, p_32403_, p_32404_);
         this.f_32400_ = p_32402_;
      }

      public boolean m_8036_() {
         return super.m_8036_() && this.f_32400_.m_32395_(this.f_32400_.m_5448_());
      }

      public boolean m_8045_() {
         return super.m_8045_() && this.f_32400_.m_32395_(this.f_32400_.m_5448_());
      }
   }

   static class DrownedGoToBeachGoal extends MoveToBlockGoal {
      private final Drowned f_32407_;

      public DrownedGoToBeachGoal(Drowned p_32409_, double p_32410_) {
         super(p_32409_, p_32410_, 8, 2);
         this.f_32407_ = p_32409_;
      }

      public boolean m_8036_() {
         return super.m_8036_() && !this.f_32407_.f_19853_.m_46461_() && this.f_32407_.m_20069_() && this.f_32407_.m_20186_() >= (double)(this.f_32407_.f_19853_.m_5736_() - 3);
      }

      public boolean m_8045_() {
         return super.m_8045_();
      }

      protected boolean m_6465_(LevelReader p_32413_, BlockPos p_32414_) {
         BlockPos blockpos = p_32414_.m_7494_();
         return p_32413_.m_46859_(blockpos) && p_32413_.m_46859_(blockpos.m_7494_()) ? p_32413_.m_8055_(p_32414_).m_60634_(p_32413_, p_32414_, this.f_32407_) : false;
      }

      public void m_8056_() {
         this.f_32407_.m_32398_(false);
         this.f_32407_.f_21344_ = this.f_32407_.f_32341_;
         super.m_8056_();
      }

      public void m_8041_() {
         super.m_8041_();
      }
   }

   static class DrownedGoToWaterGoal extends Goal {
      private final PathfinderMob f_32418_;
      private double f_32419_;
      private double f_32420_;
      private double f_32421_;
      private final double f_32422_;
      private final Level f_32423_;

      public DrownedGoToWaterGoal(PathfinderMob p_32425_, double p_32426_) {
         this.f_32418_ = p_32425_;
         this.f_32422_ = p_32426_;
         this.f_32423_ = p_32425_.f_19853_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         if (!this.f_32423_.m_46461_()) {
            return false;
         } else if (this.f_32418_.m_20069_()) {
            return false;
         } else {
            Vec3 vec3 = this.m_32430_();
            if (vec3 == null) {
               return false;
            } else {
               this.f_32419_ = vec3.f_82479_;
               this.f_32420_ = vec3.f_82480_;
               this.f_32421_ = vec3.f_82481_;
               return true;
            }
         }
      }

      public boolean m_8045_() {
         return !this.f_32418_.m_21573_().m_26571_();
      }

      public void m_8056_() {
         this.f_32418_.m_21573_().m_26519_(this.f_32419_, this.f_32420_, this.f_32421_, this.f_32422_);
      }

      @Nullable
      private Vec3 m_32430_() {
         Random random = this.f_32418_.m_21187_();
         BlockPos blockpos = this.f_32418_.m_142538_();

         for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.m_142082_(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
            if (this.f_32423_.m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
               return Vec3.m_82539_(blockpos1);
            }
         }

         return null;
      }
   }

   static class DrownedMoveControl extends MoveControl {
      private final Drowned f_32431_;

      public DrownedMoveControl(Drowned p_32433_) {
         super(p_32433_);
         this.f_32431_ = p_32433_;
      }

      public void m_8126_() {
         LivingEntity livingentity = this.f_32431_.m_5448_();
         if (this.f_32431_.m_32392_() && this.f_32431_.m_20069_()) {
            if (livingentity != null && livingentity.m_20186_() > this.f_32431_.m_20186_() || this.f_32431_.f_32342_) {
               this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_(0.0D, 0.002D, 0.0D));
            }

            if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_32431_.m_21573_().m_26571_()) {
               this.f_32431_.m_7910_(0.0F);
               return;
            }

            double d0 = this.f_24975_ - this.f_32431_.m_20185_();
            double d1 = this.f_24976_ - this.f_32431_.m_20186_();
            double d2 = this.f_24977_ - this.f_32431_.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = d1 / d3;
            float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.f_32431_.m_146922_(this.m_24991_(this.f_32431_.m_146908_(), f, 90.0F));
            this.f_32431_.f_20883_ = this.f_32431_.m_146908_();
            float f1 = (float)(this.f_24978_ * this.f_32431_.m_21133_(Attributes.f_22279_));
            float f2 = Mth.m_14179_(0.125F, this.f_32431_.m_6113_(), f1);
            this.f_32431_.m_7910_(f2);
            this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_((double)f2 * d0 * 0.005D, (double)f2 * d1 * 0.1D, (double)f2 * d2 * 0.005D));
         } else {
            if (!this.f_32431_.f_19861_) {
               this.f_32431_.m_20256_(this.f_32431_.m_20184_().m_82520_(0.0D, -0.008D, 0.0D));
            }

            super.m_8126_();
         }

      }
   }

   static class DrownedSwimUpGoal extends Goal {
      private final Drowned f_32435_;
      private final double f_32436_;
      private final int f_32437_;
      private boolean f_32438_;

      public DrownedSwimUpGoal(Drowned p_32440_, double p_32441_, int p_32442_) {
         this.f_32435_ = p_32440_;
         this.f_32436_ = p_32441_;
         this.f_32437_ = p_32442_;
      }

      public boolean m_8036_() {
         return !this.f_32435_.f_19853_.m_46461_() && this.f_32435_.m_20069_() && this.f_32435_.m_20186_() < (double)(this.f_32437_ - 2);
      }

      public boolean m_8045_() {
         return this.m_8036_() && !this.f_32438_;
      }

      public void m_8037_() {
         if (this.f_32435_.m_20186_() < (double)(this.f_32437_ - 1) && (this.f_32435_.m_21573_().m_26571_() || this.f_32435_.m_32391_())) {
            Vec3 vec3 = DefaultRandomPos.m_148412_(this.f_32435_, 4, 8, new Vec3(this.f_32435_.m_20185_(), (double)(this.f_32437_ - 1), this.f_32435_.m_20189_()), (double)((float)Math.PI / 2F));
            if (vec3 == null) {
               this.f_32438_ = true;
               return;
            }

            this.f_32435_.m_21573_().m_26519_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, this.f_32436_);
         }

      }

      public void m_8056_() {
         this.f_32435_.m_32398_(true);
         this.f_32438_ = false;
      }

      public void m_8041_() {
         this.f_32435_.m_32398_(false);
      }
   }

   static class DrownedTridentAttackGoal extends RangedAttackGoal {
      private final Drowned f_32448_;

      public DrownedTridentAttackGoal(RangedAttackMob p_32450_, double p_32451_, int p_32452_, float p_32453_) {
         super(p_32450_, p_32451_, p_32452_, p_32453_);
         this.f_32448_ = (Drowned)p_32450_;
      }

      public boolean m_8036_() {
         return super.m_8036_() && this.f_32448_.m_21205_().m_150930_(Items.f_42713_);
      }

      public void m_8056_() {
         super.m_8056_();
         this.f_32448_.m_21561_(true);
         this.f_32448_.m_6672_(InteractionHand.MAIN_HAND);
      }

      public void m_8041_() {
         super.m_8041_();
         this.f_32448_.m_5810_();
         this.f_32448_.m_21561_(false);
      }
   }
}