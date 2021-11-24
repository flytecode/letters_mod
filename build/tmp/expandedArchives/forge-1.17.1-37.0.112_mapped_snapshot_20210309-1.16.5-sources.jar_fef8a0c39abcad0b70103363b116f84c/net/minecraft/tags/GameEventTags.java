package net.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.world.level.gameevent.GameEvent;

public class GameEventTags {
   protected static final StaticTagHelper<GameEvent> f_144301_ = StaticTags.m_144351_(Registry.f_175423_, "tags/game_events");
   public static final Tag.Named<GameEvent> f_144302_ = m_144307_("vibrations");
   public static final Tag.Named<GameEvent> f_144303_ = m_144307_("ignore_vibrations_sneaking");

   public static Tag.Named<GameEvent> m_144307_(String p_144308_) {
      return f_144301_.m_13244_(p_144308_);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<GameEvent> createOptional(net.minecraft.resources.ResourceLocation name) {
      return createOptional(name, null);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<GameEvent> createOptional(net.minecraft.resources.ResourceLocation name, @javax.annotation.Nullable java.util.Set<java.util.function.Supplier<GameEvent>> defaults) {
      return f_144301_.createOptional(name, defaults);
   }

   public static TagCollection<GameEvent> m_144306_() {
      return f_144301_.m_13246_();
   }
}
