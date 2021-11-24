package net.minecraft.world.entity.animal;

import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class Turtle extends Animal {
   private static final EntityDataAccessor<BlockPos> f_30123_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135038_);
   private static final EntityDataAccessor<Boolean> f_30124_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_30125_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<BlockPos> f_30126_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135038_);
   private static final EntityDataAccessor<Boolean> f_30127_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_30128_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
   public static final Ingredient f_149066_ = Ingredient.m_43929_(Blocks.f_50037_.m_5456_());
   int f_30129_;
   public static final Predicate<LivingEntity> f_30122_ = (p_30226_) -> {
      return p_30226_.m_6162_() && !p_30226_.m_20069_();
   };

   public Turtle(EntityType<? extends Turtle> p_30132_, Level p_30133_) {
      super(p_30132_, p_30133_);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.m_21441_(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
      this.m_21441_(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
      this.m_21441_(BlockPathTypes.DOOR_OPEN, -1.0F);
      this.f_21342_ = new Turtle.TurtleMoveControl(this);
      this.f_19793_ = 1.0F;
   }

   public void m_30219_(BlockPos p_30220_) {
      this.f_19804_.m_135381_(f_30123_, p_30220_);
   }

   BlockPos m_30208_() {
      return this.f_19804_.m_135370_(f_30123_);
   }

   void m_30223_(BlockPos p_30224_) {
      this.f_19804_.m_135381_(f_30126_, p_30224_);
   }

   BlockPos m_30209_() {
      return this.f_19804_.m_135370_(f_30126_);
   }

   public boolean m_30205_() {
      return this.f_19804_.m_135370_(f_30124_);
   }

   void m_30234_(boolean p_30235_) {
      this.f_19804_.m_135381_(f_30124_, p_30235_);
   }

   public boolean m_30206_() {
      return this.f_19804_.m_135370_(f_30125_);
   }

   void m_30236_(boolean p_30237_) {
      this.f_30129_ = p_30237_ ? 1 : 0;
      this.f_19804_.m_135381_(f_30125_, p_30237_);
   }

   boolean m_30211_() {
      return this.f_19804_.m_135370_(f_30127_);
   }

   void m_30238_(boolean p_30239_) {
      this.f_19804_.m_135381_(f_30127_, p_30239_);
   }

   boolean m_30212_() {
      return this.f_19804_.m_135370_(f_30128_);
   }

   void m_30240_(boolean p_30241_) {
      this.f_19804_.m_135381_(f_30128_, p_30241_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_30123_, BlockPos.f_121853_);
      this.f_19804_.m_135372_(f_30124_, false);
      this.f_19804_.m_135372_(f_30126_, BlockPos.f_121853_);
      this.f_19804_.m_135372_(f_30127_, false);
      this.f_19804_.m_135372_(f_30128_, false);
      this.f_19804_.m_135372_(f_30125_, false);
   }

   public void m_7380_(CompoundTag p_30176_) {
      super.m_7380_(p_30176_);
      p_30176_.m_128405_("HomePosX", this.m_30208_().m_123341_());
      p_30176_.m_128405_("HomePosY", this.m_30208_().m_123342_());
      p_30176_.m_128405_("HomePosZ", this.m_30208_().m_123343_());
      p_30176_.m_128379_("HasEgg", this.m_30205_());
      p_30176_.m_128405_("TravelPosX", this.m_30209_().m_123341_());
      p_30176_.m_128405_("TravelPosY", this.m_30209_().m_123342_());
      p_30176_.m_128405_("TravelPosZ", this.m_30209_().m_123343_());
   }

   public void m_7378_(CompoundTag p_30162_) {
      int i = p_30162_.m_128451_("HomePosX");
      int j = p_30162_.m_128451_("HomePosY");
      int k = p_30162_.m_128451_("HomePosZ");
      this.m_30219_(new BlockPos(i, j, k));
      super.m_7378_(p_30162_);
      this.m_30234_(p_30162_.m_128471_("HasEgg"));
      int l = p_30162_.m_128451_("TravelPosX");
      int i1 = p_30162_.m_128451_("TravelPosY");
      int j1 = p_30162_.m_128451_("TravelPosZ");
      this.m_30223_(new BlockPos(l, i1, j1));
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_30153_, DifficultyInstance p_30154_, MobSpawnType p_30155_, @Nullable SpawnGroupData p_30156_, @Nullable CompoundTag p_30157_) {
      this.m_30219_(this.m_142538_());
      this.m_30223_(BlockPos.f_121853_);
      return super.m_6518_(p_30153_, p_30154_, p_30155_, p_30156_, p_30157_);
   }

   public static boolean m_30178_(EntityType<Turtle> p_30179_, LevelAccessor p_30180_, MobSpawnType p_30181_, BlockPos p_30182_, Random p_30183_) {
      return p_30182_.m_123342_() < p_30180_.m_5736_() + 4 && TurtleEggBlock.m_57762_(p_30180_, p_30182_) && p_30180_.m_45524_(p_30182_, 0) > 8;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new Turtle.TurtlePanicGoal(this, 1.2D));
      this.f_21345_.m_25352_(1, new Turtle.TurtleBreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(1, new Turtle.TurtleLayEggGoal(this, 1.0D));
      this.f_21345_.m_25352_(2, new TemptGoal(this, 1.1D, f_149066_, false));
      this.f_21345_.m_25352_(3, new Turtle.TurtleGoToWaterGoal(this, 1.0D));
      this.f_21345_.m_25352_(4, new Turtle.TurtleGoHomeGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new Turtle.TurtleTravelGoal(this, 1.0D));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(9, new Turtle.TurtleRandomStrollGoal(this, 1.0D, 100));
   }

   public static AttributeSupplier.Builder m_30207_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0D).m_22268_(Attributes.f_22279_, 0.25D);
   }

   public boolean m_6063_() {
      return false;
   }

   public boolean m_6040_() {
      return true;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public int m_8100_() {
      return 200;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return !this.m_20069_() && this.f_19861_ && !this.m_6162_() ? SoundEvents.f_12530_ : super.m_7515_();
   }

   protected void m_5625_(float p_30192_) {
      super.m_5625_(p_30192_ * 1.5F);
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_12489_;
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_30202_) {
      return this.m_6162_() ? SoundEvents.f_12485_ : SoundEvents.f_12536_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return this.m_6162_() ? SoundEvents.f_12532_ : SoundEvents.f_12531_;
   }

   protected void m_7355_(BlockPos p_30173_, BlockState p_30174_) {
      SoundEvent soundevent = this.m_6162_() ? SoundEvents.f_12488_ : SoundEvents.f_12487_;
      this.m_5496_(soundevent, 0.15F, 1.0F);
   }

   public boolean m_5957_() {
      return super.m_5957_() && !this.m_30205_();
   }

   protected float m_6059_() {
      return this.f_19788_ + 0.15F;
   }

   public float m_6134_() {
      return this.m_6162_() ? 0.3F : 1.0F;
   }

   protected PathNavigation m_6037_(Level p_30171_) {
      return new Turtle.TurtlePathNavigation(this, p_30171_);
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_149068_, AgeableMob p_149069_) {
      return EntityType.f_20490_.m_20615_(p_149068_);
   }

   public boolean m_6898_(ItemStack p_30231_) {
      return p_30231_.m_150930_(Blocks.f_50037_.m_5456_());
   }

   public float m_5610_(BlockPos p_30159_, LevelReader p_30160_) {
      if (!this.m_30211_() && p_30160_.m_6425_(p_30159_).m_76153_(FluidTags.f_13131_)) {
         return 10.0F;
      } else {
         return TurtleEggBlock.m_57762_(p_30160_, p_30159_) ? 10.0F : p_30160_.m_46863_(p_30159_) - 0.5F;
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_6084_() && this.m_30206_() && this.f_30129_ >= 1 && this.f_30129_ % 5 == 0) {
         BlockPos blockpos = this.m_142538_();
         if (TurtleEggBlock.m_57762_(this.f_19853_, blockpos)) {
            this.f_19853_.m_46796_(2001, blockpos, Block.m_49956_(this.f_19853_.m_8055_(blockpos.m_7495_())));
         }
      }

   }

   protected void m_142669_() {
      super.m_142669_();
      if (!this.m_6162_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
         this.m_20000_(Items.f_42355_, 1);
      }

   }

   public void m_7023_(Vec3 p_30218_) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(0.1F, p_30218_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
         if (this.m_5448_() == null && (!this.m_30211_() || !this.m_30208_().m_123306_(this.m_20182_(), 20.0D))) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.m_7023_(p_30218_);
      }

   }

   public boolean m_6573_(Player p_30151_) {
      return false;
   }

   public void m_8038_(ServerLevel p_30140_, LightningBolt p_30141_) {
      this.m_6469_(DamageSource.f_19306_, Float.MAX_VALUE);
   }

   static class TurtleBreedGoal extends BreedGoal {
      private final Turtle f_30242_;

      TurtleBreedGoal(Turtle p_30244_, double p_30245_) {
         super(p_30244_, p_30245_);
         this.f_30242_ = p_30244_;
      }

      public boolean m_8036_() {
         return super.m_8036_() && !this.f_30242_.m_30205_();
      }

      protected void m_8026_() {
         ServerPlayer serverplayer = this.f_25113_.m_27592_();
         if (serverplayer == null && this.f_25115_.m_27592_() != null) {
            serverplayer = this.f_25115_.m_27592_();
         }

         if (serverplayer != null) {
            serverplayer.m_36220_(Stats.f_12937_);
            CriteriaTriggers.f_10581_.m_147278_(serverplayer, this.f_25113_, this.f_25115_, (AgeableMob)null);
         }

         this.f_30242_.m_30234_(true);
         this.f_25113_.m_27594_();
         this.f_25115_.m_27594_();
         Random random = this.f_25113_.m_21187_();
         if (this.f_25114_.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.f_25114_.m_7967_(new ExperienceOrb(this.f_25114_, this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), random.nextInt(7) + 1));
         }

      }
   }

   static class TurtleGoHomeGoal extends Goal {
      private final Turtle f_30248_;
      private final double f_30249_;
      private boolean f_30250_;
      private int f_30251_;
      private static final int f_149074_ = 600;

      TurtleGoHomeGoal(Turtle p_30253_, double p_30254_) {
         this.f_30248_ = p_30253_;
         this.f_30249_ = p_30254_;
      }

      public boolean m_8036_() {
         if (this.f_30248_.m_6162_()) {
            return false;
         } else if (this.f_30248_.m_30205_()) {
            return true;
         } else if (this.f_30248_.m_21187_().nextInt(700) != 0) {
            return false;
         } else {
            return !this.f_30248_.m_30208_().m_123306_(this.f_30248_.m_20182_(), 64.0D);
         }
      }

      public void m_8056_() {
         this.f_30248_.m_30238_(true);
         this.f_30250_ = false;
         this.f_30251_ = 0;
      }

      public void m_8041_() {
         this.f_30248_.m_30238_(false);
      }

      public boolean m_8045_() {
         return !this.f_30248_.m_30208_().m_123306_(this.f_30248_.m_20182_(), 7.0D) && !this.f_30250_ && this.f_30251_ <= 600;
      }

      public void m_8037_() {
         BlockPos blockpos = this.f_30248_.m_30208_();
         boolean flag = blockpos.m_123306_(this.f_30248_.m_20182_(), 16.0D);
         if (flag) {
            ++this.f_30251_;
         }

         if (this.f_30248_.m_21573_().m_26571_()) {
            Vec3 vec3 = Vec3.m_82539_(blockpos);
            Vec3 vec31 = DefaultRandomPos.m_148412_(this.f_30248_, 16, 3, vec3, (double)((float)Math.PI / 10F));
            if (vec31 == null) {
               vec31 = DefaultRandomPos.m_148412_(this.f_30248_, 8, 7, vec3, (double)((float)Math.PI / 2F));
            }

            if (vec31 != null && !flag && !this.f_30248_.f_19853_.m_8055_(new BlockPos(vec31)).m_60713_(Blocks.f_49990_)) {
               vec31 = DefaultRandomPos.m_148412_(this.f_30248_, 16, 5, vec3, (double)((float)Math.PI / 2F));
            }

            if (vec31 == null) {
               this.f_30250_ = true;
               return;
            }

            this.f_30248_.m_21573_().m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, this.f_30249_);
         }

      }
   }

   static class TurtleGoToWaterGoal extends MoveToBlockGoal {
      private static final int f_149075_ = 1200;
      private final Turtle f_30260_;

      TurtleGoToWaterGoal(Turtle p_30262_, double p_30263_) {
         super(p_30262_, p_30262_.m_6162_() ? 2.0D : p_30263_, 24);
         this.f_30260_ = p_30262_;
         this.f_25603_ = -1;
      }

      public boolean m_8045_() {
         return !this.f_30260_.m_20069_() && this.f_25601_ <= 1200 && this.m_6465_(this.f_30260_.f_19853_, this.f_25602_);
      }

      public boolean m_8036_() {
         if (this.f_30260_.m_6162_() && !this.f_30260_.m_20069_()) {
            return super.m_8036_();
         } else {
            return !this.f_30260_.m_30211_() && !this.f_30260_.m_20069_() && !this.f_30260_.m_30205_() ? super.m_8036_() : false;
         }
      }

      public boolean m_8064_() {
         return this.f_25601_ % 160 == 0;
      }

      protected boolean m_6465_(LevelReader p_30270_, BlockPos p_30271_) {
         return p_30270_.m_8055_(p_30271_).m_60713_(Blocks.f_49990_);
      }
   }

   static class TurtleLayEggGoal extends MoveToBlockGoal {
      private final Turtle f_30274_;

      TurtleLayEggGoal(Turtle p_30276_, double p_30277_) {
         super(p_30276_, p_30277_, 16);
         this.f_30274_ = p_30276_;
      }

      public boolean m_8036_() {
         return this.f_30274_.m_30205_() && this.f_30274_.m_30208_().m_123306_(this.f_30274_.m_20182_(), 9.0D) ? super.m_8036_() : false;
      }

      public boolean m_8045_() {
         return super.m_8045_() && this.f_30274_.m_30205_() && this.f_30274_.m_30208_().m_123306_(this.f_30274_.m_20182_(), 9.0D);
      }

      public void m_8037_() {
         super.m_8037_();
         BlockPos blockpos = this.f_30274_.m_142538_();
         if (!this.f_30274_.m_20069_() && this.m_25625_()) {
            if (this.f_30274_.f_30129_ < 1) {
               this.f_30274_.m_30236_(true);
            } else if (this.f_30274_.f_30129_ > 200) {
               Level level = this.f_30274_.f_19853_;
               level.m_5594_((Player)null, blockpos, SoundEvents.f_12486_, SoundSource.BLOCKS, 0.3F, 0.9F + level.f_46441_.nextFloat() * 0.2F);
               level.m_7731_(this.f_25602_.m_7494_(), Blocks.f_50578_.m_49966_().m_61124_(TurtleEggBlock.f_57754_, Integer.valueOf(this.f_30274_.f_19796_.nextInt(4) + 1)), 3);
               this.f_30274_.m_30234_(false);
               this.f_30274_.m_30236_(false);
               this.f_30274_.m_27601_(600);
            }

            if (this.f_30274_.m_30206_()) {
               ++this.f_30274_.f_30129_;
            }
         }

      }

      protected boolean m_6465_(LevelReader p_30280_, BlockPos p_30281_) {
         return !p_30280_.m_46859_(p_30281_.m_7494_()) ? false : TurtleEggBlock.m_57800_(p_30280_, p_30281_);
      }
   }

   static class TurtleMoveControl extends MoveControl {
      private final Turtle f_30284_;

      TurtleMoveControl(Turtle p_30286_) {
         super(p_30286_);
         this.f_30284_ = p_30286_;
      }

      private void m_30288_() {
         if (this.f_30284_.m_20069_()) {
            this.f_30284_.m_20256_(this.f_30284_.m_20184_().m_82520_(0.0D, 0.005D, 0.0D));
            if (!this.f_30284_.m_30208_().m_123306_(this.f_30284_.m_20182_(), 16.0D)) {
               this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 2.0F, 0.08F));
            }

            if (this.f_30284_.m_6162_()) {
               this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 3.0F, 0.06F));
            }
         } else if (this.f_30284_.f_19861_) {
            this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 2.0F, 0.06F));
         }

      }

      public void m_8126_() {
         this.m_30288_();
         if (this.f_24981_ == MoveControl.Operation.MOVE_TO && !this.f_30284_.m_21573_().m_26571_()) {
            double d0 = this.f_24975_ - this.f_30284_.m_20185_();
            double d1 = this.f_24976_ - this.f_30284_.m_20186_();
            double d2 = this.f_24977_ - this.f_30284_.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = d1 / d3;
            float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.f_30284_.m_146922_(this.m_24991_(this.f_30284_.m_146908_(), f, 90.0F));
            this.f_30284_.f_20883_ = this.f_30284_.m_146908_();
            float f1 = (float)(this.f_24978_ * this.f_30284_.m_21133_(Attributes.f_22279_));
            this.f_30284_.m_7910_(Mth.m_14179_(0.125F, this.f_30284_.m_6113_(), f1));
            this.f_30284_.m_20256_(this.f_30284_.m_20184_().m_82520_(0.0D, (double)this.f_30284_.m_6113_() * d1 * 0.1D, 0.0D));
         } else {
            this.f_30284_.m_7910_(0.0F);
         }
      }
   }

   static class TurtlePanicGoal extends PanicGoal {
      TurtlePanicGoal(Turtle p_30290_, double p_30291_) {
         super(p_30290_, p_30291_);
      }

      public boolean m_8036_() {
         if (this.f_25684_.m_142581_() == null && !this.f_25684_.m_6060_()) {
            return false;
         } else {
            BlockPos blockpos = this.m_25694_(this.f_25684_.f_19853_, this.f_25684_, 7, 4);
            if (blockpos != null) {
               this.f_25686_ = (double)blockpos.m_123341_();
               this.f_25687_ = (double)blockpos.m_123342_();
               this.f_25688_ = (double)blockpos.m_123343_();
               return true;
            } else {
               return this.m_25702_();
            }
         }
      }
   }

   static class TurtlePathNavigation extends WaterBoundPathNavigation {
      TurtlePathNavigation(Turtle p_30294_, Level p_30295_) {
         super(p_30294_, p_30295_);
      }

      protected boolean m_7632_() {
         return true;
      }

      protected PathFinder m_5532_(int p_30298_) {
         this.f_26508_ = new AmphibiousNodeEvaluator(true);
         this.f_26508_.m_77355_(false);
         this.f_26508_.m_77351_(false);
         return new PathFinder(this.f_26508_, p_30298_);
      }

      public boolean m_6342_(BlockPos p_30300_) {
         if (this.f_26494_ instanceof Turtle) {
            Turtle turtle = (Turtle)this.f_26494_;
            if (turtle.m_30212_()) {
               return this.f_26495_.m_8055_(p_30300_).m_60713_(Blocks.f_49990_);
            }
         }

         return !this.f_26495_.m_8055_(p_30300_.m_7495_()).m_60795_();
      }
   }

   static class TurtleRandomStrollGoal extends RandomStrollGoal {
      private final Turtle f_30301_;

      TurtleRandomStrollGoal(Turtle p_30303_, double p_30304_, int p_30305_) {
         super(p_30303_, p_30304_, p_30305_);
         this.f_30301_ = p_30303_;
      }

      public boolean m_8036_() {
         return !this.f_25725_.m_20069_() && !this.f_30301_.m_30211_() && !this.f_30301_.m_30205_() ? super.m_8036_() : false;
      }
   }

   static class TurtleTravelGoal extends Goal {
      private final Turtle f_30329_;
      private final double f_30330_;
      private boolean f_30331_;

      TurtleTravelGoal(Turtle p_30333_, double p_30334_) {
         this.f_30329_ = p_30333_;
         this.f_30330_ = p_30334_;
      }

      public boolean m_8036_() {
         return !this.f_30329_.m_30211_() && !this.f_30329_.m_30205_() && this.f_30329_.m_20069_();
      }

      public void m_8056_() {
         int i = 512;
         int j = 4;
         Random random = this.f_30329_.f_19796_;
         int k = random.nextInt(1025) - 512;
         int l = random.nextInt(9) - 4;
         int i1 = random.nextInt(1025) - 512;
         if ((double)l + this.f_30329_.m_20186_() > (double)(this.f_30329_.f_19853_.m_5736_() - 1)) {
            l = 0;
         }

         BlockPos blockpos = new BlockPos((double)k + this.f_30329_.m_20185_(), (double)l + this.f_30329_.m_20186_(), (double)i1 + this.f_30329_.m_20189_());
         this.f_30329_.m_30223_(blockpos);
         this.f_30329_.m_30240_(true);
         this.f_30331_ = false;
      }

      public void m_8037_() {
         if (this.f_30329_.m_21573_().m_26571_()) {
            Vec3 vec3 = Vec3.m_82539_(this.f_30329_.m_30209_());
            Vec3 vec31 = DefaultRandomPos.m_148412_(this.f_30329_, 16, 3, vec3, (double)((float)Math.PI / 10F));
            if (vec31 == null) {
               vec31 = DefaultRandomPos.m_148412_(this.f_30329_, 8, 7, vec3, (double)((float)Math.PI / 2F));
            }

            if (vec31 != null) {
               int i = Mth.m_14107_(vec31.f_82479_);
               int j = Mth.m_14107_(vec31.f_82481_);
               int k = 34;
               if (!this.f_30329_.f_19853_.m_151572_(i - 34, j - 34, i + 34, j + 34)) {
                  vec31 = null;
               }
            }

            if (vec31 == null) {
               this.f_30331_ = true;
               return;
            }

            this.f_30329_.m_21573_().m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, this.f_30330_);
         }

      }

      public boolean m_8045_() {
         return !this.f_30329_.m_21573_().m_26571_() && !this.f_30331_ && !this.f_30329_.m_30211_() && !this.f_30329_.m_27593_() && !this.f_30329_.m_30205_();
      }

      public void m_8041_() {
         this.f_30329_.m_30240_(false);
         super.m_8041_();
      }
   }
}