package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public class TreeConfiguration implements FeatureConfiguration {
   public static final Codec<TreeConfiguration> f_68184_ = RecordCodecBuilder.create((p_161228_) -> {
      return p_161228_.group(BlockStateProvider.f_68747_.fieldOf("trunk_provider").forGetter((p_161248_) -> {
         return p_161248_.f_68185_;
      }), TrunkPlacer.f_70262_.fieldOf("trunk_placer").forGetter((p_161246_) -> {
         return p_161246_.f_68190_;
      }), BlockStateProvider.f_68747_.fieldOf("foliage_provider").forGetter((p_161244_) -> {
         return p_161244_.f_161213_;
      }), BlockStateProvider.f_68747_.fieldOf("sapling_provider").forGetter((p_161242_) -> {
         return p_161242_.f_161214_;
      }), FoliagePlacer.f_68519_.fieldOf("foliage_placer").forGetter((p_161240_) -> {
         return p_161240_.f_68189_;
      }), BlockStateProvider.f_68747_.fieldOf("dirt_provider").forGetter((p_161238_) -> {
         return p_161238_.f_161212_;
      }), FeatureSize.f_68281_.fieldOf("minimum_size").forGetter((p_161236_) -> {
         return p_161236_.f_68191_;
      }), TreeDecorator.f_70021_.listOf().fieldOf("decorators").forGetter((p_161234_) -> {
         return p_161234_.f_68187_;
      }), Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((p_161232_) -> {
         return p_161232_.f_68193_;
      }), Codec.BOOL.fieldOf("force_dirt").orElse(false).forGetter((p_161230_) -> {
         return p_161230_.f_161215_;
      })).apply(p_161228_, TreeConfiguration::new);
   });
   //TODO: Review this, see if we can hook in the sapling into the Codec
   public final BlockStateProvider f_68185_;
   public final BlockStateProvider f_161212_;
   public final TrunkPlacer f_68190_;
   public final BlockStateProvider f_161213_;
   public final BlockStateProvider f_161214_;
   public final FoliagePlacer f_68189_;
   public final FeatureSize f_68191_;
   public final List<TreeDecorator> f_68187_;
   public final boolean f_68193_;
   public final boolean f_161215_;

   protected TreeConfiguration(BlockStateProvider p_161217_, TrunkPlacer p_161218_, BlockStateProvider p_161219_, BlockStateProvider p_161220_, FoliagePlacer p_161221_, BlockStateProvider p_161222_, FeatureSize p_161223_, List<TreeDecorator> p_161224_, boolean p_161225_, boolean p_161226_) {
      this.f_68185_ = p_161217_;
      this.f_68190_ = p_161218_;
      this.f_161213_ = p_161219_;
      this.f_68189_ = p_161221_;
      this.f_161212_ = p_161222_;
      this.f_161214_ = p_161220_;
      this.f_68191_ = p_161223_;
      this.f_68187_ = p_161224_;
      this.f_68193_ = p_161225_;
      this.f_161215_ = p_161226_;
   }

   public TreeConfiguration m_68210_(List<TreeDecorator> p_68211_) {
      return new TreeConfiguration(this.f_68185_, this.f_68190_, this.f_161213_, this.f_161214_, this.f_68189_, this.f_161212_, this.f_68191_, p_68211_, this.f_68193_, this.f_161215_);
   }

   public static class TreeConfigurationBuilder {
      public final BlockStateProvider f_68229_;
      private final TrunkPlacer f_68232_;
      public final BlockStateProvider f_161249_;
      public final BlockStateProvider f_161250_;
      private final FoliagePlacer f_68231_;
      private BlockStateProvider f_161251_;
      private final FeatureSize f_68233_;
      private List<TreeDecorator> f_68234_ = ImmutableList.of();
      private boolean f_68236_;
      private boolean f_161252_;

      public TreeConfigurationBuilder(BlockStateProvider p_161254_, TrunkPlacer p_161255_, BlockStateProvider p_161256_, BlockStateProvider p_161257_, FoliagePlacer p_161258_, FeatureSize p_161259_) {
         this.f_68229_ = p_161254_;
         this.f_68232_ = p_161255_;
         this.f_161249_ = p_161256_;
         this.f_161250_ = p_161257_;
         this.f_161251_ = new SimpleStateProvider(Blocks.f_50493_.m_49966_());
         this.f_68231_ = p_161258_;
         this.f_68233_ = p_161259_;
      }

      public TreeConfiguration.TreeConfigurationBuilder m_161260_(BlockStateProvider p_161261_) {
         this.f_161251_ = p_161261_;
         return this;
      }

      public TreeConfiguration.TreeConfigurationBuilder m_68249_(List<TreeDecorator> p_68250_) {
         this.f_68234_ = p_68250_;
         return this;
      }

      public TreeConfiguration.TreeConfigurationBuilder m_68244_() {
         this.f_68236_ = true;
         return this;
      }

      public TreeConfiguration.TreeConfigurationBuilder m_161262_() {
         this.f_161252_ = true;
         return this;
      }

      public TreeConfiguration m_68251_() {
         return new TreeConfiguration(this.f_68229_, this.f_68232_, this.f_161249_, this.f_161250_, this.f_68231_, this.f_161251_, this.f_68233_, this.f_68234_, this.f_68236_, this.f_161252_);
      }
   }
}
