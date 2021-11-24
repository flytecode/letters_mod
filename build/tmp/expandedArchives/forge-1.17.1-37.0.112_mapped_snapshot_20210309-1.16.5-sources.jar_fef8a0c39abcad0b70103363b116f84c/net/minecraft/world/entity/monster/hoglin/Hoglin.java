package net.minecraft.world.entity.monster.hoglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Hoglin extends Animal implements Enemy, HoglinBase {
   private static final EntityDataAccessor<Boolean> f_34482_ = SynchedEntityData.m_135353_(Hoglin.class, EntityDataSerializers.f_135035_);
   private static final float f_149891_ = 0.2F;
   private static final int f_149892_ = 40;
   private static final float f_149893_ = 0.3F;
   private static final int f_149894_ = 1;
   private static final float f_149895_ = 0.6F;
   private static final int f_149896_ = 6;
   private static final float f_149897_ = 0.5F;
   private static final int f_149898_ = 300;
   private int f_34483_;
   private int f_34484_;
   private boolean f_34485_;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Hoglin>>> f_34480_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26822_, SensorType.f_26821_);
   protected static final ImmutableList<? extends MemoryModuleType<?>> f_34481_ = ImmutableList.of(MemoryModuleType.f_26375_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_26350_, MemoryModuleType.f_26383_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_, MemoryModuleType.f_26348_, MemoryModuleType.f_26331_, MemoryModuleType.f_26356_, MemoryModuleType.f_26357_);

   public Hoglin(EntityType<? extends Hoglin> p_34488_, Level p_34489_) {
      super(p_34488_, p_34489_);
      this.f_21364_ = 5;
   }

   public boolean m_6573_(Player p_34506_) {
      return !this.m_21523_();
   }

   public static AttributeSupplier.Builder m_34551_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22278_, (double)0.6F).m_22268_(Attributes.f_22282_, 1.0D).m_22268_(Attributes.f_22281_, 6.0D);
   }

   public boolean m_7327_(Entity p_34491_) {
      if (!(p_34491_ instanceof LivingEntity)) {
         return false;
      } else {
         this.f_34483_ = 10;
         this.f_19853_.m_7605_(this, (byte)4);
         this.m_5496_(SoundEvents.f_11958_, 1.0F, this.m_6100_());
         HoglinAi.m_34579_(this, (LivingEntity)p_34491_);
         return HoglinBase.m_34642_(this, (LivingEntity)p_34491_);
      }
   }

   protected void m_6731_(LivingEntity p_34550_) {
      if (this.m_34552_()) {
         HoglinBase.m_34645_(this, p_34550_);
      }

   }

   public boolean m_6469_(DamageSource p_34503_, float p_34504_) {
      boolean flag = super.m_6469_(p_34503_, p_34504_);
      if (this.f_19853_.f_46443_) {
         return false;
      } else {
         if (flag && p_34503_.m_7639_() instanceof LivingEntity) {
            HoglinAi.m_34595_(this, (LivingEntity)p_34503_.m_7639_());
         }

         return flag;
      }
   }

   protected Brain.Provider<Hoglin> m_5490_() {
      return Brain.m_21923_(f_34481_, f_34480_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_34514_) {
      return HoglinAi.m_34575_(this.m_5490_().m_22073_(p_34514_));
   }

   public Brain<Hoglin> m_6274_() {
      return (Brain<Hoglin>)super.m_6274_();
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("hoglinBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      HoglinAi.m_34577_(this);
      if (this.m_34554_()) {
         ++this.f_34484_;
         if (this.f_34484_ > 300 && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.f_20500_, (timer) -> this.f_34484_ = timer)) {
            this.m_34500_(SoundEvents.f_11959_);
            this.m_34531_((ServerLevel)this.f_19853_);
         }
      } else {
         this.f_34484_ = 0;
      }

   }

   public void m_8107_() {
      if (this.f_34483_ > 0) {
         --this.f_34483_;
      }

      super.m_8107_();
   }

   protected void m_142669_() {
      if (this.m_6162_()) {
         this.f_21364_ = 3;
         this.m_21051_(Attributes.f_22281_).m_22100_(0.5D);
      } else {
         this.f_21364_ = 5;
         this.m_21051_(Attributes.f_22281_).m_22100_(6.0D);
      }

   }

   public static boolean m_34533_(EntityType<Hoglin> p_34534_, LevelAccessor p_34535_, MobSpawnType p_34536_, BlockPos p_34537_, Random p_34538_) {
      return !p_34535_.m_8055_(p_34537_.m_7495_()).m_60713_(Blocks.f_50451_);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_34508_, DifficultyInstance p_34509_, MobSpawnType p_34510_, @Nullable SpawnGroupData p_34511_, @Nullable CompoundTag p_34512_) {
      if (p_34508_.m_5822_().nextFloat() < 0.2F) {
         this.m_6863_(true);
      }

      return super.m_6518_(p_34508_, p_34509_, p_34510_, p_34511_, p_34512_);
   }

   public boolean m_6785_(double p_34559_) {
      return !this.m_21532_();
   }

   public float m_5610_(BlockPos p_34516_, LevelReader p_34517_) {
      if (HoglinAi.m_34585_(this, p_34516_)) {
         return -1.0F;
      } else {
         return p_34517_.m_8055_(p_34516_.m_7495_()).m_60713_(Blocks.f_50699_) ? 10.0F : 0.0F;
      }
   }

   public double m_6048_() {
      return (double)this.m_20206_() - (this.m_6162_() ? 0.2D : 0.15D);
   }

   public InteractionResult m_6071_(Player p_34523_, InteractionHand p_34524_) {
      InteractionResult interactionresult = super.m_6071_(p_34523_, p_34524_);
      if (interactionresult.m_19077_()) {
         this.m_21530_();
      }

      return interactionresult;
   }

   public void m_7822_(byte p_34496_) {
      if (p_34496_ == 4) {
         this.f_34483_ = 10;
         this.m_5496_(SoundEvents.f_11958_, 1.0F, this.m_6100_());
      } else {
         super.m_7822_(p_34496_);
      }

   }

   public int m_7575_() {
      return this.f_34483_;
   }

   protected boolean m_6149_() {
      return true;
   }

   protected int m_6552_(Player p_34544_) {
      return this.f_21364_;
   }

   private void m_34531_(ServerLevel p_34532_) {
      Zoglin zoglin = this.m_21406_(EntityType.f_20500_, true);
      if (zoglin != null) {
         zoglin.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zoglin);
      }

   }

   public boolean m_6898_(ItemStack p_34562_) {
      return p_34562_.m_150930_(Items.f_41954_);
   }

   public boolean m_34552_() {
      return !this.m_6162_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_34482_, false);
   }

   public void m_7380_(CompoundTag p_34529_) {
      super.m_7380_(p_34529_);
      if (this.m_34557_()) {
         p_34529_.m_128379_("IsImmuneToZombification", true);
      }

      p_34529_.m_128405_("TimeInOverworld", this.f_34484_);
      if (this.f_34485_) {
         p_34529_.m_128379_("CannotBeHunted", true);
      }

   }

   public void m_7378_(CompoundTag p_34519_) {
      super.m_7378_(p_34519_);
      this.m_34564_(p_34519_.m_128471_("IsImmuneToZombification"));
      this.f_34484_ = p_34519_.m_128451_("TimeInOverworld");
      this.m_34566_(p_34519_.m_128471_("CannotBeHunted"));
   }

   public void m_34564_(boolean p_34565_) {
      this.m_20088_().m_135381_(f_34482_, p_34565_);
   }

   private boolean m_34557_() {
      return this.m_20088_().m_135370_(f_34482_);
   }

   public boolean m_34554_() {
      return !this.f_19853_.m_6042_().m_63960_() && !this.m_34557_() && !this.m_21525_();
   }

   private void m_34566_(boolean p_34567_) {
      this.f_34485_ = p_34567_;
   }

   public boolean m_34555_() {
      return this.m_34552_() && !this.f_34485_;
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_149900_, AgeableMob p_149901_) {
      Hoglin hoglin = EntityType.f_20456_.m_20615_(p_149900_);
      if (hoglin != null) {
         hoglin.m_21530_();
      }

      return hoglin;
   }

   public boolean m_5957_() {
      return !HoglinAi.m_34603_(this) && super.m_5957_();
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected SoundEvent m_7515_() {
      return this.f_19853_.f_46443_ ? null : HoglinAi.m_34593_(this).orElse((SoundEvent)null);
   }

   protected SoundEvent m_7975_(DamageSource p_34548_) {
      return SoundEvents.f_11961_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11960_;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_12042_;
   }

   protected SoundEvent m_5509_() {
      return SoundEvents.f_12041_;
   }

   protected void m_7355_(BlockPos p_34526_, BlockState p_34527_) {
      this.m_5496_(SoundEvents.f_11963_, 0.15F, 1.0F);
   }

   protected void m_34500_(SoundEvent p_34501_) {
      this.m_5496_(p_34501_, this.m_6121_(), this.m_6100_());
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133695_(this);
   }
}
