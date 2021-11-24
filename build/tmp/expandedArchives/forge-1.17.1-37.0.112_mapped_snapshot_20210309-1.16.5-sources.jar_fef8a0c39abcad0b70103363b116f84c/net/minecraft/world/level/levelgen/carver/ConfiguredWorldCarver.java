package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class ConfiguredWorldCarver<WC extends CarverConfiguration> {
   public static final Codec<ConfiguredWorldCarver<?>> f_64846_ = Registry.f_122837_.dispatch((p_64867_) -> {
      return p_64867_.f_64849_;
   }, WorldCarver::m_65072_);
   public static final Codec<Supplier<ConfiguredWorldCarver<?>>> f_64847_ = RegistryFileCodec.m_135589_(Registry.f_122880_, f_64846_);
   public static final Codec<List<Supplier<ConfiguredWorldCarver<?>>>> f_64848_ = RegistryFileCodec.m_135600_(Registry.f_122880_, f_64846_);
   private final WorldCarver<WC> f_64849_;
   private final WC f_64850_;

   public ConfiguredWorldCarver(WorldCarver<WC> p_64853_, WC p_64854_) {
      this.f_64849_ = p_64853_;
      this.f_64850_ = p_64854_;
   }

   public WC m_64855_() {
      return this.f_64850_;
   }

   public boolean m_159273_(Random p_159274_) {
      return this.f_64849_.m_142512_(this.f_64850_, p_159274_);
   }

   public boolean m_159265_(CarvingContext p_159266_, ChunkAccess p_159267_, Function<BlockPos, Biome> p_159268_, Random p_159269_, Aquifer p_159270_, ChunkPos p_159271_, BitSet p_159272_) {
      return this.f_64849_.m_142404_(p_159266_, this.f_64850_, p_159267_, p_159268_, p_159269_, p_159270_, p_159271_, p_159272_);
   }
}