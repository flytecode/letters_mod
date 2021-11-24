package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class StrongholdPieces {
   private static final int f_163218_ = 3;
   private static final int f_163219_ = 3;
   private static final int f_163220_ = 50;
   private static final int f_163221_ = 10;
   private static final boolean f_163222_ = true;
   private static final StrongholdPieces.PieceWeight[] f_72851_ = new StrongholdPieces.PieceWeight[]{new StrongholdPieces.PieceWeight(StrongholdPieces.Straight.class, 40, 0), new StrongholdPieces.PieceWeight(StrongholdPieces.PrisonHall.class, 5, 5), new StrongholdPieces.PieceWeight(StrongholdPieces.LeftTurn.class, 20, 0), new StrongholdPieces.PieceWeight(StrongholdPieces.RightTurn.class, 20, 0), new StrongholdPieces.PieceWeight(StrongholdPieces.RoomCrossing.class, 10, 6), new StrongholdPieces.PieceWeight(StrongholdPieces.StraightStairsDown.class, 5, 5), new StrongholdPieces.PieceWeight(StrongholdPieces.StairsDown.class, 5, 5), new StrongholdPieces.PieceWeight(StrongholdPieces.FiveCrossing.class, 5, 4), new StrongholdPieces.PieceWeight(StrongholdPieces.ChestCorridor.class, 5, 4), new StrongholdPieces.PieceWeight(StrongholdPieces.Library.class, 10, 2) {
      public boolean m_6644_(int p_72903_) {
         return super.m_6644_(p_72903_) && p_72903_ > 4;
      }
   }, new StrongholdPieces.PieceWeight(StrongholdPieces.PortalRoom.class, 20, 1) {
      public boolean m_6644_(int p_72909_) {
         return super.m_6644_(p_72909_) && p_72909_ > 5;
      }
   }};
   private static List<StrongholdPieces.PieceWeight> f_72852_;
   static Class<? extends StrongholdPieces.StrongholdPiece> f_72853_;
   private static int f_72854_;
   static final StrongholdPieces.SmoothStoneSelector f_72855_ = new StrongholdPieces.SmoothStoneSelector();

   public static void m_72857_() {
      f_72852_ = Lists.newArrayList();

      for(StrongholdPieces.PieceWeight strongholdpieces$pieceweight : f_72851_) {
         strongholdpieces$pieceweight.f_73060_ = 0;
         f_72852_.add(strongholdpieces$pieceweight);
      }

      f_72853_ = null;
   }

   private static boolean m_72888_() {
      boolean flag = false;
      f_72854_ = 0;

      for(StrongholdPieces.PieceWeight strongholdpieces$pieceweight : f_72852_) {
         if (strongholdpieces$pieceweight.f_73061_ > 0 && strongholdpieces$pieceweight.f_73060_ < strongholdpieces$pieceweight.f_73061_) {
            flag = true;
         }

         f_72854_ += strongholdpieces$pieceweight.f_73059_;
      }

      return flag;
   }

   private static StrongholdPieces.StrongholdPiece m_163233_(Class<? extends StrongholdPieces.StrongholdPiece> p_163234_, StructurePieceAccessor p_163235_, Random p_163236_, int p_163237_, int p_163238_, int p_163239_, @Nullable Direction p_163240_, int p_163241_) {
      StrongholdPieces.StrongholdPiece strongholdpieces$strongholdpiece = null;
      if (p_163234_ == StrongholdPieces.Straight.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.Straight.m_163465_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.PrisonHall.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.PrisonHall.m_163374_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.LeftTurn.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.LeftTurn.m_163316_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.RightTurn.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.RightTurn.m_163390_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.RoomCrossing.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.RoomCrossing.m_163411_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.StraightStairsDown.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.StraightStairsDown.m_163484_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.StairsDown.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.StairsDown.m_163439_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.FiveCrossing.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.FiveCrossing.m_163300_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.ChestCorridor.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.ChestCorridor.m_163264_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.Library.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.Library.m_163334_(p_163235_, p_163236_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      } else if (p_163234_ == StrongholdPieces.PortalRoom.class) {
         strongholdpieces$strongholdpiece = StrongholdPieces.PortalRoom.m_163356_(p_163235_, p_163237_, p_163238_, p_163239_, p_163240_, p_163241_);
      }

      return strongholdpieces$strongholdpiece;
   }

   private static StrongholdPieces.StrongholdPiece m_163224_(StrongholdPieces.StartPiece p_163225_, StructurePieceAccessor p_163226_, Random p_163227_, int p_163228_, int p_163229_, int p_163230_, Direction p_163231_, int p_163232_) {
      if (!m_72888_()) {
         return null;
      } else {
         if (f_72853_ != null) {
            StrongholdPieces.StrongholdPiece strongholdpieces$strongholdpiece = m_163233_(f_72853_, p_163226_, p_163227_, p_163228_, p_163229_, p_163230_, p_163231_, p_163232_);
            f_72853_ = null;
            if (strongholdpieces$strongholdpiece != null) {
               return strongholdpieces$strongholdpiece;
            }
         }

         int j = 0;

         while(j < 5) {
            ++j;
            int i = p_163227_.nextInt(f_72854_);

            for(StrongholdPieces.PieceWeight strongholdpieces$pieceweight : f_72852_) {
               i -= strongholdpieces$pieceweight.f_73059_;
               if (i < 0) {
                  if (!strongholdpieces$pieceweight.m_6644_(p_163232_) || strongholdpieces$pieceweight == p_163225_.f_73233_) {
                     break;
                  }

                  StrongholdPieces.StrongholdPiece strongholdpieces$strongholdpiece1 = m_163233_(strongholdpieces$pieceweight.f_73058_, p_163226_, p_163227_, p_163228_, p_163229_, p_163230_, p_163231_, p_163232_);
                  if (strongholdpieces$strongholdpiece1 != null) {
                     ++strongholdpieces$pieceweight.f_73060_;
                     p_163225_.f_73233_ = strongholdpieces$pieceweight;
                     if (!strongholdpieces$pieceweight.m_73066_()) {
                        f_72852_.remove(strongholdpieces$pieceweight);
                     }

                     return strongholdpieces$strongholdpiece1;
                  }
               }
            }
         }

         BoundingBox boundingbox = StrongholdPieces.FillerCorridor.m_163279_(p_163226_, p_163227_, p_163228_, p_163229_, p_163230_, p_163231_);
         return boundingbox != null && boundingbox.m_162396_() > 1 ? new StrongholdPieces.FillerCorridor(p_163232_, boundingbox, p_163231_) : null;
      }
   }

   static StructurePiece m_163242_(StrongholdPieces.StartPiece p_163243_, StructurePieceAccessor p_163244_, Random p_163245_, int p_163246_, int p_163247_, int p_163248_, @Nullable Direction p_163249_, int p_163250_) {
      if (p_163250_ > 50) {
         return null;
      } else if (Math.abs(p_163246_ - p_163243_.m_73547_().m_162395_()) <= 112 && Math.abs(p_163248_ - p_163243_.m_73547_().m_162398_()) <= 112) {
         StructurePiece structurepiece = m_163224_(p_163243_, p_163244_, p_163245_, p_163246_, p_163247_, p_163248_, p_163249_, p_163250_ + 1);
         if (structurepiece != null) {
            p_163244_.m_142679_(structurepiece);
            p_163243_.f_73235_.add(structurepiece);
         }

         return structurepiece;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StrongholdPieces.StrongholdPiece {
      private static final int f_163251_ = 5;
      private static final int f_163252_ = 5;
      private static final int f_163253_ = 7;
      private boolean f_72913_;

      public ChestCorridor(int p_72915_, Random p_72916_, BoundingBox p_72917_, Direction p_72918_) {
         super(StructurePieceType.f_67155_, p_72915_, p_72917_);
         this.m_73519_(p_72918_);
         this.f_73303_ = this.m_73326_(p_72916_);
      }

      public ChestCorridor(ServerLevel p_163255_, CompoundTag p_163256_) {
         super(StructurePieceType.f_67155_, p_163256_);
         this.f_72913_ = p_163256_.m_128471_("Chest");
      }

      protected void m_142347_(ServerLevel p_163258_, CompoundTag p_163259_) {
         super.m_142347_(p_163258_, p_163259_);
         p_163259_.m_128379_("Chest", this.f_72913_);
      }

      public void m_142537_(StructurePiece p_163261_, StructurePieceAccessor p_163262_, Random p_163263_) {
         this.m_163500_((StrongholdPieces.StartPiece)p_163261_, p_163262_, p_163263_, 1, 1);
      }

      public static StrongholdPieces.ChestCorridor m_163264_(StructurePieceAccessor p_163265_, Random p_163266_, int p_163267_, int p_163268_, int p_163269_, Direction p_163270_, int p_163271_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163267_, p_163268_, p_163269_, -1, -1, 0, 5, 5, 7, p_163270_);
         return m_73318_(boundingbox) && p_163265_.m_141921_(boundingbox) == null ? new StrongholdPieces.ChestCorridor(p_163271_, p_163266_, boundingbox, p_163270_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_72923_, StructureFeatureManager p_72924_, ChunkGenerator p_72925_, Random p_72926_, BoundingBox p_72927_, ChunkPos p_72928_, BlockPos p_72929_) {
         this.m_73464_(p_72923_, p_72927_, 0, 0, 0, 4, 4, 6, true, p_72926_, StrongholdPieces.f_72855_);
         this.m_73310_(p_72923_, p_72926_, p_72927_, this.f_73303_, 1, 1, 0);
         this.m_73310_(p_72923_, p_72926_, p_72927_, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
         this.m_73441_(p_72923_, p_72927_, 3, 1, 2, 3, 1, 4, Blocks.f_50222_.m_49966_(), Blocks.f_50222_.m_49966_(), false);
         this.m_73434_(p_72923_, Blocks.f_50411_.m_49966_(), 3, 1, 1, p_72927_);
         this.m_73434_(p_72923_, Blocks.f_50411_.m_49966_(), 3, 1, 5, p_72927_);
         this.m_73434_(p_72923_, Blocks.f_50411_.m_49966_(), 3, 2, 2, p_72927_);
         this.m_73434_(p_72923_, Blocks.f_50411_.m_49966_(), 3, 2, 4, p_72927_);

         for(int i = 2; i <= 4; ++i) {
            this.m_73434_(p_72923_, Blocks.f_50411_.m_49966_(), 2, 1, i, p_72927_);
         }

         if (!this.f_72913_ && p_72927_.m_71051_(this.m_163582_(3, 2, 3))) {
            this.f_72913_ = true;
            this.m_5606_(p_72923_, p_72927_, p_72926_, 3, 2, 3, BuiltInLootTables.f_78763_);
         }

         return true;
      }
   }

   public static class FillerCorridor extends StrongholdPieces.StrongholdPiece {
      private final int f_72944_;

      public FillerCorridor(int p_72946_, BoundingBox p_72947_, Direction p_72948_) {
         super(StructurePieceType.f_67156_, p_72946_, p_72947_);
         this.m_73519_(p_72948_);
         this.f_72944_ = p_72948_ != Direction.NORTH && p_72948_ != Direction.SOUTH ? p_72947_.m_71056_() : p_72947_.m_71058_();
      }

      public FillerCorridor(ServerLevel p_163274_, CompoundTag p_163275_) {
         super(StructurePieceType.f_67156_, p_163275_);
         this.f_72944_ = p_163275_.m_128451_("Steps");
      }

      protected void m_142347_(ServerLevel p_163277_, CompoundTag p_163278_) {
         super.m_142347_(p_163277_, p_163278_);
         p_163278_.m_128405_("Steps", this.f_72944_);
      }

      public static BoundingBox m_163279_(StructurePieceAccessor p_163280_, Random p_163281_, int p_163282_, int p_163283_, int p_163284_, Direction p_163285_) {
         int i = 3;
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163282_, p_163283_, p_163284_, -1, -1, 0, 5, 5, 4, p_163285_);
         StructurePiece structurepiece = p_163280_.m_141921_(boundingbox);
         if (structurepiece == null) {
            return null;
         } else {
            if (structurepiece.m_73547_().m_162396_() == boundingbox.m_162396_()) {
               for(int j = 2; j >= 1; --j) {
                  boundingbox = BoundingBox.m_71031_(p_163282_, p_163283_, p_163284_, -1, -1, 0, 5, 5, j, p_163285_);
                  if (!structurepiece.m_73547_().m_71049_(boundingbox)) {
                     return BoundingBox.m_71031_(p_163282_, p_163283_, p_163284_, -1, -1, 0, 5, 5, j + 1, p_163285_);
                  }
               }
            }

            return null;
         }
      }

      public boolean m_7832_(WorldGenLevel p_72953_, StructureFeatureManager p_72954_, ChunkGenerator p_72955_, Random p_72956_, BoundingBox p_72957_, ChunkPos p_72958_, BlockPos p_72959_) {
         for(int i = 0; i < this.f_72944_; ++i) {
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 0, 0, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 1, 0, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 2, 0, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 3, 0, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 4, 0, i, p_72957_);

            for(int j = 1; j <= 3; ++j) {
               this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 0, j, i, p_72957_);
               this.m_73434_(p_72953_, Blocks.f_50627_.m_49966_(), 1, j, i, p_72957_);
               this.m_73434_(p_72953_, Blocks.f_50627_.m_49966_(), 2, j, i, p_72957_);
               this.m_73434_(p_72953_, Blocks.f_50627_.m_49966_(), 3, j, i, p_72957_);
               this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 4, j, i, p_72957_);
            }

            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 0, 4, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 1, 4, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 2, 4, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 3, 4, i, p_72957_);
            this.m_73434_(p_72953_, Blocks.f_50222_.m_49966_(), 4, 4, i, p_72957_);
         }

         return true;
      }
   }

   public static class FiveCrossing extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163287_ = 10;
      protected static final int f_163288_ = 9;
      protected static final int f_163289_ = 11;
      private final boolean f_72969_;
      private final boolean f_72970_;
      private final boolean f_72971_;
      private final boolean f_72972_;

      public FiveCrossing(int p_72974_, Random p_72975_, BoundingBox p_72976_, Direction p_72977_) {
         super(StructurePieceType.f_67157_, p_72974_, p_72976_);
         this.m_73519_(p_72977_);
         this.f_73303_ = this.m_73326_(p_72975_);
         this.f_72969_ = p_72975_.nextBoolean();
         this.f_72970_ = p_72975_.nextBoolean();
         this.f_72971_ = p_72975_.nextBoolean();
         this.f_72972_ = p_72975_.nextInt(3) > 0;
      }

      public FiveCrossing(ServerLevel p_163291_, CompoundTag p_163292_) {
         super(StructurePieceType.f_67157_, p_163292_);
         this.f_72969_ = p_163292_.m_128471_("leftLow");
         this.f_72970_ = p_163292_.m_128471_("leftHigh");
         this.f_72971_ = p_163292_.m_128471_("rightLow");
         this.f_72972_ = p_163292_.m_128471_("rightHigh");
      }

      protected void m_142347_(ServerLevel p_163294_, CompoundTag p_163295_) {
         super.m_142347_(p_163294_, p_163295_);
         p_163295_.m_128379_("leftLow", this.f_72969_);
         p_163295_.m_128379_("leftHigh", this.f_72970_);
         p_163295_.m_128379_("rightLow", this.f_72971_);
         p_163295_.m_128379_("rightHigh", this.f_72972_);
      }

      public void m_142537_(StructurePiece p_163297_, StructurePieceAccessor p_163298_, Random p_163299_) {
         int i = 3;
         int j = 5;
         Direction direction = this.m_73549_();
         if (direction == Direction.WEST || direction == Direction.NORTH) {
            i = 8 - i;
            j = 8 - j;
         }

         this.m_163500_((StrongholdPieces.StartPiece)p_163297_, p_163298_, p_163299_, 5, 1);
         if (this.f_72969_) {
            this.m_163507_((StrongholdPieces.StartPiece)p_163297_, p_163298_, p_163299_, i, 1);
         }

         if (this.f_72970_) {
            this.m_163507_((StrongholdPieces.StartPiece)p_163297_, p_163298_, p_163299_, j, 7);
         }

         if (this.f_72971_) {
            this.m_163513_((StrongholdPieces.StartPiece)p_163297_, p_163298_, p_163299_, i, 1);
         }

         if (this.f_72972_) {
            this.m_163513_((StrongholdPieces.StartPiece)p_163297_, p_163298_, p_163299_, j, 7);
         }

      }

      public static StrongholdPieces.FiveCrossing m_163300_(StructurePieceAccessor p_163301_, Random p_163302_, int p_163303_, int p_163304_, int p_163305_, Direction p_163306_, int p_163307_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163303_, p_163304_, p_163305_, -4, -3, 0, 10, 9, 11, p_163306_);
         return m_73318_(boundingbox) && p_163301_.m_141921_(boundingbox) == null ? new StrongholdPieces.FiveCrossing(p_163307_, p_163302_, boundingbox, p_163306_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_72982_, StructureFeatureManager p_72983_, ChunkGenerator p_72984_, Random p_72985_, BoundingBox p_72986_, ChunkPos p_72987_, BlockPos p_72988_) {
         this.m_73464_(p_72982_, p_72986_, 0, 0, 0, 9, 8, 10, true, p_72985_, StrongholdPieces.f_72855_);
         this.m_73310_(p_72982_, p_72985_, p_72986_, this.f_73303_, 4, 3, 0);
         if (this.f_72969_) {
            this.m_73441_(p_72982_, p_72986_, 0, 3, 1, 0, 5, 3, f_73382_, f_73382_, false);
         }

         if (this.f_72971_) {
            this.m_73441_(p_72982_, p_72986_, 9, 3, 1, 9, 5, 3, f_73382_, f_73382_, false);
         }

         if (this.f_72970_) {
            this.m_73441_(p_72982_, p_72986_, 0, 5, 7, 0, 7, 9, f_73382_, f_73382_, false);
         }

         if (this.f_72972_) {
            this.m_73441_(p_72982_, p_72986_, 9, 5, 7, 9, 7, 9, f_73382_, f_73382_, false);
         }

         this.m_73441_(p_72982_, p_72986_, 5, 1, 10, 7, 3, 10, f_73382_, f_73382_, false);
         this.m_73464_(p_72982_, p_72986_, 1, 2, 1, 8, 2, 6, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73464_(p_72982_, p_72986_, 4, 1, 5, 4, 4, 9, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73464_(p_72982_, p_72986_, 8, 1, 5, 8, 4, 9, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73464_(p_72982_, p_72986_, 1, 4, 7, 3, 4, 9, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73464_(p_72982_, p_72986_, 1, 3, 5, 3, 3, 6, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73441_(p_72982_, p_72986_, 1, 3, 4, 3, 3, 4, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73441_(p_72982_, p_72986_, 1, 4, 6, 3, 4, 6, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73464_(p_72982_, p_72986_, 5, 1, 7, 7, 1, 8, false, p_72985_, StrongholdPieces.f_72855_);
         this.m_73441_(p_72982_, p_72986_, 5, 1, 9, 7, 1, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73441_(p_72982_, p_72986_, 5, 2, 7, 7, 2, 7, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73441_(p_72982_, p_72986_, 4, 5, 7, 4, 5, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73441_(p_72982_, p_72986_, 8, 5, 7, 8, 5, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
         this.m_73441_(p_72982_, p_72986_, 5, 5, 7, 7, 5, 9, Blocks.f_50405_.m_49966_().m_61124_(SlabBlock.f_56353_, SlabType.DOUBLE), Blocks.f_50405_.m_49966_().m_61124_(SlabBlock.f_56353_, SlabType.DOUBLE), false);
         this.m_73434_(p_72982_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH), 6, 5, 6, p_72986_);
         return true;
      }
   }

   public static class LeftTurn extends StrongholdPieces.Turn {
      public LeftTurn(int p_73004_, Random p_73005_, BoundingBox p_73006_, Direction p_73007_) {
         super(StructurePieceType.f_67158_, p_73004_, p_73006_);
         this.m_73519_(p_73007_);
         this.f_73303_ = this.m_73326_(p_73005_);
      }

      public LeftTurn(ServerLevel p_163310_, CompoundTag p_163311_) {
         super(StructurePieceType.f_67158_, p_163311_);
      }

      public void m_142537_(StructurePiece p_163313_, StructurePieceAccessor p_163314_, Random p_163315_) {
         Direction direction = this.m_73549_();
         if (direction != Direction.NORTH && direction != Direction.EAST) {
            this.m_163513_((StrongholdPieces.StartPiece)p_163313_, p_163314_, p_163315_, 1, 1);
         } else {
            this.m_163507_((StrongholdPieces.StartPiece)p_163313_, p_163314_, p_163315_, 1, 1);
         }

      }

      public static StrongholdPieces.LeftTurn m_163316_(StructurePieceAccessor p_163317_, Random p_163318_, int p_163319_, int p_163320_, int p_163321_, Direction p_163322_, int p_163323_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163319_, p_163320_, p_163321_, -1, -1, 0, 5, 5, 5, p_163322_);
         return m_73318_(boundingbox) && p_163317_.m_141921_(boundingbox) == null ? new StrongholdPieces.LeftTurn(p_163323_, p_163318_, boundingbox, p_163322_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73012_, StructureFeatureManager p_73013_, ChunkGenerator p_73014_, Random p_73015_, BoundingBox p_73016_, ChunkPos p_73017_, BlockPos p_73018_) {
         this.m_73464_(p_73012_, p_73016_, 0, 0, 0, 4, 4, 4, true, p_73015_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73012_, p_73015_, p_73016_, this.f_73303_, 1, 1, 0);
         Direction direction = this.m_73549_();
         if (direction != Direction.NORTH && direction != Direction.EAST) {
            this.m_73441_(p_73012_, p_73016_, 4, 1, 1, 4, 3, 3, f_73382_, f_73382_, false);
         } else {
            this.m_73441_(p_73012_, p_73016_, 0, 1, 1, 0, 3, 3, f_73382_, f_73382_, false);
         }

         return true;
      }
   }

   public static class Library extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163324_ = 14;
      protected static final int f_163325_ = 6;
      protected static final int f_163326_ = 11;
      protected static final int f_163327_ = 15;
      private final boolean f_73031_;

      public Library(int p_73033_, Random p_73034_, BoundingBox p_73035_, Direction p_73036_) {
         super(StructurePieceType.f_67159_, p_73033_, p_73035_);
         this.m_73519_(p_73036_);
         this.f_73303_ = this.m_73326_(p_73034_);
         this.f_73031_ = p_73035_.m_71057_() > 6;
      }

      public Library(ServerLevel p_163329_, CompoundTag p_163330_) {
         super(StructurePieceType.f_67159_, p_163330_);
         this.f_73031_ = p_163330_.m_128471_("Tall");
      }

      protected void m_142347_(ServerLevel p_163332_, CompoundTag p_163333_) {
         super.m_142347_(p_163332_, p_163333_);
         p_163333_.m_128379_("Tall", this.f_73031_);
      }

      public static StrongholdPieces.Library m_163334_(StructurePieceAccessor p_163335_, Random p_163336_, int p_163337_, int p_163338_, int p_163339_, Direction p_163340_, int p_163341_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163337_, p_163338_, p_163339_, -4, -1, 0, 14, 11, 15, p_163340_);
         if (!m_73318_(boundingbox) || p_163335_.m_141921_(boundingbox) != null) {
            boundingbox = BoundingBox.m_71031_(p_163337_, p_163338_, p_163339_, -4, -1, 0, 14, 6, 15, p_163340_);
            if (!m_73318_(boundingbox) || p_163335_.m_141921_(boundingbox) != null) {
               return null;
            }
         }

         return new StrongholdPieces.Library(p_163341_, p_163336_, boundingbox, p_163340_);
      }

      public boolean m_7832_(WorldGenLevel p_73041_, StructureFeatureManager p_73042_, ChunkGenerator p_73043_, Random p_73044_, BoundingBox p_73045_, ChunkPos p_73046_, BlockPos p_73047_) {
         int i = 11;
         if (!this.f_73031_) {
            i = 6;
         }

         this.m_73464_(p_73041_, p_73045_, 0, 0, 0, 13, i - 1, 14, true, p_73044_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73041_, p_73044_, p_73045_, this.f_73303_, 4, 1, 0);
         this.m_73476_(p_73041_, p_73045_, p_73044_, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.f_50033_.m_49966_(), Blocks.f_50033_.m_49966_(), false, false);
         int j = 1;
         int k = 12;

         for(int l = 1; l <= 13; ++l) {
            if ((l - 1) % 4 == 0) {
               this.m_73441_(p_73041_, p_73045_, 1, 1, l, 1, 4, l, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
               this.m_73441_(p_73041_, p_73045_, 12, 1, l, 12, 4, l, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
               this.m_73434_(p_73041_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST), 2, 3, l, p_73045_);
               this.m_73434_(p_73041_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST), 11, 3, l, p_73045_);
               if (this.f_73031_) {
                  this.m_73441_(p_73041_, p_73045_, 1, 6, l, 1, 9, l, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                  this.m_73441_(p_73041_, p_73045_, 12, 6, l, 12, 9, l, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
               }
            } else {
               this.m_73441_(p_73041_, p_73045_, 1, 1, l, 1, 4, l, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
               this.m_73441_(p_73041_, p_73045_, 12, 1, l, 12, 4, l, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
               if (this.f_73031_) {
                  this.m_73441_(p_73041_, p_73045_, 1, 6, l, 1, 9, l, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                  this.m_73441_(p_73041_, p_73045_, 12, 6, l, 12, 9, l, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
               }
            }
         }

         for(int l1 = 3; l1 < 12; l1 += 2) {
            this.m_73441_(p_73041_, p_73045_, 3, 1, l1, 4, 3, l1, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
            this.m_73441_(p_73041_, p_73045_, 6, 1, l1, 7, 3, l1, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
            this.m_73441_(p_73041_, p_73045_, 9, 1, l1, 10, 3, l1, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
         }

         if (this.f_73031_) {
            this.m_73441_(p_73041_, p_73045_, 1, 5, 1, 3, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
            this.m_73441_(p_73041_, p_73045_, 10, 5, 1, 12, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
            this.m_73441_(p_73041_, p_73045_, 4, 5, 1, 9, 5, 2, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
            this.m_73441_(p_73041_, p_73045_, 4, 5, 12, 9, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
            this.m_73434_(p_73041_, Blocks.f_50705_.m_49966_(), 9, 5, 11, p_73045_);
            this.m_73434_(p_73041_, Blocks.f_50705_.m_49966_(), 8, 5, 11, p_73045_);
            this.m_73434_(p_73041_, Blocks.f_50705_.m_49966_(), 9, 5, 10, p_73045_);
            BlockState blockstate5 = Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true));
            BlockState blockstate = Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52311_, Boolean.valueOf(true));
            this.m_73441_(p_73041_, p_73045_, 3, 6, 3, 3, 6, 11, blockstate, blockstate, false);
            this.m_73441_(p_73041_, p_73045_, 10, 6, 3, 10, 6, 9, blockstate, blockstate, false);
            this.m_73441_(p_73041_, p_73045_, 4, 6, 2, 9, 6, 2, blockstate5, blockstate5, false);
            this.m_73441_(p_73041_, p_73045_, 4, 6, 12, 7, 6, 12, blockstate5, blockstate5, false);
            this.m_73434_(p_73041_, Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true)), 3, 6, 2, p_73045_);
            this.m_73434_(p_73041_, Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52311_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true)), 3, 6, 12, p_73045_);
            this.m_73434_(p_73041_, Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true)), 10, 6, 2, p_73045_);

            for(int i1 = 0; i1 <= 2; ++i1) {
               this.m_73434_(p_73041_, Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52311_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true)), 8 + i1, 6, 12 - i1, p_73045_);
               if (i1 != 2) {
                  this.m_73434_(p_73041_, Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true)), 8 + i1, 6, 11 - i1, p_73045_);
               }
            }

            BlockState blockstate6 = Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, Direction.SOUTH);
            this.m_73434_(p_73041_, blockstate6, 10, 1, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 2, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 3, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 4, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 5, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 6, 13, p_73045_);
            this.m_73434_(p_73041_, blockstate6, 10, 7, 13, p_73045_);
            int j1 = 7;
            int k1 = 7;
            BlockState blockstate1 = Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true));
            this.m_73434_(p_73041_, blockstate1, 6, 9, 7, p_73045_);
            BlockState blockstate2 = Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true));
            this.m_73434_(p_73041_, blockstate2, 7, 9, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate1, 6, 8, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate2, 7, 8, 7, p_73045_);
            BlockState blockstate3 = blockstate.m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true)).m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true));
            this.m_73434_(p_73041_, blockstate3, 6, 7, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate3, 7, 7, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate1, 5, 7, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate2, 8, 7, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate1.m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)), 6, 7, 6, p_73045_);
            this.m_73434_(p_73041_, blockstate1.m_61124_(FenceBlock.f_52311_, Boolean.valueOf(true)), 6, 7, 8, p_73045_);
            this.m_73434_(p_73041_, blockstate2.m_61124_(FenceBlock.f_52309_, Boolean.valueOf(true)), 7, 7, 6, p_73045_);
            this.m_73434_(p_73041_, blockstate2.m_61124_(FenceBlock.f_52311_, Boolean.valueOf(true)), 7, 7, 8, p_73045_);
            BlockState blockstate4 = Blocks.f_50081_.m_49966_();
            this.m_73434_(p_73041_, blockstate4, 5, 8, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate4, 8, 8, 7, p_73045_);
            this.m_73434_(p_73041_, blockstate4, 6, 8, 6, p_73045_);
            this.m_73434_(p_73041_, blockstate4, 6, 8, 8, p_73045_);
            this.m_73434_(p_73041_, blockstate4, 7, 8, 6, p_73045_);
            this.m_73434_(p_73041_, blockstate4, 7, 8, 8, p_73045_);
         }

         this.m_5606_(p_73041_, p_73045_, p_73044_, 3, 3, 5, BuiltInLootTables.f_78761_);
         if (this.f_73031_) {
            this.m_73434_(p_73041_, f_73382_, 12, 9, 1, p_73045_);
            this.m_5606_(p_73041_, p_73045_, p_73044_, 12, 8, 1, BuiltInLootTables.f_78761_);
         }

         return true;
      }
   }

   static class PieceWeight {
      public final Class<? extends StrongholdPieces.StrongholdPiece> f_73058_;
      public final int f_73059_;
      public int f_73060_;
      public final int f_73061_;

      public PieceWeight(Class<? extends StrongholdPieces.StrongholdPiece> p_73063_, int p_73064_, int p_73065_) {
         this.f_73058_ = p_73063_;
         this.f_73059_ = p_73064_;
         this.f_73061_ = p_73065_;
      }

      public boolean m_6644_(int p_73067_) {
         return this.f_73061_ == 0 || this.f_73060_ < this.f_73061_;
      }

      public boolean m_73066_() {
         return this.f_73061_ == 0 || this.f_73060_ < this.f_73061_;
      }
   }

   public static class PortalRoom extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163343_ = 11;
      protected static final int f_163344_ = 8;
      protected static final int f_163345_ = 16;
      private boolean f_73068_;

      public PortalRoom(int p_73070_, BoundingBox p_73071_, Direction p_73072_) {
         super(StructurePieceType.f_67160_, p_73070_, p_73071_);
         this.m_73519_(p_73072_);
      }

      public PortalRoom(ServerLevel p_163347_, CompoundTag p_163348_) {
         super(StructurePieceType.f_67160_, p_163348_);
         this.f_73068_ = p_163348_.m_128471_("Mob");
      }

      protected void m_142347_(ServerLevel p_163350_, CompoundTag p_163351_) {
         super.m_142347_(p_163350_, p_163351_);
         p_163351_.m_128379_("Mob", this.f_73068_);
      }

      public void m_142537_(StructurePiece p_163353_, StructurePieceAccessor p_163354_, Random p_163355_) {
         if (p_163353_ != null) {
            ((StrongholdPieces.StartPiece)p_163353_).f_73234_ = this;
         }

      }

      public static StrongholdPieces.PortalRoom m_163356_(StructurePieceAccessor p_163357_, int p_163358_, int p_163359_, int p_163360_, Direction p_163361_, int p_163362_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163358_, p_163359_, p_163360_, -4, -1, 0, 11, 8, 16, p_163361_);
         return m_73318_(boundingbox) && p_163357_.m_141921_(boundingbox) == null ? new StrongholdPieces.PortalRoom(p_163362_, boundingbox, p_163361_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73077_, StructureFeatureManager p_73078_, ChunkGenerator p_73079_, Random p_73080_, BoundingBox p_73081_, ChunkPos p_73082_, BlockPos p_73083_) {
         this.m_73464_(p_73077_, p_73081_, 0, 0, 0, 10, 7, 15, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73077_, p_73080_, p_73081_, StrongholdPieces.StrongholdPiece.SmallDoorType.GRATES, 4, 1, 0);
         int i = 6;
         this.m_73464_(p_73077_, p_73081_, 1, i, 1, 1, i, 14, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 9, i, 1, 9, i, 14, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 2, i, 1, 8, i, 2, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 2, i, 14, 8, i, 14, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 1, 1, 1, 2, 1, 4, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 8, 1, 1, 9, 1, 4, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73441_(p_73077_, p_73081_, 1, 1, 1, 1, 1, 3, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
         this.m_73441_(p_73077_, p_73081_, 9, 1, 1, 9, 1, 3, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
         this.m_73464_(p_73077_, p_73081_, 3, 1, 8, 7, 1, 12, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73441_(p_73077_, p_73081_, 4, 1, 9, 6, 1, 11, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
         BlockState blockstate = Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true));
         BlockState blockstate1 = Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true));

         for(int j = 3; j < 14; j += 2) {
            this.m_73441_(p_73077_, p_73081_, 0, 3, j, 0, 4, j, blockstate, blockstate, false);
            this.m_73441_(p_73077_, p_73081_, 10, 3, j, 10, 4, j, blockstate, blockstate, false);
         }

         for(int i1 = 2; i1 < 9; i1 += 2) {
            this.m_73441_(p_73077_, p_73081_, i1, 3, 15, i1, 4, 15, blockstate1, blockstate1, false);
         }

         BlockState blockstate5 = Blocks.f_50194_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
         this.m_73464_(p_73077_, p_73081_, 4, 1, 5, 6, 1, 7, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 4, 2, 6, 6, 2, 7, false, p_73080_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73077_, p_73081_, 4, 3, 7, 6, 3, 7, false, p_73080_, StrongholdPieces.f_72855_);

         for(int k = 4; k <= 6; ++k) {
            this.m_73434_(p_73077_, blockstate5, k, 1, 4, p_73081_);
            this.m_73434_(p_73077_, blockstate5, k, 2, 5, p_73081_);
            this.m_73434_(p_73077_, blockstate5, k, 3, 6, p_73081_);
         }

         BlockState blockstate6 = Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.NORTH);
         BlockState blockstate2 = Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.SOUTH);
         BlockState blockstate3 = Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.EAST);
         BlockState blockstate4 = Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.WEST);
         boolean flag = true;
         boolean[] aboolean = new boolean[12];

         for(int l = 0; l < aboolean.length; ++l) {
            aboolean[l] = p_73080_.nextFloat() > 0.9F;
            flag &= aboolean[l];
         }

         this.m_73434_(p_73077_, blockstate6.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[0])), 4, 3, 8, p_73081_);
         this.m_73434_(p_73077_, blockstate6.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[1])), 5, 3, 8, p_73081_);
         this.m_73434_(p_73077_, blockstate6.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[2])), 6, 3, 8, p_73081_);
         this.m_73434_(p_73077_, blockstate2.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[3])), 4, 3, 12, p_73081_);
         this.m_73434_(p_73077_, blockstate2.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[4])), 5, 3, 12, p_73081_);
         this.m_73434_(p_73077_, blockstate2.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[5])), 6, 3, 12, p_73081_);
         this.m_73434_(p_73077_, blockstate3.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[6])), 3, 3, 9, p_73081_);
         this.m_73434_(p_73077_, blockstate3.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[7])), 3, 3, 10, p_73081_);
         this.m_73434_(p_73077_, blockstate3.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[8])), 3, 3, 11, p_73081_);
         this.m_73434_(p_73077_, blockstate4.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[9])), 7, 3, 9, p_73081_);
         this.m_73434_(p_73077_, blockstate4.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[10])), 7, 3, 10, p_73081_);
         this.m_73434_(p_73077_, blockstate4.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(aboolean[11])), 7, 3, 11, p_73081_);
         if (flag) {
            BlockState blockstate7 = Blocks.f_50257_.m_49966_();
            this.m_73434_(p_73077_, blockstate7, 4, 3, 9, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 5, 3, 9, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 6, 3, 9, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 4, 3, 10, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 5, 3, 10, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 6, 3, 10, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 4, 3, 11, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 5, 3, 11, p_73081_);
            this.m_73434_(p_73077_, blockstate7, 6, 3, 11, p_73081_);
         }

         if (!this.f_73068_) {
            BlockPos blockpos = this.m_163582_(5, 3, 6);
            if (p_73081_.m_71051_(blockpos)) {
               this.f_73068_ = true;
               p_73077_.m_7731_(blockpos, Blocks.f_50085_.m_49966_(), 2);
               BlockEntity blockentity = p_73077_.m_7702_(blockpos);
               if (blockentity instanceof SpawnerBlockEntity) {
                  ((SpawnerBlockEntity)blockentity).m_59801_().m_45462_(EntityType.f_20523_);
               }
            }
         }

         return true;
      }
   }

   public static class PrisonHall extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163364_ = 9;
      protected static final int f_163365_ = 5;
      protected static final int f_163366_ = 11;

      public PrisonHall(int p_73098_, Random p_73099_, BoundingBox p_73100_, Direction p_73101_) {
         super(StructurePieceType.f_67161_, p_73098_, p_73100_);
         this.m_73519_(p_73101_);
         this.f_73303_ = this.m_73326_(p_73099_);
      }

      public PrisonHall(ServerLevel p_163368_, CompoundTag p_163369_) {
         super(StructurePieceType.f_67161_, p_163369_);
      }

      public void m_142537_(StructurePiece p_163371_, StructurePieceAccessor p_163372_, Random p_163373_) {
         this.m_163500_((StrongholdPieces.StartPiece)p_163371_, p_163372_, p_163373_, 1, 1);
      }

      public static StrongholdPieces.PrisonHall m_163374_(StructurePieceAccessor p_163375_, Random p_163376_, int p_163377_, int p_163378_, int p_163379_, Direction p_163380_, int p_163381_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163377_, p_163378_, p_163379_, -1, -1, 0, 9, 5, 11, p_163380_);
         return m_73318_(boundingbox) && p_163375_.m_141921_(boundingbox) == null ? new StrongholdPieces.PrisonHall(p_163381_, p_163376_, boundingbox, p_163380_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73106_, StructureFeatureManager p_73107_, ChunkGenerator p_73108_, Random p_73109_, BoundingBox p_73110_, ChunkPos p_73111_, BlockPos p_73112_) {
         this.m_73464_(p_73106_, p_73110_, 0, 0, 0, 8, 4, 10, true, p_73109_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73106_, p_73109_, p_73110_, this.f_73303_, 1, 1, 0);
         this.m_73441_(p_73106_, p_73110_, 1, 1, 10, 3, 3, 10, f_73382_, f_73382_, false);
         this.m_73464_(p_73106_, p_73110_, 4, 1, 1, 4, 3, 1, false, p_73109_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73106_, p_73110_, 4, 1, 3, 4, 3, 3, false, p_73109_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73106_, p_73110_, 4, 1, 7, 4, 3, 7, false, p_73109_, StrongholdPieces.f_72855_);
         this.m_73464_(p_73106_, p_73110_, 4, 1, 9, 4, 3, 9, false, p_73109_, StrongholdPieces.f_72855_);

         for(int i = 1; i <= 3; ++i) {
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true)), 4, i, 4, p_73110_);
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), 4, i, 5, p_73110_);
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true)), 4, i, 6, p_73110_);
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), 5, i, 5, p_73110_);
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), 6, i, 5, p_73110_);
            this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), 7, i, 5, p_73110_);
         }

         this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true)), 4, 3, 2, p_73110_);
         this.m_73434_(p_73106_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(true)), 4, 3, 8, p_73110_);
         BlockState blockstate1 = Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52726_, Direction.WEST);
         BlockState blockstate = Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52726_, Direction.WEST).m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER);
         this.m_73434_(p_73106_, blockstate1, 4, 1, 2, p_73110_);
         this.m_73434_(p_73106_, blockstate, 4, 2, 2, p_73110_);
         this.m_73434_(p_73106_, blockstate1, 4, 1, 8, p_73110_);
         this.m_73434_(p_73106_, blockstate, 4, 2, 8, p_73110_);
         return true;
      }
   }

   public static class RightTurn extends StrongholdPieces.Turn {
      public RightTurn(int p_73126_, Random p_73127_, BoundingBox p_73128_, Direction p_73129_) {
         super(StructurePieceType.f_67106_, p_73126_, p_73128_);
         this.m_73519_(p_73129_);
         this.f_73303_ = this.m_73326_(p_73127_);
      }

      public RightTurn(ServerLevel p_163384_, CompoundTag p_163385_) {
         super(StructurePieceType.f_67106_, p_163385_);
      }

      public void m_142537_(StructurePiece p_163387_, StructurePieceAccessor p_163388_, Random p_163389_) {
         Direction direction = this.m_73549_();
         if (direction != Direction.NORTH && direction != Direction.EAST) {
            this.m_163507_((StrongholdPieces.StartPiece)p_163387_, p_163388_, p_163389_, 1, 1);
         } else {
            this.m_163513_((StrongholdPieces.StartPiece)p_163387_, p_163388_, p_163389_, 1, 1);
         }

      }

      public static StrongholdPieces.RightTurn m_163390_(StructurePieceAccessor p_163391_, Random p_163392_, int p_163393_, int p_163394_, int p_163395_, Direction p_163396_, int p_163397_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163393_, p_163394_, p_163395_, -1, -1, 0, 5, 5, 5, p_163396_);
         return m_73318_(boundingbox) && p_163391_.m_141921_(boundingbox) == null ? new StrongholdPieces.RightTurn(p_163397_, p_163392_, boundingbox, p_163396_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73134_, StructureFeatureManager p_73135_, ChunkGenerator p_73136_, Random p_73137_, BoundingBox p_73138_, ChunkPos p_73139_, BlockPos p_73140_) {
         this.m_73464_(p_73134_, p_73138_, 0, 0, 0, 4, 4, 4, true, p_73137_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73134_, p_73137_, p_73138_, this.f_73303_, 1, 1, 0);
         Direction direction = this.m_73549_();
         if (direction != Direction.NORTH && direction != Direction.EAST) {
            this.m_73441_(p_73134_, p_73138_, 0, 1, 1, 0, 3, 3, f_73382_, f_73382_, false);
         } else {
            this.m_73441_(p_73134_, p_73138_, 4, 1, 1, 4, 3, 3, f_73382_, f_73382_, false);
         }

         return true;
      }
   }

   public static class RoomCrossing extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163398_ = 11;
      protected static final int f_163399_ = 7;
      protected static final int f_163400_ = 11;
      protected final int f_73153_;

      public RoomCrossing(int p_73155_, Random p_73156_, BoundingBox p_73157_, Direction p_73158_) {
         super(StructurePieceType.f_67107_, p_73155_, p_73157_);
         this.m_73519_(p_73158_);
         this.f_73303_ = this.m_73326_(p_73156_);
         this.f_73153_ = p_73156_.nextInt(5);
      }

      public RoomCrossing(ServerLevel p_163402_, CompoundTag p_163403_) {
         super(StructurePieceType.f_67107_, p_163403_);
         this.f_73153_ = p_163403_.m_128451_("Type");
      }

      protected void m_142347_(ServerLevel p_163405_, CompoundTag p_163406_) {
         super.m_142347_(p_163405_, p_163406_);
         p_163406_.m_128405_("Type", this.f_73153_);
      }

      public void m_142537_(StructurePiece p_163408_, StructurePieceAccessor p_163409_, Random p_163410_) {
         this.m_163500_((StrongholdPieces.StartPiece)p_163408_, p_163409_, p_163410_, 4, 1);
         this.m_163507_((StrongholdPieces.StartPiece)p_163408_, p_163409_, p_163410_, 1, 4);
         this.m_163513_((StrongholdPieces.StartPiece)p_163408_, p_163409_, p_163410_, 1, 4);
      }

      public static StrongholdPieces.RoomCrossing m_163411_(StructurePieceAccessor p_163412_, Random p_163413_, int p_163414_, int p_163415_, int p_163416_, Direction p_163417_, int p_163418_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163414_, p_163415_, p_163416_, -4, -1, 0, 11, 7, 11, p_163417_);
         return m_73318_(boundingbox) && p_163412_.m_141921_(boundingbox) == null ? new StrongholdPieces.RoomCrossing(p_163418_, p_163413_, boundingbox, p_163417_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73163_, StructureFeatureManager p_73164_, ChunkGenerator p_73165_, Random p_73166_, BoundingBox p_73167_, ChunkPos p_73168_, BlockPos p_73169_) {
         this.m_73464_(p_73163_, p_73167_, 0, 0, 0, 10, 6, 10, true, p_73166_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73163_, p_73166_, p_73167_, this.f_73303_, 4, 1, 0);
         this.m_73441_(p_73163_, p_73167_, 4, 1, 10, 6, 3, 10, f_73382_, f_73382_, false);
         this.m_73441_(p_73163_, p_73167_, 0, 1, 4, 0, 3, 6, f_73382_, f_73382_, false);
         this.m_73441_(p_73163_, p_73167_, 10, 1, 4, 10, 3, 6, f_73382_, f_73382_, false);
         switch(this.f_73153_) {
         case 0:
            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 2, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 3, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST), 4, 3, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST), 6, 3, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH), 5, 3, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.NORTH), 5, 3, 6, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 4, 1, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 4, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 4, 1, 6, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 6, 1, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 6, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 6, 1, 6, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 5, 1, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50405_.m_49966_(), 5, 1, 6, p_73167_);
            break;
         case 1:
            for(int i1 = 0; i1 < 5; ++i1) {
               this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 3, 1, 3 + i1, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 7, 1, 3 + i1, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 3 + i1, 1, 3, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 3 + i1, 1, 7, p_73167_);
            }

            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 2, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50222_.m_49966_(), 5, 3, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_49990_.m_49966_(), 5, 4, 5, p_73167_);
            break;
         case 2:
            for(int i = 1; i <= 9; ++i) {
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 1, 3, i, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 9, 3, i, p_73167_);
            }

            for(int j = 1; j <= 9; ++j) {
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), j, 3, 1, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), j, 3, 9, p_73167_);
            }

            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 5, 1, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 5, 1, 6, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 5, 3, 4, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 5, 3, 6, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 4, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 6, 1, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 4, 3, 5, p_73167_);
            this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 6, 3, 5, p_73167_);

            for(int k = 1; k <= 3; ++k) {
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 4, k, 4, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 6, k, 4, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 4, k, 6, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50652_.m_49966_(), 6, k, 6, p_73167_);
            }

            this.m_73434_(p_73163_, Blocks.f_50081_.m_49966_(), 5, 3, 5, p_73167_);

            for(int l = 2; l <= 8; ++l) {
               this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 2, 3, l, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 3, 3, l, p_73167_);
               if (l <= 3 || l >= 7) {
                  this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 4, 3, l, p_73167_);
                  this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 5, 3, l, p_73167_);
                  this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 6, 3, l, p_73167_);
               }

               this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 7, 3, l, p_73167_);
               this.m_73434_(p_73163_, Blocks.f_50705_.m_49966_(), 8, 3, l, p_73167_);
            }

            BlockState blockstate = Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, Direction.WEST);
            this.m_73434_(p_73163_, blockstate, 9, 1, 3, p_73167_);
            this.m_73434_(p_73163_, blockstate, 9, 2, 3, p_73167_);
            this.m_73434_(p_73163_, blockstate, 9, 3, 3, p_73167_);
            this.m_5606_(p_73163_, p_73167_, p_73166_, 3, 4, 8, BuiltInLootTables.f_78762_);
         }

         return true;
      }
   }

   static class SmoothStoneSelector extends StructurePiece.BlockSelector {
      public void m_7889_(Random p_73188_, int p_73189_, int p_73190_, int p_73191_, boolean p_73192_) {
         if (p_73192_) {
            float f = p_73188_.nextFloat();
            if (f < 0.2F) {
               this.f_73553_ = Blocks.f_50224_.m_49966_();
            } else if (f < 0.5F) {
               this.f_73553_ = Blocks.f_50223_.m_49966_();
            } else if (f < 0.55F) {
               this.f_73553_ = Blocks.f_50176_.m_49966_();
            } else {
               this.f_73553_ = Blocks.f_50222_.m_49966_();
            }
         } else {
            this.f_73553_ = Blocks.f_50627_.m_49966_();
         }

      }
   }

   public static class StairsDown extends StrongholdPieces.StrongholdPiece {
      private static final int f_163420_ = 5;
      private static final int f_163421_ = 11;
      private static final int f_163422_ = 5;
      private final boolean f_73193_;

      public StairsDown(StructurePieceType p_163427_, int p_163428_, int p_163429_, int p_163430_, Direction p_163431_) {
         super(p_163427_, p_163428_, m_163541_(p_163429_, 64, p_163430_, p_163431_, 5, 11, 5));
         this.f_73193_ = true;
         this.m_73519_(p_163431_);
         this.f_73303_ = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
      }

      public StairsDown(int p_73195_, Random p_73196_, BoundingBox p_73197_, Direction p_73198_) {
         super(StructurePieceType.f_67108_, p_73195_, p_73197_);
         this.f_73193_ = false;
         this.m_73519_(p_73198_);
         this.f_73303_ = this.m_73326_(p_73196_);
      }

      public StairsDown(StructurePieceType p_73206_, CompoundTag p_73207_) {
         super(p_73206_, p_73207_);
         this.f_73193_ = p_73207_.m_128471_("Source");
      }

      public StairsDown(ServerLevel p_163424_, CompoundTag p_163425_) {
         this(StructurePieceType.f_67108_, p_163425_);
      }

      protected void m_142347_(ServerLevel p_163433_, CompoundTag p_163434_) {
         super.m_142347_(p_163433_, p_163434_);
         p_163434_.m_128379_("Source", this.f_73193_);
      }

      public void m_142537_(StructurePiece p_163436_, StructurePieceAccessor p_163437_, Random p_163438_) {
         if (this.f_73193_) {
            StrongholdPieces.f_72853_ = StrongholdPieces.FiveCrossing.class;
         }

         this.m_163500_((StrongholdPieces.StartPiece)p_163436_, p_163437_, p_163438_, 1, 1);
      }

      public static StrongholdPieces.StairsDown m_163439_(StructurePieceAccessor p_163440_, Random p_163441_, int p_163442_, int p_163443_, int p_163444_, Direction p_163445_, int p_163446_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163442_, p_163443_, p_163444_, -1, -7, 0, 5, 11, 5, p_163445_);
         return m_73318_(boundingbox) && p_163440_.m_141921_(boundingbox) == null ? new StrongholdPieces.StairsDown(p_163446_, p_163441_, boundingbox, p_163445_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73212_, StructureFeatureManager p_73213_, ChunkGenerator p_73214_, Random p_73215_, BoundingBox p_73216_, ChunkPos p_73217_, BlockPos p_73218_) {
         this.m_73464_(p_73212_, p_73216_, 0, 0, 0, 4, 10, 4, true, p_73215_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73212_, p_73215_, p_73216_, this.f_73303_, 1, 7, 0);
         this.m_73310_(p_73212_, p_73215_, p_73216_, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 4);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 2, 6, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 1, 5, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 1, 6, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 1, 5, 2, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 1, 4, 3, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 1, 5, 3, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 2, 4, 3, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 3, 3, 3, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 3, 4, 3, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 3, 3, 2, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 3, 2, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 3, 3, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 2, 2, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 1, 1, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 1, 2, 1, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50222_.m_49966_(), 1, 1, 2, p_73216_);
         this.m_73434_(p_73212_, Blocks.f_50405_.m_49966_(), 1, 1, 3, p_73216_);
         return true;
      }
   }

   public static class StartPiece extends StrongholdPieces.StairsDown {
      public StrongholdPieces.PieceWeight f_73233_;
      @Nullable
      public StrongholdPieces.PortalRoom f_73234_;
      public final List<StructurePiece> f_73235_ = Lists.newArrayList();

      public StartPiece(Random p_73240_, int p_73241_, int p_73242_) {
         super(StructurePieceType.f_67109_, 0, p_73241_, p_73242_, m_163580_(p_73240_));
      }

      public StartPiece(ServerLevel p_163449_, CompoundTag p_163450_) {
         super(StructurePieceType.f_67109_, p_163450_);
      }

      public BlockPos m_142171_() {
         return this.f_73234_ != null ? this.f_73234_.m_142171_() : super.m_142171_();
      }
   }

   public static class Straight extends StrongholdPieces.StrongholdPiece {
      private static final int f_163452_ = 5;
      private static final int f_163453_ = 5;
      private static final int f_163454_ = 7;
      private final boolean f_73243_;
      private final boolean f_73244_;

      public Straight(int p_73246_, Random p_73247_, BoundingBox p_73248_, Direction p_73249_) {
         super(StructurePieceType.f_67110_, p_73246_, p_73248_);
         this.m_73519_(p_73249_);
         this.f_73303_ = this.m_73326_(p_73247_);
         this.f_73243_ = p_73247_.nextInt(2) == 0;
         this.f_73244_ = p_73247_.nextInt(2) == 0;
      }

      public Straight(ServerLevel p_163456_, CompoundTag p_163457_) {
         super(StructurePieceType.f_67110_, p_163457_);
         this.f_73243_ = p_163457_.m_128471_("Left");
         this.f_73244_ = p_163457_.m_128471_("Right");
      }

      protected void m_142347_(ServerLevel p_163459_, CompoundTag p_163460_) {
         super.m_142347_(p_163459_, p_163460_);
         p_163460_.m_128379_("Left", this.f_73243_);
         p_163460_.m_128379_("Right", this.f_73244_);
      }

      public void m_142537_(StructurePiece p_163462_, StructurePieceAccessor p_163463_, Random p_163464_) {
         this.m_163500_((StrongholdPieces.StartPiece)p_163462_, p_163463_, p_163464_, 1, 1);
         if (this.f_73243_) {
            this.m_163507_((StrongholdPieces.StartPiece)p_163462_, p_163463_, p_163464_, 1, 2);
         }

         if (this.f_73244_) {
            this.m_163513_((StrongholdPieces.StartPiece)p_163462_, p_163463_, p_163464_, 1, 2);
         }

      }

      public static StrongholdPieces.Straight m_163465_(StructurePieceAccessor p_163466_, Random p_163467_, int p_163468_, int p_163469_, int p_163470_, Direction p_163471_, int p_163472_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163468_, p_163469_, p_163470_, -1, -1, 0, 5, 5, 7, p_163471_);
         return m_73318_(boundingbox) && p_163466_.m_141921_(boundingbox) == null ? new StrongholdPieces.Straight(p_163472_, p_163467_, boundingbox, p_163471_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73254_, StructureFeatureManager p_73255_, ChunkGenerator p_73256_, Random p_73257_, BoundingBox p_73258_, ChunkPos p_73259_, BlockPos p_73260_) {
         this.m_73464_(p_73254_, p_73258_, 0, 0, 0, 4, 4, 6, true, p_73257_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73254_, p_73257_, p_73258_, this.f_73303_, 1, 1, 0);
         this.m_73310_(p_73254_, p_73257_, p_73258_, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
         BlockState blockstate = Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST);
         BlockState blockstate1 = Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST);
         this.m_73491_(p_73254_, p_73258_, p_73257_, 0.1F, 1, 2, 1, blockstate);
         this.m_73491_(p_73254_, p_73258_, p_73257_, 0.1F, 3, 2, 1, blockstate1);
         this.m_73491_(p_73254_, p_73258_, p_73257_, 0.1F, 1, 2, 5, blockstate);
         this.m_73491_(p_73254_, p_73258_, p_73257_, 0.1F, 3, 2, 5, blockstate1);
         if (this.f_73243_) {
            this.m_73441_(p_73254_, p_73258_, 0, 1, 2, 0, 3, 4, f_73382_, f_73382_, false);
         }

         if (this.f_73244_) {
            this.m_73441_(p_73254_, p_73258_, 4, 1, 2, 4, 3, 4, f_73382_, f_73382_, false);
         }

         return true;
      }
   }

   public static class StraightStairsDown extends StrongholdPieces.StrongholdPiece {
      private static final int f_163474_ = 5;
      private static final int f_163475_ = 11;
      private static final int f_163476_ = 8;

      public StraightStairsDown(int p_73276_, Random p_73277_, BoundingBox p_73278_, Direction p_73279_) {
         super(StructurePieceType.f_67111_, p_73276_, p_73278_);
         this.m_73519_(p_73279_);
         this.f_73303_ = this.m_73326_(p_73277_);
      }

      public StraightStairsDown(ServerLevel p_163478_, CompoundTag p_163479_) {
         super(StructurePieceType.f_67111_, p_163479_);
      }

      public void m_142537_(StructurePiece p_163481_, StructurePieceAccessor p_163482_, Random p_163483_) {
         this.m_163500_((StrongholdPieces.StartPiece)p_163481_, p_163482_, p_163483_, 1, 1);
      }

      public static StrongholdPieces.StraightStairsDown m_163484_(StructurePieceAccessor p_163485_, Random p_163486_, int p_163487_, int p_163488_, int p_163489_, Direction p_163490_, int p_163491_) {
         BoundingBox boundingbox = BoundingBox.m_71031_(p_163487_, p_163488_, p_163489_, -1, -7, 0, 5, 11, 8, p_163490_);
         return m_73318_(boundingbox) && p_163485_.m_141921_(boundingbox) == null ? new StrongholdPieces.StraightStairsDown(p_163491_, p_163486_, boundingbox, p_163490_) : null;
      }

      public boolean m_7832_(WorldGenLevel p_73284_, StructureFeatureManager p_73285_, ChunkGenerator p_73286_, Random p_73287_, BoundingBox p_73288_, ChunkPos p_73289_, BlockPos p_73290_) {
         this.m_73464_(p_73284_, p_73288_, 0, 0, 0, 4, 10, 7, true, p_73287_, StrongholdPieces.f_72855_);
         this.m_73310_(p_73284_, p_73287_, p_73288_, this.f_73303_, 1, 7, 0);
         this.m_73310_(p_73284_, p_73287_, p_73288_, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 7);
         BlockState blockstate = Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);

         for(int i = 0; i < 6; ++i) {
            this.m_73434_(p_73284_, blockstate, 1, 6 - i, 1 + i, p_73288_);
            this.m_73434_(p_73284_, blockstate, 2, 6 - i, 1 + i, p_73288_);
            this.m_73434_(p_73284_, blockstate, 3, 6 - i, 1 + i, p_73288_);
            if (i < 5) {
               this.m_73434_(p_73284_, Blocks.f_50222_.m_49966_(), 1, 5 - i, 1 + i, p_73288_);
               this.m_73434_(p_73284_, Blocks.f_50222_.m_49966_(), 2, 5 - i, 1 + i, p_73288_);
               this.m_73434_(p_73284_, Blocks.f_50222_.m_49966_(), 3, 5 - i, 1 + i, p_73288_);
            }
         }

         return true;
      }
   }

   abstract static class StrongholdPiece extends StructurePiece {
      protected StrongholdPieces.StrongholdPiece.SmallDoorType f_73303_ = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;

      protected StrongholdPiece(StructurePieceType p_163494_, int p_163495_, BoundingBox p_163496_) {
         super(p_163494_, p_163495_, p_163496_);
      }

      public StrongholdPiece(StructurePieceType p_73308_, CompoundTag p_73309_) {
         super(p_73308_, p_73309_);
         this.f_73303_ = StrongholdPieces.StrongholdPiece.SmallDoorType.valueOf(p_73309_.m_128461_("EntryDoor"));
      }

      public NoiseEffect m_142318_() {
         return NoiseEffect.BURY;
      }

      protected void m_142347_(ServerLevel p_163498_, CompoundTag p_163499_) {
         p_163499_.m_128359_("EntryDoor", this.f_73303_.name());
      }

      protected void m_73310_(WorldGenLevel p_73311_, Random p_73312_, BoundingBox p_73313_, StrongholdPieces.StrongholdPiece.SmallDoorType p_73314_, int p_73315_, int p_73316_, int p_73317_) {
         switch(p_73314_) {
         case OPENING:
            this.m_73441_(p_73311_, p_73313_, p_73315_, p_73316_, p_73317_, p_73315_ + 3 - 1, p_73316_ + 3 - 1, p_73317_, f_73382_, f_73382_, false);
            break;
         case WOOD_DOOR:
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 1, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50154_.m_49966_(), p_73315_ + 1, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50154_.m_49966_().m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER), p_73315_ + 1, p_73316_ + 1, p_73317_, p_73313_);
            break;
         case GRATES:
            this.m_73434_(p_73311_, Blocks.f_50627_.m_49966_(), p_73315_ + 1, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50627_.m_49966_(), p_73315_ + 1, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)), p_73315_, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)), p_73315_, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)), p_73315_, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)), p_73315_ + 1, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)).m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(true)), p_73315_ + 2, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), p_73315_ + 2, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(true)), p_73315_ + 2, p_73316_, p_73317_, p_73313_);
            break;
         case IRON_DOOR:
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 1, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_ + 2, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50222_.m_49966_(), p_73315_ + 2, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50166_.m_49966_(), p_73315_ + 1, p_73316_, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER), p_73315_ + 1, p_73316_ + 1, p_73317_, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50124_.m_49966_().m_61124_(ButtonBlock.f_54117_, Direction.NORTH), p_73315_ + 2, p_73316_ + 1, p_73317_ + 1, p_73313_);
            this.m_73434_(p_73311_, Blocks.f_50124_.m_49966_().m_61124_(ButtonBlock.f_54117_, Direction.SOUTH), p_73315_ + 2, p_73316_ + 1, p_73317_ - 1, p_73313_);
         }

      }

      protected StrongholdPieces.StrongholdPiece.SmallDoorType m_73326_(Random p_73327_) {
         int i = p_73327_.nextInt(5);
         switch(i) {
         case 0:
         case 1:
         default:
            return StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
         case 2:
            return StrongholdPieces.StrongholdPiece.SmallDoorType.WOOD_DOOR;
         case 3:
            return StrongholdPieces.StrongholdPiece.SmallDoorType.GRATES;
         case 4:
            return StrongholdPieces.StrongholdPiece.SmallDoorType.IRON_DOOR;
         }
      }

      @Nullable
      protected StructurePiece m_163500_(StrongholdPieces.StartPiece p_163501_, StructurePieceAccessor p_163502_, Random p_163503_, int p_163504_, int p_163505_) {
         Direction direction = this.m_73549_();
         if (direction != null) {
            switch(direction) {
            case NORTH:
               return StrongholdPieces.m_163242_(p_163501_, p_163502_, p_163503_, this.f_73383_.m_162395_() + p_163504_, this.f_73383_.m_162396_() + p_163505_, this.f_73383_.m_162398_() - 1, direction, this.m_73548_());
            case SOUTH:
               return StrongholdPieces.m_163242_(p_163501_, p_163502_, p_163503_, this.f_73383_.m_162395_() + p_163504_, this.f_73383_.m_162396_() + p_163505_, this.f_73383_.m_162401_() + 1, direction, this.m_73548_());
            case WEST:
               return StrongholdPieces.m_163242_(p_163501_, p_163502_, p_163503_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_163505_, this.f_73383_.m_162398_() + p_163504_, direction, this.m_73548_());
            case EAST:
               return StrongholdPieces.m_163242_(p_163501_, p_163502_, p_163503_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_163505_, this.f_73383_.m_162398_() + p_163504_, direction, this.m_73548_());
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece m_163507_(StrongholdPieces.StartPiece p_163508_, StructurePieceAccessor p_163509_, Random p_163510_, int p_163511_, int p_163512_) {
         Direction direction = this.m_73549_();
         if (direction != null) {
            switch(direction) {
            case NORTH:
               return StrongholdPieces.m_163242_(p_163508_, p_163509_, p_163510_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_163511_, this.f_73383_.m_162398_() + p_163512_, Direction.WEST, this.m_73548_());
            case SOUTH:
               return StrongholdPieces.m_163242_(p_163508_, p_163509_, p_163510_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_163511_, this.f_73383_.m_162398_() + p_163512_, Direction.WEST, this.m_73548_());
            case WEST:
               return StrongholdPieces.m_163242_(p_163508_, p_163509_, p_163510_, this.f_73383_.m_162395_() + p_163512_, this.f_73383_.m_162396_() + p_163511_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_());
            case EAST:
               return StrongholdPieces.m_163242_(p_163508_, p_163509_, p_163510_, this.f_73383_.m_162395_() + p_163512_, this.f_73383_.m_162396_() + p_163511_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_());
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece m_163513_(StrongholdPieces.StartPiece p_163514_, StructurePieceAccessor p_163515_, Random p_163516_, int p_163517_, int p_163518_) {
         Direction direction = this.m_73549_();
         if (direction != null) {
            switch(direction) {
            case NORTH:
               return StrongholdPieces.m_163242_(p_163514_, p_163515_, p_163516_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_163517_, this.f_73383_.m_162398_() + p_163518_, Direction.EAST, this.m_73548_());
            case SOUTH:
               return StrongholdPieces.m_163242_(p_163514_, p_163515_, p_163516_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_163517_, this.f_73383_.m_162398_() + p_163518_, Direction.EAST, this.m_73548_());
            case WEST:
               return StrongholdPieces.m_163242_(p_163514_, p_163515_, p_163516_, this.f_73383_.m_162395_() + p_163518_, this.f_73383_.m_162396_() + p_163517_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_());
            case EAST:
               return StrongholdPieces.m_163242_(p_163514_, p_163515_, p_163516_, this.f_73383_.m_162395_() + p_163518_, this.f_73383_.m_162396_() + p_163517_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_());
            }
         }

         return null;
      }

      protected static boolean m_73318_(BoundingBox p_73319_) {
         return p_73319_ != null && p_73319_.m_162396_() > 10;
      }

      protected static enum SmallDoorType {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;
      }
   }

   public abstract static class Turn extends StrongholdPieces.StrongholdPiece {
      protected static final int f_163520_ = 5;
      protected static final int f_163521_ = 5;
      protected static final int f_163522_ = 5;

      protected Turn(StructurePieceType p_163524_, int p_163525_, BoundingBox p_163526_) {
         super(p_163524_, p_163525_, p_163526_);
      }

      public Turn(StructurePieceType p_73358_, CompoundTag p_73359_) {
         super(p_73358_, p_73359_);
      }
   }
}