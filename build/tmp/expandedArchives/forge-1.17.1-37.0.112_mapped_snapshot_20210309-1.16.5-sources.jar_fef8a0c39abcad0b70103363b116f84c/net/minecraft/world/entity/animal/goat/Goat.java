package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class Goat extends Animal {
   public static final EntityDimensions f_149342_ = EntityDimensions.m_20395_(0.9F, 1.3F).m_20388_(0.7F);
   private static final int f_182382_ = 2;
   private static final int f_182383_ = 1;
   protected static final ImmutableList<SensorType<? extends Sensor<? super Goat>>> f_149343_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26822_, SensorType.f_26814_, SensorType.f_148317_);
   protected static final ImmutableList<MemoryModuleType<?>> f_149344_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_148205_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26355_, MemoryModuleType.f_26375_, MemoryModuleType.f_148199_, MemoryModuleType.f_148200_, MemoryModuleType.f_148196_, MemoryModuleType.f_26331_, MemoryModuleType.f_148197_, MemoryModuleType.f_148198_, MemoryModuleType.f_148202_, MemoryModuleType.f_148203_);
   public static final int f_149345_ = 10;
   public static final double f_149346_ = 0.02D;
   private static final EntityDataAccessor<Boolean> f_149347_ = SynchedEntityData.m_135353_(Goat.class, EntityDataSerializers.f_135035_);
   private boolean f_149348_;
   private int f_149349_;

   public Goat(EntityType<? extends Goat> p_149352_, Level p_149353_) {
      super(p_149352_, p_149353_);
      this.m_21573_().m_7008_(true);
   }

   protected Brain.Provider<Goat> m_5490_() {
      return Brain.m_21923_(f_149344_, f_149343_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_149371_) {
      return GoatAi.m_149447_(this.m_5490_().m_22073_(p_149371_));
   }

   public static AttributeSupplier.Builder m_149401_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, (double)0.2F).m_22268_(Attributes.f_22281_, 2.0D);
   }

   protected void m_142669_() {
      if (this.m_6162_()) {
         this.m_21051_(Attributes.f_22281_).m_22100_(1.0D);
      } else {
         this.m_21051_(Attributes.f_22281_).m_22100_(2.0D);
      }

   }

   protected int m_5639_(float p_149389_, float p_149390_) {
      return super.m_5639_(p_149389_, p_149390_) - 10;
   }

   protected SoundEvent m_7515_() {
      return this.m_149397_() ? SoundEvents.f_144171_ : SoundEvents.f_144163_;
   }

   protected SoundEvent m_7975_(DamageSource p_149387_) {
      return this.m_149397_() ? SoundEvents.f_144146_ : SoundEvents.f_144166_;
   }

   protected SoundEvent m_5592_() {
      return this.m_149397_() ? SoundEvents.f_144172_ : SoundEvents.f_144164_;
   }

   protected void m_7355_(BlockPos p_149382_, BlockState p_149383_) {
      this.m_5496_(SoundEvents.f_144151_, 0.15F, 1.0F);
   }

   protected SoundEvent m_149403_() {
      return this.m_149397_() ? SoundEvents.f_144148_ : SoundEvents.f_144168_;
   }

   public Goat m_142606_(ServerLevel p_149376_, AgeableMob p_149377_) {
      Goat goat = EntityType.f_147035_.m_20615_(p_149376_);
      if (goat != null) {
         GoatAi.m_149449_(goat);
         boolean flag = p_149377_ instanceof Goat && ((Goat)p_149377_).m_149397_();
         goat.m_149405_(flag || p_149376_.m_5822_().nextDouble() < 0.02D);
      }

      return goat;
   }

   public Brain<Goat> m_6274_() {
      return (Brain<Goat>)super.m_6274_();
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("goatBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("goatActivityUpdate");
      GoatAi.m_149455_(this);
      this.f_19853_.m_46473_().m_7238_();
      super.m_8024_();
   }

   public int m_8085_() {
      return 15;
   }

   public void m_5616_(float p_149400_) {
      int i = this.m_8085_();
      float f = Mth.m_14118_(this.f_20883_, p_149400_);
      float f1 = Mth.m_14036_(f, (float)(-i), (float)i);
      super.m_5616_(this.f_20883_ + f1);
   }

   public SoundEvent m_7866_(ItemStack p_149394_) {
      return this.m_149397_() ? SoundEvents.f_144173_ : SoundEvents.f_144165_;
   }

   public InteractionResult m_6071_(Player p_149379_, InteractionHand p_149380_) {
      ItemStack itemstack = p_149379_.m_21120_(p_149380_);
      if (itemstack.m_150930_(Items.f_42446_) && !this.m_6162_()) {
         p_149379_.m_5496_(this.m_149403_(), 1.0F, 1.0F);
         ItemStack itemstack1 = ItemUtils.m_41813_(itemstack, p_149379_, Items.f_42455_.m_7968_());
         p_149379_.m_21008_(p_149380_, itemstack1);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         InteractionResult interactionresult = super.m_6071_(p_149379_, p_149380_);
         if (interactionresult.m_19077_() && this.m_6898_(itemstack)) {
            this.f_19853_.m_6269_((Player)null, this, this.m_7866_(itemstack), SoundSource.NEUTRAL, 1.0F, Mth.m_144924_(this.f_19853_.f_46441_, 0.8F, 1.2F));
         }

         return interactionresult;
      }
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_149365_, DifficultyInstance p_149366_, MobSpawnType p_149367_, @Nullable SpawnGroupData p_149368_, @Nullable CompoundTag p_149369_) {
      GoatAi.m_149449_(this);
      this.m_149405_(p_149365_.m_5822_().nextDouble() < 0.02D);
      return super.m_6518_(p_149365_, p_149366_, p_149367_, p_149368_, p_149369_);
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133695_(this);
   }

   public EntityDimensions m_6972_(Pose p_149361_) {
      return p_149361_ == Pose.LONG_JUMPING ? f_149342_.m_20388_(this.m_6134_()) : super.m_6972_(p_149361_);
   }

   public void m_7380_(CompoundTag p_149385_) {
      super.m_7380_(p_149385_);
      p_149385_.m_128379_("IsScreamingGoat", this.m_149397_());
   }

   public void m_7378_(CompoundTag p_149373_) {
      super.m_7378_(p_149373_);
      this.m_149405_(p_149373_.m_128471_("IsScreamingGoat"));
   }

   public void m_7822_(byte p_149356_) {
      if (p_149356_ == 58) {
         this.f_149348_ = true;
      } else if (p_149356_ == 59) {
         this.f_149348_ = false;
      } else {
         super.m_7822_(p_149356_);
      }

   }

   public void m_8107_() {
      if (this.f_149348_) {
         ++this.f_149349_;
      } else {
         this.f_149349_ -= 2;
      }

      this.f_149349_ = Mth.m_14045_(this.f_149349_, 0, 20);
      super.m_8107_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_149347_, false);
   }

   public boolean m_149397_() {
      return this.f_19804_.m_135370_(f_149347_);
   }

   public void m_149405_(boolean p_149406_) {
      this.f_19804_.m_135381_(f_149347_, p_149406_);
   }

   public float m_149398_() {
      return (float)this.f_149349_ / 20.0F * 30.0F * ((float)Math.PI / 180F);
   }

   protected PathNavigation m_6037_(Level p_149363_) {
      return new Goat.GoatPathNavigation(this, p_149363_);
   }

   static class GoatNodeEvaluator extends WalkNodeEvaluator {
      private final BlockPos.MutableBlockPos f_149408_ = new BlockPos.MutableBlockPos();

      public BlockPathTypes m_8086_(BlockGetter p_149411_, int p_149412_, int p_149413_, int p_149414_) {
         this.f_149408_.m_122178_(p_149412_, p_149413_ - 1, p_149414_);
         BlockPathTypes blockpathtypes = m_77643_(p_149411_, this.f_149408_);
         return blockpathtypes == BlockPathTypes.POWDER_SNOW ? BlockPathTypes.BLOCKED : m_77604_(p_149411_, this.f_149408_.m_122173_(Direction.UP));
      }
   }

   static class GoatPathNavigation extends GroundPathNavigation {
      GoatPathNavigation(Goat p_149416_, Level p_149417_) {
         super(p_149416_, p_149417_);
      }

      protected PathFinder m_5532_(int p_149419_) {
         this.f_26508_ = new Goat.GoatNodeEvaluator();
         return new PathFinder(this.f_26508_, p_149419_);
      }
   }
}