package net.minecraft.world.level.block;

import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractCauldronBlock extends Block {
   private static final int f_151938_ = 2;
   private static final int f_151939_ = 4;
   private static final int f_151940_ = 3;
   private static final int f_151941_ = 2;
   protected static final int f_151936_ = 4;
   private static final VoxelShape f_151942_ = m_49796_(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   protected static final VoxelShape f_151937_ = Shapes.m_83113_(Shapes.m_83144_(), Shapes.m_83124_(m_49796_(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), m_49796_(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), f_151942_), BooleanOp.f_82685_);
   private final Map<Item, CauldronInteraction> f_151943_;

   public AbstractCauldronBlock(BlockBehaviour.Properties p_151946_, Map<Item, CauldronInteraction> p_151947_) {
      super(p_151946_);
      this.f_151943_ = p_151947_;
   }

   protected double m_142446_(BlockState p_151948_) {
      return 0.0D;
   }

   protected boolean m_151979_(BlockState p_151980_, BlockPos p_151981_, Entity p_151982_) {
      return p_151982_.m_20186_() < (double)p_151981_.m_123342_() + this.m_142446_(p_151980_) && p_151982_.m_142469_().f_82292_ > (double)p_151981_.m_123342_() + 0.25D;
   }

   public InteractionResult m_6227_(BlockState p_151969_, Level p_151970_, BlockPos p_151971_, Player p_151972_, InteractionHand p_151973_, BlockHitResult p_151974_) {
      ItemStack itemstack = p_151972_.m_21120_(p_151973_);
      CauldronInteraction cauldroninteraction = this.f_151943_.get(itemstack.m_41720_());
      return cauldroninteraction.m_175710_(p_151969_, p_151970_, p_151971_, p_151972_, p_151973_, itemstack);
   }

   public VoxelShape m_5940_(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
      return f_151937_;
   }

   public VoxelShape m_6079_(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
      return f_151942_;
   }

   public boolean m_7278_(BlockState p_151986_) {
      return true;
   }

   public boolean m_7357_(BlockState p_151959_, BlockGetter p_151960_, BlockPos p_151961_, PathComputationType p_151962_) {
      return false;
   }

   public abstract boolean m_142596_(BlockState p_151984_);

   public void m_7458_(BlockState p_151950_, ServerLevel p_151951_, BlockPos p_151952_, Random p_151953_) {
      BlockPos blockpos = PointedDripstoneBlock.m_154055_(p_151951_, p_151952_);
      if (blockpos != null) {
         Fluid fluid = PointedDripstoneBlock.m_154178_(p_151951_, blockpos);
         if (fluid != Fluids.f_76191_ && this.m_142087_(fluid)) {
            this.m_142310_(p_151950_, p_151951_, p_151952_, fluid);
         }

      }
   }

   protected boolean m_142087_(Fluid p_151983_) {
      return false;
   }

   protected void m_142310_(BlockState p_151975_, Level p_151976_, BlockPos p_151977_, Fluid p_151978_) {
   }
}