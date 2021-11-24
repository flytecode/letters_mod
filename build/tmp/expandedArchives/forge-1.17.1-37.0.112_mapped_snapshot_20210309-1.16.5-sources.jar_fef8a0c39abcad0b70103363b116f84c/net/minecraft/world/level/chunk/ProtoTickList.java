package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;

public class ProtoTickList<T> implements TickList<T> {
   protected final Predicate<T> f_63295_;
   private final ChunkPos f_63296_;
   private final ShortList[] f_63297_;
   private LevelHeightAccessor f_156493_;

   public ProtoTickList(Predicate<T> p_156495_, ChunkPos p_156496_, LevelHeightAccessor p_156497_) {
      this(p_156495_, p_156496_, new ListTag(), p_156497_);
   }

   public ProtoTickList(Predicate<T> p_156499_, ChunkPos p_156500_, ListTag p_156501_, LevelHeightAccessor p_156502_) {
      this.f_63295_ = p_156499_;
      this.f_63296_ = p_156500_;
      this.f_156493_ = p_156502_;
      this.f_63297_ = new ShortList[p_156502_.m_151559_()];

      for(int i = 0; i < p_156501_.size(); ++i) {
         ListTag listtag = p_156501_.m_128744_(i);

         for(int j = 0; j < listtag.size(); ++j) {
            ChunkAccess.m_62095_(this.f_63297_, i).add(listtag.m_128757_(j));
         }
      }

   }

   public ListTag m_63316_() {
      return ChunkSerializer.m_63490_(this.f_63297_);
   }

   public void m_63305_(TickList<T> p_63306_, Function<BlockPos, T> p_63307_) {
      for(int i = 0; i < this.f_63297_.length; ++i) {
         if (this.f_63297_[i] != null) {
            for(Short oshort : this.f_63297_[i]) {
               BlockPos blockpos = ProtoChunk.m_63227_(oshort, this.f_156493_.m_151568_(i), this.f_63296_);
               p_63306_.m_5945_(blockpos, p_63307_.apply(blockpos), 0);
            }

            this.f_63297_[i].clear();
         }
      }

   }

   public boolean m_5916_(BlockPos p_63309_, T p_63310_) {
      return false;
   }

   public void m_7663_(BlockPos p_63312_, T p_63313_, int p_63314_, TickPriority p_63315_) {
      int i = this.f_156493_.m_151564_(p_63312_.m_123342_());
      if (i >= 0 && i < this.f_156493_.m_151559_()) {
         ChunkAccess.m_62095_(this.f_63297_, i).add(ProtoChunk.m_63280_(p_63312_));
      }
   }

   public boolean m_5913_(BlockPos p_63318_, T p_63319_) {
      return false;
   }

   public int m_142536_() {
      return Stream.of(this.f_63297_).filter(Objects::nonNull).mapToInt(List::size).sum();
   }
}