package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public class OceanMonumentPieces {
   private OceanMonumentPieces() {
   }

   static class FitDoubleXRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72097_) {
         return p_72097_.f_72445_[Direction.EAST.m_122411_()] && !p_72097_.f_72444_[Direction.EAST.m_122411_()].f_72446_;
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72099_, OceanMonumentPieces.RoomDefinition p_72100_, Random p_72101_) {
         p_72100_.f_72446_ = true;
         p_72100_.f_72444_[Direction.EAST.m_122411_()].f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentDoubleXRoom(p_72099_, p_72100_);
      }
   }

   static class FitDoubleXYRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72106_) {
         if (p_72106_.f_72445_[Direction.EAST.m_122411_()] && !p_72106_.f_72444_[Direction.EAST.m_122411_()].f_72446_ && p_72106_.f_72445_[Direction.UP.m_122411_()] && !p_72106_.f_72444_[Direction.UP.m_122411_()].f_72446_) {
            OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_72106_.f_72444_[Direction.EAST.m_122411_()];
            return oceanmonumentpieces$roomdefinition.f_72445_[Direction.UP.m_122411_()] && !oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()].f_72446_;
         } else {
            return false;
         }
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72108_, OceanMonumentPieces.RoomDefinition p_72109_, Random p_72110_) {
         p_72109_.f_72446_ = true;
         p_72109_.f_72444_[Direction.EAST.m_122411_()].f_72446_ = true;
         p_72109_.f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         p_72109_.f_72444_[Direction.EAST.m_122411_()].f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentDoubleXYRoom(p_72108_, p_72109_);
      }
   }

   static class FitDoubleYRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72115_) {
         return p_72115_.f_72445_[Direction.UP.m_122411_()] && !p_72115_.f_72444_[Direction.UP.m_122411_()].f_72446_;
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72117_, OceanMonumentPieces.RoomDefinition p_72118_, Random p_72119_) {
         p_72118_.f_72446_ = true;
         p_72118_.f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentDoubleYRoom(p_72117_, p_72118_);
      }
   }

   static class FitDoubleYZRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72124_) {
         if (p_72124_.f_72445_[Direction.NORTH.m_122411_()] && !p_72124_.f_72444_[Direction.NORTH.m_122411_()].f_72446_ && p_72124_.f_72445_[Direction.UP.m_122411_()] && !p_72124_.f_72444_[Direction.UP.m_122411_()].f_72446_) {
            OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_72124_.f_72444_[Direction.NORTH.m_122411_()];
            return oceanmonumentpieces$roomdefinition.f_72445_[Direction.UP.m_122411_()] && !oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()].f_72446_;
         } else {
            return false;
         }
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72126_, OceanMonumentPieces.RoomDefinition p_72127_, Random p_72128_) {
         p_72127_.f_72446_ = true;
         p_72127_.f_72444_[Direction.NORTH.m_122411_()].f_72446_ = true;
         p_72127_.f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         p_72127_.f_72444_[Direction.NORTH.m_122411_()].f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentDoubleYZRoom(p_72126_, p_72127_);
      }
   }

   static class FitDoubleZRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72133_) {
         return p_72133_.f_72445_[Direction.NORTH.m_122411_()] && !p_72133_.f_72444_[Direction.NORTH.m_122411_()].f_72446_;
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72135_, OceanMonumentPieces.RoomDefinition p_72136_, Random p_72137_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_72136_;
         if (!p_72136_.f_72445_[Direction.NORTH.m_122411_()] || p_72136_.f_72444_[Direction.NORTH.m_122411_()].f_72446_) {
            oceanmonumentpieces$roomdefinition = p_72136_.f_72444_[Direction.SOUTH.m_122411_()];
         }

         oceanmonumentpieces$roomdefinition.f_72446_ = true;
         oceanmonumentpieces$roomdefinition.f_72444_[Direction.NORTH.m_122411_()].f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentDoubleZRoom(p_72135_, oceanmonumentpieces$roomdefinition);
      }
   }

   static class FitSimpleRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72142_) {
         return true;
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72144_, OceanMonumentPieces.RoomDefinition p_72145_, Random p_72146_) {
         p_72145_.f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentSimpleRoom(p_72144_, p_72145_, p_72146_);
      }
   }

   static class FitSimpleTopRoom implements OceanMonumentPieces.MonumentRoomFitter {
      public boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72151_) {
         return !p_72151_.f_72445_[Direction.WEST.m_122411_()] && !p_72151_.f_72445_[Direction.EAST.m_122411_()] && !p_72151_.f_72445_[Direction.NORTH.m_122411_()] && !p_72151_.f_72445_[Direction.SOUTH.m_122411_()] && !p_72151_.f_72445_[Direction.UP.m_122411_()];
      }

      public OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72153_, OceanMonumentPieces.RoomDefinition p_72154_, Random p_72155_) {
         p_72154_.f_72446_ = true;
         return new OceanMonumentPieces.OceanMonumentSimpleTopRoom(p_72153_, p_72154_);
      }
   }

   public static class MonumentBuilding extends OceanMonumentPieces.OceanMonumentPiece {
      private static final int f_162988_ = 58;
      private static final int f_162989_ = 22;
      private static final int f_162990_ = 58;
      public static final int f_162992_ = 29;
      private static final int f_162991_ = 61;
      private OceanMonumentPieces.RoomDefinition f_72156_;
      private OceanMonumentPieces.RoomDefinition f_72157_;
      private final List<OceanMonumentPieces.OceanMonumentPiece> f_72158_ = Lists.newArrayList();

      public MonumentBuilding(Random p_72163_, int p_72164_, int p_72165_, Direction p_72166_) {
         super(StructurePieceType.f_67118_, p_72166_, 0, m_163541_(p_72164_, 39, p_72165_, p_72166_, 58, 23, 58));
         this.m_73519_(p_72166_);
         List<OceanMonumentPieces.RoomDefinition> list = this.m_72179_(p_72163_);
         this.f_72156_.f_72446_ = true;
         this.f_72158_.add(new OceanMonumentPieces.OceanMonumentEntryRoom(p_72166_, this.f_72156_));
         this.f_72158_.add(new OceanMonumentPieces.OceanMonumentCoreRoom(p_72166_, this.f_72157_));
         List<OceanMonumentPieces.MonumentRoomFitter> list1 = Lists.newArrayList();
         list1.add(new OceanMonumentPieces.FitDoubleXYRoom());
         list1.add(new OceanMonumentPieces.FitDoubleYZRoom());
         list1.add(new OceanMonumentPieces.FitDoubleZRoom());
         list1.add(new OceanMonumentPieces.FitDoubleXRoom());
         list1.add(new OceanMonumentPieces.FitDoubleYRoom());
         list1.add(new OceanMonumentPieces.FitSimpleTopRoom());
         list1.add(new OceanMonumentPieces.FitSimpleRoom());

         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition : list) {
            if (!oceanmonumentpieces$roomdefinition.f_72446_ && !oceanmonumentpieces$roomdefinition.m_72462_()) {
               for(OceanMonumentPieces.MonumentRoomFitter oceanmonumentpieces$monumentroomfitter : list1) {
                  if (oceanmonumentpieces$monumentroomfitter.m_7725_(oceanmonumentpieces$roomdefinition)) {
                     this.f_72158_.add(oceanmonumentpieces$monumentroomfitter.m_7924_(p_72166_, oceanmonumentpieces$roomdefinition, p_72163_));
                     break;
                  }
               }
            }
         }

         BlockPos blockpos = this.m_163582_(9, 0, 22);

         for(OceanMonumentPieces.OceanMonumentPiece oceanmonumentpieces$oceanmonumentpiece : this.f_72158_) {
            oceanmonumentpieces$oceanmonumentpiece.m_73547_().m_162373_(blockpos);
         }

         BoundingBox boundingbox = BoundingBox.m_162375_(this.m_163582_(1, 1, 1), this.m_163582_(23, 8, 21));
         BoundingBox boundingbox1 = BoundingBox.m_162375_(this.m_163582_(34, 1, 1), this.m_163582_(56, 8, 21));
         BoundingBox boundingbox2 = BoundingBox.m_162375_(this.m_163582_(22, 13, 22), this.m_163582_(35, 17, 35));
         int i = p_72163_.nextInt();
         this.f_72158_.add(new OceanMonumentPieces.OceanMonumentWingRoom(p_72166_, boundingbox, i++));
         this.f_72158_.add(new OceanMonumentPieces.OceanMonumentWingRoom(p_72166_, boundingbox1, i++));
         this.f_72158_.add(new OceanMonumentPieces.OceanMonumentPenthouse(p_72166_, boundingbox2));
      }

      public MonumentBuilding(ServerLevel p_162994_, CompoundTag p_162995_) {
         super(StructurePieceType.f_67118_, p_162995_);
      }

      private List<OceanMonumentPieces.RoomDefinition> m_72179_(Random p_72180_) {
         OceanMonumentPieces.RoomDefinition[] aoceanmonumentpieces$roomdefinition = new OceanMonumentPieces.RoomDefinition[75];

         for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
               int k = 0;
               int l = m_72393_(i, 0, j);
               aoceanmonumentpieces$roomdefinition[l] = new OceanMonumentPieces.RoomDefinition(l);
            }
         }

         for(int i2 = 0; i2 < 5; ++i2) {
            for(int l2 = 0; l2 < 4; ++l2) {
               int k3 = 1;
               int j4 = m_72393_(i2, 1, l2);
               aoceanmonumentpieces$roomdefinition[j4] = new OceanMonumentPieces.RoomDefinition(j4);
            }
         }

         for(int j2 = 1; j2 < 4; ++j2) {
            for(int i3 = 0; i3 < 2; ++i3) {
               int l3 = 2;
               int k4 = m_72393_(j2, 2, i3);
               aoceanmonumentpieces$roomdefinition[k4] = new OceanMonumentPieces.RoomDefinition(k4);
            }
         }

         this.f_72156_ = aoceanmonumentpieces$roomdefinition[f_72330_];

         for(int k2 = 0; k2 < 5; ++k2) {
            for(int j3 = 0; j3 < 5; ++j3) {
               for(int i4 = 0; i4 < 3; ++i4) {
                  int l4 = m_72393_(k2, i4, j3);
                  if (aoceanmonumentpieces$roomdefinition[l4] != null) {
                     for(Direction direction : Direction.values()) {
                        int i1 = k2 + direction.m_122429_();
                        int j1 = i4 + direction.m_122430_();
                        int k1 = j3 + direction.m_122431_();
                        if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
                           int l1 = m_72393_(i1, j1, k1);
                           if (aoceanmonumentpieces$roomdefinition[l1] != null) {
                              if (k1 == j3) {
                                 aoceanmonumentpieces$roomdefinition[l4].m_72459_(direction, aoceanmonumentpieces$roomdefinition[l1]);
                              } else {
                                 aoceanmonumentpieces$roomdefinition[l4].m_72459_(direction.m_122424_(), aoceanmonumentpieces$roomdefinition[l1]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = new OceanMonumentPieces.RoomDefinition(1003);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = new OceanMonumentPieces.RoomDefinition(1001);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = new OceanMonumentPieces.RoomDefinition(1002);
         aoceanmonumentpieces$roomdefinition[f_72331_].m_72459_(Direction.UP, oceanmonumentpieces$roomdefinition);
         aoceanmonumentpieces$roomdefinition[f_72332_].m_72459_(Direction.SOUTH, oceanmonumentpieces$roomdefinition1);
         aoceanmonumentpieces$roomdefinition[f_72333_].m_72459_(Direction.SOUTH, oceanmonumentpieces$roomdefinition2);
         oceanmonumentpieces$roomdefinition.f_72446_ = true;
         oceanmonumentpieces$roomdefinition1.f_72446_ = true;
         oceanmonumentpieces$roomdefinition2.f_72446_ = true;
         this.f_72156_.f_72447_ = true;
         this.f_72157_ = aoceanmonumentpieces$roomdefinition[m_72393_(p_72180_.nextInt(4), 0, 2)];
         this.f_72157_.f_72446_ = true;
         this.f_72157_.f_72444_[Direction.EAST.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.NORTH.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.EAST.m_122411_()].f_72444_[Direction.NORTH.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.EAST.m_122411_()].f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.NORTH.m_122411_()].f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         this.f_72157_.f_72444_[Direction.EAST.m_122411_()].f_72444_[Direction.NORTH.m_122411_()].f_72444_[Direction.UP.m_122411_()].f_72446_ = true;
         List<OceanMonumentPieces.RoomDefinition> list = Lists.newArrayList();

         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition4 : aoceanmonumentpieces$roomdefinition) {
            if (oceanmonumentpieces$roomdefinition4 != null) {
               oceanmonumentpieces$roomdefinition4.m_72451_();
               list.add(oceanmonumentpieces$roomdefinition4);
            }
         }

         oceanmonumentpieces$roomdefinition.m_72451_();
         Collections.shuffle(list, p_72180_);
         int i5 = 1;

         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 : list) {
            int j5 = 0;
            int k5 = 0;

            while(j5 < 2 && k5 < 5) {
               ++k5;
               int l5 = p_72180_.nextInt(6);
               if (oceanmonumentpieces$roomdefinition3.f_72445_[l5]) {
                  int i6 = Direction.m_122376_(l5).m_122424_().m_122411_();
                  oceanmonumentpieces$roomdefinition3.f_72445_[l5] = false;
                  oceanmonumentpieces$roomdefinition3.f_72444_[l5].f_72445_[i6] = false;
                  if (oceanmonumentpieces$roomdefinition3.m_72452_(i5++) && oceanmonumentpieces$roomdefinition3.f_72444_[l5].m_72452_(i5++)) {
                     ++j5;
                  } else {
                     oceanmonumentpieces$roomdefinition3.f_72445_[l5] = true;
                     oceanmonumentpieces$roomdefinition3.f_72444_[l5].f_72445_[i6] = true;
                  }
               }
            }
         }

         list.add(oceanmonumentpieces$roomdefinition);
         list.add(oceanmonumentpieces$roomdefinition1);
         list.add(oceanmonumentpieces$roomdefinition2);
         return list;
      }

      public boolean m_7832_(WorldGenLevel p_72168_, StructureFeatureManager p_72169_, ChunkGenerator p_72170_, Random p_72171_, BoundingBox p_72172_, ChunkPos p_72173_, BlockPos p_72174_) {
         int i = Math.max(p_72168_.m_5736_(), 64) - this.f_73383_.m_162396_();
         this.m_72360_(p_72168_, p_72172_, 0, 0, 0, 58, i, 58);
         this.m_72181_(false, 0, p_72168_, p_72171_, p_72172_);
         this.m_72181_(true, 33, p_72168_, p_72171_, p_72172_);
         this.m_72175_(p_72168_, p_72171_, p_72172_);
         this.m_72187_(p_72168_, p_72171_, p_72172_);
         this.m_72191_(p_72168_, p_72171_, p_72172_);
         this.m_72195_(p_72168_, p_72171_, p_72172_);
         this.m_72199_(p_72168_, p_72171_, p_72172_);
         this.m_72203_(p_72168_, p_72171_, p_72172_);

         for(int j = 0; j < 7; ++j) {
            int k = 0;

            while(k < 7) {
               if (k == 0 && j == 3) {
                  k = 6;
               }

               int l = j * 9;
               int i1 = k * 9;

               for(int j1 = 0; j1 < 4; ++j1) {
                  for(int k1 = 0; k1 < 4; ++k1) {
                     this.m_73434_(p_72168_, f_72324_, l + j1, 0, i1 + k1, p_72172_);
                     this.m_73528_(p_72168_, f_72324_, l + j1, -1, i1 + k1, p_72172_);
                  }
               }

               if (j != 0 && j != 6) {
                  k += 6;
               } else {
                  ++k;
               }
            }
         }

         for(int l1 = 0; l1 < 5; ++l1) {
            this.m_72360_(p_72168_, p_72172_, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1);
            this.m_72360_(p_72168_, p_72172_, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1);
            this.m_72360_(p_72168_, p_72172_, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1);
            this.m_72360_(p_72168_, p_72172_, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1);
         }

         for(OceanMonumentPieces.OceanMonumentPiece oceanmonumentpieces$oceanmonumentpiece : this.f_72158_) {
            if (oceanmonumentpieces$oceanmonumentpiece.m_73547_().m_71049_(p_72172_)) {
               oceanmonumentpieces$oceanmonumentpiece.m_7832_(p_72168_, p_72169_, p_72170_, p_72171_, p_72172_, p_72173_, p_72174_);
            }
         }

         return true;
      }

      private void m_72181_(boolean p_72182_, int p_72183_, WorldGenLevel p_72184_, Random p_72185_, BoundingBox p_72186_) {
         int i = 24;
         if (this.m_72385_(p_72186_, p_72183_, 0, p_72183_ + 23, 20)) {
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 0, 0, 0, p_72183_ + 24, 0, 20, f_72323_, f_72323_, false);
            this.m_72360_(p_72184_, p_72186_, p_72183_ + 0, 1, 0, p_72183_ + 24, 10, 20);

            for(int j = 0; j < 4; ++j) {
               this.m_73441_(p_72184_, p_72186_, p_72183_ + j, j + 1, j, p_72183_ + j, j + 1, 20, f_72324_, f_72324_, false);
               this.m_73441_(p_72184_, p_72186_, p_72183_ + j + 7, j + 5, j + 7, p_72183_ + j + 7, j + 5, 20, f_72324_, f_72324_, false);
               this.m_73441_(p_72184_, p_72186_, p_72183_ + 17 - j, j + 5, j + 7, p_72183_ + 17 - j, j + 5, 20, f_72324_, f_72324_, false);
               this.m_73441_(p_72184_, p_72186_, p_72183_ + 24 - j, j + 1, j, p_72183_ + 24 - j, j + 1, 20, f_72324_, f_72324_, false);
               this.m_73441_(p_72184_, p_72186_, p_72183_ + j + 1, j + 1, j, p_72183_ + 23 - j, j + 1, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72184_, p_72186_, p_72183_ + j + 8, j + 5, j + 7, p_72183_ + 16 - j, j + 5, j + 7, f_72324_, f_72324_, false);
            }

            this.m_73441_(p_72184_, p_72186_, p_72183_ + 4, 4, 4, p_72183_ + 6, 4, 20, f_72323_, f_72323_, false);
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 7, 4, 4, p_72183_ + 17, 4, 6, f_72323_, f_72323_, false);
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 18, 4, 4, p_72183_ + 20, 4, 20, f_72323_, f_72323_, false);
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 11, 8, 11, p_72183_ + 13, 8, 20, f_72323_, f_72323_, false);
            this.m_73434_(p_72184_, f_72326_, p_72183_ + 12, 9, 12, p_72186_);
            this.m_73434_(p_72184_, f_72326_, p_72183_ + 12, 9, 15, p_72186_);
            this.m_73434_(p_72184_, f_72326_, p_72183_ + 12, 9, 18, p_72186_);
            int j1 = p_72183_ + (p_72182_ ? 19 : 5);
            int k = p_72183_ + (p_72182_ ? 5 : 19);

            for(int l = 20; l >= 5; l -= 3) {
               this.m_73434_(p_72184_, f_72326_, j1, 5, l, p_72186_);
            }

            for(int k1 = 19; k1 >= 7; k1 -= 3) {
               this.m_73434_(p_72184_, f_72326_, k, 5, k1, p_72186_);
            }

            for(int l1 = 0; l1 < 4; ++l1) {
               int i1 = p_72182_ ? p_72183_ + 24 - (17 - l1 * 3) : p_72183_ + 17 - l1 * 3;
               this.m_73434_(p_72184_, f_72326_, i1, 5, 5, p_72186_);
            }

            this.m_73434_(p_72184_, f_72326_, k, 5, 5, p_72186_);
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 11, 1, 12, p_72183_ + 13, 7, 12, f_72323_, f_72323_, false);
            this.m_73441_(p_72184_, p_72186_, p_72183_ + 12, 1, 11, p_72183_ + 12, 7, 13, f_72323_, f_72323_, false);
         }

      }

      private void m_72175_(WorldGenLevel p_72176_, Random p_72177_, BoundingBox p_72178_) {
         if (this.m_72385_(p_72178_, 22, 5, 35, 17)) {
            this.m_72360_(p_72176_, p_72178_, 25, 0, 0, 32, 8, 20);

            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72176_, p_72178_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, f_72324_, f_72324_, false);
               this.m_73441_(p_72176_, p_72178_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, f_72324_, f_72324_, false);
               this.m_73434_(p_72176_, f_72324_, 25, 5, 5 + i * 4, p_72178_);
               this.m_73434_(p_72176_, f_72324_, 26, 6, 5 + i * 4, p_72178_);
               this.m_73434_(p_72176_, f_72327_, 26, 5, 5 + i * 4, p_72178_);
               this.m_73441_(p_72176_, p_72178_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, f_72324_, f_72324_, false);
               this.m_73441_(p_72176_, p_72178_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, f_72324_, f_72324_, false);
               this.m_73434_(p_72176_, f_72324_, 32, 5, 5 + i * 4, p_72178_);
               this.m_73434_(p_72176_, f_72324_, 31, 6, 5 + i * 4, p_72178_);
               this.m_73434_(p_72176_, f_72327_, 31, 5, 5 + i * 4, p_72178_);
               this.m_73441_(p_72176_, p_72178_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, f_72323_, f_72323_, false);
            }
         }

      }

      private void m_72187_(WorldGenLevel p_72188_, Random p_72189_, BoundingBox p_72190_) {
         if (this.m_72385_(p_72190_, 15, 20, 42, 21)) {
            this.m_73441_(p_72188_, p_72190_, 15, 0, 21, 42, 0, 21, f_72323_, f_72323_, false);
            this.m_72360_(p_72188_, p_72190_, 26, 1, 21, 31, 3, 21);
            this.m_73441_(p_72188_, p_72190_, 21, 12, 21, 36, 12, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 17, 11, 21, 40, 11, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 16, 10, 21, 41, 10, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 15, 7, 21, 42, 9, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 16, 6, 21, 41, 6, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 17, 5, 21, 40, 5, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 21, 4, 21, 36, 4, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 22, 3, 21, 26, 3, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 31, 3, 21, 35, 3, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 23, 2, 21, 25, 2, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 32, 2, 21, 34, 2, 21, f_72323_, f_72323_, false);
            this.m_73441_(p_72188_, p_72190_, 28, 4, 20, 29, 4, 21, f_72324_, f_72324_, false);
            this.m_73434_(p_72188_, f_72324_, 27, 3, 21, p_72190_);
            this.m_73434_(p_72188_, f_72324_, 30, 3, 21, p_72190_);
            this.m_73434_(p_72188_, f_72324_, 26, 2, 21, p_72190_);
            this.m_73434_(p_72188_, f_72324_, 31, 2, 21, p_72190_);
            this.m_73434_(p_72188_, f_72324_, 25, 1, 21, p_72190_);
            this.m_73434_(p_72188_, f_72324_, 32, 1, 21, p_72190_);

            for(int i = 0; i < 7; ++i) {
               this.m_73434_(p_72188_, f_72325_, 28 - i, 6 + i, 21, p_72190_);
               this.m_73434_(p_72188_, f_72325_, 29 + i, 6 + i, 21, p_72190_);
            }

            for(int j = 0; j < 4; ++j) {
               this.m_73434_(p_72188_, f_72325_, 28 - j, 9 + j, 21, p_72190_);
               this.m_73434_(p_72188_, f_72325_, 29 + j, 9 + j, 21, p_72190_);
            }

            this.m_73434_(p_72188_, f_72325_, 28, 12, 21, p_72190_);
            this.m_73434_(p_72188_, f_72325_, 29, 12, 21, p_72190_);

            for(int k = 0; k < 3; ++k) {
               this.m_73434_(p_72188_, f_72325_, 22 - k * 2, 8, 21, p_72190_);
               this.m_73434_(p_72188_, f_72325_, 22 - k * 2, 9, 21, p_72190_);
               this.m_73434_(p_72188_, f_72325_, 35 + k * 2, 8, 21, p_72190_);
               this.m_73434_(p_72188_, f_72325_, 35 + k * 2, 9, 21, p_72190_);
            }

            this.m_72360_(p_72188_, p_72190_, 15, 13, 21, 42, 15, 21);
            this.m_72360_(p_72188_, p_72190_, 15, 1, 21, 15, 6, 21);
            this.m_72360_(p_72188_, p_72190_, 16, 1, 21, 16, 5, 21);
            this.m_72360_(p_72188_, p_72190_, 17, 1, 21, 20, 4, 21);
            this.m_72360_(p_72188_, p_72190_, 21, 1, 21, 21, 3, 21);
            this.m_72360_(p_72188_, p_72190_, 22, 1, 21, 22, 2, 21);
            this.m_72360_(p_72188_, p_72190_, 23, 1, 21, 24, 1, 21);
            this.m_72360_(p_72188_, p_72190_, 42, 1, 21, 42, 6, 21);
            this.m_72360_(p_72188_, p_72190_, 41, 1, 21, 41, 5, 21);
            this.m_72360_(p_72188_, p_72190_, 37, 1, 21, 40, 4, 21);
            this.m_72360_(p_72188_, p_72190_, 36, 1, 21, 36, 3, 21);
            this.m_72360_(p_72188_, p_72190_, 33, 1, 21, 34, 1, 21);
            this.m_72360_(p_72188_, p_72190_, 35, 1, 21, 35, 2, 21);
         }

      }

      private void m_72191_(WorldGenLevel p_72192_, Random p_72193_, BoundingBox p_72194_) {
         if (this.m_72385_(p_72194_, 21, 21, 36, 36)) {
            this.m_73441_(p_72192_, p_72194_, 21, 0, 22, 36, 0, 36, f_72323_, f_72323_, false);
            this.m_72360_(p_72192_, p_72194_, 21, 1, 22, 36, 23, 36);

            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72192_, p_72194_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, f_72324_, f_72324_, false);
               this.m_73441_(p_72192_, p_72194_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, f_72324_, f_72324_, false);
               this.m_73441_(p_72192_, p_72194_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, f_72324_, f_72324_, false);
               this.m_73441_(p_72192_, p_72194_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, f_72324_, f_72324_, false);
            }

            this.m_73441_(p_72192_, p_72194_, 25, 16, 25, 32, 16, 32, f_72323_, f_72323_, false);
            this.m_73441_(p_72192_, p_72194_, 25, 17, 25, 25, 19, 25, f_72324_, f_72324_, false);
            this.m_73441_(p_72192_, p_72194_, 32, 17, 25, 32, 19, 25, f_72324_, f_72324_, false);
            this.m_73441_(p_72192_, p_72194_, 25, 17, 32, 25, 19, 32, f_72324_, f_72324_, false);
            this.m_73441_(p_72192_, p_72194_, 32, 17, 32, 32, 19, 32, f_72324_, f_72324_, false);
            this.m_73434_(p_72192_, f_72324_, 26, 20, 26, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 27, 21, 27, p_72194_);
            this.m_73434_(p_72192_, f_72327_, 27, 20, 27, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 26, 20, 31, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 27, 21, 30, p_72194_);
            this.m_73434_(p_72192_, f_72327_, 27, 20, 30, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 31, 20, 31, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 30, 21, 30, p_72194_);
            this.m_73434_(p_72192_, f_72327_, 30, 20, 30, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 31, 20, 26, p_72194_);
            this.m_73434_(p_72192_, f_72324_, 30, 21, 27, p_72194_);
            this.m_73434_(p_72192_, f_72327_, 30, 20, 27, p_72194_);
            this.m_73441_(p_72192_, p_72194_, 28, 21, 27, 29, 21, 27, f_72323_, f_72323_, false);
            this.m_73441_(p_72192_, p_72194_, 27, 21, 28, 27, 21, 29, f_72323_, f_72323_, false);
            this.m_73441_(p_72192_, p_72194_, 28, 21, 30, 29, 21, 30, f_72323_, f_72323_, false);
            this.m_73441_(p_72192_, p_72194_, 30, 21, 28, 30, 21, 29, f_72323_, f_72323_, false);
         }

      }

      private void m_72195_(WorldGenLevel p_72196_, Random p_72197_, BoundingBox p_72198_) {
         if (this.m_72385_(p_72198_, 0, 21, 6, 58)) {
            this.m_73441_(p_72196_, p_72198_, 0, 0, 21, 6, 0, 57, f_72323_, f_72323_, false);
            this.m_72360_(p_72196_, p_72198_, 0, 1, 21, 6, 7, 57);
            this.m_73441_(p_72196_, p_72198_, 4, 4, 21, 6, 4, 53, f_72323_, f_72323_, false);

            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72196_, p_72198_, i, i + 1, 21, i, i + 1, 57 - i, f_72324_, f_72324_, false);
            }

            for(int j = 23; j < 53; j += 3) {
               this.m_73434_(p_72196_, f_72326_, 5, 5, j, p_72198_);
            }

            this.m_73434_(p_72196_, f_72326_, 5, 5, 52, p_72198_);

            for(int k = 0; k < 4; ++k) {
               this.m_73441_(p_72196_, p_72198_, k, k + 1, 21, k, k + 1, 57 - k, f_72324_, f_72324_, false);
            }

            this.m_73441_(p_72196_, p_72198_, 4, 1, 52, 6, 3, 52, f_72323_, f_72323_, false);
            this.m_73441_(p_72196_, p_72198_, 5, 1, 51, 5, 3, 53, f_72323_, f_72323_, false);
         }

         if (this.m_72385_(p_72198_, 51, 21, 58, 58)) {
            this.m_73441_(p_72196_, p_72198_, 51, 0, 21, 57, 0, 57, f_72323_, f_72323_, false);
            this.m_72360_(p_72196_, p_72198_, 51, 1, 21, 57, 7, 57);
            this.m_73441_(p_72196_, p_72198_, 51, 4, 21, 53, 4, 53, f_72323_, f_72323_, false);

            for(int l = 0; l < 4; ++l) {
               this.m_73441_(p_72196_, p_72198_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, f_72324_, f_72324_, false);
            }

            for(int i1 = 23; i1 < 53; i1 += 3) {
               this.m_73434_(p_72196_, f_72326_, 52, 5, i1, p_72198_);
            }

            this.m_73434_(p_72196_, f_72326_, 52, 5, 52, p_72198_);
            this.m_73441_(p_72196_, p_72198_, 51, 1, 52, 53, 3, 52, f_72323_, f_72323_, false);
            this.m_73441_(p_72196_, p_72198_, 52, 1, 51, 52, 3, 53, f_72323_, f_72323_, false);
         }

         if (this.m_72385_(p_72198_, 0, 51, 57, 57)) {
            this.m_73441_(p_72196_, p_72198_, 7, 0, 51, 50, 0, 57, f_72323_, f_72323_, false);
            this.m_72360_(p_72196_, p_72198_, 7, 1, 51, 50, 10, 57);

            for(int j1 = 0; j1 < 4; ++j1) {
               this.m_73441_(p_72196_, p_72198_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, f_72324_, f_72324_, false);
            }
         }

      }

      private void m_72199_(WorldGenLevel p_72200_, Random p_72201_, BoundingBox p_72202_) {
         if (this.m_72385_(p_72202_, 7, 21, 13, 50)) {
            this.m_73441_(p_72200_, p_72202_, 7, 0, 21, 13, 0, 50, f_72323_, f_72323_, false);
            this.m_72360_(p_72200_, p_72202_, 7, 1, 21, 13, 10, 50);
            this.m_73441_(p_72200_, p_72202_, 11, 8, 21, 13, 8, 53, f_72323_, f_72323_, false);

            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72200_, p_72202_, i + 7, i + 5, 21, i + 7, i + 5, 54, f_72324_, f_72324_, false);
            }

            for(int j = 21; j <= 45; j += 3) {
               this.m_73434_(p_72200_, f_72326_, 12, 9, j, p_72202_);
            }
         }

         if (this.m_72385_(p_72202_, 44, 21, 50, 54)) {
            this.m_73441_(p_72200_, p_72202_, 44, 0, 21, 50, 0, 50, f_72323_, f_72323_, false);
            this.m_72360_(p_72200_, p_72202_, 44, 1, 21, 50, 10, 50);
            this.m_73441_(p_72200_, p_72202_, 44, 8, 21, 46, 8, 53, f_72323_, f_72323_, false);

            for(int k = 0; k < 4; ++k) {
               this.m_73441_(p_72200_, p_72202_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, f_72324_, f_72324_, false);
            }

            for(int l = 21; l <= 45; l += 3) {
               this.m_73434_(p_72200_, f_72326_, 45, 9, l, p_72202_);
            }
         }

         if (this.m_72385_(p_72202_, 8, 44, 49, 54)) {
            this.m_73441_(p_72200_, p_72202_, 14, 0, 44, 43, 0, 50, f_72323_, f_72323_, false);
            this.m_72360_(p_72200_, p_72202_, 14, 1, 44, 43, 10, 50);

            for(int i1 = 12; i1 <= 45; i1 += 3) {
               this.m_73434_(p_72200_, f_72326_, i1, 9, 45, p_72202_);
               this.m_73434_(p_72200_, f_72326_, i1, 9, 52, p_72202_);
               if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45) {
                  this.m_73434_(p_72200_, f_72326_, i1, 9, 47, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 9, 50, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 10, 45, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 10, 46, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 10, 51, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 10, 52, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 11, 47, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 11, 50, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 12, 48, p_72202_);
                  this.m_73434_(p_72200_, f_72326_, i1, 12, 49, p_72202_);
               }
            }

            for(int j1 = 0; j1 < 3; ++j1) {
               this.m_73441_(p_72200_, p_72202_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, f_72323_, f_72323_, false);
            }

            this.m_73441_(p_72200_, p_72202_, 11, 8, 54, 46, 8, 54, f_72324_, f_72324_, false);
            this.m_73441_(p_72200_, p_72202_, 14, 8, 44, 43, 8, 53, f_72323_, f_72323_, false);
         }

      }

      private void m_72203_(WorldGenLevel p_72204_, Random p_72205_, BoundingBox p_72206_) {
         if (this.m_72385_(p_72206_, 14, 21, 20, 43)) {
            this.m_73441_(p_72204_, p_72206_, 14, 0, 21, 20, 0, 43, f_72323_, f_72323_, false);
            this.m_72360_(p_72204_, p_72206_, 14, 1, 22, 20, 14, 43);
            this.m_73441_(p_72204_, p_72206_, 18, 12, 22, 20, 12, 39, f_72323_, f_72323_, false);
            this.m_73441_(p_72204_, p_72206_, 18, 12, 21, 20, 12, 21, f_72324_, f_72324_, false);

            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72204_, p_72206_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, f_72324_, f_72324_, false);
            }

            for(int j = 23; j <= 39; j += 3) {
               this.m_73434_(p_72204_, f_72326_, 19, 13, j, p_72206_);
            }
         }

         if (this.m_72385_(p_72206_, 37, 21, 43, 43)) {
            this.m_73441_(p_72204_, p_72206_, 37, 0, 21, 43, 0, 43, f_72323_, f_72323_, false);
            this.m_72360_(p_72204_, p_72206_, 37, 1, 22, 43, 14, 43);
            this.m_73441_(p_72204_, p_72206_, 37, 12, 22, 39, 12, 39, f_72323_, f_72323_, false);
            this.m_73441_(p_72204_, p_72206_, 37, 12, 21, 39, 12, 21, f_72324_, f_72324_, false);

            for(int k = 0; k < 4; ++k) {
               this.m_73441_(p_72204_, p_72206_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, f_72324_, f_72324_, false);
            }

            for(int l = 23; l <= 39; l += 3) {
               this.m_73434_(p_72204_, f_72326_, 38, 13, l, p_72206_);
            }
         }

         if (this.m_72385_(p_72206_, 15, 37, 42, 43)) {
            this.m_73441_(p_72204_, p_72206_, 21, 0, 37, 36, 0, 43, f_72323_, f_72323_, false);
            this.m_72360_(p_72204_, p_72206_, 21, 1, 37, 36, 14, 43);
            this.m_73441_(p_72204_, p_72206_, 21, 12, 37, 36, 12, 39, f_72323_, f_72323_, false);

            for(int i1 = 0; i1 < 4; ++i1) {
               this.m_73441_(p_72204_, p_72206_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, f_72324_, f_72324_, false);
            }

            for(int j1 = 21; j1 <= 36; j1 += 3) {
               this.m_73434_(p_72204_, f_72326_, j1, 13, 38, p_72206_);
            }
         }

      }
   }

   interface MonumentRoomFitter {
      boolean m_7725_(OceanMonumentPieces.RoomDefinition p_72207_);

      OceanMonumentPieces.OceanMonumentPiece m_7924_(Direction p_72208_, OceanMonumentPieces.RoomDefinition p_72209_, Random p_72210_);
   }

   public static class OceanMonumentCoreRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentCoreRoom(Direction p_72215_, OceanMonumentPieces.RoomDefinition p_72216_) {
         super(StructurePieceType.f_67119_, 1, p_72215_, p_72216_, 2, 2, 2);
      }

      public OceanMonumentCoreRoom(ServerLevel p_162997_, CompoundTag p_162998_) {
         super(StructurePieceType.f_67119_, p_162998_);
      }

      public boolean m_7832_(WorldGenLevel p_72218_, StructureFeatureManager p_72219_, ChunkGenerator p_72220_, Random p_72221_, BoundingBox p_72222_, ChunkPos p_72223_, BlockPos p_72224_) {
         this.m_72369_(p_72218_, p_72222_, 1, 8, 0, 14, 8, 14, f_72323_);
         int i = 7;
         BlockState blockstate = f_72324_;
         this.m_73441_(p_72218_, p_72222_, 0, 7, 0, 0, 7, 15, blockstate, blockstate, false);
         this.m_73441_(p_72218_, p_72222_, 15, 7, 0, 15, 7, 15, blockstate, blockstate, false);
         this.m_73441_(p_72218_, p_72222_, 1, 7, 0, 15, 7, 0, blockstate, blockstate, false);
         this.m_73441_(p_72218_, p_72222_, 1, 7, 15, 14, 7, 15, blockstate, blockstate, false);

         for(int k = 1; k <= 6; ++k) {
            blockstate = f_72324_;
            if (k == 2 || k == 6) {
               blockstate = f_72323_;
            }

            for(int j = 0; j <= 15; j += 15) {
               this.m_73441_(p_72218_, p_72222_, j, k, 0, j, k, 1, blockstate, blockstate, false);
               this.m_73441_(p_72218_, p_72222_, j, k, 6, j, k, 9, blockstate, blockstate, false);
               this.m_73441_(p_72218_, p_72222_, j, k, 14, j, k, 15, blockstate, blockstate, false);
            }

            this.m_73441_(p_72218_, p_72222_, 1, k, 0, 1, k, 0, blockstate, blockstate, false);
            this.m_73441_(p_72218_, p_72222_, 6, k, 0, 9, k, 0, blockstate, blockstate, false);
            this.m_73441_(p_72218_, p_72222_, 14, k, 0, 14, k, 0, blockstate, blockstate, false);
            this.m_73441_(p_72218_, p_72222_, 1, k, 15, 14, k, 15, blockstate, blockstate, false);
         }

         this.m_73441_(p_72218_, p_72222_, 6, 3, 6, 9, 6, 9, f_72325_, f_72325_, false);
         this.m_73441_(p_72218_, p_72222_, 7, 4, 7, 8, 5, 8, Blocks.f_50074_.m_49966_(), Blocks.f_50074_.m_49966_(), false);

         for(int l = 3; l <= 6; l += 3) {
            for(int i1 = 6; i1 <= 9; i1 += 3) {
               this.m_73434_(p_72218_, f_72327_, i1, l, 6, p_72222_);
               this.m_73434_(p_72218_, f_72327_, i1, l, 9, p_72222_);
            }
         }

         this.m_73441_(p_72218_, p_72222_, 5, 1, 6, 5, 2, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 5, 1, 9, 5, 2, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 1, 6, 10, 2, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 1, 9, 10, 2, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 6, 1, 5, 6, 2, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 9, 1, 5, 9, 2, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 6, 1, 10, 6, 2, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 9, 1, 10, 9, 2, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 5, 2, 5, 5, 6, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 5, 2, 10, 5, 6, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 2, 5, 10, 6, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 2, 10, 10, 6, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 5, 7, 1, 5, 7, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 7, 1, 10, 7, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 5, 7, 9, 5, 7, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 10, 7, 9, 10, 7, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 1, 7, 5, 6, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 1, 7, 10, 6, 7, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 9, 7, 5, 14, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 9, 7, 10, 14, 7, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 2, 1, 2, 2, 1, 3, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 3, 1, 2, 3, 1, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 13, 1, 2, 13, 1, 3, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 12, 1, 2, 12, 1, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 2, 1, 12, 2, 1, 13, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 3, 1, 13, 3, 1, 13, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 13, 1, 12, 13, 1, 13, f_72324_, f_72324_, false);
         this.m_73441_(p_72218_, p_72222_, 12, 1, 13, 12, 1, 13, f_72324_, f_72324_, false);
         return true;
      }
   }

   public static class OceanMonumentDoubleXRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleXRoom(Direction p_72229_, OceanMonumentPieces.RoomDefinition p_72230_) {
         super(StructurePieceType.f_67120_, 1, p_72229_, p_72230_, 2, 1, 1);
      }

      public OceanMonumentDoubleXRoom(ServerLevel p_163000_, CompoundTag p_163001_) {
         super(StructurePieceType.f_67120_, p_163001_);
      }

      public boolean m_7832_(WorldGenLevel p_72232_, StructureFeatureManager p_72233_, ChunkGenerator p_72234_, Random p_72235_, BoundingBox p_72236_, ChunkPos p_72237_, BlockPos p_72238_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.f_72334_.f_72444_[Direction.EAST.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.f_72334_;
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72232_, p_72236_, 8, 0, oceanmonumentpieces$roomdefinition.f_72445_[Direction.DOWN.m_122411_()]);
            this.m_72379_(p_72232_, p_72236_, 0, 0, oceanmonumentpieces$roomdefinition1.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72232_, p_72236_, 1, 4, 1, 7, 4, 6, f_72323_);
         }

         if (oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72232_, p_72236_, 8, 4, 1, 14, 4, 6, f_72323_);
         }

         this.m_73441_(p_72232_, p_72236_, 0, 3, 0, 0, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 15, 3, 0, 15, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 3, 0, 15, 3, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 3, 7, 14, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 0, 2, 0, 0, 2, 7, f_72323_, f_72323_, false);
         this.m_73441_(p_72232_, p_72236_, 15, 2, 0, 15, 2, 7, f_72323_, f_72323_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 2, 0, 15, 2, 0, f_72323_, f_72323_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 2, 7, 14, 2, 7, f_72323_, f_72323_, false);
         this.m_73441_(p_72232_, p_72236_, 0, 1, 0, 0, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 15, 1, 0, 15, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 1, 0, 15, 1, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 1, 1, 7, 14, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 5, 1, 0, 10, 1, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72232_, p_72236_, 6, 2, 0, 9, 2, 3, f_72323_, f_72323_, false);
         this.m_73441_(p_72232_, p_72236_, 5, 3, 0, 10, 3, 4, f_72324_, f_72324_, false);
         this.m_73434_(p_72232_, f_72327_, 6, 2, 3, p_72236_);
         this.m_73434_(p_72232_, f_72327_, 9, 2, 3, p_72236_);
         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 3, 1, 7, 4, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 11, 1, 0, 12, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 11, 1, 7, 12, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72232_, p_72236_, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleXYRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleXYRoom(Direction p_72243_, OceanMonumentPieces.RoomDefinition p_72244_) {
         super(StructurePieceType.f_67121_, 1, p_72243_, p_72244_, 2, 2, 1);
      }

      public OceanMonumentDoubleXYRoom(ServerLevel p_163003_, CompoundTag p_163004_) {
         super(StructurePieceType.f_67121_, p_163004_);
      }

      public boolean m_7832_(WorldGenLevel p_72246_, StructureFeatureManager p_72247_, ChunkGenerator p_72248_, Random p_72249_, BoundingBox p_72250_, ChunkPos p_72251_, BlockPos p_72252_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.f_72334_.f_72444_[Direction.EAST.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.f_72334_;
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = oceanmonumentpieces$roomdefinition1.f_72444_[Direction.UP.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 = oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()];
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72246_, p_72250_, 8, 0, oceanmonumentpieces$roomdefinition.f_72445_[Direction.DOWN.m_122411_()]);
            this.m_72379_(p_72246_, p_72250_, 0, 0, oceanmonumentpieces$roomdefinition1.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72246_, p_72250_, 1, 8, 1, 7, 8, 6, f_72323_);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72246_, p_72250_, 8, 8, 1, 14, 8, 6, f_72323_);
         }

         for(int i = 1; i <= 7; ++i) {
            BlockState blockstate = f_72324_;
            if (i == 2 || i == 6) {
               blockstate = f_72323_;
            }

            this.m_73441_(p_72246_, p_72250_, 0, i, 0, 0, i, 7, blockstate, blockstate, false);
            this.m_73441_(p_72246_, p_72250_, 15, i, 0, 15, i, 7, blockstate, blockstate, false);
            this.m_73441_(p_72246_, p_72250_, 1, i, 0, 15, i, 0, blockstate, blockstate, false);
            this.m_73441_(p_72246_, p_72250_, 1, i, 7, 14, i, 7, blockstate, blockstate, false);
         }

         this.m_73441_(p_72246_, p_72250_, 2, 1, 3, 2, 7, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 3, 1, 2, 4, 7, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 3, 1, 5, 4, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 13, 1, 3, 13, 7, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 11, 1, 2, 12, 7, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 11, 1, 5, 12, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 5, 1, 3, 5, 3, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 10, 1, 3, 10, 3, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 5, 7, 2, 10, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 5, 5, 2, 5, 7, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 10, 5, 2, 10, 7, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 5, 5, 5, 5, 7, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 10, 5, 5, 10, 7, 5, f_72324_, f_72324_, false);
         this.m_73434_(p_72246_, f_72324_, 6, 6, 2, p_72250_);
         this.m_73434_(p_72246_, f_72324_, 9, 6, 2, p_72250_);
         this.m_73434_(p_72246_, f_72324_, 6, 6, 5, p_72250_);
         this.m_73434_(p_72246_, f_72324_, 9, 6, 5, p_72250_);
         this.m_73441_(p_72246_, p_72250_, 5, 4, 3, 6, 4, 4, f_72324_, f_72324_, false);
         this.m_73441_(p_72246_, p_72250_, 9, 4, 3, 10, 4, 4, f_72324_, f_72324_, false);
         this.m_73434_(p_72246_, f_72327_, 5, 4, 2, p_72250_);
         this.m_73434_(p_72246_, f_72327_, 5, 4, 5, p_72250_);
         this.m_73434_(p_72246_, f_72327_, 10, 4, 2, p_72250_);
         this.m_73434_(p_72246_, f_72327_, 10, 4, 5, p_72250_);
         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 3, 1, 7, 4, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 11, 1, 0, 12, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 11, 1, 7, 12, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 15, 1, 3, 15, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 3, 5, 0, 4, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 3, 5, 7, 4, 6, 7);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 0, 5, 3, 0, 6, 4);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 11, 5, 0, 12, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 11, 5, 7, 12, 6, 7);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72246_, p_72250_, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleYRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleYRoom(Direction p_72257_, OceanMonumentPieces.RoomDefinition p_72258_) {
         super(StructurePieceType.f_67122_, 1, p_72257_, p_72258_, 1, 2, 1);
      }

      public OceanMonumentDoubleYRoom(ServerLevel p_163006_, CompoundTag p_163007_) {
         super(StructurePieceType.f_67122_, p_163007_);
      }

      public boolean m_7832_(WorldGenLevel p_72260_, StructureFeatureManager p_72261_, ChunkGenerator p_72262_, Random p_72263_, BoundingBox p_72264_, ChunkPos p_72265_, BlockPos p_72266_) {
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72260_, p_72264_, 0, 0, this.f_72334_.f_72445_[Direction.DOWN.m_122411_()]);
         }

         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.f_72334_.f_72444_[Direction.UP.m_122411_()];
         if (oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72260_, p_72264_, 1, 8, 1, 6, 8, 6, f_72323_);
         }

         this.m_73441_(p_72260_, p_72264_, 0, 4, 0, 0, 4, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 7, 4, 0, 7, 4, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 1, 4, 0, 6, 4, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 1, 4, 7, 6, 4, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 2, 4, 1, 2, 4, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 1, 4, 2, 1, 4, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 5, 4, 1, 5, 4, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 6, 4, 2, 6, 4, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 2, 4, 5, 2, 4, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 1, 4, 5, 1, 4, 5, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 5, 4, 5, 5, 4, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72260_, p_72264_, 6, 4, 5, 6, 4, 5, f_72324_, f_72324_, false);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.f_72334_;

         for(int i = 1; i <= 5; i += 4) {
            int j = 0;
            if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.SOUTH.m_122411_()]) {
               this.m_73441_(p_72260_, p_72264_, 2, i, j, 2, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 5, i, j, 5, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 3, i + 2, j, 4, i + 2, j, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72260_, p_72264_, 0, i, j, 7, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 0, i + 1, j, 7, i + 1, j, f_72323_, f_72323_, false);
            }

            j = 7;
            if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.NORTH.m_122411_()]) {
               this.m_73441_(p_72260_, p_72264_, 2, i, j, 2, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 5, i, j, 5, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 3, i + 2, j, 4, i + 2, j, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72260_, p_72264_, 0, i, j, 7, i + 2, j, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, 0, i + 1, j, 7, i + 1, j, f_72323_, f_72323_, false);
            }

            int k = 0;
            if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.WEST.m_122411_()]) {
               this.m_73441_(p_72260_, p_72264_, k, i, 2, k, i + 2, 2, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i, 5, k, i + 2, 5, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i + 2, 3, k, i + 2, 4, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72260_, p_72264_, k, i, 0, k, i + 2, 7, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i + 1, 0, k, i + 1, 7, f_72323_, f_72323_, false);
            }

            k = 7;
            if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.EAST.m_122411_()]) {
               this.m_73441_(p_72260_, p_72264_, k, i, 2, k, i + 2, 2, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i, 5, k, i + 2, 5, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i + 2, 3, k, i + 2, 4, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72260_, p_72264_, k, i, 0, k, i + 2, 7, f_72324_, f_72324_, false);
               this.m_73441_(p_72260_, p_72264_, k, i + 1, 0, k, i + 1, 7, f_72323_, f_72323_, false);
            }

            oceanmonumentpieces$roomdefinition1 = oceanmonumentpieces$roomdefinition;
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleYZRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleYZRoom(Direction p_72271_, OceanMonumentPieces.RoomDefinition p_72272_) {
         super(StructurePieceType.f_67123_, 1, p_72271_, p_72272_, 1, 2, 2);
      }

      public OceanMonumentDoubleYZRoom(ServerLevel p_163009_, CompoundTag p_163010_) {
         super(StructurePieceType.f_67123_, p_163010_);
      }

      public boolean m_7832_(WorldGenLevel p_72274_, StructureFeatureManager p_72275_, ChunkGenerator p_72276_, Random p_72277_, BoundingBox p_72278_, ChunkPos p_72279_, BlockPos p_72280_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.f_72334_.f_72444_[Direction.NORTH.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.f_72334_;
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 = oceanmonumentpieces$roomdefinition1.f_72444_[Direction.UP.m_122411_()];
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72274_, p_72278_, 0, 8, oceanmonumentpieces$roomdefinition.f_72445_[Direction.DOWN.m_122411_()]);
            this.m_72379_(p_72274_, p_72278_, 0, 0, oceanmonumentpieces$roomdefinition1.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72274_, p_72278_, 1, 8, 1, 6, 8, 7, f_72323_);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72274_, p_72278_, 1, 8, 8, 6, 8, 14, f_72323_);
         }

         for(int i = 1; i <= 7; ++i) {
            BlockState blockstate = f_72324_;
            if (i == 2 || i == 6) {
               blockstate = f_72323_;
            }

            this.m_73441_(p_72274_, p_72278_, 0, i, 0, 0, i, 15, blockstate, blockstate, false);
            this.m_73441_(p_72274_, p_72278_, 7, i, 0, 7, i, 15, blockstate, blockstate, false);
            this.m_73441_(p_72274_, p_72278_, 1, i, 0, 6, i, 0, blockstate, blockstate, false);
            this.m_73441_(p_72274_, p_72278_, 1, i, 15, 6, i, 15, blockstate, blockstate, false);
         }

         for(int j = 1; j <= 7; ++j) {
            BlockState blockstate1 = f_72325_;
            if (j == 2 || j == 6) {
               blockstate1 = f_72327_;
            }

            this.m_73441_(p_72274_, p_72278_, 3, j, 7, 4, j, 8, blockstate1, blockstate1, false);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 7, 1, 3, 7, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 3, 1, 15, 4, 2, 15);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 0, 1, 11, 0, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 7, 1, 11, 7, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 3, 5, 0, 4, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 7, 5, 3, 7, 6, 4);
            this.m_73441_(p_72274_, p_72278_, 5, 4, 2, 6, 4, 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 6, 1, 2, 6, 3, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 6, 1, 5, 6, 3, 5, f_72324_, f_72324_, false);
         }

         if (oceanmonumentpieces$roomdefinition3.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 0, 5, 3, 0, 6, 4);
            this.m_73441_(p_72274_, p_72278_, 1, 4, 2, 2, 4, 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 1, 1, 2, 1, 3, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 1, 1, 5, 1, 3, 5, f_72324_, f_72324_, false);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 3, 5, 15, 4, 6, 15);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 0, 5, 11, 0, 6, 12);
            this.m_73441_(p_72274_, p_72278_, 1, 4, 10, 2, 4, 13, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 1, 1, 10, 1, 3, 10, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 1, 1, 13, 1, 3, 13, f_72324_, f_72324_, false);
         }

         if (oceanmonumentpieces$roomdefinition2.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72274_, p_72278_, 7, 5, 11, 7, 6, 12);
            this.m_73441_(p_72274_, p_72278_, 5, 4, 10, 6, 4, 13, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 6, 1, 10, 6, 3, 10, f_72324_, f_72324_, false);
            this.m_73441_(p_72274_, p_72278_, 6, 1, 13, 6, 3, 13, f_72324_, f_72324_, false);
         }

         return true;
      }
   }

   public static class OceanMonumentDoubleZRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentDoubleZRoom(Direction p_72285_, OceanMonumentPieces.RoomDefinition p_72286_) {
         super(StructurePieceType.f_67124_, 1, p_72285_, p_72286_, 1, 1, 2);
      }

      public OceanMonumentDoubleZRoom(ServerLevel p_163012_, CompoundTag p_163013_) {
         super(StructurePieceType.f_67124_, p_163013_);
      }

      public boolean m_7832_(WorldGenLevel p_72288_, StructureFeatureManager p_72289_, ChunkGenerator p_72290_, Random p_72291_, BoundingBox p_72292_, ChunkPos p_72293_, BlockPos p_72294_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.f_72334_.f_72444_[Direction.NORTH.m_122411_()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.f_72334_;
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72288_, p_72292_, 0, 8, oceanmonumentpieces$roomdefinition.f_72445_[Direction.DOWN.m_122411_()]);
            this.m_72379_(p_72288_, p_72292_, 0, 0, oceanmonumentpieces$roomdefinition1.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72288_, p_72292_, 1, 4, 1, 6, 4, 7, f_72323_);
         }

         if (oceanmonumentpieces$roomdefinition.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72288_, p_72292_, 1, 4, 8, 6, 4, 14, f_72323_);
         }

         this.m_73441_(p_72288_, p_72292_, 0, 3, 0, 0, 3, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 7, 3, 0, 7, 3, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 3, 0, 7, 3, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 3, 15, 6, 3, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 0, 2, 0, 0, 2, 15, f_72323_, f_72323_, false);
         this.m_73441_(p_72288_, p_72292_, 7, 2, 0, 7, 2, 15, f_72323_, f_72323_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 2, 0, 7, 2, 0, f_72323_, f_72323_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 2, 15, 6, 2, 15, f_72323_, f_72323_, false);
         this.m_73441_(p_72288_, p_72292_, 0, 1, 0, 0, 1, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 7, 1, 0, 7, 1, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 1, 0, 7, 1, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 1, 15, 6, 1, 15, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 1, 1, 1, 1, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 6, 1, 1, 6, 1, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 3, 1, 1, 3, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 6, 3, 1, 6, 3, 2, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 1, 13, 1, 1, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 6, 1, 13, 6, 1, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 1, 3, 13, 1, 3, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 6, 3, 13, 6, 3, 14, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 2, 1, 6, 2, 3, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 5, 1, 6, 5, 3, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 2, 1, 9, 2, 3, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 5, 1, 9, 5, 3, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 3, 2, 6, 4, 2, 6, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 3, 2, 9, 4, 2, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 2, 2, 7, 2, 2, 8, f_72324_, f_72324_, false);
         this.m_73441_(p_72288_, p_72292_, 5, 2, 7, 5, 2, 8, f_72324_, f_72324_, false);
         this.m_73434_(p_72288_, f_72327_, 2, 2, 5, p_72292_);
         this.m_73434_(p_72288_, f_72327_, 5, 2, 5, p_72292_);
         this.m_73434_(p_72288_, f_72327_, 2, 2, 10, p_72292_);
         this.m_73434_(p_72288_, f_72327_, 5, 2, 10, p_72292_);
         this.m_73434_(p_72288_, f_72324_, 2, 3, 5, p_72292_);
         this.m_73434_(p_72288_, f_72324_, 5, 3, 5, p_72292_);
         this.m_73434_(p_72288_, f_72324_, 2, 3, 10, p_72292_);
         this.m_73434_(p_72288_, f_72324_, 5, 3, 10, p_72292_);
         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 7, 1, 3, 7, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition1.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 3, 1, 15, 4, 2, 15);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 0, 1, 11, 0, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72288_, p_72292_, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   public static class OceanMonumentEntryRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentEntryRoom(Direction p_72299_, OceanMonumentPieces.RoomDefinition p_72300_) {
         super(StructurePieceType.f_67125_, 1, p_72299_, p_72300_, 1, 1, 1);
      }

      public OceanMonumentEntryRoom(ServerLevel p_163015_, CompoundTag p_163016_) {
         super(StructurePieceType.f_67125_, p_163016_);
      }

      public boolean m_7832_(WorldGenLevel p_72302_, StructureFeatureManager p_72303_, ChunkGenerator p_72304_, Random p_72305_, BoundingBox p_72306_, ChunkPos p_72307_, BlockPos p_72308_) {
         this.m_73441_(p_72302_, p_72306_, 0, 3, 0, 2, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 5, 3, 0, 7, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 0, 2, 0, 1, 2, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 6, 2, 0, 7, 2, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 0, 1, 0, 0, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 7, 1, 0, 7, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 0, 1, 7, 7, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 1, 1, 0, 2, 3, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72302_, p_72306_, 5, 1, 0, 6, 3, 0, f_72324_, f_72324_, false);
         if (this.f_72334_.f_72445_[Direction.NORTH.m_122411_()]) {
            this.m_72360_(p_72302_, p_72306_, 3, 1, 7, 4, 2, 7);
         }

         if (this.f_72334_.f_72445_[Direction.WEST.m_122411_()]) {
            this.m_72360_(p_72302_, p_72306_, 0, 1, 3, 1, 2, 4);
         }

         if (this.f_72334_.f_72445_[Direction.EAST.m_122411_()]) {
            this.m_72360_(p_72302_, p_72306_, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   public static class OceanMonumentPenthouse extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentPenthouse(Direction p_72313_, BoundingBox p_72314_) {
         super(StructurePieceType.f_67126_, p_72313_, 1, p_72314_);
      }

      public OceanMonumentPenthouse(ServerLevel p_163018_, CompoundTag p_163019_) {
         super(StructurePieceType.f_67126_, p_163019_);
      }

      public boolean m_7832_(WorldGenLevel p_72316_, StructureFeatureManager p_72317_, ChunkGenerator p_72318_, Random p_72319_, BoundingBox p_72320_, ChunkPos p_72321_, BlockPos p_72322_) {
         this.m_73441_(p_72316_, p_72320_, 2, -1, 2, 11, -1, 11, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 0, -1, 0, 1, -1, 11, f_72323_, f_72323_, false);
         this.m_73441_(p_72316_, p_72320_, 12, -1, 0, 13, -1, 11, f_72323_, f_72323_, false);
         this.m_73441_(p_72316_, p_72320_, 2, -1, 0, 11, -1, 1, f_72323_, f_72323_, false);
         this.m_73441_(p_72316_, p_72320_, 2, -1, 12, 11, -1, 13, f_72323_, f_72323_, false);
         this.m_73441_(p_72316_, p_72320_, 0, 0, 0, 0, 0, 13, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 13, 0, 0, 13, 0, 13, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 1, 0, 0, 12, 0, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 1, 0, 13, 12, 0, 13, f_72324_, f_72324_, false);

         for(int i = 2; i <= 11; i += 3) {
            this.m_73434_(p_72316_, f_72327_, 0, 0, i, p_72320_);
            this.m_73434_(p_72316_, f_72327_, 13, 0, i, p_72320_);
            this.m_73434_(p_72316_, f_72327_, i, 0, 0, p_72320_);
         }

         this.m_73441_(p_72316_, p_72320_, 2, 0, 3, 4, 0, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 9, 0, 3, 11, 0, 9, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 4, 0, 9, 9, 0, 11, f_72324_, f_72324_, false);
         this.m_73434_(p_72316_, f_72324_, 5, 0, 8, p_72320_);
         this.m_73434_(p_72316_, f_72324_, 8, 0, 8, p_72320_);
         this.m_73434_(p_72316_, f_72324_, 10, 0, 10, p_72320_);
         this.m_73434_(p_72316_, f_72324_, 3, 0, 10, p_72320_);
         this.m_73441_(p_72316_, p_72320_, 3, 0, 3, 3, 0, 7, f_72325_, f_72325_, false);
         this.m_73441_(p_72316_, p_72320_, 10, 0, 3, 10, 0, 7, f_72325_, f_72325_, false);
         this.m_73441_(p_72316_, p_72320_, 6, 0, 10, 7, 0, 10, f_72325_, f_72325_, false);
         int l = 3;

         for(int j = 0; j < 2; ++j) {
            for(int k = 2; k <= 8; k += 3) {
               this.m_73441_(p_72316_, p_72320_, l, 0, k, l, 2, k, f_72324_, f_72324_, false);
            }

            l = 10;
         }

         this.m_73441_(p_72316_, p_72320_, 5, 0, 10, 5, 2, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 8, 0, 10, 8, 2, 10, f_72324_, f_72324_, false);
         this.m_73441_(p_72316_, p_72320_, 6, -1, 7, 7, -1, 8, f_72325_, f_72325_, false);
         this.m_72360_(p_72316_, p_72320_, 6, -1, 3, 7, -1, 4);
         this.m_72354_(p_72316_, p_72320_, 6, 1, 6);
         return true;
      }
   }

   protected abstract static class OceanMonumentPiece extends StructurePiece {
      protected static final BlockState f_72323_ = Blocks.f_50377_.m_49966_();
      protected static final BlockState f_72324_ = Blocks.f_50378_.m_49966_();
      protected static final BlockState f_72325_ = Blocks.f_50379_.m_49966_();
      protected static final BlockState f_72326_ = f_72324_;
      protected static final BlockState f_72327_ = Blocks.f_50386_.m_49966_();
      protected static final boolean f_163020_ = true;
      protected static final BlockState f_72328_ = Blocks.f_49990_.m_49966_();
      protected static final Set<Block> f_72329_ = ImmutableSet.<Block>builder().add(Blocks.f_50126_).add(Blocks.f_50354_).add(Blocks.f_50568_).add(f_72328_.m_60734_()).build();
      protected static final int f_163021_ = 8;
      protected static final int f_163022_ = 8;
      protected static final int f_163023_ = 4;
      protected static final int f_163024_ = 5;
      protected static final int f_163025_ = 5;
      protected static final int f_163026_ = 3;
      protected static final int f_163027_ = 25;
      protected static final int f_163028_ = 75;
      protected static final int f_72330_ = m_72393_(2, 0, 0);
      protected static final int f_72331_ = m_72393_(2, 2, 0);
      protected static final int f_72332_ = m_72393_(0, 1, 0);
      protected static final int f_72333_ = m_72393_(4, 1, 0);
      protected static final int f_163029_ = 1001;
      protected static final int f_163030_ = 1002;
      protected static final int f_163031_ = 1003;
      protected OceanMonumentPieces.RoomDefinition f_72334_;

      protected static int m_72393_(int p_72394_, int p_72395_, int p_72396_) {
         return p_72395_ * 25 + p_72396_ * 5 + p_72394_;
      }

      public OceanMonumentPiece(StructurePieceType p_163033_, Direction p_163034_, int p_163035_, BoundingBox p_163036_) {
         super(p_163033_, p_163035_, p_163036_);
         this.m_73519_(p_163034_);
      }

      protected OceanMonumentPiece(StructurePieceType p_72340_, int p_72341_, Direction p_72342_, OceanMonumentPieces.RoomDefinition p_72343_, int p_72344_, int p_72345_, int p_72346_) {
         super(p_72340_, p_72341_, m_163040_(p_72342_, p_72343_, p_72344_, p_72345_, p_72346_));
         this.m_73519_(p_72342_);
         this.f_72334_ = p_72343_;
      }

      private static BoundingBox m_163040_(Direction p_163041_, OceanMonumentPieces.RoomDefinition p_163042_, int p_163043_, int p_163044_, int p_163045_) {
         int i = p_163042_.f_72443_;
         int j = i % 5;
         int k = i / 5 % 5;
         int l = i / 25;
         BoundingBox boundingbox = m_163541_(0, 0, 0, p_163041_, p_163043_ * 8, p_163044_ * 4, p_163045_ * 8);
         switch(p_163041_) {
         case NORTH:
            boundingbox.m_162367_(j * 8, l * 4, -(k + p_163045_) * 8 + 1);
            break;
         case SOUTH:
            boundingbox.m_162367_(j * 8, l * 4, k * 8);
            break;
         case WEST:
            boundingbox.m_162367_(-(k + p_163045_) * 8 + 1, l * 4, j * 8);
            break;
         case EAST:
         default:
            boundingbox.m_162367_(k * 8, l * 4, j * 8);
         }

         return boundingbox;
      }

      public OceanMonumentPiece(StructurePieceType p_72352_, CompoundTag p_72353_) {
         super(p_72352_, p_72353_);
      }

      protected void m_142347_(ServerLevel p_163038_, CompoundTag p_163039_) {
      }

      protected void m_72360_(WorldGenLevel p_72361_, BoundingBox p_72362_, int p_72363_, int p_72364_, int p_72365_, int p_72366_, int p_72367_, int p_72368_) {
         for(int i = p_72364_; i <= p_72367_; ++i) {
            for(int j = p_72363_; j <= p_72366_; ++j) {
               for(int k = p_72365_; k <= p_72368_; ++k) {
                  BlockState blockstate = this.m_73398_(p_72361_, j, i, k, p_72362_);
                  if (!f_72329_.contains(blockstate.m_60734_())) {
                     if (this.m_73544_(i) >= p_72361_.m_5736_() && blockstate != f_72328_) {
                        this.m_73434_(p_72361_, Blocks.f_50016_.m_49966_(), j, i, k, p_72362_);
                     } else {
                        this.m_73434_(p_72361_, f_72328_, j, i, k, p_72362_);
                     }
                  }
               }
            }
         }

      }

      protected void m_72379_(WorldGenLevel p_72380_, BoundingBox p_72381_, int p_72382_, int p_72383_, boolean p_72384_) {
         if (p_72384_) {
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 0, 0, p_72383_ + 0, p_72382_ + 2, 0, p_72383_ + 8 - 1, f_72323_, f_72323_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 5, 0, p_72383_ + 0, p_72382_ + 8 - 1, 0, p_72383_ + 8 - 1, f_72323_, f_72323_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 3, 0, p_72383_ + 0, p_72382_ + 4, 0, p_72383_ + 2, f_72323_, f_72323_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 3, 0, p_72383_ + 5, p_72382_ + 4, 0, p_72383_ + 8 - 1, f_72323_, f_72323_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 3, 0, p_72383_ + 2, p_72382_ + 4, 0, p_72383_ + 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 3, 0, p_72383_ + 5, p_72382_ + 4, 0, p_72383_ + 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 2, 0, p_72383_ + 3, p_72382_ + 2, 0, p_72383_ + 4, f_72324_, f_72324_, false);
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 5, 0, p_72383_ + 3, p_72382_ + 5, 0, p_72383_ + 4, f_72324_, f_72324_, false);
         } else {
            this.m_73441_(p_72380_, p_72381_, p_72382_ + 0, 0, p_72383_ + 0, p_72382_ + 8 - 1, 0, p_72383_ + 8 - 1, f_72323_, f_72323_, false);
         }

      }

      protected void m_72369_(WorldGenLevel p_72370_, BoundingBox p_72371_, int p_72372_, int p_72373_, int p_72374_, int p_72375_, int p_72376_, int p_72377_, BlockState p_72378_) {
         for(int i = p_72373_; i <= p_72376_; ++i) {
            for(int j = p_72372_; j <= p_72375_; ++j) {
               for(int k = p_72374_; k <= p_72377_; ++k) {
                  if (this.m_73398_(p_72370_, j, i, k, p_72371_) == f_72328_) {
                     this.m_73434_(p_72370_, p_72378_, j, i, k, p_72371_);
                  }
               }
            }
         }

      }

      protected boolean m_72385_(BoundingBox p_72386_, int p_72387_, int p_72388_, int p_72389_, int p_72390_) {
         int i = this.m_73392_(p_72387_, p_72388_);
         int j = this.m_73525_(p_72387_, p_72388_);
         int k = this.m_73392_(p_72389_, p_72390_);
         int l = this.m_73525_(p_72389_, p_72390_);
         return p_72386_.m_71019_(Math.min(i, k), Math.min(j, l), Math.max(i, k), Math.max(j, l));
      }

      protected boolean m_72354_(WorldGenLevel p_72355_, BoundingBox p_72356_, int p_72357_, int p_72358_, int p_72359_) {
         BlockPos blockpos = this.m_163582_(p_72357_, p_72358_, p_72359_);
         if (p_72356_.m_71051_(blockpos)) {
            ElderGuardian elderguardian = EntityType.f_20563_.m_20615_(p_72355_.m_6018_());
            elderguardian.m_5634_(elderguardian.m_21233_());
            elderguardian.m_7678_((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5D, 0.0F, 0.0F);
            elderguardian.m_6518_(p_72355_, p_72355_.m_6436_(elderguardian.m_142538_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
            p_72355_.m_47205_(elderguardian);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class OceanMonumentSimpleRoom extends OceanMonumentPieces.OceanMonumentPiece {
      private int f_72397_;

      public OceanMonumentSimpleRoom(Direction p_72402_, OceanMonumentPieces.RoomDefinition p_72403_, Random p_72404_) {
         super(StructurePieceType.f_67127_, 1, p_72402_, p_72403_, 1, 1, 1);
         this.f_72397_ = p_72404_.nextInt(3);
      }

      public OceanMonumentSimpleRoom(ServerLevel p_163047_, CompoundTag p_163048_) {
         super(StructurePieceType.f_67127_, p_163048_);
      }

      public boolean m_7832_(WorldGenLevel p_72406_, StructureFeatureManager p_72407_, ChunkGenerator p_72408_, Random p_72409_, BoundingBox p_72410_, ChunkPos p_72411_, BlockPos p_72412_) {
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72406_, p_72410_, 0, 0, this.f_72334_.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (this.f_72334_.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72406_, p_72410_, 1, 4, 1, 6, 4, 6, f_72323_);
         }

         boolean flag = this.f_72397_ != 0 && p_72409_.nextBoolean() && !this.f_72334_.f_72445_[Direction.DOWN.m_122411_()] && !this.f_72334_.f_72445_[Direction.UP.m_122411_()] && this.f_72334_.m_72468_() > 1;
         if (this.f_72397_ == 0) {
            this.m_73441_(p_72406_, p_72410_, 0, 1, 0, 2, 1, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 3, 0, 2, 3, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 2, 0, 0, 2, 2, f_72323_, f_72323_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 2, 0, 2, 2, 0, f_72323_, f_72323_, false);
            this.m_73434_(p_72406_, f_72327_, 1, 2, 1, p_72410_);
            this.m_73441_(p_72406_, p_72410_, 5, 1, 0, 7, 1, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 3, 0, 7, 3, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 2, 0, 7, 2, 2, f_72323_, f_72323_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 2, 0, 6, 2, 0, f_72323_, f_72323_, false);
            this.m_73434_(p_72406_, f_72327_, 6, 2, 1, p_72410_);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 5, 2, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 3, 5, 2, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 2, 5, 0, 2, 7, f_72323_, f_72323_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 2, 7, 2, 2, 7, f_72323_, f_72323_, false);
            this.m_73434_(p_72406_, f_72327_, 1, 2, 6, p_72410_);
            this.m_73441_(p_72406_, p_72410_, 5, 1, 5, 7, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 3, 5, 7, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 2, 5, 7, 2, 7, f_72323_, f_72323_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 2, 7, 6, 2, 7, f_72323_, f_72323_, false);
            this.m_73434_(p_72406_, f_72327_, 6, 2, 6, p_72410_);
            if (this.f_72334_.f_72445_[Direction.SOUTH.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 3, 3, 0, 4, 3, 0, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72406_, p_72410_, 3, 3, 0, 4, 3, 1, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 3, 2, 0, 4, 2, 0, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 3, 1, 0, 4, 1, 1, f_72324_, f_72324_, false);
            }

            if (this.f_72334_.f_72445_[Direction.NORTH.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 3, 3, 7, 4, 3, 7, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72406_, p_72410_, 3, 3, 6, 4, 3, 7, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 3, 2, 7, 4, 2, 7, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 3, 1, 6, 4, 1, 7, f_72324_, f_72324_, false);
            }

            if (this.f_72334_.f_72445_[Direction.WEST.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 0, 3, 3, 0, 3, 4, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72406_, p_72410_, 0, 3, 3, 1, 3, 4, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 0, 2, 3, 0, 2, 4, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 0, 1, 3, 1, 1, 4, f_72324_, f_72324_, false);
            }

            if (this.f_72334_.f_72445_[Direction.EAST.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 7, 3, 3, 7, 3, 4, f_72324_, f_72324_, false);
            } else {
               this.m_73441_(p_72406_, p_72410_, 6, 3, 3, 7, 3, 4, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 7, 2, 3, 7, 2, 4, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 6, 1, 3, 7, 1, 4, f_72324_, f_72324_, false);
            }
         } else if (this.f_72397_ == 1) {
            this.m_73441_(p_72406_, p_72410_, 2, 1, 2, 2, 3, 2, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 2, 1, 5, 2, 3, 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 1, 5, 5, 3, 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 5, 1, 2, 5, 3, 2, f_72324_, f_72324_, false);
            this.m_73434_(p_72406_, f_72327_, 2, 2, 2, p_72410_);
            this.m_73434_(p_72406_, f_72327_, 2, 2, 5, p_72410_);
            this.m_73434_(p_72406_, f_72327_, 5, 2, 5, p_72410_);
            this.m_73434_(p_72406_, f_72327_, 5, 2, 2, p_72410_);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 0, 1, 3, 0, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 1, 0, 3, 1, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 7, 1, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 6, 0, 3, 6, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 6, 1, 7, 7, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 1, 6, 7, 3, 6, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 6, 1, 0, 7, 3, 0, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 1, 1, 7, 3, 1, f_72324_, f_72324_, false);
            this.m_73434_(p_72406_, f_72323_, 1, 2, 0, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 0, 2, 1, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 1, 2, 7, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 0, 2, 6, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 6, 2, 7, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 7, 2, 6, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 6, 2, 0, p_72410_);
            this.m_73434_(p_72406_, f_72323_, 7, 2, 1, p_72410_);
            if (!this.f_72334_.f_72445_[Direction.SOUTH.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 1, 3, 0, 6, 3, 0, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 1, 2, 0, 6, 2, 0, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 1, 1, 0, 6, 1, 0, f_72324_, f_72324_, false);
            }

            if (!this.f_72334_.f_72445_[Direction.NORTH.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 1, 3, 7, 6, 3, 7, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 1, 2, 7, 6, 2, 7, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 1, 1, 7, 6, 1, 7, f_72324_, f_72324_, false);
            }

            if (!this.f_72334_.f_72445_[Direction.WEST.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 0, 3, 1, 0, 3, 6, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 0, 2, 1, 0, 2, 6, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 0, 1, 1, 0, 1, 6, f_72324_, f_72324_, false);
            }

            if (!this.f_72334_.f_72445_[Direction.EAST.m_122411_()]) {
               this.m_73441_(p_72406_, p_72410_, 7, 3, 1, 7, 3, 6, f_72324_, f_72324_, false);
               this.m_73441_(p_72406_, p_72410_, 7, 2, 1, 7, 2, 6, f_72323_, f_72323_, false);
               this.m_73441_(p_72406_, p_72410_, 7, 1, 1, 7, 1, 6, f_72324_, f_72324_, false);
            }
         } else if (this.f_72397_ == 2) {
            this.m_73441_(p_72406_, p_72410_, 0, 1, 0, 0, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 1, 0, 7, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 1, 0, 6, 1, 0, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 1, 7, 6, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 2, 0, 0, 2, 7, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 2, 0, 7, 2, 7, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 2, 0, 6, 2, 0, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 2, 7, 6, 2, 7, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 3, 0, 0, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 3, 0, 7, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 3, 0, 6, 3, 0, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 1, 3, 7, 6, 3, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 0, 1, 3, 0, 2, 4, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 7, 1, 3, 7, 2, 4, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 3, 1, 0, 4, 2, 0, f_72325_, f_72325_, false);
            this.m_73441_(p_72406_, p_72410_, 3, 1, 7, 4, 2, 7, f_72325_, f_72325_, false);
            if (this.f_72334_.f_72445_[Direction.SOUTH.m_122411_()]) {
               this.m_72360_(p_72406_, p_72410_, 3, 1, 0, 4, 2, 0);
            }

            if (this.f_72334_.f_72445_[Direction.NORTH.m_122411_()]) {
               this.m_72360_(p_72406_, p_72410_, 3, 1, 7, 4, 2, 7);
            }

            if (this.f_72334_.f_72445_[Direction.WEST.m_122411_()]) {
               this.m_72360_(p_72406_, p_72410_, 0, 1, 3, 0, 2, 4);
            }

            if (this.f_72334_.f_72445_[Direction.EAST.m_122411_()]) {
               this.m_72360_(p_72406_, p_72410_, 7, 1, 3, 7, 2, 4);
            }
         }

         if (flag) {
            this.m_73441_(p_72406_, p_72410_, 3, 1, 3, 4, 1, 4, f_72324_, f_72324_, false);
            this.m_73441_(p_72406_, p_72410_, 3, 2, 3, 4, 2, 4, f_72323_, f_72323_, false);
            this.m_73441_(p_72406_, p_72410_, 3, 3, 3, 4, 3, 4, f_72324_, f_72324_, false);
         }

         return true;
      }
   }

   public static class OceanMonumentSimpleTopRoom extends OceanMonumentPieces.OceanMonumentPiece {
      public OceanMonumentSimpleTopRoom(Direction p_72417_, OceanMonumentPieces.RoomDefinition p_72418_) {
         super(StructurePieceType.f_67128_, 1, p_72417_, p_72418_, 1, 1, 1);
      }

      public OceanMonumentSimpleTopRoom(ServerLevel p_163050_, CompoundTag p_163051_) {
         super(StructurePieceType.f_67128_, p_163051_);
      }

      public boolean m_7832_(WorldGenLevel p_72420_, StructureFeatureManager p_72421_, ChunkGenerator p_72422_, Random p_72423_, BoundingBox p_72424_, ChunkPos p_72425_, BlockPos p_72426_) {
         if (this.f_72334_.f_72443_ / 25 > 0) {
            this.m_72379_(p_72420_, p_72424_, 0, 0, this.f_72334_.f_72445_[Direction.DOWN.m_122411_()]);
         }

         if (this.f_72334_.f_72444_[Direction.UP.m_122411_()] == null) {
            this.m_72369_(p_72420_, p_72424_, 1, 4, 1, 6, 4, 6, f_72323_);
         }

         for(int i = 1; i <= 6; ++i) {
            for(int j = 1; j <= 6; ++j) {
               if (p_72423_.nextInt(3) != 0) {
                  int k = 2 + (p_72423_.nextInt(4) == 0 ? 0 : 1);
                  BlockState blockstate = Blocks.f_50057_.m_49966_();
                  this.m_73441_(p_72420_, p_72424_, i, k, j, i, 3, j, blockstate, blockstate, false);
               }
            }
         }

         this.m_73441_(p_72420_, p_72424_, 0, 1, 0, 0, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 7, 1, 0, 7, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 1, 0, 6, 1, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 1, 7, 6, 1, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 0, 2, 0, 0, 2, 7, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 7, 2, 0, 7, 2, 7, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 2, 0, 6, 2, 0, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 2, 7, 6, 2, 7, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 0, 3, 0, 0, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 7, 3, 0, 7, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 3, 0, 6, 3, 0, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 1, 3, 7, 6, 3, 7, f_72324_, f_72324_, false);
         this.m_73441_(p_72420_, p_72424_, 0, 1, 3, 0, 2, 4, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 7, 1, 3, 7, 2, 4, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 3, 1, 0, 4, 2, 0, f_72325_, f_72325_, false);
         this.m_73441_(p_72420_, p_72424_, 3, 1, 7, 4, 2, 7, f_72325_, f_72325_, false);
         if (this.f_72334_.f_72445_[Direction.SOUTH.m_122411_()]) {
            this.m_72360_(p_72420_, p_72424_, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   public static class OceanMonumentWingRoom extends OceanMonumentPieces.OceanMonumentPiece {
      private int f_72427_;

      public OceanMonumentWingRoom(Direction p_72432_, BoundingBox p_72433_, int p_72434_) {
         super(StructurePieceType.f_67129_, p_72432_, 1, p_72433_);
         this.f_72427_ = p_72434_ & 1;
      }

      public OceanMonumentWingRoom(ServerLevel p_163053_, CompoundTag p_163054_) {
         super(StructurePieceType.f_67129_, p_163054_);
      }

      public boolean m_7832_(WorldGenLevel p_72436_, StructureFeatureManager p_72437_, ChunkGenerator p_72438_, Random p_72439_, BoundingBox p_72440_, ChunkPos p_72441_, BlockPos p_72442_) {
         if (this.f_72427_ == 0) {
            for(int i = 0; i < 4; ++i) {
               this.m_73441_(p_72436_, p_72440_, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, f_72324_, f_72324_, false);
            }

            this.m_73441_(p_72436_, p_72440_, 7, 0, 6, 15, 0, 16, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 6, 0, 6, 6, 3, 20, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 16, 0, 6, 16, 3, 20, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 7, 1, 7, 7, 1, 20, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 15, 1, 7, 15, 1, 20, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 7, 1, 6, 9, 3, 6, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 13, 1, 6, 15, 3, 6, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 8, 1, 7, 9, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 13, 1, 7, 14, 1, 7, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 9, 0, 5, 13, 0, 5, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 10, 0, 7, 12, 0, 7, f_72325_, f_72325_, false);
            this.m_73441_(p_72436_, p_72440_, 8, 0, 10, 8, 0, 12, f_72325_, f_72325_, false);
            this.m_73441_(p_72436_, p_72440_, 14, 0, 10, 14, 0, 12, f_72325_, f_72325_, false);

            for(int i1 = 18; i1 >= 7; i1 -= 3) {
               this.m_73434_(p_72436_, f_72327_, 6, 3, i1, p_72440_);
               this.m_73434_(p_72436_, f_72327_, 16, 3, i1, p_72440_);
            }

            this.m_73434_(p_72436_, f_72327_, 10, 0, 10, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 12, 0, 10, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 10, 0, 12, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 12, 0, 12, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 8, 3, 6, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 14, 3, 6, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 4, 2, 4, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 4, 1, 4, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 4, 0, 4, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 18, 2, 4, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 18, 1, 4, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 18, 0, 4, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 4, 2, 18, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 4, 1, 18, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 4, 0, 18, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 18, 2, 18, p_72440_);
            this.m_73434_(p_72436_, f_72327_, 18, 1, 18, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 18, 0, 18, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 9, 7, 20, p_72440_);
            this.m_73434_(p_72436_, f_72324_, 13, 7, 20, p_72440_);
            this.m_73441_(p_72436_, p_72440_, 6, 0, 21, 7, 4, 21, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 15, 0, 21, 16, 4, 21, f_72324_, f_72324_, false);
            this.m_72354_(p_72436_, p_72440_, 11, 2, 16);
         } else if (this.f_72427_ == 1) {
            this.m_73441_(p_72436_, p_72440_, 9, 3, 18, 13, 3, 20, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 9, 0, 18, 9, 2, 18, f_72324_, f_72324_, false);
            this.m_73441_(p_72436_, p_72440_, 13, 0, 18, 13, 2, 18, f_72324_, f_72324_, false);
            int j1 = 9;
            int j = 20;
            int k = 5;

            for(int l = 0; l < 2; ++l) {
               this.m_73434_(p_72436_, f_72324_, j1, 6, 20, p_72440_);
               this.m_73434_(p_72436_, f_72327_, j1, 5, 20, p_72440_);
               this.m_73434_(p_72436_, f_72324_, j1, 4, 20, p_72440_);
               j1 = 13;
            }

            this.m_73441_(p_72436_, p_72440_, 7, 3, 7, 15, 3, 14, f_72324_, f_72324_, false);
            j1 = 10;

            for(int k1 = 0; k1 < 2; ++k1) {
               this.m_73441_(p_72436_, p_72440_, j1, 0, 10, j1, 6, 10, f_72324_, f_72324_, false);
               this.m_73441_(p_72436_, p_72440_, j1, 0, 12, j1, 6, 12, f_72324_, f_72324_, false);
               this.m_73434_(p_72436_, f_72327_, j1, 0, 10, p_72440_);
               this.m_73434_(p_72436_, f_72327_, j1, 0, 12, p_72440_);
               this.m_73434_(p_72436_, f_72327_, j1, 4, 10, p_72440_);
               this.m_73434_(p_72436_, f_72327_, j1, 4, 12, p_72440_);
               j1 = 12;
            }

            j1 = 8;

            for(int l1 = 0; l1 < 2; ++l1) {
               this.m_73441_(p_72436_, p_72440_, j1, 0, 7, j1, 2, 7, f_72324_, f_72324_, false);
               this.m_73441_(p_72436_, p_72440_, j1, 0, 14, j1, 2, 14, f_72324_, f_72324_, false);
               j1 = 14;
            }

            this.m_73441_(p_72436_, p_72440_, 8, 3, 8, 8, 3, 13, f_72325_, f_72325_, false);
            this.m_73441_(p_72436_, p_72440_, 14, 3, 8, 14, 3, 13, f_72325_, f_72325_, false);
            this.m_72354_(p_72436_, p_72440_, 11, 5, 13);
         }

         return true;
      }
   }

   static class RoomDefinition {
      final int f_72443_;
      final OceanMonumentPieces.RoomDefinition[] f_72444_ = new OceanMonumentPieces.RoomDefinition[6];
      final boolean[] f_72445_ = new boolean[6];
      boolean f_72446_;
      boolean f_72447_;
      private int f_72448_;

      public RoomDefinition(int p_72450_) {
         this.f_72443_ = p_72450_;
      }

      public void m_72459_(Direction p_72460_, OceanMonumentPieces.RoomDefinition p_72461_) {
         this.f_72444_[p_72460_.m_122411_()] = p_72461_;
         p_72461_.f_72444_[p_72460_.m_122424_().m_122411_()] = this;
      }

      public void m_72451_() {
         for(int i = 0; i < 6; ++i) {
            this.f_72445_[i] = this.f_72444_[i] != null;
         }

      }

      public boolean m_72452_(int p_72453_) {
         if (this.f_72447_) {
            return true;
         } else {
            this.f_72448_ = p_72453_;

            for(int i = 0; i < 6; ++i) {
               if (this.f_72444_[i] != null && this.f_72445_[i] && this.f_72444_[i].f_72448_ != p_72453_ && this.f_72444_[i].m_72452_(p_72453_)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean m_72462_() {
         return this.f_72443_ >= 75;
      }

      public int m_72468_() {
         int i = 0;

         for(int j = 0; j < 6; ++j) {
            if (this.f_72445_[j]) {
               ++i;
            }
         }

         return i;
      }
   }
}