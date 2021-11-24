package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JigsawPlacement {
   static final Logger f_68941_ = LogManager.getLogger();

   public static void m_161612_(RegistryAccess p_161613_, JigsawConfiguration p_161614_, JigsawPlacement.PieceFactory p_161615_, ChunkGenerator p_161616_, StructureManager p_161617_, BlockPos p_161618_, StructurePieceAccessor p_161619_, Random p_161620_, boolean p_161621_, boolean p_161622_, LevelHeightAccessor p_161623_) {
      StructureFeature.m_67096_();
      List<PoolElementStructurePiece> list = Lists.newArrayList();
      Registry<StructureTemplatePool> registry = p_161613_.m_175515_(Registry.f_122884_);
      Rotation rotation = Rotation.m_55956_(p_161620_);
      StructureTemplatePool structuretemplatepool = p_161614_.m_67766_().get();
      StructurePoolElement structurepoolelement = structuretemplatepool.m_69273_(p_161620_);
      if (structurepoolelement != EmptyPoolElement.f_68856_) {
         PoolElementStructurePiece poolelementstructurepiece = p_161615_.m_68964_(p_161617_, structurepoolelement, p_161618_, structurepoolelement.m_69231_(), rotation, structurepoolelement.m_6867_(p_161617_, p_161618_, rotation));
         BoundingBox boundingbox = poolelementstructurepiece.m_73547_();
         int i = (boundingbox.m_162399_() + boundingbox.m_162395_()) / 2;
         int j = (boundingbox.m_162401_() + boundingbox.m_162398_()) / 2;
         int k;
         if (p_161622_) {
            k = p_161618_.m_123342_() + p_161616_.m_156174_(i, j, Heightmap.Types.WORLD_SURFACE_WG, p_161623_);
         } else {
            k = p_161618_.m_123342_();
         }

         int l = boundingbox.m_162396_() + poolelementstructurepiece.m_72647_();
         poolelementstructurepiece.m_6324_(0, k - l, 0);
         list.add(poolelementstructurepiece);
         if (p_161614_.m_67765_() > 0) {
            int i1 = 80;
            AABB aabb = new AABB((double)(i - 80), (double)(k - 80), (double)(j - 80), (double)(i + 80 + 1), (double)(k + 80 + 1), (double)(j + 80 + 1));
            JigsawPlacement.Placer jigsawplacement$placer = new JigsawPlacement.Placer(registry, p_161614_.m_67765_(), p_161615_, p_161616_, p_161617_, list, p_161620_);
            jigsawplacement$placer.f_69001_.addLast(new JigsawPlacement.PieceState(poolelementstructurepiece, new MutableObject<>(Shapes.m_83113_(Shapes.m_83064_(aabb), Shapes.m_83064_(AABB.m_82321_(boundingbox)), BooleanOp.f_82685_)), k + 80, 0));

            while(!jigsawplacement$placer.f_69001_.isEmpty()) {
               JigsawPlacement.PieceState jigsawplacement$piecestate = jigsawplacement$placer.f_69001_.removeFirst();
               jigsawplacement$placer.m_161636_(jigsawplacement$piecestate.f_68971_, jigsawplacement$piecestate.f_68972_, jigsawplacement$piecestate.f_68973_, jigsawplacement$piecestate.f_68974_, p_161621_, p_161623_);
            }

            list.forEach(p_161619_::m_142679_);
         }
      }
   }

   public static void m_161624_(RegistryAccess p_161625_, PoolElementStructurePiece p_161626_, int p_161627_, JigsawPlacement.PieceFactory p_161628_, ChunkGenerator p_161629_, StructureManager p_161630_, List<? super PoolElementStructurePiece> p_161631_, Random p_161632_, LevelHeightAccessor p_161633_) {
      Registry<StructureTemplatePool> registry = p_161625_.m_175515_(Registry.f_122884_);
      JigsawPlacement.Placer jigsawplacement$placer = new JigsawPlacement.Placer(registry, p_161627_, p_161628_, p_161629_, p_161630_, p_161631_, p_161632_);
      jigsawplacement$placer.f_69001_.addLast(new JigsawPlacement.PieceState(p_161626_, new MutableObject<>(Shapes.f_83036_), 0, 0));

      while(!jigsawplacement$placer.f_69001_.isEmpty()) {
         JigsawPlacement.PieceState jigsawplacement$piecestate = jigsawplacement$placer.f_69001_.removeFirst();
         jigsawplacement$placer.m_161636_(jigsawplacement$piecestate.f_68971_, jigsawplacement$piecestate.f_68972_, jigsawplacement$piecestate.f_68973_, jigsawplacement$piecestate.f_68974_, false, p_161633_);
      }

   }

   public interface PieceFactory {
      PoolElementStructurePiece m_68964_(StructureManager p_68965_, StructurePoolElement p_68966_, BlockPos p_68967_, int p_68968_, Rotation p_68969_, BoundingBox p_68970_);
   }

   static final class PieceState {
      final PoolElementStructurePiece f_68971_;
      final MutableObject<VoxelShape> f_68972_;
      final int f_68973_;
      final int f_68974_;

      PieceState(PoolElementStructurePiece p_68976_, MutableObject<VoxelShape> p_68977_, int p_68978_, int p_68979_) {
         this.f_68971_ = p_68976_;
         this.f_68972_ = p_68977_;
         this.f_68973_ = p_68978_;
         this.f_68974_ = p_68979_;
      }
   }

   static final class Placer {
      private final Registry<StructureTemplatePool> f_68994_;
      private final int f_68995_;
      private final JigsawPlacement.PieceFactory f_68996_;
      private final ChunkGenerator f_68997_;
      private final StructureManager f_68998_;
      private final List<? super PoolElementStructurePiece> f_68999_;
      private final Random f_69000_;
      final Deque<JigsawPlacement.PieceState> f_69001_ = Queues.newArrayDeque();

      Placer(Registry<StructureTemplatePool> p_69003_, int p_69004_, JigsawPlacement.PieceFactory p_69005_, ChunkGenerator p_69006_, StructureManager p_69007_, List<? super PoolElementStructurePiece> p_69008_, Random p_69009_) {
         this.f_68994_ = p_69003_;
         this.f_68995_ = p_69004_;
         this.f_68996_ = p_69005_;
         this.f_68997_ = p_69006_;
         this.f_68998_ = p_69007_;
         this.f_68999_ = p_69008_;
         this.f_69000_ = p_69009_;
      }

      void m_161636_(PoolElementStructurePiece p_161637_, MutableObject<VoxelShape> p_161638_, int p_161639_, int p_161640_, boolean p_161641_, LevelHeightAccessor p_161642_) {
         StructurePoolElement structurepoolelement = p_161637_.m_72645_();
         BlockPos blockpos = p_161637_.m_72646_();
         Rotation rotation = p_161637_.m_6830_();
         StructureTemplatePool.Projection structuretemplatepool$projection = structurepoolelement.m_69230_();
         boolean flag = structuretemplatepool$projection == StructureTemplatePool.Projection.RIGID;
         MutableObject<VoxelShape> mutableobject = new MutableObject<>();
         BoundingBox boundingbox = p_161637_.m_73547_();
         int i = boundingbox.m_162396_();

         label139:
         for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : structurepoolelement.m_6439_(this.f_68998_, blockpos, rotation, this.f_69000_)) {
            Direction direction = JigsawBlock.m_54250_(structuretemplate$structureblockinfo.f_74676_);
            BlockPos blockpos1 = structuretemplate$structureblockinfo.f_74675_;
            BlockPos blockpos2 = blockpos1.m_142300_(direction);
            int j = blockpos1.m_123342_() - i;
            int k = -1;
            ResourceLocation resourcelocation = new ResourceLocation(structuretemplate$structureblockinfo.f_74677_.m_128461_("pool"));
            Optional<StructureTemplatePool> optional = this.f_68994_.m_6612_(resourcelocation);
            if (optional.isPresent() && (optional.get().m_69278_() != 0 || Objects.equals(resourcelocation, Pools.f_127186_.m_135782_()))) {
               ResourceLocation resourcelocation1 = optional.get().m_69263_();
               Optional<StructureTemplatePool> optional1 = this.f_68994_.m_6612_(resourcelocation1);
               if (optional1.isPresent() && (optional1.get().m_69278_() != 0 || Objects.equals(resourcelocation1, Pools.f_127186_.m_135782_()))) {
                  boolean flag1 = boundingbox.m_71051_(blockpos2);
                  MutableObject<VoxelShape> mutableobject1;
                  int l;
                  if (flag1) {
                     mutableobject1 = mutableobject;
                     l = i;
                     if (mutableobject.getValue() == null) {
                        mutableobject.setValue(Shapes.m_83064_(AABB.m_82321_(boundingbox)));
                     }
                  } else {
                     mutableobject1 = p_161638_;
                     l = p_161639_;
                  }

                  List<StructurePoolElement> list = Lists.newArrayList();
                  if (p_161640_ != this.f_68995_) {
                     list.addAll(optional.get().m_69276_(this.f_69000_));
                  }

                  list.addAll(optional1.get().m_69276_(this.f_69000_));

                  for(StructurePoolElement structurepoolelement1 : list) {
                     if (structurepoolelement1 == EmptyPoolElement.f_68856_) {
                        break;
                     }

                     for(Rotation rotation1 : Rotation.m_55958_(this.f_69000_)) {
                        List<StructureTemplate.StructureBlockInfo> list1 = structurepoolelement1.m_6439_(this.f_68998_, BlockPos.f_121853_, rotation1, this.f_69000_);
                        BoundingBox boundingbox1 = structurepoolelement1.m_6867_(this.f_68998_, BlockPos.f_121853_, rotation1);
                        int i1;
                        if (p_161641_ && boundingbox1.m_71057_() <= 16) {
                           i1 = list1.stream().mapToInt((p_69032_) -> {
                              if (!boundingbox1.m_71051_(p_69032_.f_74675_.m_142300_(JigsawBlock.m_54250_(p_69032_.f_74676_)))) {
                                 return 0;
                              } else {
                                 ResourceLocation resourcelocation2 = new ResourceLocation(p_69032_.f_74677_.m_128461_("pool"));
                                 Optional<StructureTemplatePool> optional2 = this.f_68994_.m_6612_(resourcelocation2);
                                 Optional<StructureTemplatePool> optional3 = optional2.flatMap((p_161646_) -> {
                                    return this.f_68994_.m_6612_(p_161646_.m_69263_());
                                 });
                                 int k3 = optional2.map((p_161644_) -> {
                                    return p_161644_.m_69268_(this.f_68998_);
                                 }).orElse(0);
                                 int l3 = optional3.map((p_161635_) -> {
                                    return p_161635_.m_69268_(this.f_68998_);
                                 }).orElse(0);
                                 return Math.max(k3, l3);
                              }
                           }).max().orElse(0);
                        } else {
                           i1 = 0;
                        }

                        for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo1 : list1) {
                           if (JigsawBlock.m_54245_(structuretemplate$structureblockinfo, structuretemplate$structureblockinfo1)) {
                              BlockPos blockpos3 = structuretemplate$structureblockinfo1.f_74675_;
                              BlockPos blockpos4 = blockpos2.m_141950_(blockpos3);
                              BoundingBox boundingbox2 = structurepoolelement1.m_6867_(this.f_68998_, blockpos4, rotation1);
                              int j1 = boundingbox2.m_162396_();
                              StructureTemplatePool.Projection structuretemplatepool$projection1 = structurepoolelement1.m_69230_();
                              boolean flag2 = structuretemplatepool$projection1 == StructureTemplatePool.Projection.RIGID;
                              int k1 = blockpos3.m_123342_();
                              int l1 = j - k1 + JigsawBlock.m_54250_(structuretemplate$structureblockinfo.f_74676_).m_122430_();
                              int i2;
                              if (flag && flag2) {
                                 i2 = i + l1;
                              } else {
                                 if (k == -1) {
                                    k = this.f_68997_.m_156174_(blockpos1.m_123341_(), blockpos1.m_123343_(), Heightmap.Types.WORLD_SURFACE_WG, p_161642_);
                                 }

                                 i2 = k - k1;
                              }

                              int j2 = i2 - j1;
                              BoundingBox boundingbox3 = boundingbox2.m_71045_(0, j2, 0);
                              BlockPos blockpos5 = blockpos4.m_142082_(0, j2, 0);
                              if (i1 > 0) {
                                 int k2 = Math.max(i1 + 1, boundingbox3.m_162400_() - boundingbox3.m_162396_());
                                 boundingbox3.m_162371_(new BlockPos(boundingbox3.m_162395_(), boundingbox3.m_162396_() + k2, boundingbox3.m_162398_()));
                              }

                              if (!Shapes.m_83157_(mutableobject1.getValue(), Shapes.m_83064_(AABB.m_82321_(boundingbox3).m_82406_(0.25D)), BooleanOp.f_82683_)) {
                                 mutableobject1.setValue(Shapes.m_83148_(mutableobject1.getValue(), Shapes.m_83064_(AABB.m_82321_(boundingbox3)), BooleanOp.f_82685_));
                                 int j3 = p_161637_.m_72647_();
                                 int l2;
                                 if (flag2) {
                                    l2 = j3 - l1;
                                 } else {
                                    l2 = structurepoolelement1.m_69231_();
                                 }

                                 PoolElementStructurePiece poolelementstructurepiece = this.f_68996_.m_68964_(this.f_68998_, structurepoolelement1, blockpos5, l2, rotation1, boundingbox3);
                                 int i3;
                                 if (flag) {
                                    i3 = i + j;
                                 } else if (flag2) {
                                    i3 = i2 + k1;
                                 } else {
                                    if (k == -1) {
                                       k = this.f_68997_.m_156174_(blockpos1.m_123341_(), blockpos1.m_123343_(), Heightmap.Types.WORLD_SURFACE_WG, p_161642_);
                                    }

                                    i3 = k + l1 / 2;
                                 }

                                 p_161637_.m_72635_(new JigsawJunction(blockpos2.m_123341_(), i3 - j + j3, blockpos2.m_123343_(), l1, structuretemplatepool$projection1));
                                 poolelementstructurepiece.m_72635_(new JigsawJunction(blockpos1.m_123341_(), i3 - k1 + l2, blockpos1.m_123343_(), -l1, structuretemplatepool$projection));
                                 this.f_68999_.add(poolelementstructurepiece);
                                 if (p_161640_ + 1 <= this.f_68995_) {
                                    this.f_69001_.addLast(new JigsawPlacement.PieceState(poolelementstructurepiece, mutableobject1, l, p_161640_ + 1));
                                 }
                                 continue label139;
                              }
                           }
                        }
                     }
                  }
               } else {
                  JigsawPlacement.f_68941_.warn("Empty or non-existent fallback pool: {}", (Object)resourcelocation1);
               }
            } else {
               JigsawPlacement.f_68941_.warn("Empty or non-existent pool: {}", (Object)resourcelocation);
            }
         }

      }
   }
}