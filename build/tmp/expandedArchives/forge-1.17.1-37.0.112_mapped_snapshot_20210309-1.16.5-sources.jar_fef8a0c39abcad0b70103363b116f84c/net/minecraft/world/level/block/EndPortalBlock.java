package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EndPortalBlock extends BaseEntityBlock {
   protected static final VoxelShape f_53014_ = Block.m_49796_(0.0D, 6.0D, 0.0D, 16.0D, 12.0D, 16.0D);

   public EndPortalBlock(BlockBehaviour.Properties p_53017_) {
      super(p_53017_);
   }

   public BlockEntity m_142194_(BlockPos p_153196_, BlockState p_153197_) {
      return new TheEndPortalBlockEntity(p_153196_, p_153197_);
   }

   public VoxelShape m_5940_(BlockState p_53038_, BlockGetter p_53039_, BlockPos p_53040_, CollisionContext p_53041_) {
      return f_53014_;
   }

   public void m_7892_(BlockState p_53025_, Level p_53026_, BlockPos p_53027_, Entity p_53028_) {
      if (p_53026_ instanceof ServerLevel && !p_53028_.m_20159_() && !p_53028_.m_20160_() && p_53028_.m_6072_() && Shapes.m_83157_(Shapes.m_83064_(p_53028_.m_142469_().m_82386_((double)(-p_53027_.m_123341_()), (double)(-p_53027_.m_123342_()), (double)(-p_53027_.m_123343_()))), p_53025_.m_60808_(p_53026_, p_53027_), BooleanOp.f_82689_)) {
         ResourceKey<Level> resourcekey = p_53026_.m_46472_() == Level.f_46430_ ? Level.f_46428_ : Level.f_46430_;
         ServerLevel serverlevel = ((ServerLevel)p_53026_).m_142572_().m_129880_(resourcekey);
         if (serverlevel == null) {
            return;
         }

         p_53028_.m_5489_(serverlevel);
      }

   }

   public void m_7100_(BlockState p_53030_, Level p_53031_, BlockPos p_53032_, Random p_53033_) {
      double d0 = (double)p_53032_.m_123341_() + p_53033_.nextDouble();
      double d1 = (double)p_53032_.m_123342_() + 0.8D;
      double d2 = (double)p_53032_.m_123343_() + p_53033_.nextDouble();
      p_53031_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }

   public ItemStack m_7397_(BlockGetter p_53021_, BlockPos p_53022_, BlockState p_53023_) {
      return ItemStack.f_41583_;
   }

   public boolean m_5946_(BlockState p_53035_, Fluid p_53036_) {
      return false;
   }
}