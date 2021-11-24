package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public abstract class SurfaceBuilder<C extends SurfaceBuilderConfiguration> extends net.minecraftforge.registries.ForgeRegistryEntry<SurfaceBuilder<?>> {
   private static final BlockState f_75193_ = Blocks.f_50493_.m_49966_();
   private static final BlockState f_75194_ = Blocks.f_50440_.m_49966_();
   private static final BlockState f_75195_ = Blocks.f_50599_.m_49966_();
   private static final BlockState f_75196_ = Blocks.f_49994_.m_49966_();
   private static final BlockState f_75197_ = Blocks.f_50069_.m_49966_();
   private static final BlockState f_75177_ = Blocks.f_50546_.m_49966_();
   private static final BlockState f_75178_ = Blocks.f_49992_.m_49966_();
   private static final BlockState f_75179_ = Blocks.f_49993_.m_49966_();
   private static final BlockState f_75180_ = Blocks.f_50287_.m_49966_();
   private static final BlockState f_75181_ = Blocks.f_50195_.m_49966_();
   private static final BlockState f_75182_ = Blocks.f_50135_.m_49966_();
   private static final BlockState f_75183_ = Blocks.f_50134_.m_49966_();
   private static final BlockState f_75184_ = Blocks.f_50259_.m_49966_();
   private static final BlockState f_75185_ = Blocks.f_50699_.m_49966_();
   private static final BlockState f_75186_ = Blocks.f_50690_.m_49966_();
   private static final BlockState f_75187_ = Blocks.f_50451_.m_49966_();
   private static final BlockState f_75188_ = Blocks.f_50692_.m_49966_();
   private static final BlockState f_75189_ = Blocks.f_50730_.m_49966_();
   private static final BlockState f_75190_ = Blocks.f_50137_.m_49966_();
   private static final BlockState f_75191_ = Blocks.f_50450_.m_49966_();
   public static final SurfaceBuilderBaseConfiguration f_75198_ = new SurfaceBuilderBaseConfiguration(f_75195_, f_75193_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75199_ = new SurfaceBuilderBaseConfiguration(f_75196_, f_75196_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75200_ = new SurfaceBuilderBaseConfiguration(f_75194_, f_75193_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75201_ = new SurfaceBuilderBaseConfiguration(f_75197_, f_75197_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75202_ = new SurfaceBuilderBaseConfiguration(f_75177_, f_75193_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75203_ = new SurfaceBuilderBaseConfiguration(f_75178_, f_75178_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75204_ = new SurfaceBuilderBaseConfiguration(f_75194_, f_75193_, f_75178_);
   public static final SurfaceBuilderBaseConfiguration f_75205_ = new SurfaceBuilderBaseConfiguration(f_75178_, f_75178_, f_75178_);
   public static final SurfaceBuilderBaseConfiguration f_75206_ = new SurfaceBuilderBaseConfiguration(f_75179_, f_75180_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75207_ = new SurfaceBuilderBaseConfiguration(f_75181_, f_75193_, f_75196_);
   public static final SurfaceBuilderBaseConfiguration f_75208_ = new SurfaceBuilderBaseConfiguration(f_75183_, f_75183_, f_75183_);
   public static final SurfaceBuilderBaseConfiguration f_75209_ = new SurfaceBuilderBaseConfiguration(f_75182_, f_75182_, f_75182_);
   public static final SurfaceBuilderBaseConfiguration f_75210_ = new SurfaceBuilderBaseConfiguration(f_75184_, f_75184_, f_75184_);
   public static final SurfaceBuilderBaseConfiguration f_75211_ = new SurfaceBuilderBaseConfiguration(f_75185_, f_75183_, f_75187_);
   public static final SurfaceBuilderBaseConfiguration f_75212_ = new SurfaceBuilderBaseConfiguration(f_75186_, f_75183_, f_75188_);
   public static final SurfaceBuilderBaseConfiguration f_75213_ = new SurfaceBuilderBaseConfiguration(f_75189_, f_75190_, f_75191_);
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75214_ = m_75225_("default", new DefaultSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75215_ = m_75225_("mountain", new MountainSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75216_ = m_75225_("shattered_savanna", new ShatteredSavanaSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75217_ = m_75225_("gravelly_mountain", new GravellyMountainSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75218_ = m_75225_("giant_tree_taiga", new GiantTreeTaigaSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75167_ = m_75225_("swamp", new SwampSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75168_ = m_75225_("badlands", new BadlandsSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75169_ = m_75225_("wooded_badlands", new WoodedBadlandsSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75170_ = m_75225_("eroded_badlands", new ErodedBadlandsSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75171_ = m_75225_("frozen_ocean", new FrozenOceanSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75172_ = m_75225_("nether", new NetherSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75173_ = m_75225_("nether_forest", new NetherForestSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75174_ = m_75225_("soul_sand_valley", new SoulSandValleySurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75175_ = m_75225_("basalt_deltas", new BasaltDeltasSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> f_75176_ = m_75225_("nope", new NopeSurfaceBuilder(SurfaceBuilderBaseConfiguration.f_75241_));
   private final Codec<ConfiguredSurfaceBuilder<C>> f_75192_;

   private static <C extends SurfaceBuilderConfiguration, F extends SurfaceBuilder<C>> F m_75225_(String p_75226_, F p_75227_) {
      return Registry.m_122961_(Registry.f_122835_, p_75226_, p_75227_);
   }

   public SurfaceBuilder(Codec<C> p_75221_) {
      this.f_75192_ = p_75221_.fieldOf("config").xmap(this::m_75223_, ConfiguredSurfaceBuilder::m_74770_).codec();
   }

   public Codec<ConfiguredSurfaceBuilder<C>> m_75240_() {
      return this.f_75192_;
   }

   public ConfiguredSurfaceBuilder<C> m_75223_(C p_75224_) {
      return new ConfiguredSurfaceBuilder<>(this, p_75224_);
   }

   public abstract void m_142263_(Random p_164213_, ChunkAccess p_164214_, Biome p_164215_, int p_164216_, int p_164217_, int p_164218_, double p_164219_, BlockState p_164220_, BlockState p_164221_, int p_164222_, int p_164223_, long p_164224_, C p_164225_);

   public void m_6190_(long p_75222_) {
   }
}
