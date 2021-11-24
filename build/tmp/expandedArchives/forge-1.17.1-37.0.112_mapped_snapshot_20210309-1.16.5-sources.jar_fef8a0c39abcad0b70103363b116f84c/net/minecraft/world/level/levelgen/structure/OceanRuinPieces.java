package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class OceanRuinPieces {
   private static final ResourceLocation[] f_72519_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/warm_1"), new ResourceLocation("underwater_ruin/warm_2"), new ResourceLocation("underwater_ruin/warm_3"), new ResourceLocation("underwater_ruin/warm_4"), new ResourceLocation("underwater_ruin/warm_5"), new ResourceLocation("underwater_ruin/warm_6"), new ResourceLocation("underwater_ruin/warm_7"), new ResourceLocation("underwater_ruin/warm_8")};
   private static final ResourceLocation[] f_72520_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/brick_1"), new ResourceLocation("underwater_ruin/brick_2"), new ResourceLocation("underwater_ruin/brick_3"), new ResourceLocation("underwater_ruin/brick_4"), new ResourceLocation("underwater_ruin/brick_5"), new ResourceLocation("underwater_ruin/brick_6"), new ResourceLocation("underwater_ruin/brick_7"), new ResourceLocation("underwater_ruin/brick_8")};
   private static final ResourceLocation[] f_72521_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/cracked_1"), new ResourceLocation("underwater_ruin/cracked_2"), new ResourceLocation("underwater_ruin/cracked_3"), new ResourceLocation("underwater_ruin/cracked_4"), new ResourceLocation("underwater_ruin/cracked_5"), new ResourceLocation("underwater_ruin/cracked_6"), new ResourceLocation("underwater_ruin/cracked_7"), new ResourceLocation("underwater_ruin/cracked_8")};
   private static final ResourceLocation[] f_72522_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/mossy_1"), new ResourceLocation("underwater_ruin/mossy_2"), new ResourceLocation("underwater_ruin/mossy_3"), new ResourceLocation("underwater_ruin/mossy_4"), new ResourceLocation("underwater_ruin/mossy_5"), new ResourceLocation("underwater_ruin/mossy_6"), new ResourceLocation("underwater_ruin/mossy_7"), new ResourceLocation("underwater_ruin/mossy_8")};
   private static final ResourceLocation[] f_72523_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_brick_1"), new ResourceLocation("underwater_ruin/big_brick_2"), new ResourceLocation("underwater_ruin/big_brick_3"), new ResourceLocation("underwater_ruin/big_brick_8")};
   private static final ResourceLocation[] f_72524_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_mossy_1"), new ResourceLocation("underwater_ruin/big_mossy_2"), new ResourceLocation("underwater_ruin/big_mossy_3"), new ResourceLocation("underwater_ruin/big_mossy_8")};
   private static final ResourceLocation[] f_72525_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_cracked_1"), new ResourceLocation("underwater_ruin/big_cracked_2"), new ResourceLocation("underwater_ruin/big_cracked_3"), new ResourceLocation("underwater_ruin/big_cracked_8")};
   private static final ResourceLocation[] f_72526_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_warm_4"), new ResourceLocation("underwater_ruin/big_warm_5"), new ResourceLocation("underwater_ruin/big_warm_6"), new ResourceLocation("underwater_ruin/big_warm_7")};

   private static ResourceLocation m_72551_(Random p_72552_) {
      return Util.m_137545_(f_72519_, p_72552_);
   }

   private static ResourceLocation m_72557_(Random p_72558_) {
      return Util.m_137545_(f_72526_, p_72558_);
   }

   public static void m_163078_(StructureManager p_163079_, BlockPos p_163080_, Rotation p_163081_, StructurePieceAccessor p_163082_, Random p_163083_, OceanRuinConfiguration p_163084_) {
      boolean flag = p_163083_.nextFloat() <= p_163084_.f_67822_;
      float f = flag ? 0.9F : 0.8F;
      m_163085_(p_163079_, p_163080_, p_163081_, p_163082_, p_163083_, p_163084_, flag, f);
      if (flag && p_163083_.nextFloat() <= p_163084_.f_67823_) {
         m_163094_(p_163079_, p_163083_, p_163081_, p_163080_, p_163084_, p_163082_);
      }

   }

   private static void m_163094_(StructureManager p_163095_, Random p_163096_, Rotation p_163097_, BlockPos p_163098_, OceanRuinConfiguration p_163099_, StructurePieceAccessor p_163100_) {
      BlockPos blockpos = new BlockPos(p_163098_.m_123341_(), 90, p_163098_.m_123343_());
      BlockPos blockpos1 = StructureTemplate.m_74593_(new BlockPos(15, 0, 15), Mirror.NONE, p_163097_, BlockPos.f_121853_).m_141952_(blockpos);
      BoundingBox boundingbox = BoundingBox.m_162375_(blockpos, blockpos1);
      BlockPos blockpos2 = new BlockPos(Math.min(blockpos.m_123341_(), blockpos1.m_123341_()), blockpos.m_123342_(), Math.min(blockpos.m_123343_(), blockpos1.m_123343_()));
      List<BlockPos> list = m_163101_(p_163096_, blockpos2);
      int i = Mth.m_14072_(p_163096_, 4, 8);

      for(int j = 0; j < i; ++j) {
         if (!list.isEmpty()) {
            int k = p_163096_.nextInt(list.size());
            BlockPos blockpos3 = list.remove(k);
            Rotation rotation = Rotation.m_55956_(p_163096_);
            BlockPos blockpos4 = StructureTemplate.m_74593_(new BlockPos(5, 0, 6), Mirror.NONE, rotation, BlockPos.f_121853_).m_141952_(blockpos3);
            BoundingBox boundingbox1 = BoundingBox.m_162375_(blockpos3, blockpos4);
            if (!boundingbox1.m_71049_(boundingbox)) {
               m_163085_(p_163095_, blockpos3, rotation, p_163100_, p_163096_, p_163099_, false, 0.8F);
            }
         }
      }

   }

   private static List<BlockPos> m_163101_(Random p_163102_, BlockPos p_163103_) {
      List<BlockPos> list = Lists.newArrayList();
      list.add(p_163103_.m_142082_(-16 + Mth.m_14072_(p_163102_, 1, 8), 0, 16 + Mth.m_14072_(p_163102_, 1, 7)));
      list.add(p_163103_.m_142082_(-16 + Mth.m_14072_(p_163102_, 1, 8), 0, Mth.m_14072_(p_163102_, 1, 7)));
      list.add(p_163103_.m_142082_(-16 + Mth.m_14072_(p_163102_, 1, 8), 0, -16 + Mth.m_14072_(p_163102_, 4, 8)));
      list.add(p_163103_.m_142082_(Mth.m_14072_(p_163102_, 1, 7), 0, 16 + Mth.m_14072_(p_163102_, 1, 7)));
      list.add(p_163103_.m_142082_(Mth.m_14072_(p_163102_, 1, 7), 0, -16 + Mth.m_14072_(p_163102_, 4, 6)));
      list.add(p_163103_.m_142082_(16 + Mth.m_14072_(p_163102_, 1, 7), 0, 16 + Mth.m_14072_(p_163102_, 3, 8)));
      list.add(p_163103_.m_142082_(16 + Mth.m_14072_(p_163102_, 1, 7), 0, Mth.m_14072_(p_163102_, 1, 7)));
      list.add(p_163103_.m_142082_(16 + Mth.m_14072_(p_163102_, 1, 7), 0, -16 + Mth.m_14072_(p_163102_, 4, 8)));
      return list;
   }

   private static void m_163085_(StructureManager p_163086_, BlockPos p_163087_, Rotation p_163088_, StructurePieceAccessor p_163089_, Random p_163090_, OceanRuinConfiguration p_163091_, boolean p_163092_, float p_163093_) {
      switch(p_163091_.f_67821_) {
      case WARM:
      default:
         ResourceLocation resourcelocation = p_163092_ ? m_72557_(p_163090_) : m_72551_(p_163090_);
         p_163089_.m_142679_(new OceanRuinPieces.OceanRuinPiece(p_163086_, resourcelocation, p_163087_, p_163088_, p_163093_, p_163091_.f_67821_, p_163092_));
         break;
      case COLD:
         ResourceLocation[] aresourcelocation = p_163092_ ? f_72523_ : f_72520_;
         ResourceLocation[] aresourcelocation1 = p_163092_ ? f_72525_ : f_72521_;
         ResourceLocation[] aresourcelocation2 = p_163092_ ? f_72524_ : f_72522_;
         int i = p_163090_.nextInt(aresourcelocation.length);
         p_163089_.m_142679_(new OceanRuinPieces.OceanRuinPiece(p_163086_, aresourcelocation[i], p_163087_, p_163088_, p_163093_, p_163091_.f_67821_, p_163092_));
         p_163089_.m_142679_(new OceanRuinPieces.OceanRuinPiece(p_163086_, aresourcelocation1[i], p_163087_, p_163088_, 0.7F, p_163091_.f_67821_, p_163092_));
         p_163089_.m_142679_(new OceanRuinPieces.OceanRuinPiece(p_163086_, aresourcelocation2[i], p_163087_, p_163088_, 0.5F, p_163091_.f_67821_, p_163092_));
      }

   }

   public static class OceanRuinPiece extends TemplateStructurePiece {
      private final OceanRuinFeature.Type f_72559_;
      private final float f_72560_;
      private final boolean f_72563_;

      public OceanRuinPiece(StructureManager p_72568_, ResourceLocation p_72569_, BlockPos p_72570_, Rotation p_72571_, float p_72572_, OceanRuinFeature.Type p_72573_, boolean p_72574_) {
         super(StructurePieceType.f_67113_, 0, p_72568_, p_72569_, p_72569_.toString(), m_163112_(p_72571_), p_72570_);
         this.f_72560_ = p_72572_;
         this.f_72559_ = p_72573_;
         this.f_72563_ = p_72574_;
      }

      public OceanRuinPiece(ServerLevel p_163107_, CompoundTag p_163108_) {
         super(StructurePieceType.f_67113_, p_163108_, p_163107_, (p_163116_) -> {
            return m_163112_(Rotation.valueOf(p_163108_.m_128461_("Rot")));
         });
         this.f_72560_ = p_163108_.m_128457_("Integrity");
         this.f_72559_ = OceanRuinFeature.Type.valueOf(p_163108_.m_128461_("BiomeType"));
         this.f_72563_ = p_163108_.m_128471_("IsLarge");
      }

      private static StructurePlaceSettings m_163112_(Rotation p_163113_) {
         return (new StructurePlaceSettings()).m_74379_(p_163113_).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74048_);
      }

      protected void m_142347_(ServerLevel p_163110_, CompoundTag p_163111_) {
         super.m_142347_(p_163110_, p_163111_);
         p_163111_.m_128359_("Rot", this.f_73657_.m_74404_().name());
         p_163111_.m_128350_("Integrity", this.f_72560_);
         p_163111_.m_128359_("BiomeType", this.f_72559_.toString());
         p_163111_.m_128379_("IsLarge", this.f_72563_);
      }

      protected void m_7756_(String p_72590_, BlockPos p_72591_, ServerLevelAccessor p_72592_, Random p_72593_, BoundingBox p_72594_) {
         if ("chest".equals(p_72590_)) {
            p_72592_.m_7731_(p_72591_, Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51480_, Boolean.valueOf(p_72592_.m_6425_(p_72591_).m_76153_(FluidTags.f_13131_))), 2);
            BlockEntity blockentity = p_72592_.m_7702_(p_72591_);
            if (blockentity instanceof ChestBlockEntity) {
               ((ChestBlockEntity)blockentity).m_59626_(this.f_72563_ ? BuiltInLootTables.f_78691_ : BuiltInLootTables.f_78690_, p_72593_.nextLong());
            }
         } else if ("drowned".equals(p_72590_)) {
            Drowned drowned = EntityType.f_20562_.m_20615_(p_72592_.m_6018_());
            drowned.m_21530_();
            drowned.m_20035_(p_72591_, 0.0F, 0.0F);
            drowned.m_6518_(p_72592_, p_72592_.m_6436_(p_72591_), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
            p_72592_.m_47205_(drowned);
            if (p_72591_.m_123342_() > p_72592_.m_5736_()) {
               p_72592_.m_7731_(p_72591_, Blocks.f_50016_.m_49966_(), 2);
            } else {
               p_72592_.m_7731_(p_72591_, Blocks.f_49990_.m_49966_(), 2);
            }
         }

      }

      public boolean m_7832_(WorldGenLevel p_72576_, StructureFeatureManager p_72577_, ChunkGenerator p_72578_, Random p_72579_, BoundingBox p_72580_, ChunkPos p_72581_, BlockPos p_72582_) {
         this.f_73657_.m_74394_().m_74383_(new BlockRotProcessor(this.f_72560_)).m_74383_(BlockIgnoreProcessor.f_74048_);
         int i = p_72576_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, this.f_73658_.m_123341_(), this.f_73658_.m_123343_());
         this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), i, this.f_73658_.m_123343_());
         BlockPos blockpos = StructureTemplate.m_74593_(new BlockPos(this.f_73656_.m_163801_().m_123341_() - 1, 0, this.f_73656_.m_163801_().m_123343_() - 1), Mirror.NONE, this.f_73657_.m_74404_(), BlockPos.f_121853_).m_141952_(this.f_73658_);
         this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), this.m_72585_(this.f_73658_, p_72576_, blockpos), this.f_73658_.m_123343_());
         return super.m_7832_(p_72576_, p_72577_, p_72578_, p_72579_, p_72580_, p_72581_, p_72582_);
      }

      private int m_72585_(BlockPos p_72586_, BlockGetter p_72587_, BlockPos p_72588_) {
         int i = p_72586_.m_123342_();
         int j = 512;
         int k = i - 1;
         int l = 0;

         for(BlockPos blockpos : BlockPos.m_121940_(p_72586_, p_72588_)) {
            int i1 = blockpos.m_123341_();
            int j1 = blockpos.m_123343_();
            int k1 = p_72586_.m_123342_() - 1;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(i1, k1, j1);
            BlockState blockstate = p_72587_.m_8055_(blockpos$mutableblockpos);

            for(FluidState fluidstate = p_72587_.m_6425_(blockpos$mutableblockpos); (blockstate.m_60795_() || fluidstate.m_76153_(FluidTags.f_13131_) || blockstate.m_60620_(BlockTags.f_13047_)) && k1 > p_72587_.m_141937_() + 1; fluidstate = p_72587_.m_6425_(blockpos$mutableblockpos)) {
               --k1;
               blockpos$mutableblockpos.m_122178_(i1, k1, j1);
               blockstate = p_72587_.m_8055_(blockpos$mutableblockpos);
            }

            j = Math.min(j, k1);
            if (k1 < k - 2) {
               ++l;
            }
         }

         int l1 = Math.abs(p_72586_.m_123341_() - p_72588_.m_123341_());
         if (k - j > 2 && l > l1 - 2) {
            i = j + 1;
         }

         return i;
      }
   }
}