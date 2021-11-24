package net.minecraft.world.level.newbiome.layer.traits;

public interface DimensionOffset1Transformer extends DimensionTransformer {
   default int m_6320_(int p_77070_) {
      return p_77070_ - 1;
   }

   default int m_6317_(int p_77072_) {
      return p_77072_ - 1;
   }
}