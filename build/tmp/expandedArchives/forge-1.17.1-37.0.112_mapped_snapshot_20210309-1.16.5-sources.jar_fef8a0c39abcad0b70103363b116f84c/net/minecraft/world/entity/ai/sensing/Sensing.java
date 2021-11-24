package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class Sensing {
   private final Mob f_26784_;
   private final List<Entity> f_26785_ = Lists.newArrayList();
   private final List<Entity> f_26786_ = Lists.newArrayList();

   public Sensing(Mob p_26788_) {
      this.f_26784_ = p_26788_;
   }

   public void m_26789_() {
      this.f_26785_.clear();
      this.f_26786_.clear();
   }

   public boolean m_148306_(Entity p_148307_) {
      if (this.f_26785_.contains(p_148307_)) {
         return true;
      } else if (this.f_26786_.contains(p_148307_)) {
         return false;
      } else {
         this.f_26784_.f_19853_.m_46473_().m_6180_("hasLineOfSight");
         boolean flag = this.f_26784_.m_142582_(p_148307_);
         this.f_26784_.f_19853_.m_46473_().m_7238_();
         if (flag) {
            this.f_26785_.add(p_148307_);
         } else {
            this.f_26786_.add(p_148307_);
         }

         return flag;
      }
   }
}