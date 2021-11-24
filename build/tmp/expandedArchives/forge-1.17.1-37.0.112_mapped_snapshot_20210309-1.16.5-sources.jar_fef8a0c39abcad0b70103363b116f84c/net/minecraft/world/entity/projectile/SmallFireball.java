package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SmallFireball extends Fireball {
   public SmallFireball(EntityType<? extends SmallFireball> p_37364_, Level p_37365_) {
      super(p_37364_, p_37365_);
   }

   public SmallFireball(Level p_37375_, LivingEntity p_37376_, double p_37377_, double p_37378_, double p_37379_) {
      super(EntityType.f_20527_, p_37376_, p_37377_, p_37378_, p_37379_, p_37375_);
   }

   public SmallFireball(Level p_37367_, double p_37368_, double p_37369_, double p_37370_, double p_37371_, double p_37372_, double p_37373_) {
      super(EntityType.f_20527_, p_37368_, p_37369_, p_37370_, p_37371_, p_37372_, p_37373_, p_37367_);
   }

   protected void m_5790_(EntityHitResult p_37386_) {
      super.m_5790_(p_37386_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37386_.m_82443_();
         if (!entity.m_5825_()) {
            Entity entity1 = this.m_37282_();
            int i = entity.m_20094_();
            entity.m_20254_(5);
            boolean flag = entity.m_6469_(DamageSource.m_19349_(this, entity1), 5.0F);
            if (!flag) {
               entity.m_7311_(i);
            } else if (entity1 instanceof LivingEntity) {
               this.m_19970_((LivingEntity)entity1, entity);
            }
         }

      }
   }

   protected void m_8060_(BlockHitResult p_37384_) {
      super.m_8060_(p_37384_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = this.m_37282_();
         if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            BlockPos blockpos = p_37384_.m_82425_().m_142300_(p_37384_.m_82434_());
            if (this.f_19853_.m_46859_(blockpos)) {
               this.f_19853_.m_46597_(blockpos, BaseFireBlock.m_49245_(this.f_19853_, blockpos));
            }
         }

      }
   }

   protected void m_6532_(HitResult p_37388_) {
      super.m_6532_(p_37388_);
      if (!this.f_19853_.f_46443_) {
         this.m_146870_();
      }

   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource p_37381_, float p_37382_) {
      return false;
   }
}
