package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Pufferfish extends AbstractFish {
   private static final EntityDataAccessor<Integer> f_29596_ = SynchedEntityData.m_135353_(Pufferfish.class, EntityDataSerializers.f_135028_);
   int f_29598_;
   int f_29599_;
   private static final Predicate<LivingEntity> f_149008_ = (p_29634_) -> {
      if (p_29634_ instanceof Player && ((Player)p_29634_).m_7500_()) {
         return false;
      } else {
         return p_29634_.m_6095_() == EntityType.f_147039_ || p_29634_.m_6336_() != MobType.f_21644_;
      }
   };
   static final TargetingConditions f_149009_ = TargetingConditions.m_148353_().m_26893_().m_148355_().m_26888_(f_149008_);
   public static final int f_149007_ = 0;
   public static final int f_149010_ = 1;
   public static final int f_149011_ = 2;

   public Pufferfish(EntityType<? extends Pufferfish> p_29602_, Level p_29603_) {
      super(p_29602_, p_29603_);
      this.m_6210_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29596_, 0);
   }

   public int m_29631_() {
      return this.f_19804_.m_135370_(f_29596_);
   }

   public void m_29618_(int p_29619_) {
      this.f_19804_.m_135381_(f_29596_, p_29619_);
   }

   public void m_7350_(EntityDataAccessor<?> p_29615_) {
      if (f_29596_.equals(p_29615_)) {
         this.m_6210_();
      }

      super.m_7350_(p_29615_);
   }

   public void m_7380_(CompoundTag p_29624_) {
      super.m_7380_(p_29624_);
      p_29624_.m_128405_("PuffState", this.m_29631_());
   }

   public void m_7378_(CompoundTag p_29613_) {
      super.m_7378_(p_29613_);
      this.m_29618_(p_29613_.m_128451_("PuffState"));
   }

   public ItemStack m_142563_() {
      return new ItemStack(Items.f_42456_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new Pufferfish.PufferfishPuffGoal(this));
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_ && this.m_6084_() && this.m_6142_()) {
         if (this.f_29598_ > 0) {
            if (this.m_29631_() == 0) {
               this.m_5496_(SoundEvents.f_12291_, this.m_6121_(), this.m_6100_());
               this.m_29618_(1);
            } else if (this.f_29598_ > 40 && this.m_29631_() == 1) {
               this.m_5496_(SoundEvents.f_12291_, this.m_6121_(), this.m_6100_());
               this.m_29618_(2);
            }

            ++this.f_29598_;
         } else if (this.m_29631_() != 0) {
            if (this.f_29599_ > 60 && this.m_29631_() == 2) {
               this.m_5496_(SoundEvents.f_12290_, this.m_6121_(), this.m_6100_());
               this.m_29618_(1);
            } else if (this.f_29599_ > 100 && this.m_29631_() == 1) {
               this.m_5496_(SoundEvents.f_12290_, this.m_6121_(), this.m_6100_());
               this.m_29618_(0);
            }

            ++this.f_29599_;
         }
      }

      super.m_8119_();
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_6084_() && this.m_29631_() > 0) {
         for(Mob mob : this.f_19853_.m_6443_(Mob.class, this.m_142469_().m_82400_(0.3D), (p_149013_) -> {
            return f_149009_.m_26885_(this, p_149013_);
         })) {
            if (mob.m_6084_()) {
               this.m_29605_(mob);
            }
         }
      }

   }

   private void m_29605_(Mob p_29606_) {
      int i = this.m_29631_();
      if (p_29606_.m_6469_(DamageSource.m_19370_(this), (float)(1 + i))) {
         p_29606_.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 60 * i, 0), this);
         this.m_5496_(SoundEvents.f_12295_, 1.0F, 1.0F);
      }

   }

   public void m_6123_(Player p_29617_) {
      int i = this.m_29631_();
      if (p_29617_ instanceof ServerPlayer && i > 0 && p_29617_.m_6469_(DamageSource.m_19370_(this), (float)(1 + i))) {
         if (!this.m_20067_()) {
            ((ServerPlayer)p_29617_).f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132162_, 0.0F));
         }

         p_29617_.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 60 * i, 0), this);
      }

   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12289_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12292_;
   }

   protected SoundEvent m_7975_(DamageSource p_29628_) {
      return SoundEvents.f_12294_;
   }

   protected SoundEvent m_5699_() {
      return SoundEvents.f_12293_;
   }

   public EntityDimensions m_6972_(Pose p_29608_) {
      return super.m_6972_(p_29608_).m_20388_(m_29638_(this.m_29631_()));
   }

   private static float m_29638_(int p_29639_) {
      switch(p_29639_) {
      case 0:
         return 0.5F;
      case 1:
         return 0.7F;
      default:
         return 1.0F;
      }
   }

   static class PufferfishPuffGoal extends Goal {
      private final Pufferfish f_29640_;

      public PufferfishPuffGoal(Pufferfish p_29642_) {
         this.f_29640_ = p_29642_;
      }

      public boolean m_8036_() {
         List<LivingEntity> list = this.f_29640_.f_19853_.m_6443_(LivingEntity.class, this.f_29640_.m_142469_().m_82400_(2.0D), (p_149015_) -> {
            return Pufferfish.f_149009_.m_26885_(this.f_29640_, p_149015_);
         });
         return !list.isEmpty();
      }

      public void m_8056_() {
         this.f_29640_.f_29598_ = 1;
         this.f_29640_.f_29599_ = 0;
      }

      public void m_8041_() {
         this.f_29640_.f_29598_ = 0;
      }
   }
}