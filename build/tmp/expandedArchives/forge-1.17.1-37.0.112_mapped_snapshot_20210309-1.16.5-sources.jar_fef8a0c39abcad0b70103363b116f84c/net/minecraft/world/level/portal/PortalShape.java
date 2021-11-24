package net.minecraft.world.level.portal;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class PortalShape {
   private static final int f_164752_ = 2;
   public static final int f_164750_ = 21;
   private static final int f_164753_ = 3;
   public static final int f_164751_ = 21;
   private static final BlockBehaviour.StatePredicate f_77685_ = (p_77720_, p_77721_, p_77722_) -> {
      return p_77720_.isPortalFrame(p_77721_, p_77722_);
   };
   private final LevelAccessor f_77686_;
   private final Direction.Axis f_77687_;
   private final Direction f_77688_;
   private int f_77689_;
   @Nullable
   private BlockPos f_77690_;
   private int f_77691_;
   private final int f_77692_;

   public static Optional<PortalShape> m_77708_(LevelAccessor p_77709_, BlockPos p_77710_, Direction.Axis p_77711_) {
      return m_77712_(p_77709_, p_77710_, (p_77727_) -> {
         return p_77727_.m_77698_() && p_77727_.f_77689_ == 0;
      }, p_77711_);
   }

   public static Optional<PortalShape> m_77712_(LevelAccessor p_77713_, BlockPos p_77714_, Predicate<PortalShape> p_77715_, Direction.Axis p_77716_) {
      Optional<PortalShape> optional = Optional.of(new PortalShape(p_77713_, p_77714_, p_77716_)).filter(p_77715_);
      if (optional.isPresent()) {
         return optional;
      } else {
         Direction.Axis direction$axis = p_77716_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new PortalShape(p_77713_, p_77714_, direction$axis)).filter(p_77715_);
      }
   }

   public PortalShape(LevelAccessor p_77695_, BlockPos p_77696_, Direction.Axis p_77697_) {
      this.f_77686_ = p_77695_;
      this.f_77687_ = p_77697_;
      this.f_77688_ = p_77697_ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.f_77690_ = this.m_77733_(p_77696_);
      if (this.f_77690_ == null) {
         this.f_77690_ = p_77696_;
         this.f_77692_ = 1;
         this.f_77691_ = 1;
      } else {
         this.f_77692_ = this.m_77745_();
         if (this.f_77692_ > 0) {
            this.f_77691_ = this.m_77746_();
         }
      }

   }

   @Nullable
   private BlockPos m_77733_(BlockPos p_77734_) {
      for(int i = Math.max(this.f_77686_.m_141937_(), p_77734_.m_123342_() - 21); p_77734_.m_123342_() > i && m_77717_(this.f_77686_.m_8055_(p_77734_.m_7495_())); p_77734_ = p_77734_.m_7495_()) {
      }

      Direction direction = this.f_77688_.m_122424_();
      int j = this.m_77735_(p_77734_, direction) - 1;
      return j < 0 ? null : p_77734_.m_5484_(direction, j);
   }

   private int m_77745_() {
      int i = this.m_77735_(this.f_77690_, this.f_77688_);
      return i >= 2 && i <= 21 ? i : 0;
   }

   private int m_77735_(BlockPos p_77736_, Direction p_77737_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i <= 21; ++i) {
         blockpos$mutableblockpos.m_122190_(p_77736_).m_122175_(p_77737_, i);
         BlockState blockstate = this.f_77686_.m_8055_(blockpos$mutableblockpos);
         if (!m_77717_(blockstate)) {
            if (f_77685_.m_61035_(blockstate, this.f_77686_, blockpos$mutableblockpos)) {
               return i;
            }
            break;
         }

         BlockState blockstate1 = this.f_77686_.m_8055_(blockpos$mutableblockpos.m_122173_(Direction.DOWN));
         if (!f_77685_.m_61035_(blockstate1, this.f_77686_, blockpos$mutableblockpos)) {
            break;
         }
      }

      return 0;
   }

   private int m_77746_() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = this.m_77728_(blockpos$mutableblockpos);
      return i >= 3 && i <= 21 && this.m_77730_(blockpos$mutableblockpos, i) ? i : 0;
   }

   private boolean m_77730_(BlockPos.MutableBlockPos p_77731_, int p_77732_) {
      for(int i = 0; i < this.f_77692_; ++i) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77731_.m_122190_(this.f_77690_).m_122175_(Direction.UP, p_77732_).m_122175_(this.f_77688_, i);
         if (!f_77685_.m_61035_(this.f_77686_.m_8055_(blockpos$mutableblockpos), this.f_77686_, blockpos$mutableblockpos)) {
            return false;
         }
      }

      return true;
   }

   private int m_77728_(BlockPos.MutableBlockPos p_77729_) {
      for(int i = 0; i < 21; ++i) {
         p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, i).m_122175_(this.f_77688_, -1);
         if (!f_77685_.m_61035_(this.f_77686_.m_8055_(p_77729_), this.f_77686_, p_77729_)) {
            return i;
         }

         p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, i).m_122175_(this.f_77688_, this.f_77692_);
         if (!f_77685_.m_61035_(this.f_77686_.m_8055_(p_77729_), this.f_77686_, p_77729_)) {
            return i;
         }

         for(int j = 0; j < this.f_77692_; ++j) {
            p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, i).m_122175_(this.f_77688_, j);
            BlockState blockstate = this.f_77686_.m_8055_(p_77729_);
            if (!m_77717_(blockstate)) {
               return i;
            }

            if (blockstate.m_60713_(Blocks.f_50142_)) {
               ++this.f_77689_;
            }
         }
      }

      return 21;
   }

   private static boolean m_77717_(BlockState p_77718_) {
      return p_77718_.m_60795_() || p_77718_.m_60620_(BlockTags.f_13076_) || p_77718_.m_60713_(Blocks.f_50142_);
   }

   public boolean m_77698_() {
      return this.f_77690_ != null && this.f_77692_ >= 2 && this.f_77692_ <= 21 && this.f_77691_ >= 3 && this.f_77691_ <= 21;
   }

   public void m_77743_() {
      BlockState blockstate = Blocks.f_50142_.m_49966_().m_61124_(NetherPortalBlock.f_54904_, this.f_77687_);
      BlockPos.m_121940_(this.f_77690_, this.f_77690_.m_5484_(Direction.UP, this.f_77691_ - 1).m_5484_(this.f_77688_, this.f_77692_ - 1)).forEach((p_77725_) -> {
         this.f_77686_.m_7731_(p_77725_, blockstate, 18);
      });
   }

   public boolean m_77744_() {
      return this.m_77698_() && this.f_77689_ == this.f_77692_ * this.f_77691_;
   }

   public static Vec3 m_77738_(BlockUtil.FoundRectangle p_77739_, Direction.Axis p_77740_, Vec3 p_77741_, EntityDimensions p_77742_) {
      double d0 = (double)p_77739_.f_124349_ - (double)p_77742_.f_20377_;
      double d1 = (double)p_77739_.f_124350_ - (double)p_77742_.f_20378_;
      BlockPos blockpos = p_77739_.f_124348_;
      double d2;
      if (d0 > 0.0D) {
         float f = (float)blockpos.m_123304_(p_77740_) + p_77742_.f_20377_ / 2.0F;
         d2 = Mth.m_14008_(Mth.m_14112_(p_77741_.m_82507_(p_77740_) - (double)f, 0.0D, d0), 0.0D, 1.0D);
      } else {
         d2 = 0.5D;
      }

      double d4;
      if (d1 > 0.0D) {
         Direction.Axis direction$axis = Direction.Axis.Y;
         d4 = Mth.m_14008_(Mth.m_14112_(p_77741_.m_82507_(direction$axis) - (double)blockpos.m_123304_(direction$axis), 0.0D, d1), 0.0D, 1.0D);
      } else {
         d4 = 0.0D;
      }

      Direction.Axis direction$axis1 = p_77740_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double d3 = p_77741_.m_82507_(direction$axis1) - ((double)blockpos.m_123304_(direction$axis1) + 0.5D);
      return new Vec3(d2, d4, d3);
   }

   public static PortalInfo m_77699_(ServerLevel p_77700_, BlockUtil.FoundRectangle p_77701_, Direction.Axis p_77702_, Vec3 p_77703_, EntityDimensions p_77704_, Vec3 p_77705_, float p_77706_, float p_77707_) {
      BlockPos blockpos = p_77701_.f_124348_;
      BlockState blockstate = p_77700_.m_8055_(blockpos);
      Direction.Axis direction$axis = blockstate.m_61145_(BlockStateProperties.f_61364_).orElse(Direction.Axis.X);
      double d0 = (double)p_77701_.f_124349_;
      double d1 = (double)p_77701_.f_124350_;
      int i = p_77702_ == direction$axis ? 0 : 90;
      Vec3 vec3 = p_77702_ == direction$axis ? p_77705_ : new Vec3(p_77705_.f_82481_, p_77705_.f_82480_, -p_77705_.f_82479_);
      double d2 = (double)p_77704_.f_20377_ / 2.0D + (d0 - (double)p_77704_.f_20377_) * p_77703_.m_7096_();
      double d3 = (d1 - (double)p_77704_.f_20378_) * p_77703_.m_7098_();
      double d4 = 0.5D + p_77703_.m_7094_();
      boolean flag = direction$axis == Direction.Axis.X;
      Vec3 vec31 = new Vec3((double)blockpos.m_123341_() + (flag ? d2 : d4), (double)blockpos.m_123342_() + d3, (double)blockpos.m_123343_() + (flag ? d4 : d2));
      return new PortalInfo(vec31, vec3, p_77706_ + (float)i, p_77707_);
   }
}
