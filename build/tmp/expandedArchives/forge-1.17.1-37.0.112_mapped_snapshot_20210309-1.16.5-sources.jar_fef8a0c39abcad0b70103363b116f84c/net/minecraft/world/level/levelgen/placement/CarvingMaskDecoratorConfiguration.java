package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class CarvingMaskDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<CarvingMaskDecoratorConfiguration> f_70441_ = GenerationStep.Carving.f_64194_.fieldOf("step").xmap(CarvingMaskDecoratorConfiguration::new, (p_162078_) -> {
      return p_162078_.f_70442_;
   }).codec();
   protected final GenerationStep.Carving f_70442_;

   public CarvingMaskDecoratorConfiguration(GenerationStep.Carving p_162076_) {
      this.f_70442_ = p_162076_;
   }
}