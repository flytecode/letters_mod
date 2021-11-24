package net.minecraft.world.effect;

import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

@net.minecraftforge.registries.ObjectHolder("minecraft")
public class MobEffects {
   public static final MobEffect f_19596_ = m_19623_(1, "speed", (new MobEffect(MobEffectCategory.BENEFICIAL, 8171462)).m_19472_(Attributes.f_22279_, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final MobEffect f_19597_ = m_19623_(2, "slowness", (new MobEffect(MobEffectCategory.HARMFUL, 5926017)).m_19472_(Attributes.f_22279_, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final MobEffect f_19598_ = m_19623_(3, "haste", (new MobEffect(MobEffectCategory.BENEFICIAL, 14270531)).m_19472_(Attributes.f_22283_, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final MobEffect f_19599_ = m_19623_(4, "mining_fatigue", (new MobEffect(MobEffectCategory.HARMFUL, 4866583)).m_19472_(Attributes.f_22283_, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final MobEffect f_19600_ = m_19623_(5, "strength", (new AttackDamageMobEffect(MobEffectCategory.BENEFICIAL, 9643043, 3.0D)).m_19472_(Attributes.f_22281_, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, AttributeModifier.Operation.ADDITION));
   public static final MobEffect f_19601_ = m_19623_(6, "instant_health", new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 16262179));
   public static final MobEffect f_19602_ = m_19623_(7, "instant_damage", new InstantenousMobEffect(MobEffectCategory.HARMFUL, 4393481));
   public static final MobEffect f_19603_ = m_19623_(8, "jump_boost", new MobEffect(MobEffectCategory.BENEFICIAL, 2293580));
   public static final MobEffect f_19604_ = m_19623_(9, "nausea", new MobEffect(MobEffectCategory.HARMFUL, 5578058));
   public static final MobEffect f_19605_ = m_19623_(10, "regeneration", new MobEffect(MobEffectCategory.BENEFICIAL, 13458603));
   public static final MobEffect f_19606_ = m_19623_(11, "resistance", new MobEffect(MobEffectCategory.BENEFICIAL, 10044730));
   public static final MobEffect f_19607_ = m_19623_(12, "fire_resistance", new MobEffect(MobEffectCategory.BENEFICIAL, 14981690));
   public static final MobEffect f_19608_ = m_19623_(13, "water_breathing", new MobEffect(MobEffectCategory.BENEFICIAL, 3035801));
   public static final MobEffect f_19609_ = m_19623_(14, "invisibility", new MobEffect(MobEffectCategory.BENEFICIAL, 8356754));
   public static final MobEffect f_19610_ = m_19623_(15, "blindness", new MobEffect(MobEffectCategory.HARMFUL, 2039587));
   public static final MobEffect f_19611_ = m_19623_(16, "night_vision", new MobEffect(MobEffectCategory.BENEFICIAL, 2039713));
   public static final MobEffect f_19612_ = m_19623_(17, "hunger", new MobEffect(MobEffectCategory.HARMFUL, 5797459));
   public static final MobEffect f_19613_ = m_19623_(18, "weakness", (new AttackDamageMobEffect(MobEffectCategory.HARMFUL, 4738376, -4.0D)).m_19472_(Attributes.f_22281_, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION));
   public static final MobEffect f_19614_ = m_19623_(19, "poison", new MobEffect(MobEffectCategory.HARMFUL, 5149489));
   public static final MobEffect f_19615_ = m_19623_(20, "wither", new MobEffect(MobEffectCategory.HARMFUL, 3484199));
   public static final MobEffect f_19616_ = m_19623_(21, "health_boost", (new HealthBoostMobEffect(MobEffectCategory.BENEFICIAL, 16284963)).m_19472_(Attributes.f_22276_, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION));
   public static final MobEffect f_19617_ = m_19623_(22, "absorption", new AbsoptionMobEffect(MobEffectCategory.BENEFICIAL, 2445989));
   public static final MobEffect f_19618_ = m_19623_(23, "saturation", new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 16262179));
   public static final MobEffect f_19619_ = m_19623_(24, "glowing", new MobEffect(MobEffectCategory.NEUTRAL, 9740385));
   public static final MobEffect f_19620_ = m_19623_(25, "levitation", new MobEffect(MobEffectCategory.HARMFUL, 13565951));
   public static final MobEffect f_19621_ = m_19623_(26, "luck", (new MobEffect(MobEffectCategory.BENEFICIAL, 3381504)).m_19472_(Attributes.f_22286_, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0D, AttributeModifier.Operation.ADDITION));
   public static final MobEffect f_19590_ = m_19623_(27, "unluck", (new MobEffect(MobEffectCategory.HARMFUL, 12624973)).m_19472_(Attributes.f_22286_, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, AttributeModifier.Operation.ADDITION));
   public static final MobEffect f_19591_ = m_19623_(28, "slow_falling", new MobEffect(MobEffectCategory.BENEFICIAL, 16773073));
   public static final MobEffect f_19592_ = m_19623_(29, "conduit_power", new MobEffect(MobEffectCategory.BENEFICIAL, 1950417));
   public static final MobEffect f_19593_ = m_19623_(30, "dolphins_grace", new MobEffect(MobEffectCategory.BENEFICIAL, 8954814));
   public static final MobEffect f_19594_ = m_19623_(31, "bad_omen", new MobEffect(MobEffectCategory.NEUTRAL, 745784) {
      public boolean m_6584_(int p_19631_, int p_19632_) {
         return true;
      }

      public void m_6742_(LivingEntity p_19634_, int p_19635_) {
         if (p_19634_ instanceof ServerPlayer && !p_19634_.m_5833_()) {
            ServerPlayer serverplayer = (ServerPlayer)p_19634_;
            ServerLevel serverlevel = serverplayer.m_9236_();
            if (serverlevel.m_46791_() == Difficulty.PEACEFUL) {
               return;
            }

            if (serverlevel.m_8802_(p_19634_.m_142538_())) {
               serverlevel.m_8905_().m_37963_(serverplayer);
            }
         }

      }
   });
   public static final MobEffect f_19595_ = m_19623_(32, "hero_of_the_village", new MobEffect(MobEffectCategory.BENEFICIAL, 4521796));

   private static MobEffect m_19623_(int p_19624_, String p_19625_, MobEffect p_19626_) {
      return Registry.m_122956_(Registry.f_122823_, p_19624_, p_19625_, p_19626_);
   }
}
