package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherWorldCarver extends CaveWorldCarver {
   public NetherWorldCarver(Codec<CaveCarverConfiguration> p_64873_) {
      super(p_64873_);
      this.f_64983_ = ImmutableSet.of(Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_50493_, Blocks.f_50546_, Blocks.f_50599_, Blocks.f_50440_, Blocks.f_50134_, Blocks.f_50135_, Blocks.f_50136_, Blocks.f_50699_, Blocks.f_50690_, Blocks.f_50451_, Blocks.f_50692_, Blocks.f_50137_, Blocks.f_50730_);
      this.f_64984_ = ImmutableSet.of(Fluids.f_76195_, Fluids.f_76193_);
   }

   protected int m_6208_() {
      return 10;
   }

   protected float m_5710_(Random p_64893_) {
      return (p_64893_.nextFloat() * 2.0F + p_64893_.nextFloat()) * 2.0F;
   }

   protected double m_6203_() {
      return 5.0D;
   }

   protected boolean m_141949_(CarvingContext p_159287_, CaveCarverConfiguration p_159288_, ChunkAccess p_159289_, Function<BlockPos, Biome> p_159290_, BitSet p_159291_, Random p_159292_, BlockPos.MutableBlockPos p_159293_, BlockPos.MutableBlockPos p_159294_, Aquifer p_159295_, MutableBoolean p_159296_) {
      if (this.m_65010_(p_159289_.m_8055_(p_159293_))) {
         BlockState blockstate;
         if (p_159293_.m_123342_() <= p_159287_.m_142201_() + 31) {
            blockstate = f_64982_.m_76188_();
         } else {
            blockstate = f_64980_;
         }

         p_159289_.m_6978_(p_159293_, blockstate, false);
         return true;
      } else {
         return false;
      }
   }
}