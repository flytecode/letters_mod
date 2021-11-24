package net.minecraft.world.level.entity;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.ChunkPos;

@FunctionalInterface
public interface ChunkStatusUpdateListener {
   void m_156794_(ChunkPos p_156795_, ChunkHolder.FullChunkStatus p_156796_);
}