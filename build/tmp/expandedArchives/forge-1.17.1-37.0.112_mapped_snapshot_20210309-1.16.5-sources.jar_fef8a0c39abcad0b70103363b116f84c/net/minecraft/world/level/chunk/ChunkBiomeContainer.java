package net.minecraft.world.level.chunk;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkBiomeContainer implements BiomeManager.NoiseBiomeSource {
   private static final Logger f_62108_ = LogManager.getLogger();
   private static final int f_62109_ = Mth.m_14163_(16) - 2;
   private static final int f_62106_ = (1 << f_62109_) - 1;
   public static final int f_156118_ = 1 << f_62109_ + f_62109_ + DimensionType.f_156649_ - 2;
   private final IdMap<Biome> f_62111_;
   private final Biome[] f_62112_;
   private final int f_156119_;
   private final int f_156120_;

   protected ChunkBiomeContainer(IdMap<Biome> p_156137_, LevelHeightAccessor p_156138_, Biome[] p_156139_) {
      this.f_62111_ = p_156137_;
      this.f_62112_ = p_156139_;
      this.f_156119_ = QuartPos.m_175400_(p_156138_.m_141937_());
      this.f_156120_ = QuartPos.m_175400_(p_156138_.m_141928_()) - 1;
   }

   public ChunkBiomeContainer(IdMap<Biome> p_156133_, LevelHeightAccessor p_156134_, int[] p_156135_) {
      this(p_156133_, p_156134_, new Biome[p_156135_.length]);
      int i = -1;

      for(int j = 0; j < this.f_62112_.length; ++j) {
         int k = p_156135_[j];
         Biome biome = p_156133_.m_7942_(k);
         if (biome == null) {
            if (i == -1) {
               i = j;
            }

            this.f_62112_[j] = p_156133_.m_7942_(0);
         } else {
            this.f_62112_[j] = biome;
         }
      }

      if (i != -1) {
         f_62108_.warn("Invalid biome data received, starting from {}: {}", i, Arrays.toString(p_156135_));
      }

   }

   public ChunkBiomeContainer(IdMap<Biome> p_156122_, LevelHeightAccessor p_156123_, ChunkPos p_156124_, BiomeSource p_156125_) {
      this(p_156122_, p_156123_, p_156124_, p_156125_, (int[])null);
   }

   public ChunkBiomeContainer(IdMap<Biome> p_156127_, LevelHeightAccessor p_156128_, ChunkPos p_156129_, BiomeSource p_156130_, @Nullable int[] p_156131_) {
      this(p_156127_, p_156128_, new Biome[(1 << f_62109_ + f_62109_) * m_156140_(p_156128_.m_141928_(), 4)]);
      int i = QuartPos.m_175400_(p_156129_.m_45604_());
      int j = this.f_156119_;
      int k = QuartPos.m_175400_(p_156129_.m_45605_());

      for(int l = 0; l < this.f_62112_.length; ++l) {
         if (p_156131_ != null && l < p_156131_.length) {
            this.f_62112_[l] = p_156127_.m_7942_(p_156131_[l]);
         }

         if (this.f_62112_[l] == null) {
            this.f_62112_[l] = m_156143_(p_156130_, i, j, k, l);
         }
      }

   }

   private static int m_156140_(int p_156141_, int p_156142_) {
      return (p_156141_ + p_156142_ - 1) / p_156142_;
   }

   private static Biome m_156143_(BiomeSource p_156144_, int p_156145_, int p_156146_, int p_156147_, int p_156148_) {
      int i = p_156148_ & f_62106_;
      int j = p_156148_ >> f_62109_ + f_62109_;
      int k = p_156148_ >> f_62109_ & f_62106_;
      return p_156144_.m_7158_(p_156145_ + i, p_156146_ + j, p_156147_ + k);
   }

   public int[] m_62131_() {
      int[] aint = new int[this.f_62112_.length];

      for(int i = 0; i < this.f_62112_.length; ++i) {
         aint[i] = this.f_62111_.m_7447_(this.f_62112_[i]);
      }

      return aint;
   }

   public Biome m_7158_(int p_62133_, int p_62134_, int p_62135_) {
      int i = p_62133_ & f_62106_;
      int j = Mth.m_14045_(p_62134_ - this.f_156119_, 0, this.f_156120_);
      int k = p_62135_ & f_62106_;
      return this.f_62112_[j << f_62109_ + f_62109_ | k << f_62109_ | i];
   }
}