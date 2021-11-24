package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class Pools {
   public static final ResourceKey<StructureTemplatePool> f_127186_ = ResourceKey.m_135785_(Registry.f_122884_, new ResourceLocation("empty"));
   private static final StructureTemplatePool f_127187_ = m_127190_(new StructureTemplatePool(f_127186_.m_135782_(), f_127186_.m_135782_(), ImmutableList.of(), StructureTemplatePool.Projection.RIGID));

   public static StructureTemplatePool m_127190_(StructureTemplatePool p_127191_) {
      return BuiltinRegistries.m_123880_(BuiltinRegistries.f_123864_, p_127191_.m_69275_(), p_127191_);
   }

   public static StructureTemplatePool m_127189_() {
      BastionPieces.m_126675_();
      PillagerOutpostPools.m_127182_();
      VillagePools.m_127306_();
      return f_127187_;
   }

   static {
      m_127189_();
   }
}