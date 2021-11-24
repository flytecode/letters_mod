package net.minecraft.server.level.progress;

import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public interface ChunkProgressListener {
   void m_7647_(ChunkPos p_9617_);

   void m_5511_(ChunkPos p_9618_, @Nullable ChunkStatus p_9619_);

   void m_142611_();

   void m_7646_();
}