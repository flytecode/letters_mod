package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DeadBushBlock extends BushBlock implements net.minecraftforge.common.IForgeShearable {
   protected static final float f_153120_ = 6.0F;
   protected static final VoxelShape f_52414_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public DeadBushBlock(BlockBehaviour.Properties p_52417_) {
      super(p_52417_);
   }

   public VoxelShape m_5940_(BlockState p_52419_, BlockGetter p_52420_, BlockPos p_52421_, CollisionContext p_52422_) {
      return f_52414_;
   }

   protected boolean m_6266_(BlockState p_52424_, BlockGetter p_52425_, BlockPos p_52426_) {
      return p_52424_.m_60713_(Blocks.f_49992_) || p_52424_.m_60713_(Blocks.f_49993_) || p_52424_.m_60713_(Blocks.f_50352_) || p_52424_.m_60713_(Blocks.f_50287_) || p_52424_.m_60713_(Blocks.f_50288_) || p_52424_.m_60713_(Blocks.f_50289_) || p_52424_.m_60713_(Blocks.f_50290_) || p_52424_.m_60713_(Blocks.f_50291_) || p_52424_.m_60713_(Blocks.f_50292_) || p_52424_.m_60713_(Blocks.f_50293_) || p_52424_.m_60713_(Blocks.f_50294_) || p_52424_.m_60713_(Blocks.f_50295_) || p_52424_.m_60713_(Blocks.f_50296_) || p_52424_.m_60713_(Blocks.f_50297_) || p_52424_.m_60713_(Blocks.f_50298_) || p_52424_.m_60713_(Blocks.f_50299_) || p_52424_.m_60713_(Blocks.f_50300_) || p_52424_.m_60713_(Blocks.f_50301_) || p_52424_.m_60713_(Blocks.f_50302_) || p_52424_.m_60620_(BlockTags.f_144274_);
   }
}
