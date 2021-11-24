package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WeightedPressurePlateBlock extends BasePressurePlateBlock {
   public static final IntegerProperty f_58198_ = BlockStateProperties.f_61426_;
   private final int f_58199_;

   public WeightedPressurePlateBlock(int p_58202_, BlockBehaviour.Properties p_58203_) {
      super(p_58203_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_58198_, Integer.valueOf(0)));
      this.f_58199_ = p_58202_;
   }

   protected int m_6693_(Level p_58213_, BlockPos p_58214_) {
      int i = Math.min(p_58213_.m_45976_(Entity.class, f_49287_.m_82338_(p_58214_)).size(), this.f_58199_);
      if (i > 0) {
         float f = (float)Math.min(this.f_58199_, i) / (float)this.f_58199_;
         return Mth.m_14167_(f * 15.0F);
      } else {
         return 0;
      }
   }

   protected void m_5494_(LevelAccessor p_58205_, BlockPos p_58206_) {
      p_58205_.m_5594_((Player)null, p_58206_, SoundEvents.f_12067_, SoundSource.BLOCKS, 0.3F, 0.90000004F);
   }

   protected void m_5493_(LevelAccessor p_58216_, BlockPos p_58217_) {
      p_58216_.m_5594_((Player)null, p_58217_, SoundEvents.f_12066_, SoundSource.BLOCKS, 0.3F, 0.75F);
   }

   protected int m_6016_(BlockState p_58220_) {
      return p_58220_.m_61143_(f_58198_);
   }

   protected BlockState m_7422_(BlockState p_58208_, int p_58209_) {
      return p_58208_.m_61124_(f_58198_, Integer.valueOf(p_58209_));
   }

   protected int m_7342_() {
      return 10;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58211_) {
      p_58211_.m_61104_(f_58198_);
   }
}