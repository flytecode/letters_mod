package net.minecraft.core;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockSource extends Position {
   double m_7096_();

   double m_7098_();

   double m_7094_();

   BlockPos m_7961_();

   BlockState m_6414_();

   <T extends BlockEntity> T m_8118_();

   ServerLevel m_7727_();
}