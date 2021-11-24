package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkullBlock extends AbstractSkullBlock {
   public static final int f_154563_ = 15;
   private static final int f_154564_ = 16;
   public static final IntegerProperty f_56314_ = BlockStateProperties.f_61390_;
   protected static final VoxelShape f_56315_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

   public SkullBlock(SkullBlock.Type p_56318_, BlockBehaviour.Properties p_56319_) {
      super(p_56318_, p_56319_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56314_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_56331_, BlockGetter p_56332_, BlockPos p_56333_, CollisionContext p_56334_) {
      return f_56315_;
   }

   public VoxelShape m_7952_(BlockState p_56336_, BlockGetter p_56337_, BlockPos p_56338_) {
      return Shapes.m_83040_();
   }

   public BlockState m_5573_(BlockPlaceContext p_56321_) {
      return this.m_49966_().m_61124_(f_56314_, Integer.valueOf(Mth.m_14107_((double)(p_56321_.m_7074_() * 16.0F / 360.0F) + 0.5D) & 15));
   }

   public BlockState m_6843_(BlockState p_56326_, Rotation p_56327_) {
      return p_56326_.m_61124_(f_56314_, Integer.valueOf(p_56327_.m_55949_(p_56326_.m_61143_(f_56314_), 16)));
   }

   public BlockState m_6943_(BlockState p_56323_, Mirror p_56324_) {
      return p_56323_.m_61124_(f_56314_, Integer.valueOf(p_56324_.m_54843_(p_56323_.m_61143_(f_56314_), 16)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56329_) {
      p_56329_.m_61104_(f_56314_);
   }

   public interface Type {
   }

   public static enum Types implements SkullBlock.Type {
      SKELETON,
      WITHER_SKELETON,
      PLAYER,
      ZOMBIE,
      CREEPER,
      DRAGON;
   }
}