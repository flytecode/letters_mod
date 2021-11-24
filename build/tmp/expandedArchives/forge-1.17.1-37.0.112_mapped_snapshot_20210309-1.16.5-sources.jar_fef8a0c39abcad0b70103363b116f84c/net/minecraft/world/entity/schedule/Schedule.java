package net.minecraft.world.entity.schedule;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;

public class Schedule extends net.minecraftforge.registries.ForgeRegistryEntry<Schedule> {
   public static final int f_150241_ = 2000;
   public static final int f_150242_ = 7000;
   public static final Schedule f_38012_ = m_38029_("empty").m_38040_(0, Activity.f_37979_).m_38039_();
   public static final Schedule f_38013_ = m_38029_("simple").m_38040_(5000, Activity.f_37980_).m_38040_(11000, Activity.f_37982_).m_38039_();
   public static final Schedule f_38014_ = m_38029_("villager_baby").m_38040_(10, Activity.f_37979_).m_38040_(3000, Activity.f_37981_).m_38040_(6000, Activity.f_37979_).m_38040_(10000, Activity.f_37981_).m_38040_(12000, Activity.f_37982_).m_38039_();
   public static final Schedule f_38015_ = m_38029_("villager_default").m_38040_(10, Activity.f_37979_).m_38040_(2000, Activity.f_37980_).m_38040_(9000, Activity.f_37983_).m_38040_(11000, Activity.f_37979_).m_38040_(12000, Activity.f_37982_).m_38039_();
   private final Map<Activity, Timeline> f_38016_ = Maps.newHashMap();

   protected static ScheduleBuilder m_38029_(String p_38030_) {
      Schedule schedule = Registry.m_122961_(Registry.f_122873_, p_38030_, new Schedule());
      return new ScheduleBuilder(schedule);
   }

   protected void m_38024_(Activity p_38025_) {
      if (!this.f_38016_.containsKey(p_38025_)) {
         this.f_38016_.put(p_38025_, new Timeline());
      }

   }

   protected Timeline m_38031_(Activity p_38032_) {
      return this.f_38016_.get(p_38032_);
   }

   protected List<Timeline> m_38033_(Activity p_38034_) {
      return this.f_38016_.entrySet().stream().filter((p_38028_) -> {
         return p_38028_.getKey() != p_38034_;
      }).map(Entry::getValue).collect(Collectors.toList());
   }

   public Activity m_38019_(int p_38020_) {
      return this.f_38016_.entrySet().stream().max(Comparator.comparingDouble((p_38023_) -> {
         return (double)p_38023_.getValue().m_38058_(p_38020_);
      })).map(Entry::getKey).orElse(Activity.f_37979_);
   }
}
