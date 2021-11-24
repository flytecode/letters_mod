package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;

public class SpikeConfiguration implements FeatureConfiguration {
   public static final Codec<SpikeConfiguration> f_68099_ = RecordCodecBuilder.create((p_68115_) -> {
      return p_68115_.group(Codec.BOOL.fieldOf("crystal_invulnerable").orElse(false).forGetter((p_161195_) -> {
         return p_161195_.f_68100_;
      }), SpikeFeature.EndSpike.f_66872_.listOf().fieldOf("spikes").forGetter((p_161193_) -> {
         return p_161193_.f_68101_;
      }), BlockPos.f_121852_.optionalFieldOf("crystal_beam_target").forGetter((p_161191_) -> {
         return Optional.ofNullable(p_161191_.f_68102_);
      })).apply(p_68115_, SpikeConfiguration::new);
   });
   private final boolean f_68100_;
   private final List<SpikeFeature.EndSpike> f_68101_;
   @Nullable
   private final BlockPos f_68102_;

   public SpikeConfiguration(boolean p_68105_, List<SpikeFeature.EndSpike> p_68106_, @Nullable BlockPos p_68107_) {
      this(p_68105_, p_68106_, Optional.ofNullable(p_68107_));
   }

   private SpikeConfiguration(boolean p_68109_, List<SpikeFeature.EndSpike> p_68110_, Optional<BlockPos> p_68111_) {
      this.f_68100_ = p_68109_;
      this.f_68101_ = p_68110_;
      this.f_68102_ = p_68111_.orElse((BlockPos)null);
   }

   public boolean m_68116_() {
      return this.f_68100_;
   }

   public List<SpikeFeature.EndSpike> m_68119_() {
      return this.f_68101_;
   }

   @Nullable
   public BlockPos m_68122_() {
      return this.f_68102_;
   }
}