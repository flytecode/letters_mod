package net.minecraft.world.level.block.piston;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MovingPistonBlock extends BaseEntityBlock {
   public static final DirectionProperty f_60046_ = PistonHeadBlock.f_52588_;
   public static final EnumProperty<PistonType> f_60047_ = PistonHeadBlock.f_60235_;

   public MovingPistonBlock(BlockBehaviour.Properties p_60050_) {
      super(p_60050_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_60046_, Direction.NORTH).m_61124_(f_60047_, PistonType.DEFAULT));
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos p_155879_, BlockState p_155880_) {
      return null;
   }

   public static BlockEntity m_155881_(BlockPos p_155882_, BlockState p_155883_, BlockState p_155884_, Direction p_155885_, boolean p_155886_, boolean p_155887_) {
      return new PistonMovingBlockEntity(p_155882_, p_155883_, p_155884_, p_155885_, p_155886_, p_155887_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_155875_, BlockState p_155876_, BlockEntityType<T> p_155877_) {
      return m_152132_(p_155877_, BlockEntityType.f_58926_, PistonMovingBlockEntity::m_155915_);
   }

   public void m_6810_(BlockState p_60077_, Level p_60078_, BlockPos p_60079_, BlockState p_60080_, boolean p_60081_) {
      if (!p_60077_.m_60713_(p_60080_.m_60734_())) {
         BlockEntity blockentity = p_60078_.m_7702_(p_60079_);
         if (blockentity instanceof PistonMovingBlockEntity) {
            ((PistonMovingBlockEntity)blockentity).m_60401_();
         }

      }
   }

   public void m_6786_(LevelAccessor p_60061_, BlockPos p_60062_, BlockState p_60063_) {
      BlockPos blockpos = p_60062_.m_142300_(p_60063_.m_61143_(f_60046_).m_122424_());
      BlockState blockstate = p_60061_.m_8055_(blockpos);
      if (blockstate.m_60734_() instanceof PistonBaseBlock && blockstate.m_61143_(PistonBaseBlock.f_60153_)) {
         p_60061_.m_7471_(blockpos, false);
      }

   }

   public InteractionResult m_6227_(BlockState p_60070_, Level p_60071_, BlockPos p_60072_, Player p_60073_, InteractionHand p_60074_, BlockHitResult p_60075_) {
      if (!p_60071_.f_46443_ && p_60071_.m_7702_(p_60072_) == null) {
         p_60071_.m_7471_(p_60072_, false);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   public List<ItemStack> m_7381_(BlockState p_60089_, LootContext.Builder p_60090_) {
      PistonMovingBlockEntity pistonmovingblockentity = this.m_60053_(p_60090_.m_78962_(), new BlockPos(p_60090_.m_78970_(LootContextParams.f_81460_)));
      return pistonmovingblockentity == null ? Collections.emptyList() : pistonmovingblockentity.m_60400_().m_60724_(p_60090_);
   }

   public VoxelShape m_5940_(BlockState p_60099_, BlockGetter p_60100_, BlockPos p_60101_, CollisionContext p_60102_) {
      return Shapes.m_83040_();
   }

   public VoxelShape m_5939_(BlockState p_60104_, BlockGetter p_60105_, BlockPos p_60106_, CollisionContext p_60107_) {
      PistonMovingBlockEntity pistonmovingblockentity = this.m_60053_(p_60105_, p_60106_);
      return pistonmovingblockentity != null ? pistonmovingblockentity.m_60356_(p_60105_, p_60106_) : Shapes.m_83040_();
   }

   @Nullable
   private PistonMovingBlockEntity m_60053_(BlockGetter p_60054_, BlockPos p_60055_) {
      BlockEntity blockentity = p_60054_.m_7702_(p_60055_);
      return blockentity instanceof PistonMovingBlockEntity ? (PistonMovingBlockEntity)blockentity : null;
   }

   public ItemStack m_7397_(BlockGetter p_60057_, BlockPos p_60058_, BlockState p_60059_) {
      return ItemStack.f_41583_;
   }

   public BlockState m_6843_(BlockState p_60086_, Rotation p_60087_) {
      return p_60086_.m_61124_(f_60046_, p_60087_.m_55954_(p_60086_.m_61143_(f_60046_)));
   }

   public BlockState m_6943_(BlockState p_60083_, Mirror p_60084_) {
      return p_60083_.m_60717_(p_60084_.m_54846_(p_60083_.m_61143_(f_60046_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_60097_) {
      p_60097_.m_61104_(f_60046_, f_60047_);
   }

   public boolean m_7357_(BlockState p_60065_, BlockGetter p_60066_, BlockPos p_60067_, PathComputationType p_60068_) {
      return false;
   }
}