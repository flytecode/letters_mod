package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class ConfiguredSurfaceBuilder<SC extends SurfaceBuilderConfiguration> {
   public static final Codec<ConfiguredSurfaceBuilder<?>> f_74762_ = Registry.f_122835_.dispatch((p_74774_) -> {
      return p_74774_.f_74764_;
   }, SurfaceBuilder::m_75240_);
   public static final Codec<Supplier<ConfiguredSurfaceBuilder<?>>> f_74763_ = RegistryFileCodec.m_135589_(Registry.f_122879_, f_74762_);
   public final SurfaceBuilder<SC> f_74764_;
   public final SC f_74765_;

   public ConfiguredSurfaceBuilder(SurfaceBuilder<SC> p_74768_, SC p_74769_) {
      this.f_74764_ = p_74768_;
      this.f_74765_ = p_74769_;
   }

   public void m_163848_(Random p_163849_, ChunkAccess p_163850_, Biome p_163851_, int p_163852_, int p_163853_, int p_163854_, double p_163855_, BlockState p_163856_, BlockState p_163857_, int p_163858_, int p_163859_, long p_163860_) {
      this.f_74764_.m_142263_(p_163849_, p_163850_, p_163851_, p_163852_, p_163853_, p_163854_, p_163855_, p_163856_, p_163857_, p_163858_, p_163859_, p_163860_, this.f_74765_);
   }

   public void m_74771_(long p_74772_) {
      this.f_74764_.m_6190_(p_74772_);
   }

   public SC m_74770_() {
      return this.f_74765_;
   }
}