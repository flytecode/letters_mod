package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class RootSystemConfiguration implements FeatureConfiguration {
   public static final Codec<RootSystemConfiguration> f_161101_ = RecordCodecBuilder.create((p_161129_) -> {
      return p_161129_.group(ConfiguredFeature.f_65374_.fieldOf("feature").forGetter((p_161153_) -> {
         return p_161153_.f_161102_;
      }), Codec.intRange(1, 64).fieldOf("required_vertical_space_for_tree").forGetter((p_161151_) -> {
         return p_161151_.f_161103_;
      }), Codec.intRange(1, 64).fieldOf("root_radius").forGetter((p_161149_) -> {
         return p_161149_.f_161104_;
      }), ResourceLocation.f_135803_.fieldOf("root_replaceable").forGetter((p_161147_) -> {
         return p_161147_.f_161105_;
      }), BlockStateProvider.f_68747_.fieldOf("root_state_provider").forGetter((p_161145_) -> {
         return p_161145_.f_161106_;
      }), Codec.intRange(1, 256).fieldOf("root_placement_attempts").forGetter((p_161143_) -> {
         return p_161143_.f_161107_;
      }), Codec.intRange(1, 4096).fieldOf("root_column_max_height").forGetter((p_161141_) -> {
         return p_161141_.f_161108_;
      }), Codec.intRange(1, 64).fieldOf("hanging_root_radius").forGetter((p_161139_) -> {
         return p_161139_.f_161109_;
      }), Codec.intRange(0, 16).fieldOf("hanging_roots_vertical_span").forGetter((p_161137_) -> {
         return p_161137_.f_161110_;
      }), BlockStateProvider.f_68747_.fieldOf("hanging_root_state_provider").forGetter((p_161135_) -> {
         return p_161135_.f_161111_;
      }), Codec.intRange(1, 256).fieldOf("hanging_root_placement_attempts").forGetter((p_161133_) -> {
         return p_161133_.f_161112_;
      }), Codec.intRange(1, 64).fieldOf("allowed_vertical_water_for_tree").forGetter((p_161131_) -> {
         return p_161131_.f_161113_;
      })).apply(p_161129_, RootSystemConfiguration::new);
   });
   public final Supplier<ConfiguredFeature<?, ?>> f_161102_;
   public final int f_161103_;
   public final int f_161104_;
   public final ResourceLocation f_161105_;
   public final BlockStateProvider f_161106_;
   public final int f_161107_;
   public final int f_161108_;
   public final int f_161109_;
   public final int f_161110_;
   public final BlockStateProvider f_161111_;
   public final int f_161112_;
   public final int f_161113_;

   public RootSystemConfiguration(Supplier<ConfiguredFeature<?, ?>> p_161116_, int p_161117_, int p_161118_, ResourceLocation p_161119_, BlockStateProvider p_161120_, int p_161121_, int p_161122_, int p_161123_, int p_161124_, BlockStateProvider p_161125_, int p_161126_, int p_161127_) {
      this.f_161102_ = p_161116_;
      this.f_161103_ = p_161117_;
      this.f_161104_ = p_161118_;
      this.f_161105_ = p_161119_;
      this.f_161106_ = p_161120_;
      this.f_161107_ = p_161121_;
      this.f_161108_ = p_161122_;
      this.f_161109_ = p_161123_;
      this.f_161110_ = p_161124_;
      this.f_161111_ = p_161125_;
      this.f_161112_ = p_161126_;
      this.f_161113_ = p_161127_;
   }
}