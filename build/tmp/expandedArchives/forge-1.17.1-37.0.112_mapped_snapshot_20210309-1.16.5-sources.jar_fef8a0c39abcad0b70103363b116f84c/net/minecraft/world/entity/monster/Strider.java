package net.minecraft.world.entity.monster;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class Strider extends Animal implements ItemSteerable, Saddleable {
   private static final float f_149854_ = 0.23F;
   private static final float f_149855_ = 0.66F;
   private static final float f_149856_ = 0.55F;
   private static final Ingredient f_33852_ = Ingredient.m_43929_(Items.f_41955_);
   private static final Ingredient f_33853_ = Ingredient.m_43929_(Items.f_41955_, Items.f_42685_);
   private static final EntityDataAccessor<Integer> f_33854_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_33855_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_33856_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135035_);
   private final ItemBasedSteering f_33857_ = new ItemBasedSteering(this.f_19804_, f_33854_, f_33856_);
   private TemptGoal f_33858_;
   private PanicGoal f_33859_;

   public Strider(EntityType<? extends Strider> p_33862_, Level p_33863_) {
      super(p_33862_, p_33863_);
      this.f_19850_ = true;
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.LAVA, 0.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
   }

   public static boolean m_33921_(EntityType<Strider> p_33922_, LevelAccessor p_33923_, MobSpawnType p_33924_, BlockPos p_33925_, Random p_33926_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_33925_.m_122032_();

      do {
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      } while(p_33923_.m_6425_(blockpos$mutableblockpos).m_76153_(FluidTags.f_13132_));

      return p_33923_.m_8055_(blockpos$mutableblockpos).m_60795_();
   }

   public void m_7350_(EntityDataAccessor<?> p_33900_) {
      if (f_33854_.equals(p_33900_) && this.f_19853_.f_46443_) {
         this.f_33857_.m_20844_();
      }

      super.m_7350_(p_33900_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33854_, 0);
      this.f_19804_.m_135372_(f_33855_, false);
      this.f_19804_.m_135372_(f_33856_, false);
   }

   public void m_7380_(CompoundTag p_33918_) {
      super.m_7380_(p_33918_);
      this.f_33857_.m_20847_(p_33918_);
   }

   public void m_7378_(CompoundTag p_33898_) {
      super.m_7378_(p_33898_);
      this.f_33857_.m_20852_(p_33898_);
   }

   public boolean m_6254_() {
      return this.f_33857_.m_20851_();
   }

   public boolean m_6741_() {
      return this.m_6084_() && !this.m_6162_();
   }

   public void m_5853_(@Nullable SoundSource p_33878_) {
      this.f_33857_.m_20849_(true);
      if (p_33878_ != null) {
         this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12466_, p_33878_, 0.5F, 1.0F);
      }

   }

   protected void m_8099_() {
      this.f_33859_ = new PanicGoal(this, 1.65D);
      this.f_21345_.m_25352_(1, this.f_33859_);
      this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0D));
      this.f_33858_ = new TemptGoal(this, 1.4D, f_33853_, false);
      this.f_21345_.m_25352_(3, this.f_33858_);
      this.f_21345_.m_25352_(4, new Strider.StriderGoToLavaGoal(this, 1.5D));
      this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.1D));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0D, 60));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Strider.class, 8.0F));
   }

   public void m_33951_(boolean p_33952_) {
      this.f_19804_.m_135381_(f_33855_, p_33952_);
   }

   public boolean m_33935_() {
      return this.m_20202_() instanceof Strider ? ((Strider)this.m_20202_()).m_33935_() : this.f_19804_.m_135370_(f_33855_);
   }

   public boolean m_7479_(Fluid p_33893_) {
      return p_33893_.m_76108_(FluidTags.f_13132_);
   }

   public double m_6048_() {
      float f = Math.min(0.25F, this.f_20924_);
      float f1 = this.f_20925_;
      return (double)this.m_20206_() - 0.19D + (double)(0.12F * Mth.m_14089_(f1 * 1.5F) * 2.0F * f);
   }

   public boolean m_5807_() {
      Entity entity = this.m_6688_();
      if (!(entity instanceof Player)) {
         return false;
      } else {
         Player player = (Player)entity;
         return player.m_21205_().m_150930_(Items.f_42685_) || player.m_21206_().m_150930_(Items.f_42685_);
      }
   }

   public boolean m_6914_(LevelReader p_33880_) {
      return p_33880_.m_45784_(this);
   }

   @Nullable
   public Entity m_6688_() {
      return this.m_146895_();
   }

   public Vec3 m_7688_(LivingEntity p_33908_) {
      Vec3[] avec3 = new Vec3[]{m_19903_((double)this.m_20205_(), (double)p_33908_.m_20205_(), p_33908_.m_146908_()), m_19903_((double)this.m_20205_(), (double)p_33908_.m_20205_(), p_33908_.m_146908_() - 22.5F), m_19903_((double)this.m_20205_(), (double)p_33908_.m_20205_(), p_33908_.m_146908_() + 22.5F), m_19903_((double)this.m_20205_(), (double)p_33908_.m_20205_(), p_33908_.m_146908_() - 45.0F), m_19903_((double)this.m_20205_(), (double)p_33908_.m_20205_(), p_33908_.m_146908_() + 45.0F)};
      Set<BlockPos> set = Sets.newLinkedHashSet();
      double d0 = this.m_142469_().f_82292_;
      double d1 = this.m_142469_().f_82289_ - 0.5D;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Vec3 vec3 : avec3) {
         blockpos$mutableblockpos.m_122169_(this.m_20185_() + vec3.f_82479_, d0, this.m_20189_() + vec3.f_82481_);

         for(double d2 = d0; d2 > d1; --d2) {
            set.add(blockpos$mutableblockpos.m_7949_());
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         }
      }

      for(BlockPos blockpos : set) {
         if (!this.f_19853_.m_6425_(blockpos).m_76153_(FluidTags.f_13132_)) {
            double d3 = this.f_19853_.m_45573_(blockpos);
            if (DismountHelper.m_38439_(d3)) {
               Vec3 vec31 = Vec3.m_82514_(blockpos, d3);

               for(Pose pose : p_33908_.m_7431_()) {
                  AABB aabb = p_33908_.m_21270_(pose);
                  if (DismountHelper.m_38456_(this.f_19853_, p_33908_, aabb.m_82383_(vec31))) {
                     p_33908_.m_20124_(pose);
                     return vec31;
                  }
               }
            }
         }
      }

      return new Vec3(this.m_20185_(), this.m_142469_().f_82292_, this.m_20189_());
   }

   public void m_7023_(Vec3 p_33943_) {
      this.m_7910_(this.m_33936_());
      this.m_20854_(this, this.f_33857_, p_33943_);
   }

   public float m_33936_() {
      return (float)this.m_21133_(Attributes.f_22279_) * (this.m_33935_() ? 0.66F : 1.0F);
   }

   public float m_6748_() {
      return (float)this.m_21133_(Attributes.f_22279_) * (this.m_33935_() ? 0.23F : 0.55F);
   }

   public void m_7760_(Vec3 p_33902_) {
      super.m_7023_(p_33902_);
   }

   protected float m_6059_() {
      return this.f_19788_ + 0.6F;
   }

   protected void m_7355_(BlockPos p_33915_, BlockState p_33916_) {
      this.m_5496_(this.m_20077_() ? SoundEvents.f_12464_ : SoundEvents.f_12463_, 1.0F, 1.0F);
   }

   public boolean m_6746_() {
      return this.f_33857_.m_20845_(this.m_21187_());
   }

   protected void m_7840_(double p_33870_, boolean p_33871_, BlockState p_33872_, BlockPos p_33873_) {
      this.m_20101_();
      if (this.m_20077_()) {
         this.f_19789_ = 0.0F;
      } else {
         super.m_7840_(p_33870_, p_33871_, p_33872_, p_33873_);
      }
   }

   public void m_8119_() {
      if (this.m_33939_() && this.f_19796_.nextInt(140) == 0) {
         this.m_5496_(SoundEvents.f_12459_, 1.0F, this.m_6100_());
      } else if (this.m_33938_() && this.f_19796_.nextInt(60) == 0) {
         this.m_5496_(SoundEvents.f_12460_, 1.0F, this.m_6100_());
      }

      BlockState blockstate = this.f_19853_.m_8055_(this.m_142538_());
      BlockState blockstate1 = this.m_20075_();
      boolean flag = blockstate.m_60620_(BlockTags.f_13086_) || blockstate1.m_60620_(BlockTags.f_13086_) || this.m_20120_(FluidTags.f_13132_) > 0.0D;
      this.m_33951_(!flag);
      super.m_8119_();
      this.m_33940_();
      this.m_20101_();
   }

   private boolean m_33938_() {
      return this.f_33859_ != null && this.f_33859_.m_25703_();
   }

   private boolean m_33939_() {
      return this.f_33858_ != null && this.f_33858_.m_25955_();
   }

   protected boolean m_8091_() {
      return true;
   }

   private void m_33940_() {
      if (this.m_20077_()) {
         CollisionContext collisioncontext = CollisionContext.m_82750_(this);
         if (collisioncontext.m_6513_(LiquidBlock.f_54690_, this.m_142538_(), true) && !this.f_19853_.m_6425_(this.m_142538_().m_7494_()).m_76153_(FluidTags.f_13132_)) {
            this.f_19861_ = true;
         } else {
            this.m_20256_(this.m_20184_().m_82490_(0.5D).m_82520_(0.0D, 0.05D, 0.0D));
         }
      }

   }

   public static AttributeSupplier.Builder m_33937_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.175F).m_22268_(Attributes.f_22277_, 16.0D);
   }

   protected SoundEvent m_7515_() {
      return !this.m_33938_() && !this.m_33939_() ? SoundEvents.f_12458_ : null;
   }

   protected SoundEvent m_7975_(DamageSource p_33934_) {
      return SoundEvents.f_12462_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12461_;
   }

   protected boolean m_7310_(Entity p_33950_) {
      return !this.m_20160_() && !this.m_19941_(FluidTags.f_13132_);
   }

   public boolean m_6126_() {
      return true;
   }

   public boolean m_6060_() {
      return false;
   }

   protected PathNavigation m_6037_(Level p_33913_) {
      return new Strider.StriderPathNavigation(this, p_33913_);
   }

   public float m_5610_(BlockPos p_33895_, LevelReader p_33896_) {
      if (p_33896_.m_8055_(p_33895_).m_60819_().m_76153_(FluidTags.f_13132_)) {
         return 10.0F;
      } else {
         return this.m_20077_() ? Float.NEGATIVE_INFINITY : 0.0F;
      }
   }

   public Strider m_142606_(ServerLevel p_149861_, AgeableMob p_149862_) {
      return EntityType.f_20482_.m_20615_(p_149861_);
   }

   public boolean m_6898_(ItemStack p_33946_) {
      return f_33852_.test(p_33946_);
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.m_6254_()) {
         this.m_19998_(Items.f_42450_);
      }

   }

   public InteractionResult m_6071_(Player p_33910_, InteractionHand p_33911_) {
      boolean flag = this.m_6898_(p_33910_.m_21120_(p_33911_));
      if (!flag && this.m_6254_() && !this.m_20160_() && !p_33910_.m_36341_()) {
         if (!this.f_19853_.f_46443_) {
            p_33910_.m_20329_(this);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         InteractionResult interactionresult = super.m_6071_(p_33910_, p_33911_);
         if (!interactionresult.m_19077_()) {
            ItemStack itemstack = p_33910_.m_21120_(p_33911_);
            return itemstack.m_150930_(Items.f_42450_) ? itemstack.m_41647_(p_33910_, this, p_33911_) : InteractionResult.PASS;
         } else {
            if (flag && !this.m_20067_()) {
               this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12465_, this.m_5720_(), 1.0F, 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F);
            }

            return interactionresult;
         }
      }
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.6F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_33887_, DifficultyInstance p_33888_, MobSpawnType p_33889_, @Nullable SpawnGroupData p_33890_, @Nullable CompoundTag p_33891_) {
      if (this.m_6162_()) {
         return super.m_6518_(p_33887_, p_33888_, p_33889_, p_33890_, p_33891_);
      } else {
         Object object;
         if (this.f_19796_.nextInt(30) == 0) {
            Mob mob = EntityType.f_20531_.m_20615_(p_33887_.m_6018_());
            object = this.m_33881_(p_33887_, p_33888_, mob, new Zombie.ZombieGroupData(Zombie.m_34302_(this.f_19796_), false));
            mob.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42685_));
            this.m_5853_((SoundSource)null);
         } else if (this.f_19796_.nextInt(10) == 0) {
            AgeableMob ageablemob = EntityType.f_20482_.m_20615_(p_33887_.m_6018_());
            ageablemob.m_146762_(-24000);
            object = this.m_33881_(p_33887_, p_33888_, ageablemob, (SpawnGroupData)null);
         } else {
            object = new AgeableMob.AgeableMobGroupData(0.5F);
         }

         return super.m_6518_(p_33887_, p_33888_, p_33889_, (SpawnGroupData)object, p_33891_);
      }
   }

   private SpawnGroupData m_33881_(ServerLevelAccessor p_33882_, DifficultyInstance p_33883_, Mob p_33884_, @Nullable SpawnGroupData p_33885_) {
      p_33884_.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
      p_33884_.m_6518_(p_33882_, p_33883_, MobSpawnType.JOCKEY, p_33885_, (CompoundTag)null);
      p_33884_.m_7998_(this, true);
      return new AgeableMob.AgeableMobGroupData(0.0F);
   }

   static class StriderGoToLavaGoal extends MoveToBlockGoal {
      private final Strider f_33953_;

      StriderGoToLavaGoal(Strider p_33955_, double p_33956_) {
         super(p_33955_, p_33956_, 8, 2);
         this.f_33953_ = p_33955_;
      }

      public BlockPos m_6669_() {
         return this.f_25602_;
      }

      public boolean m_8045_() {
         return !this.f_33953_.m_20077_() && this.m_6465_(this.f_33953_.f_19853_, this.f_25602_);
      }

      public boolean m_8036_() {
         return !this.f_33953_.m_20077_() && super.m_8036_();
      }

      public boolean m_8064_() {
         return this.f_25601_ % 20 == 0;
      }

      protected boolean m_6465_(LevelReader p_33963_, BlockPos p_33964_) {
         return p_33963_.m_8055_(p_33964_).m_60713_(Blocks.f_49991_) && p_33963_.m_8055_(p_33964_.m_7494_()).m_60647_(p_33963_, p_33964_, PathComputationType.LAND);
      }
   }

   static class StriderPathNavigation extends GroundPathNavigation {
      StriderPathNavigation(Strider p_33969_, Level p_33970_) {
         super(p_33969_, p_33970_);
      }

      protected PathFinder m_5532_(int p_33972_) {
         this.f_26508_ = new WalkNodeEvaluator();
         return new PathFinder(this.f_26508_, p_33972_);
      }

      protected boolean m_7367_(BlockPathTypes p_33974_) {
         return p_33974_ != BlockPathTypes.LAVA && p_33974_ != BlockPathTypes.DAMAGE_FIRE && p_33974_ != BlockPathTypes.DANGER_FIRE ? super.m_7367_(p_33974_) : true;
      }

      public boolean m_6342_(BlockPos p_33976_) {
         return this.f_26495_.m_8055_(p_33976_).m_60713_(Blocks.f_49991_) || super.m_6342_(p_33976_);
      }
   }
}