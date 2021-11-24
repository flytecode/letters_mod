package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public abstract class LayerLightSectionStorage<M extends DataLayerStorageMap<M>> extends SectionTracker {
   protected static final int f_164440_ = 0;
   protected static final int f_164441_ = 1;
   protected static final int f_164442_ = 2;
   protected static final DataLayer f_75727_ = new DataLayer();
   private static final Direction[] f_75737_ = Direction.values();
   private final LightLayer f_75738_;
   private final LightChunkGetter f_75739_;
   protected final LongSet f_75728_ = new LongOpenHashSet();
   protected final LongSet f_75729_ = new LongOpenHashSet();
   protected final LongSet f_75730_ = new LongOpenHashSet();
   protected volatile M f_75731_;
   protected final M f_75732_;
   protected final LongSet f_75733_ = new LongOpenHashSet();
   protected final LongSet f_75734_ = new LongOpenHashSet();
   protected final Long2ObjectMap<DataLayer> f_75735_ = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
   private final LongSet f_75740_ = new LongOpenHashSet();
   private final LongSet f_75741_ = new LongOpenHashSet();
   private final LongSet f_75742_ = new LongOpenHashSet();
   protected volatile boolean f_75736_;

   protected LayerLightSectionStorage(LightLayer p_75745_, LightChunkGetter p_75746_, M p_75747_) {
      super(3, 16, 256);
      this.f_75738_ = p_75745_;
      this.f_75739_ = p_75746_;
      this.f_75732_ = p_75747_;
      this.f_75731_ = p_75747_.m_5972_();
      this.f_75731_.m_75534_();
   }

   protected boolean m_75791_(long p_75792_) {
      return this.m_75758_(p_75792_, true) != null;
   }

   @Nullable
   protected DataLayer m_75758_(long p_75759_, boolean p_75760_) {
      return this.m_75761_((M)(p_75760_ ? this.f_75732_ : this.f_75731_), p_75759_);
   }

   @Nullable
   protected DataLayer m_75761_(M p_75762_, long p_75763_) {
      return p_75762_.m_75532_(p_75763_);
   }

   @Nullable
   public DataLayer m_75793_(long p_75794_) {
      DataLayer datalayer = this.f_75735_.get(p_75794_);
      return datalayer != null ? datalayer : this.m_75758_(p_75794_, false);
   }

   protected abstract int m_6181_(long p_75786_);

   protected int m_75795_(long p_75796_) {
      long i = SectionPos.m_123235_(p_75796_);
      DataLayer datalayer = this.m_75758_(i, true);
      return datalayer.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_75796_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75796_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75796_)));
   }

   protected void m_75772_(long p_75773_, int p_75774_) {
      long i = SectionPos.m_123235_(p_75773_);
      if (this.f_75733_.add(i)) {
         this.f_75732_.m_75524_(i);
      }

      DataLayer datalayer = this.m_75758_(i, true);
      datalayer.m_62564_(SectionPos.m_123207_(BlockPos.m_121983_(p_75773_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75773_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75773_)), p_75774_);

      for(int j = -1; j <= 1; ++j) {
         for(int k = -1; k <= 1; ++k) {
            for(int l = -1; l <= 1; ++l) {
               this.f_75734_.add(SectionPos.m_123235_(BlockPos.m_121910_(p_75773_, k, l, j)));
            }
         }
      }

   }

   protected int m_6172_(long p_75781_) {
      if (p_75781_ == Long.MAX_VALUE) {
         return 2;
      } else if (this.f_75728_.contains(p_75781_)) {
         return 0;
      } else {
         return !this.f_75742_.contains(p_75781_) && this.f_75732_.m_75529_(p_75781_) ? 1 : 2;
      }
   }

   protected int m_7409_(long p_75771_) {
      if (this.f_75729_.contains(p_75771_)) {
         return 2;
      } else {
         return !this.f_75728_.contains(p_75771_) && !this.f_75730_.contains(p_75771_) ? 2 : 0;
      }
   }

   protected void m_7351_(long p_75749_, int p_75750_) {
      int i = this.m_6172_(p_75749_);
      if (i != 0 && p_75750_ == 0) {
         this.f_75728_.add(p_75749_);
         this.f_75730_.remove(p_75749_);
      }

      if (i == 0 && p_75750_ != 0) {
         this.f_75728_.remove(p_75749_);
         this.f_75729_.remove(p_75749_);
      }

      if (i >= 2 && p_75750_ != 2) {
         if (this.f_75742_.contains(p_75749_)) {
            this.f_75742_.remove(p_75749_);
         } else {
            this.f_75732_.m_75526_(p_75749_, this.m_7667_(p_75749_));
            this.f_75733_.add(p_75749_);
            this.m_6177_(p_75749_);

            for(int j = -1; j <= 1; ++j) {
               for(int k = -1; k <= 1; ++k) {
                  for(int l = -1; l <= 1; ++l) {
                     this.f_75734_.add(SectionPos.m_123235_(BlockPos.m_121910_(p_75749_, k, l, j)));
                  }
               }
            }
         }
      }

      if (i != 2 && p_75750_ >= 2) {
         this.f_75742_.add(p_75749_);
      }

      this.f_75736_ = !this.f_75742_.isEmpty();
   }

   protected DataLayer m_7667_(long p_75797_) {
      DataLayer datalayer = this.f_75735_.get(p_75797_);
      return datalayer != null ? datalayer : new DataLayer();
   }

   protected void m_75764_(LayerLightEngine<?, ?> p_75765_, long p_75766_) {
      if (p_75765_.m_75598_() < 8192) {
         p_75765_.m_75581_((p_75753_) -> {
            return SectionPos.m_123235_(p_75753_) == p_75766_;
         });
      } else {
         int i = SectionPos.m_123223_(SectionPos.m_123213_(p_75766_));
         int j = SectionPos.m_123223_(SectionPos.m_123225_(p_75766_));
         int k = SectionPos.m_123223_(SectionPos.m_123230_(p_75766_));

         for(int l = 0; l < 16; ++l) {
            for(int i1 = 0; i1 < 16; ++i1) {
               for(int j1 = 0; j1 < 16; ++j1) {
                  long k1 = BlockPos.m_121882_(i + l, j + i1, k + j1);
                  p_75765_.m_75600_(k1);
               }
            }
         }

      }
   }

   protected boolean m_6808_() {
      return this.f_75736_;
   }

   protected void m_6716_(LayerLightEngine<M, ?> p_75767_, boolean p_75768_, boolean p_75769_) {
      if (this.m_6808_() || !this.f_75735_.isEmpty()) {
         for(long i : this.f_75742_) {
            this.m_75764_(p_75767_, i);
            DataLayer datalayer = this.f_75735_.remove(i);
            DataLayer datalayer1 = this.f_75732_.m_75535_(i);
            if (this.f_75741_.contains(SectionPos.m_123240_(i))) {
               if (datalayer != null) {
                  this.f_75735_.put(i, datalayer);
               } else if (datalayer1 != null) {
                  this.f_75735_.put(i, datalayer1);
               }
            }
         }

         this.f_75732_.m_75531_();

         for(long k : this.f_75742_) {
            this.m_6187_(k);
         }

         this.f_75742_.clear();
         this.f_75736_ = false;

         for(Entry<DataLayer> entry : this.f_75735_.long2ObjectEntrySet()) {
            long j = entry.getLongKey();
            if (this.m_75791_(j)) {
               DataLayer datalayer2 = entry.getValue();
               if (this.f_75732_.m_75532_(j) != datalayer2) {
                  this.m_75764_(p_75767_, j);
                  this.f_75732_.m_75526_(j, datalayer2);
                  this.f_75733_.add(j);
               }
            }
         }

         this.f_75732_.m_75531_();
         if (!p_75769_) {
            for(long l : this.f_75735_.keySet()) {
               this.m_75777_(p_75767_, l);
            }
         } else {
            for(long i1 : this.f_75740_) {
               this.m_75777_(p_75767_, i1);
            }
         }

         this.f_75740_.clear();
         ObjectIterator<Entry<DataLayer>> objectiterator = this.f_75735_.long2ObjectEntrySet().iterator();

         while(objectiterator.hasNext()) {
            Entry<DataLayer> entry1 = objectiterator.next();
            long j1 = entry1.getLongKey();
            if (this.m_75791_(j1)) {
               objectiterator.remove();
            }
         }

      }
   }

   private void m_75777_(LayerLightEngine<M, ?> p_75778_, long p_75779_) {
      if (this.m_75791_(p_75779_)) {
         int i = SectionPos.m_123223_(SectionPos.m_123213_(p_75779_));
         int j = SectionPos.m_123223_(SectionPos.m_123225_(p_75779_));
         int k = SectionPos.m_123223_(SectionPos.m_123230_(p_75779_));

         for(Direction direction : f_75737_) {
            long l = SectionPos.m_123191_(p_75779_, direction);
            if (!this.f_75735_.containsKey(l) && this.m_75791_(l)) {
               for(int i1 = 0; i1 < 16; ++i1) {
                  for(int j1 = 0; j1 < 16; ++j1) {
                     long k1;
                     long l1;
                     switch(direction) {
                     case DOWN:
                        k1 = BlockPos.m_121882_(i + j1, j, k + i1);
                        l1 = BlockPos.m_121882_(i + j1, j - 1, k + i1);
                        break;
                     case UP:
                        k1 = BlockPos.m_121882_(i + j1, j + 16 - 1, k + i1);
                        l1 = BlockPos.m_121882_(i + j1, j + 16, k + i1);
                        break;
                     case NORTH:
                        k1 = BlockPos.m_121882_(i + i1, j + j1, k);
                        l1 = BlockPos.m_121882_(i + i1, j + j1, k - 1);
                        break;
                     case SOUTH:
                        k1 = BlockPos.m_121882_(i + i1, j + j1, k + 16 - 1);
                        l1 = BlockPos.m_121882_(i + i1, j + j1, k + 16);
                        break;
                     case WEST:
                        k1 = BlockPos.m_121882_(i, j + i1, k + j1);
                        l1 = BlockPos.m_121882_(i - 1, j + i1, k + j1);
                        break;
                     default:
                        k1 = BlockPos.m_121882_(i + 16 - 1, j + i1, k + j1);
                        l1 = BlockPos.m_121882_(i + 16, j + i1, k + j1);
                     }

                     p_75778_.m_75576_(k1, l1, p_75778_.m_6359_(k1, l1, p_75778_.m_6172_(k1)), false);
                     p_75778_.m_75576_(l1, k1, p_75778_.m_6359_(l1, k1, p_75778_.m_6172_(l1)), false);
                  }
               }
            }
         }

      }
   }

   protected void m_6177_(long p_75798_) {
   }

   protected void m_6187_(long p_75799_) {
   }

   protected void m_7358_(long p_75775_, boolean p_75776_) {
   }

   public void m_75782_(long p_75783_, boolean p_75784_) {
      if (p_75784_) {
         this.f_75741_.add(p_75783_);
      } else {
         this.f_75741_.remove(p_75783_);
      }

   }

   protected void m_75754_(long p_75755_, @Nullable DataLayer p_75756_, boolean p_75757_) {
      if (p_75756_ != null) {
         this.f_75735_.put(p_75755_, p_75756_);
         if (!p_75757_) {
            this.f_75740_.add(p_75755_);
         }
      } else {
         this.f_75735_.remove(p_75755_);
      }

   }

   protected void m_75787_(long p_75788_, boolean p_75789_) {
      boolean flag = this.f_75728_.contains(p_75788_);
      if (!flag && !p_75789_) {
         this.f_75730_.add(p_75788_);
         this.m_75576_(Long.MAX_VALUE, p_75788_, 0, true);
      }

      if (flag && p_75789_) {
         this.f_75729_.add(p_75788_);
         this.m_75576_(Long.MAX_VALUE, p_75788_, 2, false);
      }

   }

   protected void m_75785_() {
      if (this.m_75587_()) {
         this.m_75588_(Integer.MAX_VALUE);
      }

   }

   protected void m_75790_() {
      if (!this.f_75733_.isEmpty()) {
         M m = this.f_75732_.m_5972_();
         m.m_75534_();
         this.f_75731_ = m;
         this.f_75733_.clear();
      }

      if (!this.f_75734_.isEmpty()) {
         LongIterator longiterator = this.f_75734_.iterator();

         while(longiterator.hasNext()) {
            long i = longiterator.nextLong();
            this.f_75739_.m_6506_(this.f_75738_, SectionPos.m_123184_(i));
         }

         this.f_75734_.clear();
      }

   }
}