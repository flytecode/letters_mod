package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.Mth;

public class ItemCooldowns {
   private final Map<Item, ItemCooldowns.CooldownInstance> f_41515_ = Maps.newHashMap();
   private int f_41516_;

   public boolean m_41519_(Item p_41520_) {
      return this.m_41521_(p_41520_, 0.0F) > 0.0F;
   }

   public float m_41521_(Item p_41522_, float p_41523_) {
      ItemCooldowns.CooldownInstance itemcooldowns$cooldowninstance = this.f_41515_.get(p_41522_);
      if (itemcooldowns$cooldowninstance != null) {
         float f = (float)(itemcooldowns$cooldowninstance.f_41534_ - itemcooldowns$cooldowninstance.f_41533_);
         float f1 = (float)itemcooldowns$cooldowninstance.f_41534_ - ((float)this.f_41516_ + p_41523_);
         return Mth.m_14036_(f1 / f, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void m_41518_() {
      ++this.f_41516_;
      if (!this.f_41515_.isEmpty()) {
         Iterator<Entry<Item, ItemCooldowns.CooldownInstance>> iterator = this.f_41515_.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<Item, ItemCooldowns.CooldownInstance> entry = iterator.next();
            if ((entry.getValue()).f_41534_ <= this.f_41516_) {
               iterator.remove();
               this.m_7432_(entry.getKey());
            }
         }
      }

   }

   public void m_41524_(Item p_41525_, int p_41526_) {
      this.f_41515_.put(p_41525_, new ItemCooldowns.CooldownInstance(this.f_41516_, this.f_41516_ + p_41526_));
      this.m_6899_(p_41525_, p_41526_);
   }

   public void m_41527_(Item p_41528_) {
      this.f_41515_.remove(p_41528_);
      this.m_7432_(p_41528_);
   }

   protected void m_6899_(Item p_41529_, int p_41530_) {
   }

   protected void m_7432_(Item p_41531_) {
   }

   class CooldownInstance {
      final int f_41533_;
      final int f_41534_;

      CooldownInstance(int p_41537_, int p_41538_) {
         this.f_41533_ = p_41537_;
         this.f_41534_ = p_41538_;
      }
   }
}