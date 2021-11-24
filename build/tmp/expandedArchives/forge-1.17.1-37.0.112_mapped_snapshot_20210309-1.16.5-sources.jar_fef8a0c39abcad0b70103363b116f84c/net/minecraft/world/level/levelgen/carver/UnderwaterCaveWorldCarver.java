package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class UnderwaterCaveWorldCarver extends CaveWorldCarver {
   public UnderwaterCaveWorldCarver(Codec<CaveCarverConfiguration> p_64932_) {
      super(p_64932_);
      this.f_64983_ = ImmutableSet.of(Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_50493_, Blocks.f_50546_, Blocks.f_50599_, Blocks.f_50440_, Blocks.f_50352_, Blocks.f_50287_, Blocks.f_50288_, Blocks.f_50289_, Blocks.f_50290_, Blocks.f_50291_, Blocks.f_50292_, Blocks.f_50293_, Blocks.f_50294_, Blocks.f_50295_, Blocks.f_50296_, Blocks.f_50297_, Blocks.f_50298_, Blocks.f_50299_, Blocks.f_50300_, Blocks.f_50301_, Blocks.f_50302_, Blocks.f_50062_, Blocks.f_50394_, Blocks.f_50195_, Blocks.f_50125_, Blocks.f_49992_, Blocks.f_49994_, Blocks.f_49990_, Blocks.f_49991_, Blocks.f_50080_, Blocks.f_50354_);
   }

   protected boolean m_141931_(ChunkAccess p_159328_, int p_159329_, int p_159330_, int p_159331_, int p_159332_, int p_159333_, int p_159334_) {
      return false;
   }

   protected boolean m_141949_(CarvingContext p_159347_, CaveCarverConfiguration p_159348_, ChunkAccess p_159349_, Function<BlockPos, Biome> p_159350_, BitSet p_159351_, Random p_159352_, BlockPos.MutableBlockPos p_159353_, BlockPos.MutableBlockPos p_159354_, Aquifer p_159355_, MutableBoolean p_159356_) {
      return m_159357_(this, p_159349_, p_159352_, p_159353_, p_159354_, p_159355_);
   }

   protected static boolean m_159357_(WorldCarver<?> p_159358_, ChunkAccess p_159359_, Random p_159360_, BlockPos.MutableBlockPos p_159361_, BlockPos.MutableBlockPos p_159362_, Aquifer p_159363_) {
      if (p_159363_.m_142419_(WorldCarver.f_159364_, p_159361_.m_123341_(), p_159361_.m_123342_(), p_159361_.m_123343_(), Double.NEGATIVE_INFINITY).m_60795_()) {
         return false;
      } else {
         BlockState blockstate = p_159359_.m_8055_(p_159361_);
         if (!p_159358_.m_65010_(blockstate)) {
            return false;
         } else if (p_159361_.m_123342_() == 10) {
            float f = p_159360_.nextFloat();
            if ((double)f < 0.25D) {
               p_159359_.m_6978_(p_159361_, Blocks.f_50450_.m_49966_(), false);
               p_159359_.m_5782_().m_5945_(p_159361_, Blocks.f_50450_, 0);
            } else {
               p_159359_.m_6978_(p_159361_, Blocks.f_50080_.m_49966_(), false);
            }

            return true;
         } else if (p_159361_.m_123342_() < 10) {
            p_159359_.m_6978_(p_159361_, Blocks.f_49991_.m_49966_(), false);
            return false;
         } else {
            p_159359_.m_6978_(p_159361_, f_64981_.m_76188_(), false);
            int i = p_159359_.m_7697_().f_45578_;
            int j = p_159359_.m_7697_().f_45579_;

            for(Direction direction : LiquidBlock.f_181233_) {
               p_159362_.m_122159_(p_159361_, direction);
               if (SectionPos.m_123171_(p_159362_.m_123341_()) != i || SectionPos.m_123171_(p_159362_.m_123343_()) != j || p_159359_.m_8055_(p_159362_).m_60795_()) {
                  p_159359_.m_5783_().m_5945_(p_159361_, f_64981_.m_76152_(), 0);
                  break;
               }
            }

            return true;
         }
      }
   }
}