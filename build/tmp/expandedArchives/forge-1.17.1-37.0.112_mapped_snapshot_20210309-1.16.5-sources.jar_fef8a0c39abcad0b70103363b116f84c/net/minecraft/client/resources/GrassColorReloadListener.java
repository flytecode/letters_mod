package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrassColorReloadListener extends SimplePreparableReloadListener<int[]> {
   private static final ResourceLocation f_118673_ = new ResourceLocation("textures/colormap/grass.png");

   protected int[] m_5944_(ResourceManager p_118677_, ProfilerFiller p_118678_) {
      try {
         return LegacyStuffWrapper.m_118726_(p_118677_, f_118673_);
      } catch (IOException ioexception) {
         throw new IllegalStateException("Failed to load grass color texture", ioexception);
      }
   }

   protected void m_5787_(int[] p_118684_, ResourceManager p_118685_, ProfilerFiller p_118686_) {
      GrassColor.m_46418_(p_118684_);
   }
}