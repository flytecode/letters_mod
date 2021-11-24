package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.newbiome.layer.Layer;
import net.minecraft.world.level.newbiome.layer.Layers;

public class OverworldBiomeSource extends BiomeSource {
   public static final Codec<OverworldBiomeSource> f_48581_ = RecordCodecBuilder.create((p_48600_) -> {
      return p_48600_.group(Codec.LONG.fieldOf("seed").stable().forGetter((p_151883_) -> {
         return p_151883_.f_48584_;
      }), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", Boolean.valueOf(false), Lifecycle.stable()).forGetter((p_151881_) -> {
         return p_151881_.f_48585_;
      }), Codec.BOOL.fieldOf("large_biomes").orElse(false).stable().forGetter((p_151876_) -> {
         return p_151876_.f_48586_;
      }), RegistryLookupCodec.m_135622_(Registry.f_122885_).forGetter((p_151874_) -> {
         return p_151874_.f_48587_;
      })).apply(p_48600_, p_48600_.stable(OverworldBiomeSource::new));
   });
   private final Layer f_48582_;
   private static final List<ResourceKey<Biome>> f_48583_ = ImmutableList.of(Biomes.f_48174_, Biomes.f_48202_, Biomes.f_48203_, Biomes.f_48204_, Biomes.f_48205_, Biomes.f_48206_, Biomes.f_48207_, Biomes.f_48208_, Biomes.f_48211_, Biomes.f_48212_, Biomes.f_48213_, Biomes.f_48214_, Biomes.f_48215_, Biomes.f_48216_, Biomes.f_48217_, Biomes.f_48218_, Biomes.f_48219_, Biomes.f_48220_, Biomes.f_48221_, Biomes.f_48222_, Biomes.f_48223_, Biomes.f_48224_, Biomes.f_48225_, Biomes.f_48226_, Biomes.f_48148_, Biomes.f_48149_, Biomes.f_48150_, Biomes.f_48151_, Biomes.f_48152_, Biomes.f_48153_, Biomes.f_48154_, Biomes.f_48155_, Biomes.f_48156_, Biomes.f_48157_, Biomes.f_48158_, Biomes.f_48159_, Biomes.f_48160_, Biomes.f_48161_, Biomes.f_48166_, Biomes.f_48167_, Biomes.f_48168_, Biomes.f_48169_, Biomes.f_48170_, Biomes.f_48171_, Biomes.f_48172_, Biomes.f_48176_, Biomes.f_48177_, Biomes.f_48178_, Biomes.f_48179_, Biomes.f_48180_, Biomes.f_48181_, Biomes.f_48182_, Biomes.f_48183_, Biomes.f_48184_, Biomes.f_48185_, Biomes.f_48186_, Biomes.f_48187_, Biomes.f_48188_, Biomes.f_48189_, Biomes.f_48190_, Biomes.f_48191_, Biomes.f_48192_, Biomes.f_48193_, Biomes.f_48194_, Biomes.f_48195_, Biomes.f_48196_);
   private final long f_48584_;
   private final boolean f_48585_;
   private final boolean f_48586_;
   private final Registry<Biome> f_48587_;

   public OverworldBiomeSource(long p_48590_, boolean p_48591_, boolean p_48592_, Registry<Biome> p_48593_) {
      super(java.util.stream.Stream.concat(f_48583_.stream(), net.minecraftforge.common.BiomeManager.getAdditionalOverworldBiomes().stream()).map((p_48603_) -> {
         return () -> {
            return p_48593_.m_123013_(p_48603_);
         };
      }));
      this.f_48584_ = p_48590_;
      this.f_48585_ = p_48591_;
      this.f_48586_ = p_48592_;
      this.f_48587_ = p_48593_;
      this.f_48582_ = Layers.m_76735_(p_48590_, p_48591_, p_48592_ ? 6 : 4, 4);
   }

   protected Codec<? extends BiomeSource> m_5820_() {
      return f_48581_;
   }

   public BiomeSource m_7206_(long p_48596_) {
      return new OverworldBiomeSource(p_48596_, this.f_48585_, this.f_48586_, this.f_48587_);
   }

   public Biome m_7158_(int p_48605_, int p_48606_, int p_48607_) {
      return this.f_48582_.m_76715_(this.f_48587_, p_48605_, p_48607_);
   }
}
