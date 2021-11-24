package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnderChestBlock extends AbstractChestBlock<EnderChestBlockEntity> implements SimpleWaterloggedBlock {
   public static final DirectionProperty f_53115_ = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty f_53116_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_53117_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
   private static final Component f_53118_ = new TranslatableComponent("container.enderchest");

   public EnderChestBlock(BlockBehaviour.Properties p_53121_) {
      super(p_53121_, () -> {
         return BlockEntityType.f_58920_;
      });
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53115_, Direction.NORTH).m_61124_(f_53116_, Boolean.valueOf(false)));
   }

   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> m_5641_(BlockState p_53149_, Level p_53150_, BlockPos p_53151_, boolean p_53152_) {
      return DoubleBlockCombiner.Combiner::m_6502_;
   }

   public VoxelShape m_5940_(BlockState p_53171_, BlockGetter p_53172_, BlockPos p_53173_, CollisionContext p_53174_) {
      return f_53117_;
   }

   public RenderShape m_7514_(BlockState p_53169_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public BlockState m_5573_(BlockPlaceContext p_53128_) {
      FluidState fluidstate = p_53128_.m_43725_().m_6425_(p_53128_.m_8083_());
      return this.m_49966_().m_61124_(f_53115_, p_53128_.m_8125_().m_122424_()).m_61124_(f_53116_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public InteractionResult m_6227_(BlockState p_53137_, Level p_53138_, BlockPos p_53139_, Player p_53140_, InteractionHand p_53141_, BlockHitResult p_53142_) {
      PlayerEnderChestContainer playerenderchestcontainer = p_53140_.m_36327_();
      BlockEntity blockentity = p_53138_.m_7702_(p_53139_);
      if (playerenderchestcontainer != null && blockentity instanceof EnderChestBlockEntity) {
         BlockPos blockpos = p_53139_.m_7494_();
         if (p_53138_.m_8055_(blockpos).m_60796_(p_53138_, blockpos)) {
            return InteractionResult.m_19078_(p_53138_.f_46443_);
         } else if (p_53138_.f_46443_) {
            return InteractionResult.SUCCESS;
         } else {
            EnderChestBlockEntity enderchestblockentity = (EnderChestBlockEntity)blockentity;
            playerenderchestcontainer.m_40105_(enderchestblockentity);
            p_53140_.m_5893_(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
               return ChestMenu.m_39237_(p_53124_, p_53125_, playerenderchestcontainer);
            }, f_53118_));
            p_53140_.m_36220_(Stats.f_12963_);
            PiglinAi.m_34873_(p_53140_, true);
            return InteractionResult.CONSUME;
         }
      } else {
         return InteractionResult.m_19078_(p_53138_.f_46443_);
      }
   }

   public BlockEntity m_142194_(BlockPos p_153208_, BlockState p_153209_) {
      return new EnderChestBlockEntity(p_153208_, p_153209_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153199_, BlockState p_153200_, BlockEntityType<T> p_153201_) {
      return p_153199_.f_46443_ ? m_152132_(p_153201_, BlockEntityType.f_58920_, EnderChestBlockEntity::m_155517_) : null;
   }

   public void m_7100_(BlockState p_53144_, Level p_53145_, BlockPos p_53146_, Random p_53147_) {
      for(int i = 0; i < 3; ++i) {
         int j = p_53147_.nextInt(2) * 2 - 1;
         int k = p_53147_.nextInt(2) * 2 - 1;
         double d0 = (double)p_53146_.m_123341_() + 0.5D + 0.25D * (double)j;
         double d1 = (double)((float)p_53146_.m_123342_() + p_53147_.nextFloat());
         double d2 = (double)p_53146_.m_123343_() + 0.5D + 0.25D * (double)k;
         double d3 = (double)(p_53147_.nextFloat() * (float)j);
         double d4 = ((double)p_53147_.nextFloat() - 0.5D) * 0.125D;
         double d5 = (double)(p_53147_.nextFloat() * (float)k);
         p_53145_.m_7106_(ParticleTypes.f_123760_, d0, d1, d2, d3, d4, d5);
      }

   }

   public BlockState m_6843_(BlockState p_53157_, Rotation p_53158_) {
      return p_53157_.m_61124_(f_53115_, p_53158_.m_55954_(p_53157_.m_61143_(f_53115_)));
   }

   public BlockState m_6943_(BlockState p_53154_, Mirror p_53155_) {
      return p_53154_.m_60717_(p_53155_.m_54846_(p_53154_.m_61143_(f_53115_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53167_) {
      p_53167_.m_61104_(f_53115_, f_53116_);
   }

   public FluidState m_5888_(BlockState p_53177_) {
      return p_53177_.m_61143_(f_53116_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_53177_);
   }

   public BlockState m_7417_(BlockState p_53160_, Direction p_53161_, BlockState p_53162_, LevelAccessor p_53163_, BlockPos p_53164_, BlockPos p_53165_) {
      if (p_53160_.m_61143_(f_53116_)) {
         p_53163_.m_6217_().m_5945_(p_53164_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53163_));
      }

      return super.m_7417_(p_53160_, p_53161_, p_53162_, p_53163_, p_53164_, p_53165_);
   }

   public boolean m_7357_(BlockState p_53132_, BlockGetter p_53133_, BlockPos p_53134_, PathComputationType p_53135_) {
      return false;
   }

   public void m_7458_(BlockState p_153203_, ServerLevel p_153204_, BlockPos p_153205_, Random p_153206_) {
      BlockEntity blockentity = p_153204_.m_7702_(p_153205_);
      if (blockentity instanceof EnderChestBlockEntity) {
         ((EnderChestBlockEntity)blockentity).m_155524_();
      }

   }
}