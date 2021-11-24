package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonChargePlayerPhase extends AbstractDragonPhaseInstance {
   private static final Logger f_31201_ = LogManager.getLogger();
   private static final int f_149577_ = 10;
   private Vec3 f_31202_;
   private int f_31203_;

   public DragonChargePlayerPhase(EnderDragon p_31206_) {
      super(p_31206_);
   }

   public void m_6989_() {
      if (this.f_31202_ == null) {
         f_31201_.warn("Aborting charge player as no target was set.");
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
      } else if (this.f_31203_ > 0 && this.f_31203_++ >= 10) {
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
      } else {
         double d0 = this.f_31202_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
         if (d0 < 100.0D || d0 > 22500.0D || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
            ++this.f_31203_;
         }

      }
   }

   public void m_7083_() {
      this.f_31202_ = null;
      this.f_31203_ = 0;
   }

   public void m_31207_(Vec3 p_31208_) {
      this.f_31202_ = p_31208_;
   }

   public float m_7072_() {
      return 3.0F;
   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31202_;
   }

   public EnderDragonPhase<DragonChargePlayerPhase> m_7309_() {
      return EnderDragonPhase.f_31385_;
   }
}