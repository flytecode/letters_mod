package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class PrepareRamNearestTarget<E extends PathfinderMob> extends Behavior<E> {
   public static final int f_147713_ = 160;
   private final ToIntFunction<E> f_147714_;
   private final int f_147715_;
   private final int f_147716_;
   private final float f_147717_;
   private final TargetingConditions f_147718_;
   private final int f_147719_;
   private final Function<E, SoundEvent> f_147720_;
   private Optional<Long> f_147721_ = Optional.empty();
   private Optional<PrepareRamNearestTarget.RamCandidate> f_147722_ = Optional.empty();

   public PrepareRamNearestTarget(ToIntFunction<E> p_147724_, int p_147725_, int p_147726_, float p_147727_, TargetingConditions p_147728_, int p_147729_, Function<E, SoundEvent> p_147730_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_148202_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148203_, MemoryStatus.VALUE_ABSENT), 160);
      this.f_147714_ = p_147724_;
      this.f_147715_ = p_147725_;
      this.f_147716_ = p_147726_;
      this.f_147717_ = p_147727_;
      this.f_147718_ = p_147728_;
      this.f_147719_ = p_147729_;
      this.f_147720_ = p_147730_;
   }

   protected void m_6735_(ServerLevel p_147736_, PathfinderMob p_147737_, long p_147738_) {
      Brain<?> brain = p_147737_.m_6274_();
      brain.m_21952_(MemoryModuleType.f_148205_).flatMap((p_147750_) -> {
         return p_147750_.stream().filter((p_147789_) -> {
            return this.f_147718_.m_26885_(p_147737_, p_147789_);
         }).findFirst();
      }).ifPresent((p_147778_) -> {
         this.m_147765_(p_147737_, p_147778_);
      });
   }

   protected void m_6732_(ServerLevel p_147762_, E p_147763_, long p_147764_) {
      Brain<?> brain = p_147763_.m_6274_();
      if (!brain.m_21874_(MemoryModuleType.f_148203_)) {
         p_147762_.m_7605_(p_147763_, (byte)59);
         brain.m_21879_(MemoryModuleType.f_148202_, this.f_147714_.applyAsInt(p_147763_));
      }

   }

   protected boolean m_6737_(ServerLevel p_147773_, PathfinderMob p_147774_, long p_147775_) {
      return this.f_147722_.isPresent() && this.f_147722_.get().m_147799_().m_6084_();
   }

   protected void m_6725_(ServerLevel p_147784_, E p_147785_, long p_147786_) {
      if (this.f_147722_.isPresent()) {
         p_147785_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(this.f_147722_.get().m_147797_(), this.f_147717_, 0));
         p_147785_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(this.f_147722_.get().m_147799_(), true));
         boolean flag = !this.f_147722_.get().m_147799_().m_142538_().equals(this.f_147722_.get().m_147798_());
         if (flag) {
            p_147784_.m_7605_(p_147785_, (byte)59);
            p_147785_.m_21573_().m_26573_();
            this.m_147765_(p_147785_, (this.f_147722_.get()).f_147792_);
         } else {
            BlockPos blockpos = p_147785_.m_142538_();
            if (blockpos.equals(this.f_147722_.get().m_147797_())) {
               p_147784_.m_7605_(p_147785_, (byte)58);
               if (!this.f_147721_.isPresent()) {
                  this.f_147721_ = Optional.of(p_147786_);
               }

               if (p_147786_ - this.f_147721_.get() >= (long)this.f_147719_) {
                  p_147785_.m_6274_().m_21879_(MemoryModuleType.f_148203_, this.m_147754_(blockpos, this.f_147722_.get().m_147798_()));
                  p_147784_.m_6269_((Player)null, p_147785_, this.f_147720_.apply(p_147785_), SoundSource.HOSTILE, 1.0F, p_147785_.m_6100_());
                  this.f_147722_ = Optional.empty();
               }
            }
         }

      }
   }

   private Vec3 m_147754_(BlockPos p_147755_, BlockPos p_147756_) {
      double d0 = 0.5D;
      double d1 = 0.5D * (double)Mth.m_14205_((double)(p_147756_.m_123341_() - p_147755_.m_123341_()));
      double d2 = 0.5D * (double)Mth.m_14205_((double)(p_147756_.m_123343_() - p_147755_.m_123343_()));
      return Vec3.m_82539_(p_147756_).m_82520_(d1, 0.0D, d2);
   }

   private Optional<BlockPos> m_147742_(PathfinderMob p_147743_, LivingEntity p_147744_) {
      BlockPos blockpos = p_147744_.m_142538_();
      if (!this.m_147745_(p_147743_, blockpos)) {
         return Optional.empty();
      } else {
         List<BlockPos> list = Lists.newArrayList();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            blockpos$mutableblockpos.m_122190_(blockpos);

            for(int i = 0; i < this.f_147716_; ++i) {
               if (!this.m_147745_(p_147743_, blockpos$mutableblockpos.m_122173_(direction))) {
                  blockpos$mutableblockpos.m_122173_(direction.m_122424_());
                  break;
               }
            }

            if (blockpos$mutableblockpos.m_123333_(blockpos) >= this.f_147715_) {
               list.add(blockpos$mutableblockpos.m_7949_());
            }
         }

         PathNavigation pathnavigation = p_147743_.m_21573_();
         return list.stream().sorted(Comparator.comparingDouble(p_147743_.m_142538_()::m_123331_)).filter((p_147753_) -> {
            Path path = pathnavigation.m_7864_(p_147753_, 0);
            return path != null && path.m_77403_();
         }).findFirst();
      }
   }

   private boolean m_147745_(PathfinderMob p_147746_, BlockPos p_147747_) {
      return p_147746_.m_21573_().m_6342_(p_147747_) && p_147746_.m_21439_(WalkNodeEvaluator.m_77604_(p_147746_.f_19853_, p_147747_.m_122032_())) == 0.0F;
   }

   private void m_147765_(PathfinderMob p_147766_, LivingEntity p_147767_) {
      this.f_147721_ = Optional.empty();
      this.f_147722_ = this.m_147742_(p_147766_, p_147767_).map((p_147741_) -> {
         return new PrepareRamNearestTarget.RamCandidate(p_147741_, p_147767_.m_142538_(), p_147767_);
      });
   }

   public static class RamCandidate {
      private final BlockPos f_147790_;
      private final BlockPos f_147791_;
      final LivingEntity f_147792_;

      public RamCandidate(BlockPos p_147794_, BlockPos p_147795_, LivingEntity p_147796_) {
         this.f_147790_ = p_147794_;
         this.f_147791_ = p_147795_;
         this.f_147792_ = p_147796_;
      }

      public BlockPos m_147797_() {
         return this.f_147790_;
      }

      public BlockPos m_147798_() {
         return this.f_147791_;
      }

      public LivingEntity m_147799_() {
         return this.f_147792_;
      }
   }
}