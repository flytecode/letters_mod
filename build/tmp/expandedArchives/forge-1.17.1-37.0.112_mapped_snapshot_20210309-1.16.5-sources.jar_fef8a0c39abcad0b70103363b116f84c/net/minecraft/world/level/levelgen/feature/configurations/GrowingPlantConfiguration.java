package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GrowingPlantConfiguration implements FeatureConfiguration {
   public static final Codec<GrowingPlantConfiguration> f_160904_ = RecordCodecBuilder.create((p_160918_) -> {
      return p_160918_.group(SimpleWeightedRandomList.m_146264_(IntProvider.f_146531_).fieldOf("height_distribution").forGetter((p_160928_) -> {
         return p_160928_.f_160905_;
      }), Direction.f_175356_.fieldOf("direction").forGetter((p_160926_) -> {
         return p_160926_.f_160906_;
      }), BlockStateProvider.f_68747_.fieldOf("body_provider").forGetter((p_160924_) -> {
         return p_160924_.f_160907_;
      }), BlockStateProvider.f_68747_.fieldOf("head_provider").forGetter((p_160922_) -> {
         return p_160922_.f_160908_;
      }), Codec.BOOL.fieldOf("allow_water").forGetter((p_160920_) -> {
         return p_160920_.f_160909_;
      })).apply(p_160918_, GrowingPlantConfiguration::new);
   });
   public final SimpleWeightedRandomList<IntProvider> f_160905_;
   public final Direction f_160906_;
   public final BlockStateProvider f_160907_;
   public final BlockStateProvider f_160908_;
   public final boolean f_160909_;

   public GrowingPlantConfiguration(SimpleWeightedRandomList<IntProvider> p_160912_, Direction p_160913_, BlockStateProvider p_160914_, BlockStateProvider p_160915_, boolean p_160916_) {
      this.f_160905_ = p_160912_;
      this.f_160906_ = p_160913_;
      this.f_160907_ = p_160914_;
      this.f_160908_ = p_160915_;
      this.f_160909_ = p_160916_;
   }
}