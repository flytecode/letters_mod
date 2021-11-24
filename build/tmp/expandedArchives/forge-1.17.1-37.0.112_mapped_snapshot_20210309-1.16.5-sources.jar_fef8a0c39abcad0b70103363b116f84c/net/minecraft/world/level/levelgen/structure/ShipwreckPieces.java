package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ShipwreckPieces {
   static final BlockPos f_72809_ = new BlockPos(4, 0, 15);
   private static final ResourceLocation[] f_72810_ = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
   private static final ResourceLocation[] f_72811_ = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};

   public static void m_163200_(StructureManager p_163201_, BlockPos p_163202_, Rotation p_163203_, StructurePieceAccessor p_163204_, Random p_163205_, ShipwreckConfiguration p_163206_) {
      ResourceLocation resourcelocation = Util.m_137545_(p_163206_.f_68062_ ? f_72810_ : f_72811_, p_163205_);
      p_163204_.m_142679_(new ShipwreckPieces.ShipwreckPiece(p_163201_, resourcelocation, p_163202_, p_163203_, p_163206_.f_68062_));
   }

   public static class ShipwreckPiece extends TemplateStructurePiece {
      private final boolean f_72823_;

      public ShipwreckPiece(StructureManager p_72828_, ResourceLocation p_72829_, BlockPos p_72830_, Rotation p_72831_, boolean p_72832_) {
         super(StructurePieceType.f_67134_, 0, p_72828_, p_72829_, p_72829_.toString(), m_163213_(p_72831_), p_72830_);
         this.f_72823_ = p_72832_;
      }

      public ShipwreckPiece(ServerLevel p_163208_, CompoundTag p_163209_) {
         super(StructurePieceType.f_67134_, p_163209_, p_163208_, (p_163217_) -> {
            return m_163213_(Rotation.valueOf(p_163209_.m_128461_("Rot")));
         });
         this.f_72823_ = p_163209_.m_128471_("isBeached");
      }

      protected void m_142347_(ServerLevel p_163211_, CompoundTag p_163212_) {
         super.m_142347_(p_163211_, p_163212_);
         p_163212_.m_128379_("isBeached", this.f_72823_);
         p_163212_.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      private static StructurePlaceSettings m_163213_(Rotation p_163214_) {
         return (new StructurePlaceSettings()).m_74379_(p_163214_).m_74377_(Mirror.NONE).m_74385_(ShipwreckPieces.f_72809_).m_74383_(BlockIgnoreProcessor.f_74048_);
      }

      protected void m_7756_(String p_72844_, BlockPos p_72845_, ServerLevelAccessor p_72846_, Random p_72847_, BoundingBox p_72848_) {
         if ("map_chest".equals(p_72844_)) {
            RandomizableContainerBlockEntity.m_59620_(p_72846_, p_72847_, p_72845_.m_7495_(), BuiltInLootTables.f_78693_);
         } else if ("treasure_chest".equals(p_72844_)) {
            RandomizableContainerBlockEntity.m_59620_(p_72846_, p_72847_, p_72845_.m_7495_(), BuiltInLootTables.f_78695_);
         } else if ("supply_chest".equals(p_72844_)) {
            RandomizableContainerBlockEntity.m_59620_(p_72846_, p_72847_, p_72845_.m_7495_(), BuiltInLootTables.f_78694_);
         }

      }

      public boolean m_7832_(WorldGenLevel p_72834_, StructureFeatureManager p_72835_, ChunkGenerator p_72836_, Random p_72837_, BoundingBox p_72838_, ChunkPos p_72839_, BlockPos p_72840_) {
         int i = p_72834_.m_151558_();
         int j = 0;
         Vec3i vec3i = this.f_73656_.m_163801_();
         Heightmap.Types heightmap$types = this.f_72823_ ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
         int k = vec3i.m_123341_() * vec3i.m_123343_();
         if (k == 0) {
            j = p_72834_.m_6924_(heightmap$types, this.f_73658_.m_123341_(), this.f_73658_.m_123343_());
         } else {
            BlockPos blockpos = this.f_73658_.m_142082_(vec3i.m_123341_() - 1, 0, vec3i.m_123343_() - 1);

            for(BlockPos blockpos1 : BlockPos.m_121940_(this.f_73658_, blockpos)) {
               int l = p_72834_.m_6924_(heightmap$types, blockpos1.m_123341_(), blockpos1.m_123343_());
               j += l;
               i = Math.min(i, l);
            }

            j = j / k;
         }

         int i1 = this.f_72823_ ? i - vec3i.m_123342_() / 2 - p_72837_.nextInt(3) : j;
         this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), i1, this.f_73658_.m_123343_());
         return super.m_7832_(p_72834_, p_72835_, p_72836_, p_72837_, p_72838_, p_72839_, p_72840_);
      }
   }
}