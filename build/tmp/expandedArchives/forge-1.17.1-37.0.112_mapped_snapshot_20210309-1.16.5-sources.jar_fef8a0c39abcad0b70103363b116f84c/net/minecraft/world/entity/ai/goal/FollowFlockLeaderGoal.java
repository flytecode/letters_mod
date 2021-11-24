package net.minecraft.world.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;

public class FollowFlockLeaderGoal extends Goal {
   private static final int f_148086_ = 200;
   private final AbstractSchoolingFish f_25245_;
   private int f_25246_;
   private int f_25247_;

   public FollowFlockLeaderGoal(AbstractSchoolingFish p_25249_) {
      this.f_25245_ = p_25249_;
      this.f_25247_ = this.m_25251_(p_25249_);
   }

   protected int m_25251_(AbstractSchoolingFish p_25252_) {
      return 200 + p_25252_.m_21187_().nextInt(200) % 20;
   }

   public boolean m_8036_() {
      if (this.f_25245_.m_27543_()) {
         return false;
      } else if (this.f_25245_.m_27540_()) {
         return true;
      } else if (this.f_25247_ > 0) {
         --this.f_25247_;
         return false;
      } else {
         this.f_25247_ = this.m_25251_(this.f_25245_);
         Predicate<AbstractSchoolingFish> predicate = (p_25258_) -> {
            return p_25258_.m_27542_() || !p_25258_.m_27540_();
         };
         List<? extends AbstractSchoolingFish> list = this.f_25245_.f_19853_.m_6443_(this.f_25245_.getClass(), this.f_25245_.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), predicate);
         AbstractSchoolingFish abstractschoolingfish = DataFixUtils.orElse(list.stream().filter(AbstractSchoolingFish::m_27542_).findAny(), this.f_25245_);
         abstractschoolingfish.m_27533_(list.stream().filter((p_25255_) -> {
            return !p_25255_.m_27540_();
         }));
         return this.f_25245_.m_27540_();
      }
   }

   public boolean m_8045_() {
      return this.f_25245_.m_27540_() && this.f_25245_.m_27544_();
   }

   public void m_8056_() {
      this.f_25246_ = 0;
   }

   public void m_8041_() {
      this.f_25245_.m_27541_();
   }

   public void m_8037_() {
      if (--this.f_25246_ <= 0) {
         this.f_25246_ = 10;
         this.f_25245_.m_27545_();
      }
   }
}