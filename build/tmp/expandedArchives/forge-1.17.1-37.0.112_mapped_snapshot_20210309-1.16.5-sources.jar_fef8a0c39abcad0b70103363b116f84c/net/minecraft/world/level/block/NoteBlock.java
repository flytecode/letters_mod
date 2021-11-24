package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;

public class NoteBlock extends Block {
   public static final EnumProperty<NoteBlockInstrument> f_55011_ = BlockStateProperties.f_61395_;
   public static final BooleanProperty f_55012_ = BlockStateProperties.f_61448_;
   public static final IntegerProperty f_55013_ = BlockStateProperties.f_61424_;

   public NoteBlock(BlockBehaviour.Properties p_55016_) {
      super(p_55016_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55011_, NoteBlockInstrument.HARP).m_61124_(f_55013_, Integer.valueOf(0)).m_61124_(f_55012_, Boolean.valueOf(false)));
   }

   public BlockState m_5573_(BlockPlaceContext p_55018_) {
      return this.m_49966_().m_61124_(f_55011_, NoteBlockInstrument.m_61666_(p_55018_.m_43725_().m_8055_(p_55018_.m_8083_().m_7495_())));
   }

   public BlockState m_7417_(BlockState p_55048_, Direction p_55049_, BlockState p_55050_, LevelAccessor p_55051_, BlockPos p_55052_, BlockPos p_55053_) {
      return p_55049_ == Direction.DOWN ? p_55048_.m_61124_(f_55011_, NoteBlockInstrument.m_61666_(p_55050_)) : super.m_7417_(p_55048_, p_55049_, p_55050_, p_55051_, p_55052_, p_55053_);
   }

   public void m_6861_(BlockState p_55041_, Level p_55042_, BlockPos p_55043_, Block p_55044_, BlockPos p_55045_, boolean p_55046_) {
      boolean flag = p_55042_.m_46753_(p_55043_);
      if (flag != p_55041_.m_61143_(f_55012_)) {
         if (flag) {
            this.m_55019_(p_55042_, p_55043_);
         }

         p_55042_.m_7731_(p_55043_, p_55041_.m_61124_(f_55012_, Boolean.valueOf(flag)), 3);
      }

   }

   private void m_55019_(Level p_55020_, BlockPos p_55021_) {
      if (p_55020_.m_8055_(p_55021_.m_7494_()).m_60795_()) {
         p_55020_.m_7696_(p_55021_, this, 0, 0);
      }

   }

   public InteractionResult m_6227_(BlockState p_55034_, Level p_55035_, BlockPos p_55036_, Player p_55037_, InteractionHand p_55038_, BlockHitResult p_55039_) {
      if (p_55035_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         int _new = net.minecraftforge.common.ForgeHooks.onNoteChange(p_55035_, p_55036_, p_55034_, p_55034_.m_61143_(f_55013_), p_55034_.m_61122_(f_55013_).m_61143_(f_55013_));
         if (_new == -1) return InteractionResult.FAIL;
         p_55034_ = p_55034_.m_61124_(f_55013_, _new);
         p_55035_.m_7731_(p_55036_, p_55034_, 3);
         this.m_55019_(p_55035_, p_55036_);
         p_55037_.m_36220_(Stats.f_12960_);
         return InteractionResult.CONSUME;
      }
   }

   public void m_6256_(BlockState p_55029_, Level p_55030_, BlockPos p_55031_, Player p_55032_) {
      if (!p_55030_.f_46443_) {
         this.m_55019_(p_55030_, p_55031_);
         p_55032_.m_36220_(Stats.f_12959_);
      }
   }

   public boolean m_8133_(BlockState p_55023_, Level p_55024_, BlockPos p_55025_, int p_55026_, int p_55027_) {
      net.minecraftforge.event.world.NoteBlockEvent.Play e = new net.minecraftforge.event.world.NoteBlockEvent.Play(p_55024_, p_55025_, p_55023_, p_55023_.m_61143_(f_55013_), p_55023_.m_61143_(f_55011_));
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(e)) return false;
      p_55023_ = p_55023_.m_61124_(f_55013_, e.getVanillaNoteId()).m_61124_(f_55011_, e.getInstrument());
      int i = p_55023_.m_61143_(f_55013_);
      float f = (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
      p_55024_.m_5594_((Player)null, p_55025_, p_55023_.m_61143_(f_55011_).m_61668_(), SoundSource.RECORDS, 3.0F, f);
      p_55024_.m_7106_(ParticleTypes.f_123758_, (double)p_55025_.m_123341_() + 0.5D, (double)p_55025_.m_123342_() + 1.2D, (double)p_55025_.m_123343_() + 0.5D, (double)i / 24.0D, 0.0D, 0.0D);
      return true;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55055_) {
      p_55055_.m_61104_(f_55011_, f_55012_, f_55013_);
   }
}
