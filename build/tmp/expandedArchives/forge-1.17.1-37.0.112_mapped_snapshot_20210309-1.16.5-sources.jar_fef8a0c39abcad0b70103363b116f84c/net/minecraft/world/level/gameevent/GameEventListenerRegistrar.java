package net.minecraft.world.level.gameevent;

import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;

public class GameEventListenerRegistrar {
   private final GameEventListener f_157850_;
   @Nullable
   private SectionPos f_157851_;

   public GameEventListenerRegistrar(GameEventListener p_157853_) {
      this.f_157850_ = p_157853_;
   }

   public void m_157854_(Level p_157855_) {
      this.m_157856_(p_157855_, this.f_157851_, (p_157867_) -> {
         p_157867_.m_142500_(this.f_157850_);
      });
   }

   public void m_157862_(Level p_157863_) {
      Optional<BlockPos> optional = this.f_157850_.m_142460_().m_142502_(p_157863_);
      if (optional.isPresent()) {
         long i = SectionPos.m_123235_(optional.get().m_121878_());
         if (this.f_157851_ == null || this.f_157851_.m_123252_() != i) {
            SectionPos sectionpos = this.f_157851_;
            this.f_157851_ = SectionPos.m_123184_(i);
            this.m_157856_(p_157863_, sectionpos, (p_157865_) -> {
               p_157865_.m_142500_(this.f_157850_);
            });
            this.m_157856_(p_157863_, this.f_157851_, (p_157861_) -> {
               p_157861_.m_142501_(this.f_157850_);
            });
         }
      }

   }

   private void m_157856_(Level p_157857_, @Nullable SectionPos p_157858_, Consumer<GameEventDispatcher> p_157859_) {
      if (p_157858_ != null) {
         ChunkAccess chunkaccess = p_157857_.m_6522_(p_157858_.m_123170_(), p_157858_.m_123222_(), ChunkStatus.f_62326_, false);
         if (chunkaccess != null) {
            p_157859_.accept(chunkaccess.m_142336_(p_157858_.m_123206_()));
         }

      }
   }
}