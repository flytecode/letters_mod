package net.minecraft.world.entity.ai;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brain<E extends LivingEntity> {
   static final Logger f_21841_ = LogManager.getLogger();
   private final Supplier<Codec<Brain<E>>> f_21842_;
   private static final int f_147338_ = 20;
   private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> f_21843_ = Maps.newHashMap();
   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> f_21844_ = Maps.newLinkedHashMap();
   private final Map<Integer, Map<Activity, Set<Behavior<? super E>>>> f_21845_ = Maps.newTreeMap();
   private Schedule f_21846_ = Schedule.f_38012_;
   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> f_21847_ = Maps.newHashMap();
   private final Map<Activity, Set<MemoryModuleType<?>>> f_21848_ = Maps.newHashMap();
   private Set<Activity> f_21849_ = Sets.newHashSet();
   private final Set<Activity> f_21850_ = Sets.newHashSet();
   private Activity f_21851_ = Activity.f_37979_;
   private long f_21852_ = -9999L;

   public static <E extends LivingEntity> Brain.Provider<E> m_21923_(Collection<? extends MemoryModuleType<?>> p_21924_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_21925_) {
      return new Brain.Provider<>(p_21924_, p_21925_);
   }

   public static <E extends LivingEntity> Codec<Brain<E>> m_21946_(final Collection<? extends MemoryModuleType<?>> p_21947_, final Collection<? extends SensorType<? extends Sensor<? super E>>> p_21948_) {
      final MutableObject<Codec<Brain<E>>> mutableobject = new MutableObject<>();
      mutableobject.setValue((new MapCodec<Brain<E>>() {
         public <T> Stream<T> keys(DynamicOps<T> p_22029_) {
            return p_21947_.stream().flatMap((p_22020_) -> {
               return Util.m_137519_(p_22020_.m_26387_().map((p_147346_) -> {
                  return Registry.f_122871_.m_7981_(p_22020_);
               }));
            }).map((p_22018_) -> {
               return p_22029_.createString(p_22018_.toString());
            });
         }

         public <T> DataResult<Brain<E>> decode(DynamicOps<T> p_22022_, MapLike<T> p_22023_) {
            MutableObject<DataResult<Builder<Brain.MemoryValue<?>>>> mutableobject1 = new MutableObject<>(DataResult.success(ImmutableList.builder()));
            p_22023_.entries().forEach((p_22015_) -> {
               DataResult<MemoryModuleType<?>> dataresult = Registry.f_122871_.parse(p_22022_, p_22015_.getFirst());
               DataResult<? extends Brain.MemoryValue<?>> dataresult1 = dataresult.flatMap((p_147350_) -> {
                  return this.m_21996_(p_147350_, p_22022_, (T)p_22015_.getSecond());
               });
               mutableobject1.setValue(mutableobject1.getValue().apply2(Builder::add, dataresult1));
            });
            ImmutableList<Brain.MemoryValue<?>> immutablelist = mutableobject1.getValue().resultOrPartial(Brain.f_21841_::error).map(Builder::build).orElseGet(ImmutableList::of);
            return DataResult.success(new Brain<>(p_21947_, p_21948_, immutablelist, mutableobject::getValue));
         }

         private <T, U> DataResult<Brain.MemoryValue<U>> m_21996_(MemoryModuleType<U> p_21997_, DynamicOps<T> p_21998_, T p_21999_) {
            return p_21997_.m_26387_().map(DataResult::success).orElseGet(() -> {
               return DataResult.error("No codec for memory: " + p_21997_);
            }).flatMap((p_22011_) -> {
               return p_22011_.parse(p_21998_, p_21999_);
            }).map((p_21992_) -> {
               return new Brain.MemoryValue<>(p_21997_, Optional.of(p_21992_));
            });
         }

         public <T> RecordBuilder<T> encode(Brain<E> p_21985_, DynamicOps<T> p_21986_, RecordBuilder<T> p_21987_) {
            p_21985_.m_21975_().forEach((p_22007_) -> {
               p_22007_.m_22047_(p_21986_, p_21987_);
            });
            return p_21987_;
         }
      }).fieldOf("memories").codec());
      return mutableobject.getValue();
   }

   public Brain(Collection<? extends MemoryModuleType<?>> p_21855_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_21856_, ImmutableList<Brain.MemoryValue<?>> p_21857_, Supplier<Codec<Brain<E>>> p_21858_) {
      this.f_21842_ = p_21858_;

      for(MemoryModuleType<?> memorymoduletype : p_21855_) {
         this.f_21843_.put(memorymoduletype, Optional.empty());
      }

      for(SensorType<? extends Sensor<? super E>> sensortype : p_21856_) {
         this.f_21844_.put(sensortype, sensortype.m_26827_());
      }

      for(Sensor<? super E> sensor : this.f_21844_.values()) {
         for(MemoryModuleType<?> memorymoduletype1 : sensor.m_7163_()) {
            this.f_21843_.put(memorymoduletype1, Optional.empty());
         }
      }

      for(Brain.MemoryValue<?> memoryvalue : p_21857_) {
         memoryvalue.m_22042_(this);
      }

   }

   public <T> DataResult<T> m_21914_(DynamicOps<T> p_21915_) {
      return this.f_21842_.get().encodeStart(p_21915_, this);
   }

   Stream<Brain.MemoryValue<?>> m_21975_() {
      return this.f_21843_.entrySet().stream().map((p_21929_) -> {
         return Brain.MemoryValue.m_22059_(p_21929_.getKey(), p_21929_.getValue());
      });
   }

   public boolean m_21874_(MemoryModuleType<?> p_21875_) {
      return this.m_21876_(p_21875_, MemoryStatus.VALUE_PRESENT);
   }

   public <U> void m_21936_(MemoryModuleType<U> p_21937_) {
      this.m_21886_(p_21937_, Optional.empty());
   }

   public <U> void m_21879_(MemoryModuleType<U> p_21880_, @Nullable U p_21881_) {
      this.m_21886_(p_21880_, Optional.ofNullable(p_21881_));
   }

   public <U> void m_21882_(MemoryModuleType<U> p_21883_, U p_21884_, long p_21885_) {
      this.m_21941_(p_21883_, Optional.of(ExpirableValue.m_26311_(p_21884_, p_21885_)));
   }

   public <U> void m_21886_(MemoryModuleType<U> p_21887_, Optional<? extends U> p_21888_) {
      this.m_21941_(p_21887_, p_21888_.map(ExpirableValue::m_26309_));
   }

   <U> void m_21941_(MemoryModuleType<U> p_21942_, Optional<? extends ExpirableValue<?>> p_21943_) {
      if (this.f_21843_.containsKey(p_21942_)) {
         if (p_21943_.isPresent() && this.m_21918_(p_21943_.get().m_26319_())) {
            this.m_21936_(p_21942_);
         } else {
            this.f_21843_.put(p_21942_, p_21943_);
         }
      }

   }

   public <U> Optional<U> m_21952_(MemoryModuleType<U> p_21953_) {
      return (Optional<U>)this.f_21843_.get(p_21953_).map(ExpirableValue::m_26319_);
   }

   public <U> long m_147341_(MemoryModuleType<U> p_147342_) {
      Optional<? extends ExpirableValue<?>> optional = this.f_21843_.get(p_147342_);
      return optional.map(ExpirableValue::m_148191_).orElse(0L);
   }

   @Deprecated
   @VisibleForDebug
   public Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> m_147339_() {
      return this.f_21843_;
   }

   public <U> boolean m_21938_(MemoryModuleType<U> p_21939_, U p_21940_) {
      return !this.m_21874_(p_21939_) ? false : this.m_21952_(p_21939_).filter((p_21922_) -> {
         return p_21922_.equals(p_21940_);
      }).isPresent();
   }

   public boolean m_21876_(MemoryModuleType<?> p_21877_, MemoryStatus p_21878_) {
      Optional<? extends ExpirableValue<?>> optional = this.f_21843_.get(p_21877_);
      if (optional == null) {
         return false;
      } else {
         return p_21878_ == MemoryStatus.REGISTERED || p_21878_ == MemoryStatus.VALUE_PRESENT && optional.isPresent() || p_21878_ == MemoryStatus.VALUE_ABSENT && !optional.isPresent();
      }
   }

   public Schedule m_21932_() {
      return this.f_21846_;
   }

   public void m_21912_(Schedule p_21913_) {
      this.f_21846_ = p_21913_;
   }

   public void m_21930_(Set<Activity> p_21931_) {
      this.f_21849_ = p_21931_;
   }

   @Deprecated
   @VisibleForDebug
   public Set<Activity> m_147340_() {
      return this.f_21850_;
   }

   @Deprecated
   @VisibleForDebug
   public List<Behavior<? super E>> m_21956_() {
      List<Behavior<? super E>> list = new ObjectArrayList<>();

      for(Map<Activity, Set<Behavior<? super E>>> map : this.f_21845_.values()) {
         for(Set<Behavior<? super E>> set : map.values()) {
            for(Behavior<? super E> behavior : set) {
               if (behavior.m_22536_() == Behavior.Status.RUNNING) {
                  list.add(behavior);
               }
            }
         }
      }

      return list;
   }

   public void m_21962_() {
      this.m_21960_(this.f_21851_);
   }

   public Optional<Activity> m_21968_() {
      for(Activity activity : this.f_21850_) {
         if (!this.f_21849_.contains(activity)) {
            return Optional.of(activity);
         }
      }

      return Optional.empty();
   }

   public void m_21889_(Activity p_21890_) {
      if (this.m_21969_(p_21890_)) {
         this.m_21960_(p_21890_);
      } else {
         this.m_21962_();
      }

   }

   private void m_21960_(Activity p_21961_) {
      if (!this.m_21954_(p_21961_)) {
         this.m_21966_(p_21961_);
         this.f_21850_.clear();
         this.f_21850_.addAll(this.f_21849_);
         this.f_21850_.add(p_21961_);
      }
   }

   private void m_21966_(Activity p_21967_) {
      for(Activity activity : this.f_21850_) {
         if (activity != p_21967_) {
            Set<MemoryModuleType<?>> set = this.f_21848_.get(activity);
            if (set != null) {
               for(MemoryModuleType<?> memorymoduletype : set) {
                  this.m_21936_(memorymoduletype);
               }
            }
         }
      }

   }

   public void m_21862_(long p_21863_, long p_21864_) {
      if (p_21864_ - this.f_21852_ > 20L) {
         this.f_21852_ = p_21864_;
         Activity activity = this.m_21932_().m_38019_((int)(p_21863_ % 24000L));
         if (!this.f_21850_.contains(activity)) {
            this.m_21889_(activity);
         }
      }

   }

   public void m_21926_(List<Activity> p_21927_) {
      for(Activity activity : p_21927_) {
         if (this.m_21969_(activity)) {
            this.m_21960_(activity);
            break;
         }
      }

   }

   public void m_21944_(Activity p_21945_) {
      this.f_21851_ = p_21945_;
   }

   public void m_21891_(Activity p_21892_, int p_21893_, ImmutableList<? extends Behavior<? super E>> p_21894_) {
      this.m_21900_(p_21892_, this.m_21859_(p_21893_, p_21894_));
   }

   public void m_21895_(Activity p_21896_, int p_21897_, ImmutableList<? extends Behavior<? super E>> p_21898_, MemoryModuleType<?> p_21899_) {
      Set<Pair<MemoryModuleType<?>, MemoryStatus>> set = ImmutableSet.of(Pair.of(p_21899_, MemoryStatus.VALUE_PRESENT));
      Set<MemoryModuleType<?>> set1 = ImmutableSet.of(p_21899_);
      this.m_21907_(p_21896_, this.m_21859_(p_21897_, p_21898_), set, set1);
   }

   public void m_21900_(Activity p_21901_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21902_) {
      this.m_21907_(p_21901_, p_21902_, ImmutableSet.of(), Sets.newHashSet());
   }

   public void m_21903_(Activity p_21904_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21905_, Set<Pair<MemoryModuleType<?>, MemoryStatus>> p_21906_) {
      this.m_21907_(p_21904_, p_21905_, p_21906_, Sets.newHashSet());
   }

   public void m_21907_(Activity p_21908_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21909_, Set<Pair<MemoryModuleType<?>, MemoryStatus>> p_21910_, Set<MemoryModuleType<?>> p_21911_) {
      this.f_21847_.put(p_21908_, p_21910_);
      if (!p_21911_.isEmpty()) {
         this.f_21848_.put(p_21908_, p_21911_);
      }

      for(Pair<Integer, ? extends Behavior<? super E>> pair : p_21909_) {
         this.f_21845_.computeIfAbsent(pair.getFirst(), (p_21917_) -> {
            return Maps.newHashMap();
         }).computeIfAbsent(p_21908_, (p_21972_) -> {
            return Sets.newLinkedHashSet();
         }).add(pair.getSecond());
      }

   }

   @VisibleForTesting
   public void m_147343_() {
      this.f_21845_.clear();
   }

   public boolean m_21954_(Activity p_21955_) {
      return this.f_21850_.contains(p_21955_);
   }

   public Brain<E> m_21973_() {
      Brain<E> brain = new Brain<>(this.f_21843_.keySet(), this.f_21844_.keySet(), ImmutableList.of(), this.f_21842_);

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : this.f_21843_.entrySet()) {
         MemoryModuleType<?> memorymoduletype = entry.getKey();
         if (entry.getValue().isPresent()) {
            brain.f_21843_.put(memorymoduletype, entry.getValue());
         }
      }

      return brain;
   }

   public void m_21865_(ServerLevel p_21866_, E p_21867_) {
      this.m_21976_();
      this.m_21949_(p_21866_, p_21867_);
      this.m_21957_(p_21866_, p_21867_);
      this.m_21963_(p_21866_, p_21867_);
   }

   private void m_21949_(ServerLevel p_21950_, E p_21951_) {
      for(Sensor<? super E> sensor : this.f_21844_.values()) {
         sensor.m_26806_(p_21950_, p_21951_);
      }

   }

   private void m_21976_() {
      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : this.f_21843_.entrySet()) {
         if (entry.getValue().isPresent()) {
            ExpirableValue<?> expirablevalue = entry.getValue().get();
            expirablevalue.m_26301_();
            if (expirablevalue.m_26320_()) {
               this.m_21936_(entry.getKey());
            }
         }
      }

   }

   public void m_21933_(ServerLevel p_21934_, E p_21935_) {
      long i = p_21935_.f_19853_.m_46467_();

      for(Behavior<? super E> behavior : this.m_21956_()) {
         behavior.m_22562_(p_21934_, p_21935_, i);
      }

   }

   private void m_21957_(ServerLevel p_21958_, E p_21959_) {
      long i = p_21958_.m_46467_();

      for(Map<Activity, Set<Behavior<? super E>>> map : this.f_21845_.values()) {
         for(Entry<Activity, Set<Behavior<? super E>>> entry : map.entrySet()) {
            Activity activity = entry.getKey();
            if (this.f_21850_.contains(activity)) {
               for(Behavior<? super E> behavior : entry.getValue()) {
                  if (behavior.m_22536_() == Behavior.Status.STOPPED) {
                     behavior.m_22554_(p_21958_, p_21959_, i);
                  }
               }
            }
         }
      }

   }

   private void m_21963_(ServerLevel p_21964_, E p_21965_) {
      long i = p_21964_.m_46467_();

      for(Behavior<? super E> behavior : this.m_21956_()) {
         behavior.m_22558_(p_21964_, p_21965_, i);
      }

   }

   private boolean m_21969_(Activity p_21970_) {
      if (!this.f_21847_.containsKey(p_21970_)) {
         return false;
      } else {
         for(Pair<MemoryModuleType<?>, MemoryStatus> pair : this.f_21847_.get(p_21970_)) {
            MemoryModuleType<?> memorymoduletype = pair.getFirst();
            MemoryStatus memorystatus = pair.getSecond();
            if (!this.m_21876_(memorymoduletype, memorystatus)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean m_21918_(Object p_21919_) {
      return p_21919_ instanceof Collection && ((Collection)p_21919_).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> m_21859_(int p_21860_, ImmutableList<? extends Behavior<? super E>> p_21861_) {
      int i = p_21860_;
      Builder<Pair<Integer, ? extends Behavior<? super E>>> builder = ImmutableList.builder();

      for(Behavior<? super E> behavior : p_21861_) {
         builder.add(Pair.of(i++, behavior));
      }

      return builder.build();
   }

   static final class MemoryValue<U> {
      private final MemoryModuleType<U> f_22030_;
      private final Optional<? extends ExpirableValue<U>> f_22031_;

      static <U> Brain.MemoryValue<U> m_22059_(MemoryModuleType<U> p_22060_, Optional<? extends ExpirableValue<?>> p_22061_) {
         return new Brain.MemoryValue<U>(p_22060_, (Optional<? extends ExpirableValue<U>>)p_22061_);
      }

      MemoryValue(MemoryModuleType<U> p_22033_, Optional<? extends ExpirableValue<U>> p_22034_) {
         this.f_22030_ = p_22033_;
         this.f_22031_ = p_22034_;
      }

      void m_22042_(Brain<?> p_22043_) {
         p_22043_.m_21941_(this.f_22030_, this.f_22031_);
      }

      public <T> void m_22047_(DynamicOps<T> p_22048_, RecordBuilder<T> p_22049_) {
         this.f_22030_.m_26387_().ifPresent((p_22053_) -> {
            this.f_22031_.ifPresent((p_147355_) -> {
               p_22049_.add(Registry.f_122871_.encodeStart(p_22048_, this.f_22030_), p_22053_.encodeStart(p_22048_, p_147355_));
            });
         });
      }
   }

   public static final class Provider<E extends LivingEntity> {
      private final Collection<? extends MemoryModuleType<?>> f_22062_;
      private final Collection<? extends SensorType<? extends Sensor<? super E>>> f_22063_;
      private final Codec<Brain<E>> f_22064_;

      Provider(Collection<? extends MemoryModuleType<?>> p_22066_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_22067_) {
         this.f_22062_ = p_22066_;
         this.f_22063_ = p_22067_;
         this.f_22064_ = Brain.m_21946_(p_22066_, p_22067_);
      }

      public Brain<E> m_22073_(Dynamic<?> p_22074_) {
         return this.f_22064_.parse(p_22074_).resultOrPartial(Brain.f_21841_::error).orElseGet(() -> {
            return new Brain<>(this.f_22062_, this.f_22063_, ImmutableList.of(), () -> {
               return this.f_22064_;
            });
         });
      }
   }
}