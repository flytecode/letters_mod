package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneTorchBlock extends TorchBlock {
   public static final BooleanProperty f_55674_ = BlockStateProperties.f_61443_;
   private static final Map<BlockGetter, List<RedstoneTorchBlock.Toggle>> f_55675_ = new WeakHashMap<>();
   public static final int f_154325_ = 60;
   public static final int f_154326_ = 8;
   public static final int f_154327_ = 160;
   private static final int f_154328_ = 2;

   public RedstoneTorchBlock(BlockBehaviour.Properties p_55678_) {
      super(p_55678_, DustParticleOptions.f_123656_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55674_, Boolean.valueOf(true)));
   }

   public void m_6807_(BlockState p_55724_, Level p_55725_, BlockPos p_55726_, BlockState p_55727_, boolean p_55728_) {
      for(Direction direction : Direction.values()) {
         p_55725_.m_46672_(p_55726_.m_142300_(direction), this);
      }

   }

   public void m_6810_(BlockState p_55706_, Level p_55707_, BlockPos p_55708_, BlockState p_55709_, boolean p_55710_) {
      if (!p_55710_) {
         for(Direction direction : Direction.values()) {
            p_55707_.m_46672_(p_55708_.m_142300_(direction), this);
         }

      }
   }

   public int m_6378_(BlockState p_55694_, BlockGetter p_55695_, BlockPos p_55696_, Direction p_55697_) {
      return p_55694_.m_61143_(f_55674_) && Direction.UP != p_55697_ ? 15 : 0;
   }

   protected boolean m_6918_(Level p_55681_, BlockPos p_55682_, BlockState p_55683_) {
      return p_55681_.m_46616_(p_55682_.m_7495_(), Direction.DOWN);
   }

   public void m_7458_(BlockState p_55689_, ServerLevel p_55690_, BlockPos p_55691_, Random p_55692_) {
      boolean flag = this.m_6918_(p_55690_, p_55691_, p_55689_);
      List<RedstoneTorchBlock.Toggle> list = f_55675_.get(p_55690_);

      while(list != null && !list.isEmpty() && p_55690_.m_46467_() - (list.get(0)).f_55732_ > 60L) {
         list.remove(0);
      }

      if (p_55689_.m_61143_(f_55674_)) {
         if (flag) {
            p_55690_.m_7731_(p_55691_, p_55689_.m_61124_(f_55674_, Boolean.valueOf(false)), 3);
            if (m_55684_(p_55690_, p_55691_, true)) {
               p_55690_.m_46796_(1502, p_55691_, 0);
               p_55690_.m_6219_().m_5945_(p_55691_, p_55690_.m_8055_(p_55691_).m_60734_(), 160);
            }
         }
      } else if (!flag && !m_55684_(p_55690_, p_55691_, false)) {
         p_55690_.m_7731_(p_55691_, p_55689_.m_61124_(f_55674_, Boolean.valueOf(true)), 3);
      }

   }

   public void m_6861_(BlockState p_55699_, Level p_55700_, BlockPos p_55701_, Block p_55702_, BlockPos p_55703_, boolean p_55704_) {
      if (p_55699_.m_61143_(f_55674_) == this.m_6918_(p_55700_, p_55701_, p_55699_) && !p_55700_.m_6219_().m_5913_(p_55701_, this)) {
         p_55700_.m_6219_().m_5945_(p_55701_, this, 2);
      }

   }

   public int m_6376_(BlockState p_55719_, BlockGetter p_55720_, BlockPos p_55721_, Direction p_55722_) {
      return p_55722_ == Direction.DOWN ? p_55719_.m_60746_(p_55720_, p_55721_, p_55722_) : 0;
   }

   public boolean m_7899_(BlockState p_55730_) {
      return true;
   }

   public void m_7100_(BlockState p_55712_, Level p_55713_, BlockPos p_55714_, Random p_55715_) {
      if (p_55712_.m_61143_(f_55674_)) {
         double d0 = (double)p_55714_.m_123341_() + 0.5D + (p_55715_.nextDouble() - 0.5D) * 0.2D;
         double d1 = (double)p_55714_.m_123342_() + 0.7D + (p_55715_.nextDouble() - 0.5D) * 0.2D;
         double d2 = (double)p_55714_.m_123343_() + 0.5D + (p_55715_.nextDouble() - 0.5D) * 0.2D;
         p_55713_.m_7106_(this.f_57488_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55717_) {
      p_55717_.m_61104_(f_55674_);
   }

   private static boolean m_55684_(Level p_55685_, BlockPos p_55686_, boolean p_55687_) {
      List<RedstoneTorchBlock.Toggle> list = f_55675_.computeIfAbsent(p_55685_, (p_55680_) -> {
         return Lists.newArrayList();
      });
      if (p_55687_) {
         list.add(new RedstoneTorchBlock.Toggle(p_55686_.m_7949_(), p_55685_.m_46467_()));
      }

      int i = 0;

      for(int j = 0; j < list.size(); ++j) {
         RedstoneTorchBlock.Toggle redstonetorchblock$toggle = list.get(j);
         if (redstonetorchblock$toggle.f_55731_.equals(p_55686_)) {
            ++i;
            if (i >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class Toggle {
      final BlockPos f_55731_;
      final long f_55732_;

      public Toggle(BlockPos p_55734_, long p_55735_) {
         this.f_55731_ = p_55734_;
         this.f_55732_ = p_55735_;
      }
   }
}