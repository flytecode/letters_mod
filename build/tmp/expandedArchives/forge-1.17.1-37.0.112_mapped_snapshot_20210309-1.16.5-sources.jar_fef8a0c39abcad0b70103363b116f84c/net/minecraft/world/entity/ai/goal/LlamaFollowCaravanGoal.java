package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.phys.Vec3;

public class LlamaFollowCaravanGoal extends Goal {
   public final Llama f_25497_;
   private double f_25498_;
   private static final int f_148114_ = 8;
   private int f_25499_;

   public LlamaFollowCaravanGoal(Llama p_25501_, double p_25502_) {
      this.f_25497_ = p_25501_;
      this.f_25498_ = p_25502_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.f_25497_.m_21523_() && !this.f_25497_.m_30811_()) {
         List<Entity> list = this.f_25497_.f_19853_.m_6249_(this.f_25497_, this.f_25497_.m_142469_().m_82377_(9.0D, 4.0D, 9.0D), (p_25505_) -> {
            EntityType<?> entitytype = p_25505_.m_6095_();
            return entitytype == EntityType.f_20466_ || entitytype == EntityType.f_20488_;
         });
         Llama llama = null;
         double d0 = Double.MAX_VALUE;

         for(Entity entity : list) {
            Llama llama1 = (Llama)entity;
            if (llama1.m_30811_() && !llama1.m_30810_()) {
               double d1 = this.f_25497_.m_20280_(llama1);
               if (!(d1 > d0)) {
                  d0 = d1;
                  llama = llama1;
               }
            }
         }

         if (llama == null) {
            for(Entity entity1 : list) {
               Llama llama2 = (Llama)entity1;
               if (llama2.m_21523_() && !llama2.m_30810_()) {
                  double d2 = this.f_25497_.m_20280_(llama2);
                  if (!(d2 > d0)) {
                     d0 = d2;
                     llama = llama2;
                  }
               }
            }
         }

         if (llama == null) {
            return false;
         } else if (d0 < 4.0D) {
            return false;
         } else if (!llama.m_21523_() && !this.m_25506_(llama, 1)) {
            return false;
         } else {
            this.f_25497_.m_30766_(llama);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      if (this.f_25497_.m_30811_() && this.f_25497_.m_30812_().m_6084_() && this.m_25506_(this.f_25497_, 0)) {
         double d0 = this.f_25497_.m_20280_(this.f_25497_.m_30812_());
         if (d0 > 676.0D) {
            if (this.f_25498_ <= 3.0D) {
               this.f_25498_ *= 1.2D;
               this.f_25499_ = 40;
               return true;
            }

            if (this.f_25499_ == 0) {
               return false;
            }
         }

         if (this.f_25499_ > 0) {
            --this.f_25499_;
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_8041_() {
      this.f_25497_.m_30809_();
      this.f_25498_ = 2.1D;
   }

   public void m_8037_() {
      if (this.f_25497_.m_30811_()) {
         if (!(this.f_25497_.m_21524_() instanceof LeashFenceKnotEntity)) {
            Llama llama = this.f_25497_.m_30812_();
            double d0 = (double)this.f_25497_.m_20270_(llama);
            float f = 2.0F;
            Vec3 vec3 = (new Vec3(llama.m_20185_() - this.f_25497_.m_20185_(), llama.m_20186_() - this.f_25497_.m_20186_(), llama.m_20189_() - this.f_25497_.m_20189_())).m_82541_().m_82490_(Math.max(d0 - 2.0D, 0.0D));
            this.f_25497_.m_21573_().m_26519_(this.f_25497_.m_20185_() + vec3.f_82479_, this.f_25497_.m_20186_() + vec3.f_82480_, this.f_25497_.m_20189_() + vec3.f_82481_, this.f_25498_);
         }
      }
   }

   private boolean m_25506_(Llama p_25507_, int p_25508_) {
      if (p_25508_ > 8) {
         return false;
      } else if (p_25507_.m_30811_()) {
         if (p_25507_.m_30812_().m_21523_()) {
            return true;
         } else {
            Llama llama = p_25507_.m_30812_();
            ++p_25508_;
            return this.m_25506_(llama, p_25508_);
         }
      } else {
         return false;
      }
   }
}