package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;

public class RangedBowAttackGoal<T extends net.minecraft.world.entity.Mob & RangedAttackMob> extends Goal {
   private final T f_25782_;
   private final double f_25783_;
   private int f_25784_;
   private final float f_25785_;
   private int f_25786_ = -1;
   private int f_25787_;
   private boolean f_25788_;
   private boolean f_25789_;
   private int f_25790_ = -1;

   public <M extends Monster & RangedAttackMob> RangedBowAttackGoal(M p_25792_, double p_25793_, int p_25794_, float p_25795_){
      this((T) p_25792_, p_25793_, p_25794_, p_25795_);
   }

   public RangedBowAttackGoal(T p_25792_, double p_25793_, int p_25794_, float p_25795_) {
      this.f_25782_ = p_25792_;
      this.f_25783_ = p_25793_;
      this.f_25784_ = p_25794_;
      this.f_25785_ = p_25795_ * p_25795_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public void m_25797_(int p_25798_) {
      this.f_25784_ = p_25798_;
   }

   public boolean m_8036_() {
      return this.f_25782_.m_5448_() == null ? false : this.m_25803_();
   }

   protected boolean m_25803_() {
      return this.f_25782_.m_21093_(is -> is.m_41720_() instanceof BowItem);
   }

   public boolean m_8045_() {
      return (this.m_8036_() || !this.f_25782_.m_21573_().m_26571_()) && this.m_25803_();
   }

   public void m_8056_() {
      super.m_8056_();
      this.f_25782_.m_21561_(true);
   }

   public void m_8041_() {
      super.m_8041_();
      this.f_25782_.m_21561_(false);
      this.f_25787_ = 0;
      this.f_25786_ = -1;
      this.f_25782_.m_5810_();
   }

   public void m_8037_() {
      LivingEntity livingentity = this.f_25782_.m_5448_();
      if (livingentity != null) {
         double d0 = this.f_25782_.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         boolean flag = this.f_25782_.m_21574_().m_148306_(livingentity);
         boolean flag1 = this.f_25787_ > 0;
         if (flag != flag1) {
            this.f_25787_ = 0;
         }

         if (flag) {
            ++this.f_25787_;
         } else {
            --this.f_25787_;
         }

         if (!(d0 > (double)this.f_25785_) && this.f_25787_ >= 20) {
            this.f_25782_.m_21573_().m_26573_();
            ++this.f_25790_;
         } else {
            this.f_25782_.m_21573_().m_5624_(livingentity, this.f_25783_);
            this.f_25790_ = -1;
         }

         if (this.f_25790_ >= 20) {
            if ((double)this.f_25782_.m_21187_().nextFloat() < 0.3D) {
               this.f_25788_ = !this.f_25788_;
            }

            if ((double)this.f_25782_.m_21187_().nextFloat() < 0.3D) {
               this.f_25789_ = !this.f_25789_;
            }

            this.f_25790_ = 0;
         }

         if (this.f_25790_ > -1) {
            if (d0 > (double)(this.f_25785_ * 0.75F)) {
               this.f_25789_ = false;
            } else if (d0 < (double)(this.f_25785_ * 0.25F)) {
               this.f_25789_ = true;
            }

            this.f_25782_.m_21566_().m_24988_(this.f_25789_ ? -0.5F : 0.5F, this.f_25788_ ? 0.5F : -0.5F);
            this.f_25782_.m_21391_(livingentity, 30.0F, 30.0F);
         } else {
            this.f_25782_.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         }

         if (this.f_25782_.m_6117_()) {
            if (!flag && this.f_25787_ < -60) {
               this.f_25782_.m_5810_();
            } else if (flag) {
               int i = this.f_25782_.m_21252_();
               if (i >= 20) {
                  this.f_25782_.m_5810_();
                  this.f_25782_.m_6504_(livingentity, BowItem.m_40661_(i));
                  this.f_25786_ = this.f_25784_;
               }
            }
         } else if (--this.f_25786_ <= 0 && this.f_25787_ >= -60) {
            this.f_25782_.m_6672_(ProjectileUtil.getWeaponHoldingHand(this.f_25782_, item -> item instanceof BowItem));
         }

      }
   }
}
