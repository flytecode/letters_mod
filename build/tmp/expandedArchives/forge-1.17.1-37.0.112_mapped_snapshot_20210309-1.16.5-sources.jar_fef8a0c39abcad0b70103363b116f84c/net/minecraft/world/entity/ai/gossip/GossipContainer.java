package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.SerializableUUID;
import net.minecraft.util.VisibleForDebug;

public class GossipContainer {
   public static final int f_148158_ = 2;
   private final Map<UUID, GossipContainer.EntityGossips> f_26156_ = Maps.newHashMap();

   @VisibleForDebug
   public Map<UUID, Object2IntMap<GossipType>> m_148159_() {
      Map<UUID, Object2IntMap<GossipType>> map = Maps.newHashMap();
      this.f_26156_.keySet().forEach((p_148167_) -> {
         GossipContainer.EntityGossips gossipcontainer$entitygossips = this.f_26156_.get(p_148167_);
         map.put(p_148167_, gossipcontainer$entitygossips.f_26204_);
      });
      return map;
   }

   public void m_26198_() {
      Iterator<GossipContainer.EntityGossips> iterator = this.f_26156_.values().iterator();

      while(iterator.hasNext()) {
         GossipContainer.EntityGossips gossipcontainer$entitygossips = iterator.next();
         gossipcontainer$entitygossips.m_26208_();
         if (gossipcontainer$entitygossips.m_26225_()) {
            iterator.remove();
         }
      }

   }

   private Stream<GossipContainer.GossipEntry> m_26203_() {
      return this.f_26156_.entrySet().stream().flatMap((p_26185_) -> {
         return p_26185_.getValue().m_26215_(p_26185_.getKey());
      });
   }

