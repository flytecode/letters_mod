package net.minecraft.world.entity.npc;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class VillagerType {
   public static final VillagerType f_35819_ = m_35831_("desert");
   public static final VillagerType f_35820_ = m_35831_("jungle");
   public static final VillagerType f_35821_ = m_35831_("plains");
   public static final VillagerType f_35822_ = m_35831_("savanna");
   public static final VillagerType f_35823_ = m_35831_("snow");
   public static final VillagerType f_35824_ = m_35831_("swamp");
   public static final VillagerType f_35825_ = m_35831_("taiga");
   private final String f_35826_;
   private static final Map<ResourceKey<Biome>, VillagerType> f_35827_ = Util.m_137469_(Maps.newHashMap(), (p_35834_) -> {
      p_35834_.put(Biomes.f_48159_, f_35819_);
      p_35834_.put(Biomes.f_48161_, f_35819_);
      p_35834_.put(Biomes.f_48203_, f_35819_);
      p_35834_.put(Biomes.f_48218_, f_35819_);
      p_35834_.put(Biomes.f_48177_, f_35819_);
      p_35834_.put(Biomes.f_48194_, f_35819_);
      p_35834_.put(Biomes.f_48196_, f_35819_);
      p_35834_.put(Biomes.f_48195_, f_35819_);
      p_35834_.put(Biomes.f_48160_, f_35819_);
      p_35834_.put(Biomes.f_48197_, f_35820_);
      p_35834_.put(Biomes.f_48198_, f_35820_);
      p_35834_.put(Biomes.f_48222_, f_35820_);
      p_35834_.put(Biomes.f_48224_, f_35820_);
      p_35834_.put(Biomes.f_48223_, f_35820_);
      p_35834_.put(Biomes.f_48183_, f_35820_);
      p_35834_.put(Biomes.f_48184_, f_35820_);
      p_35834_.put(Biomes.f_48158_, f_35822_);
      p_35834_.put(Biomes.f_48157_, f_35822_);
      p_35834_.put(Biomes.f_48192_, f_35822_);
      p_35834_.put(Biomes.f_48193_, f_35822_);
      p_35834_.put(Biomes.f_48172_, f_35823_);
      p_35834_.put(Biomes.f_48211_, f_35823_);
      p_35834_.put(Biomes.f_48212_, f_35823_);
      p_35834_.put(Biomes.f_48182_, f_35823_);
      p_35834_.put(Biomes.f_48148_, f_35823_);
      p_35834_.put(Biomes.f_48214_, f_35823_);
      p_35834_.put(Biomes.f_48152_, f_35823_);
      p_35834_.put(Biomes.f_48153_, f_35823_);
      p_35834_.put(Biomes.f_48188_, f_35823_);
      p_35834_.put(Biomes.f_48213_, f_35823_);
      p_35834_.put(Biomes.f_48207_, f_35824_);
      p_35834_.put(Biomes.f_48181_, f_35824_);
      p_35834_.put(Biomes.f_48189_, f_35825_);
      p_35834_.put(Biomes.f_48190_, f_35825_);
      p_35834_.put(Biomes.f_48154_, f_35825_);
      p_35834_.put(Biomes.f_48155_, f_35825_);
      p_35834_.put(Biomes.f_48178_, f_35825_);
      p_35834_.put(Biomes.f_48191_, f_35825_);
      p_35834_.put(Biomes.f_48221_, f_35825_);
      p_35834_.put(Biomes.f_48204_, f_35825_);
      p_35834_.put(Biomes.f_48206_, f_35825_);
      p_35834_.put(Biomes.f_48220_, f_35825_);
      p_35834_.put(Biomes.f_48180_, f_35825_);
      p_35834_.put(Biomes.f_48156_, f_35825_);
   });

   private VillagerType(String p_35830_) {
      this.f_35826_ = p_35830_;
   }

   public String toString() {
      return this.f_35826_;
   }

   private static VillagerType m_35831_(String p_35832_) {
      return Registry.m_122965_(Registry.f_122868_, new ResourceLocation(p_35832_), new VillagerType(p_35832_));
   }

   public static VillagerType m_35835_(Optional<ResourceKey<Biome>> p_35836_) {
      return p_35836_.flatMap((p_35838_) -> {
         return Optional.ofNullable(f_35827_.get(p_35838_));
      }).orElse(f_35821_);
   }
}