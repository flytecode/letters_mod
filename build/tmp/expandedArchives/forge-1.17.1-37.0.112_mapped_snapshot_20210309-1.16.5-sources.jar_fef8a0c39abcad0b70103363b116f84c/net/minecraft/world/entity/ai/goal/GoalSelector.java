package net.minecraft.world.entity.ai.goal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalSelector {
   private static final Logger f_25342_ = LogManager.getLogger();
   private static final WrappedGoal f_25343_ = new WrappedGoal(Integer.MAX_VALUE, new Goal() {
      public boolean m_8036_() {
         return false;
      }
   }) {
      public boolean m_7620_() {
         return false;
      }
   };
   private final Map<Goal.Flag, WrappedGoal> f_25344_ = new EnumMap<>(Goal.Flag.class);
   private final Set<WrappedGoal> f_25345_ = Sets.newLinkedHashSet();
   private final Supplier<ProfilerFiller> f_25346_;
   private final EnumSet<Goal.Flag> f_25347_ = EnumSet.noneOf(Goal.Flag.class);
   private int f_148095_;
   private int f_25348_ = 3;

   public GoalSelector(Supplier<ProfilerFiller> p_25351_) {
      this.f_25346_ = p_25351_;
   }

   public void m_25352_(int p_25353_, Goal p_25354_) {
      this.f_25345_.add(new WrappedGoal(p_25353_, p_25354_));
   }

   @VisibleForTesting
   public void m_148096_() {
      this.f_25345_.clear();
   }

   public void m_25363_(Goal p_25364_) {
      this.f_25345_.stream().filter((p_25378_) -> {
         return p_25378_.m_26015_() == p_25364_;
      }).filter(WrappedGoal::m_7620_).forEach(WrappedGoal::m_8041_);
      this.f_25345_.removeIf((p_25367_) -> {
         return p_25367_.m_26015_() == p_25364_;
      });
   }

   public void m_25373_() {
      ProfilerFiller profilerfiller = this.f_25346_.get();
      profilerfiller.m_6180_("goalCleanup");
      this.m_25386_().filter((p_25390_) -> {
         return !p_25390_.m_7620_() || p_25390_.m_7684_().stream().anyMatch(this.f_25347_::contains) || !p_25390_.m_8045_();
      }).forEach(Goal::m_8041_);
      this.f_25344_.forEach((p_25358_, p_25359_) -> {
         if (!p_25359_.m_7620_()) {
            this.f_25344_.remove(p_25358_);
         }

      });
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("goalUpdate");
      this.f_25345_.stream().filter((p_25388_) -> {
         return !p_25388_.m_7620_();
      }).filter((p_25385_) -> {
         return p_25385_.m_7684_().stream().noneMatch(this.f_25347_::contains);
      }).filter((p_25380_) -> {
         return p_25380_.m_7684_().stream().allMatch((p_148104_) -> {
            return this.f_25344_.getOrDefault(p_148104_, f_25343_).m_26002_(p_25380_);
         });
      }).filter(WrappedGoal::m_8036_).forEach((p_25369_) -> {
         p_25369_.m_7684_().forEach((p_148101_) -> {
            WrappedGoal wrappedgoal = this.f_25344_.getOrDefault(p_148101_, f_25343_);
            wrappedgoal.m_8041_();
            this.f_25344_.put(p_148101_, p_25369_);
         });
         p_25369_.m_8056_();
      });
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("goalTick");
      this.m_25386_().forEach(WrappedGoal::m_8037_);
      profilerfiller.m_7238_();
   }

   public Set<WrappedGoal> m_148105_() {
      return this.f_25345_;
   }

   public Stream<WrappedGoal> m_25386_() {
      return this.f_25345_.stream().filter(WrappedGoal::m_7620_);
   }

   public void m_148097_(int p_148098_) {
      this.f_25348_ = p_148098_;
   }

   public void m_25355_(Goal.Flag p_25356_) {
      this.f_25347_.add(p_25356_);
   }

   public void m_25374_(Goal.Flag p_25375_) {
      this.f_25347_.remove(p_25375_);
   }

   public void m_25360_(Goal.Flag p_25361_, boolean p_25362_) {
      if (p_25362_) {
         this.m_25374_(p_25361_);
      } else {
         this.m_25355_(p_25361_);
      }

   }
}