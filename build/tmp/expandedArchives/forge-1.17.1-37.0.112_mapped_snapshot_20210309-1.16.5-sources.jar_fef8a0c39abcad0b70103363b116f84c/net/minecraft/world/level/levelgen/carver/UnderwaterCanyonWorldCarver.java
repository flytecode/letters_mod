package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class UnderwaterCanyonWorldCarver extends CanyonWorldCarver {
   public UnderwaterCanyonWorldCarver(Codec<CanyonCarverConfiguration> p_64903_) {
      super(p_64903_);
      this.f_64983_ = ImmutableSet.of(Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_50493_, Blocks.f_50546_, Blocks.f_50599_, Blocks.f_50440_, Blocks.f_50352_, Blocks.f_50287_, Blocks.f_50288_, Blocks.f_50289_, Blocks.f_50290_, Blocks.f_50291_, Blocks.f_50292_, Blocks.f_50293_, Blocks.f_50294_, Blocks.f_50295_, Blocks.f_50296_, Blocks.f_50297_, Blocks.f_50298_, Blocks.f_50299_, Blocks.f_50300_, Blocks.f_50301_, Blocks.f_50302_, Blocks.f_50062_, Blocks.f_50394_, Blocks.f_50195_, Blocks.f_50125_, Blocks.f_49992_, Blocks.f_49994_, Blocks.f_49990_, Blocks.f_49991_, Blocks.f_50080_, Blocks.f_50016_, Blocks.f_50627_);
   }

   protected boolean m_141931_(ChunkAccess p_159298_, int p_159299_, int p_159300_, int p_159301_, int p_159302_, int p_159303_, int p_159304_) {
      return false;
   }

   protected boolean m_141949_(CarvingContext p_159306_, CanyonCarverConfiguration p_159307_, ChunkAccess p_159308_, Function<BlockPos, Biome> p_159309_, BitSet p_159310_, Random p_159311_, BlockPos.MutableBlockPos p_159312_, BlockPos.MutableBlockPos p_159313_, Aquifer p_159314_, MutableBoolean p_159315_) {
      return UnderwaterCaveWorldCarver.m_159357_(this, p_159308_, p_159311_, p_159312_, p_159313_, p_159314_);
   }
}