   private Collection<GossipContainer.GossipEntry> m_26186_(Random p_26187_, int p_26188_) {
      List<GossipContainer.GossipEntry> list = this.m_26203_().collect(Collectors.toList());
      if (list.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] aint = new int[list.size()];
         int i = 0;

         for(int j = 0; j < list.size(); ++j) {
            GossipContainer.GossipEntry gossipcontainer$gossipentry = list.get(j);
            i += Math.abs(gossipcontainer$gossipentry.m_26235_());
            aint[j] = i - 1;
         }

         Set<GossipContainer.GossipEntry> set = Sets.newIdentityHashSet();

         for(int i1 = 0; i1 < p_26188_; ++i1) {
            int k = p_26187_.nextInt(i);
            int l = Arrays.binarySearch(aint, k);
            set.add(list.get(l < 0 ? -l - 1 : l));
         }

         return set;
      }
   }

   private GossipContainer.EntityGossips m_26189_(UUID p_26190_) {
      return this.f_26156_.computeIfAbsent(p_26190_, (p_26202_) -> {
         return new GossipContainer.EntityGossips();
      });
   }

   public void m_26163_(GossipContainer p_26164_, Random p_26165_, int p_26166_) {
      Collection<GossipContainer.GossipEntry> collection = p_26164_.m_26186_(p_26165_, p_26166_);
      collection.forEach((p_26200_) -> {
         int i = p_26200_.f_26230_ - p_26200_.f_26229_.f_26277_;
         if (i >= 2) {
            this.m_26189_(p_26200_.f_26228_).f_26204_.mergeInt(p_26200_.f_26229_, i, GossipContainer::m_26158_);
         }

      });
   }

   public int m_26195_(UUID p_26196_, Predicate<GossipType> p_26197_) {
      GossipContainer.EntityGossips gossipcontainer$entitygossips = this.f_26156_.get(p_26196_);
      return gossipcontainer$entitygossips != null ? gossipcontainer$entitygossips.m_26220_(p_26197_) : 0;
   }

   public long m_148162_(GossipType p_148163_, DoublePredicate p_148164_) {
      return this.f_26156_.values().stream().filter((p_148174_) -> {
         return p_148164_.test((double)(p_148174_.f_26204_.getOrDefault(p_148163_, 0) * p_148163_.f_26274_));
      }).count();
   }

   public void m_26191_(UUID p_26192_, GossipType p_26193_, int p_26194_) {
      GossipContainer.EntityGossips gossipcontainer$entitygossips = this.m_26189_(p_26192_);
      gossipcontainer$entitygossips.f_26204_.mergeInt(p_26193_, p_26194_, (p_26173_, p_26174_) -> {
         return this.m_26167_(p_26193_, p_26173_, p_26174_);
      });
      gossipcontainer$entitygossips.m_26211_(p_26193_);
      if (gossipcontainer$entitygossips.m_26225_()) {
         this.f_26156_.remove(p_26192_);
      }

   }

   public void m_148175_(UUID p_148176_, GossipType p_148177_, int p_148178_) {
      this.m_26191_(p_148176_, p_148177_, -p_148178_);
   }

   public void m_148168_(UUID p_148169_, GossipType p_148170_) {
      GossipContainer.EntityGossips gossipcontainer$entitygossips = this.f_26156_.get(p_148169_);
      if (gossipcontainer$entitygossips != null) {
         gossipcontainer$entitygossips.m_26226_(p_148170_);
         if (gossipcontainer$entitygossips.m_26225_()) {
            this.f_26156_.remove(p_148169_);
         }
      }

   }

   public void m_148160_(GossipType p_148161_) {
      Iterator<GossipContainer.EntityGossips> iterator = this.f_26156_.values().iterator();

      while(iterator.hasNext()) {
         GossipContainer.EntityGossips gossipcontainer$entitygossips = iterator.next();
         gossipcontainer$entitygossips.m_26226_(p_148161_);
         if (gossipcontainer$entitygossips.m_26225_()) {
            iterator.remove();
         }
      }

   }

   public <T> Dynamic<T> m_26179_(DynamicOps<T> p_26180_) {
      return new Dynamic<>(p_26180_, p_26180_.createList(this.m_26203_().map((p_26183_) -> {
         return p_26183_.m_26238_(p_26180_);
      }).map(Dynamic::getValue)));
   }

   public void m_26177_(Dynamic<?> p_26178_) {
      p_26178_.asStream().map(GossipContainer.GossipEntry::m_26236_).flatMap((p_26176_) -> {
         return Util.m_137519_(p_26176_.result());
      }).forEach((p_26162_) -> {
         this.m_26189_(p_26162_.f_26228_).f_26204_.put(p_26162_.f_26229_, p_26162_.f_26230_);
      });
   }

   private static int m_26158_(int p_26159_, int p_26160_) {
      return Math.max(p_26159_, p_26160_);
   }

   private int m_26167_(GossipType p_26168_, int p_26169_, int p_26170_) {
      int i = p_26169_ + p_26170_;
      return i > p_26168_.f_26275_ ? Math.max(p_26168_.f_26275_, p_26169_) : i;
   }

   static class EntityGossips {
      final Object2IntMap<GossipType> f_26204_ = new Object2IntOpenHashMap<>();

      public int m_26220_(Predicate<GossipType> p_26221_) {
         return this.f_26204_.object2IntEntrySet().stream().filter((p_26224_) -> {
            return p_26221_.test(p_26224_.getKey());
         }).mapToInt((p_26214_) -> {
            return p_26214_.getIntValue() * (p_26214_.getKey()).f_26274_;
         }).sum();
      }

      public Stream<GossipContainer.GossipEntry> m_26215_(UUID p_26216_) {
         return this.f_26204_.object2IntEntrySet().stream().map((p_26219_) -> {
            return new GossipContainer.GossipEntry(p_26216_, p_26219_.getKey(), p_26219_.getIntValue());
         });
      }

      public void m_26208_() {
         ObjectIterator<Entry<GossipType>> objectiterator = this.f_26204_.object2IntEntrySet().iterator();

         while(objectiterator.hasNext()) {
            Entry<GossipType> entry = objectiterator.next();
            int i = entry.getIntValue() - (entry.getKey()).f_26276_;
            if (i < 2) {
               objectiterator.remove();
            } else {
               entry.setValue(i);
            }
         }

      }

      public boolean m_26225_() {
         return this.f_26204_.isEmpty();
      }

      public void m_26211_(GossipType p_26212_) {
         int i = this.f_26204_.getInt(p_26212_);
         if (i > p_26212_.f_26275_) {
            this.f_26204_.put(p_26212_, p_26212_.f_26275_);
         }

         if (i < 2) {
            this.m_26226_(p_26212_);
         }

      }

      public void m_26226_(GossipType p_26227_) {
         this.f_26204_.removeInt(p_26227_);
      }
   }

   static class GossipEntry {
      public static final String f_148179_ = "Target";
      public static final String f_148180_ = "Type";
      public static final String f_148181_ = "Value";
      public final UUID f_26228_;
      public final GossipType f_26229_;
      public final int f_26230_;

      public GossipEntry(UUID p_26232_, GossipType p_26233_, int p_26234_) {
         this.f_26228_ = p_26232_;
         this.f_26229_ = p_26233_;
         this.f_26230_ = p_26234_;
      }

      public int m_26235_() {
         return this.f_26230_ * this.f_26229_.f_26274_;
      }

      public String toString() {
         return "GossipEntry{target=" + this.f_26228_ + ", type=" + this.f_26229_ + ", value=" + this.f_26230_ + "}";
      }

      public <T> Dynamic<T> m_26238_(DynamicOps<T> p_26239_) {
         return new Dynamic<>(p_26239_, p_26239_.createMap(ImmutableMap.of(p_26239_.createString("Target"), SerializableUUID.f_123272_.encodeStart(p_26239_, this.f_26228_).result().orElseThrow(RuntimeException::new), p_26239_.createString("Type"), p_26239_.createString(this.f_26229_.f_26273_), p_26239_.createString("Value"), p_26239_.createInt(this.f_26230_))));
      }

      public static DataResult<GossipContainer.GossipEntry> m_26236_(Dynamic<?> p_26237_) {
         return DataResult.unbox(DataResult.instance().group(p_26237_.get("Target").read(SerializableUUID.f_123272_), p_26237_.get("Type").asString().map(GossipType::m_26291_), p_26237_.get("Value").asNumber().map(Number::intValue)).apply(DataResult.instance(), GossipContainer.GossipEntry::new));
      }
   }
}