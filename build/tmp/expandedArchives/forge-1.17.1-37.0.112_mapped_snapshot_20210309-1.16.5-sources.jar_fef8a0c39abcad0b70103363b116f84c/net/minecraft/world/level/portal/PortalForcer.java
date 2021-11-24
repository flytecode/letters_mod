package net.minecraft.world.level.portal;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

public class PortalForcer implements net.minecraftforge.common.util.ITeleporter {
   private static final int f_164734_ = 3;
   private static final int f_164735_ = 128;
   private static final int f_164736_ = 16;
   private static final int f_164737_ = 5;
   private static final int f_164738_ = 4;
   private static final int f_164739_ = 3;
   private static final int f_164740_ = -1;
   private static final int f_164741_ = 4;
   private static final int f_164742_ = -1;
   private static final int f_164743_ = 3;
   private static final int f_164744_ = -1;
   private static final int f_164745_ = 2;
   private static final int f_164746_ = -1;
   protected final ServerLevel f_77648_;

   public PortalForcer(ServerLevel p_77650_) {
      this.f_77648_ = p_77650_;
   }

   public Optional<BlockUtil.FoundRectangle> m_77669_(BlockPos p_77670_, boolean p_77671_) {
      PoiManager poimanager = this.f_77648_.m_8904_();
      int i = p_77671_ ? 16 : 128;
      poimanager.m_27056_(this.f_77648_, p_77670_, i);
      Optional<PoiRecord> optional = poimanager.m_27166_((p_77654_) -> {
         return p_77654_ == PoiType.f_27350_;
      }, p_77670_, i, PoiManager.Occupancy.ANY).sorted(Comparator.<PoiRecord>comparingDouble((p_77660_) -> {
         return p_77660_.m_27257_().m_123331_(p_77670_);
      }).thenComparingInt((p_77675_) -> {
         return p_77675_.m_27257_().m_123342_();
      })).filter((p_77673_) -> {
         return this.f_77648_.m_8055_(p_77673_.m_27257_()).m_61138_(BlockStateProperties.f_61364_);
      }).findFirst();
      return optional.map((p_77652_) -> {
         BlockPos blockpos = p_77652_.m_27257_();
         this.f_77648_.m_7726_().m_8387_(TicketType.f_9447_, new ChunkPos(blockpos), 3, blockpos);
         BlockState blockstate = this.f_77648_.m_8055_(blockpos);
         return BlockUtil.m_124334_(blockpos, blockstate.m_61143_(BlockStateProperties.f_61364_), 21, Direction.Axis.Y, 21, (p_164749_) -> {
            return this.f_77648_.m_8055_(p_164749_) == blockstate;
         });
      });
   }

