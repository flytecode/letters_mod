package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class EntityZombifiedPiglinRenameFix extends SimplestEntityRenameFix {
   public static final Map<String, String> f_15814_ = ImmutableMap.<String, String>builder().put("minecraft:zombie_pigman_spawn_egg", "minecraft:zombified_piglin_spawn_egg").build();

   public EntityZombifiedPiglinRenameFix(Schema p_15817_) {
      super("EntityZombifiedPiglinRenameFix", p_15817_, true);
   }

   protected String m_7476_(String p_15819_) {
      return Objects.equals("minecraft:zombie_pigman", p_15819_) ? "minecraft:zombified_piglin" : p_15819_;
   }
}