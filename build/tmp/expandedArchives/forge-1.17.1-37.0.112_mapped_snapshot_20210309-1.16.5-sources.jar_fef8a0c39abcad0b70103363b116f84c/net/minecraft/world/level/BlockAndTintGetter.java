package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.lighting.LevelLightEngine;

public interface BlockAndTintGetter extends BlockGetter {
   float m_7717_(Direction p_45522_, boolean p_45523_);

   LevelLightEngine m_5518_();

   int m_6171_(BlockPos p_45520_, ColorResolver p_45521_);

   default int m_45517_(LightLayer p_45518_, BlockPos p_45519_) {
      return this.m_5518_().m_75814_(p_45518_).m_7768_(p_45519_);
   }

   default int m_45524_(BlockPos p_45525_, int p_45526_) {
      return this.m_5518_().m_75831_(p_45525_, p_45526_);
   }

   default boolean m_45527_(BlockPos p_45528_) {
      return this.m_45517_(LightLayer.SKY, p_45528_) >= this.m_7469_();
   }
}