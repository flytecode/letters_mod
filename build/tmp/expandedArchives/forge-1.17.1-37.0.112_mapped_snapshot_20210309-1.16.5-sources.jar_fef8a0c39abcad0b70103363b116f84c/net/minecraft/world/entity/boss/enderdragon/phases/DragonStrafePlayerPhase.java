package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonStrafePlayerPhase extends AbstractDragonPhaseInstance {
   private static final Logger f_31349_ = LogManager.getLogger();
   private static final int f_149586_ = 5;
   private int f_31350_;
   private Path f_31351_;
   private Vec3 f_31352_;
   private LivingEntity f_31353_;
   private boolean f_31354_;

   public DragonStrafePlayerPhase(EnderDragon p_31357_) {
      super(p_31357_);
   }

   public void m_6989_() {
      if (this.f_31353_ == null) {
         f_31349_.warn("Skipping player strafe phase because no player was found");
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
      } else {
         if (this.f_31351_ != null && this.f_31351_.m_77392_()) {
            double d0 = this.f_31353_.m_20185_();
            double d1 = this.f_31353_.m_20189_();
            double d2 = d0 - this.f_31176_.m_20185_();
            double d3 = d1 - this.f_31176_.m_20189_();
            double d4 = Math.sqrt(d2 * d2 + d3 * d3);
            double d5 = Math.min((double)0.4F + d4 / 80.0D - 1.0D, 10.0D);
            this.f_31352_ = new Vec3(d0, this.f_31353_.m_20186_() + d5, d1);
         }

         double d12 = this.f_31352_ == null ? 0.0D : this.f_31352_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
         if (d12 < 100.0D || d12 > 22500.0D) {
            this.m_31364_();
         }

         double d13 = 64.0D;
         if (this.f_31353_.m_20280_(this.f_31176_) < 4096.0D) {
            if (this.f_31176_.m_142582_(this.f_31353_)) {
               ++this.f_31350_;
               Vec3 vec31 = (new Vec3(this.f_31353_.m_20185_() - this.f_31176_.m_20185_(), 0.0D, this.f_31353_.m_20189_() - this.f_31176_.m_20189_())).m_82541_();
               Vec3 vec3 = (new Vec3((double)Mth.m_14031_(this.f_31176_.m_146908_() * ((float)Math.PI / 180F)), 0.0D, (double)(-Mth.m_14089_(this.f_31176_.m_146908_() * ((float)Math.PI / 180F))))).m_82541_();
               float f1 = (float)vec3.m_82526_(vec31);
               float f = (float)(Math.acos((double)f1) * (double)(180F / (float)Math.PI));
               f = f + 0.5F;
               if (this.f_31350_ >= 5 && f >= 0.0F && f < 10.0F) {
                  double d14 = 1.0D;
                  Vec3 vec32 = this.f_31176_.m_20252_(1.0F);
                  double d6 = this.f_31176_.f_31080_.m_20185_() - vec32.f_82479_ * 1.0D;
                  double d7 = this.f_31176_.f_31080_.m_20227_(0.5D) + 0.5D;
                  double d8 = this.f_31176_.f_31080_.m_20189_() - vec32.f_82481_ * 1.0D;
                  double d9 = this.f_31353_.m_20185_() - d6;
                  double d10 = this.f_31353_.m_20227_(0.5D) - d7;
                  double d11 = this.f_31353_.m_20189_() - d8;
                  if (!this.f_31176_.m_20067_()) {
                     this.f_31176_.f_19853_.m_5898_((Player)null, 1017, this.f_31176_.m_142538_(), 0);
                  }

                  DragonFireball dragonfireball = new DragonFireball(this.f_31176_.f_19853_, this.f_31176_, d9, d10, d11);
                  dragonfireball.m_7678_(d6, d7, d8, 0.0F, 0.0F);
                  this.f_31176_.f_19853_.m_7967_(dragonfireball);
                  this.f_31350_ = 0;
                  if (this.f_31351_ != null) {
                     while(!this.f_31351_.m_77392_()) {
                        this.f_31351_.m_77374_();
                     }
                  }

                  this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
               }
            } else if (this.f_31350_ > 0) {
               --this.f_31350_;
            }
         } else if (this.f_31350_ > 0) {
            --this.f_31350_;
         }

      }
   }

   private void m_31364_() {
      if (this.f_31351_ == null || this.f_31351_.m_77392_()) {
         int i = this.f_31176_.m_31155_();
         int j = i;
         if (this.f_31176_.m_21187_().nextInt(8) == 0) {
            this.f_31354_ = !this.f_31354_;
            j = i + 6;
         }

         if (this.f_31354_) {
            ++j;
         } else {
            --j;
         }

         if (this.f_31176_.m_31158_() != null && this.f_31176_.m_31158_().m_64098_() > 0) {
            j = j % 12;
            if (j < 0) {
               j += 12;
            }
         } else {
            j = j - 12;
            j = j & 7;
            j = j + 12;
         }

         this.f_31351_ = this.f_31176_.m_31104_(i, j, (Node)null);
         if (this.f_31351_ != null) {
            this.f_31351_.m_77374_();
         }
      }

      this.m_31365_();
   }

   private void m_31365_() {
      if (this.f_31351_ != null && !this.f_31351_.m_77392_()) {
         Vec3i vec3i = this.f_31351_.m_77400_();
         this.f_31351_.m_77374_();
         double d0 = (double)vec3i.m_123341_();
         double d2 = (double)vec3i.m_123343_();

         double d1;
         do {
            d1 = (double)((float)vec3i.m_123342_() + this.f_31176_.m_21187_().nextFloat() * 20.0F);
         } while(d1 < (double)vec3i.m_123342_());

         this.f_31352_ = new Vec3(d0, d1, d2);
      }

   }

   public void m_7083_() {
      this.f_31350_ = 0;
      this.f_31352_ = null;
      this.f_31351_ = null;
      this.f_31353_ = null;
   }

   public void m_31358_(LivingEntity p_31359_) {
      this.f_31353_ = p_31359_;
      int i = this.f_31176_.m_31155_();
      int j = this.f_31176_.m_31170_(this.f_31353_.m_20185_(), this.f_31353_.m_20186_(), this.f_31353_.m_20189_());
      int k = this.f_31353_.m_146903_();
      int l = this.f_31353_.m_146907_();
      double d0 = (double)k - this.f_31176_.m_20185_();
      double d1 = (double)l - this.f_31176_.m_20189_();
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3 = Math.min((double)0.4F + d2 / 80.0D - 1.0D, 10.0D);
      int i1 = Mth.m_14107_(this.f_31353_.m_20186_() + d3);
      Node node = new Node(k, i1, l);
      this.f_31351_ = this.f_31176_.m_31104_(i, j, node);
      if (this.f_31351_ != null) {
         this.f_31351_.m_77374_();
         this.m_31365_();
      }

   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31352_;
   }

   public EnderDragonPhase<DragonStrafePlayerPhase> m_7309_() {
      return EnderDragonPhase.f_31378_;
   }
}