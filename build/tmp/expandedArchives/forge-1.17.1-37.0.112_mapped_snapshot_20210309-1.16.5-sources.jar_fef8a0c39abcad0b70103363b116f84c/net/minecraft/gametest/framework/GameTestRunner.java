package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameTestRunner {
   private static final int f_177525_ = 100;
   public static final int f_177521_ = 2;
   public static final int f_177522_ = 5;
   public static final int f_177523_ = 6;
   public static final int f_177524_ = 8;

   public static void m_127742_(GameTestInfo p_127743_, BlockPos p_127744_, GameTestTicker p_127745_) {
      p_127743_.m_127616_();
      p_127745_.m_127788_(p_127743_);
      p_127743_.m_127624_(new ReportGameListener(p_127743_, p_127745_, p_127744_));
      p_127743_.m_127619_(p_127744_, 2);
   }

   public static Collection<GameTestInfo> m_127726_(Collection<GameTestBatch> p_127727_, BlockPos p_127728_, Rotation p_127729_, ServerLevel p_127730_, GameTestTicker p_127731_, int p_127732_) {
      GameTestBatchRunner gametestbatchrunner = new GameTestBatchRunner(p_127727_, p_127728_, p_127729_, p_127730_, p_127731_, p_127732_);
      gametestbatchrunner.m_127583_();
      return gametestbatchrunner.m_127569_();
   }

   public static Collection<GameTestInfo> m_127752_(Collection<TestFunction> p_127753_, BlockPos p_127754_, Rotation p_127755_, ServerLevel p_127756_, GameTestTicker p_127757_, int p_127758_) {
      return m_127726_(m_127724_(p_127753_), p_127754_, p_127755_, p_127756_, p_127757_, p_127758_);
   }

   public static Collection<GameTestBatch> m_127724_(Collection<TestFunction> p_127725_) {
      Map<String, List<TestFunction>> map = p_127725_.stream().collect(Collectors.groupingBy(TestFunction::m_128081_));
      return map.entrySet().stream().flatMap((p_177537_) -> {
         String s = p_177537_.getKey();
         Consumer<ServerLevel> consumer = GameTestRegistry.m_127676_(s);
         Consumer<ServerLevel> consumer1 = GameTestRegistry.m_177517_(s);
         MutableInt mutableint = new MutableInt();
         Collection<TestFunction> collection = p_177537_.getValue();
         return Streams.stream(Iterables.partition(collection, 100)).map((p_177535_) -> {
            return new GameTestBatch(s + ":" + mutableint.incrementAndGet(), ImmutableList.copyOf(p_177535_), consumer, consumer1);
         });
      }).collect(ImmutableList.toImmutableList());
   }

   public static void m_127694_(ServerLevel p_127695_, BlockPos p_127696_, GameTestTicker p_127697_, int p_127698_) {
      p_127697_.m_127787_();
      BlockPos blockpos = p_127696_.m_142082_(-p_127698_, 0, -p_127698_);
      BlockPos blockpos1 = p_127696_.m_142082_(p_127698_, 0, p_127698_);
      BlockPos.m_121990_(blockpos, blockpos1).filter((p_177540_) -> {
         return p_127695_.m_8055_(p_177540_).m_60713_(Blocks.f_50677_);
      }).forEach((p_177529_) -> {
         StructureBlockEntity structureblockentity = (StructureBlockEntity)p_127695_.m_7702_(p_177529_);
         BlockPos blockpos2 = structureblockentity.m_58899_();
         BoundingBox boundingbox = StructureUtils.m_127904_(structureblockentity);
         StructureUtils.m_127849_(boundingbox, blockpos2.m_123342_(), p_127695_);
      });
   }

   public static void m_127685_(ServerLevel p_127686_) {
      DebugPackets.m_133674_(p_127686_);
   }
}