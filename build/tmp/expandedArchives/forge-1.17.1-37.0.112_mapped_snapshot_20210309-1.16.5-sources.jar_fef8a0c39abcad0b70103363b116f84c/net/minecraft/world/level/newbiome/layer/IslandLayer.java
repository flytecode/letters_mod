package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer0;

public enum IslandLayer implements AreaTransformer0 {
   INSTANCE;

   public int m_7215_(Context p_76704_, int p_76705_, int p_76706_) {
      if (p_76705_ == 0 && p_76706_ == 0) {
         return 1;
      } else {
         return p_76704_.m_5826_(10) == 0 ? 1 : 0;
      }
   }
}