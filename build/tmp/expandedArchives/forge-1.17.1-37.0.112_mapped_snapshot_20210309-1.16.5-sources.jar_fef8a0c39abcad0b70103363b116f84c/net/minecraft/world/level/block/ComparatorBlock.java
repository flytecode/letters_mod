package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class ComparatorBlock extends DiodeBlock implements EntityBlock {
   public static final EnumProperty<ComparatorMode> f_51854_ = BlockStateProperties.f_61393_;

   public ComparatorBlock(BlockBehaviour.Properties p_51857_) {
      super(p_51857_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_52496_, Boolean.valueOf(false)).m_61124_(f_51854_, ComparatorMode.COMPARE));
   }

   protected int m_6112_(BlockState p_51912_) {
      return 2;
   }

   protected int m_5968_(BlockGetter p_51892_, BlockPos p_51893_, BlockState p_51894_) {
      BlockEntity blockentity = p_51892_.m_7702_(p_51893_);
      return blockentity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)blockentity).m_59182_() : 0;
   }

   private int m_51903_(Level p_51904_, BlockPos p_51905_, BlockState p_51906_) {
      int i = this.m_7312_(p_51904_, p_51905_, p_51906_);
      if (i == 0) {
         return 0;
      } else {
         int j = this.m_52547_(p_51904_, p_51905_, p_51906_);
         if (j > i) {
            return 0;
         } else {
            return p_51906_.m_61143_(f_51854_) == ComparatorMode.SUBTRACT ? i - j : i;
         }
      }
   }

   protected boolean m_7320_(Level p_51861_, BlockPos p_51862_, BlockState p_51863_) {
      int i = this.m_7312_(p_51861_, p_51862_, p_51863_);
      if (i == 0) {
         return false;
      } else {
         int j = this.m_52547_(p_51861_, p_51862_, p_51863_);
         if (i > j) {
            return true;
         } else {
            return i == j && p_51863_.m_61143_(f_51854_) == ComparatorMode.COMPARE;
         }
      }
   }

   protected int m_7312_(Level p_51896_, BlockPos p_51897_, BlockState p_51898_) {
      int i = super.m_7312_(p_51896_, p_51897_, p_51898_);
      Direction direction = p_51898_.m_61143_(f_54117_);
      BlockPos blockpos = p_51897_.m_142300_(direction);
      BlockState blockstate = p_51896_.m_8055_(blockpos);
      if (blockstate.m_60807_()) {
         i = blockstate.m_60674_(p_51896_, blockpos);
      } else if (i < 15 && blockstate.m_60796_(p_51896_, blockpos)) {
         blockpos = blockpos.m_142300_(direction);
         blockstate = p_51896_.m_8055_(blockpos);
         ItemFrame itemframe = this.m_51864_(p_51896_, direction, blockpos);
         int j = Math.max(itemframe == null ? Integer.MIN_VALUE : itemframe.m_31824_(), blockstate.m_60807_() ? blockstate.m_60674_(p_51896_, blockpos) : Integer.MIN_VALUE);
         if (j != Integer.MIN_VALUE) {
            i = j;
         }
      }

      return i;
   }

   @Nullable
   private ItemFrame m_51864_(Level p_51865_, Direction p_51866_, BlockPos p_51867_) {
      List<ItemFrame> list = p_51865_.m_6443_(ItemFrame.class, new AABB((double)p_51867_.m_123341_(), (double)p_51867_.m_123342_(), (double)p_51867_.m_123343_(), (double)(p_51867_.m_123341_() + 1), (double)(p_51867_.m_123342_() + 1), (double)(p_51867_.m_123343_() + 1)), (p_51890_) -> {
         return p_51890_ != null && p_51890_.m_6350_() == p_51866_;
      });
      return list.size() == 1 ? list.get(0) : null;
   }

   public InteractionResult m_6227_(BlockState p_51880_, Level p_51881_, BlockPos p_51882_, Player p_51883_, InteractionHand p_51884_, BlockHitResult p_51885_) {
      if (!p_51883_.m_150110_().f_35938_) {
         return InteractionResult.PASS;
      } else {
         p_51880_ = p_51880_.m_61122_(f_51854_);
         float f = p_51880_.m_61143_(f_51854_) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
         p_51881_.m_5594_(p_51883_, p_51882_, SoundEvents.f_11762_, SoundSource.BLOCKS, 0.3F, f);
         p_51881_.m_7731_(p_51882_, p_51880_, 2);
         this.m_51907_(p_51881_, p_51882_, p_51880_);
         return InteractionResult.m_19078_(p_51881_.f_46443_);
      }
   }

   protected void m_7321_(Level p_51900_, BlockPos p_51901_, BlockState p_51902_) {
      if (!p_51900_.m_6219_().m_5913_(p_51901_, this)) {
         int i = this.m_51903_(p_51900_, p_51901_, p_51902_);
         BlockEntity blockentity = p_51900_.m_7702_(p_51901_);
         int j = blockentity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)blockentity).m_59182_() : 0;
         if (i != j || p_51902_.m_61143_(f_52496_) != this.m_7320_(p_51900_, p_51901_, p_51902_)) {
            TickPriority tickpriority = this.m_52573_(p_51900_, p_51901_, p_51902_) ? TickPriority.HIGH : TickPriority.NORMAL;
            p_51900_.m_6219_().m_7663_(p_51901_, this, 2, tickpriority);
         }

      }
   }

   private void m_51907_(Level p_51908_, BlockPos p_51909_, BlockState p_51910_) {
      int i = this.m_51903_(p_51908_, p_51909_, p_51910_);
      BlockEntity blockentity = p_51908_.m_7702_(p_51909_);
      int j = 0;
      if (blockentity instanceof ComparatorBlockEntity) {
         ComparatorBlockEntity comparatorblockentity = (ComparatorBlockEntity)blockentity;
         j = comparatorblockentity.m_59182_();
         comparatorblockentity.m_59175_(i);
      }

      if (j != i || p_51910_.m_61143_(f_51854_) == ComparatorMode.COMPARE) {
         boolean flag1 = this.m_7320_(p_51908_, p_51909_, p_51910_);
         boolean flag = p_51910_.m_61143_(f_52496_);
         if (flag && !flag1) {
            p_51908_.m_7731_(p_51909_, p_51910_.m_61124_(f_52496_, Boolean.valueOf(false)), 2);
         } else if (!flag && flag1) {
            p_51908_.m_7731_(p_51909_, p_51910_.m_61124_(f_52496_, Boolean.valueOf(true)), 2);
         }

         this.m_52580_(p_51908_, p_51909_, p_51910_);
      }

   }

   public void m_7458_(BlockState p_51869_, ServerLevel p_51870_, BlockPos p_51871_, Random p_51872_) {
      this.m_51907_(p_51870_, p_51871_, p_51869_);
   }

   public boolean m_8133_(BlockState p_51874_, Level p_51875_, BlockPos p_51876_, int p_51877_, int p_51878_) {
      super.m_8133_(p_51874_, p_51875_, p_51876_, p_51877_, p_51878_);
      BlockEntity blockentity = p_51875_.m_7702_(p_51876_);
      return blockentity != null && blockentity.m_7531_(p_51877_, p_51878_);
   }

   public BlockEntity m_142194_(BlockPos p_153086_, BlockState p_153087_) {
      return new ComparatorBlockEntity(p_153086_, p_153087_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51887_) {
      p_51887_.m_61104_(f_54117_, f_51854_, f_52496_);
   }

   @Override
   public boolean getWeakChanges(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
      return state.m_60713_(Blocks.f_50328_);
   }

   @Override
   public void onNeighborChange(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, BlockPos neighbor) {
      if (pos.m_123342_() == neighbor.m_123342_() && world instanceof Level && !((Level)world).m_5776_()) {
         state.m_60690_((Level)world, pos, world.m_8055_(neighbor).m_60734_(), neighbor, false);
      }
   }
}
