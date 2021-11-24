package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WalkNodeEvaluator extends NodeEvaluator {
   public static final double f_164724_ = 0.5D;
   protected float f_77544_;
   private final Long2ObjectMap<BlockPathTypes> f_77545_ = new Long2ObjectOpenHashMap<>();
   private final Object2BooleanMap<AABB> f_77546_ = new Object2BooleanOpenHashMap<>();

   public void m_6028_(PathNavigationRegion p_77620_, Mob p_77621_) {
      super.m_6028_(p_77620_, p_77621_);
      this.f_77544_ = p_77621_.m_21439_(BlockPathTypes.WATER);
   }

   public void m_6802_() {
      this.f_77313_.m_21441_(BlockPathTypes.WATER, this.f_77544_);
      this.f_77545_.clear();
      this.f_77546_.clear();
      super.m_6802_();
   }

   public Node m_7171_() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = this.f_77313_.m_146904_();
      BlockState blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos.m_122169_(this.f_77313_.m_20185_(), (double)i, this.f_77313_.m_20189_()));
      if (!this.f_77313_.m_7479_(blockstate.m_60819_().m_76152_())) {
         if (this.m_77361_() && this.f_77313_.m_20069_()) {
            while(true) {
               if (!blockstate.m_60713_(Blocks.f_49990_) && blockstate.m_60819_() != Fluids.f_76193_.m_76068_(false)) {
                  --i;
                  break;
               }

               ++i;
               blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos.m_122169_(this.f_77313_.m_20185_(), (double)i, this.f_77313_.m_20189_()));
            }
         } else if (this.f_77313_.m_20096_()) {
            i = Mth.m_14107_(this.f_77313_.m_20186_() + 0.5D);
         } else {
            BlockPos blockpos;
            for(blockpos = this.f_77313_.m_142538_(); (this.f_77312_.m_8055_(blockpos).m_60795_() || this.f_77312_.m_8055_(blockpos).m_60647_(this.f_77312_, blockpos, PathComputationType.LAND)) && blockpos.m_123342_() > this.f_77313_.f_19853_.m_141937_(); blockpos = blockpos.m_7495_()) {
            }

            i = blockpos.m_7494_().m_123342_();
         }
      } else {
         while(this.f_77313_.m_7479_(blockstate.m_60819_().m_76152_())) {
            ++i;
            blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos.m_122169_(this.f_77313_.m_20185_(), (double)i, this.f_77313_.m_20189_()));
         }

         --i;
      }

      BlockPos blockpos1 = this.f_77313_.m_142538_();
      BlockPathTypes blockpathtypes = this.m_77567_(this.f_77313_, blockpos1.m_123341_(), i, blockpos1.m_123343_());
      if (this.f_77313_.m_21439_(blockpathtypes) < 0.0F) {
         AABB aabb = this.f_77313_.m_142469_();
         if (this.m_77646_(blockpos$mutableblockpos.m_122169_(aabb.f_82288_, (double)i, aabb.f_82290_)) || this.m_77646_(blockpos$mutableblockpos.m_122169_(aabb.f_82288_, (double)i, aabb.f_82293_)) || this.m_77646_(blockpos$mutableblockpos.m_122169_(aabb.f_82291_, (double)i, aabb.f_82290_)) || this.m_77646_(blockpos$mutableblockpos.m_122169_(aabb.f_82291_, (double)i, aabb.f_82293_))) {
            Node node = this.m_77349_(blockpos$mutableblockpos);
            node.f_77282_ = this.m_77572_(this.f_77313_, node.m_77288_());
            node.f_77281_ = this.f_77313_.m_21439_(node.f_77282_);
            return node;
         }
      }

      Node node1 = this.m_5676_(blockpos1.m_123341_(), i, blockpos1.m_123343_());
      node1.f_77282_ = this.m_77572_(this.f_77313_, node1.m_77288_());
      node1.f_77281_ = this.f_77313_.m_21439_(node1.f_77282_);
      return node1;
   }

   private boolean m_77646_(BlockPos p_77647_) {
      BlockPathTypes blockpathtypes = this.m_77572_(this.f_77313_, p_77647_);
      return this.f_77313_.m_21439_(blockpathtypes) >= 0.0F;
   }

   public Target m_7568_(double p_77550_, double p_77551_, double p_77552_) {
      return new Target(this.m_5676_(Mth.m_14107_(p_77550_), Mth.m_14107_(p_77551_), Mth.m_14107_(p_77552_)));
   }

   public int m_6065_(Node[] p_77640_, Node p_77641_) {
      int i = 0;
      int j = 0;
      BlockPathTypes blockpathtypes = this.m_77567_(this.f_77313_, p_77641_.f_77271_, p_77641_.f_77272_ + 1, p_77641_.f_77273_);
      BlockPathTypes blockpathtypes1 = this.m_77567_(this.f_77313_, p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_);
      if (this.f_77313_.m_21439_(blockpathtypes) >= 0.0F && blockpathtypes1 != BlockPathTypes.STICKY_HONEY) {
         j = Mth.m_14143_(Math.max(1.0F, this.f_77313_.f_19793_));
      }

      double d0 = this.m_142213_(new BlockPos(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_));
      Node node = this.m_164725_(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_ + 1, j, d0, Direction.SOUTH, blockpathtypes1);
      if (this.m_77626_(node, p_77641_)) {
         p_77640_[i++] = node;
      }

      Node node1 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_, j, d0, Direction.WEST, blockpathtypes1);
      if (this.m_77626_(node1, p_77641_)) {
         p_77640_[i++] = node1;
      }

      Node node2 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_, j, d0, Direction.EAST, blockpathtypes1);
      if (this.m_77626_(node2, p_77641_)) {
         p_77640_[i++] = node2;
      }

      Node node3 = this.m_164725_(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_ - 1, j, d0, Direction.NORTH, blockpathtypes1);
      if (this.m_77626_(node3, p_77641_)) {
         p_77640_[i++] = node3;
      }

      Node node4 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_ - 1, j, d0, Direction.NORTH, blockpathtypes1);
      if (this.m_77629_(p_77641_, node1, node3, node4)) {
         p_77640_[i++] = node4;
      }

      Node node5 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_ - 1, j, d0, Direction.NORTH, blockpathtypes1);
      if (this.m_77629_(p_77641_, node2, node3, node5)) {
         p_77640_[i++] = node5;
      }

      Node node6 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_ + 1, j, d0, Direction.SOUTH, blockpathtypes1);
      if (this.m_77629_(p_77641_, node1, node, node6)) {
         p_77640_[i++] = node6;
      }

      Node node7 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_ + 1, j, d0, Direction.SOUTH, blockpathtypes1);
      if (this.m_77629_(p_77641_, node2, node, node7)) {
         p_77640_[i++] = node7;
      }

      return i;
   }

   protected boolean m_77626_(@Nullable Node p_77627_, Node p_77628_) {
      return p_77627_ != null && !p_77627_.f_77279_ && (p_77627_.f_77281_ >= 0.0F || p_77628_.f_77281_ < 0.0F);
   }

   protected boolean m_77629_(Node p_77630_, @Nullable Node p_77631_, @Nullable Node p_77632_, @Nullable Node p_77633_) {
      if (p_77633_ != null && p_77632_ != null && p_77631_ != null) {
         if (p_77633_.f_77279_) {
            return false;
         } else if (p_77632_.f_77272_ <= p_77630_.f_77272_ && p_77631_.f_77272_ <= p_77630_.f_77272_) {
            if (p_77631_.f_77282_ != BlockPathTypes.WALKABLE_DOOR && p_77632_.f_77282_ != BlockPathTypes.WALKABLE_DOOR && p_77633_.f_77282_ != BlockPathTypes.WALKABLE_DOOR) {
               boolean flag = p_77632_.f_77282_ == BlockPathTypes.FENCE && p_77631_.f_77282_ == BlockPathTypes.FENCE && (double)this.f_77313_.m_20205_() < 0.5D;
               return p_77633_.f_77281_ >= 0.0F && (p_77632_.f_77272_ < p_77630_.f_77272_ || p_77632_.f_77281_ >= 0.0F || flag) && (p_77631_.f_77272_ < p_77630_.f_77272_ || p_77631_.f_77281_ >= 0.0F || flag);
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean m_77624_(Node p_77625_) {
      Vec3 vec3 = new Vec3((double)p_77625_.f_77271_ - this.f_77313_.m_20185_(), (double)p_77625_.f_77272_ - this.f_77313_.m_20186_(), (double)p_77625_.f_77273_ - this.f_77313_.m_20189_());
      AABB aabb = this.f_77313_.m_142469_();
      int i = Mth.m_14165_(vec3.m_82553_() / aabb.m_82309_());
      vec3 = vec3.m_82490_((double)(1.0F / (float)i));

      for(int j = 1; j <= i; ++j) {
         aabb = aabb.m_82383_(vec3);
         if (this.m_77634_(aabb)) {
            return false;
         }
      }

      return true;
   }

   protected double m_142213_(BlockPos p_164733_) {
      return m_77611_(this.f_77312_, p_164733_);
   }

   public static double m_77611_(BlockGetter p_77612_, BlockPos p_77613_) {
      BlockPos blockpos = p_77613_.m_7495_();
      VoxelShape voxelshape = p_77612_.m_8055_(blockpos).m_60812_(p_77612_, blockpos);
      return (double)blockpos.m_123342_() + (voxelshape.m_83281_() ? 0.0D : voxelshape.m_83297_(Direction.Axis.Y));
   }

   protected boolean m_141974_() {
      return false;
   }

   @Nullable
   protected Node m_164725_(int p_164726_, int p_164727_, int p_164728_, int p_164729_, double p_164730_, Direction p_164731_, BlockPathTypes p_164732_) {
      Node node = null;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      double d0 = this.m_142213_(blockpos$mutableblockpos.m_122178_(p_164726_, p_164727_, p_164728_));
      if (d0 - p_164730_ > 1.125D) {
         return null;
      } else {
         BlockPathTypes blockpathtypes = this.m_77567_(this.f_77313_, p_164726_, p_164727_, p_164728_);
         float f = this.f_77313_.m_21439_(blockpathtypes);
         double d1 = (double)this.f_77313_.m_20205_() / 2.0D;
         if (f >= 0.0F) {
            node = this.m_5676_(p_164726_, p_164727_, p_164728_);
            node.f_77282_ = blockpathtypes;
            node.f_77281_ = Math.max(node.f_77281_, f);
         }

         if (p_164732_ == BlockPathTypes.FENCE && node != null && node.f_77281_ >= 0.0F && !this.m_77624_(node)) {
            node = null;
         }

         if (blockpathtypes != BlockPathTypes.WALKABLE && (!this.m_141974_() || blockpathtypes != BlockPathTypes.WATER)) {
            if ((node == null || node.f_77281_ < 0.0F) && p_164729_ > 0 && blockpathtypes != BlockPathTypes.FENCE && blockpathtypes != BlockPathTypes.UNPASSABLE_RAIL && blockpathtypes != BlockPathTypes.TRAPDOOR && blockpathtypes != BlockPathTypes.POWDER_SNOW) {
               node = this.m_164725_(p_164726_, p_164727_ + 1, p_164728_, p_164729_ - 1, p_164730_, p_164731_, p_164732_);
               if (node != null && (node.f_77282_ == BlockPathTypes.OPEN || node.f_77282_ == BlockPathTypes.WALKABLE) && this.f_77313_.m_20205_() < 1.0F) {
                  double d2 = (double)(p_164726_ - p_164731_.m_122429_()) + 0.5D;
                  double d3 = (double)(p_164728_ - p_164731_.m_122431_()) + 0.5D;
                  AABB aabb = new AABB(d2 - d1, m_77611_(this.f_77312_, blockpos$mutableblockpos.m_122169_(d2, (double)(p_164727_ + 1), d3)) + 0.001D, d3 - d1, d2 + d1, (double)this.f_77313_.m_20206_() + m_77611_(this.f_77312_, blockpos$mutableblockpos.m_122169_((double)node.f_77271_, (double)node.f_77272_, (double)node.f_77273_)) - 0.002D, d3 + d1);
                  if (this.m_77634_(aabb)) {
                     node = null;
                  }
               }
            }

            if (!this.m_141974_() && blockpathtypes == BlockPathTypes.WATER && !this.m_77361_()) {
               if (this.m_77567_(this.f_77313_, p_164726_, p_164727_ - 1, p_164728_) != BlockPathTypes.WATER) {
                  return node;
               }

               while(p_164727_ > this.f_77313_.f_19853_.m_141937_()) {
                  --p_164727_;
                  blockpathtypes = this.m_77567_(this.f_77313_, p_164726_, p_164727_, p_164728_);
                  if (blockpathtypes != BlockPathTypes.WATER) {
                     return node;
                  }

                  node = this.m_5676_(p_164726_, p_164727_, p_164728_);
                  node.f_77282_ = blockpathtypes;
                  node.f_77281_ = Math.max(node.f_77281_, this.f_77313_.m_21439_(blockpathtypes));
               }
            }

            if (blockpathtypes == BlockPathTypes.OPEN) {
               int j = 0;
               int i = p_164727_;

               while(blockpathtypes == BlockPathTypes.OPEN) {
                  --p_164727_;
                  if (p_164727_ < this.f_77313_.f_19853_.m_141937_()) {
                     Node node3 = this.m_5676_(p_164726_, i, p_164728_);
                     node3.f_77282_ = BlockPathTypes.BLOCKED;
                     node3.f_77281_ = -1.0F;
                     return node3;
                  }

                  if (j++ >= this.f_77313_.m_6056_()) {
                     Node node2 = this.m_5676_(p_164726_, p_164727_, p_164728_);
                     node2.f_77282_ = BlockPathTypes.BLOCKED;
                     node2.f_77281_ = -1.0F;
                     return node2;
                  }

                  blockpathtypes = this.m_77567_(this.f_77313_, p_164726_, p_164727_, p_164728_);
                  f = this.f_77313_.m_21439_(blockpathtypes);
                  if (blockpathtypes != BlockPathTypes.OPEN && f >= 0.0F) {
                     node = this.m_5676_(p_164726_, p_164727_, p_164728_);
                     node.f_77282_ = blockpathtypes;
                     node.f_77281_ = Math.max(node.f_77281_, f);
                     break;
                  }

                  if (f < 0.0F) {
                     Node node1 = this.m_5676_(p_164726_, p_164727_, p_164728_);
                     node1.f_77282_ = BlockPathTypes.BLOCKED;
                     node1.f_77281_ = -1.0F;
                     return node1;
                  }
               }
            }

            if (blockpathtypes == BlockPathTypes.FENCE) {
               node = this.m_5676_(p_164726_, p_164727_, p_164728_);
               node.f_77279_ = true;
               node.f_77282_ = blockpathtypes;
               node.f_77281_ = blockpathtypes.m_77124_();
            }

            return node;
         } else {
            return node;
         }
      }
   }

   private boolean m_77634_(AABB p_77635_) {
      return this.f_77546_.computeIfAbsent(p_77635_, (p_77638_) -> {
         return !this.f_77312_.m_45756_(this.f_77313_, p_77635_);
      });
   }

   public BlockPathTypes m_7209_(BlockGetter p_77594_, int p_77595_, int p_77596_, int p_77597_, Mob p_77598_, int p_77599_, int p_77600_, int p_77601_, boolean p_77602_, boolean p_77603_) {
      EnumSet<BlockPathTypes> enumset = EnumSet.noneOf(BlockPathTypes.class);
      BlockPathTypes blockpathtypes = BlockPathTypes.BLOCKED;
      BlockPos blockpos = p_77598_.m_142538_();
      blockpathtypes = this.m_77580_(p_77594_, p_77595_, p_77596_, p_77597_, p_77599_, p_77600_, p_77601_, p_77602_, p_77603_, enumset, blockpathtypes, blockpos);
      if (enumset.contains(BlockPathTypes.FENCE)) {
         return BlockPathTypes.FENCE;
      } else if (enumset.contains(BlockPathTypes.UNPASSABLE_RAIL)) {
         return BlockPathTypes.UNPASSABLE_RAIL;
      } else {
         BlockPathTypes blockpathtypes1 = BlockPathTypes.BLOCKED;

         for(BlockPathTypes blockpathtypes2 : enumset) {
            if (p_77598_.m_21439_(blockpathtypes2) < 0.0F) {
               return blockpathtypes2;
            }

            if (p_77598_.m_21439_(blockpathtypes2) >= p_77598_.m_21439_(blockpathtypes1)) {
               blockpathtypes1 = blockpathtypes2;
            }
         }

         return blockpathtypes == BlockPathTypes.OPEN && p_77598_.m_21439_(blockpathtypes1) == 0.0F && p_77599_ <= 1 ? BlockPathTypes.OPEN : blockpathtypes1;
      }
   }

   public BlockPathTypes m_77580_(BlockGetter p_77581_, int p_77582_, int p_77583_, int p_77584_, int p_77585_, int p_77586_, int p_77587_, boolean p_77588_, boolean p_77589_, EnumSet<BlockPathTypes> p_77590_, BlockPathTypes p_77591_, BlockPos p_77592_) {
      for(int i = 0; i < p_77585_; ++i) {
         for(int j = 0; j < p_77586_; ++j) {
            for(int k = 0; k < p_77587_; ++k) {
               int l = i + p_77582_;
               int i1 = j + p_77583_;
               int j1 = k + p_77584_;
               BlockPathTypes blockpathtypes = this.m_8086_(p_77581_, l, i1, j1);
               blockpathtypes = this.m_6603_(p_77581_, p_77588_, p_77589_, p_77592_, blockpathtypes);
               if (i == 0 && j == 0 && k == 0) {
                  p_77591_ = blockpathtypes;
               }

               p_77590_.add(blockpathtypes);
            }
         }
      }

      return p_77591_;
   }

   protected BlockPathTypes m_6603_(BlockGetter p_77614_, boolean p_77615_, boolean p_77616_, BlockPos p_77617_, BlockPathTypes p_77618_) {
      if (p_77618_ == BlockPathTypes.DOOR_WOOD_CLOSED && p_77615_ && p_77616_) {
         p_77618_ = BlockPathTypes.WALKABLE_DOOR;
      }

      if (p_77618_ == BlockPathTypes.DOOR_OPEN && !p_77616_) {
         p_77618_ = BlockPathTypes.BLOCKED;
      }

      if (p_77618_ == BlockPathTypes.RAIL && !(p_77614_.m_8055_(p_77617_).m_60734_() instanceof BaseRailBlock) && !(p_77614_.m_8055_(p_77617_.m_7495_()).m_60734_() instanceof BaseRailBlock)) {
         p_77618_ = BlockPathTypes.UNPASSABLE_RAIL;
      }

      if (p_77618_ == BlockPathTypes.LEAVES) {
         p_77618_ = BlockPathTypes.BLOCKED;
      }

      return p_77618_;
   }

   private BlockPathTypes m_77572_(Mob p_77573_, BlockPos p_77574_) {
      return this.m_77567_(p_77573_, p_77574_.m_123341_(), p_77574_.m_123342_(), p_77574_.m_123343_());
   }

   protected BlockPathTypes m_77567_(Mob p_77568_, int p_77569_, int p_77570_, int p_77571_) {
      return this.f_77545_.computeIfAbsent(BlockPos.m_121882_(p_77569_, p_77570_, p_77571_), (p_77566_) -> {
         return this.m_7209_(this.f_77312_, p_77569_, p_77570_, p_77571_, p_77568_, this.f_77315_, this.f_77316_, this.f_77317_, this.m_77360_(), this.m_77357_());
      });
   }

   public BlockPathTypes m_8086_(BlockGetter p_77576_, int p_77577_, int p_77578_, int p_77579_) {
      return m_77604_(p_77576_, new BlockPos.MutableBlockPos(p_77577_, p_77578_, p_77579_));
   }

   public static BlockPathTypes m_77604_(BlockGetter p_77605_, BlockPos.MutableBlockPos p_77606_) {
      int i = p_77606_.m_123341_();
      int j = p_77606_.m_123342_();
      int k = p_77606_.m_123343_();
      BlockPathTypes blockpathtypes = m_77643_(p_77605_, p_77606_);
      if (blockpathtypes == BlockPathTypes.OPEN && j >= p_77605_.m_141937_() + 1) {
         BlockPathTypes blockpathtypes1 = m_77643_(p_77605_, p_77606_.m_122178_(i, j - 1, k));
         blockpathtypes = blockpathtypes1 != BlockPathTypes.WALKABLE && blockpathtypes1 != BlockPathTypes.OPEN && blockpathtypes1 != BlockPathTypes.WATER && blockpathtypes1 != BlockPathTypes.LAVA ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
         if (blockpathtypes1 == BlockPathTypes.DAMAGE_FIRE) {
            blockpathtypes = BlockPathTypes.DAMAGE_FIRE;
         }

         if (blockpathtypes1 == BlockPathTypes.DAMAGE_CACTUS) {
            blockpathtypes = BlockPathTypes.DAMAGE_CACTUS;
         }

         if (blockpathtypes1 == BlockPathTypes.DAMAGE_OTHER) {
            blockpathtypes = BlockPathTypes.DAMAGE_OTHER;
         }

         if (blockpathtypes1 == BlockPathTypes.STICKY_HONEY) {
            blockpathtypes = BlockPathTypes.STICKY_HONEY;
         }
      }

      if (blockpathtypes == BlockPathTypes.WALKABLE) {
         blockpathtypes = m_77607_(p_77605_, p_77606_.m_122178_(i, j, k), blockpathtypes);
      }

      return blockpathtypes;
   }

   public static BlockPathTypes m_77607_(BlockGetter p_77608_, BlockPos.MutableBlockPos p_77609_, BlockPathTypes p_77610_) {
      int i = p_77609_.m_123341_();
      int j = p_77609_.m_123342_();
      int k = p_77609_.m_123343_();

      for(int l = -1; l <= 1; ++l) {
         for(int i1 = -1; i1 <= 1; ++i1) {
            for(int j1 = -1; j1 <= 1; ++j1) {
               if (l != 0 || j1 != 0) {
                  p_77609_.m_122178_(i + l, j + i1, k + j1);
                  BlockState blockstate = p_77608_.m_8055_(p_77609_);
                  if (blockstate.m_60713_(Blocks.f_50128_)) {
                     return BlockPathTypes.DANGER_CACTUS;
                  }

                  if (blockstate.m_60713_(Blocks.f_50685_)) {
                     return BlockPathTypes.DANGER_OTHER;
                  }

                  if (m_77622_(blockstate)) {
                     return BlockPathTypes.DANGER_FIRE;
                  }

                  if (p_77608_.m_6425_(p_77609_).m_76153_(FluidTags.f_13131_)) {
                     return BlockPathTypes.WATER_BORDER;
                  }
               }
            }
         }
      }

      return p_77610_;
   }

   protected static BlockPathTypes m_77643_(BlockGetter p_77644_, BlockPos p_77645_) {
      BlockState blockstate = p_77644_.m_8055_(p_77645_);
      BlockPathTypes type = blockstate.getAiPathNodeType(p_77644_, p_77645_);
      if (type != null) return type;
      Block block = blockstate.m_60734_();
      Material material = blockstate.m_60767_();
      if (blockstate.m_60795_()) {
         return BlockPathTypes.OPEN;
      } else if (!blockstate.m_60620_(BlockTags.f_13036_) && !blockstate.m_60713_(Blocks.f_50196_) && !blockstate.m_60713_(Blocks.f_152545_)) {
         if (blockstate.m_60713_(Blocks.f_152499_)) {
            return BlockPathTypes.POWDER_SNOW;
         } else if (blockstate.m_60713_(Blocks.f_50128_)) {
            return BlockPathTypes.DAMAGE_CACTUS;
         } else if (blockstate.m_60713_(Blocks.f_50685_)) {
            return BlockPathTypes.DAMAGE_OTHER;
         } else if (blockstate.m_60713_(Blocks.f_50719_)) {
            return BlockPathTypes.STICKY_HONEY;
         } else if (blockstate.m_60713_(Blocks.f_50262_)) {
            return BlockPathTypes.COCOA;
         } else {
            FluidState fluidstate = p_77644_.m_6425_(p_77645_);
            if (fluidstate.m_76153_(FluidTags.f_13132_)) {
               return BlockPathTypes.LAVA;
            } else if (m_77622_(blockstate)) {
               return BlockPathTypes.DAMAGE_FIRE;
            } else if (DoorBlock.m_52817_(blockstate) && !blockstate.m_61143_(DoorBlock.f_52727_)) {
               return BlockPathTypes.DOOR_WOOD_CLOSED;
            } else if (block instanceof DoorBlock && material == Material.f_76279_ && !blockstate.m_61143_(DoorBlock.f_52727_)) {
               return BlockPathTypes.DOOR_IRON_CLOSED;
            } else if (block instanceof DoorBlock && blockstate.m_61143_(DoorBlock.f_52727_)) {
               return BlockPathTypes.DOOR_OPEN;
            } else if (block instanceof BaseRailBlock) {
               return BlockPathTypes.RAIL;
            } else if (block instanceof LeavesBlock) {
               return BlockPathTypes.LEAVES;
            } else if (!blockstate.m_60620_(BlockTags.f_13039_) && !blockstate.m_60620_(BlockTags.f_13032_) && (!(block instanceof FenceGateBlock) || blockstate.m_61143_(FenceGateBlock.f_53341_))) {
               if (!blockstate.m_60647_(p_77644_, p_77645_, PathComputationType.LAND)) {
                  return BlockPathTypes.BLOCKED;
               } else {
                  return fluidstate.m_76153_(FluidTags.f_13131_) ? BlockPathTypes.WATER : BlockPathTypes.OPEN;
               }
            } else {
               return BlockPathTypes.FENCE;
            }
         }
      } else {
         return BlockPathTypes.TRAPDOOR;
      }
   }

   public static boolean m_77622_(BlockState p_77623_) {
      return p_77623_.m_60620_(BlockTags.f_13076_) || p_77623_.m_60713_(Blocks.f_49991_) || p_77623_.m_60713_(Blocks.f_50450_) || CampfireBlock.m_51319_(p_77623_) || p_77623_.m_60713_(Blocks.f_152477_);
   }
}
