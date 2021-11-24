package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class NetherFossilPieces {
   private static final ResourceLocation[] f_72056_ = new ResourceLocation[]{new ResourceLocation("nether_fossils/fossil_1"), new ResourceLocation("nether_fossils/fossil_2"), new ResourceLocation("nether_fossils/fossil_3"), new ResourceLocation("nether_fossils/fossil_4"), new ResourceLocation("nether_fossils/fossil_5"), new ResourceLocation("nether_fossils/fossil_6"), new ResourceLocation("nether_fossils/fossil_7"), new ResourceLocation("nether_fossils/fossil_8"), new ResourceLocation("nether_fossils/fossil_9"), new ResourceLocation("nether_fossils/fossil_10"), new ResourceLocation("nether_fossils/fossil_11"), new ResourceLocation("nether_fossils/fossil_12"), new ResourceLocation("nether_fossils/fossil_13"), new ResourceLocation("nether_fossils/fossil_14")};

   public static void m_162965_(StructureManager p_162966_, StructurePieceAccessor p_162967_, Random p_162968_, BlockPos p_162969_) {
      Rotation rotation = Rotation.m_55956_(p_162968_);
      p_162967_.m_142679_(new NetherFossilPieces.NetherFossilPiece(p_162966_, Util.m_137545_(f_72056_, p_162968_), p_162969_, rotation));
   }

   public static class NetherFossilPiece extends TemplateStructurePiece {
      public NetherFossilPiece(StructureManager p_72069_, ResourceLocation p_72070_, BlockPos p_72071_, Rotation p_72072_) {
         super(StructurePieceType.f_67135_, 0, p_72069_, p_72070_, p_72070_.toString(), m_162976_(p_72072_), p_72071_);
      }

      public NetherFossilPiece(ServerLevel p_162971_, CompoundTag p_162972_) {
         super(StructurePieceType.f_67135_, p_162972_, p_162971_, (p_162980_) -> {
            return m_162976_(Rotation.valueOf(p_162972_.m_128461_("Rot")));
         });
      }

      private static StructurePlaceSettings m_162976_(Rotation p_162977_) {
         return (new StructurePlaceSettings()).m_74379_(p_162977_).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74048_);
      }

      protected void m_142347_(ServerLevel p_162974_, CompoundTag p_162975_) {
         super.m_142347_(p_162974_, p_162975_);
         p_162975_.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_7756_(String p_72084_, BlockPos p_72085_, ServerLevelAccessor p_72086_, Random p_72087_, BoundingBox p_72088_) {
      }

      public boolean m_7832_(WorldGenLevel p_72074_, StructureFeatureManager p_72075_, ChunkGenerator p_72076_, Random p_72077_, BoundingBox p_72078_, ChunkPos p_72079_, BlockPos p_72080_) {
         p_72078_.m_162386_(this.f_73656_.m_74633_(this.f_73657_, this.f_73658_));
         return super.m_7832_(p_72074_, p_72075_, p_72076_, p_72077_, p_72078_, p_72079_, p_72080_);
      }
   }
}