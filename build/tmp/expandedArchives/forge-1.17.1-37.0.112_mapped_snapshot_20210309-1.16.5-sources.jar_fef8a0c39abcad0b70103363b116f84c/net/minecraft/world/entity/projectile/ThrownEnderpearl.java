package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEnderpearl extends ThrowableItemProjectile {
   public ThrownEnderpearl(EntityType<? extends ThrownEnderpearl> p_37491_, Level p_37492_) {
      super(p_37491_, p_37492_);
   }

   public ThrownEnderpearl(Level p_37499_, LivingEntity p_37500_) {
      super(EntityType.f_20484_, p_37500_, p_37499_);
   }

   protected Item m_7881_() {
      return Items.f_42584_;
   }

   protected void m_5790_(EntityHitResult p_37502_) {
      super.m_5790_(p_37502_);
      p_37502_.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0F);
   }

   protected void m_6532_(HitResult p_37504_) {
      super.m_6532_(p_37504_);

      for(int i = 0; i < 32; ++i) {
         this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20185_(), this.m_20186_() + this.f_19796_.nextDouble() * 2.0D, this.m_20189_(), this.f_19796_.nextGaussian(), 0.0D, this.f_19796_.nextGaussian());
      }

      if (!this.f_19853_.f_46443_ && !this.m_146910_()) {
         Entity entity = this.m_37282_();
         if (entity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer)entity;
            if (serverplayer.f_8906_.m_6198_().m_129536_() && serverplayer.f_19853_ == this.f_19853_ && !serverplayer.m_5803_()) {
               net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl event = net.minecraftforge.event.ForgeEventFactory.onEnderPearlLand(serverplayer, this.m_20185_(), this.m_20186_(), this.m_20189_(), this, 5.0F);
               if (!event.isCanceled()) { // Don't indent to lower patch size
               if (this.f_19796_.nextFloat() < 0.05F && this.f_19853_.m_46469_().m_46207_(GameRules.f_46134_)) {
                  Endermite endermite = EntityType.f_20567_.m_20615_(this.f_19853_);
                  endermite.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
                  this.f_19853_.m_7967_(endermite);
               }

               if (entity.m_20159_()) {
                  serverplayer.m_142098_(this.m_20185_(), this.m_20186_(), this.m_20189_());
               } else {
                  entity.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
               }

               entity.m_6021_(event.getTargetX(), event.getTargetY(), event.getTargetZ());
               entity.f_19789_ = 0.0F;
               entity.m_6469_(DamageSource.f_19315_, event.getAttackDamage());
               } //Forge: End
            }
         } else if (entity != null) {
            entity.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
            entity.f_19789_ = 0.0F;
         }

         this.m_146870_();
      }

   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (entity instanceof Player && !entity.m_6084_()) {
         this.m_146870_();
      } else {
         super.m_8119_();
      }

   }

   @Nullable
   public Entity changeDimension(ServerLevel p_37506_, net.minecraftforge.common.util.ITeleporter teleporter) {
      Entity entity = this.m_37282_();
      if (entity != null && entity.f_19853_.m_46472_() != p_37506_.m_46472_()) {
         this.m_5602_((Entity)null);
      }

      return super.changeDimension(p_37506_, teleporter);
   }
}
