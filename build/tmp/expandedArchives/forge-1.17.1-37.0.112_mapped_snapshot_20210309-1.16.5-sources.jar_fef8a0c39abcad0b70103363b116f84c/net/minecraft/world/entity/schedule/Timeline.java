package net.minecraft.world.entity.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.Collection;
import java.util.List;

public class Timeline {
   private final List<Keyframe> f_38055_ = Lists.newArrayList();
   private int f_38056_;

   public ImmutableList<Keyframe> m_150246_() {
      return ImmutableList.copyOf(this.f_38055_);
   }

   public Timeline m_38060_(int p_38061_, float p_38062_) {
      this.f_38055_.add(new Keyframe(p_38061_, p_38062_));
      this.m_38066_();
      return this;
   }

   public Timeline m_150247_(Collection<Keyframe> p_150248_) {
      this.f_38055_.addAll(p_150248_);
      this.m_38066_();
      return this;
   }

   private void m_38066_() {
      Int2ObjectSortedMap<Keyframe> int2objectsortedmap = new Int2ObjectAVLTreeMap<>();
      this.f_38055_.forEach((p_38065_) -> {
         int2objectsortedmap.put(p_38065_.m_38010_(), p_38065_);
      });
      this.f_38055_.clear();
      this.f_38055_.addAll(int2objectsortedmap.values());
      this.f_38056_ = 0;
   }

   public float m_38058_(int p_38059_) {
      if (this.f_38055_.size() <= 0) {
         return 0.0F;
      } else {
         Keyframe keyframe = this.f_38055_.get(this.f_38056_);
         Keyframe keyframe1 = this.f_38055_.get(this.f_38055_.size() - 1);
         boolean flag = p_38059_ < keyframe.m_38010_();
         int i = flag ? 0 : this.f_38056_;
         float f = flag ? keyframe1.m_38011_() : keyframe.m_38011_();

         for(int j = i; j < this.f_38055_.size(); ++j) {
            Keyframe keyframe2 = this.f_38055_.get(j);
            if (keyframe2.m_38010_() > p_38059_) {
               break;
            }

            this.f_38056_ = j;
            f = keyframe2.m_38011_();
         }

         return f;
      }
   }
}