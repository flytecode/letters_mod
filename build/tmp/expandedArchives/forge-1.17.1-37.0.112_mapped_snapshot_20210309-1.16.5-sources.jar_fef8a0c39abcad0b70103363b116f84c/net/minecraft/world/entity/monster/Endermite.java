package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class Endermite extends Monster {
   private static final int f_149695_ = 2400;
   private int f_32588_;

   public Endermite(EntityType<? extends Endermite> p_32591_, Level p_32592_) {
      super(p_32591_, p_32592_);
      this.f_21364_ = 3;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_());
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
   }

   protected float m_6431_(Pose p_32604_, EntityDimensions p_32605_) {
      return 0.13F;
   }

   public static AttributeSupplier.Builder m_32619_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 8.0D).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 2.0D);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11853_;
   }

   protected SoundEvent m_7975_(DamageSource p_32615_) {
      return SoundEvents.f_11855_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11854_;
   }

   protected void m_7355_(BlockPos p_32607_, BlockState p_32608_) {
      this.m_5496_(SoundEvents.f_11856_, 0.15F, 1.0F);
   }

   public void m_7378_(CompoundTag p_32595_) {
      super.m_7378_(p_32595_);
      this.f_32588_ = p_32595_.m_128451_("Lifetime");
   }

   public void m_7380_(CompoundTag p_32610_) {
      super.m_7380_(p_32610_);
      p_32610_.m_128405_("Lifetime", this.f_32588_);
   }

   public void m_8119_() {
      this.f_20883_ = this.m_146908_();
      super.m_8119_();
   }

   public void m_5618_(float p_32621_) {
      this.m_146922_(p_32621_);
      super.m_5618_(p_32621_);
   }

   public double m_6049_() {
      return 0.1D;
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_19853_.f_46443_) {
         for(int i = 0; i < 2; ++i) {
            this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20208_(0.5D), this.m_20187_(), this.m_20262_(0.5D), (this.f_19796_.nextDouble() - 0.5D) * 2.0D, -this.f_19796_.nextDouble(), (this.f_19796_.nextDouble() - 0.5D) * 2.0D);
         }
      } else {
         if (!this.m_21532_()) {
            ++this.f_32588_;
         }

         if (this.f_32588_ >= 2400) {
            this.m_146870_();
         }
      }

   }

   public static boolean m_32597_(EntityType<Endermite> p_32598_, LevelAccessor p_32599_, MobSpawnType p_32600_, BlockPos p_32601_, Random p_32602_) {
      if (m_33023_(p_32598_, p_32599_, p_32600_, p_32601_, p_32602_)) {
         Player player = p_32599_.m_45924_((double)p_32601_.m_123341_() + 0.5D, (double)p_32601_.m_123342_() + 0.5D, (double)p_32601_.m_123343_() + 0.5D, 5.0D, true);
         return player == null;
      } else {
         return false;
      }
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }
}