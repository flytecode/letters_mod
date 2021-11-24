package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PipeBlock extends Block {
   private static final Direction[] f_55156_ = Direction.values();
   public static final BooleanProperty f_55148_ = BlockStateProperties.f_61368_;
   public static final BooleanProperty f_55149_ = BlockStateProperties.f_61369_;
   public static final BooleanProperty f_55150_ = BlockStateProperties.f_61370_;
   public static final BooleanProperty f_55151_ = BlockStateProperties.f_61371_;
   public static final BooleanProperty f_55152_ = BlockStateProperties.f_61366_;
   public static final BooleanProperty f_55153_ = BlockStateProperties.f_61367_;
   public static final Map<Direction, BooleanProperty> f_55154_ = ImmutableMap.copyOf(Util.m_137469_(Maps.newEnumMap(Direction.class), (p_55164_) -> {
      p_55164_.put(Direction.NORTH, f_55148_);
      p_55164_.put(Direction.EAST, f_55149_);
      p_55164_.put(Direction.SOUTH, f_55150_);
      p_55164_.put(Direction.WEST, f_55151_);
      p_55164_.put(Direction.UP, f_55152_);
      p_55164_.put(Direction.DOWN, f_55153_);
   }));
   protected final VoxelShape[] f_55155_;

   public PipeBlock(float p_55159_, BlockBehaviour.Properties p_55160_) {
      super(p_55160_);
      this.f_55155_ = this.m_55161_(p_55159_);
   }

   private VoxelShape[] m_55161_(float p_55162_) {
      float f = 0.5F - p_55162_;
      float f1 = 0.5F + p_55162_;
      VoxelShape voxelshape = Block.m_49796_((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
      VoxelShape[] avoxelshape = new VoxelShape[f_55156_.length];

      for(int i = 0; i < f_55156_.length; ++i) {
         Direction direction = f_55156_[i];
         avoxelshape[i] = Shapes.m_83048_(0.5D + Math.min((double)(-p_55162_), (double)direction.m_122429_() * 0.5D), 0.5D + Math.min((double)(-p_55162_), (double)direction.m_122430_() * 0.5D), 0.5D + Math.min((double)(-p_55162_), (double)direction.m_122431_() * 0.5D), 0.5D + Math.max((double)p_55162_, (double)direction.m_122429_() * 0.5D), 0.5D + Math.max((double)p_55162_, (double)direction.m_122430_() * 0.5D), 0.5D + Math.max((double)p_55162_, (double)direction.m_122431_() * 0.5D));
      }

      VoxelShape[] avoxelshape1 = new VoxelShape[64];

      for(int k = 0; k < 64; ++k) {
         VoxelShape voxelshape1 = voxelshape;

         for(int j = 0; j < f_55156_.length; ++j) {
            if ((k & 1 << j) != 0) {
               voxelshape1 = Shapes.m_83110_(voxelshape1, avoxelshape[j]);
            }
         }

         avoxelshape1[k] = voxelshape1;
      }

      return avoxelshape1;
   }

   public boolean m_7420_(BlockState p_55166_, BlockGetter p_55167_, BlockPos p_55168_) {
      return false;
   }

   public VoxelShape m_5940_(BlockState p_55170_, BlockGetter p_55171_, BlockPos p_55172_, CollisionContext p_55173_) {
      return this.f_55155_[this.m_55174_(p_55170_)];
   }

   protected int m_55174_(BlockState p_55175_) {
      int i = 0;

      for(int j = 0; j < f_55156_.length; ++j) {
         if (p_55175_.m_61143_(f_55154_.get(f_55156_[j]))) {
            i |= 1 << j;
         }
      }

      return i;
   }
}