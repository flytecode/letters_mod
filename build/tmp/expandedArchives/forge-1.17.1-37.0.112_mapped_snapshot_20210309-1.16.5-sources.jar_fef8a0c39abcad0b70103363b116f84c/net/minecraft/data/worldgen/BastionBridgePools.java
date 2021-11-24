package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class BastionBridgePools {
   public static void m_126589_() {
   }

   static {
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/starting_pieces"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/starting_pieces/entrance", ProcessorLists.f_127223_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/starting_pieces/entrance_face", ProcessorLists.f_127221_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/bridge_pieces"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/bridge_pieces/bridge", ProcessorLists.f_127192_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/legs"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/legs/leg_0", ProcessorLists.f_127221_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/legs/leg_1", ProcessorLists.f_127221_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/walls"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/walls/wall_base_0", ProcessorLists.f_127222_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/walls/wall_base_1", ProcessorLists.f_127222_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/ramparts"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/ramparts/rampart_0", ProcessorLists.f_127222_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/ramparts/rampart_1", ProcessorLists.f_127222_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/rampart_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/rampart_plates/plate_0", ProcessorLists.f_127222_), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/bridge/connectors"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69221_("bastion/bridge/connectors/back_bridge_top", ProcessorLists.f_127221_), 1), Pair.of(StructurePoolElement.m_69221_("bastion/bridge/connectors/back_bridge_bottom", ProcessorLists.f_127221_), 1)), StructureTemplatePool.Projection.RIGID));
   }
}