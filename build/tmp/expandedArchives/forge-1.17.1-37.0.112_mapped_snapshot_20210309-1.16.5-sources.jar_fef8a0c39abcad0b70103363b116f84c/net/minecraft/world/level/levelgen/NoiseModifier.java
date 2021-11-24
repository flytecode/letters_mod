package net.minecraft.world.level.levelgen;

@FunctionalInterface
public interface NoiseModifier {
   NoiseModifier f_158626_ = (p_158629_, p_158630_, p_158631_, p_158632_) -> {
      return p_158629_;
   };

   double m_142124_(double p_158633_, int p_158634_, int p_158635_, int p_158636_);
}