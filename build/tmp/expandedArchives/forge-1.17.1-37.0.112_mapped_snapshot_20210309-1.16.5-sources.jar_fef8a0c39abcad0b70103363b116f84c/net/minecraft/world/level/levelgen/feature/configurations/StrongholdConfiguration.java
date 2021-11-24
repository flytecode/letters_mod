package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class StrongholdConfiguration {
   public static final Codec<StrongholdConfiguration> f_68148_ = RecordCodecBuilder.create((p_68159_) -> {
      return p_68159_.group(Codec.intRange(0, 1023).fieldOf("distance").forGetter(StrongholdConfiguration::m_68157_), Codec.intRange(0, 1023).fieldOf("spread").forGetter(StrongholdConfiguration::m_68160_), Codec.intRange(1, 4095).fieldOf("count").forGetter(StrongholdConfiguration::m_68161_)).apply(p_68159_, StrongholdConfiguration::new);
   });
   private final int f_68149_;
   private final int f_68150_;
   private final int f_68151_;

   public StrongholdConfiguration(int p_68154_, int p_68155_, int p_68156_) {
      this.f_68149_ = p_68154_;
      this.f_68150_ = p_68155_;
      this.f_68151_ = p_68156_;
   }

   public int m_68157_() {
      return this.f_68149_;
   }

   public int m_68160_() {
      return this.f_68150_;
   }

   public int m_68161_() {
      return this.f_68151_;
   }
}