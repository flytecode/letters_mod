package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class BastionSharedPools {
   public static void m_126677_() {
   }

   static {
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/mobs/piglin"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69216_("bastion/mobs/melee_piglin"), 1), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/sword_piglin"), 4), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/crossbow_piglin"), 4), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/empty"), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/mobs/hoglin"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69216_("bastion/mobs/hoglin"), 2), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/empty"), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/blocks/gold"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69216_("bastion/blocks/air"), 3), Pair.of(StructurePoolElement.m_69216_("bastion/blocks/gold"), 1)), StructureTemplatePool.Projection.RIGID));
      Pools.m_127190_(new StructureTemplatePool(new ResourceLocation("bastion/mobs/piglin_melee"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.m_69216_("bastion/mobs/melee_piglin_always"), 1), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/melee_piglin"), 5), Pair.of(StructurePoolElement.m_69216_("bastion/mobs/sword_piglin"), 1)), StructureTemplatePool.Projection.RIGID));
   }
}