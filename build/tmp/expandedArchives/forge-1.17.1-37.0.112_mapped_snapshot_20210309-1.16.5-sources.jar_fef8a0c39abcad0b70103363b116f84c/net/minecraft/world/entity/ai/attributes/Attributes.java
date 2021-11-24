package net.minecraft.world.entity.ai.attributes;

import net.minecraft.core.Registry;

public class Attributes {
   public static final Attribute f_22276_ = m_22290_("generic.max_health", (new RangedAttribute("attribute.name.generic.max_health", 20.0D, 1.0D, 1024.0D)).m_22084_(true));
   public static final Attribute f_22277_ = m_22290_("generic.follow_range", new RangedAttribute("attribute.name.generic.follow_range", 32.0D, 0.0D, 2048.0D));
   public static final Attribute f_22278_ = m_22290_("generic.knockback_resistance", new RangedAttribute("attribute.name.generic.knockback_resistance", 0.0D, 0.0D, 1.0D));
   public static final Attribute f_22279_ = m_22290_("generic.movement_speed", (new RangedAttribute("attribute.name.generic.movement_speed", (double)0.7F, 0.0D, 1024.0D)).m_22084_(true));
   public static final Attribute f_22280_ = m_22290_("generic.flying_speed", (new RangedAttribute("attribute.name.generic.flying_speed", (double)0.4F, 0.0D, 1024.0D)).m_22084_(true));
   public static final Attribute f_22281_ = m_22290_("generic.attack_damage", new RangedAttribute("attribute.name.generic.attack_damage", 2.0D, 0.0D, 2048.0D));
   public static final Attribute f_22282_ = m_22290_("generic.attack_knockback", new RangedAttribute("attribute.name.generic.attack_knockback", 0.0D, 0.0D, 5.0D));
   public static final Attribute f_22283_ = m_22290_("generic.attack_speed", (new RangedAttribute("attribute.name.generic.attack_speed", 4.0D, 0.0D, 1024.0D)).m_22084_(true));
   public static final Attribute f_22284_ = m_22290_("generic.armor", (new RangedAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 30.0D)).m_22084_(true));
   public static final Attribute f_22285_ = m_22290_("generic.armor_toughness", (new RangedAttribute("attribute.name.generic.armor_toughness", 0.0D, 0.0D, 20.0D)).m_22084_(true));
   public static final Attribute f_22286_ = m_22290_("generic.luck", (new RangedAttribute("attribute.name.generic.luck", 0.0D, -1024.0D, 1024.0D)).m_22084_(true));
   public static final Attribute f_22287_ = m_22290_("zombie.spawn_reinforcements", new RangedAttribute("attribute.name.zombie.spawn_reinforcements", 0.0D, 0.0D, 1.0D));
   public static final Attribute f_22288_ = m_22290_("horse.jump_strength", (new RangedAttribute("attribute.name.horse.jump_strength", 0.7D, 0.0D, 2.0D)).m_22084_(true));

   private static Attribute m_22290_(String p_22291_, Attribute p_22292_) {
      return Registry.m_122961_(Registry.f_122866_, p_22291_, p_22292_);
   }
}