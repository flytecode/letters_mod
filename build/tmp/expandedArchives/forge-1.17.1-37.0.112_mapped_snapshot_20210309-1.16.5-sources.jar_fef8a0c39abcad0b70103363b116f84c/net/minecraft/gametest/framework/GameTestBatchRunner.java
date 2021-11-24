package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameTestBatchRunner {
   private static final Logger f_127550_ = LogManager.getLogger();
   private final BlockPos f_127551_;
   final ServerLevel f_127552_;
   private final GameTestTicker f_127553_;
   private final int f_127554_;
   private final List<GameTestInfo> f_127555_;
   private final List<Pair<GameTestBatch, Collection<GameTestInfo>>> f_127557_;
   private final BlockPos.MutableBlockPos f_127560_;

   public GameTestBatchRunner(Collection<GameTestBatch> p_127563_, BlockPos p_127564_, Rotation p_127565_, ServerLevel p_127566_, GameTestTicker p_127567_, int p_127568_) {
      this.f_127560_ = p_127564_.m_122032_();
      this.f_127551_ = p_127564_;
      this.f_127552_ = p_127566_;
      this.f_127553_ = p_127567_;
      this.f_127554_ = p_127568_;
      this.f_127557_ = p_127563_.stream().map((p_177068_) -> {
         Collection<GameTestInfo> collection = p_177068_.m_127549_().stream().map((p_177072_) -> {
            return new GameTestInfo(p_177072_, p_127565_, p_127566_);
         }).collect(ImmutableList.toImmutableList());
         return Pair.of(p_177068_, collection);
      }).collect(ImmutableList.toImmutableList());
      this.f_127555_ = this.f_127557_.stream().flatMap((p_177074_) -> {
         return p_177074_.getSecond().stream();
      }).collect(ImmutableList.toImmutableList());
   }

   public List<GameTestInfo> m_127569_() {
      return this.f_127555_;
   }

   public void m_127583_() {
      this.m_127570_(0);
   }

   void m_127570_(final int p_127571_) {
      if (p_127571_ < this.f_127557_.size()) {
         Pair<GameTestBatch, Collection<GameTestInfo>> pair = this.f_127557_.get(p_127571_);
         final GameTestBatch gametestbatch = pair.getFirst();
         Collection<GameTestInfo> collection = pair.getSecond();
         Map<GameTestInfo, BlockPos> map = this.m_177075_(collection);
         String s = gametestbatch.m_127546_();
         f_127550_.info("Running test batch '{}' ({} tests)...", s, collection.size());
         gametestbatch.m_127547_(this.f_127552_);
         final MultipleTestTracker multipletesttracker = new MultipleTestTracker();
         collection.forEach(multipletesttracker::m_127809_);
         multipletesttracker.m_127811_(new GameTestListener() {
            private void m_177088_() {
               if (multipletesttracker.m_127821_()) {
                  gametestbatch.m_177063_(GameTestBatchRunner.this.f_127552_);
                  GameTestBatchRunner.this.m_127570_(p_127571_ + 1);
               }

            }

            public void m_8073_(GameTestInfo p_127590_) {
            }

            public void m_142378_(GameTestInfo p_177090_) {
               this.m_177088_();
            }

            public void m_8066_(GameTestInfo p_127592_) {
               this.m_177088_();
            }
         });
         collection.forEach((p_177079_) -> {
            BlockPos blockpos = map.get(p_177079_);
            GameTestRunner.m_127742_(p_177079_, blockpos, this.f_127553_);
         });
      }
   }

   private Map<GameTestInfo, BlockPos> m_177075_(Collection<GameTestInfo> p_177076_) {
      Map<GameTestInfo, BlockPos> map = Maps.newHashMap();
      int i = 0;
      AABB aabb = new AABB(this.f_127560_);

      for(GameTestInfo gametestinfo : p_177076_) {
         BlockPos blockpos = new BlockPos(this.f_127560_);
         StructureBlockEntity structureblockentity = StructureUtils.m_127883_(gametestinfo.m_127645_(), blockpos, gametestinfo.m_127646_(), 2, this.f_127552_, true);
         AABB aabb1 = StructureUtils.m_127847_(structureblockentity);
         gametestinfo.m_127617_(structureblockentity.m_58899_());
         map.put(gametestinfo, new BlockPos(this.f_127560_));
         aabb = aabb.m_82367_(aabb1);
         this.f_127560_.m_122184_((int)aabb1.m_82362_() + 5, 0, 0);
         if (i++ % this.f_127554_ == this.f_127554_ - 1) {
            this.f_127560_.m_122184_(0, 0, (int)aabb.m_82385_() + 6);
            this.f_127560_.m_142451_(this.f_127551_.m_123341_());
            aabb = new AABB(this.f_127560_);
         }
      }

      return map;
   }
}