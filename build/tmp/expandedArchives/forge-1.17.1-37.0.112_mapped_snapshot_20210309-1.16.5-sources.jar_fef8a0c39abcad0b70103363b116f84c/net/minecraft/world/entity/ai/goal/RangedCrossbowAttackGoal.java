package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RangedCrossbowAttackGoal<T extends Monster & RangedAttackMob & CrossbowAttackMob> extends Goal {
   public static final UniformInt f_25804_ = TimeUtil.m_145020_(1, 2);
   private final T f_25805_;
   private RangedCrossbowAttackGoal.CrossbowState f_25806_ = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
   private final double f_25807_;
   private final float f_25808_;
   private int f_25809_;
   private int f_25810_;
   private int f_25811_;

   public RangedCrossbowAttackGoal(T p_25814_, double p_25815_, float p_25816_) {
      this.f_25805_ = p_25814_;
      this.f_25807_ = p_25815_;
      this.f_25808_ = p_25816_ * p_25816_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.m_25822_() && this.m_25821_();
   }

   private boolean m_25821_() {
      return this.f_25805_.m_21093_(is -> is.m_41720_() instanceof CrossbowItem);
   }

   public boolean m_8045_() {
      return this.m_25822_() && (this.m_8036_() || !this.f_25805_.m_21573_().m_26571_()) && this.m_25821_();
   }

   private boolean m_25822_() {
      return this.f_25805_.m_5448_() != null && this.f_25805_.m_5448_().m_6084_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.f_25805_.m_21561_(false);
      this.f_25805_.m_6710_((LivingEntity)null);
      this.f_25809_ = 0;
      if (this.f_25805_.m_6117_()) {
         this.f_25805_.m_5810_();
         this.f_25805_.m_6136_(false);
         CrossbowItem.m_40884_(this.f_25805_.m_21211_(), false);
      }

   }

   public void m_8037_() {
      LivingEntity livingentity = this.f_25805_.m_5448_();
      if (livingentity != null) {
         boolean flag = this.f_25805_.m_21574_().m_148306_(livingentity);
         boolean flag1 = this.f_25809_ > 0;
         if (flag != flag1) {
            this.f_25809_ = 0;
         }

         if (flag) {
            ++this.f_25809_;
         } else {
            --this.f_25809_;
         }

         double d0 = this.f_25805_.m_20280_(livingentity);
         boolean flag2 = (d0 > (double)this.f_25808_ || this.f_25809_ < 5) && this.f_25810_ == 0;
         if (flag2) {
            --this.f_25811_;
            if (this.f_25811_ <= 0) {
               this.f_25805_.m_21573_().m_5624_(livingentity, this.m_25823_() ? this.f_25807_ : this.f_25807_ * 0.5D);
               this.f_25811_ = f_25804_.m_142270_(this.f_25805_.m_21187_());
            }
         } else {
            this.f_25811_ = 0;
            this.f_25805_.m_21573_().m_26573_();
         }

         this.f_25805_.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         if (this.f_25806_ == RangedCrossbowAttackGoal.CrossbowState.UNCHARGED) {
            if (!flag2) {
               this.f_25805_.m_6672_(ProjectileUtil.getWeaponHoldingHand(this.f_25805_, item -> item instanceof CrossbowItem));
               this.f_25806_ = RangedCrossbowAttackGoal.CrossbowState.CHARGING;
               this.f_25805_.m_6136_(true);
            }
         } else if (this.f_25806_ == RangedCrossbowAttackGoal.CrossbowState.CHARGING) {
            if (!this.f_25805_.m_6117_()) {
               this.f_25806_ = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
            }

            int i = this.f_25805_.m_21252_();
            ItemStack itemstack = this.f_25805_.m_21211_();
            if (i >= CrossbowItem.m_40939_(itemstack)) {
               this.f_25805_.m_21253_();
               this.f_25806_ = RangedCrossbowAttackGoal.CrossbowState.CHARGED;
               this.f_25810_ = 20 + this.f_25805_.m_21187_().nextInt(20);
               this.f_25805_.m_6136_(false);
            }
         } else if (this.f_25806_ == RangedCrossbowAttackGoal.CrossbowState.CHARGED) {
            --this.f_25810_;
            if (this.f_25810_ == 0) {
               this.f_25806_ = RangedCrossbowAttackGoal.CrossbowState.READY_TO_ATTACK;
            }
         } else if (this.f_25806_ == RangedCrossbowAttackGoal.CrossbowState.READY_TO_ATTACK && flag) {
            this.f_25805_.m_6504_(livingentity, 1.0F);
            ItemStack itemstack1 = this.f_25805_.m_21120_(ProjectileUtil.getWeaponHoldingHand(this.f_25805_, item -> item instanceof CrossbowItem));
            CrossbowItem.m_40884_(itemstack1, false);
            this.f_25806_ = RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
         }

      }
   }

   private boolean m_25823_() {
      return this.f_25806_ == RangedCrossbowAttackGoal.CrossbowState.UNCHARGED;
   }

   static enum CrossbowState {
      UNCHARGED,
      CHARGING,
      CHARGED,
      READY_TO_ATTACK;
   }
}
