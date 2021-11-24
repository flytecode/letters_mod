package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RotatedPillarBlock extends Block {
   public static final EnumProperty<Direction.Axis> f_55923_ = BlockStateProperties.f_61365_;

   public RotatedPillarBlock(BlockBehaviour.Properties p_55926_) {
      super(p_55926_);
      this.m_49959_(this.m_49966_().m_61124_(f_55923_, Direction.Axis.Y));
   }

   public BlockState m_6843_(BlockState p_55930_, Rotation p_55931_) {
      return m_154376_(p_55930_, p_55931_);
   }

   public static BlockState m_154376_(BlockState p_154377_, Rotation p_154378_) {
      switch(p_154378_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         switch((Direction.Axis)p_154377_.m_61143_(f_55923_)) {
         case X:
            return p_154377_.m_61124_(f_55923_, Direction.Axis.Z);
         case Z:
            return p_154377_.m_61124_(f_55923_, Direction.Axis.X);
         default:
            return p_154377_;
         }
      default:
         return p_154377_;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55933_) {
      p_55933_.m_61104_(f_55923_);
   }

   public BlockState m_5573_(BlockPlaceContext p_55928_) {
      return this.m_49966_().m_61124_(f_55923_, p_55928_.m_43719_().m_122434_());
   }
}