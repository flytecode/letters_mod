package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.phys.AABB;

public class SpikeFeature extends Feature<SpikeConfiguration> {
   public static final int f_160369_ = 10;
   private static final int f_160370_ = 42;
   private static final LoadingCache<Long, List<SpikeFeature.EndSpike>> f_66849_ = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new SpikeFeature.SpikeCacheLoader());

   public SpikeFeature(Codec<SpikeConfiguration> p_66852_) {
      super(p_66852_);
   }

   public static List<SpikeFeature.EndSpike> m_66858_(WorldGenLevel p_66859_) {
      Random random = new Random(p_66859_.m_7328_());
      long i = random.nextLong() & 65535L;
      return f_66849_.getUnchecked(i);
   }

   public boolean m_142674_(FeaturePlaceContext<SpikeConfiguration> p_160372_) {
      SpikeConfiguration spikeconfiguration = p_160372_.m_159778_();
      WorldGenLevel worldgenlevel = p_160372_.m_159774_();
      Random random = p_160372_.m_159776_();
      BlockPos blockpos = p_160372_.m_159777_();
      List<SpikeFeature.EndSpike> list = spikeconfiguration.m_68119_();
      if (list.isEmpty()) {
         list = m_66858_(worldgenlevel);
      }

      for(SpikeFeature.EndSpike spikefeature$endspike : list) {
         if (spikefeature$endspike.m_66891_(blockpos)) {
            this.m_66853_(worldgenlevel, random, spikeconfiguration, spikefeature$endspike);
         }
      }

      return true;
   }

   private void m_66853_(ServerLevelAccessor p_66854_, Random p_66855_, SpikeConfiguration p_66856_, SpikeFeature.EndSpike p_66857_) {
      int i = p_66857_.m_66896_();

      for(BlockPos blockpos : BlockPos.m_121940_(new BlockPos(p_66857_.m_66886_() - i, p_66854_.m_141937_(), p_66857_.m_66893_() - i), new BlockPos(p_66857_.m_66886_() + i, p_66857_.m_66899_() + 10, p_66857_.m_66893_() + i))) {
         if (blockpos.m_123299_((double)p_66857_.m_66886_(), (double)blockpos.m_123342_(), (double)p_66857_.m_66893_(), false) <= (double)(i * i + 1) && blockpos.m_123342_() < p_66857_.m_66899_()) {
            this.m_5974_(p_66854_, blockpos, Blocks.f_50080_.m_49966_());
         } else if (blockpos.m_123342_() > 65) {
            this.m_5974_(p_66854_, blockpos, Blocks.f_50016_.m_49966_());
         }
      }

      if (p_66857_.m_66902_()) {
         int j1 = -2;
         int k1 = 2;
         int j = 3;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k = -2; k <= 2; ++k) {
            for(int l = -2; l <= 2; ++l) {
               for(int i1 = 0; i1 <= 3; ++i1) {
                  boolean flag = Mth.m_14040_(k) == 2;
                  boolean flag1 = Mth.m_14040_(l) == 2;
                  boolean flag2 = i1 == 3;
                  if (flag || flag1 || flag2) {
                     boolean flag3 = k == -2 || k == 2 || flag2;
                     boolean flag4 = l == -2 || l == 2 || flag2;
                     BlockState blockstate = Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, Boolean.valueOf(flag3 && l != -2)).m_61124_(IronBarsBlock.f_52311_, Boolean.valueOf(flag3 && l != 2)).m_61124_(IronBarsBlock.f_52312_, Boolean.valueOf(flag4 && k != -2)).m_61124_(IronBarsBlock.f_52310_, Boolean.valueOf(flag4 && k != 2));
                     this.m_5974_(p_66854_, blockpos$mutableblockpos.m_122178_(p_66857_.m_66886_() + k, p_66857_.m_66899_() + i1, p_66857_.m_66893_() + l), blockstate);
                  }
               }
            }
         }
      }

      EndCrystal endcrystal = EntityType.f_20564_.m_20615_(p_66854_.m_6018_());
      endcrystal.m_31052_(p_66856_.m_68122_());
      endcrystal.m_20331_(p_66856_.m_68116_());
      endcrystal.m_7678_((double)p_66857_.m_66886_() + 0.5D, (double)(p_66857_.m_66899_() + 1), (double)p_66857_.m_66893_() + 0.5D, p_66855_.nextFloat() * 360.0F, 0.0F);
      p_66854_.m_7967_(endcrystal);
      this.m_5974_(p_66854_, new BlockPos(p_66857_.m_66886_(), p_66857_.m_66899_(), p_66857_.m_66893_()), Blocks.f_50752_.m_49966_());
   }

   public static class EndSpike {
      public static final Codec<SpikeFeature.EndSpike> f_66872_ = RecordCodecBuilder.create((p_66890_) -> {
         return p_66890_.group(Codec.INT.fieldOf("centerX").orElse(0).forGetter((p_160382_) -> {
            return p_160382_.f_66873_;
         }), Codec.INT.fieldOf("centerZ").orElse(0).forGetter((p_160380_) -> {
            return p_160380_.f_66874_;
         }), Codec.INT.fieldOf("radius").orElse(0).forGetter((p_160378_) -> {
            return p_160378_.f_66875_;
         }), Codec.INT.fieldOf("height").orElse(0).forGetter((p_160376_) -> {
            return p_160376_.f_66876_;
         }), Codec.BOOL.fieldOf("guarded").orElse(false).forGetter((p_160374_) -> {
            return p_160374_.f_66877_;
         })).apply(p_66890_, SpikeFeature.EndSpike::new);
      });
      private final int f_66873_;
      private final int f_66874_;
      private final int f_66875_;
      private final int f_66876_;
      private final boolean f_66877_;
      private final AABB f_66878_;

      public EndSpike(int p_66881_, int p_66882_, int p_66883_, int p_66884_, boolean p_66885_) {
         this.f_66873_ = p_66881_;
         this.f_66874_ = p_66882_;
         this.f_66875_ = p_66883_;
         this.f_66876_ = p_66884_;
         this.f_66877_ = p_66885_;
         this.f_66878_ = new AABB((double)(p_66881_ - p_66883_), (double)DimensionType.f_156653_, (double)(p_66882_ - p_66883_), (double)(p_66881_ + p_66883_), (double)DimensionType.f_156652_, (double)(p_66882_ + p_66883_));
      }

      public boolean m_66891_(BlockPos p_66892_) {
         return SectionPos.m_123171_(p_66892_.m_123341_()) == SectionPos.m_123171_(this.f_66873_) && SectionPos.m_123171_(p_66892_.m_123343_()) == SectionPos.m_123171_(this.f_66874_);
      }

      public int m_66886_() {
         return this.f_66873_;
      }

      public int m_66893_() {
         return this.f_66874_;
      }

      public int m_66896_() {
         return this.f_66875_;
      }

      public int m_66899_() {
         return this.f_66876_;
      }

      public boolean m_66902_() {
         return this.f_66877_;
      }

      public AABB m_66905_() {
         return this.f_66878_;
      }
   }

   static class SpikeCacheLoader extends CacheLoader<Long, List<SpikeFeature.EndSpike>> {
      public List<SpikeFeature.EndSpike> load(Long p_66910_) {
         List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
         Collections.shuffle(list, new Random(p_66910_));
         List<SpikeFeature.EndSpike> list1 = Lists.newArrayList();

         for(int i = 0; i < 10; ++i) {
            int j = Mth.m_14107_(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int k = Mth.m_14107_(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
            int l = list.get(i);
            int i1 = 2 + l / 3;
            int j1 = 76 + l * 3;
            boolean flag = l == 1 || l == 2;
            list1.add(new SpikeFeature.EndSpike(j, k, i1, j1, flag));
         }

         return list1;
      }
   }
}