   public Optional<BlockUtil.FoundRectangle> m_77666_(BlockPos p_77667_, Direction.Axis p_77668_) {
      Direction direction = Direction.m_122390_(Direction.AxisDirection.POSITIVE, p_77668_);
      double d0 = -1.0D;
      BlockPos blockpos = null;
      double d1 = -1.0D;
      BlockPos blockpos1 = null;
      WorldBorder worldborder = this.f_77648_.m_6857_();
      int i = Math.min(this.f_77648_.m_151558_(), this.f_77648_.m_141937_() + this.f_77648_.m_142475_()) - 1;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77667_.m_122032_();

      for(BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.m_121935_(p_77667_, 16, Direction.EAST, Direction.SOUTH)) {
         int j = Math.min(i, this.f_77648_.m_6924_(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos1.m_123341_(), blockpos$mutableblockpos1.m_123343_()));
         int k = 1;
         if (worldborder.m_61937_(blockpos$mutableblockpos1) && worldborder.m_61937_(blockpos$mutableblockpos1.m_122175_(direction, 1))) {
            blockpos$mutableblockpos1.m_122175_(direction.m_122424_(), 1);

            for(int l = j; l >= this.f_77648_.m_141937_(); --l) {
               blockpos$mutableblockpos1.m_142448_(l);
               if (this.f_77648_.m_46859_(blockpos$mutableblockpos1)) {
                  int i1;
                  for(i1 = l; l > this.f_77648_.m_141937_() && this.f_77648_.m_46859_(blockpos$mutableblockpos1.m_122173_(Direction.DOWN)); --l) {
                  }

                  if (l + 4 <= i) {
                     int j1 = i1 - l;
                     if (j1 <= 0 || j1 >= 3) {
                        blockpos$mutableblockpos1.m_142448_(l);
                        if (this.m_77661_(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 0)) {
                           double d2 = p_77667_.m_123331_(blockpos$mutableblockpos1);
                           if (this.m_77661_(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, -1) && this.m_77661_(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 1) && (d0 == -1.0D || d0 > d2)) {
                              d0 = d2;
                              blockpos = blockpos$mutableblockpos1.m_7949_();
                           }

                           if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                              d1 = d2;
                              blockpos1 = blockpos$mutableblockpos1.m_7949_();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (d0 == -1.0D && d1 != -1.0D) {
         blockpos = blockpos1;
         d0 = d1;
      }

      if (d0 == -1.0D) {
         int k1 = Math.max(this.f_77648_.m_141937_() - -1, 70);
         int i2 = i - 9;
         if (i2 < k1) {
            return Optional.empty();
         }

         blockpos = (new BlockPos(p_77667_.m_123341_(), Mth.m_14045_(p_77667_.m_123342_(), k1, i2), p_77667_.m_123343_())).m_7949_();
         Direction direction1 = direction.m_122427_();
         if (!worldborder.m_61937_(blockpos)) {
            return Optional.empty();
         }

         for(int i3 = -1; i3 < 2; ++i3) {
            for(int j3 = 0; j3 < 2; ++j3) {
               for(int k3 = -1; k3 < 3; ++k3) {
                  BlockState blockstate1 = k3 < 0 ? Blocks.f_50080_.m_49966_() : Blocks.f_50016_.m_49966_();
                  blockpos$mutableblockpos.m_122154_(blockpos, j3 * direction.m_122429_() + i3 * direction1.m_122429_(), k3, j3 * direction.m_122431_() + i3 * direction1.m_122431_());
                  this.f_77648_.m_46597_(blockpos$mutableblockpos, blockstate1);
               }
            }
         }
      }

      for(int l1 = -1; l1 < 3; ++l1) {
         for(int j2 = -1; j2 < 4; ++j2) {
            if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
               blockpos$mutableblockpos.m_122154_(blockpos, l1 * direction.m_122429_(), j2, l1 * direction.m_122431_());
               this.f_77648_.m_7731_(blockpos$mutableblockpos, Blocks.f_50080_.m_49966_(), 3);
            }
         }
      }

      BlockState blockstate = Blocks.f_50142_.m_49966_().m_61124_(NetherPortalBlock.f_54904_, p_77668_);

      for(int k2 = 0; k2 < 2; ++k2) {
         for(int l2 = 0; l2 < 3; ++l2) {
            blockpos$mutableblockpos.m_122154_(blockpos, k2 * direction.m_122429_(), l2, k2 * direction.m_122431_());
            this.f_77648_.m_7731_(blockpos$mutableblockpos, blockstate, 18);
         }
      }

      return Optional.of(new BlockUtil.FoundRectangle(blockpos.m_7949_(), 2, 3));
   }

   private boolean m_77661_(BlockPos p_77662_, BlockPos.MutableBlockPos p_77663_, Direction p_77664_, int p_77665_) {
      Direction direction = p_77664_.m_122427_();

      for(int i = -1; i < 3; ++i) {
         for(int j = -1; j < 4; ++j) {
            p_77663_.m_122154_(p_77662_, p_77664_.m_122429_() * i + direction.m_122429_() * p_77665_, j, p_77664_.m_122431_() * i + direction.m_122431_() * p_77665_);
            if (j < 0 && !this.f_77648_.m_8055_(p_77663_).m_60767_().m_76333_()) {
               return false;
            }

            if (j >= 0 && !this.f_77648_.m_46859_(p_77663_)) {
               return false;
            }
         }
      }

      return true;
   }
}
