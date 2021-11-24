package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class WoodlandMansionPieces {
   public static void m_73691_(StructureManager p_73692_, BlockPos p_73693_, Rotation p_73694_, List<WoodlandMansionPieces.WoodlandMansionPiece> p_73695_, Random p_73696_) {
      WoodlandMansionPieces.MansionGrid woodlandmansionpieces$mansiongrid = new WoodlandMansionPieces.MansionGrid(p_73696_);
      WoodlandMansionPieces.MansionPiecePlacer woodlandmansionpieces$mansionpieceplacer = new WoodlandMansionPieces.MansionPiecePlacer(p_73692_, p_73696_);
      woodlandmansionpieces$mansionpieceplacer.m_73781_(p_73693_, p_73694_, p_73695_, woodlandmansionpieces$mansiongrid);
   }

   public static void m_163676_(String[] p_163677_) {
      Random random = new Random();
      long i = random.nextLong();
      System.out.println("Seed: " + i);
      random.setSeed(i);
      WoodlandMansionPieces.MansionGrid woodlandmansionpieces$mansiongrid = new WoodlandMansionPieces.MansionGrid(random);
      woodlandmansionpieces$mansiongrid.m_163694_();
   }

   static class FirstFloorRoomCollection extends WoodlandMansionPieces.FloorRoomCollection {
      public String m_7668_(Random p_73701_) {
         return "1x1_a" + (p_73701_.nextInt(5) + 1);
      }

      public String m_7669_(Random p_73706_) {
         return "1x1_as" + (p_73706_.nextInt(4) + 1);
      }

      public String m_5900_(Random p_73703_, boolean p_73704_) {
         return "1x2_a" + (p_73703_.nextInt(9) + 1);
      }

      public String m_5899_(Random p_73708_, boolean p_73709_) {
         return "1x2_b" + (p_73708_.nextInt(5) + 1);
      }

      public String m_7672_(Random p_73711_) {
         return "1x2_s" + (p_73711_.nextInt(2) + 1);
      }

      public String m_7661_(Random p_73713_) {
         return "2x2_a" + (p_73713_.nextInt(4) + 1);
      }

      public String m_7676_(Random p_73715_) {
         return "2x2_s1";
      }
   }

   abstract static class FloorRoomCollection {
      public abstract String m_7668_(Random p_73719_);

      public abstract String m_7669_(Random p_73722_);

      public abstract String m_5900_(Random p_73720_, boolean p_73721_);

      public abstract String m_5899_(Random p_73723_, boolean p_73724_);

      public abstract String m_7672_(Random p_73725_);

      public abstract String m_7661_(Random p_73726_);

      public abstract String m_7676_(Random p_73727_);
   }

   static class MansionGrid {
      private static final int f_163678_ = 11;
      private static final int f_163679_ = 0;
      private static final int f_163680_ = 1;
      private static final int f_163681_ = 2;
      private static final int f_163682_ = 3;
      private static final int f_163683_ = 4;
      private static final int f_163684_ = 5;
      private static final int f_163685_ = 65536;
      private static final int f_163686_ = 131072;
      private static final int f_163687_ = 262144;
      private static final int f_163688_ = 1048576;
      private static final int f_163689_ = 2097152;
      private static final int f_163690_ = 4194304;
      private static final int f_163691_ = 8388608;
      private static final int f_163692_ = 983040;
      private static final int f_163693_ = 65535;
      private final Random f_73728_;
      final WoodlandMansionPieces.SimpleGrid f_73729_;
      final WoodlandMansionPieces.SimpleGrid f_73730_;
      final WoodlandMansionPieces.SimpleGrid[] f_73731_;
      final int f_73732_;
      final int f_73733_;

      public MansionGrid(Random p_73735_) {
         this.f_73728_ = p_73735_;
         int i = 11;
         this.f_73732_ = 7;
         this.f_73733_ = 4;
         this.f_73729_ = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.f_73729_.m_73884_(this.f_73732_, this.f_73733_, this.f_73732_ + 1, this.f_73733_ + 1, 3);
         this.f_73729_.m_73884_(this.f_73732_ - 1, this.f_73733_, this.f_73732_ - 1, this.f_73733_ + 1, 2);
         this.f_73729_.m_73884_(this.f_73732_ + 2, this.f_73733_ - 2, this.f_73732_ + 3, this.f_73733_ + 3, 5);
         this.f_73729_.m_73884_(this.f_73732_ + 1, this.f_73733_ - 2, this.f_73732_ + 1, this.f_73733_ - 1, 1);
         this.f_73729_.m_73884_(this.f_73732_ + 1, this.f_73733_ + 2, this.f_73732_ + 1, this.f_73733_ + 3, 1);
         this.f_73729_.m_73875_(this.f_73732_ - 1, this.f_73733_ - 1, 1);
         this.f_73729_.m_73875_(this.f_73732_ - 1, this.f_73733_ + 2, 1);
         this.f_73729_.m_73884_(0, 0, 11, 1, 5);
         this.f_73729_.m_73884_(0, 9, 11, 11, 5);
         this.m_73750_(this.f_73729_, this.f_73732_, this.f_73733_ - 2, Direction.WEST, 6);
         this.m_73750_(this.f_73729_, this.f_73732_, this.f_73733_ + 3, Direction.WEST, 6);
         this.m_73750_(this.f_73729_, this.f_73732_ - 2, this.f_73733_ - 1, Direction.WEST, 3);
         this.m_73750_(this.f_73729_, this.f_73732_ - 2, this.f_73733_ + 2, Direction.WEST, 3);

         while(this.m_73738_(this.f_73729_)) {
         }

         this.f_73731_ = new WoodlandMansionPieces.SimpleGrid[3];
         this.f_73731_[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.f_73731_[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.f_73731_[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.m_73756_(this.f_73729_, this.f_73731_[0]);
         this.m_73756_(this.f_73729_, this.f_73731_[1]);
         this.f_73731_[0].m_73884_(this.f_73732_ + 1, this.f_73733_, this.f_73732_ + 1, this.f_73733_ + 1, 8388608);
         this.f_73731_[1].m_73884_(this.f_73732_ + 1, this.f_73733_, this.f_73732_ + 1, this.f_73733_ + 1, 8388608);
         this.f_73730_ = new WoodlandMansionPieces.SimpleGrid(this.f_73729_.f_73865_, this.f_73729_.f_73866_, 5);
         this.m_73759_();
         this.m_73756_(this.f_73730_, this.f_73731_[2]);
      }

      public static boolean m_73740_(WoodlandMansionPieces.SimpleGrid p_73741_, int p_73742_, int p_73743_) {
         int i = p_73741_.m_73872_(p_73742_, p_73743_);
         return i == 1 || i == 2 || i == 3 || i == 4;
      }

      public boolean m_73744_(WoodlandMansionPieces.SimpleGrid p_73745_, int p_73746_, int p_73747_, int p_73748_, int p_73749_) {
         return (this.f_73731_[p_73748_].m_73872_(p_73746_, p_73747_) & '\uffff') == p_73749_;
      }

      @Nullable
      public Direction m_73762_(WoodlandMansionPieces.SimpleGrid p_73763_, int p_73764_, int p_73765_, int p_73766_, int p_73767_) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (this.m_73744_(p_73763_, p_73764_ + direction.m_122429_(), p_73765_ + direction.m_122431_(), p_73766_, p_73767_)) {
               return direction;
            }
         }

         return null;
      }

      private void m_73750_(WoodlandMansionPieces.SimpleGrid p_73751_, int p_73752_, int p_73753_, Direction p_73754_, int p_73755_) {
         if (p_73755_ > 0) {
            p_73751_.m_73875_(p_73752_, p_73753_, 1);
            p_73751_.m_73879_(p_73752_ + p_73754_.m_122429_(), p_73753_ + p_73754_.m_122431_(), 0, 1);

            for(int i = 0; i < 8; ++i) {
               Direction direction = Direction.m_122407_(this.f_73728_.nextInt(4));
               if (direction != p_73754_.m_122424_() && (direction != Direction.EAST || !this.f_73728_.nextBoolean())) {
                  int j = p_73752_ + p_73754_.m_122429_();
                  int k = p_73753_ + p_73754_.m_122431_();
                  if (p_73751_.m_73872_(j + direction.m_122429_(), k + direction.m_122431_()) == 0 && p_73751_.m_73872_(j + direction.m_122429_() * 2, k + direction.m_122431_() * 2) == 0) {
                     this.m_73750_(p_73751_, p_73752_ + p_73754_.m_122429_() + direction.m_122429_(), p_73753_ + p_73754_.m_122431_() + direction.m_122431_(), direction, p_73755_ - 1);
                     break;
                  }
               }
            }

            Direction direction1 = p_73754_.m_122427_();
            Direction direction2 = p_73754_.m_122428_();
            p_73751_.m_73879_(p_73752_ + direction1.m_122429_(), p_73753_ + direction1.m_122431_(), 0, 2);
            p_73751_.m_73879_(p_73752_ + direction2.m_122429_(), p_73753_ + direction2.m_122431_(), 0, 2);
            p_73751_.m_73879_(p_73752_ + p_73754_.m_122429_() + direction1.m_122429_(), p_73753_ + p_73754_.m_122431_() + direction1.m_122431_(), 0, 2);
            p_73751_.m_73879_(p_73752_ + p_73754_.m_122429_() + direction2.m_122429_(), p_73753_ + p_73754_.m_122431_() + direction2.m_122431_(), 0, 2);
            p_73751_.m_73879_(p_73752_ + p_73754_.m_122429_() * 2, p_73753_ + p_73754_.m_122431_() * 2, 0, 2);
            p_73751_.m_73879_(p_73752_ + direction1.m_122429_() * 2, p_73753_ + direction1.m_122431_() * 2, 0, 2);
            p_73751_.m_73879_(p_73752_ + direction2.m_122429_() * 2, p_73753_ + direction2.m_122431_() * 2, 0, 2);
         }
      }

      private boolean m_73738_(WoodlandMansionPieces.SimpleGrid p_73739_) {
         boolean flag = false;

         for(int i = 0; i < p_73739_.f_73866_; ++i) {
            for(int j = 0; j < p_73739_.f_73865_; ++j) {
               if (p_73739_.m_73872_(j, i) == 0) {
                  int k = 0;
                  k = k + (m_73740_(p_73739_, j + 1, i) ? 1 : 0);
                  k = k + (m_73740_(p_73739_, j - 1, i) ? 1 : 0);
                  k = k + (m_73740_(p_73739_, j, i + 1) ? 1 : 0);
                  k = k + (m_73740_(p_73739_, j, i - 1) ? 1 : 0);
                  if (k >= 3) {
                     p_73739_.m_73875_(j, i, 2);
                     flag = true;
                  } else if (k == 2) {
                     int l = 0;
                     l = l + (m_73740_(p_73739_, j + 1, i + 1) ? 1 : 0);
                     l = l + (m_73740_(p_73739_, j - 1, i + 1) ? 1 : 0);
                     l = l + (m_73740_(p_73739_, j + 1, i - 1) ? 1 : 0);
                     l = l + (m_73740_(p_73739_, j - 1, i - 1) ? 1 : 0);
                     if (l <= 1) {
                        p_73739_.m_73875_(j, i, 2);
                        flag = true;
                     }
                  }
               }
            }
         }

         return flag;
      }

      private void m_73759_() {
         List<Tuple<Integer, Integer>> list = Lists.newArrayList();
         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = this.f_73731_[1];

         for(int i = 0; i < this.f_73730_.f_73866_; ++i) {
            for(int j = 0; j < this.f_73730_.f_73865_; ++j) {
               int k = woodlandmansionpieces$simplegrid.m_73872_(j, i);
               int l = k & 983040;
               if (l == 131072 && (k & 2097152) == 2097152) {
                  list.add(new Tuple<>(j, i));
               }
            }
         }

         if (list.isEmpty()) {
            this.f_73730_.m_73884_(0, 0, this.f_73730_.f_73865_, this.f_73730_.f_73866_, 5);
         } else {
            Tuple<Integer, Integer> tuple = list.get(this.f_73728_.nextInt(list.size()));
            int l1 = woodlandmansionpieces$simplegrid.m_73872_(tuple.m_14418_(), tuple.m_14419_());
            woodlandmansionpieces$simplegrid.m_73875_(tuple.m_14418_(), tuple.m_14419_(), l1 | 4194304);
            Direction direction1 = this.m_73762_(this.f_73729_, tuple.m_14418_(), tuple.m_14419_(), 1, l1 & '\uffff');
            int i2 = tuple.m_14418_() + direction1.m_122429_();
            int i1 = tuple.m_14419_() + direction1.m_122431_();

            for(int j1 = 0; j1 < this.f_73730_.f_73866_; ++j1) {
               for(int k1 = 0; k1 < this.f_73730_.f_73865_; ++k1) {
                  if (!m_73740_(this.f_73729_, k1, j1)) {
                     this.f_73730_.m_73875_(k1, j1, 5);
                  } else if (k1 == tuple.m_14418_() && j1 == tuple.m_14419_()) {
                     this.f_73730_.m_73875_(k1, j1, 3);
                  } else if (k1 == i2 && j1 == i1) {
                     this.f_73730_.m_73875_(k1, j1, 3);
                     this.f_73731_[2].m_73875_(k1, j1, 8388608);
                  }
               }
            }

            List<Direction> list1 = Lists.newArrayList();

            for(Direction direction : Direction.Plane.HORIZONTAL) {
               if (this.f_73730_.m_73872_(i2 + direction.m_122429_(), i1 + direction.m_122431_()) == 0) {
                  list1.add(direction);
               }
            }

            if (list1.isEmpty()) {
               this.f_73730_.m_73884_(0, 0, this.f_73730_.f_73865_, this.f_73730_.f_73866_, 5);
               woodlandmansionpieces$simplegrid.m_73875_(tuple.m_14418_(), tuple.m_14419_(), l1);
            } else {
               Direction direction2 = list1.get(this.f_73728_.nextInt(list1.size()));
               this.m_73750_(this.f_73730_, i2 + direction2.m_122429_(), i1 + direction2.m_122431_(), direction2, 4);

               while(this.m_73738_(this.f_73730_)) {
               }

            }
         }
      }

      private void m_73756_(WoodlandMansionPieces.SimpleGrid p_73757_, WoodlandMansionPieces.SimpleGrid p_73758_) {
         List<Tuple<Integer, Integer>> list = Lists.newArrayList();

         for(int i = 0; i < p_73757_.f_73866_; ++i) {
            for(int j = 0; j < p_73757_.f_73865_; ++j) {
               if (p_73757_.m_73872_(j, i) == 2) {
                  list.add(new Tuple<>(j, i));
               }
            }
         }

         Collections.shuffle(list, this.f_73728_);
         int k3 = 10;

         for(Tuple<Integer, Integer> tuple : list) {
            int k = tuple.m_14418_();
            int l = tuple.m_14419_();
            if (p_73758_.m_73872_(k, l) == 0) {
               int i1 = k;
               int j1 = k;
               int k1 = l;
               int l1 = l;
               int i2 = 65536;
               if (p_73758_.m_73872_(k + 1, l) == 0 && p_73758_.m_73872_(k, l + 1) == 0 && p_73758_.m_73872_(k + 1, l + 1) == 0 && p_73757_.m_73872_(k + 1, l) == 2 && p_73757_.m_73872_(k, l + 1) == 2 && p_73757_.m_73872_(k + 1, l + 1) == 2) {
                  j1 = k + 1;
                  l1 = l + 1;
                  i2 = 262144;
               } else if (p_73758_.m_73872_(k - 1, l) == 0 && p_73758_.m_73872_(k, l + 1) == 0 && p_73758_.m_73872_(k - 1, l + 1) == 0 && p_73757_.m_73872_(k - 1, l) == 2 && p_73757_.m_73872_(k, l + 1) == 2 && p_73757_.m_73872_(k - 1, l + 1) == 2) {
                  i1 = k - 1;
                  l1 = l + 1;
                  i2 = 262144;
               } else if (p_73758_.m_73872_(k - 1, l) == 0 && p_73758_.m_73872_(k, l - 1) == 0 && p_73758_.m_73872_(k - 1, l - 1) == 0 && p_73757_.m_73872_(k - 1, l) == 2 && p_73757_.m_73872_(k, l - 1) == 2 && p_73757_.m_73872_(k - 1, l - 1) == 2) {
                  i1 = k - 1;
                  k1 = l - 1;
                  i2 = 262144;
               } else if (p_73758_.m_73872_(k + 1, l) == 0 && p_73757_.m_73872_(k + 1, l) == 2) {
                  j1 = k + 1;
                  i2 = 131072;
               } else if (p_73758_.m_73872_(k, l + 1) == 0 && p_73757_.m_73872_(k, l + 1) == 2) {
                  l1 = l + 1;
                  i2 = 131072;
               } else if (p_73758_.m_73872_(k - 1, l) == 0 && p_73757_.m_73872_(k - 1, l) == 2) {
                  i1 = k - 1;
                  i2 = 131072;
               } else if (p_73758_.m_73872_(k, l - 1) == 0 && p_73757_.m_73872_(k, l - 1) == 2) {
                  k1 = l - 1;
                  i2 = 131072;
               }

               int j2 = this.f_73728_.nextBoolean() ? i1 : j1;
               int k2 = this.f_73728_.nextBoolean() ? k1 : l1;
               int l2 = 2097152;
               if (!p_73757_.m_73892_(j2, k2, 1)) {
                  j2 = j2 == i1 ? j1 : i1;
                  k2 = k2 == k1 ? l1 : k1;
                  if (!p_73757_.m_73892_(j2, k2, 1)) {
                     k2 = k2 == k1 ? l1 : k1;
                     if (!p_73757_.m_73892_(j2, k2, 1)) {
                        j2 = j2 == i1 ? j1 : i1;
                        k2 = k2 == k1 ? l1 : k1;
                        if (!p_73757_.m_73892_(j2, k2, 1)) {
                           l2 = 0;
                           j2 = i1;
                           k2 = k1;
                        }
                     }
                  }
               }

               for(int i3 = k1; i3 <= l1; ++i3) {
                  for(int j3 = i1; j3 <= j1; ++j3) {
                     if (j3 == j2 && i3 == k2) {
                        p_73758_.m_73875_(j3, i3, 1048576 | l2 | i2 | k3);
                     } else {
                        p_73758_.m_73875_(j3, i3, i2 | k3);
                     }
                  }
               }

               ++k3;
            }
         }

      }

      public void m_163694_() {
         for(int i = 0; i < 2; ++i) {
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = i == 0 ? this.f_73729_ : this.f_73730_;

            for(int j = 0; j < woodlandmansionpieces$simplegrid.f_73866_; ++j) {
               for(int k = 0; k < woodlandmansionpieces$simplegrid.f_73865_; ++k) {
                  int l = woodlandmansionpieces$simplegrid.m_73872_(k, j);
                  if (l == 1) {
                     System.out.print("+");
                  } else if (l == 4) {
                     System.out.print("x");
                  } else if (l == 2) {
                     System.out.print("X");
                  } else if (l == 3) {
                     System.out.print("O");
                  } else if (l == 5) {
                     System.out.print("#");
                  } else {
                     System.out.print(" ");
                  }
               }

               System.out.println("");
            }

            System.out.println("");
         }

      }
   }

   static class MansionPiecePlacer {
      private final StructureManager f_73774_;
      private final Random f_73775_;
      private int f_73776_;
      private int f_73777_;

      public MansionPiecePlacer(StructureManager p_73779_, Random p_73780_) {
         this.f_73774_ = p_73779_;
         this.f_73775_ = p_73780_;
      }

      public void m_73781_(BlockPos p_73782_, Rotation p_73783_, List<WoodlandMansionPieces.WoodlandMansionPiece> p_73784_, WoodlandMansionPieces.MansionGrid p_73785_) {
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata = new WoodlandMansionPieces.PlacementData();
         woodlandmansionpieces$placementdata.f_73840_ = p_73782_;
         woodlandmansionpieces$placementdata.f_73839_ = p_73783_;
         woodlandmansionpieces$placementdata.f_73841_ = "wall_flat";
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata1 = new WoodlandMansionPieces.PlacementData();
         this.m_73786_(p_73784_, woodlandmansionpieces$placementdata);
         woodlandmansionpieces$placementdata1.f_73840_ = woodlandmansionpieces$placementdata.f_73840_.m_6630_(8);
         woodlandmansionpieces$placementdata1.f_73839_ = woodlandmansionpieces$placementdata.f_73839_;
         woodlandmansionpieces$placementdata1.f_73841_ = "wall_window";
         if (!p_73784_.isEmpty()) {
         }

         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = p_73785_.f_73729_;
         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid1 = p_73785_.f_73730_;
         this.f_73776_ = p_73785_.f_73732_ + 1;
         this.f_73777_ = p_73785_.f_73733_ + 1;
         int i = p_73785_.f_73732_ + 1;
         int j = p_73785_.f_73733_;
         this.m_73789_(p_73784_, woodlandmansionpieces$placementdata, woodlandmansionpieces$simplegrid, Direction.SOUTH, this.f_73776_, this.f_73777_, i, j);
         this.m_73789_(p_73784_, woodlandmansionpieces$placementdata1, woodlandmansionpieces$simplegrid, Direction.SOUTH, this.f_73776_, this.f_73777_, i, j);
         WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata2 = new WoodlandMansionPieces.PlacementData();
         woodlandmansionpieces$placementdata2.f_73840_ = woodlandmansionpieces$placementdata.f_73840_.m_6630_(19);
         woodlandmansionpieces$placementdata2.f_73839_ = woodlandmansionpieces$placementdata.f_73839_;
         woodlandmansionpieces$placementdata2.f_73841_ = "wall_window";
         boolean flag = false;

         for(int k = 0; k < woodlandmansionpieces$simplegrid1.f_73866_ && !flag; ++k) {
            for(int l = woodlandmansionpieces$simplegrid1.f_73865_ - 1; l >= 0 && !flag; --l) {
               if (WoodlandMansionPieces.MansionGrid.m_73740_(woodlandmansionpieces$simplegrid1, l, k)) {
                  woodlandmansionpieces$placementdata2.f_73840_ = woodlandmansionpieces$placementdata2.f_73840_.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 8 + (k - this.f_73777_) * 8);
                  woodlandmansionpieces$placementdata2.f_73840_ = woodlandmansionpieces$placementdata2.f_73840_.m_5484_(p_73783_.m_55954_(Direction.EAST), (l - this.f_73776_) * 8);
                  this.m_73830_(p_73784_, woodlandmansionpieces$placementdata2);
                  this.m_73789_(p_73784_, woodlandmansionpieces$placementdata2, woodlandmansionpieces$simplegrid1, Direction.SOUTH, l, k, l, k);
                  flag = true;
               }
            }
         }

         this.m_73803_(p_73784_, p_73782_.m_6630_(16), p_73783_, woodlandmansionpieces$simplegrid, woodlandmansionpieces$simplegrid1);
         this.m_73803_(p_73784_, p_73782_.m_6630_(27), p_73783_, woodlandmansionpieces$simplegrid1, (WoodlandMansionPieces.SimpleGrid)null);
         if (!p_73784_.isEmpty()) {
         }

         WoodlandMansionPieces.FloorRoomCollection[] awoodlandmansionpieces$floorroomcollection = new WoodlandMansionPieces.FloorRoomCollection[]{new WoodlandMansionPieces.FirstFloorRoomCollection(), new WoodlandMansionPieces.SecondFloorRoomCollection(), new WoodlandMansionPieces.ThirdFloorRoomCollection()};

         for(int l2 = 0; l2 < 3; ++l2) {
            BlockPos blockpos = p_73782_.m_6630_(8 * l2 + (l2 == 2 ? 3 : 0));
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid2 = p_73785_.f_73731_[l2];
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid3 = l2 == 2 ? woodlandmansionpieces$simplegrid1 : woodlandmansionpieces$simplegrid;
            String s = l2 == 0 ? "carpet_south_1" : "carpet_south_2";
            String s1 = l2 == 0 ? "carpet_west_1" : "carpet_west_2";

            for(int i1 = 0; i1 < woodlandmansionpieces$simplegrid3.f_73866_; ++i1) {
               for(int j1 = 0; j1 < woodlandmansionpieces$simplegrid3.f_73865_; ++j1) {
                  if (woodlandmansionpieces$simplegrid3.m_73872_(j1, i1) == 1) {
                     BlockPos blockpos1 = blockpos.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 8 + (i1 - this.f_73777_) * 8);
                     blockpos1 = blockpos1.m_5484_(p_73783_.m_55954_(Direction.EAST), (j1 - this.f_73776_) * 8);
                     p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "corridor_floor", blockpos1, p_73783_));
                     if (woodlandmansionpieces$simplegrid3.m_73872_(j1, i1 - 1) == 1 || (woodlandmansionpieces$simplegrid2.m_73872_(j1, i1 - 1) & 8388608) == 8388608) {
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "carpet_north", blockpos1.m_5484_(p_73783_.m_55954_(Direction.EAST), 1).m_7494_(), p_73783_));
                     }

                     if (woodlandmansionpieces$simplegrid3.m_73872_(j1 + 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.m_73872_(j1 + 1, i1) & 8388608) == 8388608) {
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "carpet_east", blockpos1.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 1).m_5484_(p_73783_.m_55954_(Direction.EAST), 5).m_7494_(), p_73783_));
                     }

                     if (woodlandmansionpieces$simplegrid3.m_73872_(j1, i1 + 1) == 1 || (woodlandmansionpieces$simplegrid2.m_73872_(j1, i1 + 1) & 8388608) == 8388608) {
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, s, blockpos1.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 5).m_5484_(p_73783_.m_55954_(Direction.WEST), 1), p_73783_));
                     }

                     if (woodlandmansionpieces$simplegrid3.m_73872_(j1 - 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.m_73872_(j1 - 1, i1) & 8388608) == 8388608) {
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, s1, blockpos1.m_5484_(p_73783_.m_55954_(Direction.WEST), 1).m_5484_(p_73783_.m_55954_(Direction.NORTH), 1), p_73783_));
                     }
                  }
               }
            }

            String s2 = l2 == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String s3 = l2 == 0 ? "indoors_door_1" : "indoors_door_2";
            List<Direction> list = Lists.newArrayList();

            for(int k1 = 0; k1 < woodlandmansionpieces$simplegrid3.f_73866_; ++k1) {
               for(int l1 = 0; l1 < woodlandmansionpieces$simplegrid3.f_73865_; ++l1) {
                  boolean flag1 = l2 == 2 && woodlandmansionpieces$simplegrid3.m_73872_(l1, k1) == 3;
                  if (woodlandmansionpieces$simplegrid3.m_73872_(l1, k1) == 2 || flag1) {
                     int i2 = woodlandmansionpieces$simplegrid2.m_73872_(l1, k1);
                     int j2 = i2 & 983040;
                     int k2 = i2 & '\uffff';
                     flag1 = flag1 && (i2 & 8388608) == 8388608;
                     list.clear();
                     if ((i2 & 2097152) == 2097152) {
                        for(Direction direction : Direction.Plane.HORIZONTAL) {
                           if (woodlandmansionpieces$simplegrid3.m_73872_(l1 + direction.m_122429_(), k1 + direction.m_122431_()) == 1) {
                              list.add(direction);
                           }
                        }
                     }

                     Direction direction1 = null;
                     if (!list.isEmpty()) {
                        direction1 = list.get(this.f_73775_.nextInt(list.size()));
                     } else if ((i2 & 1048576) == 1048576) {
                        direction1 = Direction.UP;
                     }

                     BlockPos blockpos3 = blockpos.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 8 + (k1 - this.f_73777_) * 8);
                     blockpos3 = blockpos3.m_5484_(p_73783_.m_55954_(Direction.EAST), -1 + (l1 - this.f_73776_) * 8);
                     if (WoodlandMansionPieces.MansionGrid.m_73740_(woodlandmansionpieces$simplegrid3, l1 - 1, k1) && !p_73785_.m_73744_(woodlandmansionpieces$simplegrid3, l1 - 1, k1, l2, k2)) {
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, direction1 == Direction.WEST ? s3 : s2, blockpos3, p_73783_));
                     }

                     if (woodlandmansionpieces$simplegrid3.m_73872_(l1 + 1, k1) == 1 && !flag1) {
                        BlockPos blockpos2 = blockpos3.m_5484_(p_73783_.m_55954_(Direction.EAST), 8);
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, direction1 == Direction.EAST ? s3 : s2, blockpos2, p_73783_));
                     }

                     if (WoodlandMansionPieces.MansionGrid.m_73740_(woodlandmansionpieces$simplegrid3, l1, k1 + 1) && !p_73785_.m_73744_(woodlandmansionpieces$simplegrid3, l1, k1 + 1, l2, k2)) {
                        BlockPos blockpos4 = blockpos3.m_5484_(p_73783_.m_55954_(Direction.SOUTH), 7);
                        blockpos4 = blockpos4.m_5484_(p_73783_.m_55954_(Direction.EAST), 7);
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, direction1 == Direction.SOUTH ? s3 : s2, blockpos4, p_73783_.m_55952_(Rotation.CLOCKWISE_90)));
                     }

                     if (woodlandmansionpieces$simplegrid3.m_73872_(l1, k1 - 1) == 1 && !flag1) {
                        BlockPos blockpos5 = blockpos3.m_5484_(p_73783_.m_55954_(Direction.NORTH), 1);
                        blockpos5 = blockpos5.m_5484_(p_73783_.m_55954_(Direction.EAST), 7);
                        p_73784_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, direction1 == Direction.NORTH ? s3 : s2, blockpos5, p_73783_.m_55952_(Rotation.CLOCKWISE_90)));
                     }

                     if (j2 == 65536) {
                        this.m_73809_(p_73784_, blockpos3, p_73783_, direction1, awoodlandmansionpieces$floorroomcollection[l2]);
                     } else if (j2 == 131072 && direction1 != null) {
                        Direction direction3 = p_73785_.m_73762_(woodlandmansionpieces$simplegrid3, l1, k1, l2, k2);
                        boolean flag2 = (i2 & 4194304) == 4194304;
                        this.m_73822_(p_73784_, blockpos3, p_73783_, direction3, direction1, awoodlandmansionpieces$floorroomcollection[l2], flag2);
                     } else if (j2 == 262144 && direction1 != null && direction1 != Direction.UP) {
                        Direction direction2 = direction1.m_122427_();
                        if (!p_73785_.m_73744_(woodlandmansionpieces$simplegrid3, l1 + direction2.m_122429_(), k1 + direction2.m_122431_(), l2, k2)) {
                           direction2 = direction2.m_122424_();
                        }

                        this.m_73815_(p_73784_, blockpos3, p_73783_, direction2, direction1, awoodlandmansionpieces$floorroomcollection[l2]);
                     } else if (j2 == 262144 && direction1 == Direction.UP) {
                        this.m_73798_(p_73784_, blockpos3, p_73783_, awoodlandmansionpieces$floorroomcollection[l2]);
                     }
                  }
               }
            }
         }

      }

      private void m_73789_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73790_, WoodlandMansionPieces.PlacementData p_73791_, WoodlandMansionPieces.SimpleGrid p_73792_, Direction p_73793_, int p_73794_, int p_73795_, int p_73796_, int p_73797_) {
         int i = p_73794_;
         int j = p_73795_;
         Direction direction = p_73793_;

         do {
            if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73792_, i + p_73793_.m_122429_(), j + p_73793_.m_122431_())) {
               this.m_73833_(p_73790_, p_73791_);
               p_73793_ = p_73793_.m_122427_();
               if (i != p_73796_ || j != p_73797_ || direction != p_73793_) {
                  this.m_73830_(p_73790_, p_73791_);
               }
            } else if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73792_, i + p_73793_.m_122429_(), j + p_73793_.m_122431_()) && WoodlandMansionPieces.MansionGrid.m_73740_(p_73792_, i + p_73793_.m_122429_() + p_73793_.m_122428_().m_122429_(), j + p_73793_.m_122431_() + p_73793_.m_122428_().m_122431_())) {
               this.m_73836_(p_73790_, p_73791_);
               i += p_73793_.m_122429_();
               j += p_73793_.m_122431_();
               p_73793_ = p_73793_.m_122428_();
            } else {
               i += p_73793_.m_122429_();
               j += p_73793_.m_122431_();
               if (i != p_73796_ || j != p_73797_ || direction != p_73793_) {
                  this.m_73830_(p_73790_, p_73791_);
               }
            }
         } while(i != p_73796_ || j != p_73797_ || direction != p_73793_);

      }

      private void m_73803_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73804_, BlockPos p_73805_, Rotation p_73806_, WoodlandMansionPieces.SimpleGrid p_73807_, @Nullable WoodlandMansionPieces.SimpleGrid p_73808_) {
         for(int i = 0; i < p_73807_.f_73866_; ++i) {
            for(int j = 0; j < p_73807_.f_73865_; ++j) {
               BlockPos blockpos = p_73805_.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 8 + (i - this.f_73777_) * 8);
               blockpos = blockpos.m_5484_(p_73806_.m_55954_(Direction.EAST), (j - this.f_73776_) * 8);
               boolean flag = p_73808_ != null && WoodlandMansionPieces.MansionGrid.m_73740_(p_73808_, j, i);
               if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j, i) && !flag) {
                  p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof", blockpos.m_6630_(3), p_73806_));
                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j + 1, i)) {
                     BlockPos blockpos1 = blockpos.m_5484_(p_73806_.m_55954_(Direction.EAST), 6);
                     p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_front", blockpos1, p_73806_));
                  }

                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j - 1, i)) {
                     BlockPos blockpos5 = blockpos.m_5484_(p_73806_.m_55954_(Direction.EAST), 0);
                     blockpos5 = blockpos5.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 7);
                     p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_front", blockpos5, p_73806_.m_55952_(Rotation.CLOCKWISE_180)));
                  }

                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j, i - 1)) {
                     BlockPos blockpos6 = blockpos.m_5484_(p_73806_.m_55954_(Direction.WEST), 1);
                     p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_front", blockpos6, p_73806_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                  }

                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j, i + 1)) {
                     BlockPos blockpos7 = blockpos.m_5484_(p_73806_.m_55954_(Direction.EAST), 6);
                     blockpos7 = blockpos7.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 6);
                     p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_front", blockpos7, p_73806_.m_55952_(Rotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (p_73808_ != null) {
            for(int k = 0; k < p_73807_.f_73866_; ++k) {
               for(int i1 = 0; i1 < p_73807_.f_73865_; ++i1) {
                  BlockPos blockpos3 = p_73805_.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 8 + (k - this.f_73777_) * 8);
                  blockpos3 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.EAST), (i1 - this.f_73776_) * 8);
                  boolean flag1 = WoodlandMansionPieces.MansionGrid.m_73740_(p_73808_, i1, k);
                  if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k) && flag1) {
                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1 + 1, k)) {
                        BlockPos blockpos8 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.EAST), 7);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall", blockpos8, p_73806_));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1 - 1, k)) {
                        BlockPos blockpos9 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.WEST), 1);
                        blockpos9 = blockpos9.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 6);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall", blockpos9, p_73806_.m_55952_(Rotation.CLOCKWISE_180)));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k - 1)) {
                        BlockPos blockpos10 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.WEST), 0);
                        blockpos10 = blockpos10.m_5484_(p_73806_.m_55954_(Direction.NORTH), 1);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall", blockpos10, p_73806_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k + 1)) {
                        BlockPos blockpos11 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.EAST), 6);
                        blockpos11 = blockpos11.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 7);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall", blockpos11, p_73806_.m_55952_(Rotation.CLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1 + 1, k)) {
                        if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k - 1)) {
                           BlockPos blockpos12 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.EAST), 7);
                           blockpos12 = blockpos12.m_5484_(p_73806_.m_55954_(Direction.NORTH), 2);
                           p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall_corner", blockpos12, p_73806_));
                        }

                        if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k + 1)) {
                           BlockPos blockpos13 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.EAST), 8);
                           blockpos13 = blockpos13.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 7);
                           p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall_corner", blockpos13, p_73806_.m_55952_(Rotation.CLOCKWISE_90)));
                        }
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1 - 1, k)) {
                        if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k - 1)) {
                           BlockPos blockpos14 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.WEST), 2);
                           blockpos14 = blockpos14.m_5484_(p_73806_.m_55954_(Direction.NORTH), 1);
                           p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall_corner", blockpos14, p_73806_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, i1, k + 1)) {
                           BlockPos blockpos15 = blockpos3.m_5484_(p_73806_.m_55954_(Direction.WEST), 1);
                           blockpos15 = blockpos15.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 8);
                           p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "small_wall_corner", blockpos15, p_73806_.m_55952_(Rotation.CLOCKWISE_180)));
                        }
                     }
                  }
               }
            }
         }

         for(int l = 0; l < p_73807_.f_73866_; ++l) {
            for(int j1 = 0; j1 < p_73807_.f_73865_; ++j1) {
               BlockPos blockpos4 = p_73805_.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 8 + (l - this.f_73777_) * 8);
               blockpos4 = blockpos4.m_5484_(p_73806_.m_55954_(Direction.EAST), (j1 - this.f_73776_) * 8);
               boolean flag2 = p_73808_ != null && WoodlandMansionPieces.MansionGrid.m_73740_(p_73808_, j1, l);
               if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1, l) && !flag2) {
                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 + 1, l)) {
                     BlockPos blockpos16 = blockpos4.m_5484_(p_73806_.m_55954_(Direction.EAST), 6);
                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1, l + 1)) {
                        BlockPos blockpos2 = blockpos16.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 6);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_corner", blockpos2, p_73806_));
                     } else if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 + 1, l + 1)) {
                        BlockPos blockpos18 = blockpos16.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 5);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_inner_corner", blockpos18, p_73806_));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1, l - 1)) {
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_corner", blockpos16, p_73806_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                     } else if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 + 1, l - 1)) {
                        BlockPos blockpos19 = blockpos4.m_5484_(p_73806_.m_55954_(Direction.EAST), 9);
                        blockpos19 = blockpos19.m_5484_(p_73806_.m_55954_(Direction.NORTH), 2);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_inner_corner", blockpos19, p_73806_.m_55952_(Rotation.CLOCKWISE_90)));
                     }
                  }

                  if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 - 1, l)) {
                     BlockPos blockpos17 = blockpos4.m_5484_(p_73806_.m_55954_(Direction.EAST), 0);
                     blockpos17 = blockpos17.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 0);
                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1, l + 1)) {
                        BlockPos blockpos20 = blockpos17.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 6);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_corner", blockpos20, p_73806_.m_55952_(Rotation.CLOCKWISE_90)));
                     } else if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 - 1, l + 1)) {
                        BlockPos blockpos21 = blockpos17.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 8);
                        blockpos21 = blockpos21.m_5484_(p_73806_.m_55954_(Direction.WEST), 3);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_inner_corner", blockpos21, p_73806_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1, l - 1)) {
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_corner", blockpos17, p_73806_.m_55952_(Rotation.CLOCKWISE_180)));
                     } else if (WoodlandMansionPieces.MansionGrid.m_73740_(p_73807_, j1 - 1, l - 1)) {
                        BlockPos blockpos22 = blockpos17.m_5484_(p_73806_.m_55954_(Direction.SOUTH), 1);
                        p_73804_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "roof_inner_corner", blockpos22, p_73806_.m_55952_(Rotation.CLOCKWISE_180)));
                     }
                  }
               }
            }
         }

      }

      private void m_73786_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73787_, WoodlandMansionPieces.PlacementData p_73788_) {
         Direction direction = p_73788_.f_73839_.m_55954_(Direction.WEST);
         p_73787_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "entrance", p_73788_.f_73840_.m_5484_(direction, 9), p_73788_.f_73839_));
         p_73788_.f_73840_ = p_73788_.f_73840_.m_5484_(p_73788_.f_73839_.m_55954_(Direction.SOUTH), 16);
      }

      private void m_73830_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73831_, WoodlandMansionPieces.PlacementData p_73832_) {
         p_73831_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73832_.f_73841_, p_73832_.f_73840_.m_5484_(p_73832_.f_73839_.m_55954_(Direction.EAST), 7), p_73832_.f_73839_));
         p_73832_.f_73840_ = p_73832_.f_73840_.m_5484_(p_73832_.f_73839_.m_55954_(Direction.SOUTH), 8);
      }

      private void m_73833_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73834_, WoodlandMansionPieces.PlacementData p_73835_) {
         p_73835_.f_73840_ = p_73835_.f_73840_.m_5484_(p_73835_.f_73839_.m_55954_(Direction.SOUTH), -1);
         p_73834_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, "wall_corner", p_73835_.f_73840_, p_73835_.f_73839_));
         p_73835_.f_73840_ = p_73835_.f_73840_.m_5484_(p_73835_.f_73839_.m_55954_(Direction.SOUTH), -7);
         p_73835_.f_73840_ = p_73835_.f_73840_.m_5484_(p_73835_.f_73839_.m_55954_(Direction.WEST), -6);
         p_73835_.f_73839_ = p_73835_.f_73839_.m_55952_(Rotation.CLOCKWISE_90);
      }

      private void m_73836_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73837_, WoodlandMansionPieces.PlacementData p_73838_) {
         p_73838_.f_73840_ = p_73838_.f_73840_.m_5484_(p_73838_.f_73839_.m_55954_(Direction.SOUTH), 6);
         p_73838_.f_73840_ = p_73838_.f_73840_.m_5484_(p_73838_.f_73839_.m_55954_(Direction.EAST), 8);
         p_73838_.f_73839_ = p_73838_.f_73839_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
      }

      private void m_73809_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73810_, BlockPos p_73811_, Rotation p_73812_, Direction p_73813_, WoodlandMansionPieces.FloorRoomCollection p_73814_) {
         Rotation rotation = Rotation.NONE;
         String s = p_73814_.m_7668_(this.f_73775_);
         if (p_73813_ != Direction.EAST) {
            if (p_73813_ == Direction.NORTH) {
               rotation = rotation.m_55952_(Rotation.COUNTERCLOCKWISE_90);
            } else if (p_73813_ == Direction.WEST) {
               rotation = rotation.m_55952_(Rotation.CLOCKWISE_180);
            } else if (p_73813_ == Direction.SOUTH) {
               rotation = rotation.m_55952_(Rotation.CLOCKWISE_90);
            } else {
               s = p_73814_.m_7669_(this.f_73775_);
            }
         }

         BlockPos blockpos = StructureTemplate.m_74587_(new BlockPos(1, 0, 0), Mirror.NONE, rotation, 7, 7);
         rotation = rotation.m_55952_(p_73812_);
         blockpos = blockpos.m_7954_(p_73812_);
         BlockPos blockpos1 = p_73811_.m_142082_(blockpos.m_123341_(), 0, blockpos.m_123343_());
         p_73810_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, s, blockpos1, rotation));
      }

      private void m_73822_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73823_, BlockPos p_73824_, Rotation p_73825_, Direction p_73826_, Direction p_73827_, WoodlandMansionPieces.FloorRoomCollection p_73828_, boolean p_73829_) {
         if (p_73827_ == Direction.EAST && p_73826_ == Direction.SOUTH) {
            BlockPos blockpos13 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos13, p_73825_));
         } else if (p_73827_ == Direction.EAST && p_73826_ == Direction.NORTH) {
            BlockPos blockpos12 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            blockpos12 = blockpos12.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 6);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos12, p_73825_, Mirror.LEFT_RIGHT));
         } else if (p_73827_ == Direction.WEST && p_73826_ == Direction.NORTH) {
            BlockPos blockpos11 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 7);
            blockpos11 = blockpos11.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 6);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos11, p_73825_.m_55952_(Rotation.CLOCKWISE_180)));
         } else if (p_73827_ == Direction.WEST && p_73826_ == Direction.SOUTH) {
            BlockPos blockpos10 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 7);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos10, p_73825_, Mirror.FRONT_BACK));
         } else if (p_73827_ == Direction.SOUTH && p_73826_ == Direction.EAST) {
            BlockPos blockpos9 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos9, p_73825_.m_55952_(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
         } else if (p_73827_ == Direction.SOUTH && p_73826_ == Direction.WEST) {
            BlockPos blockpos8 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 7);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos8, p_73825_.m_55952_(Rotation.CLOCKWISE_90)));
         } else if (p_73827_ == Direction.NORTH && p_73826_ == Direction.WEST) {
            BlockPos blockpos7 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 7);
            blockpos7 = blockpos7.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 6);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos7, p_73825_.m_55952_(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
         } else if (p_73827_ == Direction.NORTH && p_73826_ == Direction.EAST) {
            BlockPos blockpos6 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            blockpos6 = blockpos6.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 6);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5900_(this.f_73775_, p_73829_), blockpos6, p_73825_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
         } else if (p_73827_ == Direction.SOUTH && p_73826_ == Direction.NORTH) {
            BlockPos blockpos5 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            blockpos5 = blockpos5.m_5484_(p_73825_.m_55954_(Direction.NORTH), 8);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5899_(this.f_73775_, p_73829_), blockpos5, p_73825_));
         } else if (p_73827_ == Direction.NORTH && p_73826_ == Direction.SOUTH) {
            BlockPos blockpos4 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 7);
            blockpos4 = blockpos4.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 14);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5899_(this.f_73775_, p_73829_), blockpos4, p_73825_.m_55952_(Rotation.CLOCKWISE_180)));
         } else if (p_73827_ == Direction.WEST && p_73826_ == Direction.EAST) {
            BlockPos blockpos3 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 15);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5899_(this.f_73775_, p_73829_), blockpos3, p_73825_.m_55952_(Rotation.CLOCKWISE_90)));
         } else if (p_73827_ == Direction.EAST && p_73826_ == Direction.WEST) {
            BlockPos blockpos2 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.WEST), 7);
            blockpos2 = blockpos2.m_5484_(p_73825_.m_55954_(Direction.SOUTH), 6);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_5899_(this.f_73775_, p_73829_), blockpos2, p_73825_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
         } else if (p_73827_ == Direction.UP && p_73826_ == Direction.EAST) {
            BlockPos blockpos1 = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 15);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_7672_(this.f_73775_), blockpos1, p_73825_.m_55952_(Rotation.CLOCKWISE_90)));
         } else if (p_73827_ == Direction.UP && p_73826_ == Direction.SOUTH) {
            BlockPos blockpos = p_73824_.m_5484_(p_73825_.m_55954_(Direction.EAST), 1);
            blockpos = blockpos.m_5484_(p_73825_.m_55954_(Direction.NORTH), 0);
            p_73823_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73828_.m_7672_(this.f_73775_), blockpos, p_73825_));
         }

      }

      private void m_73815_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73816_, BlockPos p_73817_, Rotation p_73818_, Direction p_73819_, Direction p_73820_, WoodlandMansionPieces.FloorRoomCollection p_73821_) {
         int i = 0;
         int j = 0;
         Rotation rotation = p_73818_;
         Mirror mirror = Mirror.NONE;
         if (p_73820_ == Direction.EAST && p_73819_ == Direction.SOUTH) {
            i = -7;
         } else if (p_73820_ == Direction.EAST && p_73819_ == Direction.NORTH) {
            i = -7;
            j = 6;
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_73820_ == Direction.NORTH && p_73819_ == Direction.EAST) {
            i = 1;
            j = 14;
            rotation = p_73818_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
         } else if (p_73820_ == Direction.NORTH && p_73819_ == Direction.WEST) {
            i = 7;
            j = 14;
            rotation = p_73818_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_73820_ == Direction.SOUTH && p_73819_ == Direction.WEST) {
            i = 7;
            j = -8;
            rotation = p_73818_.m_55952_(Rotation.CLOCKWISE_90);
         } else if (p_73820_ == Direction.SOUTH && p_73819_ == Direction.EAST) {
            i = 1;
            j = -8;
            rotation = p_73818_.m_55952_(Rotation.CLOCKWISE_90);
            mirror = Mirror.LEFT_RIGHT;
         } else if (p_73820_ == Direction.WEST && p_73819_ == Direction.NORTH) {
            i = 15;
            j = 6;
            rotation = p_73818_.m_55952_(Rotation.CLOCKWISE_180);
         } else if (p_73820_ == Direction.WEST && p_73819_ == Direction.SOUTH) {
            i = 15;
            mirror = Mirror.FRONT_BACK;
         }

         BlockPos blockpos = p_73817_.m_5484_(p_73818_.m_55954_(Direction.EAST), i);
         blockpos = blockpos.m_5484_(p_73818_.m_55954_(Direction.SOUTH), j);
         p_73816_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73821_.m_7661_(this.f_73775_), blockpos, rotation, mirror));
      }

      private void m_73798_(List<WoodlandMansionPieces.WoodlandMansionPiece> p_73799_, BlockPos p_73800_, Rotation p_73801_, WoodlandMansionPieces.FloorRoomCollection p_73802_) {
         BlockPos blockpos = p_73800_.m_5484_(p_73801_.m_55954_(Direction.EAST), 1);
         p_73799_.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.f_73774_, p_73802_.m_7676_(this.f_73775_), blockpos, p_73801_, Mirror.NONE));
      }
   }

   static class PlacementData {
      public Rotation f_73839_;
      public BlockPos f_73840_;
      public String f_73841_;
   }

   static class SecondFloorRoomCollection extends WoodlandMansionPieces.FloorRoomCollection {
      public String m_7668_(Random p_73849_) {
         return "1x1_b" + (p_73849_.nextInt(4) + 1);
      }

      public String m_7669_(Random p_73854_) {
         return "1x1_as" + (p_73854_.nextInt(4) + 1);
      }

      public String m_5900_(Random p_73851_, boolean p_73852_) {
         return p_73852_ ? "1x2_c_stairs" : "1x2_c" + (p_73851_.nextInt(4) + 1);
      }

      public String m_5899_(Random p_73856_, boolean p_73857_) {
         return p_73857_ ? "1x2_d_stairs" : "1x2_d" + (p_73856_.nextInt(5) + 1);
      }

      public String m_7672_(Random p_73859_) {
         return "1x2_se" + (p_73859_.nextInt(1) + 1);
      }

      public String m_7661_(Random p_73861_) {
         return "2x2_b" + (p_73861_.nextInt(5) + 1);
      }

      public String m_7676_(Random p_73863_) {
         return "2x2_s1";
      }
   }

   static class SimpleGrid {
      private final int[][] f_73864_;
      final int f_73865_;
      final int f_73866_;
      private final int f_73867_;

      public SimpleGrid(int p_73869_, int p_73870_, int p_73871_) {
         this.f_73865_ = p_73869_;
         this.f_73866_ = p_73870_;
         this.f_73867_ = p_73871_;
         this.f_73864_ = new int[p_73869_][p_73870_];
      }

      public void m_73875_(int p_73876_, int p_73877_, int p_73878_) {
         if (p_73876_ >= 0 && p_73876_ < this.f_73865_ && p_73877_ >= 0 && p_73877_ < this.f_73866_) {
            this.f_73864_[p_73876_][p_73877_] = p_73878_;
         }

      }

      public void m_73884_(int p_73885_, int p_73886_, int p_73887_, int p_73888_, int p_73889_) {
         for(int i = p_73886_; i <= p_73888_; ++i) {
            for(int j = p_73885_; j <= p_73887_; ++j) {
               this.m_73875_(j, i, p_73889_);
            }
         }

      }

      public int m_73872_(int p_73873_, int p_73874_) {
         return p_73873_ >= 0 && p_73873_ < this.f_73865_ && p_73874_ >= 0 && p_73874_ < this.f_73866_ ? this.f_73864_[p_73873_][p_73874_] : this.f_73867_;
      }

      public void m_73879_(int p_73880_, int p_73881_, int p_73882_, int p_73883_) {
         if (this.m_73872_(p_73880_, p_73881_) == p_73882_) {
            this.m_73875_(p_73880_, p_73881_, p_73883_);
         }

      }

      public boolean m_73892_(int p_73893_, int p_73894_, int p_73895_) {
         return this.m_73872_(p_73893_ - 1, p_73894_) == p_73895_ || this.m_73872_(p_73893_ + 1, p_73894_) == p_73895_ || this.m_73872_(p_73893_, p_73894_ + 1) == p_73895_ || this.m_73872_(p_73893_, p_73894_ - 1) == p_73895_;
      }
   }

   static class ThirdFloorRoomCollection extends WoodlandMansionPieces.SecondFloorRoomCollection {
   }

   public static class WoodlandMansionPiece extends TemplateStructurePiece {
      public WoodlandMansionPiece(StructureManager p_73905_, String p_73906_, BlockPos p_73907_, Rotation p_73908_) {
         this(p_73905_, p_73906_, p_73907_, p_73908_, Mirror.NONE);
      }

      public WoodlandMansionPiece(StructureManager p_73910_, String p_73911_, BlockPos p_73912_, Rotation p_73913_, Mirror p_73914_) {
         super(StructurePieceType.f_67131_, 0, p_73910_, m_163705_(p_73911_), p_73911_, m_163702_(p_73914_, p_73913_), p_73912_);
      }

      public WoodlandMansionPiece(ServerLevel p_163696_, CompoundTag p_163697_) {
         super(StructurePieceType.f_67131_, p_163697_, p_163696_, (p_163709_) -> {
            return m_163702_(Mirror.valueOf(p_163697_.m_128461_("Mi")), Rotation.valueOf(p_163697_.m_128461_("Rot")));
         });
      }

      protected ResourceLocation m_142415_() {
         return m_163705_(this.f_163658_);
      }

      private static ResourceLocation m_163705_(String p_163706_) {
         return new ResourceLocation("woodland_mansion/" + p_163706_);
      }

      private static StructurePlaceSettings m_163702_(Mirror p_163703_, Rotation p_163704_) {
         return (new StructurePlaceSettings()).m_74392_(true).m_74379_(p_163704_).m_74377_(p_163703_).m_74383_(BlockIgnoreProcessor.f_74046_);
      }

      protected void m_142347_(ServerLevel p_163700_, CompoundTag p_163701_) {
         super.m_142347_(p_163700_, p_163701_);
         p_163701_.m_128359_("Rot", this.f_73657_.m_74404_().name());
         p_163701_.m_128359_("Mi", this.f_73657_.m_74401_().name());
      }

      protected void m_7756_(String p_73921_, BlockPos p_73922_, ServerLevelAccessor p_73923_, Random p_73924_, BoundingBox p_73925_) {
         if (p_73921_.startsWith("Chest")) {
            Rotation rotation = this.f_73657_.m_74404_();
            BlockState blockstate = Blocks.f_50087_.m_49966_();
            if ("ChestWest".equals(p_73921_)) {
               blockstate = blockstate.m_61124_(ChestBlock.f_51478_, rotation.m_55954_(Direction.WEST));
            } else if ("ChestEast".equals(p_73921_)) {
               blockstate = blockstate.m_61124_(ChestBlock.f_51478_, rotation.m_55954_(Direction.EAST));
            } else if ("ChestSouth".equals(p_73921_)) {
               blockstate = blockstate.m_61124_(ChestBlock.f_51478_, rotation.m_55954_(Direction.SOUTH));
            } else if ("ChestNorth".equals(p_73921_)) {
               blockstate = blockstate.m_61124_(ChestBlock.f_51478_, rotation.m_55954_(Direction.NORTH));
            }

            this.m_73420_(p_73923_, p_73925_, p_73924_, p_73922_, BuiltInLootTables.f_78689_, blockstate);
         } else {
            AbstractIllager abstractillager;
            switch(p_73921_) {
            case "Mage":
               abstractillager = EntityType.f_20568_.m_20615_(p_73923_.m_6018_());
               break;
            case "Warrior":
               abstractillager = EntityType.f_20493_.m_20615_(p_73923_.m_6018_());
               break;
            default:
               return;
            }

            abstractillager.m_21530_();
            abstractillager.m_20035_(p_73922_, 0.0F, 0.0F);
            abstractillager.m_6518_(p_73923_, p_73923_.m_6436_(abstractillager.m_142538_()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
            p_73923_.m_47205_(abstractillager);
            p_73923_.m_7731_(p_73922_, Blocks.f_50016_.m_49966_(), 2);
         }

      }
   }
}