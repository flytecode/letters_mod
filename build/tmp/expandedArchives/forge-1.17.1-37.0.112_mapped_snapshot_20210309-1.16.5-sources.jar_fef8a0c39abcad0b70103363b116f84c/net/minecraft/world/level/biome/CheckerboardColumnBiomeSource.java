package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;

public class CheckerboardColumnBiomeSource extends BiomeSource {
   public static final Codec<CheckerboardColumnBiomeSource> f_48230_ = RecordCodecBuilder.create((p_48244_) -> {
      return p_48244_.group(Biome.f_47432_.fieldOf("biomes").forGetter((p_151790_) -> {
         return p_151790_.f_48231_;
      }), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter((p_151788_) -> {
         return p_151788_.f_48233_;
      })).apply(p_48244_, CheckerboardColumnBiomeSource::new);
   });
   private final List<Supplier<Biome>> f_48231_;
   private final int f_48232_;
   private final int f_48233_;

   public CheckerboardColumnBiomeSource(List<Supplier<Biome>> p_48236_, int p_48237_) {
      super(p_48236_.stream());
      this.f_48231_ = p_48236_;
      this.f_48232_ = p_48237_ + 2;
      this.f_48233_ = p_48237_;
   }

   protected Codec<? extends BiomeSource> m_5820_() {
      return f_48230_;
   }

   public BiomeSource m_7206_(long p_48240_) {
      return this;
   }

   public Biome m_7158_(int p_48246_, int p_48247_, int p_48248_) {
      return this.f_48231_.get(Math.floorMod((p_48246_ >> this.f_48232_) + (p_48248_ >> this.f_48232_), this.f_48231_.size())).get();
   }
}