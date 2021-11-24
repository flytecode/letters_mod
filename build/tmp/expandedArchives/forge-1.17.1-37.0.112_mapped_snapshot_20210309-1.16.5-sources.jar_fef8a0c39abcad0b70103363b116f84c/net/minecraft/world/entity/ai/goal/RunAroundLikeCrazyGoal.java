package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RunAroundLikeCrazyGoal extends Goal {
   private final AbstractHorse f_25884_;
   private final double f_25885_;
   private double f_25886_;
   private double f_25887_;
   private double f_25888_;

   public RunAroundLikeCrazyGoal(AbstractHorse p_25890_, double p_25891_) {
      this.f_25884_ = p_25890_;
      this.f_25885_ = p_25891_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.f_25884_.m_30614_() && this.f_25884_.m_20160_()) {
         Vec3 vec3 = DefaultRandomPos.m_148403_(this.f_25884_, 5, 4);
         if (vec3 == null) {
            return false;
         } else {
            this.f_25886_ = vec3.f_82479_;
            this.f_25887_ = vec3.f_82480_;
            this.f_25888_ = vec3.f_82481_;
            return true;
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      this.f_25884_.m_21573_().m_26519_(this.f_25886_, this.f_25887_, this.f_25888_, this.f_25885_);
   }

   public boolean m_8045_() {
      return !this.f_25884_.m_30614_() && !this.f_25884_.m_21573_().m_26571_() && this.f_25884_.m_20160_();
   }

   public void m_8037_() {
      if (!this.f_25884_.m_30614_() && this.f_25884_.m_21187_().nextInt(50) == 0) {
         Entity entity = this.f_25884_.m_20197_().get(0);
         if (entity == null) {
            return;
         }

         if (entity instanceof Player) {
            int i = this.f_25884_.m_30624_();
            int j = this.f_25884_.m_7555_();
            if (j > 0 && this.f_25884_.m_21187_().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(f_25884_, (Player)entity)) {
               this.f_25884_.m_30637_((Player)entity);
               return;
            }

            this.f_25884_.m_30653_(5);
         }

         this.f_25884_.m_20153_();
         this.f_25884_.m_7564_();
         this.f_25884_.f_19853_.m_7605_(this.f_25884_, (byte)6);
      }

   }
}
