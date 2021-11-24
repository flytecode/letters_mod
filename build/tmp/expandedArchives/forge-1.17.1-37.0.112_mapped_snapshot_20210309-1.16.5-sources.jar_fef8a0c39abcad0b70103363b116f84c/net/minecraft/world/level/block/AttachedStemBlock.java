package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AttachedStemBlock extends BushBlock {
   public static final DirectionProperty f_48830_ = HorizontalDirectionalBlock.f_54117_;
   protected static final float f_152057_ = 2.0F;
   private static final Map<Direction, VoxelShape> f_48832_ = Maps.newEnumMap(ImmutableMap.of(Direction.SOUTH, Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 16.0D), Direction.WEST, Block.m_49796_(0.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D), Direction.NORTH, Block.m_49796_(6.0D, 0.0D, 0.0D, 10.0D, 10.0D, 10.0D), Direction.EAST, Block.m_49796_(6.0D, 0.0D, 6.0D, 16.0D, 10.0D, 10.0D)));
   private final StemGrownBlock f_48831_;
   private final Supplier<Item> f_152058_;

   public AttachedStemBlock(StemGrownBlock p_152060_, Supplier<Item> p_152061_, BlockBehaviour.Properties p_152062_) {
      super(p_152062_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_48830_, Direction.NORTH));
      this.f_48831_ = p_152060_;
      this.f_152058_ = p_152061_;
   }

   public VoxelShape m_5940_(BlockState p_48858_, BlockGetter p_48859_, BlockPos p_48860_, CollisionContext p_48861_) {
      return f_48832_.get(p_48858_.m_61143_(f_48830_));
   }

   public BlockState m_7417_(BlockState p_48848_, Direction p_48849_, BlockState p_48850_, LevelAccessor p_48851_, BlockPos p_48852_, BlockPos p_48853_) {
      return !p_48850_.m_60713_(this.f_48831_) && p_48849_ == p_48848_.m_61143_(f_48830_) ? this.f_48831_.m_7161_().m_49966_().m_61124_(StemBlock.f_57013_, Integer.valueOf(7)) : super.m_7417_(p_48848_, p_48849_, p_48850_, p_48851_, p_48852_, p_48853_);
   }

   protected boolean m_6266_(BlockState p_48863_, BlockGetter p_48864_, BlockPos p_48865_) {
      return p_48863_.m_60713_(Blocks.f_50093_);
   }

   public ItemStack m_7397_(BlockGetter p_48838_, BlockPos p_48839_, BlockState p_48840_) {
      return new ItemStack(this.f_152058_.get());
   }

   public BlockState m_6843_(BlockState p_48845_, Rotation p_48846_) {
      return p_48845_.m_61124_(f_48830_, p_48846_.m_55954_(p_48845_.m_61143_(f_48830_)));
   }

   public BlockState m_6943_(BlockState p_48842_, Mirror p_48843_) {
      return p_48842_.m_60717_(p_48843_.m_54846_(p_48842_.m_61143_(f_48830_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48855_) {
      p_48855_.m_61104_(f_48830_);
   }
}