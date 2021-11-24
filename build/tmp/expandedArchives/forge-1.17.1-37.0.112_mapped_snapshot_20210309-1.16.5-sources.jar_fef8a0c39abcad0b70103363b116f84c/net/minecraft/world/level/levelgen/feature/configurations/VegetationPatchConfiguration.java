package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class VegetationPatchConfiguration implements FeatureConfiguration {
   public static final Codec<VegetationPatchConfiguration> f_161280_ = RecordCodecBuilder.create((p_161304_) -> {
      return p_161304_.group(ResourceLocation.f_135803_.fieldOf("replaceable").forGetter((p_161324_) -> {
         return p_161324_.f_161281_;
      }), BlockStateProvider.f_68747_.fieldOf("ground_state").forGetter((p_161322_) -> {
         return p_161322_.f_161282_;
      }), ConfiguredFeature.f_65374_.fieldOf("vegetation_feature").forGetter((p_161320_) -> {
         return p_161320_.f_161283_;
      }), CaveSurface.f_162094_.fieldOf("surface").forGetter((p_161318_) -> {
         return p_161318_.f_161284_;
      }), IntProvider.m_146545_(1, 128).fieldOf("depth").forGetter((p_161316_) -> {
         return p_161316_.f_161285_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter((p_161314_) -> {
         return p_161314_.f_161286_;
      }), Codec.intRange(1, 256).fieldOf("vertical_range").forGetter((p_161312_) -> {
         return p_161312_.f_161287_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter((p_161310_) -> {
         return p_161310_.f_161288_;
      }), IntProvider.f_146531_.fieldOf("xz_radius").forGetter((p_161308_) -> {
         return p_161308_.f_161289_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((p_161306_) -> {
         return p_161306_.f_161290_;
      })).apply(p_161304_, VegetationPatchConfiguration::new);
   });
   public final ResourceLocation f_161281_;
   public final BlockStateProvider f_161282_;
   public final Supplier<ConfiguredFeature<?, ?>> f_161283_;
   public final CaveSurface f_161284_;
   public final IntProvider f_161285_;
   public final float f_161286_;
   public final int f_161287_;
   public final float f_161288_;
   public final IntProvider f_161289_;
   public final float f_161290_;

   public VegetationPatchConfiguration(ResourceLocation p_161293_, BlockStateProvider p_161294_, Supplier<ConfiguredFeature<?, ?>> p_161295_, CaveSurface p_161296_, IntProvider p_161297_, float p_161298_, int p_161299_, float p_161300_, IntProvider p_161301_, float p_161302_) {
      this.f_161281_ = p_161293_;
      this.f_161282_ = p_161294_;
      this.f_161283_ = p_161295_;
      this.f_161284_ = p_161296_;
      this.f_161285_ = p_161297_;
      this.f_161286_ = p_161298_;
      this.f_161287_ = p_161299_;
      this.f_161288_ = p_161300_;
      this.f_161289_ = p_161301_;
      this.f_161290_ = p_161302_;
   }
}