package net.minecraft.world.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Zoglin extends Monster implements Enemy, HoglinBase {
   private static final EntityDataAccessor<Boolean> f_34201_ = SynchedEntityData.m_135353_(Zoglin.class, EntityDataSerializers.f_135035_);
   private static final int f_149879_ = 40;
   private static final int f_149870_ = 1;
   private static final float f_149871_ = 0.6F;
   private static final int f_149872_ = 6;
   private static final float f_149873_ = 0.5F;
   private static final int f_149874_ = 40;
   private static final int f_149875_ = 15;
   private static final int f_149876_ = 200;
   private static final float f_149877_ = 0.3F;
   private static final float f_149878_ = 0.4F;
   private int f_34199_;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Zoglin>>> f_34198_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_);
   protected static final ImmutableList<? extends MemoryModuleType<?>> f_34200_ = ImmutableList.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_);

   public Zoglin(EntityType<? extends Zoglin> p_34204_, Level p_34205_) {
      super(p_34204_, p_34205_);
      this.f_21364_ = 5;
   }

   protected Brain.Provider<Zoglin> m_5490_() {
      return Brain.m_21923_(f_34200_, f_34198_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_34221_) {
      Brain<Zoglin> brain = this.m_5490_().m_22073_(p_34221_);
      m_34216_(brain);
      m_34228_(brain);
      m_34236_(brain);
      brain.m_21930_(ImmutableSet.of(Activity.f_37978_));
      brain.m_21944_(Activity.f_37979_);
      brain.m_21962_();
      return brain;
   }

   private static void m_34216_(Brain<Zoglin> p_34217_) {
      p_34217_.m_21891_(Activity.f_37978_, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
   }

   private static void m_34228_(Brain<Zoglin> p_34229_) {
      p_34229_.m_21891_(Activity.f_37979_, 10, ImmutableList.of(new StartAttacking<Zoglin>(Zoglin::m_34251_), new RunSometimes<Zoglin>(new SetEntityLookTarget(8.0F), UniformInt.m_146622_(30, 60)), new RunOne<Zoglin>(ImmutableList.of(Pair.of(new RandomStroll(0.4F), 2), Pair.of(new SetWalkTargetFromLookTarget(0.4F, 3), 2), Pair.of(new DoNothing(30, 60), 1)))));
   }

   private static void m_34236_(Brain<Zoglin> p_34237_) {
      p_34237_.m_21895_(Activity.f_37988_, 10, ImmutableList.<net.minecraft.world.entity.ai.behavior.Behavior<net.minecraft.world.entity.Mob>>of(new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F), (net.minecraft.world.entity.ai.behavior.Behavior<net.minecraft.world.entity.Mob>)(net.minecraft.world.entity.ai.behavior.Behavior)new RunIf<Zoglin>(Zoglin::m_34247_, new MeleeAttack(40)), (net.minecraft.world.entity.ai.behavior.Behavior<net.minecraft.world.entity.Mob>)(net.minecraft.world.entity.ai.behavior.Behavior)new RunIf<Zoglin>(Zoglin::m_6162_, new MeleeAttack(15)), new StopAttackingIfTargetInvalid()), MemoryModuleType.f_26372_);
   }

   private Optional<? extends LivingEntity> m_34251_() {
      return this.m_6274_().m_21952_(MemoryModuleType.f_148205_).orElse(ImmutableList.of()).stream().filter(this::m_34252_).findFirst();
   }

   private boolean m_34252_(LivingEntity p_34253_) {
      EntityType<?> entitytype = p_34253_.m_6095_();
      return entitytype != EntityType.f_20500_ && entitytype != EntityType.f_20558_ && Sensor.m_148312_(this, p_34253_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_34201_, false);
   }

   public void m_7350_(EntityDataAccessor<?> p_34225_) {
      super.m_7350_(p_34225_);
      if (f_34201_.equals(p_34225_)) {
         this.m_6210_();
      }

   }

   public static AttributeSupplier.Builder m_34257_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22278_, (double)0.6F).m_22268_(Attributes.f_22282_, 1.0D).m_22268_(Attributes.f_22281_, 6.0D);
   }

   public boolean m_34247_() {
      return !this.m_6162_();
   }

   public boolean m_7327_(Entity p_34207_) {
      if (!(p_34207_ instanceof LivingEntity)) {
         return false;
      } else {
         this.f_34199_ = 10;
         this.f_19853_.m_7605_(this, (byte)4);
         this.m_5496_(SoundEvents.f_12594_, 1.0F, this.m_6100_());
         return HoglinBase.m_34642_(this, (LivingEntity)p_34207_);
      }
   }

   public boolean m_6573_(Player p_34219_) {
      return !this.m_21523_();
   }

   protected void m_6731_(LivingEntity p_34246_) {
      if (!this.m_6162_()) {
         HoglinBase.m_34645_(this, p_34246_);
      }

   }

   public double m_6048_() {
      return (double)this.m_20206_() - (this.m_6162_() ? 0.2D : 0.15D);
   }

   public boolean m_6469_(DamageSource p_34214_, float p_34215_) {
      boolean flag = super.m_6469_(p_34214_, p_34215_);
      if (this.f_19853_.f_46443_) {
         return false;
      } else if (flag && p_34214_.m_7639_() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)p_34214_.m_7639_();
         if (this.m_6779_(livingentity) && !BehaviorUtils.m_22598_(this, livingentity, 4.0D)) {
            this.m_34254_(livingentity);
         }

         return flag;
      } else {
         return flag;
      }
   }

   private void m_34254_(LivingEntity p_34255_) {
      this.f_20939_.m_21936_(MemoryModuleType.f_26326_);
      this.f_20939_.m_21882_(MemoryModuleType.f_26372_, p_34255_, 200L);
   }

   public Brain<Zoglin> m_6274_() {
      return (Brain<Zoglin>)super.m_6274_();
   }

   protected void m_34248_() {
      Activity activity = this.f_20939_.m_21968_().orElse((Activity)null);
      this.f_20939_.m_21926_(ImmutableList.of(Activity.f_37988_, Activity.f_37979_));
      Activity activity1 = this.f_20939_.m_21968_().orElse((Activity)null);
      if (activity1 == Activity.f_37988_ && activity != Activity.f_37988_) {
         this.m_34250_();
      }

      this.m_21561_(this.f_20939_.m_21874_(MemoryModuleType.f_26372_));
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("zoglinBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      this.m_34248_();
   }

   public void m_6863_(boolean p_34227_) {
      this.m_20088_().m_135381_(f_34201_, p_34227_);
      if (!this.f_19853_.f_46443_ && p_34227_) {
         this.m_21051_(Attributes.f_22281_).m_22100_(0.5D);
      }

   }

   public boolean m_6162_() {
      return this.m_20088_().m_135370_(f_34201_);
   }

   public void m_8107_() {
      if (this.f_34199_ > 0) {
         --this.f_34199_;
      }

      super.m_8107_();
   }

   public void m_7822_(byte p_34212_) {
      if (p_34212_ == 4) {
         this.f_34199_ = 10;
         this.m_5496_(SoundEvents.f_12594_, 1.0F, this.m_6100_());
      } else {
         super.m_7822_(p_34212_);
      }

   }

   public int m_7575_() {
      return this.f_34199_;
   }

   protected SoundEvent m_7515_() {
      if (this.f_19853_.f_46443_) {
         return null;
      } else {
         return this.f_20939_.m_21874_(MemoryModuleType.f_26372_) ? SoundEvents.f_12593_ : SoundEvents.f_12592_;
      }
   }

   protected SoundEvent m_7975_(DamageSource p_34244_) {
      return SoundEvents.f_12596_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12595_;
   }

   protected void m_7355_(BlockPos p_34231_, BlockState p_34232_) {
      this.m_5496_(SoundEvents.f_12597_, 0.15F, 1.0F);
   }

   protected void m_34250_() {
      this.m_5496_(SoundEvents.f_12593_, 1.0F, this.m_6100_());
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133695_(this);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public void m_7380_(CompoundTag p_34234_) {
      super.m_7380_(p_34234_);
      if (this.m_6162_()) {
         p_34234_.m_128379_("IsBaby", true);
      }

   }

   public void m_7378_(CompoundTag p_34223_) {
      super.m_7378_(p_34223_);
      if (p_34223_.m_128471_("IsBaby")) {
         this.m_6863_(true);
      }

   }
}