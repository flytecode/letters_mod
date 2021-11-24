package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class MinecartTNT extends AbstractMinecart {
   private static final byte f_150345_ = 10;
   private int f_38647_ = -1;

   public MinecartTNT(EntityType<? extends MinecartTNT> p_38649_, Level p_38650_) {
      super(p_38649_, p_38650_);
   }

   public MinecartTNT(Level p_38652_, double p_38653_, double p_38654_, double p_38655_) {
      super(EntityType.f_20475_, p_38652_, p_38653_, p_38654_, p_38655_);
   }

   public AbstractMinecart.Type m_6064_() {
      return AbstractMinecart.Type.TNT;
   }

   public BlockState m_6390_() {
      return Blocks.f_50077_.m_49966_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_38647_ > 0) {
         --this.f_38647_;
         this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), 0.0D, 0.0D, 0.0D);
      } else if (this.f_38647_ == 0) {
         this.m_38688_(this.m_20184_().m_165925_());
      }

      if (this.f_19862_) {
         double d0 = this.m_20184_().m_165925_();
         if (d0 >= (double)0.01F) {
            this.m_38688_(d0);
         }
      }

   }

   public boolean m_6469_(DamageSource p_38666_, float p_38667_) {
      Entity entity = p_38666_.m_7640_();
      if (entity instanceof AbstractArrow) {
         AbstractArrow abstractarrow = (AbstractArrow)entity;
         if (abstractarrow.m_6060_()) {
            this.m_38688_(abstractarrow.m_20184_().m_82556_());
         }
      }

      return super.m_6469_(p_38666_, p_38667_);
   }

   public void m_7617_(DamageSource p_38664_) {
      double d0 = this.m_20184_().m_165925_();
      if (!p_38664_.m_19384_() && !p_38664_.m_19372_() && !(d0 >= (double)0.01F)) {
         super.m_7617_(p_38664_);
         if (!p_38664_.m_19372_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
            this.m_19998_(Blocks.f_50077_);
         }

      } else {
         if (this.f_38647_ < 0) {
            this.m_38693_();
            this.f_38647_ = this.f_19796_.nextInt(20) + this.f_19796_.nextInt(20);
         }

      }
   }

   protected void m_38688_(double p_38689_) {
      if (!this.f_19853_.f_46443_) {
         double d0 = Math.sqrt(p_38689_);
         if (d0 > 5.0D) {
            d0 = 5.0D;
         }

         this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)(4.0D + this.f_19796_.nextDouble() * 1.5D * d0), Explosion.BlockInteraction.BREAK);
         this.m_146870_();
      }

   }

   public boolean m_142535_(float p_150347_, float p_150348_, DamageSource p_150349_) {
      if (p_150347_ >= 3.0F) {
         float f = p_150347_ / 10.0F;
         this.m_38688_((double)(f * f));
      }

      return super.m_142535_(p_150347_, p_150348_, p_150349_);
   }

   public void m_6025_(int p_38659_, int p_38660_, int p_38661_, boolean p_38662_) {
      if (p_38662_ && this.f_38647_ < 0) {
         this.m_38693_();
      }

   }

   public void m_7822_(byte p_38657_) {
      if (p_38657_ == 10) {
         this.m_38693_();
      } else {
         super.m_7822_(p_38657_);
      }

   }

   public void m_38693_() {
      this.f_38647_ = 80;
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7605_(this, (byte)10);
         if (!this.m_20067_()) {
            this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }

   }

   public int m_38694_() {
      return this.f_38647_;
   }

   public boolean m_38695_() {
      return this.f_38647_ > -1;
   }

   public float m_7077_(Explosion p_38675_, BlockGetter p_38676_, BlockPos p_38677_, BlockState p_38678_, FluidState p_38679_, float p_38680_) {
      return !this.m_38695_() || !p_38678_.m_60620_(BlockTags.f_13034_) && !p_38676_.m_8055_(p_38677_.m_7494_()).m_60620_(BlockTags.f_13034_) ? super.m_7077_(p_38675_, p_38676_, p_38677_, p_38678_, p_38679_, p_38680_) : 0.0F;
   }

   public boolean m_7349_(Explosion p_38669_, BlockGetter p_38670_, BlockPos p_38671_, BlockState p_38672_, float p_38673_) {
      return !this.m_38695_() || !p_38672_.m_60620_(BlockTags.f_13034_) && !p_38670_.m_8055_(p_38671_.m_7494_()).m_60620_(BlockTags.f_13034_) ? super.m_7349_(p_38669_, p_38670_, p_38671_, p_38672_, p_38673_) : false;
   }

   protected void m_7378_(CompoundTag p_38682_) {
      super.m_7378_(p_38682_);
      if (p_38682_.m_128425_("TNTFuse", 99)) {
         this.f_38647_ = p_38682_.m_128451_("TNTFuse");
      }

   }

   protected void m_7380_(CompoundTag p_38687_) {
      super.m_7380_(p_38687_);
      p_38687_.m_128405_("TNTFuse", this.f_38647_);
   }
}