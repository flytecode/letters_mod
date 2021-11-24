package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class AmbientParticleSettings {
   public static final Codec<AmbientParticleSettings> f_47412_ = RecordCodecBuilder.create((p_47423_) -> {
      return p_47423_.group(ParticleTypes.f_123791_.fieldOf("options").forGetter((p_151654_) -> {
         return p_151654_.f_47413_;
      }), Codec.FLOAT.fieldOf("probability").forGetter((p_151652_) -> {
         return p_151652_.f_47414_;
      })).apply(p_47423_, AmbientParticleSettings::new);
   });
   private final ParticleOptions f_47413_;
   private final float f_47414_;

   public AmbientParticleSettings(ParticleOptions p_47417_, float p_47418_) {
      this.f_47413_ = p_47417_;
      this.f_47414_ = p_47418_;
   }

   public ParticleOptions m_47419_() {
      return this.f_47413_;
   }

   public boolean m_47424_(Random p_47425_) {
      return p_47425_.nextFloat() <= this.f_47414_;
   }
}