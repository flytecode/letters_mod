package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FarmBlock extends Block {
   public static final IntegerProperty f_53243_ = BlockStateProperties.f_61423_;
   protected static final VoxelShape f_53244_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
   public static final int f_153225_ = 7;

   public FarmBlock(BlockBehaviour.Properties p_53247_) {
      super(p_53247_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53243_, Integer.valueOf(0)));
   }

   public BlockState m_7417_(BlockState p_53276_, Direction p_53277_, BlockState p_53278_, LevelAccessor p_53279_, BlockPos p_53280_, BlockPos p_53281_) {
      if (p_53277_ == Direction.UP && !p_53276_.m_60710_(p_53279_, p_53280_)) {
         p_53279_.m_6219_().m_5945_(p_53280_, this, 1);
      }

      return super.m_7417_(p_53276_, p_53277_, p_53278_, p_53279_, p_53280_, p_53281_);
   }

   public boolean m_7898_(BlockState p_53272_, LevelReader p_53273_, BlockPos p_53274_) {
      BlockState blockstate = p_53273_.m_8055_(p_53274_.m_7494_());
      return !blockstate.m_60767_().m_76333_() || blockstate.m_60734_() instanceof FenceGateBlock || blockstate.m_60734_() instanceof MovingPistonBlock;
   }

   public BlockState m_5573_(BlockPlaceContext p_53249_) {
      return !this.m_49966_().m_60710_(p_53249_.m_43725_(), p_53249_.m_8083_()) ? Blocks.f_50493_.m_49966_() : super.m_5573_(p_53249_);
   }

   public boolean m_7923_(BlockState p_53295_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_53290_, BlockGetter p_53291_, BlockPos p_53292_, CollisionContext p_53293_) {
      return f_53244_;
   }

   public void m_7458_(BlockState p_53262_, ServerLevel p_53263_, BlockPos p_53264_, Random p_53265_) {
      if (!p_53262_.m_60710_(p_53263_, p_53264_)) {
         m_53296_(p_53262_, p_53263_, p_53264_);
      }

   }

   public void m_7455_(BlockState p_53285_, ServerLevel p_53286_, BlockPos p_53287_, Random p_53288_) {
      int i = p_53285_.m_61143_(f_53243_);
      if (!m_53258_(p_53286_, p_53287_) && !p_53286_.m_46758_(p_53287_.m_7494_())) {
         if (i > 0) {
            p_53286_.m_7731_(p_53287_, p_53285_.m_61124_(f_53243_, Integer.valueOf(i - 1)), 2);
         } else if (!m_53250_(p_53286_, p_53287_)) {
            m_53296_(p_53285_, p_53286_, p_53287_);
         }
      } else if (i < 7) {
         p_53286_.m_7731_(p_53287_, p_53285_.m_61124_(f_53243_, Integer.valueOf(7)), 2);
      }

   }

   public void m_142072_(Level p_153227_, BlockState p_153228_, BlockPos p_153229_, Entity p_153230_, float p_153231_) {
      if (!p_153227_.f_46443_ && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(p_153227_, p_153229_, Blocks.f_50493_.m_49966_(), p_153231_, p_153230_)) { // Forge: Move logic to Entity#canTrample
         m_53296_(p_153228_, p_153227_, p_153229_);
      }

      super.m_142072_(p_153227_, p_153228_, p_153229_, p_153230_, p_153231_);
   }

   public static void m_53296_(BlockState p_53297_, Level p_53298_, BlockPos p_53299_) {
      p_53298_.m_46597_(p_53299_, m_49897_(p_53297_, Blocks.f_50493_.m_49966_(), p_53298_, p_53299_));
   }

   private static boolean m_53250_(BlockGetter p_53251_, BlockPos p_53252_) {
      BlockState plant = p_53251_.m_8055_(p_53252_.m_7494_());
      BlockState state = p_53251_.m_8055_(p_53252_);
      return plant.m_60734_() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_53251_, p_53252_, Direction.UP, (net.minecraftforge.common.IPlantable)plant.m_60734_());
   }

   private static boolean m_53258_(LevelReader p_53259_, BlockPos p_53260_) {
      for(BlockPos blockpos : BlockPos.m_121940_(p_53260_.m_142082_(-4, 0, -4), p_53260_.m_142082_(4, 1, 4))) {
         if (p_53259_.m_6425_(blockpos).m_76153_(FluidTags.f_13131_)) {
            return true;
         }
      }

      return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_53259_, p_53260_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53283_) {
      p_53283_.m_61104_(f_53243_);
   }

   public boolean m_7357_(BlockState p_53267_, BlockGetter p_53268_, BlockPos p_53269_, PathComputationType p_53270_) {
      return false;
   }
}
