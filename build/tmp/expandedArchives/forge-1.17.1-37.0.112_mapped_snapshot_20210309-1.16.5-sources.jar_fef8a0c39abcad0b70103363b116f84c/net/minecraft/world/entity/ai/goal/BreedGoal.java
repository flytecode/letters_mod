package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class BreedGoal extends Goal {
   private static final TargetingConditions f_25116_ = TargetingConditions.m_148353_().m_26883_(8.0D).m_148355_();
   protected final Animal f_25113_;
   private final Class<? extends Animal> f_25117_;
   protected final Level f_25114_;
   protected Animal f_25115_;
   private int f_25118_;
   private final double f_25119_;

   public BreedGoal(Animal p_25122_, double p_25123_) {
      this(p_25122_, p_25123_, p_25122_.getClass());
   }

   public BreedGoal(Animal p_25125_, double p_25126_, Class<? extends Animal> p_25127_) {
      this.f_25113_ = p_25125_;
      this.f_25114_ = p_25125_.f_19853_;
      this.f_25117_ = p_25127_;
      this.f_25119_ = p_25126_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.f_25113_.m_27593_()) {
         return false;
      } else {
         this.f_25115_ = this.m_25132_();
         return this.f_25115_ != null;
      }
   }

   public boolean m_8045_() {
      return this.f_25115_.m_6084_() && this.f_25115_.m_27593_() && this.f_25118_ < 60;
   }

   public void m_8041_() {
      this.f_25115_ = null;
      this.f_25118_ = 0;
   }

   public void m_8037_() {
      this.f_25113_.m_21563_().m_24960_(this.f_25115_, 10.0F, (float)this.f_25113_.m_8132_());
      this.f_25113_.m_21573_().m_5624_(this.f_25115_, this.f_25119_);
      ++this.f_25118_;
      if (this.f_25118_ >= 60 && this.f_25113_.m_20280_(this.f_25115_) < 9.0D) {
         this.m_8026_();
      }

   }

   @Nullable
   private Animal m_25132_() {
      List<? extends Animal> list = this.f_25114_.m_45971_(this.f_25117_, f_25116_, this.f_25113_, this.f_25113_.m_142469_().m_82400_(8.0D));
      double d0 = Double.MAX_VALUE;
      Animal animal = null;

      for(Animal animal1 : list) {
         if (this.f_25113_.m_7848_(animal1) && this.f_25113_.m_20280_(animal1) < d0) {
            animal = animal1;
            d0 = this.f_25113_.m_20280_(animal1);
         }
      }

      return animal;
   }

   protected void m_8026_() {
      this.f_25113_.m_27563_((ServerLevel)this.f_25114_, this.f_25115_);
   }
}