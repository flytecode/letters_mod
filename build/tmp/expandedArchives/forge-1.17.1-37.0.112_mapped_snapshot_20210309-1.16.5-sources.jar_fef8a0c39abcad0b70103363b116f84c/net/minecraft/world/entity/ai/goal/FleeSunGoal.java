package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FleeSunGoal extends Goal {
   protected final PathfinderMob f_25214_;
   private double f_25215_;
   private double f_25216_;
   private double f_25217_;
   private final double f_25218_;
   private final Level f_25219_;

   public FleeSunGoal(PathfinderMob p_25221_, double p_25222_) {
      this.f_25214_ = p_25221_;
      this.f_25218_ = p_25222_;
      this.f_25219_ = p_25221_.f_19853_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.f_25214_.m_5448_() != null) {
         return false;
      } else if (!this.f_25219_.m_46461_()) {
         return false;
      } else if (!this.f_25214_.m_6060_()) {
         return false;
      } else if (!this.f_25219_.m_45527_(this.f_25214_.m_142538_())) {
         return false;
      } else {
         return !this.f_25214_.m_6844_(EquipmentSlot.HEAD).m_41619_() ? false : this.m_25226_();
      }
   }

   protected boolean m_25226_() {
      Vec3 vec3 = this.m_25227_();
      if (vec3 == null) {
         return false;
      } else {
         this.f_25215_ = vec3.f_82479_;
         this.f_25216_ = vec3.f_82480_;
         this.f_25217_ = vec3.f_82481_;
         return true;
      }
   }

   public boolean m_8045_() {
      return !this.f_25214_.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.f_25214_.m_21573_().m_26519_(this.f_25215_, this.f_25216_, this.f_25217_, this.f_25218_);
   }

   @Nullable
   protected Vec3 m_25227_() {
      Random random = this.f_25214_.m_21187_();
      BlockPos blockpos = this.f_25214_.m_142538_();

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.m_142082_(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
         if (!this.f_25219_.m_45527_(blockpos1) && this.f_25214_.m_21692_(blockpos1) < 0.0F) {
            return Vec3.m_82539_(blockpos1);
         }
      }

      return null;
   }
}