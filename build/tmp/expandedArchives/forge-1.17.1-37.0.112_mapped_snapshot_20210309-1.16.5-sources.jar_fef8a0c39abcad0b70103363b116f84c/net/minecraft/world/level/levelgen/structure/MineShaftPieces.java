package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.MineshaftFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MineShaftPieces {
   static final Logger f_162462_ = LogManager.getLogger();
   private static final int f_162463_ = 3;
   private static final int f_162464_ = 3;
   private static final int f_162465_ = 5;
   private static final int f_162466_ = 20;
   private static final int f_162467_ = 50;
   private static final int f_162468_ = 8;

   private static MineShaftPieces.MineShaftPiece m_162480_(StructurePieceAccessor p_162481_, Random p_162482_, int p_162483_, int p_162484_, int p_162485_, @Nullable Direction p_162486_, int p_162487_, MineshaftFeature.Type p_162488_) {
      int i = p_162482_.nextInt(100);
      if (i >= 80) {
         BoundingBox boundingbox = MineShaftPieces.MineShaftCrossing.m_162563_(p_162481_, p_162482_, p_162483_, p_162484_, p_162485_, p_162486_);
         if (boundingbox != null) {
            return new MineShaftPieces.MineShaftCrossing(p_162487_, boundingbox, p_162486_, p_162488_);
         }
      } else if (i >= 70) {
         BoundingBox boundingbox1 = MineShaftPieces.MineShaftStairs.m_162614_(p_162481_, p_162482_, p_162483_, p_162484_, p_162485_, p_162486_);
         if (boundingbox1 != null) {
            return new MineShaftPieces.MineShaftStairs(p_162487_, boundingbox1, p_162486_, p_162488_);
         }
      } else {
         BoundingBox boundingbox2 = MineShaftPieces.MineShaftCorridor.m_162537_(p_162481_, p_162482_, p_162483_, p_162484_, p_162485_, p_162486_);
         if (boundingbox2 != null) {
            return new MineShaftPieces.MineShaftCorridor(p_162487_, p_162482_, boundingbox2, p_162486_, p_162488_);
         }
      }

      return null;
   }

   static MineShaftPieces.MineShaftPiece m_162471_(StructurePiece p_162472_, StructurePieceAccessor p_162473_, Random p_162474_, int p_162475_, int p_162476_, int p_162477_, Direction p_162478_, int p_162479_) {
      if (p_162479_ > 8) {
         return null;
      } else if (Math.abs(p_162475_ - p_162472_.m_73547_().m_162395_()) <= 80 && Math.abs(p_162477_ - p_162472_.m_73547_().m_162398_()) <= 80) {
         MineshaftFeature.Type mineshaftfeature$type = ((MineShaftPieces.MineShaftPiece)p_162472_).f_71465_;
         MineShaftPieces.MineShaftPiece mineshaftpieces$mineshaftpiece = m_162480_(p_162473_, p_162474_, p_162475_, p_162476_, p_162477_, p_162478_, p_162479_ + 1, mineshaftfeature$type);
         if (mineshaftpieces$mineshaftpiece != null) {
            p_162473_.m_142679_(mineshaftpieces$mineshaftpiece);
            mineshaftpieces$mineshaftpiece.m_142537_(p_162472_, p_162473_, p_162474_);
         }

         return mineshaftpieces$mineshaftpiece;
      } else {
         return null;
      }
   }

   public static class MineShaftCorridor extends MineShaftPieces.MineShaftPiece {
      private final boolean f_71368_;
      private final boolean f_71369_;
      private boolean f_71370_;
      private final int f_71371_;

      public MineShaftCorridor(ServerLevel p_162490_, CompoundTag p_162491_) {
         super(StructurePieceType.f_67132_, p_162491_);
         this.f_71368_ = p_162491_.m_128471_("hr");
         this.f_71369_ = p_162491_.m_128471_("sc");
         this.f_71370_ = p_162491_.m_128471_("hps");
         this.f_71371_ = p_162491_.m_128451_("Num");
      }

      protected void m_142347_(ServerLevel p_162493_, CompoundTag p_162494_) {
         super.m_142347_(p_162493_, p_162494_);
         p_162494_.m_128379_("hr", this.f_71368_);
         p_162494_.m_128379_("sc", this.f_71369_);
         p_162494_.m_128379_("hps", this.f_71370_);
         p_162494_.m_128405_("Num", this.f_71371_);
      }

      public MineShaftCorridor(int p_71373_, Random p_71374_, BoundingBox p_71375_, Direction p_71376_, MineshaftFeature.Type p_71377_) {
         super(StructurePieceType.f_67132_, p_71373_, p_71377_, p_71375_);
         this.m_73519_(p_71376_);
         this.f_71368_ = p_71374_.nextInt(3) == 0;
         this.f_71369_ = !this.f_71368_ && p_71374_.nextInt(23) == 0;
         if (this.m_73549_().m_122434_() == Direction.Axis.Z) {
            this.f_71371_ = p_71375_.m_71058_() / 5;
         } else {
            this.f_71371_ = p_71375_.m_71056_() / 5;
         }

      }

      @Nullable
      public static BoundingBox m_162537_(StructurePieceAccessor p_162538_, Random p_162539_, int p_162540_, int p_162541_, int p_162542_, Direction p_162543_) {
         for(int i = p_162539_.nextInt(3) + 2; i > 0; --i) {
            int j = i * 5;
            BoundingBox boundingbox;
            switch(p_162543_) {
            case NORTH:
            default:
               boundingbox = new BoundingBox(0, 0, -(j - 1), 2, 2, 0);
               break;
            case SOUTH:
               boundingbox = new BoundingBox(0, 0, 0, 2, 2, j - 1);
               break;
            case WEST:
               boundingbox = new BoundingBox(-(j - 1), 0, 0, 0, 2, 2);
               break;
            case EAST:
               boundingbox = new BoundingBox(0, 0, 0, j - 1, 2, 2);
            }

            boundingbox.m_162367_(p_162540_, p_162541_, p_162542_);
            if (p_162538_.m_141921_(boundingbox) == null) {
               return boundingbox;
            }
         }

         return null;
      }

      public void m_142537_(StructurePiece p_162534_, StructurePieceAccessor p_162535_, Random p_162536_) {
         int i = this.m_73548_();
         int j = p_162536_.nextInt(4);
         Direction direction = this.m_73549_();
         if (direction != null) {
            switch(direction) {
            case NORTH:
            default:
               if (j <= 1) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_() - 1, direction, i);
               } else if (j == 2) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_(), Direction.WEST, i);
               } else {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_(), Direction.EAST, i);
               }
               break;
            case SOUTH:
               if (j <= 1) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162401_() + 1, direction, i);
               } else if (j == 2) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162401_() - 3, Direction.WEST, i);
               } else {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162401_() - 3, Direction.EAST, i);
               }
               break;
            case WEST:
               if (j <= 1) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_(), direction, i);
               } else if (j == 2) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
               } else {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
               }
               break;
            case EAST:
               if (j <= 1) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_(), direction, i);
               } else if (j == 2) {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() - 3, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
               } else {
                  MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() - 3, this.f_73383_.m_162396_() - 1 + p_162536_.nextInt(3), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
               }
            }
         }

         if (i < 8) {
            if (direction != Direction.NORTH && direction != Direction.SOUTH) {
               for(int i1 = this.f_73383_.m_162395_() + 3; i1 + 3 <= this.f_73383_.m_162399_(); i1 += 5) {
                  int j1 = p_162536_.nextInt(5);
                  if (j1 == 0) {
                     MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, i1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, i + 1);
                  } else if (j1 == 1) {
                     MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, i1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i + 1);
                  }
               }
            } else {
               for(int k = this.f_73383_.m_162398_() + 3; k + 3 <= this.f_73383_.m_162401_(); k += 5) {
                  int l = p_162536_.nextInt(5);
                  if (l == 0) {
                     MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), k, Direction.WEST, i + 1);
                  } else if (l == 1) {
                     MineShaftPieces.m_162471_(p_162534_, p_162535_, p_162536_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), k, Direction.EAST, i + 1);
                  }
               }
            }
         }

      }

      protected boolean m_5606_(WorldGenLevel p_71407_, BoundingBox p_71408_, Random p_71409_, int p_71410_, int p_71411_, int p_71412_, ResourceLocation p_71413_) {
         BlockPos blockpos = this.m_163582_(p_71410_, p_71411_, p_71412_);
         if (p_71408_.m_71051_(blockpos) && p_71407_.m_8055_(blockpos).m_60795_() && !p_71407_.m_8055_(blockpos.m_7495_()).m_60795_()) {
            BlockState blockstate = Blocks.f_50156_.m_49966_().m_61124_(RailBlock.f_55392_, p_71409_.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
            this.m_73434_(p_71407_, blockstate, p_71410_, p_71411_, p_71412_, p_71408_);
            MinecartChest minecartchest = new MinecartChest(p_71407_.m_6018_(), (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.5D, (double)blockpos.m_123343_() + 0.5D);
            minecartchest.m_38236_(p_71413_, p_71409_.nextLong());
            p_71407_.m_7967_(minecartchest);
            return true;
         } else {
            return false;
         }
      }

      public boolean m_7832_(WorldGenLevel p_71382_, StructureFeatureManager p_71383_, ChunkGenerator p_71384_, Random p_71385_, BoundingBox p_71386_, ChunkPos p_71387_, BlockPos p_71388_) {
         if (this.m_162578_(p_71382_, p_71386_)) {
            return false;
         } else {
            int i = 0;
            int j = 2;
            int k = 0;
            int l = 2;
            int i1 = this.f_71371_ * 5 - 1;
            BlockState blockstate = this.f_71465_.m_160062_();
            this.m_73441_(p_71382_, p_71386_, 0, 0, 0, 2, 1, i1, f_73382_, f_73382_, false);
            this.m_73476_(p_71382_, p_71386_, p_71385_, 0.8F, 0, 2, 0, 2, 2, i1, f_73382_, f_73382_, false, false);
            if (this.f_71369_) {
               this.m_73476_(p_71382_, p_71386_, p_71385_, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.f_50033_.m_49966_(), f_73382_, false, true);
            }

            for(int j1 = 0; j1 < this.f_71371_; ++j1) {
               int k1 = 2 + j1 * 5;
               this.m_71389_(p_71382_, p_71386_, 0, 0, k1, 2, 2, p_71385_);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.1F, 0, 2, k1 - 1);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.1F, 2, 2, k1 - 1);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.1F, 0, 2, k1 + 1);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.1F, 2, 2, k1 + 1);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.05F, 0, 2, k1 - 2);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.05F, 2, 2, k1 - 2);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.05F, 0, 2, k1 + 2);
               this.m_162525_(p_71382_, p_71386_, p_71385_, 0.05F, 2, 2, k1 + 2);
               if (p_71385_.nextInt(100) == 0) {
                  this.m_5606_(p_71382_, p_71386_, p_71385_, 2, 0, k1 - 1, BuiltInLootTables.f_78759_);
               }

               if (p_71385_.nextInt(100) == 0) {
                  this.m_5606_(p_71382_, p_71386_, p_71385_, 0, 0, k1 + 1, BuiltInLootTables.f_78759_);
               }

               if (this.f_71369_ && !this.f_71370_) {
                  int l1 = 1;
                  int i2 = k1 - 1 + p_71385_.nextInt(3);
                  BlockPos blockpos = this.m_163582_(1, 0, i2);
                  if (p_71386_.m_71051_(blockpos) && this.m_73414_(p_71382_, 1, 0, i2, p_71386_)) {
                     this.f_71370_ = true;
                     p_71382_.m_7731_(blockpos, Blocks.f_50085_.m_49966_(), 2);
                     BlockEntity blockentity = p_71382_.m_7702_(blockpos);
                     if (blockentity instanceof SpawnerBlockEntity) {
                        ((SpawnerBlockEntity)blockentity).m_59801_().m_45462_(EntityType.f_20554_);
                     }
                  }
               }
            }

            for(int j2 = 0; j2 <= 2; ++j2) {
               for(int l2 = 0; l2 <= i1; ++l2) {
                  this.m_162587_(p_71382_, p_71386_, blockstate, j2, -1, l2);
               }
            }

            int k2 = 2;
            this.m_162512_(p_71382_, p_71386_, 0, -1, 2);
            if (this.f_71371_ > 1) {
               int i3 = i1 - 2;
               this.m_162512_(p_71382_, p_71386_, 0, -1, i3);
            }

            if (this.f_71368_) {
               BlockState blockstate1 = Blocks.f_50156_.m_49966_().m_61124_(RailBlock.f_55392_, RailShape.NORTH_SOUTH);

               for(int j3 = 0; j3 <= i1; ++j3) {
                  BlockState blockstate2 = this.m_73398_(p_71382_, 1, -1, j3, p_71386_);
                  if (!blockstate2.m_60795_() && blockstate2.m_60804_(p_71382_, this.m_163582_(1, -1, j3))) {
                     float f = this.m_73414_(p_71382_, 1, 0, j3, p_71386_) ? 0.7F : 0.9F;
                     this.m_73491_(p_71382_, p_71386_, p_71385_, f, 1, 0, j3, blockstate1);
                  }
               }
            }

            return true;
         }
      }

      private void m_162512_(WorldGenLevel p_162513_, BoundingBox p_162514_, int p_162515_, int p_162516_, int p_162517_) {
         BlockState blockstate = this.f_71465_.m_160061_();
         BlockState blockstate1 = this.f_71465_.m_160062_();
         if (this.m_73398_(p_162513_, p_162515_, p_162516_, p_162517_, p_162514_).m_60713_(blockstate1.m_60734_())) {
            this.m_162544_(p_162513_, blockstate, p_162515_, p_162516_, p_162517_, p_162514_);
         }

         if (this.m_73398_(p_162513_, p_162515_ + 2, p_162516_, p_162517_, p_162514_).m_60713_(blockstate1.m_60734_())) {
            this.m_162544_(p_162513_, blockstate, p_162515_ + 2, p_162516_, p_162517_, p_162514_);
         }

      }

      protected void m_73528_(WorldGenLevel p_162500_, BlockState p_162501_, int p_162502_, int p_162503_, int p_162504_, BoundingBox p_162505_) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = this.m_163582_(p_162502_, p_162503_, p_162504_);
         if (p_162505_.m_71051_(blockpos$mutableblockpos)) {
            int i = blockpos$mutableblockpos.m_123342_();

            while(this.m_163572_(p_162500_.m_8055_(blockpos$mutableblockpos)) && blockpos$mutableblockpos.m_123342_() > p_162500_.m_141937_() + 1) {
               blockpos$mutableblockpos.m_122173_(Direction.DOWN);
            }

            if (this.m_162551_(p_162500_.m_8055_(blockpos$mutableblockpos))) {
               while(blockpos$mutableblockpos.m_123342_() < i) {
                  blockpos$mutableblockpos.m_122173_(Direction.UP);
                  p_162500_.m_7731_(blockpos$mutableblockpos, p_162501_, 2);
               }

            }
         }
      }

      protected void m_162544_(WorldGenLevel p_162545_, BlockState p_162546_, int p_162547_, int p_162548_, int p_162549_, BoundingBox p_162550_) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = this.m_163582_(p_162547_, p_162548_, p_162549_);
         if (p_162550_.m_71051_(blockpos$mutableblockpos)) {
            int i = blockpos$mutableblockpos.m_123342_();
            int j = 1;
            boolean flag = true;

            for(boolean flag1 = true; flag || flag1; ++j) {
               if (flag) {
                  blockpos$mutableblockpos.m_142448_(i - j);
                  BlockState blockstate = p_162545_.m_8055_(blockpos$mutableblockpos);
                  boolean flag2 = this.m_163572_(blockstate) && !blockstate.m_60713_(Blocks.f_49991_);
                  if (!flag2 && this.m_162551_(blockstate)) {
                     m_162506_(p_162545_, p_162546_, blockpos$mutableblockpos, i - j + 1, i);
                     return;
                  }

                  flag = j <= 20 && flag2 && blockpos$mutableblockpos.m_123342_() > p_162545_.m_141937_() + 1;
               }

               if (flag1) {
                  blockpos$mutableblockpos.m_142448_(i + j);
                  BlockState blockstate1 = p_162545_.m_8055_(blockpos$mutableblockpos);
                  boolean flag3 = this.m_163572_(blockstate1);
                  if (!flag3 && this.m_162495_(p_162545_, blockpos$mutableblockpos, blockstate1)) {
                     p_162545_.m_7731_(blockpos$mutableblockpos.m_142448_(i + 1), this.f_71465_.m_160063_(), 2);
                     m_162506_(p_162545_, Blocks.f_50184_.m_49966_(), blockpos$mutableblockpos, i + 2, i + j);
                     return;
                  }

                  flag1 = j <= 50 && flag3 && blockpos$mutableblockpos.m_123342_() < p_162545_.m_151558_() - 1;
               }
            }

         }
      }

      private static void m_162506_(WorldGenLevel p_162507_, BlockState p_162508_, BlockPos.MutableBlockPos p_162509_, int p_162510_, int p_162511_) {
         for(int i = p_162510_; i < p_162511_; ++i) {
            p_162507_.m_7731_(p_162509_.m_142448_(i), p_162508_, 2);
         }

      }

      private boolean m_162551_(BlockState p_162552_) {
         return !p_162552_.m_60713_(Blocks.f_50156_) && !p_162552_.m_60713_(Blocks.f_49991_);
      }

      private boolean m_162495_(LevelReader p_162496_, BlockPos p_162497_, BlockState p_162498_) {
         return Block.m_49863_(p_162496_, p_162497_, Direction.DOWN) && !(p_162498_.m_60734_() instanceof FallingBlock);
      }

      private void m_71389_(WorldGenLevel p_71390_, BoundingBox p_71391_, int p_71392_, int p_71393_, int p_71394_, int p_71395_, int p_71396_, Random p_71397_) {
         if (this.m_71474_(p_71390_, p_71391_, p_71392_, p_71396_, p_71395_, p_71394_)) {
            BlockState blockstate = this.f_71465_.m_160062_();
            BlockState blockstate1 = this.f_71465_.m_160063_();
            this.m_73441_(p_71390_, p_71391_, p_71392_, p_71393_, p_71394_, p_71392_, p_71395_ - 1, p_71394_, blockstate1.m_61124_(FenceBlock.f_52312_, Boolean.valueOf(true)), f_73382_, false);
            this.m_73441_(p_71390_, p_71391_, p_71396_, p_71393_, p_71394_, p_71396_, p_71395_ - 1, p_71394_, blockstate1.m_61124_(FenceBlock.f_52310_, Boolean.valueOf(true)), f_73382_, false);
            if (p_71397_.nextInt(4) == 0) {
               this.m_73441_(p_71390_, p_71391_, p_71392_, p_71395_, p_71394_, p_71392_, p_71395_, p_71394_, blockstate, f_73382_, false);
               this.m_73441_(p_71390_, p_71391_, p_71396_, p_71395_, p_71394_, p_71396_, p_71395_, p_71394_, blockstate, f_73382_, false);
            } else {
               this.m_73441_(p_71390_, p_71391_, p_71392_, p_71395_, p_71394_, p_71396_, p_71395_, p_71394_, blockstate, f_73382_, false);
               this.m_73491_(p_71390_, p_71391_, p_71397_, 0.05F, p_71392_ + 1, p_71395_, p_71394_ - 1, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.NORTH));
               this.m_73491_(p_71390_, p_71391_, p_71397_, 0.05F, p_71392_ + 1, p_71395_, p_71394_ + 1, Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH));
            }

         }
      }

      private void m_162525_(WorldGenLevel p_162526_, BoundingBox p_162527_, Random p_162528_, float p_162529_, int p_162530_, int p_162531_, int p_162532_) {
         if (this.m_73414_(p_162526_, p_162530_, p_162531_, p_162532_, p_162527_) && p_162528_.nextFloat() < p_162529_ && this.m_162518_(p_162526_, p_162527_, p_162530_, p_162531_, p_162532_, 2)) {
            this.m_73434_(p_162526_, Blocks.f_50033_.m_49966_(), p_162530_, p_162531_, p_162532_, p_162527_);
         }

      }

      private boolean m_162518_(WorldGenLevel p_162519_, BoundingBox p_162520_, int p_162521_, int p_162522_, int p_162523_, int p_162524_) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = this.m_163582_(p_162521_, p_162522_, p_162523_);
         int i = 0;

         for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.m_122173_(direction);
            if (p_162520_.m_71051_(blockpos$mutableblockpos) && p_162519_.m_8055_(blockpos$mutableblockpos).m_60783_(p_162519_, blockpos$mutableblockpos, direction.m_122424_())) {
               ++i;
               if (i >= p_162524_) {
                  return true;
               }
            }

            blockpos$mutableblockpos.m_122173_(direction.m_122424_());
         }

         return false;
      }
   }

   public static class MineShaftCrossing extends MineShaftPieces.MineShaftPiece {
      private final Direction f_71427_;
      private final boolean f_71428_;

      public MineShaftCrossing(ServerLevel p_162554_, CompoundTag p_162555_) {
         super(StructurePieceType.f_67137_, p_162555_);
         this.f_71428_ = p_162555_.m_128471_("tf");
         this.f_71427_ = Direction.m_122407_(p_162555_.m_128451_("D"));
      }

      protected void m_142347_(ServerLevel p_162557_, CompoundTag p_162558_) {
         super.m_142347_(p_162557_, p_162558_);
         p_162558_.m_128379_("tf", this.f_71428_);
         p_162558_.m_128405_("D", this.f_71427_.m_122416_());
      }

      public MineShaftCrossing(int p_71430_, BoundingBox p_71431_, @Nullable Direction p_71432_, MineshaftFeature.Type p_71433_) {
         super(StructurePieceType.f_67137_, p_71430_, p_71433_, p_71431_);
         this.f_71427_ = p_71432_;
         this.f_71428_ = p_71431_.m_71057_() > 3;
      }

      @Nullable
      public static BoundingBox m_162563_(StructurePieceAccessor p_162564_, Random p_162565_, int p_162566_, int p_162567_, int p_162568_, Direction p_162569_) {
         int i;
         if (p_162565_.nextInt(4) == 0) {
            i = 6;
         } else {
            i = 2;
         }

         BoundingBox boundingbox;
         switch(p_162569_) {
         case NORTH:
         default:
            boundingbox = new BoundingBox(-1, 0, -4, 3, i, 0);
            break;
         case SOUTH:
            boundingbox = new BoundingBox(-1, 0, 0, 3, i, 4);
            break;
         case WEST:
            boundingbox = new BoundingBox(-4, 0, -1, 0, i, 3);
            break;
         case EAST:
            boundingbox = new BoundingBox(0, 0, -1, 4, i, 3);
         }

         boundingbox.m_162367_(p_162566_, p_162567_, p_162568_);
         return p_162564_.m_141921_(boundingbox) != null ? null : boundingbox;
      }

      public void m_142537_(StructurePiece p_162560_, StructurePieceAccessor p_162561_, Random p_162562_) {
         int i = this.m_73548_();
         switch(this.f_71427_) {
         case NORTH:
         default:
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, i);
            break;
         case SOUTH:
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, i);
            break;
         case WEST:
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, i);
            break;
         case EAST:
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
            MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, i);
         }

         if (this.f_71428_) {
            if (p_162562_.nextBoolean()) {
               MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
            }

            if (p_162562_.nextBoolean()) {
               MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() + 1, Direction.WEST, i);
            }

            if (p_162562_.nextBoolean()) {
               MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() + 1, Direction.EAST, i);
            }

            if (p_162562_.nextBoolean()) {
               MineShaftPieces.m_162471_(p_162560_, p_162561_, p_162562_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
            }
         }

      }

      public boolean m_7832_(WorldGenLevel p_71438_, StructureFeatureManager p_71439_, ChunkGenerator p_71440_, Random p_71441_, BoundingBox p_71442_, ChunkPos p_71443_, BlockPos p_71444_) {
         if (this.m_162578_(p_71438_, p_71442_)) {
            return false;
         } else {
            BlockState blockstate = this.f_71465_.m_160062_();
            if (this.f_71428_) {
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_() + 3 - 1, this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162396_() + 3 - 1, this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162400_() - 2, this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_(), this.f_73383_.m_162400_() - 2, this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3, this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_() + 3, this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
            } else {
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
               this.m_73441_(p_71438_, p_71442_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
            }

            this.m_71445_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162400_());
            this.m_71445_(p_71438_, p_71442_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() - 1, this.f_73383_.m_162400_());
            this.m_71445_(p_71438_, p_71442_, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162400_());
            this.m_71445_(p_71438_, p_71442_, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() - 1, this.f_73383_.m_162400_());
            int i = this.f_73383_.m_162396_() - 1;

            for(int j = this.f_73383_.m_162395_(); j <= this.f_73383_.m_162399_(); ++j) {
               for(int k = this.f_73383_.m_162398_(); k <= this.f_73383_.m_162401_(); ++k) {
                  this.m_162587_(p_71438_, p_71442_, blockstate, j, i, k);
               }
            }

            return true;
         }
      }

      private void m_71445_(WorldGenLevel p_71446_, BoundingBox p_71447_, int p_71448_, int p_71449_, int p_71450_, int p_71451_) {
         if (!this.m_73398_(p_71446_, p_71448_, p_71451_ + 1, p_71450_, p_71447_).m_60795_()) {
            this.m_73441_(p_71446_, p_71447_, p_71448_, p_71449_, p_71450_, p_71448_, p_71451_, p_71450_, this.f_71465_.m_160062_(), f_73382_, false);
         }

      }
   }

   abstract static class MineShaftPiece extends StructurePiece {
      protected MineshaftFeature.Type f_71465_;

      public MineShaftPiece(StructurePieceType p_162571_, int p_162572_, MineshaftFeature.Type p_162573_, BoundingBox p_162574_) {
         super(p_162571_, p_162572_, p_162574_);
         this.f_71465_ = p_162573_;
      }

      public MineShaftPiece(StructurePieceType p_71471_, CompoundTag p_71472_) {
         super(p_71471_, p_71472_);
         this.f_71465_ = MineshaftFeature.Type.m_66330_(p_71472_.m_128451_("MST"));
      }

      protected boolean m_142085_(LevelReader p_162582_, int p_162583_, int p_162584_, int p_162585_, BoundingBox p_162586_) {
         BlockState blockstate = this.m_73398_(p_162582_, p_162583_, p_162584_, p_162585_, p_162586_);
         return !blockstate.m_60713_(this.f_71465_.m_160062_().m_60734_()) && !blockstate.m_60713_(this.f_71465_.m_160061_().m_60734_()) && !blockstate.m_60713_(this.f_71465_.m_160063_().m_60734_()) && !blockstate.m_60713_(Blocks.f_50184_);
      }

      protected void m_142347_(ServerLevel p_162576_, CompoundTag p_162577_) {
         p_162577_.m_128405_("MST", this.f_71465_.ordinal());
      }

      protected boolean m_71474_(BlockGetter p_71475_, BoundingBox p_71476_, int p_71477_, int p_71478_, int p_71479_, int p_71480_) {
         for(int i = p_71477_; i <= p_71478_; ++i) {
            if (this.m_73398_(p_71475_, i, p_71479_ + 1, p_71480_, p_71476_).m_60795_()) {
               return false;
            }
         }

         return true;
      }

      protected boolean m_162578_(BlockGetter p_162579_, BoundingBox p_162580_) {
         int i = Math.max(this.f_73383_.m_162395_() - 1, p_162580_.m_162395_());
         int j = Math.max(this.f_73383_.m_162396_() - 1, p_162580_.m_162396_());
         int k = Math.max(this.f_73383_.m_162398_() - 1, p_162580_.m_162398_());
         int l = Math.min(this.f_73383_.m_162399_() + 1, p_162580_.m_162399_());
         int i1 = Math.min(this.f_73383_.m_162400_() + 1, p_162580_.m_162400_());
         int j1 = Math.min(this.f_73383_.m_162401_() + 1, p_162580_.m_162401_());
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k1 = i; k1 <= l; ++k1) {
            for(int l1 = k; l1 <= j1; ++l1) {
               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(k1, j, l1)).m_60767_().m_76332_()) {
                  return true;
               }

               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(k1, i1, l1)).m_60767_().m_76332_()) {
                  return true;
               }
            }
         }

         for(int i2 = i; i2 <= l; ++i2) {
            for(int k2 = j; k2 <= i1; ++k2) {
               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(i2, k2, k)).m_60767_().m_76332_()) {
                  return true;
               }

               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(i2, k2, j1)).m_60767_().m_76332_()) {
                  return true;
               }
            }
         }

         for(int j2 = k; j2 <= j1; ++j2) {
            for(int l2 = j; l2 <= i1; ++l2) {
               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(i, l2, j2)).m_60767_().m_76332_()) {
                  return true;
               }

               if (p_162579_.m_8055_(blockpos$mutableblockpos.m_122178_(l, l2, j2)).m_60767_().m_76332_()) {
                  return true;
               }
            }
         }

         return false;
      }

      protected void m_162587_(WorldGenLevel p_162588_, BoundingBox p_162589_, BlockState p_162590_, int p_162591_, int p_162592_, int p_162593_) {
         if (this.m_73414_(p_162588_, p_162591_, p_162592_, p_162593_, p_162589_)) {
            BlockPos blockpos = this.m_163582_(p_162591_, p_162592_, p_162593_);
            BlockState blockstate = p_162588_.m_8055_(blockpos);
            if (blockstate.m_60795_() || blockstate.m_60713_(Blocks.f_50184_)) {
               p_162588_.m_7731_(blockpos, p_162590_, 2);
            }

         }
      }
   }

   public static class MineShaftRoom extends MineShaftPieces.MineShaftPiece {
      private final List<BoundingBox> f_71484_ = Lists.newLinkedList();

      public MineShaftRoom(int p_71486_, Random p_71487_, int p_71488_, int p_71489_, MineshaftFeature.Type p_71490_) {
         super(StructurePieceType.f_67138_, p_71486_, p_71490_, new BoundingBox(p_71488_, 50, p_71489_, p_71488_ + 7 + p_71487_.nextInt(6), 54 + p_71487_.nextInt(6), p_71489_ + 7 + p_71487_.nextInt(6)));
         this.f_71465_ = p_71490_;
      }

      public MineShaftRoom(ServerLevel p_162595_, CompoundTag p_162596_) {
         super(StructurePieceType.f_67138_, p_162596_);
         BoundingBox.f_162354_.listOf().parse(NbtOps.f_128958_, p_162596_.m_128437_("Entrances", 11)).resultOrPartial(MineShaftPieces.f_162462_::error).ifPresent(this.f_71484_::addAll);
      }

      public void m_142537_(StructurePiece p_162601_, StructurePieceAccessor p_162602_, Random p_162603_) {
         int i = this.m_73548_();
         int j = this.f_73383_.m_71057_() - 3 - 1;
         if (j <= 0) {
            j = 1;
         }

         int k;
         for(k = 0; k < this.f_73383_.m_71056_(); k = k + 4) {
            k = k + p_162603_.nextInt(this.f_73383_.m_71056_());
            if (k + 3 > this.f_73383_.m_71056_()) {
               break;
            }

            MineShaftPieces.MineShaftPiece mineshaftpieces$mineshaftpiece = MineShaftPieces.m_162471_(p_162601_, p_162602_, p_162603_, this.f_73383_.m_162395_() + k, this.f_73383_.m_162396_() + p_162603_.nextInt(j) + 1, this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
            if (mineshaftpieces$mineshaftpiece != null) {
               BoundingBox boundingbox = mineshaftpieces$mineshaftpiece.m_73547_();
               this.f_71484_.add(new BoundingBox(boundingbox.m_162395_(), boundingbox.m_162396_(), this.f_73383_.m_162398_(), boundingbox.m_162399_(), boundingbox.m_162400_(), this.f_73383_.m_162398_() + 1));
            }
         }

         for(k = 0; k < this.f_73383_.m_71056_(); k = k + 4) {
            k = k + p_162603_.nextInt(this.f_73383_.m_71056_());
            if (k + 3 > this.f_73383_.m_71056_()) {
               break;
            }

            MineShaftPieces.MineShaftPiece mineshaftpieces$mineshaftpiece1 = MineShaftPieces.m_162471_(p_162601_, p_162602_, p_162603_, this.f_73383_.m_162395_() + k, this.f_73383_.m_162396_() + p_162603_.nextInt(j) + 1, this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
            if (mineshaftpieces$mineshaftpiece1 != null) {
               BoundingBox boundingbox1 = mineshaftpieces$mineshaftpiece1.m_73547_();
               this.f_71484_.add(new BoundingBox(boundingbox1.m_162395_(), boundingbox1.m_162396_(), this.f_73383_.m_162401_() - 1, boundingbox1.m_162399_(), boundingbox1.m_162400_(), this.f_73383_.m_162401_()));
            }
         }

         for(k = 0; k < this.f_73383_.m_71058_(); k = k + 4) {
            k = k + p_162603_.nextInt(this.f_73383_.m_71058_());
            if (k + 3 > this.f_73383_.m_71058_()) {
               break;
            }

            MineShaftPieces.MineShaftPiece mineshaftpieces$mineshaftpiece2 = MineShaftPieces.m_162471_(p_162601_, p_162602_, p_162603_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_162603_.nextInt(j) + 1, this.f_73383_.m_162398_() + k, Direction.WEST, i);
            if (mineshaftpieces$mineshaftpiece2 != null) {
               BoundingBox boundingbox2 = mineshaftpieces$mineshaftpiece2.m_73547_();
               this.f_71484_.add(new BoundingBox(this.f_73383_.m_162395_(), boundingbox2.m_162396_(), boundingbox2.m_162398_(), this.f_73383_.m_162395_() + 1, boundingbox2.m_162400_(), boundingbox2.m_162401_()));
            }
         }

         for(k = 0; k < this.f_73383_.m_71058_(); k = k + 4) {
            k = k + p_162603_.nextInt(this.f_73383_.m_71058_());
            if (k + 3 > this.f_73383_.m_71058_()) {
               break;
            }

            StructurePiece structurepiece = MineShaftPieces.m_162471_(p_162601_, p_162602_, p_162603_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_162603_.nextInt(j) + 1, this.f_73383_.m_162398_() + k, Direction.EAST, i);
            if (structurepiece != null) {
               BoundingBox boundingbox3 = structurepiece.m_73547_();
               this.f_71484_.add(new BoundingBox(this.f_73383_.m_162399_() - 1, boundingbox3.m_162396_(), boundingbox3.m_162398_(), this.f_73383_.m_162399_(), boundingbox3.m_162400_(), boundingbox3.m_162401_()));
            }
         }

      }

      public boolean m_7832_(WorldGenLevel p_71499_, StructureFeatureManager p_71500_, ChunkGenerator p_71501_, Random p_71502_, BoundingBox p_71503_, ChunkPos p_71504_, BlockPos p_71505_) {
         if (this.m_162578_(p_71499_, p_71503_)) {
            return false;
         } else {
            this.m_73441_(p_71499_, p_71503_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), this.f_73383_.m_162399_(), this.f_73383_.m_162396_(), this.f_73383_.m_162401_(), Blocks.f_50493_.m_49966_(), f_73382_, true);
            this.m_73441_(p_71499_, p_71503_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() + 1, this.f_73383_.m_162398_(), this.f_73383_.m_162399_(), Math.min(this.f_73383_.m_162396_() + 3, this.f_73383_.m_162400_()), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);

            for(BoundingBox boundingbox : this.f_71484_) {
               this.m_73441_(p_71499_, p_71503_, boundingbox.m_162395_(), boundingbox.m_162400_() - 2, boundingbox.m_162398_(), boundingbox.m_162399_(), boundingbox.m_162400_(), boundingbox.m_162401_(), f_73382_, f_73382_, false);
            }

            this.m_73453_(p_71499_, p_71503_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() + 4, this.f_73383_.m_162398_(), this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, false);
            return true;
         }
      }

      public void m_6324_(int p_71495_, int p_71496_, int p_71497_) {
         super.m_6324_(p_71495_, p_71496_, p_71497_);

         for(BoundingBox boundingbox : this.f_71484_) {
            boundingbox.m_162367_(p_71495_, p_71496_, p_71497_);
         }

      }

      protected void m_142347_(ServerLevel p_162598_, CompoundTag p_162599_) {
         super.m_142347_(p_162598_, p_162599_);
         BoundingBox.f_162354_.listOf().encodeStart(NbtOps.f_128958_, this.f_71484_).resultOrPartial(MineShaftPieces.f_162462_::error).ifPresent((p_162606_) -> {
            p_162599_.m_128365_("Entrances", p_162606_);
         });
      }
   }

   public static class MineShaftStairs extends MineShaftPieces.MineShaftPiece {
      public MineShaftStairs(int p_71513_, BoundingBox p_71514_, Direction p_71515_, MineshaftFeature.Type p_71516_) {
         super(StructurePieceType.f_67139_, p_71513_, p_71516_, p_71514_);
         this.m_73519_(p_71515_);
      }

      public MineShaftStairs(ServerLevel p_162608_, CompoundTag p_162609_) {
         super(StructurePieceType.f_67139_, p_162609_);
      }

      @Nullable
      public static BoundingBox m_162614_(StructurePieceAccessor p_162615_, Random p_162616_, int p_162617_, int p_162618_, int p_162619_, Direction p_162620_) {
         BoundingBox boundingbox;
         switch(p_162620_) {
         case NORTH:
         default:
            boundingbox = new BoundingBox(0, -5, -8, 2, 2, 0);
            break;
         case SOUTH:
            boundingbox = new BoundingBox(0, -5, 0, 2, 2, 8);
            break;
         case WEST:
            boundingbox = new BoundingBox(-8, -5, 0, 0, 2, 2);
            break;
         case EAST:
            boundingbox = new BoundingBox(0, -5, 0, 8, 2, 2);
         }

         boundingbox.m_162367_(p_162617_, p_162618_, p_162619_);
         return p_162615_.m_141921_(boundingbox) != null ? null : boundingbox;
      }

      public void m_142537_(StructurePiece p_162611_, StructurePieceAccessor p_162612_, Random p_162613_) {
         int i = this.m_73548_();
         Direction direction = this.m_73549_();
         if (direction != null) {
            switch(direction) {
            case NORTH:
            default:
               MineShaftPieces.m_162471_(p_162611_, p_162612_, p_162613_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, i);
               break;
            case SOUTH:
               MineShaftPieces.m_162471_(p_162611_, p_162612_, p_162613_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, i);
               break;
            case WEST:
               MineShaftPieces.m_162471_(p_162611_, p_162612_, p_162613_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), Direction.WEST, i);
               break;
            case EAST:
               MineShaftPieces.m_162471_(p_162611_, p_162612_, p_162613_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), Direction.EAST, i);
            }
         }

      }

      public boolean m_7832_(WorldGenLevel p_71521_, StructureFeatureManager p_71522_, ChunkGenerator p_71523_, Random p_71524_, BoundingBox p_71525_, ChunkPos p_71526_, BlockPos p_71527_) {
         if (this.m_162578_(p_71521_, p_71525_)) {
            return false;
         } else {
            this.m_73441_(p_71521_, p_71525_, 0, 5, 0, 2, 7, 1, f_73382_, f_73382_, false);
            this.m_73441_(p_71521_, p_71525_, 0, 0, 7, 2, 2, 8, f_73382_, f_73382_, false);

            for(int i = 0; i < 5; ++i) {
               this.m_73441_(p_71521_, p_71525_, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, f_73382_, f_73382_, false);
            }

            return true;
         }
      }
   }
}