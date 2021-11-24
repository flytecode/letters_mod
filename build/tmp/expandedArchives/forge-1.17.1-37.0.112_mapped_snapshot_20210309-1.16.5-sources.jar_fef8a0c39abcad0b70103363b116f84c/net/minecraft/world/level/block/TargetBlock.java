package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TargetBlock extends Block {
   private static final IntegerProperty f_57376_ = BlockStateProperties.f_61426_;
   private static final int f_154777_ = 20;
   private static final int f_154778_ = 8;

   public TargetBlock(BlockBehaviour.Properties p_57379_) {
      super(p_57379_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57376_, Integer.valueOf(0)));
   }

   public void m_5581_(Level p_57381_, BlockState p_57382_, BlockHitResult p_57383_, Projectile p_57384_) {
      int i = m_57391_(p_57381_, p_57382_, p_57383_, p_57384_);
      Entity entity = p_57384_.m_37282_();
      if (entity instanceof ServerPlayer) {
         ServerPlayer serverplayer = (ServerPlayer)entity;
         serverplayer.m_36220_(Stats.f_12953_);
         CriteriaTriggers.f_10561_.m_70211_(serverplayer, p_57384_, p_57383_.m_82450_(), i);
      }

   }

   private static int m_57391_(LevelAccessor p_57392_, BlockState p_57393_, BlockHitResult p_57394_, Entity p_57395_) {
      int i = m_57408_(p_57394_, p_57394_.m_82450_());
      int j = p_57395_ instanceof AbstractArrow ? 20 : 8;
      if (!p_57392_.m_6219_().m_5916_(p_57394_.m_82425_(), p_57393_.m_60734_())) {
         m_57385_(p_57392_, p_57393_, i, p_57394_.m_82425_(), j);
      }

      return i;
   }

   private static int m_57408_(BlockHitResult p_57409_, Vec3 p_57410_) {
      Direction direction = p_57409_.m_82434_();
      double d0 = Math.abs(Mth.m_14185_(p_57410_.f_82479_) - 0.5D);
      double d1 = Math.abs(Mth.m_14185_(p_57410_.f_82480_) - 0.5D);
      double d2 = Math.abs(Mth.m_14185_(p_57410_.f_82481_) - 0.5D);
      Direction.Axis direction$axis = direction.m_122434_();
      double d3;
      if (direction$axis == Direction.Axis.Y) {
         d3 = Math.max(d0, d2);
      } else if (direction$axis == Direction.Axis.Z) {
         d3 = Math.max(d0, d1);
      } else {
         d3 = Math.max(d1, d2);
      }

      return Math.max(1, Mth.m_14165_(15.0D * Mth.m_14008_((0.5D - d3) / 0.5D, 0.0D, 1.0D)));
   }

   private static void m_57385_(LevelAccessor p_57386_, BlockState p_57387_, int p_57388_, BlockPos p_57389_, int p_57390_) {
      p_57386_.m_7731_(p_57389_, p_57387_.m_61124_(f_57376_, Integer.valueOf(p_57388_)), 3);
      p_57386_.m_6219_().m_5945_(p_57389_, p_57387_.m_60734_(), p_57390_);
   }

   public void m_7458_(BlockState p_57397_, ServerLevel p_57398_, BlockPos p_57399_, Random p_57400_) {
      if (p_57397_.m_61143_(f_57376_) != 0) {
         p_57398_.m_7731_(p_57399_, p_57397_.m_61124_(f_57376_, Integer.valueOf(0)), 3);
      }

   }

   public int m_6378_(BlockState p_57402_, BlockGetter p_57403_, BlockPos p_57404_, Direction p_57405_) {
      return p_57402_.m_61143_(f_57376_);
   }

   public boolean m_7899_(BlockState p_57418_) {
      return true;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57407_) {
      p_57407_.m_61104_(f_57376_);
   }

   public void m_6807_(BlockState p_57412_, Level p_57413_, BlockPos p_57414_, BlockState p_57415_, boolean p_57416_) {
      if (!p_57413_.m_5776_() && !p_57412_.m_60713_(p_57415_.m_60734_())) {
         if (p_57412_.m_61143_(f_57376_) > 0 && !p_57413_.m_6219_().m_5916_(p_57414_, this)) {
            p_57413_.m_7731_(p_57414_, p_57412_.m_61124_(f_57376_, Integer.valueOf(0)), 18);
         }

      }
   }
}