package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoliageColorReloadListener extends SimplePreparableReloadListener<int[]> {
   private static final ResourceLocation f_118656_ = new ResourceLocation("textures/colormap/foliage.png");

   protected int[] m_5944_(ResourceManager p_118660_, ProfilerFiller p_118661_) {
      try {
         return LegacyStuffWrapper.m_118726_(p_118660_, f_118656_);
      } catch (IOException ioexception) {
         throw new IllegalStateException("Failed to load foliage color texture", ioexception);
      }
   }

   protected void m_5787_(int[] p_118667_, ResourceManager p_118668_, ProfilerFiller p_118669_) {
      FoliageColor.m_46110_(p_118667_);
   }
}