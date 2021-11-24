package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class FossilFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<FossilFeatureConfiguration> f_159796_ = RecordCodecBuilder.create((p_159816_) -> {
      return p_159816_.group(ResourceLocation.f_135803_.listOf().fieldOf("fossil_structures").forGetter((p_159830_) -> {
         return p_159830_.f_159797_;
      }), ResourceLocation.f_135803_.listOf().fieldOf("overlay_structures").forGetter((p_159828_) -> {
         return p_159828_.f_159798_;
      }), StructureProcessorType.f_74468_.fieldOf("fossil_processors").forGetter((p_159826_) -> {
         return p_159826_.f_159799_;
      }), StructureProcessorType.f_74468_.fieldOf("overlay_processors").forGetter((p_159822_) -> {
         return p_159822_.f_159800_;
      }), Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter((p_159818_) -> {
         return p_159818_.f_159801_;
      })).apply(p_159816_, FossilFeatureConfiguration::new);
   });
   public final List<ResourceLocation> f_159797_;
   public final List<ResourceLocation> f_159798_;
   public final Supplier<StructureProcessorList> f_159799_;
   public final Supplier<StructureProcessorList> f_159800_;
   public final int f_159801_;

   public FossilFeatureConfiguration(List<ResourceLocation> p_159810_, List<ResourceLocation> p_159811_, Supplier<StructureProcessorList> p_159812_, Supplier<StructureProcessorList> p_159813_, int p_159814_) {
      if (p_159810_.isEmpty()) {
         throw new IllegalArgumentException("Fossil structure lists need at least one entry");
      } else if (p_159810_.size() != p_159811_.size()) {
         throw new IllegalArgumentException("Fossil structure lists must be equal lengths");
      } else {
         this.f_159797_ = p_159810_;
         this.f_159798_ = p_159811_;
         this.f_159799_ = p_159812_;
         this.f_159800_ = p_159813_;
         this.f_159801_ = p_159814_;
      }
   }

   public FossilFeatureConfiguration(List<ResourceLocation> p_159804_, List<ResourceLocation> p_159805_, StructureProcessorList p_159806_, StructureProcessorList p_159807_, int p_159808_) {
      this(p_159804_, p_159805_, () -> {
         return p_159806_;
      }, () -> {
         return p_159807_;
      }, p_159808_);
   }
}