package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;

public class OfferFlowerGoal extends Goal {
   private static final TargetingConditions f_25663_ = TargetingConditions.m_148353_().m_26883_(6.0D);
   public static final int f_148131_ = 400;
   private final IronGolem f_25664_;
   private Villager f_25665_;
   private int f_25666_;

   public OfferFlowerGoal(IronGolem p_25669_) {
      this.f_25664_ = p_25669_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.f_25664_.f_19853_.m_46461_()) {
         return false;
      } else if (this.f_25664_.m_21187_().nextInt(8000) != 0) {
         return false;
      } else {
         this.f_25665_ = this.f_25664_.f_19853_.m_45963_(Villager.class, f_25663_, this.f_25664_, this.f_25664_.m_20185_(), this.f_25664_.m_20186_(), this.f_25664_.m_20189_(), this.f_25664_.m_142469_().m_82377_(6.0D, 2.0D, 6.0D));
         return this.f_25665_ != null;
      }
   }

   public boolean m_8045_() {
      return this.f_25666_ > 0;
   }

   public void m_8056_() {
      this.f_25666_ = 400;
      this.f_25664_.m_28885_(true);
   }

   public void m_8041_() {
      this.f_25664_.m_28885_(false);
      this.f_25665_ = null;
   }

   public void m_8037_() {
      this.f_25664_.m_21563_().m_24960_(this.f_25665_, 30.0F, 30.0F);
      --this.f_25666_;
   }
}