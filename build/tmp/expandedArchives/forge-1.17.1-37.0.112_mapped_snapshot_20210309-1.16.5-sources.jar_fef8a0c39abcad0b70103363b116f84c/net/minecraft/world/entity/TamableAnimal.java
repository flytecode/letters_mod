package net.minecraft.world.entity;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

public abstract class TamableAnimal extends Animal implements OwnableEntity {
   protected static final EntityDataAccessor<Byte> f_21798_ = SynchedEntityData.m_135353_(TamableAnimal.class, EntityDataSerializers.f_135027_);
   protected static final EntityDataAccessor<Optional<UUID>> f_21799_ = SynchedEntityData.m_135353_(TamableAnimal.class, EntityDataSerializers.f_135041_);
   private boolean f_21800_;

   protected TamableAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
      super(p_21803_, p_21804_);
      this.m_5849_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_21798_, (byte)0);
      this.f_19804_.m_135372_(f_21799_, Optional.empty());
   }

   public void m_7380_(CompoundTag p_21819_) {
      super.m_7380_(p_21819_);
      if (this.m_142504_() != null) {
         p_21819_.m_128362_("Owner", this.m_142504_());
      }

      p_21819_.m_128379_("Sitting", this.f_21800_);
   }

   public void m_7378_(CompoundTag p_21815_) {
      super.m_7378_(p_21815_);
      UUID uuid;
      if (p_21815_.m_128403_("Owner")) {
         uuid = p_21815_.m_128342_("Owner");
      } else {
         String s = p_21815_.m_128461_("Owner");
         uuid = OldUsersConverter.m_11083_(this.m_20194_(), s);
      }

      if (uuid != null) {
         try {
            this.m_21816_(uuid);
            this.m_7105_(true);
         } catch (Throwable throwable) {
            this.m_7105_(false);
         }
      }

      this.f_21800_ = p_21815_.m_128471_("Sitting");
      this.m_21837_(this.f_21800_);
   }

   public boolean m_6573_(Player p_21813_) {
      return !this.m_21523_();
   }

   protected void m_21834_(boolean p_21835_) {
      ParticleOptions particleoptions = ParticleTypes.f_123750_;
      if (!p_21835_) {
         particleoptions = ParticleTypes.f_123762_;
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.02D;
         double d1 = this.f_19796_.nextGaussian() * 0.02D;
         double d2 = this.f_19796_.nextGaussian() * 0.02D;
         this.f_19853_.m_7106_(particleoptions, this.m_20208_(1.0D), this.m_20187_() + 0.5D, this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   public void m_7822_(byte p_21807_) {
      if (p_21807_ == 7) {
         this.m_21834_(true);
      } else if (p_21807_ == 6) {
         this.m_21834_(false);
      } else {
         super.m_7822_(p_21807_);
      }

   }

   public boolean m_21824_() {
      return (this.f_19804_.m_135370_(f_21798_) & 4) != 0;
   }

   public void m_7105_(boolean p_21836_) {
      byte b0 = this.f_19804_.m_135370_(f_21798_);
      if (p_21836_) {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 | 4));
      } else {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 & -5));
      }

      this.m_5849_();
   }

   protected void m_5849_() {
   }

   public boolean m_21825_() {
      return (this.f_19804_.m_135370_(f_21798_) & 1) != 0;
   }

   public void m_21837_(boolean p_21838_) {
      byte b0 = this.f_19804_.m_135370_(f_21798_);
      if (p_21838_) {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 | 1));
      } else {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 & -2));
      }

   }

   @Nullable
   public UUID m_142504_() {
      return this.f_19804_.m_135370_(f_21799_).orElse((UUID)null);
   }

   public void m_21816_(@Nullable UUID p_21817_) {
      this.f_19804_.m_135381_(f_21799_, Optional.ofNullable(p_21817_));
   }

   public void m_21828_(Player p_21829_) {
      this.m_7105_(true);
      this.m_21816_(p_21829_.m_142081_());
      if (p_21829_ instanceof ServerPlayer) {
         CriteriaTriggers.f_10590_.m_68829_((ServerPlayer)p_21829_, this);
      }

   }

   @Nullable
   public LivingEntity m_142480_() {
      try {
         UUID uuid = this.m_142504_();
         return uuid == null ? null : this.f_19853_.m_46003_(uuid);
      } catch (IllegalArgumentException illegalargumentexception) {
         return null;
      }
   }

   public boolean m_6779_(LivingEntity p_21822_) {
      return this.m_21830_(p_21822_) ? false : super.m_6779_(p_21822_);
   }

   public boolean m_21830_(LivingEntity p_21831_) {
      return p_21831_ == this.m_142480_();
   }

   public boolean m_7757_(LivingEntity p_21810_, LivingEntity p_21811_) {
      return true;
   }

   public Team m_5647_() {
      if (this.m_21824_()) {
         LivingEntity livingentity = this.m_142480_();
         if (livingentity != null) {
            return livingentity.m_5647_();
         }
      }

      return super.m_5647_();
   }

   public boolean m_7307_(Entity p_21833_) {
      if (this.m_21824_()) {
         LivingEntity livingentity = this.m_142480_();
         if (p_21833_ == livingentity) {
            return true;
         }

         if (livingentity != null) {
            return livingentity.m_7307_(p_21833_);
         }
      }

      return super.m_7307_(p_21833_);
   }

   public void m_6667_(DamageSource p_21809_) {
      // FORGE: Super moved to top so that death message would be cancelled properly
      super.m_6667_(p_21809_);

      if (this.f_20890_)
      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46142_) && this.m_142480_() instanceof ServerPlayer) {
         this.m_142480_().m_6352_(this.m_21231_().m_19293_(), Util.f_137441_);
      }

   }

   public boolean m_21827_() {
      return this.f_21800_;
   }

   public void m_21839_(boolean p_21840_) {
      this.f_21800_ = p_21840_;
   }
}
