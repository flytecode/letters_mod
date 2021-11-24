package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class BastionPieces {
   public static final StructureTemplatePool f_126673_ = Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/starts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/units/air_base", ProcessorLists.f_127221_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/hoglin_stable/air_base", ProcessorLists.f_127221_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/treasure/big_air_full", ProcessorLists.f_127221_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/starting_pieces/entrance_base", ProcessorLists.f_127221_), 1)), StructureTemplatePool.Projection.RIGID));

   public static void m_126675_() {
      BastionHousingUnitsPools.m_126672_();
      BastionHoglinStablePools.m_126591_();
      BastionTreasureRoomPools.m_126679_();
      BastionBridgePools.m_126589_();
      BastionSharedPools.m_126677_();
   }
}