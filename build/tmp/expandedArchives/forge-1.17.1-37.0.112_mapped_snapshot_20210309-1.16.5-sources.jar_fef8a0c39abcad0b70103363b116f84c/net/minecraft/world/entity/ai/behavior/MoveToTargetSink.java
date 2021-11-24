package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveToTargetSink extends Behavior<Mob> {
   private static final int f_147699_ = 40;
   private int f_23567_;
   @Nullable
   private Path f_23568_;
   @Nullable
   private BlockPos f_23569_;
   private float f_23570_;

   public MoveToTargetSink() {
      this(150, 250);
   }

   public MoveToTargetSink(int p_23573_, int p_23574_) {
      super(ImmutableMap.of(MemoryModuleType.f_26326_, MemoryStatus.REGISTERED, MemoryModuleType.f_26377_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26370_, MemoryStatus.VALUE_PRESENT), p_23573_, p_23574_);
   }

   protected boolean m_6114_(ServerLevel p_23583_, Mob p_23584_) {
      if (this.f_23567_ > 0) {
         --this.f_23567_;
         return false;
      } else {
         Brain<?> brain = p_23584_.m_6274_();
         WalkTarget walktarget = brain.m_21952_(MemoryModuleType.f_26370_).get();
         boolean flag = this.m_23589_(p_23584_, walktarget);
         if (!flag && this.m_23592_(p_23584_, walktarget, p_23583_.m_46467_())) {
            this.f_23569_ = walktarget.m_26420_().m_6675_();
            return true;
         } else {
            brain.m_21936_(MemoryModuleType.f_26370_);
            if (flag) {
               brain.m_21936_(MemoryModuleType.f_26326_);
            }

            return false;
         }
      }
   }

   protected boolean m_6737_(ServerLevel p_23586_, Mob p_23587_, long p_23588_) {
      if (this.f_23568_ != null && this.f_23569_ != null) {
         Optional<WalkTarget> optional = p_23587_.m_6274_().m_21952_(MemoryModuleType.f_26370_);
         PathNavigation pathnavigation = p_23587_.m_21573_();
         return !pathnavigation.m_26571_() && optional.isPresent() && !this.m_23589_(p_23587_, optional.get());
      } else {
         return false;
      }
   }

   protected void m_6732_(ServerLevel p_23601_, Mob p_23602_, long p_23603_) {
      if (p_23602_.m_6274_().m_21874_(MemoryModuleType.f_26370_) && !this.m_23589_(p_23602_, p_23602_.m_6274_().m_21952_(MemoryModuleType.f_26370_).get()) && p_23602_.m_21573_().m_26577_()) {
         this.f_23567_ = p_23601_.m_5822_().nextInt(40);
      }

      p_23602_.m_21573_().m_26573_();
      p_23602_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
      p_23602_.m_6274_().m_21936_(MemoryModuleType.f_26377_);
      this.f_23568_ = null;
   }

   protected void m_6735_(ServerLevel p_23609_, Mob p_23610_, long p_23611_) {
      p_23610_.m_6274_().m_21879_(MemoryModuleType.f_26377_, this.f_23568_);
      p_23610_.m_21573_().m_26536_(this.f_23568_, (double)this.f_23570_);
   }

   protected void m_6725_(ServerLevel p_23617_, Mob p_23618_, long p_23619_) {
      Path path = p_23618_.m_21573_().m_26570_();
      Brain<?> brain = p_23618_.m_6274_();
      if (this.f_23568_ != path) {
         this.f_23568_ = path;
         brain.m_21879_(MemoryModuleType.f_26377_, path);
      }

      if (path != null && this.f_23569_ != null) {
         WalkTarget walktarget = brain.m_21952_(MemoryModuleType.f_26370_).get();
         if (walktarget.m_26420_().m_6675_().m_123331_(this.f_23569_) > 4.0D && this.m_23592_(p_23618_, walktarget, p_23617_.m_46467_())) {
            this.f_23569_ = walktarget.m_26420_().m_6675_();
            this.m_6735_(p_23617_, p_23618_, p_23619_);
         }

      }
   }

   private boolean m_23592_(Mob p_23593_, WalkTarget p_23594_, long p_23595_) {
      BlockPos blockpos = p_23594_.m_26420_().m_6675_();
      this.f_23568_ = p_23593_.m_21573_().m_7864_(blockpos, 0);
      this.f_23570_ = p_23594_.m_26421_();
      Brain<?> brain = p_23593_.m_6274_();
      if (this.m_23589_(p_23593_, p_23594_)) {
         brain.m_21936_(MemoryModuleType.f_26326_);
      } else {
         boolean flag = this.f_23568_ != null && this.f_23568_.m_77403_();
         if (flag) {
            brain.m_21936_(MemoryModuleType.f_26326_);
         } else if (!brain.m_21874_(MemoryModuleType.f_26326_)) {
            brain.m_21879_(MemoryModuleType.f_26326_, p_23595_);
         }

         if (this.f_23568_ != null) {
            return true;
         }

         Vec3 vec3 = DefaultRandomPos.m_148412_((PathfinderMob)p_23593_, 10, 7, Vec3.m_82539_(blockpos), (double)((float)Math.PI / 2F));
         if (vec3 != null) {
            this.f_23568_ = p_23593_.m_21573_().m_26524_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0);
            return this.f_23568_ != null;
         }
      }

      return false;
   }

   private boolean m_23589_(Mob p_23590_, WalkTarget p_23591_) {
      return p_23591_.m_26420_().m_6675_().m_123333_(p_23590_.m_142538_()) <= p_23591_.m_26422_();
   }
}