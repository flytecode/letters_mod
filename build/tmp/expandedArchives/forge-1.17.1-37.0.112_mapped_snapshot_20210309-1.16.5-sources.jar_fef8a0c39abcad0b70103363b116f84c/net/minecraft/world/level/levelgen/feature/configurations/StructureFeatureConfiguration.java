package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;

public class StructureFeatureConfiguration {
   public static final Codec<StructureFeatureConfiguration> f_68162_ = RecordCodecBuilder.<StructureFeatureConfiguration>create((p_68175_) -> {
      return p_68175_.group(Codec.intRange(0, 4096).fieldOf("spacing").forGetter((p_161211_) -> {
         return p_161211_.f_68163_;
      }), Codec.intRange(0, 4096).fieldOf("separation").forGetter((p_161209_) -> {
         return p_161209_.f_68164_;
      }), ExtraCodecs.f_144628_.fieldOf("salt").forGetter((p_161207_) -> {
         return p_161207_.f_68165_;
      })).apply(p_68175_, StructureFeatureConfiguration::new);
   }).comapFlatMap((p_68173_) -> {
      return p_68173_.f_68163_ <= p_68173_.f_68164_ ? DataResult.error("Spacing has to be smaller than separation") : DataResult.success(p_68173_);
   }, Function.identity());
   private final int f_68163_;
   private final int f_68164_;
   private final int f_68165_;

   public StructureFeatureConfiguration(int p_68168_, int p_68169_, int p_68170_) {
      this.f_68163_ = p_68168_;
      this.f_68164_ = p_68169_;
      this.f_68165_ = p_68170_;
   }

   public int m_68171_() {
      return this.f_68163_;
   }

   public int m_68176_() {
      return this.f_68164_;
   }

   public int m_68179_() {
      return this.f_68165_;
   }
}