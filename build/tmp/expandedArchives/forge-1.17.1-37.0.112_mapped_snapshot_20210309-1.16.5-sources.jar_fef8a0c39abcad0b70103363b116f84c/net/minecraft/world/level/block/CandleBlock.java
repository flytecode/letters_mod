package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.function.ToIntFunction;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleBlock extends AbstractCandleBlock implements SimpleWaterloggedBlock {
   public static final int f_152788_ = 1;
   public static final int f_152789_ = 4;
   public static final IntegerProperty f_152790_ = BlockStateProperties.f_155994_;
   public static final BooleanProperty f_152791_ = AbstractCandleBlock.f_151895_;
   public static final BooleanProperty f_152792_ = BlockStateProperties.f_61362_;
   public static final ToIntFunction<BlockState> f_152793_ = (p_152848_) -> {
      return p_152848_.m_61143_(f_152791_) ? 3 * p_152848_.m_61143_(f_152790_) : 0;
   };
   private static final Int2ObjectMap<List<Vec3>> f_152794_ = Util.m_137537_(() -> {
      Int2ObjectMap<List<Vec3>> int2objectmap = new Int2ObjectOpenHashMap<>();
      int2objectmap.defaultReturnValue(ImmutableList.of());
      int2objectmap.put(1, ImmutableList.of(new Vec3(0.5D, 0.5D, 0.5D)));
      int2objectmap.put(2, ImmutableList.of(new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.625D, 0.5D, 0.44D)));
      int2objectmap.put(3, ImmutableList.of(new Vec3(0.5D, 0.313D, 0.625D), new Vec3(0.375D, 0.44D, 0.5D), new Vec3(0.56D, 0.5D, 0.44D)));
      int2objectmap.put(4, ImmutableList.of(new Vec3(0.44D, 0.313D, 0.56D), new Vec3(0.625D, 0.44D, 0.56D), new Vec3(0.375D, 0.44D, 0.375D), new Vec3(0.56D, 0.5D, 0.375D)));
      return Int2ObjectMaps.unmodifiable(int2objectmap);
   });
   private static final VoxelShape f_152795_ = Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D);
   private static final VoxelShape f_152796_ = Block.m_49796_(5.0D, 0.0D, 6.0D, 11.0D, 6.0D, 9.0D);
   private static final VoxelShape f_152797_ = Block.m_49796_(5.0D, 0.0D, 6.0D, 10.0D, 6.0D, 11.0D);
   private static final VoxelShape f_152798_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 10.0D);

   public CandleBlock(BlockBehaviour.Properties p_152801_) {
      super(p_152801_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_152790_, Integer.valueOf(1)).m_61124_(f_152791_, Boolean.valueOf(false)).m_61124_(f_152792_, Boolean.valueOf(false)));
   }

   public InteractionResult m_6227_(BlockState p_152822_, Level p_152823_, BlockPos p_152824_, Player p_152825_, InteractionHand p_152826_, BlockHitResult p_152827_) {
      if (p_152825_.m_150110_().f_35938_ && p_152825_.m_21120_(p_152826_).m_41619_() && p_152822_.m_61143_(f_152791_)) {
         m_151899_(p_152825_, p_152822_, p_152823_, p_152824_);
         return InteractionResult.m_19078_(p_152823_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean m_6864_(BlockState p_152814_, BlockPlaceContext p_152815_) {
      return !p_152815_.m_7078_() && p_152815_.m_43722_().m_41720_() == this.m_5456_() && p_152814_.m_61143_(f_152790_) < 4 ? true : super.m_6864_(p_152814_, p_152815_);
   }

   public BlockState m_5573_(BlockPlaceContext p_152803_) {
      BlockState blockstate = p_152803_.m_43725_().m_8055_(p_152803_.m_8083_());
      if (blockstate.m_60713_(this)) {
         return blockstate.m_61122_(f_152790_);
      } else {
         FluidState fluidstate = p_152803_.m_43725_().m_6425_(p_152803_.m_8083_());
         boolean flag = fluidstate.m_76152_() == Fluids.f_76193_;
         return super.m_5573_(p_152803_).m_61124_(f_152792_, Boolean.valueOf(flag));
      }
   }

   public BlockState m_7417_(BlockState p_152833_, Direction p_152834_, BlockState p_152835_, LevelAccessor p_152836_, BlockPos p_152837_, BlockPos p_152838_) {
      if (p_152833_.m_61143_(f_152792_)) {
         p_152836_.m_6217_().m_5945_(p_152837_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152836_));
      }

      return super.m_7417_(p_152833_, p_152834_, p_152835_, p_152836_, p_152837_, p_152838_);
   }

   public FluidState m_5888_(BlockState p_152844_) {
      return p_152844_.m_61143_(f_152792_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_152844_);
   }

   public VoxelShape m_5940_(BlockState p_152817_, BlockGetter p_152818_, BlockPos p_152819_, CollisionContext p_152820_) {
      switch(p_152817_.m_61143_(f_152790_)) {
      case 1:
      default:
         return f_152795_;
      case 2:
         return f_152796_;
      case 3:
         return f_152797_;
      case 4:
         return f_152798_;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152840_) {
      p_152840_.m_61104_(f_152790_, f_152791_, f_152792_);
   }

   public boolean m_7361_(LevelAccessor p_152805_, BlockPos p_152806_, BlockState p_152807_, FluidState p_152808_) {
      if (!p_152807_.m_61143_(f_152792_) && p_152808_.m_76152_() == Fluids.f_76193_) {
         BlockState blockstate = p_152807_.m_61124_(f_152792_, Boolean.valueOf(true));
         if (p_152807_.m_61143_(f_152791_)) {
            m_151899_((Player)null, blockstate, p_152805_, p_152806_);
         } else {
            p_152805_.m_7731_(p_152806_, blockstate, 3);
         }

         p_152805_.m_6217_().m_5945_(p_152806_, p_152808_.m_76152_(), p_152808_.m_76152_().m_6718_(p_152805_));
         return true;
      } else {
         return false;
      }
   }

   public static boolean m_152845_(BlockState p_152846_) {
      return p_152846_.m_60622_(BlockTags.f_144265_, (p_152810_) -> {
         return p_152810_.m_61138_(f_152791_) && p_152810_.m_61138_(f_152792_);
      }) && !p_152846_.m_61143_(f_152791_) && !p_152846_.m_61143_(f_152792_);
   }

   protected Iterable<Vec3> m_142199_(BlockState p_152812_) {
      return f_152794_.get(p_152812_.m_61143_(f_152790_).intValue());
   }

   protected boolean m_142595_(BlockState p_152842_) {
      return !p_152842_.m_61143_(f_152792_) && super.m_142595_(p_152842_);
   }

   public boolean m_7898_(BlockState p_152829_, LevelReader p_152830_, BlockPos p_152831_) {
      return Block.m_49863_(p_152830_, p_152831_.m_7495_(), Direction.UP);
   }
}