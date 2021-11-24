package net.minecraft.world.level.timers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerQueue<T> {
   private static final Logger f_82240_ = LogManager.getLogger();
   private static final String f_165876_ = "Callback";
   private static final String f_165877_ = "Name";
   private static final String f_165878_ = "TriggerTime";
   private final TimerCallbacks<T> f_82241_;
   private final Queue<TimerQueue.Event<T>> f_82242_ = new PriorityQueue<>(m_82270_());
   private UnsignedLong f_82243_ = UnsignedLong.ZERO;
   private final Table<String, Long, TimerQueue.Event<T>> f_82244_ = HashBasedTable.create();

   private static <T> Comparator<TimerQueue.Event<T>> m_82270_() {
      return Comparator.<TimerQueue.Event<T>>comparingLong((p_82272_) -> {
         return p_82272_.f_82273_;
      }).thenComparing((p_82269_) -> {
         return p_82269_.f_82274_;
      });
   }

   public TimerQueue(TimerCallbacks<T> p_82249_, Stream<Dynamic<Tag>> p_82250_) {
      this(p_82249_);
      this.f_82242_.clear();
      this.f_82244_.clear();
      this.f_82243_ = UnsignedLong.ZERO;
      p_82250_.forEach((p_82253_) -> {
         if (!(p_82253_.getValue() instanceof CompoundTag)) {
            f_82240_.warn("Invalid format of events: {}", (Object)p_82253_);
         } else {
            this.m_82265_((CompoundTag)p_82253_.getValue());
         }
      });
   }

   public TimerQueue(TimerCallbacks<T> p_82247_) {
      this.f_82241_ = p_82247_;
   }

   public void m_82256_(T p_82257_, long p_82258_) {
      while(true) {
         TimerQueue.Event<T> event = this.f_82242_.peek();
         if (event == null || event.f_82273_ > p_82258_) {
            return;
         }

         this.f_82242_.remove();
         this.f_82244_.remove(event.f_82275_, p_82258_);
         event.f_82276_.m_5821_(p_82257_, this, p_82258_);
      }
   }

   public void m_82261_(String p_82262_, long p_82263_, TimerCallback<T> p_82264_) {
      if (!this.f_82244_.contains(p_82262_, p_82263_)) {
         this.f_82243_ = this.f_82243_.plus(UnsignedLong.ONE);
         TimerQueue.Event<T> event = new TimerQueue.Event<>(p_82263_, this.f_82243_, p_82262_, p_82264_);
         this.f_82244_.put(p_82262_, p_82263_, event);
         this.f_82242_.add(event);
      }
   }

   public int m_82259_(String p_82260_) {
      Collection<TimerQueue.Event<T>> collection = this.f_82244_.row(p_82260_).values();
      collection.forEach(this.f_82242_::remove);
      int i = collection.size();
      collection.clear();
      return i;
   }

   public Set<String> m_82251_() {
      return Collections.unmodifiableSet(this.f_82244_.rowKeySet());
   }

   private void m_82265_(CompoundTag p_82266_) {
      CompoundTag compoundtag = p_82266_.m_128469_("Callback");
      TimerCallback<T> timercallback = this.f_82241_.m_82238_(compoundtag);
      if (timercallback != null) {
         String s = p_82266_.m_128461_("Name");
         long i = p_82266_.m_128454_("TriggerTime");
         this.m_82261_(s, i, timercallback);
      }

   }

   private CompoundTag m_82254_(TimerQueue.Event<T> p_82255_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("Name", p_82255_.f_82275_);
      compoundtag.m_128356_("TriggerTime", p_82255_.f_82273_);
      compoundtag.m_128365_("Callback", this.f_82241_.m_82234_(p_82255_.f_82276_));
      return compoundtag;
   }

   public ListTag m_82267_() {
      ListTag listtag = new ListTag();
      this.f_82242_.stream().sorted(m_82270_()).map(this::m_82254_).forEach(listtag::add);
      return listtag;
   }

   public static class Event<T> {
      public final long f_82273_;
      public final UnsignedLong f_82274_;
      public final String f_82275_;
      public final TimerCallback<T> f_82276_;

      Event(long p_82278_, UnsignedLong p_82279_, String p_82280_, TimerCallback<T> p_82281_) {
         this.f_82273_ = p_82278_;
         this.f_82274_ = p_82279_;
         this.f_82275_ = p_82280_;
         this.f_82276_ = p_82281_;
      }
   }
}