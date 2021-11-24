package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;

public final class EntityTypeTags {
   protected static final StaticTagHelper<EntityType<?>> f_13119_ = StaticTags.m_144351_(Registry.f_122903_, "tags/entity_types");
   public static final Tag.Named<EntityType<?>> f_13120_ = m_13127_("skeletons");
   public static final Tag.Named<EntityType<?>> f_13121_ = m_13127_("raiders");
   public static final Tag.Named<EntityType<?>> f_13122_ = m_13127_("beehive_inhabitors");
   public static final Tag.Named<EntityType<?>> f_13123_ = m_13127_("arrows");
   public static final Tag.Named<EntityType<?>> f_13124_ = m_13127_("impact_projectiles");
   public static final Tag.Named<EntityType<?>> f_144291_ = m_13127_("powder_snow_walkable_mobs");
   public static final Tag.Named<EntityType<?>> f_144292_ = m_13127_("axolotl_always_hostiles");
   public static final Tag.Named<EntityType<?>> f_144293_ = m_13127_("axolotl_hunt_targets");
   public static final Tag.Named<EntityType<?>> f_144294_ = m_13127_("freeze_immune_entity_types");
   public static final Tag.Named<EntityType<?>> f_144295_ = m_13127_("freeze_hurts_extra_types");

   private EntityTypeTags() {
   }

   public static Tag.Named<EntityType<?>> m_13127_(String p_13128_) {
      return f_13119_.m_13244_(p_13128_);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<EntityType<?>> createOptional(net.minecraft.resources.ResourceLocation name) {
       return createOptional(name, null);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<EntityType<?>> createOptional(net.minecraft.resources.ResourceLocation name, @javax.annotation.Nullable java.util.Set<java.util.function.Supplier<EntityType<?>>> defaults) {
      return f_13119_.createOptional(name, defaults);
   }

   public static TagCollection<EntityType<?>> m_13126_() {
      return f_13119_.m_13246_();
   }
}
