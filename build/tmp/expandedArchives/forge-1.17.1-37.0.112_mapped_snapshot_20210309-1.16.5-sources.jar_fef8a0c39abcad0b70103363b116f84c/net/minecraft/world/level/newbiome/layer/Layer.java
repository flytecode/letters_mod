package net.minecraft.world.level.newbiome.layer;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Layer {
   private static final Logger f_76710_ = LogManager.getLogger();
   private final LazyArea f_76711_;

   public Layer(AreaFactory<LazyArea> p_76714_) {
      this.f_76711_ = p_76714_.m_76488_();
   }

   public Biome m_76715_(Registry<Biome> p_76716_, int p_76717_, int p_76718_) {
      int i = this.f_76711_.m_7929_(p_76717_, p_76718_);
      ResourceKey<Biome> resourcekey = Biomes.m_127325_(i);
      if (resourcekey == null) {
         throw new IllegalStateException("Unknown biome id emitted by layers: " + i);
      } else {
         Biome biome = p_76716_.m_6246_(resourcekey);
         if (biome == null) {
            Util.m_143785_("Unknown biome id: " + i);
            return p_76716_.m_6246_(Biomes.m_127325_(0));
         } else {
            return biome;
         }
      }
   }
}