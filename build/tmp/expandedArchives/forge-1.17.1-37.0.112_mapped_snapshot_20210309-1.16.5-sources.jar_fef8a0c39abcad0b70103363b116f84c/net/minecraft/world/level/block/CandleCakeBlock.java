package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleCakeBlock extends AbstractCandleBlock {
   public static final BooleanProperty f_152850_ = AbstractCandleBlock.f_151895_;
   protected static final float f_152851_ = 1.0F;
   protected static final VoxelShape f_152852_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
   protected static final VoxelShape f_152853_ = Block.m_49796_(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
   protected static final VoxelShape f_152854_ = Shapes.m_83110_(f_152852_, f_152853_);
   private static final Map<Block, CandleCakeBlock> f_152855_ = Maps.newHashMap();
   private static final Iterable<Vec3> f_152856_ = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

   public CandleCakeBlock(Block p_152859_, BlockBehaviour.Properties p_152860_) {
      super(p_152860_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_152850_, Boolean.valueOf(false)));
      f_152855_.put(p_152859_, this);
   }

   protected Iterable<Vec3> m_142199_(BlockState p_152868_) {
      return f_152856_;
   }

   public VoxelShape m_5940_(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_, CollisionContext p_152878_) {
      return f_152854_;
   }

   public InteractionResult m_6227_(BlockState p_152884_, Level p_152885_, BlockPos p_152886_, Player p_152887_, InteractionHand p_152888_, BlockHitResult p_152889_) {
      ItemStack itemstack = p_152887_.m_21120_(p_152888_);
      if (!itemstack.m_150930_(Items.f_42409_) && !itemstack.m_150930_(Items.f_42613_)) {
         if (m_152906_(p_152889_) && p_152887_.m_21120_(p_152888_).m_41619_() && p_152884_.m_61143_(f_152850_)) {
            m_151899_(p_152887_, p_152884_, p_152885_, p_152886_);
            return InteractionResult.m_19078_(p_152885_.f_46443_);
         } else {
            InteractionResult interactionresult = CakeBlock.m_51185_(p_152885_, p_152886_, Blocks.f_50145_.m_49966_(), p_152887_);
            if (interactionresult.m_19077_()) {
               m_49950_(p_152884_, p_152885_, p_152886_);
            }

            return interactionresult;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   private static boolean m_152906_(BlockHitResult p_152907_) {
      return p_152907_.m_82450_().f_82480_ - (double)p_152907_.m_82425_().m_123342_() > 0.5D;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152905_) {
      p_152905_.m_61104_(f_152850_);
   }

   public ItemStack m_7397_(BlockGetter p_152862_, BlockPos p_152863_, BlockState p_152864_) {
      return new ItemStack(Blocks.f_50145_);
   }

   public BlockState m_7417_(BlockState p_152898_, Direction p_152899_, BlockState p_152900_, LevelAccessor p_152901_, BlockPos p_152902_, BlockPos p_152903_) {
      return p_152899_ == Direction.DOWN && !p_152898_.m_60710_(p_152901_, p_152902_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_152898_, p_152899_, p_152900_, p_152901_, p_152902_, p_152903_);
   }

   public boolean m_7898_(BlockState p_152891_, LevelReader p_152892_, BlockPos p_152893_) {
      return p_152892_.m_8055_(p_152893_.m_7495_()).m_60767_().m_76333_();
   }

   public int m_6782_(BlockState p_152880_, Level p_152881_, BlockPos p_152882_) {
      return CakeBlock.f_152743_;
   }

   public boolean m_7278_(BlockState p_152909_) {
      return true;
   }

   public boolean m_7357_(BlockState p_152870_, BlockGetter p_152871_, BlockPos p_152872_, PathComputationType p_152873_) {
      return false;
   }

   public static BlockState m_152865_(Block p_152866_) {
      return f_152855_.get(p_152866_).m_49966_();
   }

   public static boolean m_152910_(BlockState p_152911_) {
      return p_152911_.m_60622_(BlockTags.f_144268_, (p_152896_) -> {
         return p_152896_.m_61138_(f_152850_) && !p_152911_.m_61143_(f_152850_);
      });
   }
}