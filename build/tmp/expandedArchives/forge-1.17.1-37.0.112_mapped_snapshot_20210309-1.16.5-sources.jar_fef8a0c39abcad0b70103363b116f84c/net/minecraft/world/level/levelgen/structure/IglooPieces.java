package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class IglooPieces {
   public static final int f_162432_ = 90;
   static final ResourceLocation f_71223_ = new ResourceLocation("igloo/top");
   private static final ResourceLocation f_71224_ = new ResourceLocation("igloo/middle");
   private static final ResourceLocation f_71225_ = new ResourceLocation("igloo/bottom");
   static final Map<ResourceLocation, BlockPos> f_71226_ = ImmutableMap.of(f_71223_, new BlockPos(3, 5, 5), f_71224_, new BlockPos(1, 3, 1), f_71225_, new BlockPos(3, 6, 7));
   static final Map<ResourceLocation, BlockPos> f_71227_ = ImmutableMap.of(f_71223_, BlockPos.f_121853_, f_71224_, new BlockPos(2, -3, 4), f_71225_, new BlockPos(0, -3, -2));

   public static void m_162434_(StructureManager p_162435_, BlockPos p_162436_, Rotation p_162437_, StructurePieceAccessor p_162438_, Random p_162439_) {
      if (p_162439_.nextDouble() < 0.5D) {
         int i = p_162439_.nextInt(8) + 4;
         p_162438_.m_142679_(new IglooPieces.IglooPiece(p_162435_, f_71225_, p_162436_, p_162437_, i * 3));

         for(int j = 0; j < i - 1; ++j) {
            p_162438_.m_142679_(new IglooPieces.IglooPiece(p_162435_, f_71224_, p_162436_, p_162437_, j * 3));
         }
      }

      p_162438_.m_142679_(new IglooPieces.IglooPiece(p_162435_, f_71223_, p_162436_, p_162437_, 0));
   }

   public static class IglooPiece extends TemplateStructurePiece {
      public IglooPiece(StructureManager p_71244_, ResourceLocation p_71245_, BlockPos p_71246_, Rotation p_71247_, int p_71248_) {
         super(StructurePieceType.f_67114_, 0, p_71244_, p_71245_, p_71245_.toString(), m_162446_(p_71247_, p_71245_), m_162452_(p_71245_, p_71246_, p_71248_));
      }

      public IglooPiece(ServerLevel p_162441_, CompoundTag p_162442_) {
         super(StructurePieceType.f_67114_, p_162442_, p_162441_, (p_162451_) -> {
            return m_162446_(Rotation.valueOf(p_162442_.m_128461_("Rot")), p_162451_);
         });
      }

      private static StructurePlaceSettings m_162446_(Rotation p_162447_, ResourceLocation p_162448_) {
         return (new StructurePlaceSettings()).m_74379_(p_162447_).m_74377_(Mirror.NONE).m_74385_(IglooPieces.f_71226_.get(p_162448_)).m_74383_(BlockIgnoreProcessor.f_74046_);
      }

      private static BlockPos m_162452_(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
         return p_162454_.m_141952_(IglooPieces.f_71227_.get(p_162453_)).m_6625_(p_162455_);
      }

      protected void m_142347_(ServerLevel p_162444_, CompoundTag p_162445_) {
         super.m_142347_(p_162444_, p_162445_);
         p_162445_.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_7756_(String p_71260_, BlockPos p_71261_, ServerLevelAccessor p_71262_, Random p_71263_, BoundingBox p_71264_) {
         if ("chest".equals(p_71260_)) {
            p_71262_.m_7731_(p_71261_, Blocks.f_50016_.m_49966_(), 3);
            BlockEntity blockentity = p_71262_.m_7702_(p_71261_.m_7495_());
            if (blockentity instanceof ChestBlockEntity) {
               ((ChestBlockEntity)blockentity).m_59626_(BuiltInLootTables.f_78688_, p_71263_.nextLong());
            }

         }
      }

      public boolean m_7832_(WorldGenLevel p_71250_, StructureFeatureManager p_71251_, ChunkGenerator p_71252_, Random p_71253_, BoundingBox p_71254_, ChunkPos p_71255_, BlockPos p_71256_) {
         ResourceLocation resourcelocation = new ResourceLocation(this.f_163658_);
         StructurePlaceSettings structureplacesettings = m_162446_(this.f_73657_.m_74404_(), resourcelocation);
         BlockPos blockpos = IglooPieces.f_71227_.get(resourcelocation);
         BlockPos blockpos1 = this.f_73658_.m_141952_(StructureTemplate.m_74563_(structureplacesettings, new BlockPos(3 - blockpos.m_123341_(), 0, -blockpos.m_123343_())));
         int i = p_71250_.m_6924_(Heightmap.Types.WORLD_SURFACE_WG, blockpos1.m_123341_(), blockpos1.m_123343_());
         BlockPos blockpos2 = this.f_73658_;
         this.f_73658_ = this.f_73658_.m_142082_(0, i - 90 - 1, 0);
         boolean flag = super.m_7832_(p_71250_, p_71251_, p_71252_, p_71253_, p_71254_, p_71255_, p_71256_);
         if (resourcelocation.equals(IglooPieces.f_71223_)) {
            BlockPos blockpos3 = this.f_73658_.m_141952_(StructureTemplate.m_74563_(structureplacesettings, new BlockPos(3, 0, 5)));
            BlockState blockstate = p_71250_.m_8055_(blockpos3.m_7495_());
            if (!blockstate.m_60795_() && !blockstate.m_60713_(Blocks.f_50155_)) {
               p_71250_.m_7731_(blockpos3, Blocks.f_50127_.m_49966_(), 3);
            }
         }

         this.f_73658_ = blockpos2;
         return flag;
      }
   }
}