package net.minecraft.world.entity.monster;

import java.util.List;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class ElderGuardian extends Guardian {
   public static final float f_32457_ = EntityType.f_20563_.m_20678_() / EntityType.f_20455_.m_20678_();

   public ElderGuardian(EntityType<? extends ElderGuardian> p_32460_, Level p_32461_) {
      super(p_32460_, p_32461_);
      this.m_21530_();
      if (this.f_32806_ != null) {
         this.f_32806_.m_25746_(400);
      }

   }

   public static AttributeSupplier.Builder m_32471_() {
      return Guardian.m_32853_().m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, 8.0D).m_22268_(Attributes.f_22276_, 80.0D);
   }

   public int m_7552_() {
      return 60;
   }

   protected SoundEvent m_7515_() {
      return this.m_20072_() ? SoundEvents.f_11878_ : SoundEvents.f_11879_;
   }

   protected SoundEvent m_7975_(DamageSource p_32468_) {
      return this.m_20072_() ? SoundEvents.f_11884_ : SoundEvents.f_11885_;
   }

   protected SoundEvent m_5592_() {
      return this.m_20072_() ? SoundEvents.f_11881_ : SoundEvents.f_11882_;
   }

   protected SoundEvent m_7868_() {
      return SoundEvents.f_11883_;
   }

   protected void m_8024_() {
      super.m_8024_();
      int i = 1200;
      if ((this.f_19797_ + this.m_142049_()) % 1200 == 0) {
         MobEffect mobeffect = MobEffects.f_19599_;
         List<ServerPlayer> list = ((ServerLevel)this.f_19853_).m_8795_((p_32465_) -> {
            return this.m_20280_(p_32465_) < 2500.0D && p_32465_.f_8941_.m_9294_();
         });
         int j = 2;
         int k = 6000;
         int l = 1200;

         for(ServerPlayer serverplayer : list) {
            if (!serverplayer.m_21023_(mobeffect) || serverplayer.m_21124_(mobeffect).m_19564_() < 2 || serverplayer.m_21124_(mobeffect).m_19557_() < 1200) {
               serverplayer.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132163_, this.m_20067_() ? 0.0F : 1.0F));
               serverplayer.m_147207_(new MobEffectInstance(mobeffect, 6000, 2), this);
            }
         }
      }

      if (!this.m_21536_()) {
         this.m_21446_(this.m_142538_(), 16);
      }

   }
}