package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.WeighedRandom;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LongJumpToRandomPos<E extends Mob> extends Behavior<E> {
   private static final int f_147623_ = 20;
   private static final int f_147624_ = 40;
   private static final int f_147625_ = 8;
   public static final int f_147622_ = 200;
   private final UniformInt f_147626_;
   private final int f_147627_;
   private final int f_147628_;
   private final float f_147629_;
   private final List<LongJumpToRandomPos.PossibleJump> f_147630_ = new ArrayList<>();
   private Optional<Vec3> f_147631_ = Optional.empty();
   private Optional<LongJumpToRandomPos.PossibleJump> f_147632_ = Optional.empty();
   private int f_147633_;
   private long f_147634_;
   private Function<E, SoundEvent> f_147635_;

   public LongJumpToRandomPos(UniformInt p_147637_, int p_147638_, int p_147639_, float p_147640_, Function<E, SoundEvent> p_147641_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_148199_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148200_, MemoryStatus.VALUE_ABSENT), 200);
      this.f_147626_ = p_147637_;
      this.f_147627_ = p_147638_;
      this.f_147628_ = p_147639_;
      this.f_147629_ = p_147640_;
      this.f_147635_ = p_147641_;
   }

   protected boolean m_6114_(ServerLevel p_147650_, Mob p_147651_) {
      return p_147651_.m_20096_() && !p_147650_.m_8055_(p_147651_.m_142538_()).m_60713_(Blocks.f_50719_);
   }

   protected boolean m_6737_(ServerLevel p_147653_, Mob p_147654_, long p_147655_) {
      boolean flag = this.f_147631_.isPresent() && this.f_147631_.get().equals(p_147654_.m_20182_()) && this.f_147633_ > 0 && (this.f_147632_.isPresent() || !this.f_147630_.isEmpty());
      if (!flag && !p_147654_.m_6274_().m_21952_(MemoryModuleType.f_148200_).isPresent()) {
         p_147654_.m_6274_().m_21879_(MemoryModuleType.f_148199_, this.f_147626_.m_142270_(p_147653_.f_46441_) / 2);
      }

      return flag;
   }

   protected void m_6735_(ServerLevel p_147676_, Mob p_147677_, long p_147678_) {
      this.f_147632_ = Optional.empty();
      this.f_147633_ = 20;
      this.f_147630_.clear();
      this.f_147631_ = Optional.of(p_147677_.m_20182_());
      BlockPos blockpos = p_147677_.m_142538_();
      int i = blockpos.m_123341_();
      int j = blockpos.m_123342_();
      int k = blockpos.m_123343_();
      Iterable<BlockPos> iterable = BlockPos.m_121976_(i - this.f_147628_, j - this.f_147627_, k - this.f_147628_, i + this.f_147628_, j + this.f_147627_, k + this.f_147628_);
      PathNavigation pathnavigation = p_147677_.m_21573_();

      for(BlockPos blockpos1 : iterable) {
         double d0 = blockpos1.m_123331_(blockpos);
         if ((i != blockpos1.m_123341_() || k != blockpos1.m_123343_()) && pathnavigation.m_6342_(blockpos1) && p_147677_.m_21439_(WalkNodeEvaluator.m_77604_(p_147677_.f_19853_, blockpos1.m_122032_())) == 0.0F) {
            Optional<Vec3> optional = this.m_147656_(p_147677_, Vec3.m_82512_(blockpos1));
            optional.ifPresent((p_147670_) -> {
               this.f_147630_.add(new LongJumpToRandomPos.PossibleJump(new BlockPos(blockpos1), p_147670_, Mth.m_14165_(d0)));
            });
         }
      }

   }

   protected void m_6725_(ServerLevel p_147680_, E p_147681_, long p_147682_) {
      if (this.f_147632_.isPresent()) {
         if (p_147682_ - this.f_147634_ >= 40L) {
            p_147681_.m_146922_(p_147681_.f_20883_);
            p_147681_.m_147244_(true);
            Vec3 vec3 = this.f_147632_.get().m_147694_();
            double d0 = vec3.m_82553_();
            double d1 = d0 + p_147681_.m_182332_();
            p_147681_.m_20256_(vec3.m_82490_(d1 / d0));
            p_147681_.m_6274_().m_21879_(MemoryModuleType.f_148200_, true);
            p_147680_.m_6269_((Player)null, p_147681_, this.f_147635_.apply(p_147681_), SoundSource.NEUTRAL, 1.0F, 1.0F);
         }
      } else {
         --this.f_147633_;
         Optional<LongJumpToRandomPos.PossibleJump> optional = WeighedRandom.m_145034_(p_147680_.f_46441_, this.f_147630_);
         if (optional.isPresent()) {
            this.f_147630_.remove(optional.get());
            p_147681_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(optional.get().m_147693_()));
            PathNavigation pathnavigation = p_147681_.m_21573_();
            Path path = pathnavigation.m_148218_(optional.get().m_147693_(), 0, 8);
            if (path == null || !path.m_77403_()) {
               this.f_147632_ = optional;
               this.f_147634_ = p_147682_;
            }
         }
      }

   }

   private Optional<Vec3> m_147656_(Mob p_147657_, Vec3 p_147658_) {
      Optional<Vec3> optional = Optional.empty();

      for(int i = 65; i < 85; i += 5) {
         Optional<Vec3> optional1 = this.m_147659_(p_147657_, p_147658_, i);
         if (!optional.isPresent() || optional1.isPresent() && optional1.get().m_82556_() < optional.get().m_82556_()) {
            optional = optional1;
         }
      }

      return optional;
   }

   private Optional<Vec3> m_147659_(Mob p_147660_, Vec3 p_147661_, int p_147662_) {
      Vec3 vec3 = p_147660_.m_20182_();
      Vec3 vec31 = (new Vec3(p_147661_.f_82479_ - vec3.f_82479_, 0.0D, p_147661_.f_82481_ - vec3.f_82481_)).m_82541_().m_82490_(0.5D);
      p_147661_ = p_147661_.m_82546_(vec31);
      Vec3 vec32 = p_147661_.m_82546_(vec3);
      float f = (float)p_147662_ * (float)Math.PI / 180.0F;
      double d0 = Math.atan2(vec32.f_82481_, vec32.f_82479_);
      double d1 = vec32.m_82492_(0.0D, vec32.f_82480_, 0.0D).m_82556_();
      double d2 = Math.sqrt(d1);
      double d3 = vec32.f_82480_;
      double d4 = Math.sin((double)(2.0F * f));
      double d5 = 0.08D;
      double d6 = Math.pow(Math.cos((double)f), 2.0D);
      double d7 = Math.sin((double)f);
      double d8 = Math.cos((double)f);
      double d9 = Math.sin(d0);
      double d10 = Math.cos(d0);
      double d11 = d1 * 0.08D / (d2 * d4 - 2.0D * d3 * d6);
      if (d11 < 0.0D) {
         return Optional.empty();
      } else {
         double d12 = Math.sqrt(d11);
         if (d12 > (double)this.f_147629_) {
            return Optional.empty();
         } else {
            double d13 = d12 * d8;
            double d14 = d12 * d7;
            int i = Mth.m_14165_(d2 / d13) * 2;
            double d15 = 0.0D;
            Vec3 vec33 = null;

            for(int j = 0; j < i - 1; ++j) {
               d15 += d2 / (double)i;
               double d16 = d7 / d8 * d15 - Math.pow(d15, 2.0D) * 0.08D / (2.0D * d11 * Math.pow(d8, 2.0D));
               double d17 = d15 * d10;
               double d18 = d15 * d9;
               Vec3 vec34 = new Vec3(vec3.f_82479_ + d17, vec3.f_82480_ + d16, vec3.f_82481_ + d18);
               if (vec33 != null && !this.m_147663_(p_147660_, vec33, vec34)) {
                  return Optional.empty();
               }

               vec33 = vec34;
            }

            return Optional.of((new Vec3(d13 * d10, d14, d13 * d9)).m_82490_((double)0.95F));
         }
      }
   }

   private boolean m_147663_(Mob p_147664_, Vec3 p_147665_, Vec3 p_147666_) {
      EntityDimensions entitydimensions = p_147664_.m_6972_(Pose.LONG_JUMPING);
      Vec3 vec3 = p_147666_.m_82546_(p_147665_);
      double d0 = (double)Math.min(entitydimensions.f_20377_, entitydimensions.f_20378_);
      int i = Mth.m_14165_(vec3.m_82553_() / d0);
      Vec3 vec31 = vec3.m_82541_();
      Vec3 vec32 = p_147665_;

      for(int j = 0; j < i; ++j) {
         vec32 = j == i - 1 ? p_147666_ : vec32.m_82549_(vec31.m_82490_(d0 * (double)0.9F));
         AABB aabb = entitydimensions.m_20393_(vec32);
         if (!p_147664_.f_19853_.m_45756_(p_147664_, aabb)) {
            return false;
         }
      }

      return true;
   }

   public static class PossibleJump extends WeighedRandom.WeighedRandomItem {
      private final BlockPos f_147687_;
      private final Vec3 f_147688_;

      public PossibleJump(BlockPos p_147690_, Vec3 p_147691_, int p_147692_) {
         super(p_147692_);
         this.f_147687_ = p_147690_;
         this.f_147688_ = p_147691_;
      }

      public BlockPos m_147693_() {
         return this.f_147687_;
      }

      public Vec3 m_147694_() {
         return this.f_147688_;
      }
   }
}