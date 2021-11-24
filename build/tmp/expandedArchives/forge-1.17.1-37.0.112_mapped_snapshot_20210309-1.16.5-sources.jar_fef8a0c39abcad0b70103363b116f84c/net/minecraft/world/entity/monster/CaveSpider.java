package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class CaveSpider extends Spider {
   public CaveSpider(EntityType<? extends CaveSpider> p_32254_, Level p_32255_) {
      super(p_32254_, p_32255_);
   }

   public static AttributeSupplier.Builder m_32267_() {
      return Spider.m_33815_().m_22268_(Attributes.f_22276_, 12.0D);
   }

   public boolean m_7327_(Entity p_32257_) {
      if (super.m_7327_(p_32257_)) {
         if (p_32257_ instanceof LivingEntity) {
            int i = 0;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
               i = 7;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
               i = 15;
            }

            if (i > 0) {
               ((LivingEntity)p_32257_).m_147207_(new MobEffectInstance(MobEffects.f_19614_, i * 20, 0), this);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_32259_, DifficultyInstance p_32260_, MobSpawnType p_32261_, @Nullable SpawnGroupData p_32262_, @Nullable CompoundTag p_32263_) {
      return p_32262_;
   }

   protected float m_6431_(Pose p_32265_, EntityDimensions p_32266_) {
      return 0.45F;
   }
}