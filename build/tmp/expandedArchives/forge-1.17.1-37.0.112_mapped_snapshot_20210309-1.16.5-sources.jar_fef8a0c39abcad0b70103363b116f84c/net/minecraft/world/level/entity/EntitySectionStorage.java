package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

public class EntitySectionStorage<T extends EntityAccess> {
   private final Class<T> f_156850_;
   private final Long2ObjectFunction<Visibility> f_156851_;
   private final Long2ObjectMap<EntitySection<T>> f_156852_ = new Long2ObjectOpenHashMap<>();
   private final LongSortedSet f_156853_ = new LongAVLTreeSet();

   public EntitySectionStorage(Class<T> p_156855_, Long2ObjectFunction<Visibility> p_156856_) {
      this.f_156850_ = p_156855_;
      this.f_156851_ = p_156856_;
   }

   public void m_156877_(AABB p_156878_, Consumer<EntitySection<T>> p_156879_) {
      int i = SectionPos.m_175552_(p_156878_.f_82288_ - 2.0D);
      int j = SectionPos.m_175552_(p_156878_.f_82289_ - 2.0D);
      int k = SectionPos.m_175552_(p_156878_.f_82290_ - 2.0D);
      int l = SectionPos.m_175552_(p_156878_.f_82291_ + 2.0D);
      int i1 = SectionPos.m_175552_(p_156878_.f_82292_ + 2.0D);
      int j1 = SectionPos.m_175552_(p_156878_.f_82293_ + 2.0D);

      for(int k1 = i; k1 <= l; ++k1) {
         long l1 = SectionPos.m_123209_(k1, 0, 0);
         long i2 = SectionPos.m_123209_(k1, -1, -1);
         LongIterator longiterator = this.f_156853_.subSet(l1, i2 + 1L).iterator();

         while(longiterator.hasNext()) {
            long j2 = longiterator.nextLong();
            int k2 = SectionPos.m_123225_(j2);
            int l2 = SectionPos.m_123230_(j2);
            if (k2 >= j && k2 <= i1 && l2 >= k && l2 <= j1) {
               EntitySection<T> entitysection = this.f_156852_.get(j2);
               if (entitysection != null && entitysection.m_156848_().m_157694_()) {
                  p_156879_.accept(entitysection);
               }
            }
         }
      }

   }

   public LongStream m_156861_(long p_156862_) {
      int i = ChunkPos.m_45592_(p_156862_);
      int j = ChunkPos.m_45602_(p_156862_);
      LongSortedSet longsortedset = this.m_156858_(i, j);
      if (longsortedset.isEmpty()) {
         return LongStream.empty();
      } else {
         OfLong oflong = longsortedset.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(oflong, 1301), false);
      }
   }

   private LongSortedSet m_156858_(int p_156859_, int p_156860_) {
      long i = SectionPos.m_123209_(p_156859_, 0, p_156860_);
      long j = SectionPos.m_123209_(p_156859_, -1, p_156860_);
      return this.f_156853_.subSet(i, j + 1L);
   }

   public Stream<EntitySection<T>> m_156888_(long p_156889_) {
      return this.m_156861_(p_156889_).mapToObj(this.f_156852_::get).filter(Objects::nonNull);
   }

   private static long m_156899_(long p_156900_) {
      return ChunkPos.m_45589_(SectionPos.m_123213_(p_156900_), SectionPos.m_123230_(p_156900_));
   }

   public EntitySection<T> m_156893_(long p_156894_) {
      return this.f_156852_.computeIfAbsent(p_156894_, this::m_156901_);
   }

   @Nullable
   public EntitySection<T> m_156895_(long p_156896_) {
      return this.f_156852_.get(p_156896_);
   }

   private EntitySection<T> m_156901_(long p_156902_) {
      long i = m_156899_(p_156902_);
      Visibility visibility = this.f_156851_.get(i);
      this.f_156853_.add(p_156902_);
      return new EntitySection<>(this.f_156850_, visibility);
   }

   public LongSet m_156857_() {
      LongSet longset = new LongOpenHashSet();
      this.f_156852_.keySet().forEach((java.util.function.LongConsumer)(p_156886_) -> {
         longset.add(m_156899_(p_156886_));
      });
      return longset;
   }

   private static <T extends EntityAccess> Predicate<T> m_156872_(AABB p_156873_) {
      return (p_156876_) -> {
         return p_156876_.m_142469_().m_82381_(p_156873_);
      };
   }

   public void m_156890_(AABB p_156891_, Consumer<T> p_156892_) {
      this.m_156877_(p_156891_, (p_156883_) -> {
         p_156883_.m_156842_(m_156872_(p_156891_), p_156892_);
      });
   }

   public <U extends T> void m_156863_(EntityTypeTest<T, U> p_156864_, AABB p_156865_, Consumer<U> p_156866_) {
      this.m_156877_(p_156865_, (p_156871_) -> {
         p_156871_.m_156834_(p_156864_, m_156872_(p_156865_), p_156866_);
      });
   }

   public void m_156897_(long p_156898_) {
      this.f_156852_.remove(p_156898_);
      this.f_156853_.remove(p_156898_);
   }

   @VisibleForDebug
   public int m_156887_() {
      return this.f_156853_.size();
   }
}