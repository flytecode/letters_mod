package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class ServerTickList<T> implements TickList<T> {
   public static final int f_151628_ = 65536;
   protected final Predicate<T> f_47207_;
   private final Function<T, ResourceLocation> f_47208_;
   private final Set<TickNextTickData<T>> f_47209_ = Sets.newHashSet();
   private final Set<TickNextTickData<T>> f_47210_ = Sets.newTreeSet(TickNextTickData.m_47337_());
   private final ServerLevel f_47211_;
   private final Queue<TickNextTickData<T>> f_47212_ = Queues.newArrayDeque();
   private final List<TickNextTickData<T>> f_47213_ = Lists.newArrayList();
   private final Consumer<TickNextTickData<T>> f_47214_;

   public ServerTickList(ServerLevel p_47216_, Predicate<T> p_47217_, Function<T, ResourceLocation> p_47218_, Consumer<TickNextTickData<T>> p_47219_) {
      this.f_47207_ = p_47217_;
      this.f_47208_ = p_47218_;
      this.f_47211_ = p_47216_;
      this.f_47214_ = p_47219_;
   }

   public void m_47253_() {
      int i = this.f_47210_.size();
      if (i != this.f_47209_.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (i > 65536) {
            i = 65536;
         }

         Iterator<TickNextTickData<T>> iterator = this.f_47210_.iterator();
         this.f_47211_.m_46473_().m_6180_("cleaning");

         while(i > 0 && iterator.hasNext()) {
            TickNextTickData<T> ticknexttickdata = iterator.next();
            if (ticknexttickdata.f_47324_ > this.f_47211_.m_46467_()) {
               break;
            }

            if (this.f_47211_.m_143336_(ticknexttickdata.f_47323_)) {
               iterator.remove();
               this.f_47209_.remove(ticknexttickdata);
               this.f_47212_.add(ticknexttickdata);
               --i;
            }
         }

         this.f_47211_.m_46473_().m_6182_("ticking");

         TickNextTickData<T> ticknexttickdata1;
         while((ticknexttickdata1 = this.f_47212_.poll()) != null) {
            if (this.f_47211_.m_143336_(ticknexttickdata1.f_47323_)) {
               try {
                  this.f_47213_.add(ticknexttickdata1);
                  this.f_47214_.accept(ticknexttickdata1);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.m_127521_(throwable, "Exception while ticking");
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Block being ticked");
                  CrashReportCategory.m_178950_(crashreportcategory, this.f_47211_, ticknexttickdata1.f_47323_, (BlockState)null);
                  throw new ReportedException(crashreport);
               }
            } else {
               this.m_5945_(ticknexttickdata1.f_47323_, ticknexttickdata1.m_47340_(), 0);
            }
         }

         this.f_47211_.m_46473_().m_7238_();
         this.f_47213_.clear();
         this.f_47212_.clear();
      }
   }

   public boolean m_5913_(BlockPos p_47255_, T p_47256_) {
      return this.f_47212_.contains(new TickNextTickData(p_47255_, p_47256_));
   }

   public List<TickNextTickData<T>> m_47223_(ChunkPos p_47224_, boolean p_47225_, boolean p_47226_) {
      int i = p_47224_.m_45604_() - 2;
      int j = i + 16 + 2;
      int k = p_47224_.m_45605_() - 2;
      int l = k + 16 + 2;
      return this.m_47232_(new BoundingBox(i, this.f_47211_.m_141937_(), k, j, this.f_47211_.m_151558_(), l), p_47225_, p_47226_);
   }

   public List<TickNextTickData<T>> m_47232_(BoundingBox p_47233_, boolean p_47234_, boolean p_47235_) {
      List<TickNextTickData<T>> list = this.m_47244_((List<TickNextTickData<T>>)null, this.f_47210_, p_47233_, p_47234_);
      if (p_47234_ && list != null) {
         this.f_47209_.removeAll(list);
      }

      list = this.m_47244_(list, this.f_47212_, p_47233_, p_47234_);
      if (!p_47235_) {
         list = this.m_47244_(list, this.f_47213_, p_47233_, p_47234_);
      }

      return list == null ? Collections.emptyList() : list;
   }

   @Nullable
   private List<TickNextTickData<T>> m_47244_(@Nullable List<TickNextTickData<T>> p_47245_, Collection<TickNextTickData<T>> p_47246_, BoundingBox p_47247_, boolean p_47248_) {
      Iterator<TickNextTickData<T>> iterator = p_47246_.iterator();

      while(iterator.hasNext()) {
         TickNextTickData<T> ticknexttickdata = iterator.next();
         BlockPos blockpos = ticknexttickdata.f_47323_;
         if (blockpos.m_123341_() >= p_47247_.m_162395_() && blockpos.m_123341_() < p_47247_.m_162399_() && blockpos.m_123343_() >= p_47247_.m_162398_() && blockpos.m_123343_() < p_47247_.m_162401_()) {
            if (p_47248_) {
               iterator.remove();
            }

            if (p_47245_ == null) {
               p_47245_ = Lists.newArrayList();
            }

            p_47245_.add(ticknexttickdata);
         }
      }

      return p_47245_;
   }

   public void m_47229_(BoundingBox p_47230_, BlockPos p_47231_) {
      for(TickNextTickData<T> ticknexttickdata : this.m_47232_(p_47230_, false, false)) {
         if (p_47230_.m_71051_(ticknexttickdata.f_47323_)) {
            BlockPos blockpos = ticknexttickdata.f_47323_.m_141952_(p_47231_);
            T t = ticknexttickdata.m_47340_();
            this.m_47227_(new TickNextTickData<>(blockpos, t, ticknexttickdata.f_47324_, ticknexttickdata.f_47325_));
         }
      }

   }

   public ListTag m_47221_(ChunkPos p_47222_) {
      List<TickNextTickData<T>> list = this.m_47223_(p_47222_, false, true);
      return m_47249_(this.f_47208_, list, this.f_47211_.m_46467_());
   }

   private static <T> ListTag m_47249_(Function<T, ResourceLocation> p_47250_, Iterable<TickNextTickData<T>> p_47251_, long p_47252_) {
      ListTag listtag = new ListTag();

      for(TickNextTickData<T> ticknexttickdata : p_47251_) {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128359_("i", p_47250_.apply(ticknexttickdata.m_47340_()).toString());
         compoundtag.m_128405_("x", ticknexttickdata.f_47323_.m_123341_());
         compoundtag.m_128405_("y", ticknexttickdata.f_47323_.m_123342_());
         compoundtag.m_128405_("z", ticknexttickdata.f_47323_.m_123343_());
         compoundtag.m_128405_("t", (int)(ticknexttickdata.f_47324_ - p_47252_));
         compoundtag.m_128405_("p", ticknexttickdata.f_47325_.m_47363_());
         listtag.add(compoundtag);
      }

      return listtag;
   }

   public boolean m_5916_(BlockPos p_47237_, T p_47238_) {
      return this.f_47209_.contains(new TickNextTickData(p_47237_, p_47238_));
   }

   public void m_7663_(BlockPos p_47240_, T p_47241_, int p_47242_, TickPriority p_47243_) {
      if (!this.f_47207_.test(p_47241_)) {
         this.m_47227_(new TickNextTickData<>(p_47240_, p_47241_, (long)p_47242_ + this.f_47211_.m_46467_(), p_47243_));
      }

   }

   private void m_47227_(TickNextTickData<T> p_47228_) {
      if (!this.f_47209_.contains(p_47228_)) {
         this.f_47209_.add(p_47228_);
         this.f_47210_.add(p_47228_);
      }

   }

   public int m_142536_() {
      return this.f_47209_.size();
   }
}