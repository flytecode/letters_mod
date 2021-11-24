package net.minecraft.world.level.biome;

import com.google.common.hash.Hashing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;

public class BiomeManager {
   static final int f_151750_ = QuartPos.m_175400_(8);
   private final BiomeManager.NoiseBiomeSource f_47862_;
   private final long f_47863_;
   private final BiomeZoomer f_47864_;

   public BiomeManager(BiomeManager.NoiseBiomeSource p_47866_, long p_47867_, BiomeZoomer p_47868_) {
      this.f_47862_ = p_47866_;
      this.f_47863_ = p_47867_;
      this.f_47864_ = p_47868_;
   }

   public static long m_47877_(long p_47878_) {
      return Hashing.sha256().hashLong(p_47878_).asLong();
   }

   public BiomeManager m_47879_(BiomeSource p_47880_) {
      return new BiomeManager(p_47880_, this.f_47863_, this.f_47864_);
   }

   public Biome m_47881_(BlockPos p_47882_) {
      return this.f_47864_.m_7782_(this.f_47863_, p_47882_.m_123341_(), p_47882_.m_123342_(), p_47882_.m_123343_(), this.f_47862_);
   }

   public Biome m_47869_(double p_47870_, double p_47871_, double p_47872_) {
      int i = QuartPos.m_175400_(Mth.m_14107_(p_47870_));
      int j = QuartPos.m_175400_(Mth.m_14107_(p_47871_));
      int k = QuartPos.m_175400_(Mth.m_14107_(p_47872_));
      return this.m_47873_(i, j, k);
   }

   public Biome m_47883_(BlockPos p_47884_) {
      int i = QuartPos.m_175400_(p_47884_.m_123341_());
      int j = QuartPos.m_175400_(p_47884_.m_123342_());
      int k = QuartPos.m_175400_(p_47884_.m_123343_());
      return this.m_47873_(i, j, k);
   }

   public Biome m_47873_(int p_47874_, int p_47875_, int p_47876_) {
      return this.f_47862_.m_7158_(p_47874_, p_47875_, p_47876_);
   }

   public Biome m_151752_(ChunkPos p_151753_) {
      return this.f_47862_.m_151754_(p_151753_);
   }

   public interface NoiseBiomeSource {
      Biome m_7158_(int p_47885_, int p_47886_, int p_47887_);

      default Biome m_151754_(ChunkPos p_151755_) {
         return this.m_7158_(QuartPos.m_175404_(p_151755_.f_45578_) + BiomeManager.f_151750_, 0, QuartPos.m_175404_(p_151755_.f_45579_) + BiomeManager.f_151750_);
      }
   }
}