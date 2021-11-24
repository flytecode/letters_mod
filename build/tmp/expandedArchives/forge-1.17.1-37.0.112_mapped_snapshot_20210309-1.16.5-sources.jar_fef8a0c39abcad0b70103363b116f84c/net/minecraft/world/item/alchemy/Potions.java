package net.minecraft.world.item.alchemy;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Potions {
   public static final Potion f_43598_ = m_43625_("empty", new Potion());
   public static final Potion f_43599_ = m_43625_("water", new Potion());
   public static final Potion f_43600_ = m_43625_("mundane", new Potion());
   public static final Potion f_43601_ = m_43625_("thick", new Potion());
   public static final Potion f_43602_ = m_43625_("awkward", new Potion());
   public static final Potion f_43603_ = m_43625_("night_vision", new Potion(new MobEffectInstance(MobEffects.f_19611_, 3600)));
   public static final Potion f_43604_ = m_43625_("long_night_vision", new Potion("night_vision", new MobEffectInstance(MobEffects.f_19611_, 9600)));
   public static final Potion f_43605_ = m_43625_("invisibility", new Potion(new MobEffectInstance(MobEffects.f_19609_, 3600)));
   public static final Potion f_43606_ = m_43625_("long_invisibility", new Potion("invisibility", new MobEffectInstance(MobEffects.f_19609_, 9600)));
   public static final Potion f_43607_ = m_43625_("leaping", new Potion(new MobEffectInstance(MobEffects.f_19603_, 3600)));
   public static final Potion f_43608_ = m_43625_("long_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.f_19603_, 9600)));
   public static final Potion f_43609_ = m_43625_("strong_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.f_19603_, 1800, 1)));
   public static final Potion f_43610_ = m_43625_("fire_resistance", new Potion(new MobEffectInstance(MobEffects.f_19607_, 3600)));
   public static final Potion f_43611_ = m_43625_("long_fire_resistance", new Potion("fire_resistance", new MobEffectInstance(MobEffects.f_19607_, 9600)));
   public static final Potion f_43612_ = m_43625_("swiftness", new Potion(new MobEffectInstance(MobEffects.f_19596_, 3600)));
   public static final Potion f_43613_ = m_43625_("long_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.f_19596_, 9600)));
   public static final Potion f_43614_ = m_43625_("strong_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.f_19596_, 1800, 1)));
   public static final Potion f_43615_ = m_43625_("slowness", new Potion(new MobEffectInstance(MobEffects.f_19597_, 1800)));
   public static final Potion f_43616_ = m_43625_("long_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.f_19597_, 4800)));
   public static final Potion f_43617_ = m_43625_("strong_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.f_19597_, 400, 3)));
   public static final Potion f_43618_ = m_43625_("turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 400, 3), new MobEffectInstance(MobEffects.f_19606_, 400, 2)));
   public static final Potion f_43619_ = m_43625_("long_turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 800, 3), new MobEffectInstance(MobEffects.f_19606_, 800, 2)));
   public static final Potion f_43620_ = m_43625_("strong_turtle_master", new Potion("turtle_master", new MobEffectInstance(MobEffects.f_19597_, 400, 5), new MobEffectInstance(MobEffects.f_19606_, 400, 3)));
   public static final Potion f_43621_ = m_43625_("water_breathing", new Potion(new MobEffectInstance(MobEffects.f_19608_, 3600)));
   public static final Potion f_43622_ = m_43625_("long_water_breathing", new Potion("water_breathing", new MobEffectInstance(MobEffects.f_19608_, 9600)));
   public static final Potion f_43623_ = m_43625_("healing", new Potion(new MobEffectInstance(MobEffects.f_19601_, 1)));
   public static final Potion f_43581_ = m_43625_("strong_healing", new Potion("healing", new MobEffectInstance(MobEffects.f_19601_, 1, 1)));
   public static final Potion f_43582_ = m_43625_("harming", new Potion(new MobEffectInstance(MobEffects.f_19602_, 1)));
   public static final Potion f_43583_ = m_43625_("strong_harming", new Potion("harming", new MobEffectInstance(MobEffects.f_19602_, 1, 1)));
   public static final Potion f_43584_ = m_43625_("poison", new Potion(new MobEffectInstance(MobEffects.f_19614_, 900)));
   public static final Potion f_43585_ = m_43625_("long_poison", new Potion("poison", new MobEffectInstance(MobEffects.f_19614_, 1800)));
   public static final Potion f_43586_ = m_43625_("strong_poison", new Potion("poison", new MobEffectInstance(MobEffects.f_19614_, 432, 1)));
   public static final Potion f_43587_ = m_43625_("regeneration", new Potion(new MobEffectInstance(MobEffects.f_19605_, 900)));
   public static final Potion f_43588_ = m_43625_("long_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.f_19605_, 1800)));
   public static final Potion f_43589_ = m_43625_("strong_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.f_19605_, 450, 1)));
   public static final Potion f_43590_ = m_43625_("strength", new Potion(new MobEffectInstance(MobEffects.f_19600_, 3600)));
   public static final Potion f_43591_ = m_43625_("long_strength", new Potion("strength", new MobEffectInstance(MobEffects.f_19600_, 9600)));
   public static final Potion f_43592_ = m_43625_("strong_strength", new Potion("strength", new MobEffectInstance(MobEffects.f_19600_, 1800, 1)));
   public static final Potion f_43593_ = m_43625_("weakness", new Potion(new MobEffectInstance(MobEffects.f_19613_, 1800)));
   public static final Potion f_43594_ = m_43625_("long_weakness", new Potion("weakness", new MobEffectInstance(MobEffects.f_19613_, 4800)));
   public static final Potion f_43595_ = m_43625_("luck", new Potion("luck", new MobEffectInstance(MobEffects.f_19621_, 6000)));
   public static final Potion f_43596_ = m_43625_("slow_falling", new Potion(new MobEffectInstance(MobEffects.f_19591_, 1800)));
   public static final Potion f_43597_ = m_43625_("long_slow_falling", new Potion("slow_falling", new MobEffectInstance(MobEffects.f_19591_, 4800)));

   private static Potion m_43625_(String p_43626_, Potion p_43627_) {
      return Registry.m_122961_(Registry.f_122828_, p_43626_, p_43627_);
   }
}