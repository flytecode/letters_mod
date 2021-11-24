package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DaylightDetectorBlock extends BaseEntityBlock {
   public static final IntegerProperty f_52377_ = BlockStateProperties.f_61426_;
   public static final BooleanProperty f_52378_ = BlockStateProperties.f_61441_;
   protected static final VoxelShape f_52379_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);

   public DaylightDetectorBlock(BlockBehaviour.Properties p_52382_) {
      super(p_52382_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52377_, Integer.valueOf(0)).m_61124_(f_52378_, Boolean.valueOf(false)));
   }

   public VoxelShape m_5940_(BlockState p_52402_, BlockGetter p_52403_, BlockPos p_52404_, CollisionContext p_52405_) {
      return f_52379_;
   }

   public boolean m_7923_(BlockState p_52409_) {
      return true;
   }

   public int m_6378_(BlockState p_52386_, BlockGetter p_52387_, BlockPos p_52388_, Direction p_52389_) {
      return p_52386_.m_61143_(f_52377_);
   }

   private static void m_52410_(BlockState p_52411_, Level p_52412_, BlockPos p_52413_) {
      int i = p_52412_.m_45517_(LightLayer.SKY, p_52413_) - p_52412_.m_7445_();
      float f = p_52412_.m_46490_(1.0F);
      boolean flag = p_52411_.m_61143_(f_52378_);
      if (flag) {
         i = 15 - i;
      } else if (i > 0) {
         float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
         f = f + (f1 - f) * 0.2F;
         i = Math.round((float)i * Mth.m_14089_(f));
      }

      i = Mth.m_14045_(i, 0, 15);
      if (p_52411_.m_61143_(f_52377_) != i) {
         p_52412_.m_7731_(p_52413_, p_52411_.m_61124_(f_52377_, Integer.valueOf(i)), 3);
      }

   }

   public InteractionResult m_6227_(BlockState p_52391_, Level p_52392_, BlockPos p_52393_, Player p_52394_, InteractionHand p_52395_, BlockHitResult p_52396_) {
      if (p_52394_.m_36326_()) {
         if (p_52392_.f_46443_) {
            return InteractionResult.SUCCESS;
         } else {
            BlockState blockstate = p_52391_.m_61122_(f_52378_);
            p_52392_.m_7731_(p_52393_, blockstate, 4);
            m_52410_(blockstate, p_52392_, p_52393_);
            return InteractionResult.CONSUME;
         }
      } else {
         return super.m_6227_(p_52391_, p_52392_, p_52393_, p_52394_, p_52395_, p_52396_);
      }
   }

   public RenderShape m_7514_(BlockState p_52400_) {
      return RenderShape.MODEL;
   }

   public boolean m_7899_(BlockState p_52407_) {
      return true;
   }

   public BlockEntity m_142194_(BlockPos p_153118_, BlockState p_153119_) {
      return new DaylightDetectorBlockEntity(p_153118_, p_153119_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153109_, BlockState p_153110_, BlockEntityType<T> p_153111_) {
      return !p_153109_.f_46443_ && p_153109_.m_6042_().m_63935_() ? m_152132_(p_153111_, BlockEntityType.f_58932_, DaylightDetectorBlock::m_153112_) : null;
   }

   private static void m_153112_(Level p_153113_, BlockPos p_153114_, BlockState p_153115_, DaylightDetectorBlockEntity p_153116_) {
      if (p_153113_.m_46467_() % 20L == 0L) {
         m_52410_(p_153115_, p_153113_, p_153114_);
      }

   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52398_) {
      p_52398_.m_61104_(f_52377_, f_52378_);
   }
}