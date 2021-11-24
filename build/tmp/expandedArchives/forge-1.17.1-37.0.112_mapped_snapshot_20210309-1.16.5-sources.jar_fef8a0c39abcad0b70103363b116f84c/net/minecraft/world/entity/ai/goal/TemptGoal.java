package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptGoal extends Goal {
   private static final TargetingConditions f_25926_ = TargetingConditions.m_148353_().m_26883_(10.0D).m_148355_();
   private final TargetingConditions f_148137_;
   protected final PathfinderMob f_25924_;
   private final double f_25927_;
   private double f_25928_;
   private double f_25929_;
   private double f_25930_;
   private double f_25931_;
   private double f_25932_;
   protected Player f_25925_;
   private int f_25933_;
   private boolean f_25934_;
   private final Ingredient f_25935_;
   private final boolean f_25936_;

   public TemptGoal(PathfinderMob p_25939_, double p_25940_, Ingredient p_25941_, boolean p_25942_) {
      this.f_25924_ = p_25939_;
      this.f_25927_ = p_25940_;
      this.f_25935_ = p_25941_;
      this.f_25936_ = p_25942_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      this.f_148137_ = f_25926_.m_148354_().m_26888_(this::m_148138_);
   }

   public boolean m_8036_() {
      if (this.f_25933_ > 0) {
         --this.f_25933_;
         return false;
      } else {
         this.f_25925_ = this.f_25924_.f_19853_.m_45946_(this.f_148137_, this.f_25924_);
         return this.f_25925_ != null;
      }
   }

   private boolean m_148138_(LivingEntity p_148139_) {
      return this.f_25935_.test(p_148139_.m_21205_()) || this.f_25935_.test(p_148139_.m_21206_());
   }

   public boolean m_8045_() {
      if (this.m_7497_()) {
         if (this.f_25924_.m_20280_(this.f_25925_) < 36.0D) {
            if (this.f_25925_.m_20275_(this.f_25928_, this.f_25929_, this.f_25930_) > 0.010000000000000002D) {
               return false;
            }

            if (Math.abs((double)this.f_25925_.m_146909_() - this.f_25931_) > 5.0D || Math.abs((double)this.f_25925_.m_146908_() - this.f_25932_) > 5.0D) {
               return false;
            }
         } else {
            this.f_25928_ = this.f_25925_.m_20185_();
            this.f_25929_ = this.f_25925_.m_20186_();
            this.f_25930_ = this.f_25925_.m_20189_();
         }

         this.f_25931_ = (double)this.f_25925_.m_146909_();
         this.f_25932_ = (double)this.f_25925_.m_146908_();
      }

      return this.m_8036_();
   }

   protected boolean m_7497_() {
      return this.f_25936_;
   }

   public void m_8056_() {
      this.f_25928_ = this.f_25925_.m_20185_();
      this.f_25929_ = this.f_25925_.m_20186_();
      this.f_25930_ = this.f_25925_.m_20189_();
      this.f_25934_ = true;
   }

   public void m_8041_() {
      this.f_25925_ = null;
      this.f_25924_.m_21573_().m_26573_();
      this.f_25933_ = 100;
      this.f_25934_ = false;
   }

   public void m_8037_() {
      this.f_25924_.m_21563_().m_24960_(this.f_25925_, (float)(this.f_25924_.m_8085_() + 20), (float)this.f_25924_.m_8132_());
      if (this.f_25924_.m_20280_(this.f_25925_) < 6.25D) {
         this.f_25924_.m_21573_().m_26573_();
      } else {
         this.f_25924_.m_21573_().m_5624_(this.f_25925_, this.f_25927_);
      }

   }

   public boolean m_25955_() {
      return this.f_25934_;
   }
}