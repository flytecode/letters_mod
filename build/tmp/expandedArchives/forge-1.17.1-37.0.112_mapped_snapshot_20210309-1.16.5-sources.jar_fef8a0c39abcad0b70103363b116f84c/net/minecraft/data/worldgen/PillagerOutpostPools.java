package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class PillagerOutpostPools {
   public static final StructureTemplatePool f_127180_ = Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/base_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69192_("pillager_outpost/base_plate"), 1)), StructureTemplatePool.Projection.RIGID));

   public static void m_127182_() {
   }

   static {
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/towers"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69204_(ImmutableList.of(StructurePoolElement.m_69192_("pillager_outpost/watchtower"), StructurePoolElement.m_69197_("pillager_outpost/watchtower_overgrown", ProcessorLists.f_127215_))), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/feature_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_plate"), 1)), StructureTemplatePool.Projection.TERRAIN_MATCHING));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("pillager_outpost/features"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_cage1"), 1), Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_cage2"), 1), Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_logs"), 1), Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_tent1"), 1), Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_tent2"), 1), Pair.of(StructurePoolElement.m_69192_("pillager_outpost/feature_targets"), 1), Pair.of(StructurePoolElement.m_69232_(), 6)), StructureTemplatePool.Projection.RIGID));
   }
}