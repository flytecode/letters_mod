package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Stitcher {
   private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger();

   private static final Comparator<Stitcher.Holder> f_118161_ = Comparator.<Stitcher.Holder, Integer>comparing((p_118201_) -> {
      return -p_118201_.f_118204_;
   }).thenComparing((p_118199_) -> {
      return -p_118199_.f_118203_;
   }).thenComparing((p_118197_) -> {
      return p_118197_.f_118202_.m_118431_();
   });
   private final int f_118162_;
   private final Set<Stitcher.Holder> f_118163_ = Sets.newHashSetWithExpectedSize(256);
   private final List<Stitcher.Region> f_118164_ = Lists.newArrayListWithCapacity(256);
   private int f_118165_;
   private int f_118166_;
   private final int f_118167_;
   private final int f_118168_;

   public Stitcher(int p_118171_, int p_118172_, int p_118173_) {
      this.f_118162_ = p_118173_;
      this.f_118167_ = p_118171_;
      this.f_118168_ = p_118172_;
   }

   public int m_118174_() {
      return this.f_118165_;
   }

   public int m_118187_() {
      return this.f_118166_;
   }

   public void m_118185_(TextureAtlasSprite.Info p_118186_) {
      Stitcher.Holder stitcher$holder = new Stitcher.Holder(p_118186_, this.f_118162_);
      this.f_118163_.add(stitcher$holder);
   }

   public void m_118193_() {
      List<Stitcher.Holder> list = Lists.newArrayList(this.f_118163_);
      list.sort(f_118161_);

      for(Stitcher.Holder stitcher$holder : list) {
         if (!this.m_118178_(stitcher$holder)) {
            LOGGER.info(new net.minecraftforge.fml.loading.AdvancedLogMessageAdapter(sb->{
               sb.append("Unable to fit: ").append(stitcher$holder.f_118202_.m_118431_());
               sb.append(" - size: ").append(stitcher$holder.f_118202_.m_118434_()).append("x").append(stitcher$holder.f_118202_.m_118437_());
               sb.append(" - Maybe try a lower resolution resourcepack?\n");
               list.forEach(h-> sb.append("\t").append(h).append("\n"));
            }));
            throw new StitcherException(stitcher$holder.f_118202_, list.stream().map((p_118195_) -> {
               return p_118195_.f_118202_;
            }).collect(ImmutableList.toImmutableList()));
         }
      }

      this.f_118165_ = Mth.m_14125_(this.f_118165_);
      this.f_118166_ = Mth.m_14125_(this.f_118166_);
   }

   public void m_118180_(Stitcher.SpriteLoader p_118181_) {
      for(Stitcher.Region stitcher$region : this.f_118164_) {
         stitcher$region.m_118223_((p_118184_) -> {
            Stitcher.Holder stitcher$holder = p_118184_.m_118220_();
            TextureAtlasSprite.Info textureatlassprite$info = stitcher$holder.f_118202_;
            p_118181_.m_118228_(textureatlassprite$info, this.f_118165_, this.f_118166_, p_118184_.m_118225_(), p_118184_.m_118226_());
         });
      }

   }

   static int m_118188_(int p_118189_, int p_118190_) {
      return (p_118189_ >> p_118190_) + ((p_118189_ & (1 << p_118190_) - 1) == 0 ? 0 : 1) << p_118190_;
   }

   private boolean m_118178_(Stitcher.Holder p_118179_) {
      for(Stitcher.Region stitcher$region : this.f_118164_) {
         if (stitcher$region.m_118221_(p_118179_)) {
            return true;
         }
      }

      return this.m_118191_(p_118179_);
   }

   private boolean m_118191_(Stitcher.Holder p_118192_) {
      int i = Mth.m_14125_(this.f_118165_);
      int j = Mth.m_14125_(this.f_118166_);
      int k = Mth.m_14125_(this.f_118165_ + p_118192_.f_118203_);
      int l = Mth.m_14125_(this.f_118166_ + p_118192_.f_118204_);
      boolean flag1 = k <= this.f_118167_;
      boolean flag2 = l <= this.f_118168_;
      if (!flag1 && !flag2) {
         return false;
      } else {
         boolean flag3 = flag1 && i != k;
         boolean flag4 = flag2 && j != l;
         boolean flag;
         if (flag3 ^ flag4) {
            flag = !flag3 && flag1; // Forge: Fix stitcher not expanding entire height before growing width, and (potentially) growing larger then the max size.
         } else {
            flag = flag1 && i <= j;
         }

         Stitcher.Region stitcher$region;
         if (flag) {
            if (this.f_118166_ == 0) {
               this.f_118166_ = p_118192_.f_118204_;
            }

            stitcher$region = new Stitcher.Region(this.f_118165_, 0, p_118192_.f_118203_, this.f_118166_);
            this.f_118165_ += p_118192_.f_118203_;
         } else {
            stitcher$region = new Stitcher.Region(0, this.f_118166_, this.f_118165_, p_118192_.f_118204_);
            this.f_118166_ += p_118192_.f_118204_;
         }

         stitcher$region.m_118221_(p_118192_);
         this.f_118164_.add(stitcher$region);
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Holder {
      public final TextureAtlasSprite.Info f_118202_;
      public final int f_118203_;
      public final int f_118204_;

      public Holder(TextureAtlasSprite.Info p_118206_, int p_118207_) {
         this.f_118202_ = p_118206_;
         this.f_118203_ = Stitcher.m_118188_(p_118206_.m_118434_(), p_118207_);
         this.f_118204_ = Stitcher.m_118188_(p_118206_.m_118437_(), p_118207_);
      }

      public String toString() {
         return "Holder{width=" + this.f_118203_ + ", height=" + this.f_118204_ + ", name=" + this.f_118202_.m_118431_() + '}';
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Region {
      private final int f_118209_;
      private final int f_118210_;
      private final int f_118211_;
      private final int f_118212_;
      private List<Stitcher.Region> f_118213_;
      private Stitcher.Holder f_118214_;

      public Region(int p_118216_, int p_118217_, int p_118218_, int p_118219_) {
         this.f_118209_ = p_118216_;
         this.f_118210_ = p_118217_;
         this.f_118211_ = p_118218_;
         this.f_118212_ = p_118219_;
      }

      public Stitcher.Holder m_118220_() {
         return this.f_118214_;
      }

      public int m_118225_() {
         return this.f_118209_;
      }

      public int m_118226_() {
         return this.f_118210_;
      }

      public boolean m_118221_(Stitcher.Holder p_118222_) {
         if (this.f_118214_ != null) {
            return false;
         } else {
            int i = p_118222_.f_118203_;
            int j = p_118222_.f_118204_;
            if (i <= this.f_118211_ && j <= this.f_118212_) {
               if (i == this.f_118211_ && j == this.f_118212_) {
                  this.f_118214_ = p_118222_;
                  return true;
               } else {
                  if (this.f_118213_ == null) {
                     this.f_118213_ = Lists.newArrayListWithCapacity(1);
                     this.f_118213_.add(new Stitcher.Region(this.f_118209_, this.f_118210_, i, j));
                     int k = this.f_118211_ - i;
                     int l = this.f_118212_ - j;
                     if (l > 0 && k > 0) {
                        int i1 = Math.max(this.f_118212_, k);
                        int j1 = Math.max(this.f_118211_, l);
                        if (i1 >= j1) {
                           this.f_118213_.add(new Stitcher.Region(this.f_118209_, this.f_118210_ + j, i, l));
                           this.f_118213_.add(new Stitcher.Region(this.f_118209_ + i, this.f_118210_, k, this.f_118212_));
                        } else {
                           this.f_118213_.add(new Stitcher.Region(this.f_118209_ + i, this.f_118210_, k, j));
                           this.f_118213_.add(new Stitcher.Region(this.f_118209_, this.f_118210_ + j, this.f_118211_, l));
                        }
                     } else if (k == 0) {
                        this.f_118213_.add(new Stitcher.Region(this.f_118209_, this.f_118210_ + j, i, l));
                     } else if (l == 0) {
                        this.f_118213_.add(new Stitcher.Region(this.f_118209_ + i, this.f_118210_, k, j));
                     }
                  }

                  for(Stitcher.Region stitcher$region : this.f_118213_) {
                     if (stitcher$region.m_118221_(p_118222_)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void m_118223_(Consumer<Stitcher.Region> p_118224_) {
         if (this.f_118214_ != null) {
            p_118224_.accept(this);
         } else if (this.f_118213_ != null) {
            for(Stitcher.Region stitcher$region : this.f_118213_) {
               stitcher$region.m_118223_(p_118224_);
            }
         }

      }

      public String toString() {
         return "Slot{originX=" + this.f_118209_ + ", originY=" + this.f_118210_ + ", width=" + this.f_118211_ + ", height=" + this.f_118212_ + ", texture=" + this.f_118214_ + ", subSlots=" + this.f_118213_ + "}";
      }
   }

   @OnlyIn(Dist.CLIENT)
   public interface SpriteLoader {
      void m_118228_(TextureAtlasSprite.Info p_118229_, int p_118230_, int p_118231_, int p_118232_, int p_118233_);
   }
}
