package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public interface FeatureAccess {
   @Nullable
   StructureStart<?> m_7253_(StructureFeature<?> p_62632_);

   void m_8078_(StructureFeature<?> p_62635_, StructureStart<?> p_62636_);

   LongSet m_6705_(StructureFeature<?> p_62637_);

   void m_6306_(StructureFeature<?> p_62633_, long p_62634_);

   Map<StructureFeature<?>, LongSet> m_7049_();

   void m_7946_(Map<StructureFeature<?>, LongSet> p_62638_);
}