package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public abstract class SpellcasterIllager extends AbstractIllager {
   private static final EntityDataAccessor<Byte> f_33720_ = SynchedEntityData.m_135353_(SpellcasterIllager.class, EntityDataSerializers.f_135027_);
   protected int f_33719_;
   private SpellcasterIllager.IllagerSpell f_33721_ = SpellcasterIllager.IllagerSpell.NONE;

   protected SpellcasterIllager(EntityType<? extends SpellcasterIllager> p_33724_, Level p_33725_) {
      super(p_33724_, p_33725_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33720_, (byte)0);
   }

   public void m_7378_(CompoundTag p_33732_) {
      super.m_7378_(p_33732_);
      this.f_33719_ = p_33732_.m_128451_("SpellTicks");
   }

   public void m_7380_(CompoundTag p_33734_) {
      super.m_7380_(p_33734_);
      p_33734_.m_128405_("SpellTicks", this.f_33719_);
   }

   public AbstractIllager.IllagerArmPose m_6768_() {
      if (this.m_33736_()) {
         return AbstractIllager.IllagerArmPose.SPELLCASTING;
      } else {
         return this.m_37888_() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
      }
   }

   public boolean m_33736_() {
      if (this.f_19853_.f_46443_) {
         return this.f_19804_.m_135370_(f_33720_) > 0;
      } else {
         return this.f_33719_ > 0;
      }
   }

   public void m_33727_(SpellcasterIllager.IllagerSpell p_33728_) {
      this.f_33721_ = p_33728_;
      this.f_19804_.m_135381_(f_33720_, (byte)p_33728_.f_33747_);
   }

   protected SpellcasterIllager.IllagerSpell m_33737_() {
      return !this.f_19853_.f_46443_ ? this.f_33721_ : SpellcasterIllager.IllagerSpell.m_33758_(this.f_19804_.m_135370_(f_33720_));
   }

   protected void m_8024_() {
      super.m_8024_();
      if (this.f_33719_ > 0) {
         --this.f_33719_;
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_ && this.m_33736_()) {
         SpellcasterIllager.IllagerSpell spellcasterillager$illagerspell = this.m_33737_();
         double d0 = spellcasterillager$illagerspell.f_33748_[0];
         double d1 = spellcasterillager$illagerspell.f_33748_[1];
         double d2 = spellcasterillager$illagerspell.f_33748_[2];
         float f = this.f_20883_ * ((float)Math.PI / 180F) + Mth.m_14089_((float)this.f_19797_ * 0.6662F) * 0.25F;
         float f1 = Mth.m_14089_(f);
         float f2 = Mth.m_14031_(f);
         this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() + (double)f1 * 0.6D, this.m_20186_() + 1.8D, this.m_20189_() + (double)f2 * 0.6D, d0, d1, d2);
         this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() - (double)f1 * 0.6D, this.m_20186_() + 1.8D, this.m_20189_() - (double)f2 * 0.6D, d0, d1, d2);
      }

   }

   protected int m_33738_() {
      return this.f_33719_;
   }

   protected abstract SoundEvent m_7894_();

   protected static enum IllagerSpell {
      NONE(0, 0.0D, 0.0D, 0.0D),
      SUMMON_VEX(1, 0.7D, 0.7D, 0.8D),
      FANGS(2, 0.4D, 0.3D, 0.35D),
      WOLOLO(3, 0.7D, 0.5D, 0.2D),
      DISAPPEAR(4, 0.3D, 0.3D, 0.8D),
      BLINDNESS(5, 0.1D, 0.1D, 0.2D);

      final int f_33747_;
      final double[] f_33748_;

      private IllagerSpell(int p_33754_, double p_33755_, double p_33756_, double p_33757_) {
         this.f_33747_ = p_33754_;
         this.f_33748_ = new double[]{p_33755_, p_33756_, p_33757_};
      }

      public static SpellcasterIllager.IllagerSpell m_33758_(int p_33759_) {
         for(SpellcasterIllager.IllagerSpell spellcasterillager$illagerspell : values()) {
            if (p_33759_ == spellcasterillager$illagerspell.f_33747_) {
               return spellcasterillager$illagerspell;
            }
         }

         return NONE;
      }
   }

   protected class SpellcasterCastingSpellGoal extends Goal {
      public SpellcasterCastingSpellGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         return SpellcasterIllager.this.m_33738_() > 0;
      }

      public void m_8056_() {
         super.m_8056_();
         SpellcasterIllager.this.f_21344_.m_26573_();
      }

      public void m_8041_() {
         super.m_8041_();
         SpellcasterIllager.this.m_33727_(SpellcasterIllager.IllagerSpell.NONE);
      }

      public void m_8037_() {
         if (SpellcasterIllager.this.m_5448_() != null) {
            SpellcasterIllager.this.m_21563_().m_24960_(SpellcasterIllager.this.m_5448_(), (float)SpellcasterIllager.this.m_8085_(), (float)SpellcasterIllager.this.m_8132_());
         }

      }
   }

   protected abstract class SpellcasterUseSpellGoal extends Goal {
      protected int f_33774_;
      protected int f_33775_;

      public boolean m_8036_() {
         LivingEntity livingentity = SpellcasterIllager.this.m_5448_();
         if (livingentity != null && livingentity.m_6084_()) {
            if (SpellcasterIllager.this.m_33736_()) {
               return false;
            } else {
               return SpellcasterIllager.this.f_19797_ >= this.f_33775_;
            }
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         LivingEntity livingentity = SpellcasterIllager.this.m_5448_();
         return livingentity != null && livingentity.m_6084_() && this.f_33774_ > 0;
      }

      public void m_8056_() {
         this.f_33774_ = this.m_8069_();
         SpellcasterIllager.this.f_33719_ = this.m_8089_();
         this.f_33775_ = SpellcasterIllager.this.f_19797_ + this.m_8067_();
         SoundEvent soundevent = this.m_7030_();
         if (soundevent != null) {
            SpellcasterIllager.this.m_5496_(soundevent, 1.0F, 1.0F);
         }

         SpellcasterIllager.this.m_33727_(this.m_7269_());
      }

      public void m_8037_() {
         --this.f_33774_;
         if (this.f_33774_ == 0) {
            this.m_8130_();
            SpellcasterIllager.this.m_5496_(SpellcasterIllager.this.m_7894_(), 1.0F, 1.0F);
         }

      }

      protected abstract void m_8130_();

      protected int m_8069_() {
         return 20;
      }

      protected abstract int m_8089_();

      protected abstract int m_8067_();

      @Nullable
      protected abstract SoundEvent m_7030_();

      protected abstract SpellcasterIllager.IllagerSpell m_7269_();
   }
}