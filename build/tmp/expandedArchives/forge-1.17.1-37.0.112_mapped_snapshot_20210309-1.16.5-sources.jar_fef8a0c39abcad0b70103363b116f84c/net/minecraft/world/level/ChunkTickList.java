package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public class ChunkTickList<T> implements TickList<T> {
   private final List<ChunkTickList.ScheduledTick<T>> f_45631_;
   private final Function<T, ResourceLocation> f_45632_;

   public ChunkTickList(Function<T, ResourceLocation> p_45637_, List<TickNextTickData<T>> p_45638_, long p_45639_) {
      this(p_45637_, p_45638_.stream().map((p_45642_) -> {
         return new ChunkTickList.ScheduledTick<>(p_45642_.m_47340_(), p_45642_.f_47323_, (int)(p_45642_.f_47324_ - p_45639_), p_45642_.f_47325_);
      }).collect(Collectors.toList()));
   }

   private ChunkTickList(Function<T, ResourceLocation> p_45634_, List<ChunkTickList.ScheduledTick<T>> p_45635_) {
      this.f_45631_ = p_45635_;
      this.f_45632_ = p_45634_;
   }

   public boolean m_5916_(BlockPos p_45649_, T p_45650_) {
      return false;
   }

   public void m_7663_(BlockPos p_45652_, T p_45653_, int p_45654_, TickPriority p_45655_) {
      this.f_45631_.add(new ChunkTickList.ScheduledTick<>(p_45653_, p_45652_, p_45654_, p_45655_));
   }

   public boolean m_5913_(BlockPos p_45662_, T p_45663_) {
      return false;
   }

   public ListTag m_45660_() {
      ListTag listtag = new ListTag();

      for(ChunkTickList.ScheduledTick<T> scheduledtick : this.f_45631_) {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128359_("i", this.f_45632_.apply(scheduledtick.f_45667_).toString());
         compoundtag.m_128405_("x", scheduledtick.f_45664_.m_123341_());
         compoundtag.m_128405_("y", scheduledtick.f_45664_.m_123342_());
         compoundtag.m_128405_("z", scheduledtick.f_45664_.m_123343_());
         compoundtag.m_128405_("t", scheduledtick.f_45665_);
         compoundtag.m_128405_("p", scheduledtick.f_45666_.m_47363_());
         listtag.add(compoundtag);
      }

      return listtag;
   }

   public static <T> ChunkTickList<T> m_45656_(ListTag p_45657_, Function<T, ResourceLocation> p_45658_, Function<ResourceLocation, T> p_45659_) {
      List<ChunkTickList.ScheduledTick<T>> list = Lists.newArrayList();

      for(int i = 0; i < p_45657_.size(); ++i) {
         CompoundTag compoundtag = p_45657_.m_128728_(i);
         T t = p_45659_.apply(new ResourceLocation(compoundtag.m_128461_("i")));
         if (t != null) {
            BlockPos blockpos = new BlockPos(compoundtag.m_128451_("x"), compoundtag.m_128451_("y"), compoundtag.m_128451_("z"));
            list.add(new ChunkTickList.ScheduledTick<>(t, blockpos, compoundtag.m_128451_("t"), TickPriority.m_47364_(compoundtag.m_128451_("p"))));
         }
      }

      return new ChunkTickList<>(p_45658_, list);
   }

   public void m_45643_(TickList<T> p_45644_) {
      this.f_45631_.forEach((p_45647_) -> {
         p_45644_.m_7663_(p_45647_.f_45664_, p_45647_.f_45667_, p_45647_.f_45665_, p_45647_.f_45666_);
      });
   }

   public int m_142536_() {
      return this.f_45631_.size();
   }

   static class ScheduledTick<T> {
      final T f_45667_;
      public final BlockPos f_45664_;
      public final int f_45665_;
      public final TickPriority f_45666_;

      ScheduledTick(T p_45669_, BlockPos p_45670_, int p_45671_, TickPriority p_45672_) {
         this.f_45667_ = p_45669_;
         this.f_45664_ = p_45670_;
         this.f_45665_ = p_45671_;
         this.f_45666_ = p_45672_;
      }

      public String toString() {
         return this.f_45667_ + ": " + this.f_45664_ + ", " + this.f_45665_ + ", " + this.f_45666_;
      }
   }
}